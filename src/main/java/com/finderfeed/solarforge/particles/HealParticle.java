package com.finderfeed.solarforge.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import javax.annotation.Nullable;

public class HealParticle extends SpriteTexturedParticle {
    protected HealParticle(ClientWorld p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
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
        this.lifetime = 60 + (int)Math.floor(p_i232448_1_.random.nextFloat()*50);
        this.quadSize = 0.2f;


    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifetime-- <=0){
            this.remove();
        }
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSetl;
        public Factory(IAnimatedSprite sprite){
            this.spriteSetl = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xv, double yv, double zv) {
            HealParticle particle = new HealParticle(world,x,y,z,xv,yv,zv);
            particle.setColor(1,1,1);
            particle.pickSprite(this.spriteSetl);
            return particle;
        }
    }
}
