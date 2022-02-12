package com.finderfeed.solarforge.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class SolarcraftClientConfig {


    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SHADERS_ENABLED;

    static {
        BUILDER.push("SolarCraft client config");


        SHADERS_ENABLED =
                BUILDER.comment("Enable/Disable shader effects. If your screen blackens when playing with shaders or optifine disable this.")
                        .define("shaders_enabled",true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
