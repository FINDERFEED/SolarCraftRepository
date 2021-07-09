package com.finderfeed.solarforge.solar_lexicon.screen;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.IScrollable;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.Items;
import net.minecraft.item.MinecartItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class SolarLexiconRecipesScreen extends Screen implements IScrollable {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/solar_lexicon_recipes_page.png");
    public final ResourceLocation FRAME = new ResourceLocation("solarforge","textures/misc/frame.png");
    public final ResourceLocation MAIN_SCREEN_SCROLLABLE = new ResourceLocation("solarforge","textures/gui/solar_lexicon_main_page_scrollablet.png");



    public IItemHandler handler;
    public final ItemStackButton goBack = new ItemStackButton(0,10,12,12,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f,false);
    public final ItemStackButton nothing = new ItemStackButton(0,10,12,12,(button)->{}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false);
    public Point armorCategory;
    public Point magicItemsCategory;

    public int armorRecipeCount;
    public int magicRecipesCount;

    public Point magicMaterialsCategory;
    public int magicMaterialsCount;

    public Point magicToolsCategory;
    public int magicToolsCount;

    public Point smeltingCategory;
    public int smeltingRecipeCount;

    public Point upgradingRecipesCategory;
    public int upgradingRecipesCount;



    public Point structures;
    public int structuresCount;
    public int scrollX;
    public int scrollY;
    public int prevscrollX;
    public int prevscrollY;
    public int relX;
    public int relY;

    @Override
    public void performScroll(int keyCode) {
        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_LEFT) && !(scrollX -4 < -400)){
            scrollX-=4;
        } else if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP) && !(scrollY -4 < -138)){
            scrollY-=4;
        }else if(keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN) && !(scrollY +4 > 0)){
            scrollY+=4;
        }else if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT)&& !(scrollX +4 > 0)){
            scrollX+=4;
        }

        if (this.prevscrollX != scrollX){
            List<Widget> list = this.buttons;
            list.remove(goBack);
            list.remove(nothing);
            for (Widget a : list) {
                if (prevscrollX < scrollX) {
                    a.x += 4;
                } else {
                    a.x -= 4;
                }

            }
            this.prevscrollX = scrollX;
        }
        if (this.prevscrollY != scrollY){
            List<Widget> list = this.buttons;
            list.remove(goBack);
            list.remove(nothing);
            for (Widget a : list) {
                if (prevscrollY < scrollY) {

                    a.y += 4;
                } else {

                    a.y -= 4;
                }


            }
            this.prevscrollY = scrollY;
        }
    }

    protected SolarLexiconRecipesScreen() {
        super(new StringTextComponent(""));
    }


    @Override
    protected void init() {
        super.init();
        handler = getLexiconInventory();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        scrollX = 0;
        scrollY = 0;
        prevscrollX = 0;
        prevscrollY = 0;
        armorCategory = new Point(relX+20,relY+40);
        armorRecipeCount = 0;

        magicItemsCategory = new Point(relX+20,relY+150);
        magicRecipesCount = 0;

        magicMaterialsCategory = new Point(relX+20,relY+260);
        magicMaterialsCount = 0;

        magicToolsCategory = new Point(relX+180,relY+40);
        magicToolsCount = 0;

        smeltingCategory = new Point(relX+180,relY+150);
        smeltingRecipeCount = 0;

        structures = new Point(relX+180,relY+260);
        structuresCount = 0;

        upgradingRecipesCategory = new Point(relX+360,relY+40);
        upgradingRecipesCount = 0;

        List<InfusingRecipe> recipe = minecraft.level.getRecipeManager().getAllRecipesFor(SolarForge.INFUSING_RECIPE_TYPE);
        List<SolarSmeltingRecipe> recipeSmelt = minecraft.level.getRecipeManager().getAllRecipesFor(SolarForge.SOLAR_SMELTING);
        for (InfusingRecipe a :recipe){
            if (a.category.equals("solar_category.armor")){
                if (minecraft.player.getPersistentData().getBoolean(a.child)){
                    addButton(new ItemStackButton(armorCategory.x+armorRecipeCount*25,armorCategory.y + (int)Math.floor(((double)armorRecipeCount/6))*25,24,24,(button)->{
                        minecraft.setScreen(new InfusingRecipeScreen(a));
                    },a.output,1.5f,false));
                    armorRecipeCount++;

                }
            }else if (a.category.equals("solar_category.magic_items")){
                if (minecraft.player.getPersistentData().getBoolean(a.child)){
                    addButton(new ItemStackButton(magicItemsCategory.x+magicRecipesCount*25 -(int)Math.floor(((double)magicRecipesCount/6))*25*6,magicItemsCategory.y + (int)Math.floor(((double)magicRecipesCount/6))*25,24,24,(button)->{
                        minecraft.setScreen(new InfusingRecipeScreen(a));
                    },a.output,1.5f,false));
                    magicRecipesCount++;

                }
            }else if (a.category.equals("solar_category.materials")){
                if (minecraft.player.getPersistentData().getBoolean(a.child)){
                    addButton(new ItemStackButton(magicMaterialsCategory.x+magicMaterialsCount*25 -(int)Math.floor(((double)magicMaterialsCount/6))*25*6 ,magicMaterialsCategory.y + (int)Math.floor(((double)magicMaterialsCount/6))*25,24,24,(button)->{
                        minecraft.setScreen(new InfusingRecipeScreen(a));
                    },a.output,1.5f,false));
                    magicMaterialsCount++;

                }
            }else if (a.category.equals("solar_category.tools")){
                if (minecraft.player.getPersistentData().getBoolean(a.child)){
                    addButton(new ItemStackButton(magicToolsCategory.x+magicToolsCount*25 -(int)Math.floor(((double)magicToolsCount/6))*25*6,magicToolsCategory.y + (int)Math.floor(((double)magicToolsCount/6))*25,24,24,(button)->{
                        minecraft.setScreen(new InfusingRecipeScreen(a));
                    },a.output,1.5f,false));
                    magicToolsCount++;

                }
            }else if (a.category.equals("solar_category.upgrade")){
                if (minecraft.player.getPersistentData().getBoolean(a.child)){
                    addButton(new ItemStackButton(upgradingRecipesCategory.x+upgradingRecipesCount*25 -(int)Math.floor(((double)upgradingRecipesCount/6))*25*6,upgradingRecipesCategory.y + (int)Math.floor(((double)upgradingRecipesCount/6))*25,24,24,(button)->{
                        minecraft.setScreen(new InfusingRecipeScreen(a));
                    },a.output,1.5f,false));
                    upgradingRecipesCount++;

                }
            }



        }
        for (SolarSmeltingRecipe a :recipeSmelt){
            if (Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_LENS, minecraft.player)){
                addButton(new ItemStackButton(smeltingCategory.x+smeltingRecipeCount*25 -(int)Math.floor(((double)smeltingRecipeCount/6))*25*6,smeltingCategory.y + (int)Math.floor(((double)smeltingRecipeCount/6))*25,24,24,(button)->{
                    minecraft.setScreen(new SmeltingRecipeScreen(a));
                },a.output,1.5f,false));
                smeltingRecipeCount++;
            }
        }

        for (Multiblocks a : Multiblocks.ALL_STRUCTURES){
            Multiblock b = a.getM();
            if (Helpers.hasPlayerUnlocked(b.reqAchievement, minecraft.player)){
                addButton(new ItemStackButton(structures.x+structuresCount*25 - (int)Math.floor(((double)structuresCount/6))*25*6,structures.y + (int)Math.floor(((double)structuresCount/6))*25,24,24,(button)->{
                    minecraft.setScreen(new StructureScreen(b));
                },b.getMainBlock().asItem().getDefaultInstance(),1.5f,false));
                structuresCount++;
            }
        }

        addButton(goBack);
        addButton(nothing);
        nothing.x = relX +207;
        nothing.y = relY + 184;
        goBack.x = relX +207;
        goBack.y = relY + 164;



    }


    @Override
    public void render(MatrixStack matrices, int mousex, int mousey, float partialTicks) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();
        GL11.glScissor(width/2-(83*scale),height/2-(89*scale),(188*scale),190*scale);
        minecraft.getTextureManager().bind(MAIN_SCREEN_SCROLLABLE);
        blit(matrices,relX,relY,0,0,256,256);
        super.render(matrices,mousex,mousey,partialTicks);

        minecraft.getTextureManager().bind(FRAME);


        drawCategoryString(matrices,armorRecipeCount,new TranslationTextComponent("solar_category.armor"),armorCategory);
        drawCategoryString(matrices,magicRecipesCount,new TranslationTextComponent("solar_category.magic_items"),magicItemsCategory);
        drawCategoryString(matrices,magicMaterialsCount,new TranslationTextComponent("solar_category.materials"),magicMaterialsCategory);
        drawCategoryString(matrices,magicToolsCount,new TranslationTextComponent("solar_category.tools"),magicToolsCategory);
        drawCategoryString(matrices,smeltingRecipeCount,new TranslationTextComponent("solar_category.smelting"),smeltingCategory);
        drawCategoryString(matrices,structuresCount,new TranslationTextComponent("solar_category.structures"),structures);
        drawCategoryString(matrices,upgradingRecipesCount,new TranslationTextComponent("solar_category.upgrade"),upgradingRecipesCategory);





        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        minecraft.getTextureManager().bind(MAIN_SCREEN);
        blit(matrices,relX,relY,0,0,256,256);

        goBack.render(matrices,mousex,mousey,partialTicks);
        nothing.render(matrices,mousex,mousey,partialTicks);


    }



    public void drawRectangle(MatrixStack matrices,int x,int y,Point p){
        Helpers.drawLine(matrices,p.x+scrollX,p.y+scrollY,p.x+x+scrollX,p.y+scrollY,1,1,1);
        Helpers.drawLine(matrices,p.x+x+scrollX,p.y+scrollY,p.x+x+scrollX,p.y+y+scrollY,1,1,1);
        Helpers.drawLine(matrices,p.x+scrollX,p.y+y+scrollY,p.x+x+scrollX,p.y+y+scrollY,1,1,1);
        Helpers.drawLine(matrices,p.x+scrollX,p.y+scrollY,p.x+scrollX,p.y+y+scrollY,1,1,1);
    }


    public void drawCategoryString(MatrixStack matrices,int count,TranslationTextComponent translationTextComponent,Point point){
        int x;
        if (count <= 6) {
            x = (count) * 25;
        }else{
            x = 25*6;
        }
        int y = (int)((Math.floor((float)count/6)+1))*25;
        drawRectangle(matrices,x,y,point);
        drawString(matrices,minecraft.font,translationTextComponent,point.x+scrollX,point.y-8+scrollY,0xffffff);
    }


    public IItemHandler getLexiconInventory(){
        return Minecraft.getInstance().player.getMainHandItem().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }
}
