package com.finderfeed.solarcraft.compat.jei.drawables;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class InfusingRecipeJEI implements IDrawable {

    public static final ResourceLocation LOC = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/jei_infusing_recipe.png");


    public InfusingRecipeJEI(){

    }


    @Override
    public int getWidth() {
        return 161;
    }

    @Override
    public int getHeight() {
        return 141;
    }


    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        PoseStack matrices = guiGraphics.pose();
        matrices.pushPose();
        ClientHelpers.bindText(LOC);
        RenderingTools.blitWithBlend(matrices,xOffset,yOffset,0,0,161,141,161,141,0,1f);

        matrices.popPose();
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        PoseStack matrices = guiGraphics.pose();
        matrices.pushPose();
        ClientHelpers.bindText(LOC);
        RenderingTools.blitWithBlend(matrices,0,0,0,0,161,141,161,141,0,1f);

        matrices.popPose();
    }

//    @Override
//    public void draw(PoseStack matrices, int x, int y) {
//        matrices.pushPose();
//        ClientHelpers.bindText(LOC);
//        Gui.blit(matrices,x,y,0,0,161,141,161,141);
//
//        matrices.popPose();
//    }


}
