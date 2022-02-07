package com.finderfeed.solarforge.config;

import com.finderfeed.solarforge.SolarForge;
import com.google.gson.*;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class EnchantmentsConfig {

    public static final Gson SERIALIZER = new Gson().newBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static JsonObject SERVERSIDE_JSON;
    public static final Path configDir = FMLPaths.CONFIGDIR.get().resolve(SolarcraftConfig.CUSTOM_CONFIGS_FOLDER).resolve(SolarcraftConfig.CUSTOM_BLOCKS_CONFIGS);

    public static void setupJSON(){
        SolarForge.LOGGER.log(Level.INFO,"Creating enchanter config");
        Path filePath = configDir.resolve("enchanter_enchantments.json");
        try {
            if (!Files.exists(filePath)){
                Files.createDirectories(filePath.getParent());
                Writer writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE);
                JsonElement object = JsonParser.parseString("""
                        {
                            "enchantments": [
                                {
                                    "enchantment_id": "minecraft:sharpness",
                                    "urba": 2000,
                                    "tera": 5000
                                },
                                {
                                    "enchantment_id": "minecraft:protection",
                                    "tera": 10000
                                }
                            ]
                        }
                        """);


                SERIALIZER.toJson(object,writer);
                writer.flush();
                writer.close();
            }
            SolarForge.LOGGER.log(Level.INFO,"Enchanter config created");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void readJson(){
        SolarForge.LOGGER.log(Level.INFO,"Reading enchanter config");
        Path filePath = configDir.resolve("enchanter_enchantments.json");
        if (Files.exists(filePath)){
            try {
                Reader reader = Files.newBufferedReader(filePath);
                JsonObject object = SERIALIZER.fromJson(reader,JsonObject.class);
                reader.close();
                SERVERSIDE_JSON = object;
                SolarForge.LOGGER.log(Level.INFO,"Enchanter config reading complete");
            }catch (IOException e){
                SolarForge.LOGGER.log(Level.ERROR,"Error while reading enchanter config");
                e.printStackTrace();

            }
        }else{
            SolarForge.LOGGER.log(Level.ERROR,"Error while reading enchanter config, file doesn't exist");

        }
    }
    public static boolean shouldBeRead(){
        return SERVERSIDE_JSON == null;
    }


}
