package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class SmeltingRecipeScreen extends Screen {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/smelting_recipe_gui.png");
    public final SolarSmeltingRecipe recipe;
    public int relX;
    public int relY;
    public List<ItemStack> stacks ;

    protected SmeltingRecipeScreen(SolarSmeltingRecipe a) {
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
        stacks.add(recipe.list.get(0).getItems()[0]);
        stacks.add(recipe.list.get(1).getItems()[0]);
        stacks.add(recipe.list.get(2).getItems()[0]);
        stacks.add(recipe.list.get(3).getItems()[0]);
        stacks.add(recipe.output);


        addButton(new ItemStackButton(relX+185,relY+187,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        super.init();
    }


    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        minecraft.getTextureManager().bind(MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(0),relX+77,relY+111);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(1),relX+94,relY+111);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(2),relX+111,relY+111);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(3),relX+77,relY+128);

        minecraft.getItemRenderer().renderGuiItem(stacks.get(4),relX+94,relY+161);
        super.render(matrices,mousex,mousey,partialTicks);
    }
}
