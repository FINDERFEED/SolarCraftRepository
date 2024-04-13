package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.config.SCDefaultConfig;
import com.finderfeed.solarcraft.config.json_config.JsonConfig;

import java.util.HashMap;
import java.util.Map;

public class SCConfigs {

    public static Map<String, JsonConfig> CONFIG_REGISTRY = new HashMap<>();


    public static SCDefaultConfig DEFAULT_CONFIG = register(new SCDefaultConfig());

    public static <T extends JsonConfig> T register(T config){
        CONFIG_REGISTRY.put(config.getName(),config);
        return config;
    }

    public static void init(){}

}
