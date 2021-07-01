package com.finderfeed.solarforge.capability_mana;

import com.finderfeed.solarforge.SolarAbilities.SolarAbilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateManaPacket {

    private final double mana;
    public UpdateManaPacket(PacketBuffer buf){
        mana = buf.readDouble();

    }
    public UpdateManaPacket(double mana){
        this.mana = mana;
    }
    public void toBytes(PacketBuffer buf){
        buf.writeDouble(mana);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ()-> {
                ClientPlayerEntity entitycl = Minecraft.getInstance().player;
                entitycl.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).setMana(mana);
            });

        });
        ctx.get().setPacketHandled(true);
    }
}
