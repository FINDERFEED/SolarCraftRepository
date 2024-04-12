package com.finderfeed.solarcraft.content.entities.dungeon_ray_controller;

import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;

public class DungeonRayHandler {

    public static boolean stopRays = false;
    public List<BlockPos> movePositionOffsets;
    public float movespeed;
    public int moveTarget;
    public boolean backward;
    public boolean dontTraceBack = false;
    public Direction rayDir;
    public Vec3 currentPosition;
    public Vec3 oldPos;
    public float rayLength;

    public DungeonRayHandler(){
        rayDir = Direction.UP;
        movespeed = 0.3f;
        backward = false;
        movePositionOffsets = new ArrayList<>();
    }
    public void tickRay(BlockPos mainPos,Level level){
        if (!this.movePositionOffsets.isEmpty()) {
            this.rayLength = this.computeRayLength(level, mainPos.getCenter());
            this.oldPos = this.currentPosition;
            if (!level.isClientSide) {
                this.doDamage(mainPos.getCenter(), level);
            }
            if (stopRays) {
                return;
            }
            if (movePositionOffsets.size() > 1) {
                BlockPos offs = movePositionOffsets.get(moveTarget);
                Vec3 movePos = new Vec3(offs.getX(),offs.getY(),offs.getZ());
                Vec3 rayPos = currentPosition;
                Vec3 b = movePos.subtract(rayPos);
                if (b.length() > movespeed) {
                    currentPosition = rayPos.add(b.normalize().multiply(movespeed, movespeed, movespeed));
                } else {
                    currentPosition = movePos;
                    this.cycleNextMoveTarget();
                }
            }else{
                moveTarget = 0;
                BlockPos offs = movePositionOffsets.get(0);
                Vec3 movePos = new Vec3(offs.getX(),offs.getY(),offs.getZ());
                currentPosition = movePos;
                oldPos = currentPosition;
            }

        }
    }

    public void cycleNextMoveTarget(){
        if (dontTraceBack){
            moveTarget = Mth.clamp((moveTarget + 1) % movePositionOffsets.size(),0,Integer.MAX_VALUE);
        }else {
            if (!backward) {
                moveTarget++;
                if (moveTarget >= movePositionOffsets.size()) {
                    moveTarget = movePositionOffsets.size() - 2;
                    backward = true;
                }
            } else {
                moveTarget--;
                if (moveTarget < 0) {
                    moveTarget = 1;
                    backward = false;
                }
            }
        }
    }


    private void doDamage(Vec3 mainPos,Level level){
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,this.getDamageBox(mainPos));
        for (LivingEntity entity : entities){
            entity.hurt(SCDamageSources.RUNIC_MAGIC,10f);
        }
    }

    private AABB getDamageBox(Vec3 mainPos){
        float rayLen = this.rayLength;
        if (rayLen < 0.5){
            return new AABB(this.currentPosition,this.currentPosition);
        }
        float damageRadius = 0.25f;
        Vec3 begin = currentPosition.add(mainPos);
        Vec3i n = this.rayDir.getNormal();
        Vec3 nrm = new Vec3(n.getX(),n.getY(),n.getZ());
        rayLen -= damageRadius;
        Vec3 end = begin.add(nrm.x * rayLen,nrm.y * rayLen,nrm.z * rayLen);
        AABB box = new AABB(begin,end).inflate(damageRadius);
        return box;
    }

    private float computeRayLength(Level level,Vec3 mainPos){
        Vec3i n = this.rayDir.getNormal();
        Vec3 begin = currentPosition.add(mainPos);
        Vec3 end = begin.add(n.getX()*20,n.getY()*20,n.getZ()*20);
        ClipContext ctx = new ClipContext(begin,end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty());
        BlockHitResult res = level.clip(ctx);
        Vec3 location = res.getLocation();
        Vec3 i = location.subtract(begin);
        return Math.max((float) i.length() - 0.5f,0);
    }

    public void addPos(BlockPos offset){
        if (this.movePositionOffsets.isEmpty()){
            Vec3 movePos = new Vec3(offset.getX(),offset.getY(),offset.getZ());
            this.currentPosition = movePos;
            this.oldPos = currentPosition;
        }
        movePositionOffsets.add(offset);
    }



    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putString("direction",rayDir.getName());
        tag.putInt("moveTarget",moveTarget);
        tag.putBoolean("backward",backward);
        tag.putBoolean("dontTraceBack",dontTraceBack);
        tag.putFloat("movespeed",movespeed);
        CompoundNBTHelper.writeVec3("currentPosition",currentPosition,tag);
        CompoundNBTHelper.writeBlockPosList("movePositions",movePositionOffsets,tag);
        return tag;
    }

    public static DungeonRayHandler fromTag(CompoundTag tag){
        int moveTarget = tag.getInt("moveTarget");
        boolean backward = tag.getBoolean("backward");
        boolean dontTraceBack = tag.getBoolean("dontTraceBack");
        float speed = tag.getFloat("movespeed");
        List<BlockPos> offsets = CompoundNBTHelper.getBlockPosList("movePositions",tag);
        Direction dir = Direction.UP;
        if (tag.contains("direction")) {
            dir = Direction.byName(tag.getString("direction"));
        }
        Vec3 pos = CompoundNBTHelper.getVec3("currentPosition",tag);
        DungeonRayHandler handler = new DungeonRayHandler();
        handler.currentPosition = pos;
        handler.moveTarget = moveTarget;
        handler.backward = backward;
        handler.movespeed = speed;
        handler.movePositionOffsets = offsets;
        handler.rayDir = dir;
        handler.dontTraceBack = dontTraceBack;

        return handler;
    }
}