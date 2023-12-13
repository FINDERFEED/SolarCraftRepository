package com.finderfeed.solarcraft.content.commands;


import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarcraft")
public class ServerStartEvent {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event){
        SolarCraftCommands.register(event.getDispatcher());
    }
}
