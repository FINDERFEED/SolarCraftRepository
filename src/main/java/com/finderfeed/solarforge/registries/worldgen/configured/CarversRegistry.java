package com.finderfeed.solarforge.registries.worldgen.configured;

import com.finderfeed.solarforge.SolarForge;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = SolarForge.MOD_ID)
public class CarversRegistry {


    @SubscribeEvent
    public static void registerCarvers(RegistryEvent.Register<WorldCarver<?>> event){


    }


    @SubscribeEvent
    public static void registerConfiguredCarvers(FMLCommonSetupEvent event){

    }

}
