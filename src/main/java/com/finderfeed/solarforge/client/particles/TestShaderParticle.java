package com.finderfeed.solarforge.client.particles;

import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class TestShaderParticle extends ShaderParticle{
    public TestShaderParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd, 2f, 100, SolarCraftRenderTypes.TEST_STATE_SHARD);
    }



    public static class Factory implements ParticleProvider<SimpleParticleType>{

        public Factory(SpriteSet spriteSet){

        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new TestShaderParticle(level,x,y,z,xd,yd,zd);
        }
    }

}

