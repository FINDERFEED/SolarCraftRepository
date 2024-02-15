package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.finderfeed.solarcraft.client.particles.FDParticleOptions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

public class FDDefaultOptions extends FDParticleOptions<FDDefaultOptions> {

    public float size = 0.25f;
    public int lifetime = 60;
    public float r = 1;
    public float g = 1;
    public float b = 1;
    public float a = 1;
    public boolean hasPhysics;
    public boolean additive = false;


    public FDDefaultOptions(FriendlyByteBuf buf){
        this.size = buf.readFloat();
        this.lifetime = buf.readInt();
        this.r = buf.readFloat();
        this.g = buf.readFloat();
        this.b = buf.readFloat();
        this.a = buf.readFloat();
        this.hasPhysics = buf.readBoolean();
        this.additive = buf.readBoolean();
    }

    public FDDefaultOptions(float size, int lifetime, float r, float g, float b, float a,boolean hasPhysics,boolean additiveRendering) {
        this.size = size;
        this.lifetime = lifetime;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.hasPhysics = hasPhysics;
        this.additive = additiveRendering;
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
        buf.writeBoolean(this.additive);
    }

    public static final Codec<FDDefaultOptions> CODEC = RecordCodecBuilder.create(p->p.group(
            Codec.FLOAT.fieldOf("size").forGetter(o->o.size),
            Codec.INT.fieldOf("lifetime").forGetter(o->o.lifetime),
            Codec.FLOAT.fieldOf("r").forGetter(o->o.r),
            Codec.FLOAT.fieldOf("g").forGetter(o->o.g),
            Codec.FLOAT.fieldOf("b").forGetter(o->o.b),
            Codec.FLOAT.fieldOf("a").forGetter(o->o.r),
            Codec.BOOL.fieldOf("hasPhysics").forGetter(o->o.hasPhysics),
            Codec.BOOL.fieldOf("additive_rendering").forGetter(o->o.additive)
    ).apply(p,FDDefaultOptions::new));

    @Override
    public Codec<FDDefaultOptions> codec() {
        return CODEC;
    }
}
