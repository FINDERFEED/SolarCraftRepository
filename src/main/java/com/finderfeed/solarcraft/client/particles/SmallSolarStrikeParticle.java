package com.finderfeed.solarcraft.client.particles;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class SmallSolarStrikeParticle extends SolarcraftParticle {
    public SmallSolarStrikeParticle(ClientLevel level, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
        super(level, p_i232448_2_, p_i232448_4_, p_i232448_6_, x,y,z,0.45f);

        this.rCol = 255;
        this.gCol = 255;
        this.bCol = 40;

        this.xd =x;
        this.yd =y;
        this.zd =z;
        this.x = p_i232448_2_;
        this.y = p_i232448_4_;
        this.z = p_i232448_6_;
        this.lifetime = 60;

    }

    @Override
    public int getLightColor(float p_172146_) {
//        float f = 2;
//        f = Mth.clamp(f, 0.0F, 1.0F);
//        int i = super.getLightColor(p_172146_);
//        int j = i & 255;
//        int k = i >> 16 & 255;
//        j = j + (int)(f * 15.0F * 16.0F);
//        if (j > 240) {
//            j = 240;
//        }

        return 15728880;
    }

    @Override
    public ParticleRenderType getRenderType() {
        //setAlphaState(DEFAULT_ALPHA).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setDepthTestState(NO_DEPTH_TEST).setWriteMaskState(COLOR_WRITE)

        return SCRenderTypes.ParticleRenderTypes.SOLAR_STRIKE_PARTICLE_RENDER_TYPE;
    }




    @Override
    public void tick() {
        super.tick();
        if (quadSize >= 0) {
            this.quadSize -= this.maxSize / this.lifetime;
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
            SmallSolarStrikeParticle particle = new SmallSolarStrikeParticle(world,x,y,z,xv,yv,zv);
            particle.setColor(1,1,0);

            particle.pickSprite(this.spriteSetl);
            return particle;
        }
    }
    private static final ParticleRenderType SOLAR_STRIKE_PARTICLE_RENDER = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);



            ClientHelpers.bindText(TextureAtlas.LOCATION_PARTICLES);
            textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, false);
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();

            Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();


            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);


        }

        @Override
        public String toString() {
            return "solarcraft:solar_strike_particle";
        }
    };
}
