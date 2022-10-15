package com.finderfeed.solarcraft.local_library.custom_registries;

import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class RegistryDelegate {

    private static Map<CustomRegistryEntry<?>, Map<String,Object>> REGISTRY_ENTRIES = new HashMap<>();

    public static <T> Collection<T> getAllRegisteredEntriesFor(CustomRegistryEntry<T> entry){

        return (Collection<T>)(Collection<?>)REGISTRY_ENTRIES.get(entry).values();
    }

    public static <T> T getObject(CustomRegistryEntry<T> entry,ResourceLocation location){
        T toReturn = (T)REGISTRY_ENTRIES.get(entry).get(location.toString());
        if (toReturn == null){
            throw new RuntimeException("No such registered element: "+ location.toString());
        }
        return toReturn;
    }


    public static void register(CustomRegistryEntry<?> entry, ResourceLocation loc, Object reg){
        if (!REGISTRY_ENTRIES.containsKey(entry)){
            REGISTRY_ENTRIES.put(entry,new HashMap<>());
        }
        Map<String,Object> list = REGISTRY_ENTRIES.get(entry);
        String key = loc.toString();
        if (!list.containsKey(key)){
            list.put(key,reg);
        }else{
            throw new RuntimeException("Tried to register %s more than once.".formatted(key));
        }
    }

}
