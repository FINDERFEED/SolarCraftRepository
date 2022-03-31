package com.finderfeed.solarforge.config.enchanter_config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

public class EnchanterConfigChanges {

    private static final EnchanterConfig.Changes VERSION_0 = (file)->{};

    private static final EnchanterConfig.Changes VERSION_1 = (file)->{
        if (!file.has(EnchanterConfig.MODE)){
            file.addProperty(EnchanterConfig.MODE, EnchanterConfig.Mode.STATIC.toString());
        }
        if (!file.has("_comment")){
            file.addProperty("_comment","DO NOT TOUCH THE VERSION VALUE! I use it to indicate if config was updated to automatically insert changes.");
        }
        for (JsonElement element : file.getAsJsonArray(EnchanterConfig.ENCHANTMENTS)){
            JsonObject object = element.getAsJsonObject();
            if (!object.has(EnchanterConfig.MAX_LEVEL)){
                String eID = object.get(EnchanterConfig.ENCHANTMENT_ID).getAsString();
                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(eID));
                if (enchantment != null){
                    object.addProperty(EnchanterConfig.MAX_LEVEL,enchantment.getMaxLevel());
                }
            }
        }
    };

    public static final EnchanterConfig.Changes[] CHANGES_IN_ORDER = {VERSION_0,VERSION_1};

}
