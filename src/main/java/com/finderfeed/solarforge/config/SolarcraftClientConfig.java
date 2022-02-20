package com.finderfeed.solarforge.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public final class SolarcraftClientConfig {


    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SHADERS_ENABLED;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DISABLE_WELCOME_MESSAGE;

    static {
        BUILDER.push("SolarCraft client config");


        SHADERS_ENABLED =
                BUILDER.comment("Enable/Disable shader effects. If your screen blackens when looking at pylons(and other things that use my custom effects) with shaders or optifine disable this.")
                        .define("shaders_enabled",true);

        DISABLE_WELCOME_MESSAGE =
                BUILDER.comment("Disable \"welcome\" message")
                        .define("disabled",false);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
