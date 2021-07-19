package com.finderfeed.solarforge.events.other_events;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.UldoradiumOre;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class Tooltips {

    @SubscribeEvent
    public static void renderTooltip(final ItemTooltipEvent event){
        if (event.getItemStack().getItem().equals(ItemsRegister.ULDORADIUM_ORE.get()) && false){
            UldoradiumOre block = BlocksRegistry.ULDORADIUM_ORE.get();
            if (Helpers.hasPlayerUnlocked(block.getRequiredProgression(), ClientHelpers.getClientPlayer())) {
                event.getToolTip().set(0, new TranslationTextComponent(block.getDescriptionId()));
            }else{
                event.getToolTip().set(0, new TranslationTextComponent(block.getLockedBlock().getDescriptionId()));
            }

        }

    }
}
