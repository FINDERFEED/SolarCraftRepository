package com.finderfeed.solarcraft.misc_things;

import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;

public class PostShader {

    private UniformPlusPlus uniformPlusPlus;
    private PostChainPlusUltra postChainPlusUltra;

    public PostShader(UniformPlusPlus uniforms, PostChainPlusUltra postChain){
        this.uniformPlusPlus = uniforms;
        this.postChainPlusUltra = postChain;
    }

    public void process(float t){
        this.postChainPlusUltra.updateUniforms(uniformPlusPlus);
        this.postChainPlusUltra.process(t);
    }
}
