package com.finderfeed.solarcraft.content.entities.projectiles;

import com.finderfeed.solarcraft.client.particles.SolarcraftParticle;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.content.entities.OrbitalCannonExplosionEntity;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.CameraShakePacket;
import com.finderfeed.solarcraft.packet_handler.packets.FlashPacket;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.network.NetworkDirection;

import java.util.List;

public class OrbitalExplosionProjectile extends NormalProjectile{

    private int flyUpTicks = 80;
    private int xDest;
    private int zDest;
    private boolean fallingDown = false;
    private int explosionRadius = 10;
    private int explosionDepth = 10;

    public OrbitalExplosionProjectile(EntityType<? extends AbstractHurtingProjectile> p_36817_, double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
        super(p_36817_, p_36818_, p_36819_, p_36820_, p_36821_, p_36822_, p_36823_, p_36824_);
    }

    public OrbitalExplosionProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
        super(p_36826_, p_36827_, p_36828_, p_36829_, p_36830_, p_36831_);
    }

    public OrbitalExplosionProjectile(EntityType<OrbitalExplosionProjectile> orbitalExplosionProjectileEntityType, Level level) {
        super(orbitalExplosionProjectileEntityType,level);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide){
            if (this.tickCount > 5000){
                this.remove(RemovalReason.DISCARDED);
            }
            this.beforeFalling();
            this.afterFalling();
        }else{
           this.clientTick();
        }
    }

    private void clientTick(){
        for (int i = 0; i < 5;i++){
            ClientHelpers.Particles.createParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    this.getX() + level.random.nextDouble()*3-1.5,
                    this.getY() + level.random.nextDouble()*3-1.5,
                    this.getZ() + level.random.nextDouble()*3-1.5,

                    level.random.nextDouble()*0.05-0.025,
                    level.random.nextDouble()*0.05-0.025,
                    level.random.nextDouble()*0.05-0.025,255,255,0,0.5f);

        }
    }

    private void beforeFalling(){
        if (!fallingDown) {
            if (flyUpTicks-- > 0) {
                this.setDeltaMovement(0,3,0);
            } else {
                ChunkPos pos = new ChunkPos(xDest >> 4, zDest >> 4);
                Helpers.loadChunkAtPos((ServerLevel) level, new BlockPos(pos.getMinBlockX(), 0, pos.getMinBlockZ()), true, true);
                ChunkPos thispos = new ChunkPos(this.getOnPos());
                Helpers.loadChunkAtPos((ServerLevel) level,new BlockPos(thispos.getMinBlockX(),0,thispos.getMinBlockZ()),false,true);
                fallingDown = true;
                int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, xDest, zDest);
                this.teleportTo(xDest, y + 300, zDest);
            }
        }
    }


    private void afterFalling(){
        if (fallingDown){
            this.setDeltaMovement(0,-6,0);
        }
    }

    private boolean exploded = false;
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (!level.isClientSide && !exploded){
            this.doExplosion(blockHitResult.getBlockPos());
            exploded = true;
        }
        super.onHitBlock(blockHitResult);
    }

    private void doExplosion(BlockPos pos){
        List<ServerPlayer> players = level.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(explosionRadius + 100));
        for (ServerPlayer player : players){
            if (explosionRadius >= 15 || explosionDepth >= 15) {
                SCPacketHandler.INSTANCE.sendTo(new FlashPacket(0, 40, 40), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
            SCPacketHandler.INSTANCE.sendTo(new CameraShakePacket(0,40,160,1.0f),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
        level.playSound(null,this.getOnPos(), SolarcraftSounds.ORBITAL_EXPLOSION.get(), SoundSource.HOSTILE,
                100,1);
        OrbitalCannonExplosionEntity explosion = new OrbitalCannonExplosionEntity(level,explosionRadius,explosionDepth,3);
        explosion.setPos(Helpers.getBlockCenter(pos).add(0,-0.5,0));
        level.addFreshEntity(explosion);
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    public boolean save(CompoundTag tag) {
        tag.putInt("xDest",this.xDest);
        tag.putInt("zDest",this.zDest);
        tag.putInt("eradius",this.explosionRadius);
        tag.putInt("edpeth",this.explosionDepth);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        this.zDest = tag.getInt("zDest");
        this.xDest = tag.getInt("xDest");
        this.explosionDepth = tag.getInt("edepth");
        this.explosionRadius = tag.getInt("eradius");
        super.load(tag);
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_36837_) {
        return true;
    }

    public void setExplosionDepth(int explosionDepth) {
        this.explosionDepth = explosionDepth;
    }

    public void setExplosionRadius(int explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public void setDestination(int x, int z){
        this.xDest = x;
        this.zDest = z;
    }
}
