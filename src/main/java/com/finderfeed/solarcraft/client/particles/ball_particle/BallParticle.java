package com.finderfeed.solarcraft.client.particles.ball_particle;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class BallParticle extends TextureSheetParticle {

    private BallParticleOptions options;


    protected BallParticle(BallParticleOptions options,ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.options = options;
        this.rCol = options.getR() / 255f;
        this.gCol = options.getG() / 255f;
        this.bCol = options.getB() / 255f;
        this.lifetime = options.getLifetime();
        this.quadSize = options.getSize();
    }

    @Override
    public void tick() {
        super.tick();
        if (options.isShouldShrink()){
            this.quadSize = Mth.clamp(
                    (this.lifetime - this.age) / (float)lifetime,
                    0,
                    1
            ) * options.getSize();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SolarCraftRenderTypes.ParticleRenderTypes.SOLAR_STRIKE_PARTICLE_RENDER_TYPE;
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return LightTexture.FULL_BRIGHT;
    }

    public static class Provider implements ParticleProvider<BallParticleOptions>{

        private SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(BallParticleOptions data, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            BallParticle particle = new BallParticle(data,level,x,y,z,xd,yd,zd);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
