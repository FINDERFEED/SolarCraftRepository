package com.finderfeed.solarforge.shaders;

import org.lwjgl.opengl.GL20;

public enum Shaders {
    TEST(new SolarShader("testshader"));


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
