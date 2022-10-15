package com.finderfeed.solarcraft.registries.loot_modifiers;

import com.finderfeed.solarcraft.SolarCraft;

import com.finderfeed.solarcraft.content.loot_modifiers.AddRandomAncientFragmentToChests;
import com.finderfeed.solarcraft.content.loot_modifiers.SmeltingLootModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SolarcraftLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SolarCraft.MOD_ID);

    public static final RegistryObject<Codec<AddRandomAncientFragmentToChests>> ANCIENT_FRAGMENT_SERIALIZER =
            MODIFIERS.register("add_fragments",()->AddRandomAncientFragmentToChests.CODEC);
    public static final RegistryObject<Codec<SmeltingLootModifier>> SMELTING_SERIALIZER =
            MODIFIERS.register("smelting",()->SmeltingLootModifier.CODEC);

}
