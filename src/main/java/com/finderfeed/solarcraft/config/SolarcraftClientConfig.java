package com.finderfeed.solarcraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class SolarcraftClientConfig {


    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Boolean> SHADERS_ENABLED;

    static {
        BUILDER.push("SolarCraft client config");


        SHADERS_ENABLED =
                BUILDER.comment("Enable/Disable shader effects. If your screen blackens when looking at pylons(and other things that use my custom effects) with shaders or optifine disable this.")
                        .define("shaders_enabled",true);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
