package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;

public class OrbitalCannonExplosionEntity extends Entity {

    private static final int RADIUS_OF_RING = 5;
    private static final int EXPLOSION_TICK = 40;

    private int radius;
    private int depth;
    private int blockCorrosionRadius;
    private int explosionTick = 0;
    private List<ExplosionRing> rings;


    public OrbitalCannonExplosionEntity(EntityType<?> type, Level level){
        super(type,level);
    }

    public OrbitalCannonExplosionEntity(Level level,int maxBlockRadius,int maxExplosionDepth,int blockCorrosionRadius) {
        this(SolarcraftEntityTypes.ORBITAL_EXPLOSION.get(), level);
        this.radius = maxBlockRadius;
        this.depth = maxExplosionDepth;
        this.blockCorrosionRadius = blockCorrosionRadius;
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (rings == null) {
                this.initRings();
            }

            this.explode(explosionTick);;
            explosionTick++;
        }

    }

    private void explode(int tick){
        if (explosionTick % EXPLOSION_TICK == 0){
            int ringN = tick / EXPLOSION_TICK;
            if (ringN < rings.size()) {
                ExplosionRing ring = rings.get(ringN);
                for (BlockPos pos : ring.blocksToDestroy()){
                    this.tryDestroyBlock(pos);
                }
            }else{
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    private void tryDestroyBlock(BlockPos pos){
        level.setBlock(pos, Blocks.AIR.defaultBlockState(),2);
    }

    private void initRings(){
        rings = new ArrayList<>();
        if (radius > depth){
            float mod = 1f / radius;
            float nDepth = depth*mod;
            int rm = this.radius;
            for (int i = 0; rm > 0; i++){
                int rad = Math.min(RADIUS_OF_RING,rm);
                float innerRad = i*RADIUS_OF_RING;
                float innerDepth = i*RADIUS_OF_RING*nDepth;
                this.rings.add(constructRing(innerRad,innerDepth, innerRad + rad,innerDepth + rad*nDepth));
                rm -= rad;
            }
        }else{
            float mod = 1f / depth;
            float nRad = radius*mod;
            int rm = this.depth;
            for (int i = 0; rm > 0; i++){
                int rad = Math.min(RADIUS_OF_RING,rm);
                float innerRad = i*RADIUS_OF_RING*nRad;
                float innerDepth = i*RADIUS_OF_RING;
                this.rings.add(constructRing(innerRad,innerDepth, innerRad + rad*nRad,innerDepth + rad));
                rm -= rad;
            }

        }
    }

    private ExplosionRing constructRing(float innerRad,float innerDepth,float endRad,float endDepth){
        AABB outerBox = new AABB(-endRad,-endDepth,-endRad,endRad,endDepth,endRad);
        List<BlockPos> all = new ArrayList<>();
        BlockPos.betweenClosedStream(outerBox).forEach(pos->{
            if (this.shouldPosBeAdded(pos,innerRad,innerDepth,endRad,endDepth)){
                BlockPos ps = new BlockPos(pos.getX(),pos.getY(),pos.getZ()).offset(blockPosition());
                all.add(ps);
            }
        });

        return new ExplosionRing(all);
    }

    private boolean shouldPosBeAdded(BlockPos pos,float innerRad,float innerDepth,float endRad,float endDepth){
        boolean a = FDMathHelper.isBetweenEllipses(
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                radius - blockCorrosionRadius,
                depth - blockCorrosionRadius,
                radius,
                depth
        );
        if (a && level.random.nextFloat() < 0.45){
            return false;
        }
        return FDMathHelper.isBetweenEllipses(pos.getX() + 0.5f,pos.getY() + 0.5f,pos.getZ() + 0.5f,
                innerRad,innerDepth,endRad,endDepth);
    }


    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.radius = tag.getInt("explosion_radius");
        this.explosionTick = tag.getInt("explosion_tick");
        this.depth = tag.getInt("explosion_depth");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("explosion_radius",this.radius);
        tag.putInt("explosion_tick",this.explosionTick);
        tag.putInt("explosion_depth",this.depth);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public record ExplosionRing(List<BlockPos> blocksToDestroy){}
}
