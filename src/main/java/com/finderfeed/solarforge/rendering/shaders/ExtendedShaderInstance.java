package com.finderfeed.solarforge.rendering.shaders;

import com.finderfeed.solarforge.rendering.shaders.post_chains.UniformPlusPlus;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExtendedShaderInstance extends ShaderInstance {

    private Map<String,Object> uniformsExtra = new HashMap<>();

    public ExtendedShaderInstance(ResourceProvider p_173336_, ResourceLocation shaderLocation, VertexFormat p_173338_) throws IOException {
        super(p_173336_, shaderLocation, p_173338_);

    }
    @Override
    public void apply() {
        super.apply();
        this.uniformsExtra.forEach((name,value)->{
            if (value instanceof Float a) {
                this.safeGetUniform(name).set(a);
            }else if (value instanceof Matrix4f a){
                this.safeGetUniform(name).set(a);
            }else if (value instanceof Vector3f a){
                this.safeGetUniform(name).set(a);
            }
        });
    }
    @Override
    public void close() {
        super.close();
        this.uniformsExtra.clear();
    }
    public void addOrModifyUniform(String name, Object value){
        uniformsExtra.put(name,value);
    }


}
