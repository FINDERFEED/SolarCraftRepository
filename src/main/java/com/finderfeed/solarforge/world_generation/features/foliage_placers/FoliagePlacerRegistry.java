package com.finderfeed.solarforge.world_generation.features.foliage_placers;

import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FoliagePlacerRegistry {

    public static final DeferredRegister<FoliagePlacerType<?>> DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES,"solarforge");

    public static final RegistryObject<FoliagePlacerType<BurntTreeFoliagePlacer>> BURNT_TREE_FOLIAGE_PLACER_FOLIAGE_PLACER_TYPE = DEFERRED_REGISTER.register("burnt_tree_foliage",()->
            new FoliagePlacerType<>(BurntTreeFoliagePlacer.CODEC));
}
