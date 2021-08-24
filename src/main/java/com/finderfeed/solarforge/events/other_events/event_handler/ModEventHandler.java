package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.capabilities.capability_mana.SolarForgeMana;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event){
        event.register(SolarForgeMana.class);
    }
}
