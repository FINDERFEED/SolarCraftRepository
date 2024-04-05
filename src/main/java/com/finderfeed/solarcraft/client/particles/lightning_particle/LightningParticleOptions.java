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

            return new LightningParticleOptions(1f, 255, 255, 0, -1, 60,false);
        }

        @Override
        public LightningParticleOptions fromNetwork(ParticleType<LightningParticleOptions> type, FriendlyByteBuf buf) {
            if (type != SCParticleTypes.LIGHTNING_PARTICLE.get()) {
                return new LightningParticleOptions(1f, 255, 255, 0,  -1, 60,false);
            }
            return new LightningParticleOptions(
                    buf.readFloat(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readInt(),
                    buf.readBoolean()
            );
        }
    };


    private float quadSize;
    private int r;
    private int g;
    private int b;

    private int seed;
    private int lifetime;

    private boolean hasPhysics = false;

    public LightningParticleOptions(float quadSize, int r, int g, int b,int seed,int lifetime,boolean hasPhysics) {
        this.quadSize = quadSize;
        this.r = r;
        this.g = g;
        this.b = b;
        this.seed = seed;
        this.lifetime = lifetime;
    }

    public LightningParticleOptions(Builder builder) {
        this(builder.quadSize, builder.r, builder.g, builder.b, builder.seed, builder.lifetime, builder.hasPhysics);
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



    public int getSeed() {
        return seed;
    }

    public int getLifetime() {
        return lifetime;
    }

    public boolean hasPhysics() {
        return hasPhysics;
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
        buf.writeInt(this.seed);
        buf.writeInt(this.lifetime);
        buf.writeBoolean(this.hasPhysics);
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
                Codec.INT.fieldOf("seed").forGetter(p->p.seed),
                Codec.INT.fieldOf("lifetime").forGetter(p->p.lifetime),
                Codec.BOOL.fieldOf("hasPhysics").forGetter(p->p.hasPhysics)
        ).apply(builder,LightningParticleOptions::new)
    );

    public static Codec<LightningParticleOptions> codec(){
        return CODEC;
    }

    public static class Builder{

        private float quadSize;
        private int r;
        private int g;
        private int b;

        private int seed;
        private int lifetime;

        private boolean hasPhysics = false;

        public static Builder start(){
            return new Builder();
        }

        public Builder setQuadSize(float quadSize) {
            this.quadSize = quadSize;
            return this;
        }

        public Builder setRGB(int r,int g,int b) {
            this.r = r;
            this.g = g;
            this.b = b;
            return this;
        }

        public Builder setSeed(int seed) {
            this.seed = seed;
            return this;
        }

        public Builder setLifetime(int lifetime) {
            this.lifetime = lifetime;
            return this;
        }

        public Builder setHasPhysics(boolean hasPhysics) {
            this.hasPhysics = hasPhysics;
            return this;
        }

        public LightningParticleOptions build(){
            return new LightningParticleOptions(this);
        }
    }
}
