package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import java.util.function.Supplier;

@Packet("teleport_entity_packet")
public class TeleportEntityPacket extends FDPacket {

    private int entityID;
    private Vec3 position;


    @Override
    public void read(FriendlyByteBuf buf) {
        this.entityID = buf.readInt();
        this.position = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
    }

    public TeleportEntityPacket(Entity entity,Vec3 position){
        this.entityID = entity.getId();
        this.position = position;

    }
    public void toBytes(FriendlyByteBuf buf){
       buf.writeInt(entityID);
       buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
    }
//    public void handle(PlayPayloadContext ctx){
//
//            ClientHelpers.handleTeleportEntityPacket(entityID,position);
//
//    }


    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.handleTeleportEntityPacket(entityID,position);
    }

    public static void sendPacket(ServerLevel world, Entity entity, Vec3 position){
        Vec3 ePos  = entity.position();
        PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(ePos.x,ePos.y,ePos.z,100,world.dimension()).get()).send(
                new TeleportEntityPacket(entity,position));
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
