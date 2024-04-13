package com.finderfeed.solarcraft.config.json_config;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class JsonConfig {
    protected static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private Path path;
    private JsonObject loadedJson;
    private String name;
    public JsonConfig(String name){
        path = FMLPaths.CONFIGDIR.get().resolve(SolarcraftConfig.CUSTOM_CONFIGS_FOLDER).resolve(name + ".json");
        this.name = name;
    }


    public void loadFromDisk(){
        try {
            SolarCraft.LOGGER.log(Level.INFO,"Loading config " + name);
            if (!Files.exists(path)){
                Writer writer = Files.newBufferedWriter(path);
                GSON.toJson(new JsonObject(),writer);
                writer.close();
            }
            Reader reader = Files.newBufferedReader(path);
            JsonObject object = GSON.fromJson(reader,JsonObject.class);
            reader.close();
            boolean changesWereMade = this.parseJson(object);
            if (changesWereMade){
                Writer writer = Files.newBufferedWriter(path);
                GSON.toJson(object,writer);
                writer.close();
            }
            this.loadedJson = object;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse all data and assign all values into json config
     * @return if changes to json object were made
     */
    public abstract boolean parseJson(JsonObject object);

    public String getName() {
        return name;
    }

    public JsonObject getLoadedJson() {
        return loadedJson;
    }

    public void setLoadedJson(JsonObject loadedJson) {
        this.loadedJson = loadedJson;
    }
}
