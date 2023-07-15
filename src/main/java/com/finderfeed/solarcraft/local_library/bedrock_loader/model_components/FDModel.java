package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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


//    private void parseModel(JsonObject bmodel,float cubeScale){
//        JsonObject jmodel = bmodel.getAsJsonArray("minecraft:geometry")
//                .get(0).getAsJsonObject();
//        JsonObject description = jmodel.getAsJsonObject("description");
//        int texWidth = description.get("texture_width").getAsInt();
//        int texHeight = description.get("texture_height").getAsInt();
//        Map<String,FDModelPart> loadedParts = new HashMap<>();
//        Map<String,FDModelPart> noChildrenParts = new HashMap<>();
//        JsonArray parts = jmodel.getAsJsonArray("bones");
//        for (JsonElement epart : parts){
//            JsonObject part = epart.getAsJsonObject();
//            String name = JsonHelper.getString(part,"name");
//            String parent = JsonHelper.getString(part,"parent");
//            Vec3 pivot = JsonHelper.parseVec3(part,"pivot").multiply(-1,1,1);
//            Vec3 rotation = JsonHelper.parseVec3(part,"rotation").multiply(-1,-1,1);
//            List<FDCube> cubes = this.parseCubes(part.getAsJsonArray("cubes"),texWidth,texHeight,cubeScale);
//            FDModelPart parsed = new FDModelPart(cubes,pivot,
//                    (float)rotation.x,
//                    (float)rotation.y,
//                    (float)rotation.z,
//                    new HashMap<>()
//            );
//            if (parent.equals("")){
//                loadedParts.put(name,parsed);
//                noChildrenParts.put(name,parsed);
//            }else{
//                loadedParts.get(parent).children.put(name,parsed);
//                loadedParts.put(name,parsed);
//            }
//        }
//        FDModelPart main = new FDModelPart(new ArrayList<>(),Vec3.ZERO,0,0,0,noChildrenParts);
//        this.main = main;
//    }
//
//    private List<FDCube> parseCubes(JsonArray array,int texWidth,int texHeight,float cubeScale){
//        List<FDCube> cubes = new ArrayList<>();
//        for (JsonElement element : array){
//            cubes.add(FDCube.fromJson(element.getAsJsonObject(),texWidth,texHeight,cubeScale));
//        }
//        return cubes;
//    }
}
