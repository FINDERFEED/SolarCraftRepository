package com.finderfeed.solarcraft.packet_handler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public class PacketHelper {

    public static void sendToPlayersTrackingEntity(Entity trackingEntity,Object packet){
        SCPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(()->trackingEntity),packet);
    }

    public static void sendToPlayersCloseToSpot(Level level, Vec3 spot,double radius,Object packet){
        SCPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(()->new PacketDistributor.TargetPoint(
                spot.x,spot.y,spot.z,radius,level.dimension()
        )),packet);
    }


}
