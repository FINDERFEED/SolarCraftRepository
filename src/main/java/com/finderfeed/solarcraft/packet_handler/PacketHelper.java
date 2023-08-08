package com.finderfeed.solarcraft.packet_handler;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.PacketDistributor;

public class PacketHelper {

    public static void sendToPlayersTrackingEntity(Entity trackingEntity,Object packet){
        SCPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(()->trackingEntity),packet);
    }



}
