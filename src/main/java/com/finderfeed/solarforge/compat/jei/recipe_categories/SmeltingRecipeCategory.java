package com.finderfeed.solarforge.compat.jei.recipe_categories;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.jei.JeiRecipeTypes;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public class SmeltingRecipeCategory implements IRecipeCategory<SolarSmeltingRecipe> {

    public IJeiHelpers helpers;

    public SmeltingRecipeCategory(IJeiHelpers helpers){
        this.helpers = helpers;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block.solarforge.solar_lens");
    }

    @Override
    public IDrawable getBackground() {
        return helpers.getGuiHelper().drawableBuilder(new ResourceLocation(SolarForge.MOD_ID,"textures/gui/jei_smelting_recipe.png"),0,0,173,62)
                .setTextureSize(173,62).build();
    }

    @Override
    public IDrawable getIcon() {
        return helpers.getGuiHelper().createDrawableIngredient(VanillaTypes.ITEM_STACK, ItemsRegister.SOLAR_LENS.get().getDefaultInstance());
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

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(SolarForge.MOD_ID,"solar_smelting");
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends SolarSmeltingRecipe> getRecipeClass() {
        return SolarSmeltingRecipe.class;
    }
}
