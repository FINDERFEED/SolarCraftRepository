package com.finderfeed.solarforge.for_future_library;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class EntityHelper {
    public static void setEntityPos(Entity entity, Vec3 position,double x,double y,double z){
        entity.setPos(position.x+x,position.y+y,position.z+z);
    }
    public static void setEntityPos(Entity entity, Vec3 position){
        entity.setPos(position.x,position.y,position.z);
    }
    public static void setEntityPos(Entity entity, Player position){
        entity.setPos(position.getX(),position.getY(),position.getZ());
    }
}
