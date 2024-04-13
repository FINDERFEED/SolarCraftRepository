package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.config.ItemREConfig;
import com.finderfeed.solarcraft.config.LegacyJsonConfig;
import com.finderfeed.solarcraft.config.PuzzlePatternsConfig;

import java.util.HashMap;
import java.util.Map;

public class LegacyConfigRegistry {

    public static final Map<String, LegacyJsonConfig> EARLY_LOAD_CONFIGS = new HashMap<>();
    public static final Map<String, LegacyJsonConfig> POST_LOAD_CONFIGS = new HashMap<>();


    public static final ItemREConfig ITEM_RE_CONFIG = registerPostLoadConfig(new ItemREConfig());
    public static final PuzzlePatternsConfig PUZZLE_PATTERNS = registerPostLoadConfig(new PuzzlePatternsConfig());


    private static <T extends LegacyJsonConfig> T registerEarlyLoadConfig(T config){
        EARLY_LOAD_CONFIGS.putIfAbsent(config.getName(),config);
        return config;
    }
    private static <T extends LegacyJsonConfig> T registerPostLoadConfig(T config){
        POST_LOAD_CONFIGS.putIfAbsent(config.getName(),config);
        return config;
    }

    public static void init(){}
}
