package com.finderfeed.solarforge.shaders;

public enum Shaders {
    TEST(new SolarShader("testshader"));


    private SolarShader shader;

    Shaders(SolarShader shader){
        this.shader = shader;
    }

    public SolarShader getShader() {
        return shader;
    }
}
