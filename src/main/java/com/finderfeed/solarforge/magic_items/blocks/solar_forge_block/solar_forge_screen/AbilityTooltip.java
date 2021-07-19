package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

public class AbilityTooltip implements Button.ITooltip {

    public final ResourceLocation TOOLTIPS = new ResourceLocation("solarforge","textures/gui/tooltips_solarforge");
    public final int index;

    public AbilityTooltip(int index){
        this.index = index;
    }

    @Override
    public void onTooltip(Button button, MatrixStack matrices, int x, int y) {
        Minecraft.getInstance().textureManager.bind(TOOLTIPS);

    }
}
