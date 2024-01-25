package com.finderfeed.solarcraft.packet_handler.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
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
    public void handle(PlayPayloadContext ctx){
        
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                LocalPlayer entitycl = Minecraft.getInstance().player;
                entitycl.getPersistentData().putBoolean("is_alchemist_toggled", toggle);
            });

        });
        
    }
}
