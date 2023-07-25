package com.finderfeed.solarcraft.registries.animations;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;


import java.util.Map;
import java.util.function.Supplier;

public class AnimationsPacket {

    private CompoundTag data;

    public AnimationsPacket(Map<ResourceLocation, JsonObject> animationData){
        data = new CompoundTag();
        for (var entry : animationData.entrySet()){
            data.putString(entry.getKey().toString(),entry.getValue().toString());
        }
    }

    public AnimationsPacket(FriendlyByteBuf buf){
        this.data = buf.readAnySizeNbt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeNbt(data);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
           AnimationReloadableResourceListener.INSTANCE.replaceAnimations(data);
        });
        ctx.get().setPacketHandled(true);
    }

}
