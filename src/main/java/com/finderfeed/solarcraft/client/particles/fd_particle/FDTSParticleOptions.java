package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public abstract class FDTSParticleOptions implements ParticleOptions {
    public FDDefaultOptions defaultOptions;
    public FDScalingOptions scalingOptions;
    public AlphaInOutOptions alphaOptions;

    public FDTSParticleOptions(FDDefaultOptions defaultOptions, FDScalingOptions scalingOptions, AlphaInOutOptions alphaOptions){
        this.defaultOptions = defaultOptions;
        this.scalingOptions = scalingOptions;
        this.alphaOptions = alphaOptions;
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

}
