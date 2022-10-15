package com.finderfeed.solarcraft.compat.crafttweaker;

//import com.blamejared.crafttweaker.api.CraftTweakerAPI;
//import com.blamejared.crafttweaker.api.command.CommandUtilities;
//import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
//import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
//import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;

//@CraftTweakerPlugin("solarcraft:default")
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
//        handler.registerDump("solarcraft_fragments", Component.translatable("solarcraft.command.description.fragment_dump"), builder -> {
//            builder.executes(context -> {
//                ServerPlayer player = context.getSource().getPlayerOrException();
//                CraftTweakerAPI.LOGGER.info("List of all known SolarForge Fragments ");
//                Arrays.stream(AncientFragment.getAllFragments()).map(AncientFragment::getId).map(it -> "- " + it).forEach(CraftTweakerAPI.LOGGER::info);
//                CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("solarcraft.command.dump.fragments")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
//
//                return Command.SINGLE_SUCCESS;
//            });
//        });
//        handler.registerDump("solarcraft_catalysts", Component.translatable("solarcraft.command.description.catalyst_blocks_dump"), builder ->{
//            builder.executes(context -> {
//                ServerPlayer player = context.getSource().getPlayerOrException();
//                CraftTweakerAPI.LOGGER.info("List of all known Catalyst Blocks ");
//                CraftTweakerSolarForgeCompatUtilities.getMap().keySet().stream().map(thing -> "<block:" + thing + ">").map(it -> "- " + it).forEach(CraftTweakerAPI.LOGGER::info);
//                CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("solarcraft.command.dump.catalysts")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
//                return Command.SINGLE_SUCCESS;
//            });
//        });
//        }
}
