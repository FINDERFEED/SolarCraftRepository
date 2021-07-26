package com.finderfeed.solarforge.commands;


import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge")
public class ServerStartEvent {


    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event){
        CommandsSolarCraft.register(event.getDispatcher());
    }
}
