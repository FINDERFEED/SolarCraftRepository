package com.finderfeed.solarcraft.client.particles.ball_particle;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class BallParticleOptions implements ParticleOptions {
    public static final ParticleOptions.Deserializer<BallParticleOptions> DESERIALIZER = new Deserializer<BallParticleOptions>() {
        @Override
        public BallParticleOptions fromCommand(ParticleType<BallParticleOptions> p_123733_, StringReader p_123734_) throws CommandSyntaxException {
            return new BallParticleOptions(0.25f,255,255,255,60,true,true);
        }

        @Override
        public BallParticleOptions fromNetwork(ParticleType<BallParticleOptions> type, FriendlyByteBuf buf) {
            if (type != SCParticleTypes.BALL_PARTICLE.get()){
                return new BallParticleOptions(0.25f,255,255,255,60,true,true);
            }
            return BallParticleOptions.fromNetwork(buf);
        }
    };
    private float size;
    private int r;
    private int g;
    private int b;
    private int lifetime;
    private boolean shouldShrink;

    private boolean hasPhysics = true;
    public BallParticleOptions(float size,int r,int g,int b,int lifetime,boolean shouldShrink,boolean hasPhysics){
        this.size = size;
        this.r = r;
        this.g = g;
        this.b = b;
        this.lifetime = lifetime;
        this.shouldShrink = shouldShrink;
        this.hasPhysics = hasPhysics;
    }
    public BallParticleOptions(Builder builder){
        this(
                builder.size,
                builder.r,
                builder.g,
                builder.b,
                builder.lifetime,
                builder.shouldShrink,
                builder.hasPhysics
        );
    }

    @Override
    public ParticleType<?> getType() {
        return SCParticleTypes.BALL_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(size);
        buf.writeInt(r);
        buf.writeInt(g);
        buf.writeInt(b);
        buf.writeInt(lifetime);
        buf.writeBoolean(shouldShrink);
        buf.writeBoolean(this.hasPhysics);
    }

    public static BallParticleOptions fromNetwork(FriendlyByteBuf buf){
        float size = buf.readFloat();
        int r = buf.readInt();
        int g = buf.readInt();
        int b = buf.readInt();
        int lifetime = buf.readInt();
        boolean shrink = buf.readBoolean();
        boolean physics = buf.readBoolean();
        return new BallParticleOptions(size,r,g,b,lifetime,shrink,physics);
    }

    @Override
    public String writeToString() {
        return "solarcraft:ball_particle_type";
    }

    public static Codec<BallParticleOptions> codec(){
        return RecordCodecBuilder.create(builder->
           builder.group(
                   Codec.FLOAT.fieldOf("size").forGetter(options->options.size),
                   Codec.INT.fieldOf("r").forGetter(options->options.r),
                   Codec.INT.fieldOf("g").forGetter(options->options.g),
                   Codec.INT.fieldOf("b").forGetter(options->options.b),
                   Codec.INT.fieldOf("lifetime").forGetter(options->options.lifetime),
                   Codec.BOOL.fieldOf("shrink").forGetter(options->options.shouldShrink),
                   Codec.BOOL.fieldOf("hasphysics").forGetter(options->options.hasPhysics)
           ).apply(builder,BallParticleOptions::new)
        );
    }


    public float getSize() {
        return size;
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

    public int getLifetime() {
        return lifetime;
    }

    public boolean isShouldShrink() {
        return shouldShrink;
    }

    public boolean isHasPhysics() {
        return hasPhysics;
    }

    public static class Builder{

        private float size = 0.5f;
        private int r;
        private int g;
        private int b;
        private int lifetime = 60;
        private boolean shouldShrink = true;
        private boolean hasPhysics = true;

        public static Builder begin(){
            return new Builder();
        }

        public Builder setRGB(int r,int g,int b){
            this.r = r;
            this.g = g;
            this.b = b;
            return this;
        }

        public Builder setSize(float size) {
            this.size = size;
            return this;
        }


        public Builder setLifetime(int lifetime) {
            this.lifetime = lifetime;
            return this;
        }

        public Builder setShouldShrink(boolean shouldShrink) {
            this.shouldShrink = shouldShrink;
            return this;
        }

        public Builder setPhysics(boolean physics){
            this.hasPhysics = physics;
            return this;
        }

        public BallParticleOptions build(){
            return new BallParticleOptions(this);
        }
    }
}
