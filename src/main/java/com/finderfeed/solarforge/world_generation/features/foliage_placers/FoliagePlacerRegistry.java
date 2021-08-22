package com.finderfeed.solarforge.world_generation.features.foliage_placers;

import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantSmallTreeFoliagePlacer;
import com.finderfeed.solarforge.world_generation.dimension_related.radiant_land.RadiantTreeFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FoliagePlacerRegistry {

    public static final DeferredRegister<FoliagePlacerType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES,"solarforge");

    public static final RegistryObject<FoliagePlacerType<BurntTreeFoliagePlacer>> BURNT_TREE_FOLIAGE_PLACER_FOLIAGE_PLACER_TYPE = DEFERRED_REGISTER.register("burnt_tree_foliage",()->
            new FoliagePlacerType<>(BurntTreeFoliagePlacer.CODEC));

    public static final RegistryObject<FoliagePlacerType<RadiantTreeFoliagePlacer>> RADIANT_TREE_FOLIAGE_PLACER = DEFERRED_REGISTER.register("radiant_tree_foliage",()->
            new FoliagePlacerType<>(RadiantTreeFoliagePlacer.CODEC));

    public static final RegistryObject<FoliagePlacerType<RadiantSmallTreeFoliagePlacer>> RADIANT_TREE_SMALL_FOLIAGE_PLACER = DEFERRED_REGISTER.register("radiant_tree_small_foliage",()->
            new FoliagePlacerType<>(RadiantSmallTreeFoliagePlacer.CODEC));
}
