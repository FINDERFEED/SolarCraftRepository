package com.finderfeed.solarforge.client.toasts;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.Progression;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.TranslatableComponent;

public class SolarAchievementToast implements Toast {

    public Progression progression;

    public SolarAchievementToast(Progression a){
        this.progression = a;
    }


    public static ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_forge_toasts.png");

    @Override
    public Visibility render(PoseStack matrices, ToastComponent gui, long timer) {
        Minecraft mc = gui.getMinecraft();

        ClientHelpers.bindText(LOC);

        gui.blit(matrices, 0, 0, 0, 32, this.width(), this.height());
        mc.getItemRenderer().renderGuiItem(progression.getIcon(),8,8);
        mc.font.draw(matrices, progression.getTranslation(),30,8,0xffffff);
        mc.font.draw(matrices,new TranslatableComponent("ach.completed"),30,17,0xffffff);
        if (timer <= 5000) {
            return Visibility.SHOW;
        }else{
            return Visibility.HIDE;
        }
    }



    public static void addOrUpdate(ToastComponent gui, Progression ach){

        gui.addToast(new SolarAchievementToast(ach));

    }
}
