package com.finderfeed.solarforge.rendering.shaders.post_chains;


import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.rendering.shaders.Shaders;
import com.finderfeed.solarforge.rendering.shaders.Uniform;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.Objects;
import java.util.function.IntSupplier;

public class PostChainPlusUltra extends PostChain {


    private UniformPlusPlus uniforms;

    public PostChainPlusUltra(ResourceLocation loc,UniformPlusPlus uniform) throws IOException, JsonSyntaxException {
        super(Minecraft.getInstance().textureManager,
                Minecraft.getInstance().getResourceManager(),
                Minecraft.getInstance().getMainRenderTarget(),loc);
        this.uniforms = uniform;
    }


    public void updateUniforms(UniformPlusPlus uniforms){
        this.uniforms = uniforms;
    }

    @Override
    public void process(float p_110024_) {
        if (p_110024_ < this.lastStamp) {
            this.time += 1.0F - this.lastStamp;
            this.time += p_110024_;
        } else {
            this.time += p_110024_ - this.lastStamp;
        }
        for(this.lastStamp = p_110024_; this.time > 20.0F; this.time -= 20.0F) {
        }
        for(PostPass postpass : this.passes) {

            if(uniforms != null){
                uniforms.addAll(postpass.getEffect());
            }
            postpass.process(this.time / 20.0F);
        }
    }
}
