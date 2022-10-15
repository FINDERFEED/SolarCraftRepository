package com.finderfeed.solarcraft.client.particles;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public abstract class SolarcraftParticle extends TextureSheetParticle {

    protected float maxSize;

    protected SolarcraftParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_,float maxSize) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
        this.maxSize = maxSize;
        this.quadSize = maxSize;

    }

    public void setQuadSize(float size){
        this.quadSize = size;
    }

    public void setMaxSize(float maxSize) {
        this.maxSize = maxSize;
        this.quadSize = maxSize;
    }

    public float getMaxSize() {
        return maxSize;
    }

    private final ParticleRenderType TRANSLUCENT_PARTICLE = new ParticleRenderType() {
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
