package com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ItemStackTabButton extends ItemStackButton {

    public static final ResourceLocation LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/tab_button.png");

    public ItemStackTabButton(int x, int y, int xLoc, int yLoc, OnPress press, ItemStack stack, float scaleFactor) {
        super(x, y, xLoc, yLoc, press, stack, scaleFactor);
    }

    public ItemStackTabButton(int x, int y, int xLoc, int yLoc, OnPress press, ItemStack stack, float scaleFactor, OnTooltip tooltip) {
        super(x, y, xLoc, yLoc, press, stack, scaleFactor, tooltip);
    }


    @Override
    public void renderWidget(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
//        if (this.isHovered){
//            this.renderTooltip(graphics,mousex,mousey);
//        }
//        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        float scaleFactor = width / 17f;
        float itemScale = (width - scaleFactor*5f) / 17f;
        RenderingTools.renderScaledGuiItemCentered(stack, x + width / 2f,  y + height / 2f,itemScale,0);

        RenderSystem.setShaderTexture(0,LOCATION);
        RenderSystem.enableBlend();
//        if (!this.isHovered){
//            Gui.blit(matrices,x - width/4,y - height/4,0,0,17,17,17,34);
//        }else{
//            Gui.blit(matrices,x - width/4,y - height/4,0,17,17,17,17,34);
//        }
        if (!this.isHovered){
            RenderingTools.blitWithBlend(matrices,x,y ,0,0,width,height,width,height*2,0,1f);
        }else{
            RenderingTools.blitWithBlend(matrices,x,y ,0,height,width,height,width,height*2,0,1f);
        }
        matrices.popPose();
    }

    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks, double zOffset){
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x + width,y + height);
//        matrices.pushPose();
//        if (this.isHovered){
//            this.renderToolTip(matrices,mousex,mousey);
//        }
////        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
//        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor,zOffset);
//
//        RenderSystem.setShaderTexture(0,LOCATION);
//        RenderSystem.enableBlend();
//        if (!this.isHovered){
//            Gui.blit(matrices, x, y,0,0,17,17,17,34);
//        }else{
//            Gui.blit(matrices, x, y,0,17,17,17,17,34);
//        }
//        matrices.popPose();
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        if (this.isHovered){
            this.renderTooltip(graphics,mousex,mousey);
        }
//        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        float scaleFactor = width / 17f;
        float itemScale = (width - scaleFactor*2f) / 17f;
        RenderingTools.renderScaledGuiItemCentered(stack, x + width / 2f,  y + height / 2f,itemScale,0);

        RenderSystem.setShaderTexture(0,LOCATION);
        RenderSystem.enableBlend();
//        if (!this.isHovered){
//            Gui.blit(matrices,x - width/4,y - height/4,0,0,17,17,17,34);
//        }else{
//            Gui.blit(matrices,x - width/4,y - height/4,0,17,17,17,17,34);
//        }
        if (!this.isHovered){
            RenderingTools.blitWithBlend(matrices,x,y ,0,0,width,height,width,height*2,0,1f);
        }else{
            RenderingTools.blitWithBlend(matrices,x,y ,0,height,width,height,width,height*2,0,1f);
        }
        matrices.popPose();

    }
}
