package com.finderfeed.solarcraft.content.entities.uldera_crystal;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.SendShapeParticlesPacket;
import com.finderfeed.solarcraft.client.particles.server_data.shapes.instances.BurstAttackParticleShape;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimatedObject;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationManager;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.EntityServerAnimationManager;
import com.finderfeed.solarcraft.packet_handler.PacketHelper;
import com.finderfeed.solarcraft.registries.animations.SCAnimations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EffectCrystal extends LivingEntity implements AnimatedObject,UlderaCrystalBuddy {


    public static List<MobEffect> EFFECTS = List.of(
            MobEffects.MOVEMENT_SLOWDOWN,
            MobEffects.POISON,
            MobEffects.WITHER
    );
    public static final AABB BOX = new AABB(-20,-20,-20,20,20,20);


    private AnimationManager manager;
    public EffectCrystal(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
        setNoGravity(true);
        manager = AnimationManager.createEntityAnimationManager(this,level.isClientSide);
    }


    @Override
    public void tick() {
        super.tick();
        this.getAnimationManager().tickAnimations();
        if (!level.isClientSide){
            if (this.tickCount % 300 == 0){
                this.spreadEffects();

            }
        }else{
            this.getAnimationManager().setAnimation("main",
                    AnimationTicker.Builder.begin(SCAnimations.EFFECT_CRYSTAL_IDLE.get())
                            .replaceable(false)
                            .startFrom(0)
                            .toNullTransitionTime(10)
                            .build());
        }
    }

    @Override
    public void die(DamageSource p_21014_) {
        super.die(p_21014_);
        if (!level.isClientSide && this.isDeadOrDying()){
            Vec3 c = getCenterPos();
            ((ServerLevel)level).sendParticles(BallParticleOptions.Builder.begin()
                    .setLifetime(60)
                    .setSize(0.5f)
                    .setRGB(90,0,186)
                    .setShouldShrink(true)
                    .setPhysics(false)
                    .build(),c.x,c.y,c.z,25,0,0,0,0.05);
            this.deathTime = 100;
        }
    }

    private void spreadEffects(){
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class,BOX.move(this.position()),entity->{
            if (entity instanceof Player player && (player.isCreative() || player.isSpectator())){
                return false;
            }
            return !(entity instanceof UlderaCrystalBuddy) && this.isEntityReachable(entity);
        })){
            entity.addEffect(new MobEffectInstance(EFFECTS.get(level.random.nextInt(EFFECTS.size())),
                    300,0));
            Vec3 epos = entity.position().add(0,entity.getBbHeight()/2f,0);
            PacketHelper.sendToPlayersTrackingEntity(this,
                    new SendShapeParticlesPacket(
                            new BurstAttackParticleShape(this.getCenterPos(),epos,0.5f,2,
                                    0.05),
                            BallParticleOptions.Builder.begin()
                                    .setLifetime(30)
                                    .setSize(0.25f)
                                    .setRGB(90,0,186)
                                    .setShouldShrink(true)
                                    .setPhysics(false)
                                    .build()
                    ));
        }
    }


    private boolean isEntityReachable(LivingEntity target){
        Vec3 centerPos = this.getCenterPos();
        Vec3 targetCenter = target.position().add(0,target.getEyeHeight(target.getPose())/2f,0);
        HitResult result = Helpers.getEntityHitResult(this,level,centerPos,targetCenter,(entity)->true);
        if (result == null){
            return false;
        }
        return true;
    }

    private Vec3 getCenterPos(){
        return this.position().add(0,this.getBbHeight()/2f,0);
    }

    @Override
    public void knockback(double p_147241_, double p_147242_, double p_147243_) {

    }

    @Override
    public void push(Entity p_21294_) {

    }

    @Override
    public void push(double p_20286_, double p_20287_, double p_20288_) {

    }

    @Override
    protected void pushEntities() {

    }

    public static AttributeSupplier.Builder createAttributes(){
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,20)
                .add(Attributes.ARMOR,10)
                .add(Attributes.ARMOR_TOUGHNESS,2);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public AnimationManager getAnimationManager() {
        return manager;
    }

}
