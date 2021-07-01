package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.misc_things.IScrollable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ScrollThings {

    @SubscribeEvent
    public static void listenToHotkeys(final InputEvent.KeyInputEvent event){
        if (Minecraft.getInstance().screen instanceof IScrollable){
            ((IScrollable) Minecraft.getInstance().screen).performScroll(event.getScanCode());
        }
    }

}
