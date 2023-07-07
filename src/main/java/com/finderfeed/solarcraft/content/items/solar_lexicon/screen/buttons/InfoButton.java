package com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class InfoButton extends FDButton {
    public static final ResourceLocation LOC = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/info_button.png");

    public ResourceLocation LOCR = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/info_button.png");

    public InfoButton(int x, int y, int width, int height) {
        super(x, y, width, height, Component.literal(""), (btn)->{});
    }

    public InfoButton(int x, int y, int width, int height, OnTooltip tooltip) {
        super(x, y, width, height, Component.literal(""), (btn)->{}, tooltip);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float pticks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        RenderSystem.setShaderTexture(0, LOCR);
        if (!this.isHovered) {
            RenderingTools.blitWithBlend(matrices, x, y, 0, 0, width, height, width, height*2,0,1f);
        }else{
            RenderingTools.blitWithBlend(matrices, x, y, 0, height, width, height, width, height*2,0,1f);
        }

        matrices.popPose();
    }

//    @Override
//    public void renderButton(PoseStack matrices, int mousex, int mousey, float partialTicks) {
//        matrices.pushPose();
//        RenderSystem.setShaderTexture(0, LOCR);
//        if (!this.isHoveredOrFocused()) {
//            Gui.blit(matrices, x, y, width, height, 0, 0, 16, 16, 16, 32);
//        }else{
//            Gui.blit(matrices, x, y, width, height, 0, 16, 16, 16, 16, 32);
//        }
//        if (this.isHoveredOrFocused()){
//            this.renderToolTip(matrices,mousex,mousey);
//        }
//        matrices.popPose();
//    }


    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
    }

    public static class Wooden extends InfoButton{

        public Wooden(int x, int y, int width, int height) {
            super(x, y, width, height);
            this.LOCR = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/info_button_wood.png");
        }

        public Wooden(int x, int y, int width, int height, OnTooltip tooltip) {
            super(x, y, width, height, tooltip);
            this.LOCR = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/info_button_wood.png");
        }
    }
}
