package com.finderfeed.solarforge.SolarAbilities.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AbilityBuyScreen extends Screen {

    public int relX;
    public int relY;
    private final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/ability_buy_screen.png");

    protected AbilityBuyScreen(Component p_96550_) {
        super(p_96550_);
    }


    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
    }




    @Override
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
    }
}
