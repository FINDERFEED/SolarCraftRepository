package com.finderfeed.solarcraft.registries;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.loot_modifiers.custom_loot_conditions.SolarcraftModulePredicate;
import com.finderfeed.solarcraft.content.loot_modifiers.custom_loot_conditions.SolarcraftNBTPredicate;
import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.neoforged.neoforge.common.advancements.critereon.ICustomItemPredicate;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SCItemPredicates {


    public static final DeferredRegister<Codec<? extends ICustomItemPredicate>> PREDICATES = DeferredRegister.create(NeoForgeRegistries.ITEM_PREDICATE_SERIALIZERS, SolarCraft.MOD_ID);

    public static final DeferredHolder<Codec<? extends ICustomItemPredicate>,Codec<? extends ICustomItemPredicate>>
    NBT_PREDICATE = PREDICATES.register("solarcraft_predicate",()-> SolarcraftNBTPredicate.CODEC);
    public static final DeferredHolder<Codec<? extends ICustomItemPredicate>,Codec<? extends ICustomItemPredicate>>
    MODULE_PREDICATE = PREDICATES.register("solarcraft_module_predicate",()-> SolarcraftModulePredicate.CODEC);

}
