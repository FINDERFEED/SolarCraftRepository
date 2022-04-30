package com.finderfeed.solarforge.compat.jei.recipe_categories;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.jei.drawables.InfusingRecipeJEI;
import com.finderfeed.solarforge.compat.jei.JeiRecipeTypes;
import com.finderfeed.solarforge.local_library.client.tooltips.BlackBackgroundTooltip;
import com.finderfeed.solarforge.local_library.client.tooltips.StaticBlackBackgroundTooltip;
import com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents.ComponentSequence;
import com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents.ContentAlignment;
import com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents.CustomRenderComponent;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.magic.items.solar_lexicon.screen.InfoButton;
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
        Screen screen = Minecraft.getInstance().screen;
        if (screen == null) return;
        if (RenderingTools.isMouseInBorders((int) mouseX, (int) mouseY, 68, 100, 68 + 6, 100 + 6)) {


            List<Component> components = new ArrayList<>();
            for (RunicEnergy.Type type : recipe.RUNIC_ENERGY_COST.getSetTypes()) {
                float re = recipe.RUNIC_ENERGY_COST.get(type);
                components.add(new TextComponent(type.id.toUpperCase(Locale.ROOT) + ": ").withStyle(ChatFormatting.GOLD)
                        .append(new TextComponent(re + "").withStyle(ChatFormatting.RESET)));
            }
            if (recipe.requriedEnergy != 0) {
                components.add(new TranslatableComponent("solarcraft.solar_energy").withStyle(ChatFormatting.GOLD)
                        .append(new TextComponent(": " + recipe.requriedEnergy).withStyle(ChatFormatting.RESET)));
                screen.renderTooltip(matrices, components, Optional.empty(), (int) mouseX, (int) mouseY - 40);
            }
        }
        Block[] catalysts = recipe.deserializeCatalysts();
        if (catalysts == null) return;
        ClientHelpers.bindText(InfoButton.LOC);
        if (RenderingTools.isMouseInBorders((int) mouseX, (int) mouseY,137,15,137 + 12,15 + 12)){
            Gui.blit(matrices,137,15,0,12,12,12,12,24);

            if (Helpers.hasPlayerUnlocked(Progression.CATALYSTS,Minecraft.getInstance().player)){
                /*
                012
              11    3
              10    4
               9    5
                876
                 */
                String cats = recipe.getCatalysts();
                List<Component> cmps = new ArrayList<>();
                cmps.add(new TextComponent(" " + cats.substring(0,3) + " "));
                cmps.add(new TextComponent(cats.charAt(11) + "   " + cats.charAt(3)));
                cmps.add(new TextComponent(cats.charAt(10) + "   " + cats.charAt(4)));
                cmps.add(new TextComponent(cats.charAt(9) + "   " + cats.charAt(5)));
                cmps.add(new TextComponent(" " + cats.charAt(8) + cats.charAt(7) + cats.charAt(6) + " "));
                cmps.add(new TranslatableComponent("solarcraft.catalysts_jei"));
                for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
                    cmps.add(new TextComponent(InfusingRecipe.DESERIALIZATOR_RE_TO_CHARACTER[type.getIndex()] + ": " + type.id.toUpperCase(Locale.ROOT)));
                }
                screen.renderTooltip(matrices,cmps,Optional.empty(),(int)mouseX,(int)mouseY);
            }else{
                screen.renderTooltip(matrices,Minecraft.getInstance().font.split(new TranslatableComponent("solarcraft.catalysts_not_unlocked"),100),
                        (int)mouseX,(int)mouseY);
            }
        }else{
            Gui.blit(matrices,137,15,0,0,12,12,12,24);
        }


    }

//    private ComponentSequence.ComponentSequenceBuilder constructBuilder(Block[] catalysts){
//        ComponentSequence.ComponentSequenceBuilder builder = new ComponentSequence.ComponentSequenceBuilder();
//        builder.addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16*5,16*5,(matrix,x,y, pTicks,mX,mY,ticker,animationLength)->{
//            for (int i = 0; i < 3;i++){
//                Block b = catalysts[i];
//                if (b != null){
//                    RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b);
//                    ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                    Gui.blit(matrix,x + i*3,y,0,0,16,16,16,16);
//                }
//            }
//        })).nextLine();
//        builder.addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16*5,16*5,(matrix,x,y, pTicks,mX,mY,ticker,animationLength)->{
//            Block b3 = catalysts[3];
//            Block b11 = catalysts[11];
//            if (b3 != null){
//                RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b3);
//                ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                Gui.blit(matrix,x + 4*16,y,0,0,16,16,16,16);
//            }
//            if (b11 != null){
//                RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b11);
//                ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                Gui.blit(matrix,x ,y,0,0,16,16,16,16);
//            }
//        })).nextLine();
//        builder.addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16*5,16*5,(matrix,x,y, pTicks,mX,mY,ticker,animationLength)->{
//            Block b10 = catalysts[4];
//            Block b4 = catalysts[10];
//            if (b10 != null){
//                RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b10);
//                ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                Gui.blit(matrix,x + 4*16,y,0,0,16,16,16,16);
//            }
//            if (b4 != null){
//                RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b4);
//                ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                Gui.blit(matrix,x ,y,0,0,16,16,16,16);
//            }
//        })).nextLine();
//        builder.addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16*5,16*5,(matrix,x,y, pTicks,mX,mY,ticker,animationLength)->{
//            Block b5 = catalysts[5];
//            Block b9 = catalysts[9];
//            if (b5 != null){
//                RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b5);
//                ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                Gui.blit(matrix,x + 4*16,y,0,0,16,16,16,16);
//            }
//            if (b9 != null){
//                RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b9);
//                ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                Gui.blit(matrix,x ,y,0,0,16,16,16,16);
//            }
//        })).nextLine();
//        builder.addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16*5,16*5,(matrix,x,y, pTicks,mX,mY,ticker,animationLength)->{
//            int iter = 0;
//            for (int i = 8; i != 5;i--){
//                Block b = catalysts[i];
//                if (b != null){
//                    RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(b);
//                    ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
//                    Gui.blit(matrix,x + iter*3,y,0,0,16,16,16,16);
//                }
//                iter++;
//            }
//        })).build();
//        return builder;
//    }

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
