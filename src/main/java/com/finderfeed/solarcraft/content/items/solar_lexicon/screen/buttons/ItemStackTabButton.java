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
        float scaleFactor = width / 17f;
        float itemScale = (width - scaleFactor*5f) / 17f;
        RenderingTools.renderScaledGuiItemCentered(graphics,stack, x + width / 2f,  y + height / 2f,itemScale,300);

        RenderSystem.setShaderTexture(0,LOCATION);
        RenderSystem.enableBlend();
        if (!this.isHovered){
            RenderingTools.blitWithBlend(matrices,x,y ,0,0,width,height,width,height*2,0,1f);
        }else{
            RenderingTools.blitWithBlend(matrices,x,y ,0,height,width,height,width,height*2,0,1f);
        }
        matrices.popPose();
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mx, int my) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        super.renderTooltip(graphics, mx, my);
        matrices.popPose();
    }
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks, double zOffset){
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x + width,y + height);

        PoseStack matrices = graphics.pose();
        matrices.pushPose();

//        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        float scaleFactor = width / 17f;
        float itemScale = (width - scaleFactor*5f) / 17f;
//        RenderingTools.renderScaledGuiItemCentered(stack, x + width / 2f,  y + height / 2f,itemScale,0);

        RenderingTools.renderScaledGuiItemCentered(graphics,stack,(int) x + width/2f , (int) y + height/2f,itemScale,zOffset + 300);


        RenderSystem.setShaderTexture(0,LOCATION);
        RenderSystem.enableBlend();
        if (!this.isHovered){
            RenderingTools.blitWithBlend(matrices,x,y ,0,0,width,height,width,height*2,0,1f);
        }else{
            RenderingTools.blitWithBlend(matrices,x,y ,0,height,width,height,width,height*2,0,1f);
        }

        matrices.popPose();
        if (this.isHovered){
            matrices.pushPose();
            matrices.translate(0,0,100);
            this.renderTooltip(graphics,mousex,mousey);
            matrices.popPose();
        }
    }
}
