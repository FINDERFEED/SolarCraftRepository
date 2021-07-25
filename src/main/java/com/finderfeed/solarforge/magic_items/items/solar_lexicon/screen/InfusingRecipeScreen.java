package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class InfusingRecipeScreen extends Screen {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/solar_lexicon_infusing_recipe.png");
    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarforge","textures/gui/energy_bar.png");
    public final InfusingRecipe recipe;
    public int relX;
    public int relY;
    public List<ItemStack> stacks ;

    protected InfusingRecipeScreen(InfusingRecipe a) {
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
        stacks.add(recipe.input1.getItems()[0]);
        stacks.add(recipe.input2.getItems()[0]);
        stacks.add(recipe.input3.getItems()[0]);
        stacks.add(recipe.input4.getItems()[0]);
        stacks.add(recipe.input5.getItems()[0]);
        stacks.add(recipe.input6.getItems()[0]);
        stacks.add(recipe.input7.getItems()[0]);
        stacks.add(recipe.input8.getItems()[0]);
        stacks.add(recipe.input9.getItems()[0]);
        stacks.add(recipe.output);


        addButton(new ItemStackButton(relX+185,relY+190,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        addButton(new ItemStackButton(relX+172,relY+190,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,false));
        super.init();
    }


    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {

        minecraft.getTextureManager().bind(MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);

        minecraft.getItemRenderer().renderGuiItem(stacks.get(0),relX+120,relY+69);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(1),relX+173,relY+69);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(2),relX+159,relY+30);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(3),relX+120,relY+16);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(4),relX+81,relY+30);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(5),relX+67,relY+69);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(6),relX+81,relY+108);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(7),relX+120,relY+122);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(8),relX+159,relY+108);
        minecraft.getItemRenderer().renderGuiItem(stacks.get(9),relX+23,relY+21);

        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("solarforge.seconds"),relX+30,relY+120,0xffffff);
        drawCenteredString(matrices, minecraft.font,new TextComponent(recipe.infusingTime / 20 +" ").append(new TranslatableComponent("solarforge.seconds2")),relX+30,relY+130,0xffffff);

//        Helpers.drawBoundedText(matrices,relX+10,relY+152,33,recipe.child.getItemDescription().getString());

        super.render(matrices,mousex,mousey,partialTicks);
        minecraft.getTextureManager().bind(REQ_ENERGY);
        matrices.translate(relX+36,relY+96,0);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        float percent = (float)recipe.requriedEnergy / 100000;
        blit(matrices,0,0,0,0,16,(int)(33*percent),16,33);
    }
}
