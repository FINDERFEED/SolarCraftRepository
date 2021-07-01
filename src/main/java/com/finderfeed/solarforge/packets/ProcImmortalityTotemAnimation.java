package com.finderfeed.solarforge.packets;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.AbstractPacket;
import com.finderfeed.solarforge.misc_things.AbstractSolarNetworkRepeater;
import com.finderfeed.solarforge.particles.HealParticle;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ProcImmortalityTotemAnimation extends AbstractPacket {



    public ProcImmortalityTotemAnimation(PacketBuffer buf) {}
    public ProcImmortalityTotemAnimation(){}
    @Override
    public void toBytes(PacketBuffer buf) {

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()->{
            DistExecutor.safeRunWhenOn(Dist.CLIENT,()-> ClientHelpers::playTotemAnimation);
        });
        ctx.get().setPacketHandled(true);
    }
}
