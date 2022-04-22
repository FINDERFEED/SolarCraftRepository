package com.finderfeed.solarforge.compat.jei.recipe_categories;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.jei.JeiRecipeTypes;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfusingRecipeCategory implements IRecipeCategory<InfusingRecipe> {

    private IJeiHelpers helpers;

    public InfusingRecipeCategory(IJeiHelpers helpers){
        this.helpers = helpers;
    }


    @Override
    public RecipeType<InfusingRecipe> getRecipeType() {
        return JeiRecipeTypes.INFUSING_RECIPE;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Infuser");
    }

    @Override
    public IDrawable getBackground() {
        return helpers.getGuiHelper().createBlankDrawable(100,100);
    }

    @Override
    public IDrawable getIcon() {
        return helpers.getGuiHelper().createDrawableIngredient(VanillaTypes.ITEM_STACK, SolarForge.INFUSER_ITEM.get().getDefaultInstance());
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfusingRecipe recipe, IFocusGroup focuses) {
        IRecipeCategory.super.setRecipe(builder, recipe, focuses);
        IIngredientAcceptor<?> acceptor = builder.addInvisibleIngredients(RecipeIngredientRole.INPUT);
        for (Ingredient ingr : recipe.INGR_MAP.values()){
            acceptor.addIngredients(ingr);
        }

        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(recipe.output.copy());
    }

    @Override
    public void draw(InfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrices, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, matrices, mouseX, mouseY);
    }

    @Override
    public List<Component> getTooltipStrings(InfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }

    @Override
    public boolean handleInput(InfusingRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {
        return IRecipeCategory.super.handleInput(recipe, mouseX, mouseY, input);
    }

    @Override
    public boolean isHandled(InfusingRecipe recipe) {
        return IRecipeCategory.super.isHandled(recipe);
    }


    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(SolarForge.MOD_ID,"infusing_category");
    }

    @SuppressWarnings("removal")
    @Override
    public Class<? extends InfusingRecipe> getRecipeClass() {
        return InfusingRecipe.class;
    }
}
