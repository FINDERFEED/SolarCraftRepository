package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;


public class StructureScreen extends Screen {

    public final ResourceLocation STRUCTURE_GUI = new ResourceLocation("solarforge","textures/gui/structure_screen.png");
    public final ResourceLocation BUTTONS = new ResourceLocation("solarforge","textures/misc/page_buttons.png");
    public int currentPage;

    public int structWidth;
    public int structHeightAndPageCount;
    public Multiblock structure;
    public  int relX;
    public  int relY;
    protected StructureScreen(Multiblock structure) {
        super(new TextComponent(""));
        this.structure = structure;
    }


    @Override
    protected void init() {

        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        currentPage = 1;
        structHeightAndPageCount = structure.getStruct().length;
        structWidth = structure.getStruct()[0].length / 2;
        addButton(new ImageButton(relX+104,relY+16,16,16,0,0,0,BUTTONS,16,32,(button)->{
            if ((currentPage+1 <= structHeightAndPageCount) ){
                currentPage+=1;
            }
        }));
        addButton(new ImageButton(relX+87,relY+16,16,16,0,16,0,BUTTONS,16,32,(button)->{
            if ((currentPage-1 > 0)){
                currentPage-=1;
            }
        }));

        addButton(new ItemStackButton(relX+186,relY+9,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        addButton(new ItemStackButton(relX+174,relY+9,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,false));

        super.init();


    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        minecraft.getTextureManager().bind(STRUCTURE_GUI);
        blit(matrices,relX,relY,0,0,256,256);

        String[] struct = structure.struct[currentPage-1];
        drawCenteredString(matrices, minecraft.font,new TextComponent(currentPage+ "/" + structHeightAndPageCount),relX+103,relY+8,0xffffff);
        //drawCenteredString(matrices, minecraft.font,new TranslationTextComponent(structure.getName()),relX+20,relY+10,0xffffff);
        Helpers.drawBoundedText(matrices,relX+14,relY+10,7,new TranslatableComponent(structure.getName()).getString());


        ItemRenderer ren = Minecraft.getInstance().getItemRenderer();
        for (int i = -structWidth; i <= structWidth;i++){
            for (int g = -structWidth; g <= structWidth;g++){
                ren.renderGuiItem(structure.getBlockByCharacter(struct[i+structWidth].charAt(g+structWidth)).asItem().getDefaultInstance(),relX+95+g*18,relY+109+i*18);
            }
        }



        super.render(matrices, mousex, mousey, partialTicks);
    }
}
