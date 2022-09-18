package com.finderfeed.solarforge.client.toasts;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class UnlockedEnergyTypeToast implements Toast {
    public static ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_forge_toasts.png");
    private RunicEnergy.Type type;

    public UnlockedEnergyTypeToast(RunicEnergy.Type type){
        this.type = type;
    }

    @Override
    public Visibility render(PoseStack matrices, ToastComponent cmp, long timer) {
        ClientHelpers.bindText(LOC);
        cmp.blit(matrices, 0, 0, 0, 32, this.width(), this.height());

        ClientHelpers.bindText(new ResourceLocation("solarforge", "textures/misc/tile_energy_pylon_" + type.id + ".png"));
        Gui.blit(matrices,8,8,0,0,16,16,16,16);

        Gui.drawCenteredString(matrices,cmp.getMinecraft().font,type.id.toUpperCase(Locale.ROOT),81,7,0xffffff);
        Gui.drawCenteredString(matrices,cmp.getMinecraft().font,new TranslatableComponent("solarcraft.energy_type_unlocked"),81,15,0xffffff);


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
