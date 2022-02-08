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
                                    "ardo": 10000,
                                    "kelda": 1000
                                },
                                {
                                    "enchantment_id": "minecraft:aqua_affinity",
                                    "urba": 1500,
                                    "zeta":1000
                               
                                },
                                {
                                    "enchantment_id": "minecraft:bane_of_arthropods",
                                    "fira": 3500,
                                    "ultima": 2000
                                    
                                },
                                {
                                    "enchantment_id": "minecraft:blast_protection",
                                    "tera": 5000,
                                    "giro": 3000
                                },
                                {
                                    "enchantment_id": "minecraft:channeling",
                                    "ultima": 2500,
                                    "urba": 2500
                                },
                                {
                                    "enchantment_id": "minecraft:depth_strider",
                                    "tera": 1500,
                                    "zeta": 2500
                                },
                                {
                                    "enchantment_id": "minecraft:efficiency",
                                    "urba": 1000,
                                    "tera": 4000,
                                    "giro": 3000
                                },
                                {
                                    "enchantment_id": "minecraft:feather_falling",
                                    "kelda": 2000,
                                    "zeta": 1500
                                },
                                {
                                    "enchantment_id": "minecraft:fire_aspect",
                                    "ultima": 5000,
                                    "fira": 5000
                                },
                                {
                                    "enchantment_id": "minecraft:fire_protection",
                                    "tera": 4000,
                                    "giro": 3500,
                                    "urba": 1000
                                  
                                },
                                {
                                    "enchantment_id": "minecraft:flame",
                                    "ultima": 5000,
                                    "fira": 5000
                                },
                                {
                                    "enchantment_id": "minecraft:fortune",
                                    "ultima": 5000,
                                    "fira": 5000,
                                    "kelda": 5000
                                },
                                {
                                    "enchantment_id": "minecraft:frost_walker",
                                    "tera": 2000,
                                    "fira": 3500
                                },
                                {
                                    "enchantment_id": "minecraft:impaling",
                                    "zeta": 3000,
                                    "ardo": 2500
                                },
                                {
                                    "enchantment_id": "minecraft:infinity",
                                    "tera": 10000,
                                    "ardo": 7500,
                                    "kelda": 4000
                                },
                                {
                                    "enchantment_id": "minecraft:knockback",
                                    "tera": 2000,
                                    "fira": 3000,
                                    "ardo": 2500
                                },
                                {
                                    "enchantment_id": "minecraft:looting",
                                    "ultima": 5000,
                                    "fira": 5000,
                                    "kelda": 5000
                                },
                                {
                                    "enchantment_id": "minecraft:loyalty",
                                    "zeta": 3500,
                                    "fira": 4000
                                },
                                {
                                    "enchantment_id": "minecraft:luck_of_the_sea",
                                    "tera": 2000,
                                    "urba": 1500
                                },
                                {
                                    "enchantment_id": "minecraft:lure",
                                    "tera": 10000
                                },
                                {
                                    "enchantment_id": "minecraft:mending",
                                    "tera": 10000,
                                    "kelda": 10000,
                                    "fira": 10000,
                                    "zeta": 10000,
                                    "urba": 10000,
                                    "ardo": 10000,
                                    "ultima": 10000,
                                    "giro": 10000
                                },
                                {
                                    "enchantment_id": "minecraft:multishot",
                                    "ardo": 2500,
                                    "tera": 3000
                                },
                                {
                                    "enchantment_id": "minecraft:piercing",
                                    "zeta": 2000,
                                    "ardo": 3500
                                },
                                {
                                    "enchantment_id": "minecraft:power",
                                    "tera": 5000,
                                    "ardo": 4500,
                                    "kelda": 2500
                                },
                                {
                                    "enchantment_id": "minecraft:projectile_protection",
                                    "tera": 5000,
                                    "giro": 3000
                                },
                                {
                                    "enchantment_id": "minecraft:punch",
                                    "ultima": 2500,
                                    "giro": 1500
                                },
                                {
                                    "enchantment_id": "minecraft:quick_charge",
                                    "fira": 3500,
                                    "kelda": 4000
                                },
                                {
                                    "enchantment_id": "minecraft:respiration",
                                    "tera": 2000,
                                    "urba": 1000
                                },
                                {
                                    "enchantment_id": "minecraft:riptide",
                                    "tera": 3500,
                                    "kelda": 8000
                                },
                                {
                                    "enchantment_id": "minecraft:silk_touch",
                                    "tera": 7500
                                },
                                {
                                    "enchantment_id": "minecraft:smite",
                                    "tera": 2500,
                                    "kelda": 4000
                                },
                                {
                                    "enchantment_id": "minecraft:soul_speed",
                                    "tera": 2000
                                },
                                {
                                    "enchantment_id": "minecraft:sweeping",
                                    "zeta": 4000,
                                    "ardo": 3000,
                                    "fira": 2500
                                },
                                {
                                    "enchantment_id": "minecraft:thorns",
                                    "tera": 3500,
                                    "kelda": 2000,
                                    "ardo": 3000
                                },
                                {
                                    "enchantment_id": "minecraft:unbreaking",
                                    "ultima": 3500
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
