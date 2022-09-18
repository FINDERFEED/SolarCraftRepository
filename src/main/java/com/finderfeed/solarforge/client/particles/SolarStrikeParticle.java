package com.finderfeed.solarforge.client.particles;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

import org.lwjgl.opengl.GL11;


import javax.annotation.Nullable;

import static org.apache.commons.lang3.RandomUtils.nextFloat;

public class SolarStrikeParticle extends TextureSheetParticle {


    public SolarStrikeParticle(ClientLevel p_i232448_1_, double p_i232448_2_, double p_i232448_4_, double p_i232448_6_, double x, double y, double z) {
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
        this.quadSize = 0.5f;


    }



    @Override
    public ParticleRenderType getRenderType() {


        return SolarCraftRenderTypes.ParticleRenderTypes.SOLAR_STRIKE_PARTICLE_RENDER ;
    }



    @Override
    public void tick() {
        super.tick();
        if (this.age <= 5){
            this.scale(this.level.random.nextFloat()+1);
        }else{
            this.scale(0.95f);
        }

    }



    public static class Factory implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSetl;
                public Factory(SpriteSet sprite){
            this.spriteSetl = sprite;
                }
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xv, double yv, double zv) {
            SolarStrikeParticle particle = new SolarStrikeParticle(world,x,y,z,xv,yv,zv);
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
//            GL11.glAlphaFunc(GL11.GL_GREATER,0.003921569F);

//            GL11.glDisable(GL11.GL_LIGHTING);
            ClientHelpers.bindText(TextureAtlas.LOCATION_PARTICLES);
            textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, false);
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);

        }

        @Override
        public void end(Tesselator tessellator) {
            tessellator.end();

            Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
//            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);


        }

        @Override
        public String toString() {
            return "solarforge:solar_strike_particle";
        }
    };
}
