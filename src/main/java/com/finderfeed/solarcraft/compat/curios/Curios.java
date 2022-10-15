package com.finderfeed.solarcraft.compat.curios;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import top.theillusivec4.curios.api.SlotTypeMessage;


public class Curios {

    public static void addCuriosSlots(InterModEnqueueEvent event){
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> {
                return new SlotTypeMessage.Builder("necklace").size(3).priority(2).build();
            });
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> {
            return new SlotTypeMessage.Builder("hands").size(1).priority(2).build();
        });
    }

}
