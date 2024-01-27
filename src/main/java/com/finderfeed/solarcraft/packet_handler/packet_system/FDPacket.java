package com.finderfeed.solarcraft.packet_handler.packet_system;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.util.thread.EffectiveSide;
import net.neoforged.neoforge.network.handling.ConfigurationPayloadContext;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

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
    public void clientPlayHandle(PlayPayloadContext ctx){}
    public void serverPlayHandle(PlayPayloadContext ctx){}
    public void clientConfigurationHandle(ConfigurationPayloadContext ctx){}
    public void serverConfigurationHandle(ConfigurationPayloadContext ctx){}
    public static void handlePlayPacket(CustomPacketPayload packet, PlayPayloadContext context){
        if (!(packet instanceof FDPacket fdPacket)) throw new RuntimeException("You can't do that");
        if (EffectiveSide.get().isClient()){
            fdPacket.clientPlayHandle(context);
        }else{
            fdPacket.serverPlayHandle(context);
        }
    }
    public static void handleConfigurationPacket(CustomPacketPayload packet, ConfigurationPayloadContext context){
        if (!(packet instanceof FDPacket fdPacket)) throw new RuntimeException("You can't do that");
        if (EffectiveSide.get().isClient()){
            fdPacket.clientConfigurationHandle(context);
        }else{
            fdPacket.serverConfigurationHandle(context);
        }

    }

}
