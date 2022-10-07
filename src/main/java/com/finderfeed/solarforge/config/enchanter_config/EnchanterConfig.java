package com.finderfeed.solarforge.config.enchanter_config;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class EnchanterConfig {

    public static final String VERSION = "version";
    public static final String MODE = "mode";
    public static final String ENCHANTMENTS = "enchantments";
    public static final String ENCHANTMENT_ID = "enchantment_id";
    public static final String MAX_LEVEL = "max_level";


    private List<ConfigEnchantmentInstance> CONFIG_ENCHANTMENTS;
    private Map<String,ConfigEnchantmentInstance> CONFIG_ENCHANTMENTS_MAP;
    private Mode mode;
    private float maxEnchanterRunicEnergyCapacity;

    /**
     * Should only be called when all registries happened!
     * @param file the enchantments json file to be parsed
     */
    public EnchanterConfig(JsonObject file){
        CONFIG_ENCHANTMENTS = new ArrayList<>();
        CONFIG_ENCHANTMENTS_MAP = new HashMap<>();
        float max = 0;
        Mode mode = Mode.byId(file.get(MODE).getAsString());
        if (mode != null){
            this.mode = mode;
        }else{
            SolarForge.LOGGER.log(Level.ERROR,"Incorrect mode value in enchanter config, proceeding with static mode.");
            this.mode = Mode.STATIC;
        }
        try {
            for (JsonElement element : file.getAsJsonArray(ENCHANTMENTS)) {
                JsonObject object = element.getAsJsonObject();
                String enchantmentID = object.get(ENCHANTMENT_ID).getAsString();
                int maxLevel = object.get(MAX_LEVEL).getAsInt();

                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantmentID));
                if (enchantment == null){
                    SolarForge.LOGGER.log(Level.ERROR,"Enchantment " + enchantmentID + " doesn't exist. Skipping...");
                    continue;
                }
                RunicEnergyCost cost = new RunicEnergyCost();
                for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
                    if (object.has(type.id)){
                        float c = object.get(type.id).getAsFloat();
                        if (c*maxLevel > max) max = c*maxLevel;
                        cost.set(type,c);
                    }
                }
                if (cost.getSetTypes().isEmpty()){
                    SolarForge.LOGGER.log(Level.ERROR,"Enchantment " + enchantmentID + " doesn't have runic energy baseCost. Skipping...");
                }
                ConfigEnchantmentInstance instance = new ConfigEnchantmentInstance(enchantment,maxLevel,cost);
                CONFIG_ENCHANTMENTS.add(instance);
                CONFIG_ENCHANTMENTS_MAP.put(enchantmentID,instance);
            }
            this.maxEnchanterRunicEnergyCapacity = max;
        }catch (JsonParseException e){
            SolarForge.LOGGER.log(Level.ERROR,"Error reading enchantments array in enchanter config");
            e.printStackTrace();
        }


    }

    public Mode getMode() {
        return mode;
    }

    public List<ConfigEnchantmentInstance> getEnchantments() {
        return CONFIG_ENCHANTMENTS;
    }

    public ConfigEnchantmentInstance getEnchantmentById(String id){
        return CONFIG_ENCHANTMENTS_MAP.get(id);
    }

    public ConfigEnchantmentInstance getConfigEntryByEnchantment(Enchantment enchantment) {
        return CONFIG_ENCHANTMENTS_MAP.get(ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
    }

    public float getMaxEnchanterRunicEnergyCapacity() {
        return maxEnchanterRunicEnergyCapacity;
    }

    public static class JsonBuilder{

        private JsonObject jsonFile;


        public JsonBuilder(){
            this.jsonFile = new JsonObject();
        }


        public EnchantmentsArray beginEnchantments(){
            return new EnchantmentsArray(this);
        }

        public JsonBuilder setMode(Mode mode){
            jsonFile.addProperty(MODE,mode.id);
            return this;
        }

        public JsonBuilder setVersion(int version){
            jsonFile.addProperty(VERSION,version);
            return this;
        }

        public JsonObject build(){
            return jsonFile;
        }



        public static class EnchantmentsArray{

            private JsonArray array;
            private JsonBuilder builder;
            public EnchantmentsArray(JsonBuilder builder){
                this.array = new JsonArray();
                this.builder = builder;
            }

            public EnchantmentsArray addEnchantment(Enchantment enchantment,int maxLevel,RunicEnergyCost cost){
                JsonObject enchantmentInstance = new JsonObject();
                enchantmentInstance.addProperty("enchantment_id",ForgeRegistries.ENCHANTMENTS.getKey(enchantment).toString());
                enchantmentInstance.addProperty("max_level",maxLevel);
                for (RunicEnergy.Type type : cost.getSetTypes()){
                    enchantmentInstance.addProperty(type.id,cost.get(type));
                }
                array.add(enchantmentInstance);
                return this;
            }

            public JsonBuilder end(){
                builder.jsonFile.add("enchantments",array);
                return builder;
            }
        }

    }
    public enum Mode{
        SQUARE("square",(x)->x*x),
        STATIC("static",(x)->x);

        private String id;
        private Function<Float,Float> func;
        Mode(String id, Function<Float,Float> func){
            this.id = id;
            this.func = func;
        }

        private static Mode byId(String id){
            for (Mode mode : Mode.values()){
                if (id.equals(mode.id)){
                    return mode;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return id;
        }
    }



    @FunctionalInterface
    public interface Changes{
        void applyChanges(JsonObject object);
    }
    public static record ConfigEnchantmentInstance(Enchantment enchantment,int maxLevel,RunicEnergyCost baseCost){
        public RunicEnergyCost getCostForLevel(Mode mode,int level){
            RunicEnergyCost newCost = new RunicEnergyCost();
            for (RunicEnergy.Type type : baseCost.getSetTypes()) {
                float maximum = baseCost.get(type)*maxLevel;
                newCost.set(type, Mth.lerp(mode.func.apply(level/(float)maxLevel),0,maximum));
            }
            return newCost;
        }

    }
}
