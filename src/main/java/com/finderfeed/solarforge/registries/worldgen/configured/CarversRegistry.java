package com.finderfeed.solarforge.registries.worldgen.configured;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.world_generation.features.carvers.TestCarver;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = SolarForge.MOD_ID)
public class CarversRegistry {

//    public static WorldCarver<CaveCarverConfiguration> TEST_CARVER;
//    public static ConfiguredWorldCarver<CaveCarverConfiguration> TEST_CARVER_CONFIGURED;

    @SubscribeEvent
    public static void registerCarvers(RegistryEvent.Register<WorldCarver<?>> event){
//        TEST_CARVER = new TestCarver(CaveCarverConfiguration.CODEC);
//        event.getRegistry().register(TEST_CARVER.setRegistryName(new ResourceLocation(SolarForge.MOD_ID,"test_carver")));

    }


    @SubscribeEvent
    public static void registerConfiguredCarvers(FMLCommonSetupEvent event){
        event.enqueueWork(()-> {
//            CaveCarverConfiguration c = new CaveCarverConfiguration(0.07F,
//                    UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(47)),
//                    UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8),
//                    CarverDebugSettings.of(false, Blocks.OAK_BUTTON.defaultBlockState()),
//                    UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F),
//                    UniformFloat.of(-1.0F, -0.4F));
//            TEST_CARVER_CONFIGURED = TEST_CARVER.configured(c);
//            Registry.register(BuiltinRegistries.CONFIGURED_CARVER,new ResourceLocation(SolarForge.MOD_ID,"test_carver"),TEST_CARVER_CONFIGURED);

        });
    }

}
