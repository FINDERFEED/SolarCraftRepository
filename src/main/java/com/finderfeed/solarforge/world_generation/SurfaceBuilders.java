package com.finderfeed.solarforge.world_generation;


import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)
public class SurfaceBuilders {

    public static final ConfiguredSurfaceBuilder<?> MOLTEN_FOREST_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
            .configured(new SurfaceBuilderBaseConfiguration(Blocks.GRASS_BLOCK.defaultBlockState(),
                    Blocks.STONE.defaultBlockState(),
                    Blocks.GRAVEL.defaultBlockState()
            ));

    @SubscribeEvent
    public static void registerConfiguredSurfaceBuilders(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER,new ResourceLocation("solarforge","molten_forest_surface"),MOLTEN_FOREST_SURFACE_BUILDER);

        });
    }

}
