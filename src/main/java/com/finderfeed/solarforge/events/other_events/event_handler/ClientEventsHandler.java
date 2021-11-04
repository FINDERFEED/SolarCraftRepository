package com.finderfeed.solarforge.events.other_events.event_handler;


import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.events.my_events.MyColorEvent;
import com.finderfeed.solarforge.events.my_events.PostColorEvent;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.items.ModuleItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
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


