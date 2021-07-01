package com.finderfeed.solarforge.other_events;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerWorldTick {


    @SubscribeEvent
    public static void clientWorldTick(final TickEvent.WorldTickEvent event){


    }

}
