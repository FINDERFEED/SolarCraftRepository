package com.finderfeed.solarforge.packet_handler.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleAlchemistPacket {
    private final boolean toggle;

    public ToggleAlchemistPacket(FriendlyByteBuf buf){
        toggle = buf.readBoolean();
    }
    public ToggleAlchemistPacket(boolean toggle){
        this.toggle = toggle;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeBoolean(toggle);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                LocalPlayer entitycl = Minecraft.getInstance().player;
                entitycl.getPersistentData().putBoolean("is_alchemist_toggled", toggle);
            });

        });
        ctx.get().setPacketHandled(true);
    }
}
