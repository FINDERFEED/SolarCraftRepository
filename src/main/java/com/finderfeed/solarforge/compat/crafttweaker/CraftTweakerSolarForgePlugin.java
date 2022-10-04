package com.finderfeed.solarforge.compat.crafttweaker;

//import com.blamejared.crafttweaker.api.CraftTweakerAPI;
//import com.blamejared.crafttweaker.api.command.CommandUtilities;
//import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
//import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
//import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;

//@CraftTweakerPlugin("solarforge:default")
public class CraftTweakerSolarForgePlugin /*implements ICraftTweakerPlugin*/ {

//
//    /**
//     * Manages the registration of commands for the {@code /ct} main command.
//     *
//     * @param handler The handler responsible for registration.
//     * @see ICommandRegistrationHandler
//     * @since 9.1.0
//     */
//    @Override
//    public void registerCommands(ICommandRegistrationHandler handler) {
//        CraftTweakerSolarForgeCompatUtilities.setupCatalystsMap();
//        handler.registerDump("solarforge_fragments", Component.translatable("solarforge.command.description.fragment_dump"), builder -> {
//            builder.executes(context -> {
//                ServerPlayer player = context.getSource().getPlayerOrException();
//                CraftTweakerAPI.LOGGER.info("List of all known SolarForge Fragments ");
//                Arrays.stream(AncientFragment.getAllFragments()).map(AncientFragment::getId).map(it -> "- " + it).forEach(CraftTweakerAPI.LOGGER::info);
//                CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("solarforge.command.dump.fragments")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
//
//                return Command.SINGLE_SUCCESS;
//            });
//        });
//        handler.registerDump("solarforge_catalysts", Component.translatable("solarforge.command.description.catalyst_blocks_dump"), builder ->{
//            builder.executes(context -> {
//                ServerPlayer player = context.getSource().getPlayerOrException();
//                CraftTweakerAPI.LOGGER.info("List of all known Catalyst Blocks ");
//                CraftTweakerSolarForgeCompatUtilities.getMap().keySet().stream().map(thing -> "<block:" + thing + ">").map(it -> "- " + it).forEach(CraftTweakerAPI.LOGGER::info);
//                CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("solarforge.command.dump.catalysts")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
//                return Command.SINGLE_SUCCESS;
//            });
//        });
//        }
}
