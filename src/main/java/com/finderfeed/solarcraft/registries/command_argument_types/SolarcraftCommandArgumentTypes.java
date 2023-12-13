package com.finderfeed.solarcraft.registries.command_argument_types;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.commands.SCAbilityArgument;
import com.finderfeed.solarcraft.content.commands.SolarCraftCommands;
import com.finderfeed.solarcraft.content.commands.SolarcraftStructureArgument;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class SolarcraftCommandArgumentTypes {

    public static final DeferredRegister<ArgumentTypeInfo<?,?>> ARGUMENT_TYPES =
            DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, SolarCraft.MOD_ID);

    public static final RegistryObject<ArgumentTypeInfo<?,?>> STRUCTURE_ARGUMENT = ARGUMENT_TYPES.register("structure_type",
            ()-> ArgumentTypeInfos.registerByClass(SolarcraftStructureArgument.class, SingletonArgumentInfo.contextFree(SolarcraftStructureArgument::new)));

    public static final RegistryObject<ArgumentTypeInfo<?,?>> ABILITY_ARGUMENT = ARGUMENT_TYPES.register("ability_type",
            ()-> ArgumentTypeInfos.registerByClass(SCAbilityArgument.class, SingletonArgumentInfo.contextFree(SCAbilityArgument::new)));

}
