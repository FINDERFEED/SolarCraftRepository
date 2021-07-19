package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class SolarAchievementToast implements IToast {

    public Achievement achievement;

    public SolarAchievementToast(Achievement a){
        this.achievement = a;
    }

    @Override
    public Visibility render(MatrixStack matrices, ToastGui gui, long timer) {
        Minecraft mc = gui.getMinecraft();
        mc.getTextureManager().bind(new ResourceLocation("solarforge","textures/gui/solar_forge_toasts.png"));

        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        gui.blit(matrices, 0, 0, 0, 32, this.width(), this.height());
        mc.getItemRenderer().renderGuiItem(achievement.getIcon(),8,8);
        mc.font.draw(matrices,achievement.getTranslation(),30,8,0xffffff);
        mc.font.draw(matrices,new TranslationTextComponent("ach.completed"),30,17,0xffffff);
        if (timer <= 5000) {
            return Visibility.SHOW;
        }else{
            return Visibility.HIDE;
        }
    }

    public static void addOrUpdate(ToastGui gui, Achievement ach){

        gui.addToast(new SolarAchievementToast(ach));

    }
}
