package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.capabilities.capability_mana.SolarForgeMana;
import com.finderfeed.solarforge.compat.curios.Curios;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {



    @SubscribeEvent
    public static void interModComms(InterModEnqueueEvent event){
        if (ModList.get().isLoaded("curios")){
            Curios.addCuriosSlots(event);
        }
    }
}
