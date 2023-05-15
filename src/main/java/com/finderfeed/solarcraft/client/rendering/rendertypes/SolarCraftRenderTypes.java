package com.finderfeed.solarcraft.client.rendering.rendertypes;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.renderers.OrbitalExplosionEntityRenderer;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;
import org.lwjgl.opengl.GL11;


public class SolarCraftRenderTypes extends RenderType{



    public SolarCraftRenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    @Deprecated
    public static RenderType cutoutTranslucent(){
        return RenderType.create("cutout_translucent", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS,
                131072, true, false, RenderType.CompositeState.builder()
                .setLightmapState(RenderType.LIGHTMAP)
                .setShaderState(RenderType.RENDERTYPE_CUTOUT_SHADER)
                .setTextureState(RenderType.BLOCK_SHEET)
                .setTransparencyState(TRANSLUCENT)

                .createCompositeState(true));
    }
    protected static final RenderStateShard.TransparencyStateShard TRANSLUCENT =
            new RenderStateShard.TransparencyStateShard("translucent", RenderSystem::enableBlend, RenderSystem::disableBlend);


    public static RenderType shaderRendertype(ShaderStateShard shaderStateShard){
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(shaderStateShard)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setWriteMaskState(COLOR_DEPTH_WRITE)
                .setTransparencyState(LIGHTNING_TRANSPARENCY)
                .setOutputState(WEATHER_TARGET)
                .createCompositeState(false);
        return RenderType.create("shaderRendertype", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, true,state);
    }


    public static RenderType shaderRendertype2(ShaderStateShard shaderStateShard){
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(shaderStateShard)
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderStateShard.CULL)
                .setOutputState(RenderStateShard.ITEM_ENTITY_TARGET)
                .setLayeringState(RenderType.VIEW_OFFSET_Z_LAYERING)
                .setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
                .createCompositeState(false);
        return RenderType.create("shaderRendertypetest", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, 256, true, true,state);
    }

    public static RenderType depthMaskedTextSeeThrough(ResourceLocation loc){
        return create("text_see_through", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true,
                RenderType.CompositeState.builder().setShaderState(RENDERTYPE_TEXT_SEE_THROUGH_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(loc, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(false));
    }

    public static RenderType LIGHTING_NO_CULL =
            create("lightning", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS,
                    256, false, true,
                    RenderType.CompositeState.builder()
                            .setCullState(RenderStateShard.NO_CULL)
                            .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                            .setWriteMaskState(COLOR_DEPTH_WRITE)
                            .setTransparencyState(LIGHTNING_TRANSPARENCY)
            .setOutputState(WEATHER_TARGET).createCompositeState(false));


    public static RenderTarget orbitalExplosionOutTarget;
    public static final OutputStateShard ORBITAL_EXPLOSION_TARGET = new OutputStateShard("orbital_explosion_target", ()->{
        orbitalExplosionOutTarget.bindWrite(false);
    }, ()->{
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    });

    public static RenderType ORBITAL_EXPLOSION_RENDER_TYPE =
      create("entity_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true,
              RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                      .setTextureState(new RenderStateShard.TextureStateShard(OrbitalExplosionEntityRenderer.LOCATION
                              , false, false))
                      .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                      .setCullState(NO_CULL)
                      .setLightmapState(LIGHTMAP)
                      .setOutputState(ORBITAL_EXPLOSION_TARGET)
                      .setOverlayState(OVERLAY)
                      .createCompositeState(true));



    public static class ParticleRenderTypes {
        public static final ParticleRenderType SOLAR_STRIKE_PARTICLE_RENDER = new ParticleRenderType() {
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

        public static final ParticleRenderType RUNE_TILE_PARTICLE = new ParticleRenderType() {
            ResourceLocation loc = new ResourceLocation(SolarCraft.MOD_ID,"textures/particle/rune_tile_particle.png");

            @Override
            public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.depthMask(false);
                ClientHelpers.bindText(loc);
                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
            }

            @Override
            public void end(Tesselator tessellator) {
                tessellator.end();

                RenderSystem.depthMask(true);
            }
            @Override
            public String toString() {
                return "solarcraft:rune_tile_particle";
            }
        };

        public static final ParticleRenderType SOLAR_STRIKE_PARTICLE_SCREEN = new ParticleRenderType() {
            ResourceLocation loc = new ResourceLocation(SolarCraft.MOD_ID,"textures/particle/solar_strike_particle.png");
            @Override
            public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
                RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
                ClientHelpers.bindText(loc);
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

                //textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, false);
                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
            }

            @Override
            public void end(Tesselator tessellator) {
                tessellator.end();
                //Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
            }
            @Override
            public String toString() {
                return "solarcraft:solar_strike_particle_screen";
            }
        };

    }
}
