package com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class SolarForgeAbilityConfigScreen extends Screen {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 88;

    private ResourceLocation GUI = new ResourceLocation("solarcraft","textures/gui/solar_config_gui.png");

    public SolarForgeAbilityConfigScreen() {
        super(Component.translatable("test.screen.thing"));

    }

    @Override
    public void init(){
        super.init();
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        addRenderableWidget(new SolarForgeButton(relX +7 , relY + 10-3, 65, 15, Component.literal("Hotkey 1"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(1))));
        addRenderableWidget(new SolarForgeButton(relX +7, relY + 30-3, 65, 15, Component.literal("Hotkey 2"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(2))));
        addRenderableWidget(new SolarForgeButton(relX +7, relY + 50-3, 65, 15, Component.literal("Hotkey 3"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(3))));
        addRenderableWidget(new SolarForgeButton(relX +7, relY + 70-3, 65, 15, Component.literal("Hotkey 4"), button -> Minecraft.getInstance().setScreen(new SolarForgeConfigScreenAdditional(4))));
    }



    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks){



        ClientHelpers.bindText(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(stack, relX, relY, 77, 0, 100, HEIGHT);
        //this.renderBackground(stack);
        super.render(stack,rouseX,rouseY,partialTicks);
    }
}
