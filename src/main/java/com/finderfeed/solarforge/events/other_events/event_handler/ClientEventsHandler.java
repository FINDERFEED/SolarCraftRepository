package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.magic_items.items.ModuleItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ClientEventsHandler {


    @SubscribeEvent
    public static void renderModules(ItemTooltipEvent event){
        ModuleItem.applyHoverText(event.getItemStack(),event.getToolTip());
    }
}
