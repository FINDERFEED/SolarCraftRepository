package com.finderfeed.solarforge.compat.drawables;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.local_library.other.ItemRator;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InfusingRecipeJEI implements IDrawable {

    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/jei_infusing_recipe.png");


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
        MouseHandler handler = Minecraft.getInstance().mouseHandler;
        double mx = handler.xpos() - x;
        double my = handler.ypos() - y;
        Gui.blit(matrices,x,y,0,0,161,141,161,141);
        matrices.popPose();
    }


}
