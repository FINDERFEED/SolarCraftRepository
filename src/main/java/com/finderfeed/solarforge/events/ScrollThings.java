package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.misc_things.IScrollable;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.BookEntry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
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

    @SubscribeEvent
    public static void initMaps(final ClientPlayerNetworkEvent.LoggedInEvent event){


        AncientFragment.initFragmentsMap();
        BookEntry.initMap();
        if (event.getPlayer() != null) {
            event.getPlayer().sendMessage(new TranslationTextComponent("solarcraft.welcome_message"), event.getPlayer().getUUID());
            event.getPlayer().sendMessage(new TranslationTextComponent("solarcraft.welcome_message2"), event.getPlayer().getUUID());
            ProgressionHelper.initInfRecipesMap(event.getPlayer().level);
            ProgressionHelper.initSmeltingRecipesMap(event.getPlayer().level);
        }
    }
}
