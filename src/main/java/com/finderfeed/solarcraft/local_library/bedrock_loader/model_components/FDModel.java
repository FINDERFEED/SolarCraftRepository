package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FDModel {


    private ResourceLocation modelName;

    public FDModelPart main;
    private Map<String,FDModelPart> partsLookup;

    public FDModel(FDModelInfo info){
        this.modelName = info.getModelName();
        this.load(info.partDefinitionList);
    }

    public void render(PoseStack matrices, VertexConsumer vertex, int light, int overlay, float r, float g, float b, float a){
        main.render(matrices,vertex,light,overlay,r,g,b,a);
    }

//    public void renderDebugNormals(PoseStack matrices, MultiBufferSource src, int light, int overlay, float r, float g, float b, float a){
//        main.renderDebugNormals(matrices,src.getBuffer(RenderType.debugLineStrip(1f)),light,overlay,r,g,b,a);
//    }



    private void load(List<FDModelPartDefinition> definitions){
        Map<String,FDModelPart> allParts = new HashMap<>();
        Map<String,String> childParent = new HashMap<>();
        List<FDModelPart> noParentsParts = new ArrayList<>();
        for (FDModelPartDefinition definition : definitions){
            List<FDCube> cubes = definition.cubes;
            String name = definition.name;
            String parent = definition.parent;
            Vec3 rotation = definition.initRotation;
            Vec3 pivot = definition.pivot;
            FDModelPart part = new FDModelPart(name,cubes,pivot,rotation);
            allParts.put(name,part);
            if (!parent.equals("")){
                childParent.put(name,parent);
            }else{
                noParentsParts.add(part);
            }
        }
        for (var entry : childParent.entrySet()){
            String sname = entry.getKey();
            String sparent = entry.getValue();
            FDModelPart parent = allParts.get(sparent);
            FDModelPart child = allParts.get(sname);
            parent.children.put(child.name,child);
        }
        this.main = new FDModelPart("root",new ArrayList<>(),Vec3.ZERO,Vec3.ZERO);
        noParentsParts.forEach(part->main.children.put(part.name,part));
        this.partsLookup = allParts;
    }



    public FDModelPart getModelPart(String name){
        FDModelPart part;
        if ((part = partsLookup.get(name)) != null) {
            return part;
        }else{
            throw new RuntimeException("Couldn't find part: \"" + name + "\" in model: " + modelName);
        }
    }

    public FDModelPart getPartOrNull(String name){
        return this.partsLookup.get(name);
    }

    public void resetTransformations(){
        this.main.reset();
    }
}
