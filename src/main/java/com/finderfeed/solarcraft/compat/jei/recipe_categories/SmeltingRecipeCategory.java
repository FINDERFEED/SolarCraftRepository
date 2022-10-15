package com.finderfeed.solarcraft.compat.jei.recipe_categories;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.compat.jei.JeiRecipeTypes;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public class SmeltingRecipeCategory implements IRecipeCategory<SolarSmeltingRecipe> {

    public IJeiHelpers helpers;

    public SmeltingRecipeCategory(IJeiHelpers helpers){
        this.helpers = helpers;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.solarcraft.solar_lens");
    }

    @Override
    public IDrawable getBackground() {
        return helpers.getGuiHelper().drawableBuilder(new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/jei_smelting_recipe.png"),0,0,173,62)
                .setTextureSize(173,62).build();
    }

    @Override
    public IDrawable getIcon() {
        return helpers.getGuiHelper().createDrawableIngredient(VanillaTypes.ITEM_STACK, SolarcraftItems.SOLAR_LENS.get().getDefaultInstance());
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SolarSmeltingRecipe recipe, IFocusGroup focuses) {
        int iter = 0;
        for (ItemStack item : recipe.getStacks()){
            int posX = (iter % 3) * 17;
            int posY = (int)Math.floor((float)iter / 3) * 17;
            builder.addSlot(RecipeIngredientRole.INPUT,posX + 95,posY + 15).addItemStack(item.copy());
            iter++;
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT,150,24).addItemStack(recipe.output.copy());
    }

    @Override
    public void draw(SolarSmeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {

    }

    @Override
    public RecipeType<SolarSmeltingRecipe> getRecipeType() {
        return JeiRecipeTypes.SMELTING;
    }


}
