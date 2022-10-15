package com.finderfeed.solarcraft.compat.jei.drawables;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.Gui;
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
    public void draw(PoseStack matrices, int x, int y) {
        matrices.pushPose();
        ClientHelpers.bindText(LOC);
        Gui.blit(matrices,x,y,0,0,161,141,161,141);

        matrices.popPose();
    }


}
