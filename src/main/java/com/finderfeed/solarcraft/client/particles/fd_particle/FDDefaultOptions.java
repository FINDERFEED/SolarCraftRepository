package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.finderfeed.solarcraft.client.particles.FDParticleOptions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

public class FDDefaultOptions extends FDParticleOptions<FDDefaultOptions> {

    public float size;
    public int lifetime;
    public float r;
    public float g;
    public float b;
    public float a;
    public boolean hasPhysics;


    public FDDefaultOptions(FriendlyByteBuf buf){
        this.size = buf.readFloat();
        this.lifetime = buf.readInt();
        this.r = buf.readFloat();
        this.g = buf.readFloat();
        this.b = buf.readFloat();
        this.a = buf.readFloat();
    }

    public FDDefaultOptions(float size, int lifetime, float r, float g, float b, float a,boolean hasPhysics) {
        this.size = size;
        this.lifetime = lifetime;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.hasPhysics = hasPhysics;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(size);
        buf.writeInt(lifetime);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(a);
        buf.writeBoolean(this.hasPhysics);
    }

    public static final Codec<FDDefaultOptions> CODEC = RecordCodecBuilder.create(p->p.group(
            Codec.FLOAT.fieldOf("size").forGetter(o->o.size),
            Codec.INT.fieldOf("lifetime").forGetter(o->o.lifetime),
            Codec.FLOAT.fieldOf("r").forGetter(o->o.r),
            Codec.FLOAT.fieldOf("g").forGetter(o->o.g),
            Codec.FLOAT.fieldOf("b").forGetter(o->o.b),
            Codec.FLOAT.fieldOf("a").forGetter(o->o.r),
            Codec.BOOL.fieldOf("hasPhysics").forGetter(o->o.hasPhysics)
    ).apply(p,FDDefaultOptions::new));

    @Override
    public Codec<FDDefaultOptions> codec() {
        return CODEC;
    }
}
