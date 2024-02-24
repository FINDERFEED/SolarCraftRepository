package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

public abstract class FDTSParticle extends TextureSheetParticle {
    private FDTSParticleOptions options;
    public float initSize;
    public float initAlpha;
    protected FDTSParticle(FDTSParticleOptions options,ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.options = options;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        FDDefaultOptions defaultOptions = options.defaultOptions;
        this.rCol = defaultOptions.r;
        this.gCol = defaultOptions.g;
        this.bCol = defaultOptions.b;
        this.alpha = defaultOptions.a;
        this.initAlpha = this.alpha;
        this.quadSize = defaultOptions.size;
        this.hasPhysics = defaultOptions.hasPhysics;
        this.lifetime = defaultOptions.lifetime;
        this.initSize = this.quadSize;
        if (options.scalingOptions.in > 0){
            this.quadSize = 0;
        }
        if (options.alphaOptions.in > 0){
            this.alpha = 0;
        }
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
                p = remTime / (float)scaleOut;
            }
            this.quadSize = initSize * p;

            AlphaInOutOptions alphaInOutOptions = this.options.alphaOptions;
            int alphaIn = alphaInOutOptions.in;
            int alphaOut = alphaInOutOptions.out;
            p = 1;
            if (this.age < alphaIn) {
                p = this.age / (float) alphaIn;
            } else if (this.age > this.lifetime - alphaOut) {
                int remTime = this.lifetime - this.age;
                p = remTime / (float)alphaOut;
            }
            this.alpha = initAlpha * p;
        }

    }

    public FDTSParticleOptions getOptions() {
        return options;
    }


    @Override
    public ParticleRenderType getRenderType() {
        return options.defaultOptions.additive ? SCRenderTypes.ParticleRenderTypes.ADDITIVE_TRANSLUCENT :
                SCRenderTypes.ParticleRenderTypes.NORMAL_TRANSLUCENT;
    }
}
