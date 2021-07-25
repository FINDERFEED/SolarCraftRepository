package com.finderfeed.solarforge.world_generation.features.trunk_placers;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class TrunkPlacersRegistry {

    public static Constructor<TrunkPlacerType> CONSTRUCTOR = null;

    static {
        try {
            CONSTRUCTOR = TrunkPlacerType.class.getDeclaredConstructor(Codec.class);
            CONSTRUCTOR.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static TrunkPlacerType<BurntTreeTrunkPlacer> BURNT_TREE_TRUNK_PLACER_TRUNK_PLACER_TYPE = null;

    static {
        try {
            BURNT_TREE_TRUNK_PLACER_TRUNK_PLACER_TYPE = CONSTRUCTOR.newInstance(BurntTreeTrunkPlacer.CODEC);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void registerTrunkPlacerTypes(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{

            Registry.register(Registry.TRUNK_PLACER_TYPES,new ResourceLocation("solarforge","burnt_tree_trunk_placer_type"),BURNT_TREE_TRUNK_PLACER_TRUNK_PLACER_TYPE);
        });
    }
}
