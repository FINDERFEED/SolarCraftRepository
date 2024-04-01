package com.finderfeed.solarcraft.content.entities.dungeon_ray_controller;

import com.finderfeed.solarcraft.content.entities.DungeonRay;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DungeonRayHandler {
    public List<BlockPos> movePositionOffsets;
    public float movespeed;
    public int moveTarget;
    public boolean backward;
    public Direction rayDir;
    public void tickRay(BlockPos mainPos,DungeonRay ray){
        ray.setDirection(rayDir);
        Vec3 movePos = movePositionOffsets.get(moveTarget).getCenter();
        Vec3 rayPos = ray.position();
        Vec3 b = movePos.subtract(rayPos);
        if (b.length() > movespeed){
            ray.setPos(rayPos.add(b.normalize().multiply(movespeed,movespeed,movespeed)));
        }else{
            ray.setPos(movePos);
            if (!backward){
                moveTarget++;
                if (moveTarget >= movePositionOffsets.size()){
                    moveTarget = movePositionOffsets.size() - 2;
                    backward = true;
                }
            }else{
                moveTarget--;
                if (moveTarget < 0){
                    moveTarget = 1;
                    backward = false;
                }
            }
        }
    }


    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putString("direction",rayDir.getName());
        tag.putInt("moveTarget",moveTarget);
        tag.putBoolean("backward",backward);
        tag.putFloat("movespeed",movespeed);
        CompoundNBTHelper.writeBlockPosList("movePositions",movePositionOffsets,tag);
        return tag;
    }

    public static DungeonRayHandler fromTag(CompoundTag tag){
        int tick = tag.getInt("tick");
        int moveTarget = tag.getInt("moveTarget");
        boolean backward = tag.getBoolean("backward");
        float speed = tag.getFloat("movespeed");
        List<BlockPos> offsets = CompoundNBTHelper.getBlockPosList("movePositions",tag);
        Direction dir = Direction.UP;
        if (tag.contains("direction")) {
            dir = Direction.byName(tag.getString("direction"));
        }
        DungeonRayHandler handler = new DungeonRayHandler();
        handler.moveTarget = moveTarget;
        handler.backward = backward;
        handler.movespeed = speed;
        handler.movePositionOffsets = offsets;
        handler.rayDir = dir;


        return handler;
    }
}