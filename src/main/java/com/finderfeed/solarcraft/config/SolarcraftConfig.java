package com.finderfeed.solarcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;



public final class SolarcraftConfig {
    public static final String CUSTOM_CONFIGS_FOLDER = "solarcraft_custom_configs";
    public static final String CUSTOM_BLOCKS_CONFIGS = "block_configs";

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<String>> PICKAXE_TREASURES;
    public static final ForgeConfigSpec.ConfigValue<Double> SOLAR_STRIKE_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> AUTOHEAL_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> BLESSED_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> DISARM_CHANCE_MODULE;
    public static final ForgeConfigSpec.ConfigValue<Double> RUNIC_ENERGY_PER_TICK_PYLON;
    public static final ForgeConfigSpec.ConfigValue<Double> RUNIC_ENERGY_PER_TICK_UPGRADE;
    public static final ForgeConfigSpec.ConfigValue<Double> RUNIC_ENERGY_PER_RUNE_CHARGER;
    public static final ForgeConfigSpec.ConfigValue<Integer> ENERGY_PYLON_SPAWN_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<List<String>> ISLAND_ORES;

    public static final ForgeConfigSpec.ConfigValue<Boolean> IS_ORBITAL_MISSILE_LAUNCHER_ALLOWED;
    static {
        BUILDER.push("SolarCraft config");

        List<String> DEFAULT_TREASURES = new ArrayList<>();
        DEFAULT_TREASURES.addAll(List.of("minecraft:diamond", "minecraft:coal", "minecraft:gold_nugget", "minecraft:iron_nugget"));
        PICKAXE_TREASURES = BUILDER.comment("Treasure items for qualadium pickaxe")
                .define("treasures",DEFAULT_TREASURES);

        SOLAR_STRIKE_DAMAGE = BUILDER.comment("Solar strike ability damage").define("solar_strike_damage",300d);
        AUTOHEAL_CHANCE = BUILDER.comment("The chance to heal the sword with sword heal module").define("chance",10);
        BLESSED_CHANCE = BUILDER.comment("The chance to restore durability with blessed module").define("chance",5);
        DISARM_CHANCE_MODULE = BUILDER.comment("Chance to disarm an entity when having the disarm module on armor").define("chance",10);
        RUNIC_ENERGY_PER_TICK_PYLON = BUILDER.comment("Base runic energy pylon energy generation").define("per tick",0.5d);
        RUNIC_ENERGY_PER_TICK_UPGRADE = BUILDER.comment("Runic energy regen increment per each inscripted stone").define("per tick upgrade",0.05d);

        List<String> DEFAULT_BLOCKS = new ArrayList<>();
        DEFAULT_BLOCKS.addAll(List.of("minecraft:diamond_ore","minecraft:iron_ore","minecraft:gold_ore","minecraft:lapis_ore"));
        ISLAND_ORES = BUILDER.comment("Blocks that can generate in islands/crystallized ore veins in radiant land dimension")
                .define("blocks",DEFAULT_BLOCKS);

        RUNIC_ENERGY_PER_RUNE_CHARGER = BUILDER.comment("How much runic energy will be given to runic energy charger when it consumes a rune.")
                .define("runic_energy_per_rune",7.5d);

        ENERGY_PYLON_SPAWN_CHANCE = BUILDER.comment("Runic energy pylon spawn chance. Works similar to datapacks")
                .define("spawn_chance",80);

        IS_ORBITAL_MISSILE_LAUNCHER_ALLOWED = BUILDER.comment("Is Orbital Missile Launcher block allowed")
                        .define("allowed",true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }


}
