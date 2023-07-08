package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.local_library.client.tooltips.AnimatedTooltip;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ItemStackButtonAnimatedTooltip extends ItemStackButton {

    private AnimatedTooltip tooltip;
    private boolean shouldRenderTooltip;

    public ItemStackButtonAnimatedTooltip(int x, int y, int xLoc, int yLoc, OnPress press, ItemStack stack, float scaleFactor) {
        super(x, y, xLoc, yLoc, press, stack, scaleFactor);
    }


    @Override
    public void renderWidget(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();

        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(graphics,stack,(int) x, (int) y,scaleFactor,300);


        RenderSystem.enableBlend();

        if (tooltip != null && shouldRenderTooltip) {

            if (Minecraft.getInstance().screen instanceof PostRenderTooltips t) {
                t.addPostRenderTooltip(()->{
                    RenderSystem.disableDepthTest();
                    tooltip.render(graphics, this.x, this.y, partialTicks, mousex, mousey);
                    RenderSystem.enableDepthTest();
                });
            }else{
                RenderSystem.disableDepthTest();
                tooltip.render(graphics, this.x, this.y, partialTicks, mousex, mousey);
                RenderSystem.enableDepthTest();
            }
        }


        matrices.popPose();
    }


    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks,double zOffset){
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x + width,y + height);
        PoseStack matrices = graphics.pose();

        matrices.pushPose();

        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(graphics,stack,(int) x, (int) y,scaleFactor,zOffset);


        RenderSystem.enableBlend();

        if (tooltip != null && shouldRenderTooltip) {

            if (Minecraft.getInstance().screen instanceof PostRenderTooltips t) {
                t.addPostRenderTooltip(()->{
                    RenderSystem.disableDepthTest();
                    tooltip.render(graphics, this.x, this.y, partialTicks, mousex, mousey);
                    RenderSystem.enableDepthTest();
                });
            }else{
                RenderSystem.disableDepthTest();
                tooltip.render(graphics, this.x, this.y, partialTicks, mousex, mousey);
                RenderSystem.enableDepthTest();
            }
        }


        matrices.popPose();
    }

    public void renderTooltip(GuiGraphics graphics, int mousex, int mousey, float partialTicks){
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x+width,y+height);
        if (tooltip != null && shouldRenderTooltip) {

            if (Minecraft.getInstance().screen instanceof PostRenderTooltips t) {
                t.addPostRenderTooltip(()->{
                    RenderSystem.disableDepthTest();
                    tooltip.render(graphics, this.x, this.y, partialTicks, mousex, mousey);
                    RenderSystem.enableDepthTest();
                });
            }else{
                RenderSystem.disableDepthTest();
                tooltip.render(graphics, this.x, this.y, partialTicks, mousex, mousey);
                RenderSystem.enableDepthTest();
            }
        }
        matrices.popPose();
    }


    public void setTooltip(AnimatedTooltip tooltip) {
        this.tooltip = tooltip;
    }

    public void tick(){
        if (tooltip != null){
            if (!this.active) {
                this.shouldRenderTooltip = false;
                tooltip.tick(false);
                return;
            }
            tooltip.tick(isHovered);
            if (isHovered){
                shouldRenderTooltip = true;
            }else {
                shouldRenderTooltip = tooltip.getTicker() > 0;
            }
        }
    }
}
