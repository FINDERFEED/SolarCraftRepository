package com.finderfeed.solarcraft.local_library.other.delayed_getter;

import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

public class DelayedObjectGetterManager<T> {

    private Map<ResourceLocation,DelayedObjectGetter<T>> objects = new HashMap<>();

    public DelayedObjectGetterManager(){}


    public DelayedObjectGetter<T> define(ResourceLocation location){
        if (objects.containsKey(location)){
            throw new IllegalStateException("Duplicate keys: " + location);
        }
        DelayedObjectGetter<T> getter = new DelayedObjectGetter<>();
        this.objects.put(location,getter);
        return getter;
    }

    public void assignValue(ResourceLocation location,T object){
        if (this.objects.containsKey(location)) {
            this.objects.get(location).value = object;
        }else{
            SolarCraft.LOGGER.log(Level.ERROR,"Value getter with name " + location +" doesn't exist.");
        }
    }


}
