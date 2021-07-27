package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.TranslatableComponent;

import net.minecraft.client.gui.components.toasts.Toast.Visibility;

public class SolarAchievementToast implements Toast {

    public Achievement achievement;

    public SolarAchievementToast(Achievement a){
        this.achievement = a;
    }


    public static ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_forge_toasts.png");

    @Override
    public Visibility render(PoseStack matrices, ToastComponent gui, long timer) {
        Minecraft mc = gui.getMinecraft();

        ClientHelpers.bindText(LOC);

        gui.blit(matrices, 0, 0, 0, 32, this.width(), this.height());
        mc.getItemRenderer().renderGuiItem(achievement.getIcon(),8,8);
        mc.font.draw(matrices,achievement.getTranslation(),30,8,0xffffff);
        mc.font.draw(matrices,new TranslatableComponent("ach.completed"),30,17,0xffffff);
        if (timer <= 5000) {
            return Visibility.SHOW;
        }else{
            return Visibility.HIDE;
        }
    }



    public static void addOrUpdate(ToastComponent gui, Achievement ach){

        gui.addToast(new SolarAchievementToast(ach));

    }
}
