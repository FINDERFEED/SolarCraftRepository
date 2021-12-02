package com.finderfeed.solarforge.world_generation;


import com.finderfeed.solarforge.events.other_events.event_handler.EventHandler;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceSystem;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)
public class SurfaceBuilders {

//    public static final ConfiguredSurfaceBuilder<?> MOLTEN_FOREST_SURFACE_BUILDER;
//
//    public static ConfiguredSurfaceBuilder<?> RADIANT_LAND_SURFACE_BUILDER;

//    public static SurfaceRules.RuleSource RADIANT_LAND_RULE;


    @SubscribeEvent
    public static void registerConfiguredSurfaceBuilders(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{

//
//            MOLTEN_FOREST_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
//                    .configured(new SurfaceBuilderBaseConfiguration(Blocks.GRASS_BLOCK.defaultBlockState(),
//                            Blocks.STONE.defaultBlockState(),
//                            Blocks.GRAVEL.defaultBlockState()
//                    ));
//            Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER,new ResourceLocation("solarforge","molten_forest_surface"),MOLTEN_FOREST_SURFACE_BUILDER);
//
//            RADIANT_LAND_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
//                    .configured(new SurfaceBuilderBaseConfiguration(BlocksRegistry.RADIANT_GRASS.get().defaultBlockState(),
//                            Blocks.STONE.defaultBlockState(),
//                            Blocks.GRAVEL.defaultBlockState()
//                    ));
//            Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER,new ResourceLocation("solarforge","radiant_land"),RADIANT_LAND_SURFACE_BUILDER);
        });
    }

}
