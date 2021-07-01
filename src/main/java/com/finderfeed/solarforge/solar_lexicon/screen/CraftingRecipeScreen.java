package com.finderfeed.solarforge.solar_lexicon.screen;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class CraftingRecipeScreen extends Screen {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/solar_lexicon_crafting_recipe_recipe.png");

    public final ShapedRecipe recipe;
    public int relX;
    public int relY;
    public List<ItemStack> stacks ;

    protected CraftingRecipeScreen(ShapedRecipe a) {
        super(new StringTextComponent(""));
        this.recipe = a;
    }


    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        stacks = new ArrayList<>();
        stacks.add(recipe.getResultItem());

        NonNullList<Ingredient> list = recipe.getIngredients();


        addButton(new ItemStackButton(relX+185,relY+187,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        super.init();
    }


    @Override
    public void render(MatrixStack matrices, int mousex, int mousey, float partialTicks) {
        minecraft.getTextureManager().bind(MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);

        super.render(matrices,mousex,mousey,partialTicks);
    }
}