package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.config.SCAbilitiesConfig;
import com.finderfeed.solarcraft.config.bosses.SCBossesConfig;
import com.finderfeed.solarcraft.config.json_config.JsonConfig;

import java.util.HashMap;
import java.util.Map;

public class SCConfigs {

    public static Map<String, JsonConfig> CONFIG_REGISTRY = new HashMap<>();


    public static SCAbilitiesConfig ABILITIES = register(new SCAbilitiesConfig());
    public static SCBossesConfig BOSSES = register(new SCBossesConfig());

    public static <T extends JsonConfig> T register(T config){
        CONFIG_REGISTRY.put(config.getName(),config);
        return config;
    }

    public static void init(){}

}
