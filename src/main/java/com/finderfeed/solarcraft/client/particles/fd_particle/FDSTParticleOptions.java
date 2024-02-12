package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class FDSTParticleOptions extends ParticleType<FDSTParticleOptions> implements ParticleOptions {

    public static final ParticleOptions.Deserializer<FDSTParticleOptions> DESERIALIZER = new Deserializer<FDSTParticleOptions>() {
        @Override
        public FDSTParticleOptions fromCommand(ParticleType<FDSTParticleOptions> buf, StringReader reader) throws CommandSyntaxException {
            return new FDSTParticleOptions(true);
        }

        @Override
        public FDSTParticleOptions fromNetwork(ParticleType<FDSTParticleOptions> type, FriendlyByteBuf buf) {
            return FDSTParticleOptions.fromNetwork(buf);
        }
    };

    public FDDefaultOptions defaultOptions;
    public FDScalingOptions scalingOptions;
    public AlphaInOutOptions alphaOptions;

    public FDSTParticleOptions(boolean overrideLimiter){
        super(overrideLimiter,DESERIALIZER);
        this.defaultOptions = new FDDefaultOptions(0.5f,60,1f,1f,1f,1f);
        this.scalingOptions = new FDScalingOptions(0,0);
        this.alphaOptions = new AlphaInOutOptions(0,0);
    }

    public FDSTParticleOptions(FDDefaultOptions defaultOptions,FDScalingOptions scalingOptions,AlphaInOutOptions alphaOptions){
        super(true,DESERIALIZER);
        this.defaultOptions = defaultOptions;
        this.scalingOptions = scalingOptions;
        this.alphaOptions = alphaOptions;
    }

    public static FDSTParticleOptions fromNetwork(FriendlyByteBuf buf){
        return new FDSTParticleOptions(new FDDefaultOptions(buf),new FDScalingOptions(buf),new AlphaInOutOptions(buf));
    }

    @Override
    public ParticleType<?> getType() {
        return this;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        this.defaultOptions.toNetwork(buf);
        this.scalingOptions.toNetwork(buf);
        this.alphaOptions.toNetwork(buf);
    }

    @Override
    public String writeToString() {
        return "fdst_particle_options";
    }

    public static final Codec<FDSTParticleOptions> CODEC = RecordCodecBuilder.create(p->p.group(
            FDDefaultOptions.CODEC.fieldOf("default_options").forGetter(r->r.defaultOptions),
            FDScalingOptions.CODEC.fieldOf("scaling_options").forGetter(r->r.scalingOptions),
            AlphaInOutOptions.CODEC.fieldOf("alpha_options").forGetter(r->r.alphaOptions)
    ).apply(p,FDSTParticleOptions::new));

    @Override
    public Codec<FDSTParticleOptions> codec() {
        return CODEC;
    }
}
