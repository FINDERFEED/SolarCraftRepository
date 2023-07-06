package com.finderfeed.solarcraft.client.toasts;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class UnlockedEnergyTypeToast implements Toast {
    public static ResourceLocation LOC = new ResourceLocation("solarcraft","textures/gui/solar_forge_toasts.png");
    private RunicEnergy.Type type;

    public UnlockedEnergyTypeToast(RunicEnergy.Type type){
        this.type = type;
    }

    @Override
    public Visibility render(GuiGraphics graphics, ToastComponent cmp, long timer) {
        ClientHelpers.bindText(LOC);
        RenderingTools.blitWithBlend(graphics.pose(), 0, 0, 0, 32, this.width(), this.height(),256,256,0,1f);

        ClientHelpers.bindText(new ResourceLocation("solarcraft", "textures/misc/tile_energy_pylon_" + type.id + ".png"));
        RenderingTools.blitWithBlend(graphics.pose(),8,8,0,0,16,16,16,16,0,1f);

        graphics.drawCenteredString(cmp.getMinecraft().font,type.id.toUpperCase(Locale.ROOT),81,7,0xffffff);
        graphics.drawCenteredString(cmp.getMinecraft().font,Component.translatable("solarcraft.energy_type_unlocked"),81,15,0xffffff);


        if (timer <= 5000) {
            return Visibility.SHOW;
        }else{
            return Visibility.HIDE;
        }
    }


    public static void addOrUpdate(ToastComponent gui, RunicEnergy.Type type){

        gui.addToast(new UnlockedEnergyTypeToast(type));

    }
}
