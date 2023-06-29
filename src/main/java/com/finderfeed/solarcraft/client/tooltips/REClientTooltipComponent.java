package com.finderfeed.solarcraft.client.tooltips;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.RETypeSelectionScreen;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class REClientTooltipComponent implements ClientTooltipComponent {

    private RunicEnergyCost cost;

    public REClientTooltipComponent(RETooltipComponent tooltipComponent){
        this.cost = tooltipComponent.getCost();
    }



    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        graphics.pose().pushPose();
        ClientHelpers.bindText(RETypeSelectionScreen.ALL_ELEMENTS_ID_ORDERED);
        int i = 0;
        for (RunicEnergy.Type type : cost.getSetTypes()){
            RenderingTools.blitWithBlend(graphics.pose(),x + i,y + 2,type.getIndex()*16,0,16,16,128,16,0,1f);
            i += 18;
        }

        graphics.pose().popPose();
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
