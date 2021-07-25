package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class CraftingRecipeScreen extends Screen {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/solar_lexicon_crafting_recipe_recipe.png");

    public final ShapedRecipe recipe;
    public int relX;
    public int relY;
    public List<ItemStack> stacks ;

    protected CraftingRecipeScreen(ShapedRecipe a) {
        super(new TextComponent(""));
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


        addRenderableWidget(new ItemStackButton(relX+185,relY+187,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        super.init();
    }


    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        RenderSystem.setShaderTexture(0,MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);

        super.render(matrices,mousex,mousey,partialTicks);
    }
}