package com.finderfeed.solarforge.compat.curios;

import com.finderfeed.solarforge.SolarForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;


public class Curios {

    public static void addCuriosSlots(InterModEnqueueEvent event){
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> {
                return new SlotTypeMessage.Builder("necklace").size(3).priority(2).build();
            });
    }

}
