package com.finderfeed.solarforge.shaders.render_types;

import com.finderfeed.solarforge.shaders.SolarShader;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;



public class ShaderRenderType extends RenderType {
    public ShaderRenderType(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_,
                            SolarShader shader,
                            RenderType.State state) {

        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_,()->{
            shader.process();
            ImmutableList<RenderState> states = ObfuscationReflectionHelper.getPrivateValue(RenderType.State.class,state,"field_228693_q_");
            states.forEach(RenderState::setupRenderState);

        }, ()->{
            shader.disable();
            ImmutableList<RenderState> states = ObfuscationReflectionHelper.getPrivateValue(RenderType.State.class,state,"field_228693_q_");
            states.forEach(RenderState::clearRenderState);
        });

    }

    public static RenderType textWithShader(ResourceLocation p_228658_0_,SolarShader shader) {

        return new ShaderRenderType("textwithshader", DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, false, true, shader,
                RenderType.State.builder()
                        .setTextureState(new RenderState.TextureState(p_228658_0_, false, false))
                        .setAlphaState(DEFAULT_ALPHA).setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .createCompositeState(false));
    }

}
