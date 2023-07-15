package com.finderfeed.solarcraft.local_library.bedrock_loader.model_components;

import com.finderfeed.solarcraft.local_library.bedrock_loader.JsonHelper;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDCube;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelPart;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelPartDefinition;
import com.google.gson.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.*;

public class FDModelInfo {

    private static final Gson GSON = new GsonBuilder()
            .create();

    private ResourceLocation modelName;
    public List<FDModelPartDefinition> partDefinitionList = new ArrayList<>();

    public FDModelInfo(ResourceLocation location,float cubeScale){
        this.modelName = location;
        JsonElement jmodel = loadJsonModel(location);
        this.parseModel(jmodel.getAsJsonObject(),cubeScale);
    }


    public ResourceLocation getModelName() {
        return modelName;
    }

    private void parseModel(JsonObject bmodel, float cubeScale){
        JsonObject jmodel = bmodel.getAsJsonArray("minecraft:geometry")
                .get(0).getAsJsonObject();
        JsonObject description = jmodel.getAsJsonObject("description");
        int texWidth = description.get("texture_width").getAsInt();
        int texHeight = description.get("texture_height").getAsInt();
        JsonArray parts = jmodel.getAsJsonArray("bones");
        for (JsonElement epart : parts){
            JsonObject part = epart.getAsJsonObject();
            String name = JsonHelper.getString(part,"name");
            String parent = JsonHelper.getString(part,"parent");
            Vec3 pivot = JsonHelper.parseVec3(part,"pivot").multiply(-1,1,1);
            Vec3 rotation = JsonHelper.parseVec3(part,"rotation").multiply(-1,-1,1);
            List<FDCube> cubes;
            if (part.has("cubes")){
                cubes = this.parseCubes(part.getAsJsonArray("cubes"),texWidth,texHeight,cubeScale);
            }else{
                cubes = new ArrayList<>();
            }
            FDModelPartDefinition definition = new FDModelPartDefinition(cubes,name,parent,rotation,pivot);
            this.partDefinitionList.add(definition);
        }
    }

    private List<FDCube> parseCubes(JsonArray array,int texWidth,int texHeight,float cubeScale){
        List<FDCube> cubes = new ArrayList<>();
        for (JsonElement element : array){
            cubes.add(FDCube.fromJson(element.getAsJsonObject(),texWidth,texHeight,cubeScale));
        }
        return cubes;
    }


    private JsonElement loadJsonModel(ResourceLocation location){
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        Optional<Resource> modelJson = manager.getResource(location);
        try{
            if (modelJson.isPresent()){

                BufferedReader stream = modelJson.get().openAsReader();
                JsonElement element = GSON.fromJson(stream,JsonElement.class);
                stream.close();
                return element;
            }else{
                throw new RuntimeException("Couldn't find model: " + location);
            }
        }catch (Exception e){
            throw new RuntimeException("Error loading model file: " + location,e);
        }
    }
}
