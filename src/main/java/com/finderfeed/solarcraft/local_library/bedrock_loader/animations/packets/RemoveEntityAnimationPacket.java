package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.packets;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("remove_entity_animation_packet")
public class RemoveEntityAnimationPacket extends FDPacket {

    private int entityId;
    private String tickerName;

    public RemoveEntityAnimationPacket(Entity entity, String tickerName) {
        this.entityId = entity.getId();
        this.tickerName = tickerName;
    }

//    public RemoveEntityAnimationPacket(FriendlyByteBuf buf){
//        this.entityId = buf.readInt();
//        this.tickerName = buf.readUtf();
//    }

    @Override
    public void read(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.tickerName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(entityId);
        buf.writeUtf(tickerName);
    }

//    public void handle(PlayPayloadContext ctx){
//
//            ClientPacketHandles.handleRemoveEntityAnimationPacket(entityId,tickerName);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleRemoveEntityAnimationPacket(entityId,tickerName);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
