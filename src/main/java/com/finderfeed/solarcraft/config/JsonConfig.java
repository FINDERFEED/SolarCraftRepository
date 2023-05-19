package com.finderfeed.solarcraft.config;

import com.finderfeed.solarcraft.SolarCraft;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

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

    private String name;
    private Path savePath;
    private boolean initialized = false;

    /**
     * Saving the json object in order to sync server and client efficiently
     */
    private JsonObject object;

    public JsonConfig(String name){
        this.name = name;
        this.savePath = FMLPaths.CONFIGDIR.get().resolve(SolarcraftConfig.CUSTOM_CONFIGS_FOLDER)
                .resolve(name + ".json");
    }


    public void init(){
        if (!initialized) {
            this.createFileIfNecessary();
            this.loadFromFile();
            this.deserialize(object);
            initialized = true;
        }
    }

    private JsonObject loadFromFile(){
        SolarCraft.LOGGER.log(Level.INFO,"Loading custom config: " + name);
        try{
            Reader reader = Files.newBufferedReader(savePath);
            JsonObject object = GSON.fromJson(reader,JsonObject.class);
            this.object = object;
            reader.close();
            SolarCraft.LOGGER.log(Level.INFO,"Config loaded: " + name);
            return object;
        }catch (IOException e){

            throw new RuntimeException(e);
        }
    }

    private void createFileIfNecessary(){
        SolarCraft.LOGGER.log(Level.INFO,"Creating custom config if necessary: " + name);
        try{
            if (!Files.exists(savePath)){
                Writer writer = Files.newBufferedWriter(savePath, StandardOpenOption.CREATE);
                GSON.toJson(defaultJson(),writer);
                writer.flush();
                writer.close();
                SolarCraft.LOGGER.log(Level.INFO,"Custom config created: " + name);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public abstract JsonObject defaultJson();

    public abstract void deserialize(JsonObject json);


    public String getName() {
        return name;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public JsonObject getJson() {
        return object;
    }
}
