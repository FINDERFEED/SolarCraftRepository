package com.finderfeed.solarforge.rendering.shaders;

import org.lwjgl.opengl.GL20;

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
