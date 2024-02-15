package com.finderfeed.solarcraft.client.particles.fd_particle.instances;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.client.particles.fd_particle.AlphaInOutOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDDefaultOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDScalingOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDTSParticleOptions;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class SmokeParticleOptions extends FDTSParticleOptions {

    public static final Deserializer<SmokeParticleOptions> DESERIALIZER = new Deserializer<SmokeParticleOptions>() {
        @Override
        public SmokeParticleOptions fromCommand(ParticleType<SmokeParticleOptions> p_123733_, StringReader p_123734_) throws CommandSyntaxException {
            return new SmokeParticleOptions();
        }

        @Override
        public SmokeParticleOptions fromNetwork(ParticleType<SmokeParticleOptions> p_123735_, FriendlyByteBuf buf) {
            return new SmokeParticleOptions(new FDDefaultOptions(buf),new FDScalingOptions(buf),new AlphaInOutOptions(buf));
        }
    };

    public SmokeParticleOptions(FDDefaultOptions defaultOptions, FDScalingOptions scalingOptions, AlphaInOutOptions alphaOptions) {
        super(defaultOptions, scalingOptions, alphaOptions);
    }
    public SmokeParticleOptions() {
        super(new FDDefaultOptions(0.5f,60,1,1,1,1,true,false),
                new FDScalingOptions(30,30),
                new AlphaInOutOptions(0,0));
    }


    public static final Codec<SmokeParticleOptions> CODEC = RecordCodecBuilder.create(p->p.group(
            FDDefaultOptions.CODEC.fieldOf("default_options").forGetter(o->o.defaultOptions),
            FDScalingOptions.CODEC.fieldOf("scaling_options").forGetter(o->o.scalingOptions),
            AlphaInOutOptions.CODEC.fieldOf("alpha_in_out_options").forGetter(o->o.alphaOptions)
    ).apply(p,SmokeParticleOptions::new));

    @Override
    public ParticleType<?> getType() {
        return SCParticleTypes.SMOKE_PARTICLE.get();
    }
}
