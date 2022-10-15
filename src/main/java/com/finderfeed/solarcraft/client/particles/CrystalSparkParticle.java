package com.finderfeed.solarcraft.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class CrystalSparkParticle extends SolarcraftParticle{
    private SpriteSet spriteSet;
    public CrystalSparkParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double xd, double yd, double zd,SpriteSet spriteSet) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, xd, yd, zd, 0.5f);

        this.xd = xd;
        this.yd = yd;
        this.zd = zd;


        this.lifetime = 4;
        this.spriteSet = spriteSet;
        this.setSpriteFromAge(spriteSet);

    }


    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }




    public static class Provider implements ParticleProvider<SimpleParticleType>{

        private SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }


        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType p_107421_, ClientLevel level, double posx, double posy, double posz, double vx, double vy, double vz) {
            return new CrystalSparkParticle(level,posx,posy,posz,vx,vy,vz,spriteSet);
        }
    }
}
