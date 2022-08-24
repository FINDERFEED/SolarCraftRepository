package com.finderfeed.solarforge.content.items.solar_lexicon.screen;

import com.finderfeed.solarforge.local_library.client.tooltips.AnimatedTooltip;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ItemStackButtonAnimatedTooltip extends ItemStackButton{

    private AnimatedTooltip tooltip;
    private boolean shouldRenderTooltip;

    public ItemStackButtonAnimatedTooltip(int x, int y, int xLoc, int yLoc, OnPress press, ItemStack stack, float scaleFactor) {
        super(x, y, xLoc, yLoc, press, stack, scaleFactor);
    }


    @Override
    public void renderButton(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        matrices.pushPose();

        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor,0);


        RenderSystem.enableBlend();

        if (tooltip != null && shouldRenderTooltip) {

            if (Minecraft.getInstance().screen instanceof PostRenderTooltips t) {
                t.addPostRenderTooltip(()->{
                    RenderSystem.disableDepthTest();
                    tooltip.render(matrices, this.x, this.y, partialTicks, mousex, mousey);
                    RenderSystem.enableDepthTest();
                });
            }else{
                RenderSystem.disableDepthTest();
                tooltip.render(matrices, this.x, this.y, partialTicks, mousex, mousey);
                RenderSystem.enableDepthTest();
            }
        }


        matrices.popPose();
    }


    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks,double zOffset){
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x + width,y + height);
        matrices.pushPose();

        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor,zOffset);


        RenderSystem.enableBlend();

        if (tooltip != null && shouldRenderTooltip) {

            if (Minecraft.getInstance().screen instanceof PostRenderTooltips t) {
                t.addPostRenderTooltip(()->{
                    RenderSystem.disableDepthTest();
                    tooltip.render(matrices, this.x, this.y, partialTicks, mousex, mousey);
                    RenderSystem.enableDepthTest();
                });
            }else{
                RenderSystem.disableDepthTest();
                tooltip.render(matrices, this.x, this.y, partialTicks, mousex, mousey);
                RenderSystem.enableDepthTest();
            }
        }


        matrices.popPose();
    }

    public void renderTooltip(PoseStack matrices, int mousex, int mousey, float partialTicks){
        matrices.pushPose();
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x+width,y+height);
        if (tooltip != null && shouldRenderTooltip) {

            if (Minecraft.getInstance().screen instanceof PostRenderTooltips t) {
                t.addPostRenderTooltip(()->{
                    RenderSystem.disableDepthTest();
                    tooltip.render(matrices, this.x, this.y, partialTicks, mousex, mousey);
                    RenderSystem.enableDepthTest();
                });
            }else{
                RenderSystem.disableDepthTest();
                tooltip.render(matrices, this.x, this.y, partialTicks, mousex, mousey);
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
