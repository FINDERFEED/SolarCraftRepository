package com.finderfeed.solarcraft.client.tooltips;

import com.finderfeed.solarcraft.client.screens.EightElementsFragmentScreen;
import com.finderfeed.solarcraft.config.ItemREConfig;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.RETypeSelectionScreen;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.Locale;

public class REClientTooltipComponent implements ClientTooltipComponent {

    private RunicEnergyCost cost;

    public REClientTooltipComponent(RETooltipComponent tooltipComponent){
        this.cost = tooltipComponent.getCost();
    }



    @Override
    public void renderImage(Font font, int x, int y, PoseStack matrices, ItemRenderer renderer, int idk3) {
        matrices.pushPose();
        ClientHelpers.bindText(RETypeSelectionScreen.ALL_ELEMENTS_ID_ORDERED);
        int i = 0;
        for (RunicEnergy.Type type : cost.getSetTypes()){
            Gui.blit(matrices,x + i,y + 2,type.getIndex()*16,0,16,16,128,16);
            i += 18;
        }

        matrices.popPose();
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public int getWidth(Font font) {
        return cost.getSetTypes().size() * 20;
    }


}
