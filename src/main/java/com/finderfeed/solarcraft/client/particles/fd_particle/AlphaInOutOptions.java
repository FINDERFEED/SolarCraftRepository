package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.finderfeed.solarcraft.client.particles.FDParticleOptions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

public class AlphaInOutOptions extends FDParticleOptions<AlphaInOutOptions> {


    public int in;
    public int out;

    public AlphaInOutOptions(int in, int out) {
        this.in = in;
        this.out = out;
    }

    public AlphaInOutOptions(FriendlyByteBuf buf){
        this.in = buf.readInt();
        this.out = buf.readInt();
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeInt(in);
        buf.writeInt(out);
    }

    public static final Codec<AlphaInOutOptions> CODEC = RecordCodecBuilder.create(p->p.group(
            Codec.INT.fieldOf("in").forGetter(i->i.in),
            Codec.INT.fieldOf("out").forGetter(i->i.out)
    ).apply(p,AlphaInOutOptions::new));

    @Override
    public Codec<AlphaInOutOptions> codec() {
        return CODEC;
    }
}
