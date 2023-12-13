package com.finderfeed.solarcraft.content.world_generation.features.foliage_placers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.RadiantSmallTreeFoliagePlacer;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.RadiantTreeFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.RegistryObject;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;

public class FoliagePlacerRegistry {

//    public static FoliagePlacerType<BurntTreeFoliagePlacer> BURNT_TREE_PLACER = new FoliagePlacerType<>(BurntTreeFoliagePlacer.CODEC);
//    public static FoliagePlacerType<RadiantTreeFoliagePlacer> RADIANT_PLACER = new FoliagePlacerType<>(RadiantTreeFoliagePlacer.CODEC);
//    public static FoliagePlacerType<RadiantSmallTreeFoliagePlacer> RADIANT_SMALL_PLACER = new FoliagePlacerType<>(RadiantSmallTreeFoliagePlacer.CODEC);

    public static final DeferredRegister<FoliagePlacerType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, SolarCraft.MOD_ID);

    public static final RegistryObject<FoliagePlacerType<BurntTreeFoliagePlacer>> BURNT_TREE_PLACER = DEFERRED_REGISTER.register("burnt_tree_foliage",()->
            new FoliagePlacerType<>(BurntTreeFoliagePlacer.CODEC));

    public static final RegistryObject<FoliagePlacerType<RadiantTreeFoliagePlacer>> RADIANT_PLACER = DEFERRED_REGISTER.register("radiant_tree_foliage",()->
            new FoliagePlacerType<>(RadiantTreeFoliagePlacer.CODEC));

    public static final RegistryObject<FoliagePlacerType<RadiantSmallTreeFoliagePlacer>> RADIANT_SMALL_PLACER = DEFERRED_REGISTER.register("radiant_tree_small_foliage",()->
            new FoliagePlacerType<>(RadiantSmallTreeFoliagePlacer.CODEC));
}
