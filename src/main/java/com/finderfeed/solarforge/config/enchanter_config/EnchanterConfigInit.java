package com.finderfeed.solarforge.config.enchanter_config;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

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
                JsonObject obj = new EnchanterConfig.JsonBuilder()
                        .setMode(EnchanterConfig.Mode.STATIC)
                        .setVersion(SolarForge.ENCHANTER_CONFIG_VERSION)
                        .beginEnchantments()
                        .addEnchantment(Enchantments.SHARPNESS, Enchantments.SHARPNESS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.URBA,2000).set(RunicEnergy.Type.TERA,5000))
                        .addEnchantment(Enchantments.ALL_DAMAGE_PROTECTION,Enchantments.ALL_DAMAGE_PROTECTION.getMaxLevel(), new RunicEnergyCost().set(RunicEnergy.Type.ARDO,10000).set(RunicEnergy.Type.KELDA,1000))
                        .addEnchantment(Enchantments.AQUA_AFFINITY,Enchantments.AQUA_AFFINITY.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.URBA,1500).set(RunicEnergy.Type.ZETA,1000))
                        .addEnchantment(Enchantments.BANE_OF_ARTHROPODS,Enchantments.BANE_OF_ARTHROPODS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.FIRA,3500).set(RunicEnergy.Type.ULTIMA,2000))
                        .addEnchantment(Enchantments.BLAST_PROTECTION,Enchantments.BANE_OF_ARTHROPODS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,5000).set(RunicEnergy.Type.GIRO,3000))
                        .addEnchantment(Enchantments.CHANNELING,Enchantments.CHANNELING.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,2500).set(RunicEnergy.Type.URBA,2500))
                        .addEnchantment(Enchantments.DEPTH_STRIDER,Enchantments.DEPTH_STRIDER.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,1500).set(RunicEnergy.Type.ZETA,2500))
                        .addEnchantment(Enchantments.BLOCK_EFFICIENCY,Enchantments.BLOCK_EFFICIENCY.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.URBA,1000).set(RunicEnergy.Type.TERA,4000).set(RunicEnergy.Type.GIRO,3000))
                        .addEnchantment(Enchantments.FALL_PROTECTION,Enchantments.FALL_PROTECTION.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.KELDA,2000).set(RunicEnergy.Type.ZETA,1500))
                        .addEnchantment(Enchantments.FIRE_ASPECT,Enchantments.FIRE_ASPECT.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,5000).set(RunicEnergy.Type.FIRA,5000))
                        .addEnchantment(Enchantments.FIRE_PROTECTION,Enchantments.FIRE_PROTECTION.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,4000).set(RunicEnergy.Type.GIRO,3500).set(RunicEnergy.Type.URBA,1000))
                        .addEnchantment(Enchantments.FLAMING_ARROWS,Enchantments.FLAMING_ARROWS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,5000).set(RunicEnergy.Type.FIRA,5000))
                        .addEnchantment(Enchantments.BLOCK_FORTUNE,Enchantments.BLOCK_FORTUNE.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,5000).set(RunicEnergy.Type.FIRA,5000).set(RunicEnergy.Type.KELDA,5000))
                        .addEnchantment(Enchantments.FROST_WALKER,Enchantments.FROST_WALKER.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,2000).set(RunicEnergy.Type.FIRA,3500))
                        .addEnchantment(Enchantments.IMPALING,Enchantments.IMPALING.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ZETA,3000).set(RunicEnergy.Type.ARDO,2500))
                        .addEnchantment(Enchantments.INFINITY_ARROWS,Enchantments.INFINITY_ARROWS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,10000).set(RunicEnergy.Type.ARDO,7500).set(RunicEnergy.Type.KELDA,4000))
                        .addEnchantment(Enchantments.KNOCKBACK,Enchantments.KNOCKBACK.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,2000).set(RunicEnergy.Type.FIRA,3000).set(RunicEnergy.Type.ARDO,2500))
                        .addEnchantment(Enchantments.MOB_LOOTING,Enchantments.MOB_LOOTING.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,5000).set(RunicEnergy.Type.FIRA,5000).set(RunicEnergy.Type.KELDA,5000))
                        .addEnchantment(Enchantments.LOYALTY,Enchantments.LOYALTY.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ZETA,3500).set(RunicEnergy.Type.FIRA,4000))
                        .addEnchantment(Enchantments.FISHING_LUCK,Enchantments.FISHING_LUCK.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,2000).set(RunicEnergy.Type.URBA,1500))
                        .addEnchantment(Enchantments.FISHING_SPEED,Enchantments.FISHING_SPEED.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,1000))
                        .addEnchantment(Enchantments.MENDING,Enchantments.MENDING.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,10000).set(RunicEnergy.Type.KELDA,10000).set(RunicEnergy.Type.FIRA,10000).set(RunicEnergy.Type.ZETA,10000).set(RunicEnergy.Type.URBA,10000).set(RunicEnergy.Type.ARDO,10000).set(RunicEnergy.Type.ULTIMA,10000).set(RunicEnergy.Type.GIRO,10000))
                        .addEnchantment(Enchantments.MULTISHOT,Enchantments.MULTISHOT.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ARDO,2500).set(RunicEnergy.Type.TERA,3000))
                        .addEnchantment(Enchantments.PIERCING,Enchantments.PIERCING.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ZETA,2000).set(RunicEnergy.Type.ARDO,3500))
                        .addEnchantment(Enchantments.POWER_ARROWS,Enchantments.POWER_ARROWS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,5000).set(RunicEnergy.Type.ARDO,4500).set(RunicEnergy.Type.KELDA,2500))
                        .addEnchantment(Enchantments.PROJECTILE_PROTECTION,Enchantments.PROJECTILE_PROTECTION.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,5000).set(RunicEnergy.Type.GIRO,3000))
                        .addEnchantment(Enchantments.PUNCH_ARROWS,Enchantments.PUNCH_ARROWS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,2500).set(RunicEnergy.Type.GIRO,1500))
                        .addEnchantment(Enchantments.QUICK_CHARGE,Enchantments.QUICK_CHARGE.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.FIRA,3500).set(RunicEnergy.Type.KELDA,4000))
                        .addEnchantment(Enchantments.RESPIRATION,Enchantments.RESPIRATION.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,2000).set(RunicEnergy.Type.URBA,1000))
                        .addEnchantment(Enchantments.RIPTIDE,Enchantments.RIPTIDE.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,3500).set(RunicEnergy.Type.KELDA,8000))
                        .addEnchantment(Enchantments.SILK_TOUCH,Enchantments.SILK_TOUCH.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,7500))
                        .addEnchantment(Enchantments.SMITE,Enchantments.SMITE.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,2500).set(RunicEnergy.Type.KELDA,4000))
                        .addEnchantment(Enchantments.SOUL_SPEED,Enchantments.SOUL_SPEED.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,2000))
                        .addEnchantment(Enchantments.SWEEPING_EDGE,Enchantments.SWEEPING_EDGE.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ZETA,4000).set(RunicEnergy.Type.ARDO,3000).set(RunicEnergy.Type.FIRA,2500))
                        .addEnchantment(Enchantments.THORNS,Enchantments.THORNS.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.TERA,3500).set(RunicEnergy.Type.KELDA,2000).set(RunicEnergy.Type.ARDO,3000))
                        .addEnchantment(Enchantments.UNBREAKING,Enchantments.UNBREAKING.getMaxLevel(),new RunicEnergyCost().set(RunicEnergy.Type.ULTIMA,3500))
                        .end()
                        .build();



                SERIALIZER.toJson(obj,writer);
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
