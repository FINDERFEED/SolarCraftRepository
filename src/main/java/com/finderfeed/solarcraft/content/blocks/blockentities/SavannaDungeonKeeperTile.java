package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SavannaDungeonKeeperTile extends SolarcraftBlockEntity {

    public static final BlockPos[] MONSTER_OFFSETS = {
        new BlockPos(3,-4,3),
        new BlockPos(3,-4,-3),
        new BlockPos(-3,-4,3),
        new BlockPos(-3,-4,-3),
    };

    private static final EntityType[] MONSTER_TYPES = {
            EntityType.ZOMBIE,
            EntityType.SKELETON
    };

    private List<UUID> usedPlayers = new ArrayList<>();

    public static final int MAX_TIME = 1250;
    public static final int SPAWN_EACH = 300;

    private int activeTime = 0;
    private boolean active = false;


    public SavannaDungeonKeeperTile(BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.SAVANNA_DUNGEON_KEEPER.get(), p_155229_, p_155230_);
    }


    public static void tick(SavannaDungeonKeeperTile tile, Level world,BlockState state,BlockPos pos){
        if (!world.isClientSide){
            tile.endActiveStateIfNecessary();
            tile.processActiveState();
        }else{
            tile.processActiveParticles();
        }
    }

    private void endActiveStateIfNecessary(){
        if (this.wasUsed()){
            List<Player> players = this.playersInRange();
            for (Player player : players){
                ServerPlayer p = (ServerPlayer) player;
                this.usedPlayers.add(p.getUUID());
            }
            if (this.level.getBlockEntity(this.getBlockPos().below(3)) instanceof ChestBlockEntity chestBlockEntity){
                Helpers.placeItemInChest(chestBlockEntity, SCItems.SOLAR_KEY.get().getDefaultInstance(),true);
            }
            this.active = false;
            this.activeTime = 0;
            Helpers.updateTile(this);
        }
    }

    private void processActiveState(){
        if (this.active){
            List<Player> players = this.playersInRange();
            if (this.activeTime % SPAWN_EACH == 0){
                Vec3 p = Helpers.posToVec(this.getBlockPos()).add(0.5,0.5,0.5);
                for (BlockPos offset : MONSTER_OFFSETS){
                    EntityType type = MONSTER_TYPES[level.random.nextInt(MONSTER_TYPES.length)];
                    Entity entity = type.spawn((ServerLevel) level,
                            (CompoundTag) null,null,this.getBlockPos().offset(offset), MobSpawnType.STRUCTURE,false,false);
                    if (entity instanceof Mob mob){
                        mob.setPersistenceRequired();
                    }
                }
                level.playSound(null,p.x,p.y,p.z, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS,1f,1f);
            }

            AABB box = this.affectionBox();

            for (Player player : players){
                if (Helpers.AABBTouchesAABB(box,player.getBoundingBox())){
                    Helpers.setServerPlayerSpeed((ServerPlayer) player,box.getCenter()
                            .subtract(player.position()).normalize().multiply(0.2f,0.2f,0.2f));
                }
            }
            this.activeTime++;
            if (this.activeTime > MAX_TIME){
                this.active = false;
                Helpers.updateTile(this);
            }
        }
    }

    private void processActiveParticles(){
        if (this.active){
            this.activeTime++;
            if (this.activeTime % SPAWN_EACH == 0){
                Vec3 p = Helpers.posToVec(this.getBlockPos());
                for (BlockPos offset : MONSTER_OFFSETS){
                    Vec3 ppos = p.add(offset.getX()+0.5f,offset.getY() + 0.25f,offset.getZ()+0.5f);
                    for (int i = 0; i < 10;i++) {
                        ClientHelpers.Particles.createParticle(
                                SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                                ppos.x, ppos.y, ppos.z,
                                level.random.nextDouble() * 0.05 - 0.025,
                                level.random.nextDouble() * 0.05 - 0.025,
                                level.random.nextDouble() * 0.05 - 0.025,
                                255, 255, 0, 0.25f
                        );
                    }
                }
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public boolean trigger(ServerPlayer p){
        if (this.usedPlayers.contains(p.getUUID())){
            return true;
        }
        if (!active && activeTime <= MAX_TIME && !playersInRange().isEmpty()){
            active = true;
            Helpers.updateTile(this);
            for (Player player : playersInRange()){
                Helpers.fireProgressionEvent(player, Progression.FIND_KEY_SOURCE);
            }
        }
        return false;
    }

    public List<Player> playersInRange(){
        return level.getEntitiesOfClass(Player.class,
                new AABB(Helpers.posToVec(this.getBlockPos()).add(-3.5,-4,-3.5),
                        Helpers.posToVec(this.getBlockPos()).add(4.5,-0.5,4.5)),
                player->!(player.isCreative() || player.isSpectator()));
    }

    public AABB affectionBox(){
        return new AABB(Helpers.posToVec(this.getBlockPos()).add(-3.5,-4.5,-3.5),
                Helpers.posToVec(this.getBlockPos()).add(4.5,-0.5,4.5));
    }

    public boolean wasUsed(){
        return activeTime >= MAX_TIME;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("time",this.activeTime);
        tag.putBoolean("activated",this.active);
        CompoundNBTHelper.saveUUIDList(tag,usedPlayers,"usedPlayers");
    }

    @Override
    public void load(CompoundTag tag) {
        this.active = tag.getBoolean("activated");
        this.activeTime = tag.getInt("time");
        this.usedPlayers = CompoundNBTHelper.loadUUIDList(tag,"usedPlayers");
        super.load(tag);
    }



    @Mod.EventBusSubscriber(modid = SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class LocalEventHandler{

        @SubscribeEvent
        public static void detectChestOpening(PlayerInteractEvent.RightClickBlock event){
            Player player = event.getEntity();
            if (!player.level().isClientSide){
                SavannaDungeonKeeperTile keeper = searchKeeper(player.level(),event.getPos(),4);
                if (keeper != null){
                    player.swing(event.getHand());
                    event.setCanceled(!keeper.trigger((ServerPlayer) player));
                }
            }
        }

        @SubscribeEvent
        public static void detectBlockPlacing(BlockEvent.EntityPlaceEvent event){
            if (event.getEntity() instanceof Player player && !player.level().isClientSide){
                SavannaDungeonKeeperTile keeper = searchKeeper(player.level(),event.getPos(),4);
                if (keeper != null){
                    event.setCanceled(true);
                }
            }
        }
        @SubscribeEvent
        public static void detectBlockBreaking(BlockEvent.BreakEvent event){
            Player player = event.getPlayer();
            if (!player.level().isClientSide){
                SavannaDungeonKeeperTile keeper = searchKeeper(player.level(),event.getPos(),4);
                if (keeper != null){
                    event.setCanceled(true);
                }
            }
        }
        @SubscribeEvent
        public static void detectTNT(ExplosionEvent.Start event){
            Level world = event.getLevel();
            if (!world.isClientSide){
                SavannaDungeonKeeperTile keeper = searchKeeper(world,Helpers.vecToPos(event.getExplosion().center()),10);
                if (keeper != null){
                    event.setCanceled(true);
                }
            }
        }

        private static SavannaDungeonKeeperTile searchKeeper(Level world, BlockPos offset,int horizontalCheck){
            for (int x = -horizontalCheck;x <= horizontalCheck;x++){
                for (int z = -horizontalCheck;z <= horizontalCheck;z++){
                    for (int y = -6;y <= 10;y++){
                        BlockPos pos = new BlockPos(x,y,z).offset(offset);
                        if (world.getBlockEntity(pos) instanceof SavannaDungeonKeeperTile tile && !tile.wasUsed()){
                            return tile;
                        }
                    }
                }
            }
            return null;
        }

    }
}
