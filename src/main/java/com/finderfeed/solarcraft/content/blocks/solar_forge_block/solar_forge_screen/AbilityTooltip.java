package com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;

public class AbilityTooltip implements FDButton.OnTooltip {

    public final ResourceLocation TOOLTIPS = new ResourceLocation("solarcraft","textures/gui/tooltips_solarforge");
    public final int index;

    public AbilityTooltip(int index){
        this.index = index;
    }

    @Override
    public void renderTooltip(Button button, GuiGraphics graphics, int x, int y) {

        ClientHelpers.bindText(TOOLTIPS);
    }
}
