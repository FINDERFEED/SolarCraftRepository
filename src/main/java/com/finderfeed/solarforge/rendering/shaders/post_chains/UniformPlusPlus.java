package com.finderfeed.solarforge.rendering.shaders.post_chains;

import java.util.Map;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.EffectInstance;

public class UniformPlusPlus {


    private final Map<String ,Object> uniforms;

    public UniformPlusPlus(Map<String,Object> uniforms){
        this.uniforms = uniforms;
    }


    public void addAll(EffectInstance shader){
        for(var uniform : this.uniforms.entrySet())
        {
        	String name = uniform.getKey();
        	Object value = uniform.getValue();
            if (value instanceof Float a) {
                shader.safeGetUniform(name).set(a);
            }else if (value instanceof Matrix4f a){
                shader.safeGetUniform(name).set(a);
            }else if (value instanceof Vector3f a){
                shader.safeGetUniform(name).set(a);
            }
        };
    }

}
