package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class InfusingRecipeScreen extends Screen {
    public final ResourceLocation BUTTONS = new ResourceLocation("solarforge","textures/misc/page_buttons.png");
    public final ResourceLocation MAIN_SCREEN_OPENED = new ResourceLocation("solarforge","textures/gui/solar_lexicon_infusing_recipe_with_catalysts.png");
    public final ResourceLocation MAIN_SCREEN_UNOPENED = new ResourceLocation("solarforge","textures/gui/solar_lexicon_infusing_recipe_without_catalysts.png");
    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarforge","textures/gui/energy_bar.png");
    //60*6
    private int[][] runicEnergySymbolsRenderPositions = new int[12][2];
    public final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation("solarforge","textures/gui/runic_energy_bar.png");
    public final List<InfusingRecipe> recipe;
    private int maxPages;
    private int currentPage = 0;
    public int relX;
    public int relY;
    public List<ItemStack> stacks ;
    private boolean catalystsUnlocked = false;

    public InfusingRecipeScreen(InfusingRecipe a) {
        super(new TextComponent(""));
        this.recipe = List.of(a);
        maxPages = 0;
    }
    public InfusingRecipeScreen(List<InfusingRecipe> a) {
        super(new TextComponent(""));
        this.recipe = a;
        maxPages = recipe.size()-1;
    }

    public InfusingRecipeScreen(List<InfusingRecipe> a,int page) {
        super(new TextComponent(""));
        this.recipe = a;
        maxPages = recipe.size()-1;
        this.currentPage = page;
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
        fillArray();
        stacks = new ArrayList<>();
        InfusingRecipe currentRecipe = recipe.get(currentPage);
        this.catalystsUnlocked = Helpers.hasPlayerUnlocked(Progression.CATALYSTS,Minecraft.getInstance().player);
        addStack(currentRecipe.input1,stacks);
        addStack(currentRecipe.input2,stacks);
        addStack(currentRecipe.input3,stacks);
        addStack(currentRecipe.input4,stacks);
        addStack(currentRecipe.input5,stacks);
        addStack(currentRecipe.input6,stacks);
        addStack(currentRecipe.input7,stacks);
        addStack(currentRecipe.input8,stacks);
        addStack(currentRecipe.input9,stacks);
        stacks.add(currentRecipe.output);

        if (maxPages != 0) {
            addRenderableWidget(new ImageButton(relX + 180, relY + 9, 16, 16, 0, 0, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage + 1 <= maxPages)) {
                    currentPage += 1;
                    InfusingRecipe newRecipe = recipe.get(currentPage);
                    stacks.clear();
                    addStack(newRecipe.input1,stacks);
                    addStack(newRecipe.input2,stacks);
                    addStack(newRecipe.input3,stacks);
                    addStack(newRecipe.input4,stacks);
                    addStack(newRecipe.input5,stacks);
                    addStack(newRecipe.input6,stacks);
                    addStack(newRecipe.input7,stacks);
                    addStack(newRecipe.input8,stacks);
                    addStack(newRecipe.input9,stacks);
//                    stacks.add(recipe.get(currentPage).input1.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input2.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input3.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input4.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input5.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input6.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input7.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input8.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input9.getItems()[0]);
                    stacks.add(newRecipe.output);
                }
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Next recipe"),mousex,mousey);
            },new TextComponent("")));
            addRenderableWidget(new ImageButton(relX + 164, relY + 9, 16, 16, 0, 16, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage - 1 >= 0)) {
                    currentPage -= 1;
                    InfusingRecipe newRecipe = recipe.get(currentPage);
                    stacks.clear();
                    addStack(newRecipe.input1,stacks);
                    addStack(newRecipe.input2,stacks);
                    addStack(newRecipe.input3,stacks);
                    addStack(newRecipe.input4,stacks);
                    addStack(newRecipe.input5,stacks);
                    addStack(newRecipe.input6,stacks);
                    addStack(newRecipe.input7,stacks);
                    addStack(newRecipe.input8,stacks);
                    addStack(newRecipe.input9,stacks);
//                    stacks.add(recipe.get(currentPage).input1.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input2.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input3.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input4.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input5.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input6.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input7.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input8.getItems()[0]);
//                    stacks.add(recipe.get(currentPage).input9.getItems()[0]);
                    stacks.add(newRecipe.output);
                }
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Previous recipe"),mousex,mousey);
            },new TextComponent("")));
        }

        //13
        int xoffs = 37-8;
        boolean d = false;
        for (int i = 0; i <= maxPages;i++){
            if (recipe.get(i).requriedEnergy != 0 || InfuserTileEntity.doRecipeRequiresRunicEnergy(recipe.get(i).RUNIC_ENERGY_COST)){
                d = true;
                break;
            }
        }
        if (d) {
            xoffs+=8;
            addRenderableWidget(new ItemStackButton(relX + 48 + xoffs, relY + 9, 12, 12, (button) -> {
                minecraft.setScreen(new InfusingRecipeEnergyScreen(recipe, currentPage));
            }, ItemsRegister.SOLAR_WAND.get().getDefaultInstance(), 0.7f, false));
        }
        addRenderableWidget(new ItemStackButton(relX+74+xoffs,relY+9,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        addRenderableWidget(new ItemStackButton(relX+61+xoffs,relY+9,12,12,(button)->{
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
        if (catalystsUnlocked) {
            ClientHelpers.bindText(MAIN_SCREEN_OPENED);
        }else{
            ClientHelpers.bindText(MAIN_SCREEN_UNOPENED);
        }
        blit(matrices,relX,relY,0,0,256,256);
        int xOffset = -25;
        int yOffset = 27;
        renderItemAndTooltip(stacks.get(0),relX+120 + xOffset,relY+69 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(1),relX+173 + xOffset,relY+69 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(2),relX+159 + xOffset,relY+30 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(3),relX+120 + xOffset,relY+16 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(4),relX+81 + xOffset,relY+30 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(5),relX+67 + xOffset,relY+69 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(6),relX+81 + xOffset,relY+108 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(7),relX+120 + xOffset,relY+122 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(8),relX+159 + xOffset,relY+108 + yOffset,mousex,mousey,matrices,false);
        renderItemAndTooltip(stacks.get(9),relX+120+xOffset ,relY+180 ,mousex,mousey,matrices,true);



//        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("solarforge.seconds"),relX+30,relY+120,0xffffff);
        drawCenteredString(matrices, minecraft.font,new TextComponent(recipe.get(currentPage).infusingTime / 20 +" ").append(new TranslatableComponent("solarforge.seconds2")),relX+104,relY+28,0xff0000);

//        Helpers.drawBoundedText(matrices,relX+10,relY+152,33,recipe.child.getItemDescription().getString());

        super.render(matrices,mousex,mousey,partialTicks);
//        ClientHelpers.bindText(REQ_ENERGY);
//        matrices.translate(relX+36,relY+96,0);
//        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
//        float percent = (float)recipe.get(currentPage).requriedEnergy / 100000;
//        blit(matrices,0,0,0,0,16,(int)(33*percent),16,33);
        matrices.popPose();


//        ClientHelpers.bindText(RUNIC_ENERGY_BAR);
//        renderEnergyBar(matrices,39,157, RunicEnergy.Type.ARDO);
//        renderEnergyBar(matrices,39,189, RunicEnergy.Type.URBA);
//        renderEnergyBar(matrices,124,189, RunicEnergy.Type.KELDA);
//        renderEnergyBar(matrices,124,173, RunicEnergy.Type.TERA);
//        renderEnergyBar(matrices,124,157, RunicEnergy.Type.ZETA);
//        renderEnergyBar(matrices,39,173, RunicEnergy.Type.FIRA);

        if (catalystsUnlocked) {
            matrices.pushPose();
            InfusingRecipe r = recipe.get(currentPage);
            if (r.getDeserializedCatalysts() != null) {
                int iterator = 0;
                for (int[] pos : runicEnergySymbolsRenderPositions) {
                    Block block = r.getDeserializedCatalysts()[iterator];
                    if (block != null) {
                        matrices.pushPose();
                        RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(block);
                        bindTypeTexture(type);
                        blit(matrices, pos[0], pos[1], 0, 0, 16, 16, 16, 16);
                        matrices.popPose();
                    }

                    iterator++;
                }
            }
            matrices.popPose();
        }

    }


    private void renderItemAndTooltip(ItemStack toRender,int place1,int place2,int mousex,int mousey,PoseStack matrices,boolean last){
        if (!last) {
            minecraft.getItemRenderer().renderGuiItem(toRender, place1, place2);
        }else{
            ItemStack renderThis = toRender.copy();
            renderThis.setCount(recipe.get(currentPage).count);
            minecraft.getItemRenderer().renderGuiItem(renderThis, place1, place2);
            minecraft.getItemRenderer().renderGuiItemDecorations(font,renderThis,place1,place2);
        }


        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            renderTooltip(matrices,toRender,mousex,mousey);
            matrices.popPose();
        }
    }

    private void bindTypeTexture(RunicEnergy.Type type){
        ClientHelpers.bindText(new ResourceLocation(SolarForge.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
    }

    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, RunicEnergy.Type type){
        matrices.pushPose();
        double energyCostPerItem = recipe.get(currentPage).RUNIC_ENERGY_COST.get(type);
        int xtexture =  ( Math.round( (float)energyCostPerItem/100000*60));
        blit(matrices,relX+offsetx,relY+offsety,0,0,xtexture,6);
        matrices.popPose();
    }

    private void fillArray(){
        runicEnergySymbolsRenderPositions[0][0] = relX+15;
        runicEnergySymbolsRenderPositions[0][1] = relY+54;

        runicEnergySymbolsRenderPositions[1][0] = relX+34;
        runicEnergySymbolsRenderPositions[1][1] = relY+35;

        runicEnergySymbolsRenderPositions[2][0] = relX+34+19;
        runicEnergySymbolsRenderPositions[2][1] = relY+16;

        runicEnergySymbolsRenderPositions[3][0] = relX+100+37;
        runicEnergySymbolsRenderPositions[3][1] = relY+16;

        runicEnergySymbolsRenderPositions[4][0] = relX+119+37;
        runicEnergySymbolsRenderPositions[4][1] = relY+35;

        runicEnergySymbolsRenderPositions[5][0] = relX+138+37;
        runicEnergySymbolsRenderPositions[5][1] = relY+54;

        runicEnergySymbolsRenderPositions[6][0] = relX+138+37;
        runicEnergySymbolsRenderPositions[6][1] = relY+150-12;

        runicEnergySymbolsRenderPositions[7][0] = relX+119+37;
        runicEnergySymbolsRenderPositions[7][1] = relY+169-12;

        runicEnergySymbolsRenderPositions[8][0] = relX+100+37;
        runicEnergySymbolsRenderPositions[8][1] = relY+188-12;

        runicEnergySymbolsRenderPositions[9][0] = relX+34+19;
        runicEnergySymbolsRenderPositions[9][1] = relY+188-12;

        runicEnergySymbolsRenderPositions[10][0] = relX+34;
        runicEnergySymbolsRenderPositions[10][1] = relY+169-12;

        runicEnergySymbolsRenderPositions[11][0] = relX+15;
        runicEnergySymbolsRenderPositions[11][1] = relY+150-12;
    }
}
