package com.finderfeed.solarcraft.local_library.bedrock_loader.animations.packets;

import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.registries.animations.AnimationReloadableResourceListener;
import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.Map;
import java.util.function.Supplier;

@Packet("animations_sync_packet")
public class AnimationsSyncPacket extends FDPacket {

    private CompoundTag data;

    public AnimationsSyncPacket(Map<ResourceLocation, JsonObject> animationData){
        data = new CompoundTag();
        for (var entry : animationData.entrySet()){
            data.putString(entry.getKey().toString(),entry.getValue().toString());
        }
    }



    public AnimationsSyncPacket(FriendlyByteBuf buf) {
        this.data = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(data);
    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        AnimationReloadableResourceListener.INSTANCE.replaceAnimations(data);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
