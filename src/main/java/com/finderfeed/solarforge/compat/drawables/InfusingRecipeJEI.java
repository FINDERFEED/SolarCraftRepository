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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InfusingRecipeJEI implements IDrawable {

    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/jei_infusing_recipe.png");

    private final ItemStack output;
    private final List<ItemRator> itemRators = new ArrayList<>();
    private int solarEnergy;
    private RunicEnergyCost cost;
    private double mx;
    private double my;

    public InfusingRecipeJEI(InfusingRecipe recipe,double mousex,double mousey){
        this.output = recipe.output.copy();
        this.fillItemRators(recipe);
        this.cost = recipe.RUNIC_ENERGY_COST;
        this.solarEnergy = recipe.requriedEnergy;
        this.mx = mousex;
        this.my = mousey;
    }


    @Override
    public int getWidth() {
        return 188;
    }

    @Override
    public int getHeight() {
        return 188;
    }

    @Override
    public void draw(PoseStack matrices, int x, int y) {
        matrices.pushPose();
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        ClientHelpers.bindText(LOC);
        Gui.blit(matrices,x,y,0,0,188,188,188,188);

        renderer.renderGuiItem(output,x + 161,y + 30);
        matrices.popPose();
    }

    private void fillItemRators(InfusingRecipe currentRecipe){
        for (int i = 0; i < currentRecipe.oneRowPattern.length();i++){
            char c = currentRecipe.oneRowPattern.charAt(i);
            this.itemRators.add(new ItemRator(currentRecipe.INGR_MAP.get(c)));
        }
    }

}
