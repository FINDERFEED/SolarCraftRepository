package com.finderfeed.solarforge.local_library.custom_registries;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CustomDeferredRegistry<T> {

    private final Map<String,T> entries;
    private final String namespace;
    private final CustomRegistryEntry<T> registerTo;

    private CustomDeferredRegistry(String namespace, CustomRegistryEntry<T> entry){
        this.entries = new HashMap<>();
        this.namespace = namespace;
        this.registerTo = entry;
    }

    public static <V> CustomDeferredRegistry<V> create(String namespace, CustomRegistryEntry<V> entry){
        return new CustomDeferredRegistry<>(namespace,entry);
    }

    public void registerAll(){
        entries.forEach((id,reg)->{
            RegistryDelegate.register(registerTo,new ResourceLocation(namespace,id),reg);
        });
    }

    public T register(String id, Supplier<T> sup){
        T reg = sup.get();
        entries.put(id,reg);
        return reg;
    }


    public String getNamespace() {
        return namespace;
    }

}
