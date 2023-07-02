package com.finderfeed.solarcraft.client.screens.buttons;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.client.screens.FDButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class RuneButtonRunicTable extends FDButton {
    public boolean turnedOff = false;
    public static final ResourceLocation RUNIC_BUTTONS = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/rune_buttons.png");
    public int rune;

    public RuneButtonRunicTable(int xPos, int yPos, int xSize, int ySize, OnPress onPress, OnTooltip onTooltip, int xoffs) {
        super(xPos, yPos, xSize, ySize, Component.literal(""), onPress, onTooltip);
        this.rune = xoffs;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float pticks) {
        ClientHelpers.bindText(RUNIC_BUTTONS);
        PoseStack matrices = graphics.pose();
        int i = this.isHoveredOrFocused() ? 15 : 0;
        matrices.pushPose();

        RenderingTools.blitWithBlend(matrices,x,y,rune*15,i,15,15,176*15/22,44*15/22,0,1f);
        matrices.popPose();
    }

//    @Override
//    public void renderButton(PoseStack matrices, int mousex, int mousey, float pticks) {
//        ClientHelpers.bindText(RUNIC_BUTTONS);
//        int i = this.isHoveredOrFocused() ? 15 : 0;
//        matrices.pushPose();
//
//        blit(matrices,x,y,rune*15,i,15,15,176*15/22,44*15/22);
//        matrices.popPose();
//    }


    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(SoundEvents.DEEPSLATE_BREAK,1,1));
    }
}
