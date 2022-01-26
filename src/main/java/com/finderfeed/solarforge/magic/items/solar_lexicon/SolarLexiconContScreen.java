package com.finderfeed.solarforge.magic.items.solar_lexicon;

import com.finderfeed.solarforge.ClientHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class SolarLexiconContScreen extends AbstractContainerScreen<SolarLexiconContainer> {

    public static final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_lexicon_inventory.png");

    public int relX;
    public int relY;


    public SolarLexiconContScreen(SolarLexiconContainer p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_) {
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
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);

        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_,p_230430_2_,p_230430_3_);
    }

    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int mousex, int mousey) {
        ClientHelpers.bindText(LOC);

        int a = 0;
        if (minecraft.getWindow().getGuiScale() != 2){
            a = 1;
        }

        blit(matrices,relX+3+a,relY+33,0,0,256,256,256,256);
    }
}
