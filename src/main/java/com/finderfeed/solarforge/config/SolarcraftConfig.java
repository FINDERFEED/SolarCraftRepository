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
    public static final ForgeConfigSpec.ConfigValue<Float> SOLAR_STRIKE_DAMAGE;
    static {
        BUILDER.push("SolarCraft config");


        List<String> DEFAULT = new ArrayList<>();
        DEFAULT.addAll(List.of("minecraft:diamond", "minecraft:coal", "minecraft:gold_nugget", "minecraft:iron_nugget"));
        PICKAXE_TREASURES = BUILDER.comment("Treasure items for qualadium pickaxe")
                .define("treasures",DEFAULT);

        SOLAR_STRIKE_DAMAGE = BUILDER.comment("Solar strike ability damage").define("solar_strike_damage",300f);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
