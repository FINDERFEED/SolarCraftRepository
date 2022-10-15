package com.finderfeed.solarcraft.events.other_events.event_handler;


import com.finderfeed.solarcraft.compat.curios.Curios;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {



    @SubscribeEvent
    public static void interModComms(InterModEnqueueEvent event){
        if (ModList.get().isLoaded("curios")){
            Curios.addCuriosSlots(event);
        }
    }
}
