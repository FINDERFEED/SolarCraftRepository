package com.finderfeed.solarcraft.client.toasts;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.Component;

public class ProgressionToast implements Toast {

    public Progression progression;

    public ProgressionToast(Progression a){
        this.progression = a;
    }


    public static ResourceLocation LOC = new ResourceLocation("solarcraft","textures/gui/solar_forge_toasts.png");

    @Override
    public Visibility render(GuiGraphics graphics, ToastComponent gui, long timer) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        Minecraft mc = gui.getMinecraft();

        ClientHelpers.bindText(LOC);

        RenderingTools.blitWithBlend(matrices, 0, 0, 0, 32, this.width(), this.height(),256,256,0,1f);
        graphics.renderItem(progression.getIcon(),8,8);
        graphics.drawString(Minecraft.getInstance().font, progression.getTranslation(),30,8,0xffffff);
        graphics.drawString(Minecraft.getInstance().font,Component.translatable("ach.completed"),30,17,0xffffff);
        matrices.popPose();
        if (timer <= 5000) {
            return Visibility.SHOW;
        }else{
            return Visibility.HIDE;
        }

    }



    public static void addOrUpdate(ToastComponent gui, Progression ach){

        gui.addToast(new ProgressionToast(ach));

    }
}
