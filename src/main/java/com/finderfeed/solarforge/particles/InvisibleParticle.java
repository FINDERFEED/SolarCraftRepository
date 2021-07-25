package com.finderfeed.solarforge.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nullable;

public class InvisibleParticle extends TextureSheetParticle {

    protected InvisibleParticle(ClientLevel p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
        super(p_i232448_1_, p_i232448_2_, p_i232448_4_, p_i232448_6_, x,y,z);

        this.rCol = 255;
        this.gCol = 255;
        this.bCol = 255;

        this.xd =x;
        this.yd =y;
        this.zd =z;
        this.x = p_i232448_2_;
        this.y = p_i232448_4_;
        this.z = p_i232448_6_;
        this.lifetime = 0;
        this.quadSize = 1;


    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.NO_RENDER;
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
            InvisibleParticle particle = new InvisibleParticle(world,x,y,z,xv,yv,zv);
            particle.setColor(1,1,1);
            particle.pickSprite(this.spriteSetl);
            return particle;
        }
    }
}
