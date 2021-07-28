package com.finderfeed.solarforge.rendering.shaders.post_chains;

import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.ShaderInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                shader.safeGetUniform(name).set(a);
            }else if (uniform instanceof Vector3f a){
                shader.safeGetUniform(name).set(a);
            }else if (uniform instanceof Integer a){
                shader.safeGetUniform(name).set(a);
            }
        });
    }

}
