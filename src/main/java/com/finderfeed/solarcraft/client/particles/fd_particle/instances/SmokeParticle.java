package com.finderfeed.solarcraft.client.particles.fd_particle.instances;

import com.finderfeed.solarcraft.client.particles.fd_particle.FDDefaultOptions;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDTSParticle;
import com.finderfeed.solarcraft.client.particles.fd_particle.FDTSParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.Nullable;

public class SmokeParticle extends FDTSParticle {

    public SmokeParticle(FDTSParticleOptions options,ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(options,level, x, y, z, xd, yd, zd);
    }


    @Override
    public void tick() {
        super.tick();
    }



    public static class Provider implements ParticleProvider<FDTSParticleOptions> {

        public SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(FDTSParticleOptions options, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            SmokeParticle particle = new SmokeParticle(options,level,x,y,z,xd,yd,zd);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
