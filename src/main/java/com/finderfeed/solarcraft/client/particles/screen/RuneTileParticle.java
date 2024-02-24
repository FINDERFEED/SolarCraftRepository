package com.finderfeed.solarcraft.client.particles.screen;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.local_library.client.particles.TextureScreenParticle;
import net.minecraft.client.particle.ParticleRenderType;

public class RuneTileParticle extends TextureScreenParticle {
    public RuneTileParticle(int lifetime, double x, double y, double xSpeed, double ySpeed, double xAcceleration, double yAcceleration, int rCol, int gCol, int bCol, int alpha) {
        super(lifetime, x, y, xSpeed, ySpeed, xAcceleration, yAcceleration, rCol, gCol, bCol, alpha);
    }

    public RuneTileParticle(int lifetime, double x, double y, double xSpeed, double ySpeed, int rCol, int gCol, int bCol, int alpha) {
        super(lifetime, x, y, xSpeed, ySpeed, rCol, gCol, bCol, alpha);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SCRenderTypes.ParticleRenderTypes.RUNE_TILE_PARTICLE;
    }
}
