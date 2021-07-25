package com.finderfeed.solarforge.rendering.shaders.render_types;

import com.finderfeed.solarforge.rendering.shaders.SolarShader;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;


public class ShaderRenderType extends RenderType {
    public ShaderRenderType(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_,
                            SolarShader shader,
                            RenderType.CompositeState state) {

        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_,()->{
            shader.process();

            ImmutableList<RenderStateShard> states = ObfuscationReflectionHelper.getPrivateValue(RenderType.CompositeState.class,state,"field_228693_q_");
            states.forEach(RenderStateShard::setupRenderState);



        }, ()->{
            shader.disable();
            ImmutableList<RenderStateShard> states = ObfuscationReflectionHelper.getPrivateValue(RenderType.CompositeState.class,state,"field_228693_q_");
            states.forEach(RenderStateShard::clearRenderState);
        });

    }

    public static RenderType textWithShader(ResourceLocation p_228658_0_,SolarShader shader) {

        return new ShaderRenderType("textwithshader", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, 7, 256, false, true, shader,
                RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(p_228658_0_, false, false))
                        .setAlphaState(DEFAULT_ALPHA).setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .createCompositeState(false));
    }

}
