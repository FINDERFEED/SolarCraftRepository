package com.finderfeed.solarforge.packet_handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleAlchemistPacket {
    private final boolean toggle;

    public ToggleAlchemistPacket(PacketBuffer buf){
        toggle = buf.readBoolean();
    }
    public ToggleAlchemistPacket(boolean toggle){
        this.toggle = toggle;
    }
    public void toBytes(PacketBuffer buf){
        buf.writeBoolean(toggle);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                ClientPlayerEntity entitycl = Minecraft.getInstance().player;
                entitycl.getPersistentData().putBoolean("is_alchemist_toggled", toggle);
            });

        });
        ctx.get().setPacketHandled(true);
    }
}
