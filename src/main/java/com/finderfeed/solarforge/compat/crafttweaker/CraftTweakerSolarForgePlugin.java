package com.finderfeed.solarforge.compat.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStack;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@CraftTweakerPlugin("solarforge:default")
public class CraftTweakerSolarForgePlugin implements ICraftTweakerPlugin {


    /**
     * Manages the registration of commands for the {@code /ct} main command.
     *
     * @param handler The handler responsible for registration.
     * @see ICommandRegistrationHandler
     * @since 9.1.0
     */
    @Override
    public void registerCommands(ICommandRegistrationHandler handler) {
        CraftTweakerUtilities.setupCatalystsMap();
        handler.registerDump("solarforge_fragments", new TranslatableComponent("solarforge.command.description.fragment_dump"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CraftTweakerAPI.LOGGER.info("List of all known SolarForge Fragments ");
                Arrays.stream(AncientFragment.getAllFragments()).map(AncientFragment::getId).map(it -> "- " + it).forEach(CraftTweakerAPI.LOGGER::info);
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("solarforge.command.dump.fragments")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);

                return Command.SINGLE_SUCCESS;
            });
        });
        handler.registerDump("solarforge_catalysts", new TranslatableComponent("solarforge.command.description.catalyst_blocks_dump"), builder ->{
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CraftTweakerAPI.LOGGER.info("List of all known Catalyst Blocks ");
                CraftTweakerUtilities.getMap().keySet().stream().map(thing -> "<block:" + thing + ">").map(it -> "- " + it).forEach(CraftTweakerAPI.LOGGER::info);
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("solarforge.command.dump.catalysts")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                return Command.SINGLE_SUCCESS;
            });
        });
        }
}
