package com.finderfeed.solarcraft.local_library.client.screens.buttons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FDImageButton extends ImageButton {

    private FDButton.OnTooltip tooltip;

    public FDImageButton(int p_94256_, int p_94257_, int p_94258_, int p_94259_, WidgetSprites p_295874_, OnPress p_94266_, FDButton.OnTooltip tooltip) {
        super(p_94256_, p_94257_, p_94258_, p_94259_, p_295874_, p_94266_);
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
