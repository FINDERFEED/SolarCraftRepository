package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
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
    public final ResourceLocation BUTTONS = new ResourceLocation("solarforge","textures/misc/page_buttons.png");
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/solar_lexicon_infusing_recipe.png");
    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarforge","textures/gui/energy_bar.png");
    //60*6
    public final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation("solarforge","textures/gui/runic_energy_bar.png");
    public final List<InfusingRecipe> recipe;
    private int maxPages;
    private int currentPage;
    public int relX;
    public int relY;
    public List<ItemStack> stacks ;

    protected InfusingRecipeScreen(InfusingRecipe a) {
        super(new TextComponent(""));
        this.recipe = List.of(a);
        maxPages = recipe.size()-1;
    }
    protected InfusingRecipeScreen(List<InfusingRecipe> a) {
        super(new TextComponent(""));
        this.recipe = a;
        maxPages = recipe.size()-1;
    }

    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        currentPage = 0;
        stacks = new ArrayList<>();
        stacks.add(recipe.get(0).input1.getItems()[0]);
        stacks.add(recipe.get(0).input2.getItems()[0]);
        stacks.add(recipe.get(0).input3.getItems()[0]);
        stacks.add(recipe.get(0).input4.getItems()[0]);
        stacks.add(recipe.get(0).input5.getItems()[0]);
        stacks.add(recipe.get(0).input6.getItems()[0]);
        stacks.add(recipe.get(0).input7.getItems()[0]);
        stacks.add(recipe.get(0).input8.getItems()[0]);
        stacks.add(recipe.get(0).input9.getItems()[0]);
        stacks.add(recipe.get(0).output);

        if (maxPages != 0) {
            addRenderableWidget(new ImageButton(relX + 180, relY + 9, 16, 16, 0, 0, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage + 1 <= maxPages)) {
                    currentPage += 1;
                    stacks.clear();
                    stacks.add(recipe.get(currentPage).input1.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input2.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input3.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input4.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input5.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input6.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input7.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input8.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input9.getItems()[0]);
                    stacks.add(recipe.get(currentPage).output);
                }
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Next recipe"),mousex,mousey);
            },new TextComponent("")));
            addRenderableWidget(new ImageButton(relX + 164, relY + 9, 16, 16, 0, 16, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage - 1 >= 0)) {
                    currentPage -= 1;
                    stacks.clear();
                    stacks.add(recipe.get(currentPage).input1.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input2.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input3.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input4.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input5.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input6.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input7.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input8.getItems()[0]);
                    stacks.add(recipe.get(currentPage).input9.getItems()[0]);
                    stacks.add(recipe.get(currentPage).output);
                }
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Previous recipe"),mousex,mousey);
            },new TextComponent("")));
        }


        addRenderableWidget(new ItemStackButton(relX+185,relY+9,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        addRenderableWidget(new ItemStackButton(relX+172,relY+9,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,false));
        super.init();
    }


    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        matrices.pushPose();
        ClientHelpers.bindText(MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);

        renderItemAndTooltip(stacks.get(0),relX+120,relY+69,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(1),relX+173,relY+69,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(2),relX+159,relY+30,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(3),relX+120,relY+16,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(4),relX+81,relY+30,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(5),relX+67,relY+69,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(6),relX+81,relY+108,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(7),relX+120,relY+122,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(8),relX+159,relY+108,mousex,mousey,matrices);
        renderItemAndTooltip(stacks.get(9),relX+23,relY+21,mousex,mousey,matrices);



        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("solarforge.seconds"),relX+30,relY+120,0xffffff);
        drawCenteredString(matrices, minecraft.font,new TextComponent(recipe.get(currentPage).infusingTime / 20 +" ").append(new TranslatableComponent("solarforge.seconds2")),relX+30,relY+130,0xffffff);

//        Helpers.drawBoundedText(matrices,relX+10,relY+152,33,recipe.child.getItemDescription().getString());

        super.render(matrices,mousex,mousey,partialTicks);
        ClientHelpers.bindText(REQ_ENERGY);
        matrices.translate(relX+36,relY+96,0);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        float percent = (float)recipe.get(currentPage).requriedEnergy / 100000;
        blit(matrices,0,0,0,0,16,(int)(33*percent),16,33);
        matrices.popPose();


        ClientHelpers.bindText(RUNIC_ENERGY_BAR);
        renderEnergyBar(matrices,39,157, RunicEnergy.Type.ARDO);
        renderEnergyBar(matrices,39,189, RunicEnergy.Type.URBA);
        renderEnergyBar(matrices,124,189, RunicEnergy.Type.KELDA);
        renderEnergyBar(matrices,124,173, RunicEnergy.Type.TERA);
        renderEnergyBar(matrices,124,157, RunicEnergy.Type.ZETA);
        renderEnergyBar(matrices,39,173, RunicEnergy.Type.FIRA);
    }


    private void renderItemAndTooltip(ItemStack toRender,int place1,int place2,int mousex,int mousey,PoseStack matrices){
        minecraft.getItemRenderer().renderGuiItem(toRender,place1,place2);
        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            renderTooltip(matrices,toRender,mousex,mousey);
            matrices.popPose();
        }
    }

    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, RunicEnergy.Type type){
        matrices.pushPose();
        double energyCostPerItem = recipe.get(currentPage).RUNIC_ENERGY_COST.get(type);
        int xtexture =  ( Math.round( (float)energyCostPerItem/100000*60));
        blit(matrices,relX+offsetx,relY+offsety,0,0,xtexture,6);
        matrices.popPose();
    }
}
