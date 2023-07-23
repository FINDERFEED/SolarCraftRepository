package com.finderfeed.solarcraft.compat.jei.recipe_categories;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.compat.jei.drawables.InfusingRecipeJEI;
import com.finderfeed.solarcraft.compat.jei.JeiRecipeTypes;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.*;

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
        return Component.translatable("block.solarcraft.solar_infuser");
    }

    @Override
    public IDrawable getBackground() {

        return helpers.getGuiHelper().drawableBuilder(InfusingRecipeJEI.LOC,0,0,161,141).setTextureSize(161,141).build();
    }

    @Override
    public IDrawable getIcon() {
        return helpers.getGuiHelper().createDrawableIngredient(VanillaTypes.ITEM_STACK, SolarcraftItems.INFUSER_ITEM.get().getDefaultInstance());
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfusingRecipe recipe, IFocusGroup focuses) {
        AncientFragment fragment = AncientFragment.getFragmentByID(recipe.fragID);
        if (fragment == null) return;
        if (!AncientFragmentHelper.doPlayerHasFragment(Minecraft.getInstance().player, fragment) && !Minecraft.getInstance().player.isCreative()) {
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
    public void draw(InfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {

        PoseStack matrices = graphics.pose();



        AncientFragment fragment = AncientFragment.getFragmentByID(recipe.fragID);
        if (fragment == null) return;
        if (!AncientFragmentHelper.doPlayerHasFragment(Minecraft.getInstance().player, fragment) && !Minecraft.getInstance().player.isCreative()) {
            RenderingTools.fill(matrices,5,5,161-5,141-5,0.3f,0,0.45f,1);
            int iter = 0;
            for (FormattedCharSequence s : Minecraft.getInstance().font.split(Component.translatable("solarcraft.fragment_not_unlocked"),140)) {
                graphics.drawCenteredString( Minecraft.getInstance().font, s, 161 / 2, 141 / 2 + iter * 9 - 9, 0xffff00);
                iter++;
            }

            return;
        }
        Screen screen = Minecraft.getInstance().screen;
        if (screen == null) return;
        if (RenderingTools.isMouseInBorders((int) mouseX, (int) mouseY, 68, 100, 68 + 6, 100 + 6)) {


            List<Component> components = new ArrayList<>();
            for (RunicEnergy.Type type : recipe.RUNIC_ENERGY_COST.getSetTypes()) {
                float re = recipe.RUNIC_ENERGY_COST.get(type);
                components.add(Component.literal(type.id.toUpperCase(Locale.ROOT) + ": ").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(re + "").withStyle(ChatFormatting.RESET)));
            }
            if (recipe.requriedEnergy != 0) {
                components.add(Component.translatable("solarcraft.solar_energy").withStyle(ChatFormatting.GOLD)
                        .append(Component.literal(": " + recipe.requriedEnergy).withStyle(ChatFormatting.RESET)));
            }
            if (!components.isEmpty()) {
                graphics.renderTooltip(Minecraft.getInstance().font, components, Optional.empty(), (int) mouseX, (int) mouseY - 40* Mth.clamp(components.size()/8,0,1));
            }
        }
        Block[] catalysts = recipe.deserializeCatalysts();
        if (catalysts == null) return;
        ClientHelpers.bindText(InfoButton.LOC);
        if (RenderingTools.isMouseInBorders((int) mouseX, (int) mouseY,137,15,137 + 12,15 + 12)){
            RenderingTools.blitWithBlend(matrices,137,15,0,12,12,12,12,24,0,1f);

            if (Helpers.hasPlayerCompletedProgression(Progression.CATALYSTS,Minecraft.getInstance().player)){
                /*
                012
              11    3
              10    4
               9    5
                876
                 */
                String cats = recipe.getCatalysts().replace(' ','-');
                List<Component> cmps = new ArrayList<>();
                cmps.add(Component.literal(" " + cats.substring(0,3) + " "));
                cmps.add(Component.literal(cats.charAt(11) + "   " + cats.charAt(3)));
                cmps.add(Component.literal(cats.charAt(10) + "   " + cats.charAt(4)));
                cmps.add(Component.literal(cats.charAt(9) + "   " + cats.charAt(5)));
                cmps.add(Component.literal(" " + cats.charAt(8) + cats.charAt(7) + cats.charAt(6) + " "));
                cmps.add(Component.translatable("solarcraft.catalysts_jei"));
                for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
                    cmps.add(Component.literal(InfusingRecipe.DESERIALIZATOR_RE_TO_CHARACTER[type.getIndex()] + ": " + type.id.toUpperCase(Locale.ROOT)));
                }
                graphics.renderTooltip(Minecraft.getInstance().font,cmps,Optional.empty(),(int)mouseX,(int)mouseY);
            }else{
                graphics.renderTooltip(Minecraft.getInstance().font,Minecraft.getInstance().font.split(Component.translatable("solarcraft.catalysts_not_unlocked"),100),
                        (int)mouseX,(int)mouseY);
            }
        }else{
            RenderingTools.blitWithBlend(matrices,137,15,0,0,12,12,12,24,0,1f);
        }

    }

//    @Override
//    public void draw(InfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrices, double mouseX, double mouseY) {
//
//        AncientFragment fragment = AncientFragment.getFragmentByID(recipe.fragID);
//        if (fragment == null) return;
//        if (!ProgressionHelper.doPlayerHasFragment(Minecraft.getInstance().player, fragment) && !Minecraft.getInstance().player.isCreative()) {
//            RenderingTools.fill(matrices,5,5,161-5,141-5,0.3f,0,0.45f,1);
//            int iter = 0;
//            for (FormattedCharSequence s : Minecraft.getInstance().font.split(Component.translatable("solarcraft.fragment_not_unlocked"),140)) {
//                Gui.drawCenteredString(matrices, Minecraft.getInstance().font, s, 161 / 2, 141 / 2 + iter * 9 - 9, 0xffff00);
//                iter++;
//            }
//            return;
//        }
//        Screen screen = Minecraft.getInstance().screen;
//        if (screen == null) return;
//        if (RenderingTools.isMouseInBorders((int) mouseX, (int) mouseY, 68, 100, 68 + 6, 100 + 6)) {
//
//
//            List<Component> components = new ArrayList<>();
//            for (RunicEnergy.Type type : recipe.RUNIC_ENERGY_COST.getSetTypes()) {
//                float re = recipe.RUNIC_ENERGY_COST.get(type);
//                components.add(Component.literal(type.id.toUpperCase(Locale.ROOT) + ": ").withStyle(ChatFormatting.GOLD)
//                        .append(Component.literal(re + "").withStyle(ChatFormatting.RESET)));
//            }
//            if (recipe.requriedEnergy != 0) {
//                components.add(Component.translatable("solarcraft.solar_energy").withStyle(ChatFormatting.GOLD)
//                        .append(Component.literal(": " + recipe.requriedEnergy).withStyle(ChatFormatting.RESET)));
//            }
//            if (!components.isEmpty()) {
//                screen.renderTooltip(matrices, components, Optional.empty(), (int) mouseX, (int) mouseY - 40* Mth.clamp(components.size()/8,0,1));
//            }
//        }
//        Block[] catalysts = recipe.deserializeCatalysts();
//        if (catalysts == null) return;
//        ClientHelpers.bindText(InfoButton.LOC);
//        if (RenderingTools.isMouseInBorders((int) mouseX, (int) mouseY,137,15,137 + 12,15 + 12)){
//            Gui.blit(matrices,137,15,0,12,12,12,12,24);
//
//            if (Helpers.hasPlayerCompletedProgression(Progression.CATALYSTS,Minecraft.getInstance().player)){
//                /*
//                012
//              11    3
//              10    4
//               9    5
//                876
//                 */
//                String cats = recipe.getCatalysts().replace(' ','-');
//                List<Component> cmps = new ArrayList<>();
//                cmps.add(Component.literal(" " + cats.substring(0,3) + " "));
//                cmps.add(Component.literal(cats.charAt(11) + "   " + cats.charAt(3)));
//                cmps.add(Component.literal(cats.charAt(10) + "   " + cats.charAt(4)));
//                cmps.add(Component.literal(cats.charAt(9) + "   " + cats.charAt(5)));
//                cmps.add(Component.literal(" " + cats.charAt(8) + cats.charAt(7) + cats.charAt(6) + " "));
//                cmps.add(Component.translatable("solarcraft.catalysts_jei"));
//                for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
//                    cmps.add(Component.literal(InfusingRecipe.DESERIALIZATOR_RE_TO_CHARACTER[type.getIndex()] + ": " + type.id.toUpperCase(Locale.ROOT)));
//                }
//                screen.renderTooltip(matrices,cmps,Optional.empty(),(int)mouseX,(int)mouseY);
//            }else{
//                screen.renderTooltip(matrices,Minecraft.getInstance().font.split(Component.translatable("solarcraft.catalysts_not_unlocked"),100),
//                        (int)mouseX,(int)mouseY);
//            }
//        }else{
//            Gui.blit(matrices,137,15,0,0,12,12,12,24);
//        }
//
//
//    }

    @Override
    public List<Component> getTooltipStrings(InfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        return IRecipeCategory.super.getTooltipStrings(recipe, recipeSlotsView, mouseX, mouseY);
    }


    @Override
    public boolean isHandled(InfusingRecipe recipe) {
        return true;
    }



}
