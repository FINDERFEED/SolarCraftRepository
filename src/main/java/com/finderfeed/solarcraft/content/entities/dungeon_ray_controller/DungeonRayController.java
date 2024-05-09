package com.finderfeed.solarcraft.content.entities.dungeon_ray_controller;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimatedObject;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationManager;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.EntityServerAnimationManager;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.animations.SCAnimations;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DungeonRayController extends Entity implements AnimatedObject {

    private List<DungeonRayHandler> handlers = new ArrayList<>();

    public static final EntityDataAccessor<Integer> CURRENT_SELECTED_HANDLER = SynchedEntityData.defineId(DungeonRayController.class, EntityDataSerializers.INT);

    private AnimationManager manager;
    private List<UUID> usedPlayers = new ArrayList<>();
    private int givingOutTicker = -1;

    public DungeonRayController(EntityType<?> entity, Level level) {
        super(entity, level);
        this.manager = AnimationManager.createEntityAnimationManager(this,level.isClientSide);
    }

    @Override
    public void tick() {
        super.tick();
        this.tickRays();
        this.manager.tickAnimations();
        if (!level.isClientSide){
            manager.getAsServerManager().setAnimation("idle",new AnimationTicker(SCAnimations.RAY_CONTROLLER_IDLE.get()));
            this.processGivingOut();
        }
    }

    private void processGivingOut() {
        if (givingOutTicker == 60) {
            this.manager.setAnimation("givingOut", new AnimationTicker.Builder(SCAnimations.RAY_CONTROLLER_SHAKE.get()).replaceable(true).build());
            this.particles();
        } else if (givingOutTicker == 7) {
            this.manager.setAnimation("givingOut", new AnimationTicker(SCAnimations.RAY_CONTROLLER_EXPLODE.get()));
            this.particles();
        }else if (givingOutTicker == 0){
            Vec3 v = this.position();
            ItemEntity entity = new ItemEntity(level,v.x,v.y,v.z, SCItems.DIMENSIONAL_SHARD.get().getDefaultInstance());
            entity.setDeltaMovement(entity.getDeltaMovement().add(0,0.1,0));
            level.addFreshEntity(entity);
        }else if (givingOutTicker == -1){
            this.manager.stopAnimation("givingOut");;
        }else{
            this.particles();
        }
        givingOutTicker = Mth.clamp(givingOutTicker - 1, -1,Integer.MAX_VALUE);
    }

    private void particles(){
        Vec3 v = this.position();
        ((ServerLevel) level).sendParticles(
                new BallParticleOptions(0.25f, 255, 255, 0, 20, true, false),
                v.x, v.y, v.z, 10, 0.25, 0.25, 0.25, 0
        );
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer){
            Helpers.fireProgressionEvent(serverPlayer, Progression.DIMENSIONAL_SHARD_DUNGEON);
            if (!usedPlayers.contains(serverPlayer.getUUID())){
                givingOutTicker = 60;
                usedPlayers.add(serverPlayer.getUUID());
            }
        }
        return super.interactAt(player, vec, hand);
    }

    private void tickRays(){
        for (int i = 0; i < handlers.size();i++){
            DungeonRayHandler handler = handlers.get(i);
            handler.tickRay(this.blockPosition(),this.level);
        }
    }

    public List<DungeonRayHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<DungeonRayHandler> handlers){
        this.handlers = handlers;
    }


    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        FDPacketUtil.sendToPlayer(player,new SendHandlersToClient(this));
        ((EntityServerAnimationManager)this.getAnimationManager().getAsServerManager()).sendAllAnimations(player);
    }

    @Override
    public boolean save(CompoundTag tag) {
        handlersToTag(this.getHandlers(),tag);
        CompoundNBTHelper.saveUUIDList(tag,usedPlayers,"uuids");
        tag.putInt("givingOutTicker",givingOutTicker);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.handlers = handlersFromTag(tag);
        this.usedPlayers = CompoundNBTHelper.loadUUIDList(tag,"uuids");
        if (tag.contains("givingOutTicker")) {
            this.givingOutTicker = tag.getInt("givingOutTicker");
        }else{
            this.givingOutTicker = -1;
        }
    }

    public static CompoundTag handlersToTag(List<DungeonRayHandler> handlers,CompoundTag tag){
        for (int i = 0; i < handlers.size();i++){
            var h = handlers.get(i);
            CompoundTag t = h.toTag();
            tag.put("handler"+i,t);
        }
        tag.putInt("handlersLen",handlers.size());
        return tag;
    }

    public static List<DungeonRayHandler> handlersFromTag(CompoundTag tag){
        List<DungeonRayHandler> handlers = new ArrayList<>();
        for (int i = 0; i < tag.getInt("handlersLen");i++){
            DungeonRayHandler handler = DungeonRayHandler.fromTag(tag.getCompound("handler"+i));
            handlers.add(handler);
        }
        return handlers;
    }

    @Nullable
    public DungeonRayHandler getCurrentSelectedHandler(){
        if (this.getCurrentSelectedHandlerId() < handlers.size()){
            return handlers.get(this.getCurrentSelectedHandlerId());
        }else{
            return null;
        }
    }

    public void removeSelectedHandler(){
        if (this.getCurrentSelectedHandlerId() < handlers.size()){
            handlers.remove(this.getCurrentSelectedHandlerId());
            this.cycleCurrentSelectedHandler();
        }
    }

    public int getCurrentSelectedHandlerId(){
        return this.entityData.get(CURRENT_SELECTED_HANDLER);
    }

    public void cycleCurrentSelectedHandler(){
        int len = this.handlers.size();
        int c = this.getCurrentSelectedHandlerId();
        this.entityData.set(CURRENT_SELECTED_HANDLER,((c + 1) % len));
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(CURRENT_SELECTED_HANDLER,0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }


    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }

    @Override
    public AnimationManager getAnimationManager() {
        return manager;
    }


    @Override
    public boolean isPickable() {
        return true;
    }


    @Override
    public void push(Entity p_20293_) {

    }

    @Override
    public void push(double p_20286_, double p_20287_, double p_20288_) {

    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    public static boolean isControllerAround(Level level,Vec3 pos){
        AABB checkBox = new AABB(-20,-10,-20,20,10,20).move(pos);
        if (!level.getEntitiesOfClass(DungeonRayController.class,checkBox).isEmpty()){
            return true;
        }
        return false;
    }

    @Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class Listener {

        @SubscribeEvent
        public static void preventExplosions(ExplosionEvent.Detonate event){
            Level level = event.getLevel();
            Explosion explosion = event.getExplosion();
            Vec3 pos = explosion.center();
            if (!level.isClientSide){
                if (isControllerAround(level,pos)){
                    explosion.clearToBlow();
                }
            }
        }

        @SubscribeEvent
        public static void preventBreakingBlocks(BlockEvent.BreakEvent event){
            Player player = event.getPlayer();
            Level level = player.level;
            BlockPos pos = event.getPos();
            if (!level.isClientSide && !player.isCreative()){
                BlockState state = event.getState();
                if (state.is(Tags.MAGISTONE) && isControllerAround(level,new Vec3(pos.getX(),pos.getY(),pos.getZ()))){
                    event.setCanceled(true);
                }
            }
        }
        @SubscribeEvent
        public static void preventPlacingBlocks(BlockEvent.EntityPlaceEvent event){
            Entity entity = event.getEntity();
            Level level = entity.level;
            BlockPos pos = event.getPos();
            if (!level.isClientSide && isControllerAround(level,new Vec3(pos.getX(),pos.getY(),pos.getZ()))){
                if (!(entity instanceof Player player)) {
                    event.setCanceled(true);
                }else{
                    if (!player.isCreative()){
                        event.setCanceled(true);
                    }
                }

            }
        }


    }
}
