package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.misc_things.AbstractPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnSolarStrikeParticleExplosionPacket extends AbstractPacket {


    public SpawnSolarStrikeParticleExplosionPacket(Vec3 vec){

    }

    public SpawnSolarStrikeParticleExplosionPacket(FriendlyByteBuf buf){

    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {

    }
}
