package com.finderfeed.solarforge.capabilities.capability_mana;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateManaPacket {

    private final double mana;
    public UpdateManaPacket(FriendlyByteBuf buf){
        mana = buf.readDouble();

    }
    public UpdateManaPacket(double mana){
        this.mana = mana;
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(mana);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                LocalPlayer entitycl = Minecraft.getInstance().player;
                entitycl.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).setMana(mana);
            });

        });
        ctx.get().setPacketHandled(true);
    }
}
