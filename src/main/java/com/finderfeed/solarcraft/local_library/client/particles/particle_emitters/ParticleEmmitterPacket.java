package com.finderfeed.solarcraft.local_library.client.particles.particle_emitters;

import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

@Packet("particle_emitter_packet")
public class ParticleEmmitterPacket extends FDPacket {


    private ParticleEmitterData data;
    public ParticleEmmitterPacket(ParticleEmitterData data){
        this.data = data;
    }


    public ParticleEmmitterPacket(FriendlyByteBuf buf){
        ParticleEmitterData data = new ParticleEmitterData();
        data.fromNetwork(buf);
        this.data = data;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        this.data.toNetwork(buf);
    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ParticleEmitter emitter = data.toParticleEmitter();
        ParticleEmittersHandler.EMITTER_ENGINE.addEmitter(emitter);
    }
}
