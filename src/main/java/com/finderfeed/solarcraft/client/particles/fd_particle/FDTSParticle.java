package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.Nullable;

public abstract class FDTSParticle extends TextureSheetParticle {
    private FDTSParticleOptions options;
    public float initSize;
    protected FDTSParticle(FDTSParticleOptions options,ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.options = options;
        this.x = x;
        this.y = y;
        this.z = z;
        FDDefaultOptions defaultOptions = options.defaultOptions;
        this.rCol = defaultOptions.r;
        this.gCol = defaultOptions.g;
        this.bCol = defaultOptions.b;
        this.alpha = defaultOptions.a;
        this.quadSize = defaultOptions.size;
        this.hasPhysics = defaultOptions.hasPhysics;
        this.lifetime = defaultOptions.lifetime;
        this.initSize = this.quadSize;
    }


    @Override
    public void tick() {
        super.tick();
        if (!removed) {
            FDScalingOptions scalingOptions = this.options.scalingOptions;
            int scaleIn = scalingOptions.in;
            int scaleOut = scalingOptions.out;
            float p = 1;
            if (this.age < scaleIn) {
                p = this.age / (float) scaleIn;
            } else if (this.age > this.lifetime - scaleOut) {
                int remTime = this.lifetime - this.age;
                p = 1 - remTime / (float)scaleOut;
            }
            this.quadSize = initSize * p;
        }

    }

    public FDTSParticleOptions getOptions() {
        return options;
    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
