package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class SolarForgeAbilityConfigScreen extends Screen {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 88;

    private ResourceLocation GUI = new ResourceLocation("solarforge","textures/gui/solar_config_gui.png");

    public SolarForgeAbilityConfigScreen() {
        super(new TranslatableComponent("test.screen.thing"));

    }

    @Override
    public void init(){
        super.init();
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        addButton(new SolarForgeButton(relX +7 , relY + 10-3, 65, 15, new TextComponent("Hotkey 1"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(1))));
        addButton(new SolarForgeButton(relX +7, relY + 30-3, 65, 15, new TextComponent("Hotkey 2"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(2))));
        addButton(new SolarForgeButton(relX +7, relY + 50-3, 65, 15, new TextComponent("Hotkey 3"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(3))));
        addButton(new SolarForgeButton(relX +7, relY + 70-3, 65, 15, new TextComponent("Hotkey 4"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(4))));
    }



    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks){
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(stack, relX, relY, 77, 0, 100, HEIGHT);
        //this.renderBackground(stack);
        super.render(stack,rouseX,rouseY,partialTicks);
    }
}
