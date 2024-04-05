package com.finderfeed.solarcraft.client.rendering.shaders.post_chains;

import net.minecraft.client.renderer.EffectInstance;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Map;

public class UniformPlusPlus {


    private final Map<String ,Object> uniforms;

    public UniformPlusPlus(Map<String,Object> uniforms){
        this.uniforms = uniforms;
    }


    public void addAll(EffectInstance shader){
        uniforms.forEach((name,uniform)->{
            if (uniform instanceof Float a) {
                shader.safeGetUniform(name).set(a);
            }else if (uniform instanceof Matrix4f a){
                shader.safeGetUniform(name).set(new Matrix4f(a));
            }else if (uniform instanceof Vector3f a){
                shader.safeGetUniform(name).set(a);
            }
        });
    }

}
