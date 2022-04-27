package com.finderfeed.solarforge.compat.jei.recipe_categories;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.jei.drawables.InfusingRecipeJEI;
import com.finderfeed.solarforge.compat.jei.JeiRecipeTypes;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
        return new TranslatableComponent("block.solarforge.solar_infuser");
    }

    @Override
    public IDrawable getBackground() {

        return helpers.getGuiHelper().drawableBuilder(InfusingRecipeJEI.LOC,0,0,161,141).setTextureSize(161,141).build();
    }

    @Override
    public IDrawable getIcon() {
        return helpers.getGuiHelper().createDrawableIngredient(VanillaTypes.ITEM_STACK, SolarForge.INFUSER_ITEM.get().getDefaultInstance());
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfusingRecipe recipe, IFocusGroup focuses) {
        AncientFragment fragment = AncientFragment.getFragmentByID(recipe.fragID);
        if (fragment == null) return;
        if (!ProgressionHelper.doPlayerHasFragment(Minecraft.getInstance().player, fragment) && !Minecraft.getInstance().player.isCreative()) {
            builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(recipe.output.copy());
            return;
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < recipe.oneRowPattern.length();i++){
            char c = recipe.oneRowPattern.charAt(i);
            ingredients.add(recipe.INGR_MAP.get(c));
        }
        IRecipeSlotBuilder slotB = builder.addSlot(RecipeIngredientRole.INPUT,17,17);
        slotB.addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,63,10).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,109,17).addIngredients(ingredients.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT,37,37).addIngredients(ingredients.get(3));
        builder.addSlot(RecipeIngredientRole.INPUT,89,37).addIngredients(ingredients.get(4));
        builder.addSlot(RecipeIngredientRole.INPUT,10,63).addIngredients(ingredients.get(5));
        builder.addSlot(RecipeIngredientRole.INPUT,63,63).addIngredients(ingredients.get(6));
        builder.addSlot(RecipeIngredientRole.INPUT,116,63).addIngredients(ingredients.get(7));
        builder.addSlot(RecipeIngredientRole.INPUT,37,89).addIngredients(ingredients.get(8));
        builder.addSlot(RecipeIngredientRole.INPUT,89,89).addIngredients(ingredients.get(9));
        builder.addSlot(RecipeIngredientRole.INPUT,17,109).addIngredients(ingredients.get(10));
        builder.addSlot(RecipeIngredientRole.INPUT,63,116).addIngredients(ingredients.get(11));
        builder.addSlot(RecipeIngredientRole.INPUT,109,109).addIngredients(ingredients.get(12));
        builder.addSlot(RecipeIngredientRole.OUTPUT,136,63).addItemStack(recipe.output.copy());
    }

    @Override
    public void draw(InfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrices, double mouseX, double mouseY) {

        AncientFragment fragment = AncientFragment.getFragmentByID(recipe.fragID);
        if (fragment == null) return;
        if (!ProgressionHelper.doPlayerHasFragment(Minecraft.getInstance().player, fragment) && !Minecraft.getInstance().player.isCreative()) {
            RenderingTools.fill(matrices,5,5,161-5,141-5,0.3f,0,0.45f,1);
            int iter = 0;
            for (FormattedCharSequence s : Minecraft.getInstance().font.split(new TranslatableComponent("solarcraft.fragment_not_unlocked"),140)) {
                Gui.drawCenteredString(matrices, Minecraft.getInstance().font, s, 161 / 2, 141 / 2 + iter * 9 - 9, 0xffff00);
                iter++;
            }
            return;
        }
        if (RenderingTools.isMouseInBorders((int) mouseX, (int) mouseY, 68, 100, 68 + 6, 100 + 6)) {
            Screen screen = Minecraft.getInstance().screen;
            if (screen != null) {
                List<Component> components = new ArrayList<>();
                for (RunicEnergy.Type type : recipe.RUNIC_ENERGY_COST.getSetTypes()) {
                    float re = recipe.RUNIC_ENERGY_COST.get(type);
                    components.add(new TextComponent(type.id.toUpperCase(Locale.ROOT) + ": ").withStyle(ChatFormatting.GOLD)
                            .append(new TextComponent(re + "").withStyle(ChatFormatting.RESET)));
                }
                components.add(new TranslatableComponent("solarcraft.solar_energy").withStyle(ChatFormatting.GOLD)
                        .append(new TextComponent(": " + recipe.requriedEnergy).withStyle(ChatFormatting.RESET)));
                screen.renderTooltip(matrices, components, Optional.empty(), (int) mouseX, (int) mouseY - 70);
            }
        }


    }

    @Override
    public List<Component> getTooltipStrings(InfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }


    @Override
    public boolean isHandled(InfusingRecipe recipe) {
        return true;
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
