package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import net.minecraft.world.phys.Vec3;

public class FDVertex {

    private float u;
    private float v;

    private Vec3 position;


    protected FDVertex(float u,float v,Vec3 position){
        this.u = u;
        this.v = v;
        this.position = position;
    }

    public float getU() {
        return u;
    }

    public float getV() {
        return v;
    }

    public Vec3 getPosition() {
        return position;
    }
}
