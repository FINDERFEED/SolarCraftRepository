package com.finderfeed.solarforge.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.finderfeed.solarforge.SolarForge;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.Level;
import org.lwjgl.system.CallbackI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public final class SolarcraftConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<List<String>> PICKAXE_TREASURES;
    public static final ForgeConfigSpec.ConfigValue<Double> SOLAR_STRIKE_DAMAGE;
    public static final ForgeConfigSpec.ConfigValue<Integer> AUTOHEAL_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> BLESSED_CHANCE;
    public static final ForgeConfigSpec.ConfigValue<Integer> DISARM_CHANCE_MODULE;
    public static final ForgeConfigSpec.ConfigValue<Double> RUNIC_ENERGY_PER_TICK_PYLON;
    public static final ForgeConfigSpec.ConfigValue<List<String>> ISLAND_ORES;
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

        List<String> DEFAULT_BLOCKS = new ArrayList<>();
        DEFAULT_BLOCKS.addAll(List.of("minecraft:diamond_ore","minecraft:iron_ore","minecraft:gold_ore","minecraft:lapis_ore"));
        ISLAND_ORES = BUILDER.comment("Blocks that can generate in islands/crystallized ore veins in radiant land dimension")
                .define("blocks",DEFAULT_BLOCKS);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }


}
