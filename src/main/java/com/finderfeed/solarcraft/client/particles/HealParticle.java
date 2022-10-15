package com.finderfeed.solarcraft.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nullable;

public class HealParticle extends TextureSheetParticle {
    protected HealParticle(ClientLevel level, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
        super(level, p_i232448_2_, p_i232448_4_, p_i232448_6_, x,y,z);

        this.rCol = 255;
        this.gCol = 255;
        this.bCol = 255;

        this.xd =x;
        this.yd =y;
        this.zd =z;
        this.x = p_i232448_2_;
        this.y = p_i232448_4_;
        this.z = p_i232448_6_;
        if (level != null) {
            this.lifetime = 60 + (int)Math.floor(level.random.nextFloat()*50);
        }else{
            this.lifetime = 60;
        }

        this.quadSize = 0.2f;


    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifetime-- <=0){
            this.remove();
        }
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSetl;
        public Factory(SpriteSet sprite){
            this.spriteSetl = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xv, double yv, double zv) {
            HealParticle particle = new HealParticle(world,x,y,z,xv,yv,zv);
            particle.setColor(1,1,1);
            particle.pickSprite(this.spriteSetl);
            return particle;
        }
    }
}
