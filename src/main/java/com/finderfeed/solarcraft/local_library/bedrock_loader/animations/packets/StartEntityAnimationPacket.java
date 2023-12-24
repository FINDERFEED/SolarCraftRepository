package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.packets;

import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.Animation;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.packet_handler.ClientPacketHandles;
import com.finderfeed.solarcraft.registries.animations.AnimationReloadableResourceListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.NetworkEvent;
import java.util.function.Supplier;

public class StartEntityAnimationPacket {

    private int entityId;
    private String tickerName;
    private AnimationTicker animationTicker;

    public StartEntityAnimationPacket(Entity entity, String tickerName, AnimationTicker ticker) {
        this.entityId = entity.getId();
        this.tickerName = tickerName;
        this.animationTicker = ticker;
    }

    public StartEntityAnimationPacket(FriendlyByteBuf buf){
        this.entityId = buf.readInt();
        this.tickerName = buf.readUtf();
        this.animationTicker = AnimationTicker.deserialize(buf.readAnySizeNbt());
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(entityId);
        buf.writeUtf(tickerName);
        buf.writeNbt(animationTicker.serialize());
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ClientPacketHandles.handleSetEntityAnimationPacket(entityId,tickerName,animationTicker);
        });
        ctx.get().setPacketHandled(true);
    }


}
