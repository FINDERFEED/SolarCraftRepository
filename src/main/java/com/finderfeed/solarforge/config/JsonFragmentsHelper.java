package com.finderfeed.solarforge.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class JsonFragmentsHelper {

    private static final Gson SERIALIZER = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final Path PATH_TO_FRAGMENTS_JSON = FMLPaths.CONFIGDIR.get()
            .resolve("solarcraft_custom_fragments");
    public static void setupJSON(){
        Path filePath = PATH_TO_FRAGMENTS_JSON.resolve("custom_fragments.json");
        if (!Files.exists(filePath)){
            try {
                Files.createDirectories(filePath.getParent());
                Writer writer = Files.newBufferedWriter(filePath,StandardOpenOption.CREATE);
                JsonObject object = new JsonObject();
                object.add("fragments",new JsonArray());
                SERIALIZER.toJson(object,writer);
                writer.flush();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }





}
