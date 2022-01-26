package com.finderfeed.solarforge.client.rendering.deprecated_shaders;

import org.lwjgl.opengl.GL20;

@Deprecated
public enum Shaders {
    WAVE(new SolarShader("wave"));


    private SolarShader shader;

    Shaders(SolarShader shader){
        this.shader = shader;
    }

    public SolarShader getShader() {
        return shader;
    }



    public static void close(){
        GL20.glUseProgram(0);
    }
}
