package com.finderfeed.solarcraft.local_library.bedrock_loader;

import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDCube;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelPart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class FDModelInfo {

    private static final Gson GSON = new GsonBuilder()
            .create();

    private JsonElement json;

    public FDModelInfo(ResourceLocation location){
        JsonElement jmodel = loadJsonModel(location);
        this.json = jmodel;
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

    public JsonElement getJson() {
        return json;
    }
}
