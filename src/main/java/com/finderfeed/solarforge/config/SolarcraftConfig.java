package com.finderfeed.solarforge.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.finderfeed.solarforge.SolarForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.Level;
import org.lwjgl.system.CallbackI;

import java.io.File;

//@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)

//unused
public class SolarcraftConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG;

    static {
        CONFIG = BUILDER.build();
    }
    public static void load(ForgeConfigSpec configSpec,String path){
        SolarForge.LOGGER.log(Level.INFO,"Loading Config "+path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().writingMode(WritingMode.REPLACE).build();
        SolarForge.LOGGER.log(Level.INFO,"Loading Config "+path + " finished");
        file.load();
        configSpec.setConfig(file);
    }
}
