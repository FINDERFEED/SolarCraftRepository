package com.finderfeed.solarcraft.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class SolarcraftClientConfig {


    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<Boolean> SHADERS_ENABLED;
    public static final ModConfigSpec.ConfigValue<Boolean> GLOW_ENABLED;

    static {
        BUILDER.push("SolarCraft client config");


        SHADERS_ENABLED =
                BUILDER.comment("Enable/Disable solar craft shader effects. If you experience some strange visual bugs disable this.")
                        .define("shaders_enabled",true);
        GLOW_ENABLED =
                BUILDER.comment("Enable/Disable experimental solar craft glow shader.")
                        .define("shaders_enabled",true);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
