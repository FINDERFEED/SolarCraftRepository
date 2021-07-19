package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class InvokeScreenTest {
    public static void openScreen(){
        Minecraft mc = Minecraft.getInstance();
        ItemStack stack = mc.player.getMainHandItem();
        if (stack.getItem() instanceof SolarLexicon){
            SolarLexicon lexicon = (SolarLexicon) stack.getItem();
            if (lexicon.currentSavedScreen == null){
                mc.setScreen(new SolarLexiconScreen());
            }else{
                mc.setScreen(lexicon.currentSavedScreen);
                lexicon.currentSavedScreen = null;
            }

        }

    }
}
