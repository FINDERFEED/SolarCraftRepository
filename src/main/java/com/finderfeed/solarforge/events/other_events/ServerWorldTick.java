package com.finderfeed.solarforge.events.other_events;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerWorldTick {


    @SubscribeEvent
    public static void clientWorldTick(final TickEvent.WorldTickEvent event){


    }

}
