package com.finderfeed.solarcraft.packet_handler.packet_system;

import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public final class FDPacketUtil {

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload... packetPayload){
        PacketDistributor.PLAYER.with(player).send(packetPayload);
    }

    public static void sendToDimension(ResourceKey<Level> dimension,CustomPacketPayload... packetPayload){
        PacketDistributor.DIMENSION.with(dimension).send(packetPayload);
    }

    public static void sendToServer(CustomPacketPayload... packetPayload){
        PacketDistributor.SERVER.noArg().send(packetPayload);
    }

    public static void sendToTrackingChunk(LevelChunk chunk,CustomPacketPayload packetPayload){
        PacketDistributor.TRACKING_CHUNK.with(chunk).send(packetPayload);
    }
    public static void sendToTrackingEntity(Entity entity, CustomPacketPayload packetPayload){
        PacketDistributor.TRACKING_ENTITY.with(entity).send(packetPayload);
    }

    public static void sendToPlayersCloseToSpot(Level level, Vec3 spot, double radius, CustomPacketPayload packet){
        PacketDistributor.NEAR.with(new PacketDistributor.TargetPoint(
                spot.x,spot.y,spot.z,radius,level.dimension()
        )).send(packet);
    }
}
