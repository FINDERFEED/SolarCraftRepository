package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.config.ItemREConfig;
import com.finderfeed.solarcraft.config.JsonConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigRegistry {

    public static final Map<String, JsonConfig> EARLY_LOAD_CONFIGS = new HashMap<>();
    public static final Map<String, JsonConfig> POST_LOAD_CONFIGS = new HashMap<>();


    public static final ItemREConfig ITEM_RE_CONFIG = registerPostLoadConfig(new ItemREConfig());


    private static <T extends JsonConfig> T registerEarlyLoadConfig(T config){
        EARLY_LOAD_CONFIGS.putIfAbsent(config.getName(),config);
        return config;
    }
    private static <T extends JsonConfig> T registerPostLoadConfig(T config){
        POST_LOAD_CONFIGS.putIfAbsent(config.getName(),config);
        return config;
    }

    public static void init(){}
}
