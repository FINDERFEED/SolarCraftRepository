package com.finderfeed.solarforge.content.items.solar_lexicon.screen;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class SmeltingRecipeScreen extends Screen {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/smelting_recipe_gui.png");
    public final SolarSmeltingRecipe recipe;
    public int relX;
    public int relY;
    public List<ItemStack> stacks ;

    public SmeltingRecipeScreen(SolarSmeltingRecipe a) {
        super(new TextComponent(""));
        this.recipe = a;
    }

    private void addStack(Ingredient ingr, List<ItemStack> stacks){
        if (!ingr.isEmpty()){
            stacks.add(ingr.getItems()[0]);
        }else{
            stacks.add(ItemStack.EMPTY);
        }
    }


    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        stacks = new ArrayList<>();

//        stacks.add(recipe.list.get(0).getItems()[0]);
//        stacks.add(recipe.list.get(1).getItems()[0]);
//        stacks.add(recipe.list.get(2).getItems()[0]);
//        stacks.add(recipe.list.get(3).getItems()[0]);
        stacks.add(recipe.output);


        addRenderableWidget(new ItemStackTabButton(relX+205,relY+9 + 20,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());},
                Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackTabButton(relX + 205,relY+27 + 20,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f));
        super.init();
    }


    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        ClientHelpers.bindText(MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);
//        renderItemAndTooltip(stacks.get(0),relX+77,relY+111,mousex,mousey,matrices,false);
//        renderItemAndTooltip(stacks.get(1),relX+94,relY+111,mousex,mousey,matrices,false);
//        renderItemAndTooltip(stacks.get(2),relX+111,relY+111,mousex,mousey,matrices,false);
//        renderItemAndTooltip(stacks.get(3),relX+77,relY+128,mousex,mousey,matrices,false);

        int iter = 0;

        for (ItemStack item : recipe.getStacks()){

            int posX = (iter % 3) * 17;
            int posY = (int)Math.floor((float)iter / 3) * 17;

            renderItemAndTooltip(item,relX + 77 + posX , relY + 118 + posY,mousex,mousey,matrices,false);

            iter++;
        }


        renderItemAndTooltip(recipe.getResultItem(),relX+94,relY+169,mousex,mousey,matrices,false);
        super.render(matrices,mousex,mousey,partialTicks);
    }
    private void renderItemAndTooltip(ItemStack toRender,int place1,int place2,int mousex,int mousey,PoseStack matrices,boolean last){
        ItemStack renderThis = toRender.copy();
        if (!last) {
            minecraft.getItemRenderer().renderGuiItem(toRender, place1, place2);
        }else{
            minecraft.getItemRenderer().renderGuiItem(renderThis, place1, place2);
        }
        minecraft.getItemRenderer().renderGuiItemDecorations(font,renderThis,place1,place2);


        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            renderTooltip(matrices,toRender,mousex,mousey);
            matrices.popPose();
        }
    }
}
