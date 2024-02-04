package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.packets;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.animations.AnimationReloadableResourceListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("start_entity_animation_packet")
public class StartEntityAnimationPacket extends FDPacket {

    private int entityId;
    private String tickerName;
    private AnimationTicker animationTicker;

    public StartEntityAnimationPacket(Entity entity, String tickerName, AnimationTicker ticker) {
        this.entityId = entity.getId();
        this.tickerName = tickerName;
        this.animationTicker = new AnimationTicker(ticker);
    }



    public StartEntityAnimationPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.tickerName = buf.readUtf();
        this.animationTicker = AnimationTicker.deserialize(buf.readNbt());
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(entityId);
        buf.writeUtf(tickerName);
        buf.writeNbt(animationTicker.serialize());
    }

//    public void handle(PlayPayloadContext ctx){
//
//            ClientPacketHandles.handleSetEntityAnimationPacket(entityId,tickerName,animationTicker);
//
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientPacketHandles.handleSetEntityAnimationPacket(entityId,tickerName,animationTicker);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
