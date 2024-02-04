package com.finderfeed.solarcraft.local_library.client.screens.buttons;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public abstract class FDButton extends Button {

    private OnTooltip tooltip;


    protected FDButton(int p_259075_, int p_259271_, int p_260232_, int p_260028_, Component p_259351_) {
        super(p_259075_, p_259271_, p_260232_, p_260028_, p_259351_, (btn)->{}, DEFAULT_NARRATION);
    }
    protected FDButton(int p_259075_, int p_259271_, int p_260232_, int p_260028_, Component p_259351_,OnPress press) {
        super(p_259075_, p_259271_, p_260232_, p_260028_, p_259351_, press, DEFAULT_NARRATION);
    }

    protected FDButton(int p_259075_, int p_259271_, int p_260232_, int p_260028_, Component p_259351_,OnPress press,OnTooltip tooltip) {
        super(p_259075_, p_259271_, p_260232_, p_260028_, p_259351_, press, DEFAULT_NARRATION);
        this.tooltip = tooltip;
    }


    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float pticks) {
        super.renderWidget(graphics, mx, my, pticks);
    }


    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pticks) {
        if (this.visible) {
            this.isHovered = RenderingTools.isMouseInBorders(mx,my,x,y,x + width,y + height);
            this.renderWidget(graphics,mx,my,pticks);
            if (this.isHovered()) {
                this.renderTooltip(graphics,mx,my);
            }
        }
    }

    protected void renderTooltip(GuiGraphics graphics, int mx, int my){
        if (this.tooltip != null){
            graphics.pose().pushPose();
            this.tooltip.renderTooltip(this,graphics,mx,my);
            graphics.pose().popPose();
        }
    }


    protected int getYImage() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHovered) {
            i = 2;
        }

        return i;
    }

    @FunctionalInterface
    public interface OnTooltip{
        void renderTooltip(Button button,GuiGraphics graphics, int mouseX,int mouseY);
    }
}
