package com.finderfeed.solarforge.magic_items.items.solar_lexicon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SolarLexiconContScreen extends ContainerScreen<SolarLexiconContainer> {

    public static final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_lexicon_inventory.png");

    public int relX;
    public int relY;


    public SolarLexiconContScreen(SolarLexiconContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }



    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        super.init();
    }

    @Override
    protected void renderBg(MatrixStack matrices, float partialTicks, int mousex, int mousey) {
        Minecraft.getInstance().getTextureManager().bind(LOC);

        int a = 0;
        if (minecraft.getWindow().getGuiScale() != 2){
            a = 1;
        }

        blit(matrices,relX+3+a,relY+33,0,0,256,256,256,256);
    }
}
