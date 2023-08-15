package com.finderfeed.solarcraft.compat.jei.recipe_categories;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.compat.jei.JeiRecipeTypes;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.registries.items.SCItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.Item;

public class InfusingCraftingRecipeCategory implements IRecipeCategory<InfusingCraftingRecipe> {

    private IJeiHelpers helpers;

    public InfusingCraftingRecipeCategory(IJeiHelpers helpers){
        this.helpers = helpers;
    }

    @Override
    public RecipeType<InfusingCraftingRecipe> getRecipeType() {
        return JeiRecipeTypes.INFUSING_CRAFTING_RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.solarcraft.infusing_crafting_table");
    }

    @Override
    public IDrawable getBackground() {
        return helpers.getGuiHelper().drawableBuilder(new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/jei_infusing_crafting_recipe.png"),0,0,110,74)
                .setTextureSize(110,74).build();
    }

    @Override
    public IDrawable getIcon() {
        return helpers.getGuiHelper().createDrawableIngredient(VanillaTypes.ITEM_STACK, SCItems.INFUSING_TABLE.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfusingCraftingRecipe recipe, IFocusGroup focuses) {
        AncientFragment fragment = recipe.getFragment();
        if (fragment == null) return;
        if (!AncientFragmentHelper.doPlayerHasFragment(Minecraft.getInstance().player, fragment) && !Minecraft.getInstance().player.isCreative()){
            builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(recipe.getOutput().copy());
            return;
        }
        String[] pattern = recipe.getPattern();
        for (int i = 0; i < pattern.length;i++){
            String s = pattern[i];
            for (int g = 0; g < s.length();g++){
                Item stack = recipe.getDefinitions().get(s.charAt(g));
                if (stack != null){
                    builder.addSlot(RecipeIngredientRole.INPUT,11 + g * 18,11 + i * 18).addItemStack(stack.getDefaultInstance());
                }
            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT,84,29).addItemStack(recipe.getOutput().copy());
    }

    @Override
    public void draw(InfusingCraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        AncientFragment fragment = recipe.getFragment();
        if (fragment == null) return;
        if (!AncientFragmentHelper.doPlayerHasFragment(Minecraft.getInstance().player,fragment)){
            RenderingTools.fill(guiGraphics.pose(),6,6,104,68,0.3f,0,0.45f,1);
            int iter = 0;
            for (FormattedCharSequence charSequence : Minecraft.getInstance().font.split(Component.translatable("solarcraft.fragment_not_unlocked"),80)) {
                guiGraphics.drawCenteredString(Minecraft.getInstance().font,charSequence,110/2,74/2 + iter*9 - 14,0xffff00);
                iter++;
            }
        }
    }

//    @Override
//    public void draw(InfusingCraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
//        AncientFragment fragment = recipe.getFragment();
//        if (fragment == null) return;
//        if (!ProgressionHelper.doPlayerHasFragment(Minecraft.getInstance().player,fragment)){
//            RenderingTools.fill(stack,6,6,104,68,0.3f,0,0.45f,1);
//            int iter = 0;
//            for (FormattedCharSequence charSequence : Minecraft.getInstance().font.split(Component.translatable("solarcraft.fragment_not_unlocked"),80)) {
//                Gui.drawCenteredString(stack,Minecraft.getInstance().font,charSequence,110/2,74/2 + iter*9 - 14,0xffff00);
//                iter++;
//            }
//        }
//
//    }

}
