package com.finderfeed.solarcraft.client.rendering.rendertypes;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.GlowShaderProcessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;


public class SCRenderTypes extends RenderType {



    public SCRenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static Function<ResourceLocation,RenderType> TEX_TRIANGLES = Util.memoize(SCRenderTypes::texTriangles);

    public static RenderType texTriangles(ResourceLocation location){
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_TEXT_SHADER)
                .setTextureState(new TextureStateShard(location,true,false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(false);
        return create("tex_triangles", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, rendertype$state);
    }

    public static final RenderType LIGHTNING_TRIANGLES = create(
            "lightning_triangles",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.TRIANGLES,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setOutputState(WEATHER_TARGET)
                    .createCompositeState(false)
    );

    public static TransparencyStateShard NORMAL_TRANSPARENCY = new TransparencyStateShard("normal_transparency",()->{

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
        },()->{

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static RenderType LIGHTNING_NO_CULL = create(
            "my_lightning",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setCullState(RenderStateShard.NO_CULL)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .createCompositeState(false)
    );
    public static Function<ResourceLocation, RenderType> TEXT_BLOOM = Util.memoize(SCRenderTypes::getBloomText);

    private static RenderType getBloomText(ResourceLocation texture) {
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOutputState(GlowShaderProcessor.getBloomShard())
                .createCompositeState(false);
        return create("bloom_text"
                , DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, rendertype$state);
    }

    public static Function<ResourceLocation, RenderType> ORBITAL_EXPLOSION_BLOOM = Util.memoize(SCRenderTypes::getOrbitalExplosionRenderType);
    private static RenderType getOrbitalExplosionRenderType(ResourceLocation texture) {
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_TRANSLUCENT_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(texture, true, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOutputState(GlowShaderProcessor.getOrbitalExplosionShard())
                .createCompositeState(false);
        return create("orbital_explosion_text"
                , DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, rendertype$state);
    }

    public static final RenderType LIGHTNING_PARTICLES = create("lightning_particle",
            DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .setOutputState(RenderStateShard.PARTICLES_TARGET)
                    .createCompositeState(false));

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
                        .setOutputState(OutputStateShard.WEATHER_TARGET)
                .setLightmapState(LIGHTMAP)
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


    private static final Function<ResourceLocation, RenderType> EYES_POSITION_COLOR_TEX_LIGHTMAP = Util.memoize((p_286170_) -> {
        RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_286170_, false, false);
        return create("eyes_position_tex_lightmap_no_normal", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(renderstateshard$texturestateshard).setTransparencyState(ADDITIVE_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).createCompositeState(false));
    });

    public static RenderType eyesPositionColorTexLightmapNoNormal(ResourceLocation tex){
        return EYES_POSITION_COLOR_TEX_LIGHTMAP.apply(tex);
    }




    public static RenderTarget orbitalExplosionOutTarget;
    public static RenderTarget orbitalExplosionDepthTarget;
    public static final OutputStateShard ORBITAL_EXPLOSION_TARGET = new OutputStateShard("orbital_explosion_target", ()->{
        orbitalExplosionOutTarget.bindWrite(false);
    }, ()->{
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    });

    public static RenderType ORBITAL_EXPLOSION_RENDER_TYPE =
      create("orbital_explosion", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true,
              RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
                      .setTextureState(new RenderStateShard.TextureStateShard(TextureAtlas.LOCATION_BLOCKS
                              , false, false))
                      .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                      .setCullState(NO_CULL)
                      .setLightmapState(LIGHTMAP)
                      .setOutputState(ORBITAL_EXPLOSION_TARGET)
                      .setOverlayState(OVERLAY)
                      .createCompositeState(true));



    public static class ParticleRenderTypes {

        public static final ParticleRenderType ADDITIVE_TRANSLUCENT = new ParticleRenderType() {

            @Override
            public void begin(BufferBuilder builder, TextureManager mmanager) {
                if (Minecraft.useShaderTransparency()){
                    Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
                }
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
                ClientHelpers.bindText(TextureAtlas.LOCATION_PARTICLES);
                builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.PARTICLE);
            }

            @Override
            public void end(Tesselator tesselator) {
                tesselator.end();
                if (Minecraft.useShaderTransparency()){
                    Minecraft.getInstance().levelRenderer.getParticlesTarget().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                    Minecraft.getInstance().levelRenderer.getParticlesTarget().bindWrite(false);
                }
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
            }

            @Override
            public String toString() {
                return "solarcraft:additive";
            }
        };

        public static final ParticleRenderType NORMAL_TRANSLUCENT = new ParticleRenderType() {
            @Override
            public void begin(BufferBuilder builder, TextureManager mmanager) {
                if (Minecraft.useShaderTransparency()){
                    Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
                }
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
                ClientHelpers.bindText(TextureAtlas.LOCATION_PARTICLES);
                builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.PARTICLE);
            }

            @Override
            public void end(Tesselator tesselator) {
                tesselator.end();
                if (Minecraft.useShaderTransparency()){
                    Minecraft.getInstance().levelRenderer.getParticlesTarget().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                    Minecraft.getInstance().levelRenderer.getParticlesTarget().bindWrite(false);
                }
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
            }

            @Override
            public String toString() {
                return "solarcraft:normal";
            }
        };

        public static final ParticleRenderType SOLAR_STRIKE_PARTICLE_RENDER_TYPE = new ParticleRenderType() {
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
