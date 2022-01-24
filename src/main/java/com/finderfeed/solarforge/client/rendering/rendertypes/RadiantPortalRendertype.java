package com.finderfeed.solarforge.client.rendering.rendertypes;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

public class RadiantPortalRendertype extends RenderType {

    //waves
    public static ShaderInstance WATER_SHADER;
    public static final ShaderStateShard RENDERTYPE_WATER_SHADER = new ShaderStateShard(() -> WATER_SHADER);

    //ray
    public static ShaderInstance RAY_SHADER;
    public static final ShaderStateShard RENDERTYPE_RAY_SHADER = new ShaderStateShard(() -> RAY_SHADER);


    public RadiantPortalRendertype(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable one, Runnable two) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, one, two);
    }




    public static RenderType textWithWaterShader(ResourceLocation loc){
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_WATER_SHADER)
                .setTextureState(new TextureStateShard(loc, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(false);
        return create("textWithWaterShader", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true,state);
    }

    public static RenderType textWithRayShader(ResourceLocation loc){
        RenderType.CompositeState state = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_RAY_SHADER)
                .setTextureState(new TextureStateShard(loc, false, false))
                .setTransparencyState(DEPTH_MASKED_TRANSLUCENT_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(false);
        return create("textWithWaterShader", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true,state);
    }
    //create("text", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, Mode.QUADS, 256, false, true, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_TEXT_SHADER)
    // .setTextureState(new TextureStateShard(p_173251_, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).createCompositeState(false))
    protected static final RenderStateShard.TransparencyStateShard DEPTH_MASKED_TRANSLUCENT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("depth_masked_translucent_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.defaultBlendFunc();
    });
}
