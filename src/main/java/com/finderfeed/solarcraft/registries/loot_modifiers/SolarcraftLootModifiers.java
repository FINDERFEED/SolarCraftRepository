package com.finderfeed.solarcraft.registries.loot_modifiers;

import com.finderfeed.solarcraft.SolarCraft;

import com.finderfeed.solarcraft.content.loot_modifiers.AddRandomAncientFragmentToChests;
import com.finderfeed.solarcraft.content.loot_modifiers.SmeltingLootModifier;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;


public class SolarcraftLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SolarCraft.MOD_ID);

    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>,Codec<AddRandomAncientFragmentToChests>> ANCIENT_FRAGMENT_SERIALIZER =
            MODIFIERS.register("add_fragments",()->AddRandomAncientFragmentToChests.CODEC);
    public static final DeferredHolder<Codec<? extends IGlobalLootModifier>,Codec<SmeltingLootModifier>> SMELTING_SERIALIZER =
            MODIFIERS.register("smelting",()->SmeltingLootModifier.CODEC);

}
