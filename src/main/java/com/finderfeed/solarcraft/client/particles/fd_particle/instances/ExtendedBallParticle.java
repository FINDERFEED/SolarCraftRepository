package com.finderfeed.solarcraft.client.particles.fd_particle.instances;

import com.finderfeed.solarcraft.client.particles.fd_particle.FDTSParticle;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDTSParticleOptions;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.Nullable;

public class ExtendedBallParticle extends FDTSParticle {
    public ExtendedBallParticle(ExtendedBallParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(options, level, x, y, z, xd, yd, zd);
    }


    @Override
    public ParticleRenderType getRenderType() {
        return SCRenderTypes.ParticleRenderTypes.SOLAR_STRIKE_PARTICLE_RENDER_TYPE;
    }


    public static class Provider implements ParticleProvider<ExtendedBallParticleOptions>{

        public SpriteSet set;
        public Provider(SpriteSet spriteSet){
            this.set = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(ExtendedBallParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            ExtendedBallParticle particle = new ExtendedBallParticle(options,level,x,y,z,xd,yd,zd);
            particle.pickSprite(set);
            return particle;
        }
    }

}
