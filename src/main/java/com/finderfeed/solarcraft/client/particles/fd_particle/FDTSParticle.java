package com.finderfeed.solarcraft.client.particles.fd_particle;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

public class FDTSParticle extends TextureSheetParticle {
    protected FDTSParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
