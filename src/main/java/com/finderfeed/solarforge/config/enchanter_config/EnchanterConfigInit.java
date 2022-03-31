package com.finderfeed.solarforge.config.enchanter_config;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.google.gson.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class EnchanterConfigInit {



    public static final Gson SERIALIZER = new Gson().newBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    public static JsonObject SERVERSIDE_JSON;
    public static final Path configDir = FMLPaths.CONFIGDIR.get().resolve(SolarcraftConfig.CUSTOM_CONFIGS_FOLDER).resolve(SolarcraftConfig.CUSTOM_BLOCKS_CONFIGS);


    private static boolean checkVersionAndApplyChanges(JsonObject object){
        boolean changesWereMade = false;
        if (!object.has(EnchanterConfig.VERSION)){
            object.addProperty(EnchanterConfig.VERSION,0);
            changesWereMade = true;
        }
        int version = object.get(EnchanterConfig.VERSION).getAsInt();
        if (version < SolarForge.ENCHANTER_CONFIG_VERSION) {
            changesWereMade = true;
            SolarForge.LOGGER.log(Level.INFO,"Detected outdated enchanter config. Applying changes using default values.");
            for (int i = version+1; i <= SolarForge.ENCHANTER_CONFIG_VERSION; i++) {
                EnchanterConfigChanges.CHANGES_IN_ORDER[i].applyChanges(object);
            }
            object.remove(EnchanterConfig.VERSION);
            object.addProperty(EnchanterConfig.VERSION,SolarForge.ENCHANTER_CONFIG_VERSION);
        }
        return changesWereMade;
    }


    public static void setupJSON(){
        SolarForge.LOGGER.log(Level.INFO,"Creating enchanter config");
        Path filePath = configDir.resolve("enchanter_enchantments.json");
        try {
            if (!Files.exists(filePath)){
                Files.createDirectories(filePath.getParent());
                Writer writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE);
                //TODO:finish this
                JsonObject obj = new EnchanterConfig.JsonBuilder()
                        .setMode(EnchanterConfig.Mode.STATIC)
                        .setVersion(SolarForge.ENCHANTER_CONFIG_VERSION)
                        .beginEnchantments()
                        .addEnchantment(Enchantments.SHARPNESS, Enchantments.SHARPNESS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.URBA,2000).set(RunicEnergy.Type.TERA,5000))


                        .end()
                        .build();
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
                boolean flag = checkVersionAndApplyChanges(object);
                if (flag){
                    Writer writer = Files.newBufferedWriter(filePath);
                    SERIALIZER.toJson(object,writer);
                    writer.flush();
                    writer.close();
                }
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
