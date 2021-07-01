package com.finderfeed.solarforge.misc_things;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;


import javax.annotation.Nullable;

import static org.apache.commons.lang3.RandomUtils.nextFloat;


public class SolarStrikeParticle extends SpriteTexturedParticle {


    public SolarStrikeParticle(ClientWorld p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
        super(p_i232448_1_, p_i232448_2_, p_i232448_4_, p_i232448_6_, x,y,z);

        this.rCol = 255;
        this.gCol = 255;
        this.bCol = 40;

        this.xd =x;
        this.yd =y;
        this.zd =z;
        this.x = p_i232448_2_;
        this.y = p_i232448_4_;
        this.z = p_i232448_6_;
        this.lifetime = 60 + (int)(p_i232448_1_.random.nextFloat()*6);
        this.quadSize = 2f;


    }



    @Override
    public IParticleRenderType getRenderType() {
        //setAlphaState(DEFAULT_ALPHA).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setDepthTestState(NO_DEPTH_TEST).setWriteMaskState(COLOR_WRITE)

        return SOLAR_STRIKE_PARTICLE_RENDER ;
    }




    @Override
    public void tick() {
        super.tick();
        if (this.lifetime-- <=0){
            this.remove();
        }
    }

    public static class Factory implements IParticleFactory<BasicParticleType>{
        private final IAnimatedSprite spriteSetl;
                public Factory(IAnimatedSprite sprite){
            this.spriteSetl = sprite;
                }
        @Nullable
        @Override
        public Particle createParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xv, double yv, double zv) {
            SolarStrikeParticle particle = new SolarStrikeParticle(world,x,y,z,xv,yv,zv);
            particle.setColor(1,1,0);

            particle.pickSprite(this.spriteSetl);
            return particle;
        }
    }
    private static final IParticleRenderType SOLAR_STRIKE_PARTICLE_RENDER = new IParticleRenderType() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            RenderSystem.alphaFunc(GL11.GL_GREATER, 0.003921569F);
            RenderSystem.disableLighting();




            textureManager.bind(AtlasTexture.LOCATION_PARTICLES);
            textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES).setBlurMipmap(true, false);
            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE);
        }

        @Override
        public void end(Tessellator tessellator) {
            tessellator.end();

            Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES).restoreLastBlurMipmap();
            RenderSystem.alphaFunc(GL11.GL_GREATER, 0.1F);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);


        }

        @Override
        public String toString() {
            return "solarforge:solar_strike_particle";
        }
    };
}
