package com.finderfeed.solarcraft.local_library.client.screens.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FDImageButton extends ImageButton {

    private FDButton.OnTooltip tooltip;
    public FDImageButton(int p_94256_, int p_94257_, int p_94258_, int p_94259_, int p_94260_, int p_94261_, int p_94262_, ResourceLocation p_94263_, int p_94264_, int p_94265_, OnPress p_94266_,FDButton.OnTooltip tooltip, Component p_94267_) {
        super(p_94256_, p_94257_, p_94258_, p_94259_, p_94260_, p_94261_, p_94262_, p_94263_, p_94264_, p_94265_, p_94266_, p_94267_);
        this.tooltip = tooltip;
    }


    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float p_282518_) {
        super.renderWidget(graphics, mx, my, p_282518_);
        if (this.isHovered){
            tooltip.renderTooltip(this,graphics,mx,my);
        }
    }
}
