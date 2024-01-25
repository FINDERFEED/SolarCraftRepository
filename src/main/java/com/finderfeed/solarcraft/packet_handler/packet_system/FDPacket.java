package com.finderfeed.solarcraft.packet_handler.packet_system;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public abstract class FDPacket implements CustomPacketPayload {
    private ResourceLocation id;

    public ResourceLocation id(){
        if (id == null) {
            Packet packet = this.getClass().getAnnotation(Packet.class);
            if (packet == null)
                throw new RuntimeException("Packet " + this.getClass().getName() + " doesn't have @Packet annotation");
            return id = new ResourceLocation(SolarCraft.MOD_ID,packet.value());
        }else{
            return id;
        }
    }

}
