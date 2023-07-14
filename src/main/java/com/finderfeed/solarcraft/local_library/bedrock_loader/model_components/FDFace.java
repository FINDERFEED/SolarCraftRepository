package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FDFace {

    private FDVertex[] vertices = new FDVertex[4];
    private Vec3 normal;


    protected FDFace(Vec3 normal,FDVertex... vertex){
        this.normal = normal;
        vertices[0] = vertex[0];
        vertices[1] = vertex[1];
        vertices[2] = vertex[2];
        vertices[3] = vertex[3];
    }


    public FDVertex[] getVertices() {
        return vertices;
    }

    public Vec3 getNormal() {
        return normal;
    }
}
