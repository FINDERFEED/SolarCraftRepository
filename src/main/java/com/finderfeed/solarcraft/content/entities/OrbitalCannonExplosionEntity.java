package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OrbitalCannonExplosionEntity extends Entity {

    private static final int RADIUS_OF_RING = 10;

    private int radius;
    private int depth;
    private int explosionTick = 0;
    private List<ExplosionRing> rings;


    public OrbitalCannonExplosionEntity(EntityType<?> type, Level level,int maxBlockRadius,int maxExplosionDepth) {
        super(type, level);
        this.radius = maxBlockRadius;
        this.depth = maxExplosionDepth;
    }


    @Override
    public void tick() {
        super.tick();
        if (rings == null){
           this.initRings();
        }
    }

    private void initRings(){
        if (radius > depth){
            float mod = 1f / radius;
            float nDepth = depth*mod;

        }else{
            float mod = 1f / depth;
            float nRad = radius*mod;

        }
    }

    private ExplosionRing constructRing(int innerRad,int innerDepth,int endRad,int endDepth){
        AABB outerBox = new AABB(-endRad,-endDepth,-endRad,endRad,endDepth,endRad);
        List<BlockPos> all = BlockPos.betweenClosedStream(outerBox).toList();
        Iterator<BlockPos> iterator = all.iterator();
        while (iterator.hasNext()){
            BlockPos pos = iterator.next();
            if (!FDMathHelper.isBetweenEllipses(pos.getX(),pos.getY(),pos.getZ(),innerRad,innerDepth,endRad,endDepth)){
                iterator.remove();
            }
        }
        return new ExplosionRing(all.stream().map(pos->pos.offset(blockPosition())).toList());
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
