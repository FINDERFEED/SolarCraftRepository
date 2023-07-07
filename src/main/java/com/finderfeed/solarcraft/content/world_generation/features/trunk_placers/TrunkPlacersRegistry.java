package com.finderfeed.solarcraft.content.world_generation.features.trunk_placers;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class TrunkPlacersRegistry {



    public static Constructor<TrunkPlacerType> CONSTRUCTOR = null;

    static {
        try {
            CONSTRUCTOR = TrunkPlacerType.class.getDeclaredConstructor(Codec.class);
            CONSTRUCTOR.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static TrunkPlacerType<BurntTreeTrunkPlacer> BURNT_TREE_TRUNK_PLACER_TRUNK_PLACER_TYPE = null;

    static {
        try {
            BURNT_TREE_TRUNK_PLACER_TRUNK_PLACER_TYPE = CONSTRUCTOR.newInstance(BurntTreeTrunkPlacer.CODEC);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void registerTrunkPlacerTypes(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{


            Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE,new ResourceLocation("solarcraft","burnt_tree_trunk_placer_type"),BURNT_TREE_TRUNK_PLACER_TRUNK_PLACER_TYPE);
        });
    }
}
