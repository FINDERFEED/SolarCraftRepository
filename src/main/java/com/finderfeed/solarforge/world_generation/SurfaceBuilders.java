package com.finderfeed.solarforge.world_generation;


import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)
public class SurfaceBuilders {

    public static final ConfiguredSurfaceBuilder<?> MOLTEN_FOREST_SURFACE_BUILDER = SurfaceBuilder.DEFAULT
            .configured(new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.defaultBlockState(),
                    Blocks.STONE.defaultBlockState(),
                    Blocks.GRAVEL.defaultBlockState()
            ));

    @SubscribeEvent
    public static void registerConfiguredSurfaceBuilders(final FMLCommonSetupEvent event){
        event.enqueueWork(()->{
            Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER,new ResourceLocation("solarforge","molten_forest_surface"),MOLTEN_FOREST_SURFACE_BUILDER);

        });
    }

}
