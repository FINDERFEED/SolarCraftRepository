package com.finderfeed.solarcraft.client.particles.lightning_particle;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class LightningParticleOptions implements ParticleOptions {


    public static final ParticleOptions.Deserializer<LightningParticleOptions> DESERIALIZER = new Deserializer<>() {
        @Override
        public LightningParticleOptions fromCommand(ParticleType<LightningParticleOptions> p_123733_, StringReader p_123734_) throws CommandSyntaxException {

            return new LightningParticleOptions(1f, 255, 255, 0, 2, 2343542, 60);
        }

        @Override
        public LightningParticleOptions fromNetwork(ParticleType<LightningParticleOptions> type, FriendlyByteBuf buf) {
            if (type != SCParticleTypes.LIGHTNING_PARTICLE.get()) {
                return new LightningParticleOptions(1f, 255, 255, 0, 2, 2343542, 60);
            }
            return new LightningParticleOptions(
                    buf.readFloat(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt()
            );
        }
    };


    private float quadSize;
    private int r;
    private int g;
    private int b;
    private int breaksCount;

    private int seed;
    private int lifetime;

    public LightningParticleOptions(float quadSize, int r, int g, int b, int breaksCount,int seed,int lifetime) {
        this.quadSize = quadSize;
        this.r = r;
        this.g = g;
        this.b = b;
        this.breaksCount = breaksCount;
        this.seed = seed;
        this.lifetime = lifetime;
    }


    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public float getQuadSize() {
        return quadSize;
    }

    public int getBreaksCount() {
        return breaksCount;
    }

    public int getSeed() {
        return seed;
    }

    public int getLifetime() {
        return lifetime;
    }

    @Override
    public ParticleType<?> getType() {
        return SCParticleTypes.LIGHTNING_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.quadSize);
        buf.writeInt(this.r);
        buf.writeInt(this.g);
        buf.writeInt(this.b);
        buf.writeInt(this.breaksCount);
        buf.writeInt(this.seed);
        buf.writeInt(this.lifetime);
    }

    @Override
    public String writeToString() {
        return "Lightning Particle";
    }

    private static final Codec<LightningParticleOptions> CODEC = RecordCodecBuilder.create(builder->
        builder.group(
                Codec.FLOAT.fieldOf("quadSize").forGetter(p->p.quadSize),
                Codec.INT.fieldOf("r").forGetter(p->p.r),
                Codec.INT.fieldOf("g").forGetter(p->p.g),
                Codec.INT.fieldOf("b").forGetter(p->p.b),
                Codec.INT.fieldOf("breaksCount").forGetter(p->p.breaksCount),
                Codec.INT.fieldOf("seed").forGetter(p->p.seed),
                Codec.INT.fieldOf("lifetime").forGetter(p->p.lifetime)
        ).apply(builder,LightningParticleOptions::new)
    );

    public static Codec<LightningParticleOptions> codec(){
        return CODEC;
    }
}
