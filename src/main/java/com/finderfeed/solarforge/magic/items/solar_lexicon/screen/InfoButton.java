package com.finderfeed.solarforge.magic.items.solar_lexicon.screen;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public class InfoButton extends Button {

    public static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/info_button.png");

    public InfoButton(int x, int y, int width, int height) {
        super(x, y, width, height, new TextComponent(""), (btn)->{});
    }

    public InfoButton(int x, int y, int width, int height, OnTooltip tooltip) {
        super(x, y, width, height, new TextComponent(""), (btn)->{}, tooltip);
    }

    @Override
    public void renderButton(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        matrices.pushPose();
        RenderSystem.setShaderTexture(0,LOC);
        if (!this.isHoveredOrFocused()) {
            Gui.blit(matrices, x, y, width, height, 0, 0, 16, 16, 16, 32);
        }else{
            Gui.blit(matrices, x, y, width, height, 0, 16, 16, 16, 16, 32);
        }
        if (this.isHoveredOrFocused()){
            this.renderToolTip(matrices,mousex,mousey);
        }
        matrices.popPose();
    }


    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
    }
}
