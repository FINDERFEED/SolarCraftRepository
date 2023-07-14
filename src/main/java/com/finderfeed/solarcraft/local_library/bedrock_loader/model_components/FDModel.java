package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import com.finderfeed.solarcraft.local_library.bedrock_loader.FDModelInfo;
import com.finderfeed.solarcraft.local_library.bedrock_loader.JsonHelper;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDCube;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelPart;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FDModel {


    public FDModelPart main;

    public FDModel(FDModelInfo info,float cubeScale){
        JsonElement jmodel = info.getJson();
        this.parseModel(jmodel.getAsJsonObject(),cubeScale);
    }


    public void render(PoseStack matrices, VertexConsumer vertex, int light, int overlay, float r, float g, float b, float a){
        main.render(matrices,vertex,light,overlay,r,g,b,a);
    }




    private void parseModel(JsonObject bmodel,float cubeScale){
        JsonObject jmodel = bmodel.getAsJsonArray("minecraft:geometry")
                .get(0).getAsJsonObject();
        JsonObject description = jmodel.getAsJsonObject("description");
        int texWidth = description.get("texture_width").getAsInt();
        int texHeight = description.get("texture_height").getAsInt();
        Map<String,FDModelPart> loadedParts = new HashMap<>();
        Map<String,FDModelPart> noChildrenParts = new HashMap<>();
        JsonArray parts = jmodel.getAsJsonArray("bones");
        for (JsonElement epart : parts){
            JsonObject part = epart.getAsJsonObject();
            String name = JsonHelper.getString(part,"name");
            String parent = JsonHelper.getString(part,"parent");
            Vec3 pivot = JsonHelper.parseVec3(part,"pivot").multiply(-1,1,1);
            Vec3 rotation = JsonHelper.parseVec3(part,"rotation").multiply(-1,-1,1);
            List<FDCube> cubes = this.parseCubes(part.getAsJsonArray("cubes"),texWidth,texHeight,cubeScale);
            FDModelPart parsed = new FDModelPart(cubes,pivot,
                    (float)rotation.x,
                    (float)rotation.y,
                    (float)rotation.z,
                    new HashMap<>()
            );
            if (parent.equals("")){
                loadedParts.put(name,parsed);
                noChildrenParts.put(name,parsed);
            }else{
                loadedParts.get(parent).children.put(name,parsed);
                loadedParts.put(name,parsed);
            }
        }
        FDModelPart main = new FDModelPart(new ArrayList<>(),Vec3.ZERO,0,0,0,noChildrenParts);
        this.main = main;
    }

    private List<FDCube> parseCubes(JsonArray array,int texWidth,int texHeight,float cubeScale){
        List<FDCube> cubes = new ArrayList<>();
        for (JsonElement element : array){
            cubes.add(FDCube.fromJson(element.getAsJsonObject(),texWidth,texHeight,cubeScale));
        }
        return cubes;
    }
}
