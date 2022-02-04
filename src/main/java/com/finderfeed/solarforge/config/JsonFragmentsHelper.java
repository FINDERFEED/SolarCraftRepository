package com.finderfeed.solarforge.config;

import com.google.gson.*;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


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

    @Nullable
    public static List<JsonObject> readFragments(){
        Path filePath = PATH_TO_FRAGMENTS_JSON.resolve("custom_fragments.json");
        if (Files.exists(filePath)){
            try {
                Reader reader = Files.newBufferedReader(filePath);
                JsonObject object = SERIALIZER.fromJson(reader,JsonObject.class);
                reader.close();

                JsonArray array = object.getAsJsonArray("fragments");
                List<JsonObject> l = new ArrayList<>();
                for (JsonElement e : array){
                    l.add(e.getAsJsonObject());
                }
                return l;
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }




}
