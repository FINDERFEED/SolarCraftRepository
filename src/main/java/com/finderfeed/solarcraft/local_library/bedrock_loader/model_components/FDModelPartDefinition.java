package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FDModelPartDefinition {

    public List<FDCube> cubes;
    public String parent;
    public Vec3 initRotation;
    public Vec3 pivot;
    public String name;

    public FDModelPartDefinition(List<FDCube> cubes,String name,String parent,Vec3 initRotation,Vec3 pivot){
        this.cubes = cubes;
        this.name = name;
        this.parent = parent;
        this.initRotation = initRotation;
        this.pivot = pivot;
    }

}
