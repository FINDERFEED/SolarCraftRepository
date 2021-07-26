package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;

public class AbilityTooltip implements Button.OnTooltip {

    public final ResourceLocation TOOLTIPS = new ResourceLocation("solarforge","textures/gui/tooltips_solarforge");
    public final int index;

    public AbilityTooltip(int index){
        this.index = index;
    }

    @Override
    public void onTooltip(Button button, PoseStack matrices, int x, int y) {

        ClientHelpers.bindText(TOOLTIPS);
    }
}
