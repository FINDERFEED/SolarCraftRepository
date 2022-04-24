package com.finderfeed.solarforge.magic.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SolarForgeButtonYellow extends Button {
    protected  Button.OnTooltip tool;
    public static final ResourceLocation WIDGETS_SOLARFORGE = new ResourceLocation("solarforge","textures/gui/solar_button_yellow.png");
    public SolarForgeButtonYellow(int posx, int posy, int sizeX, int sizeY, Component component, OnPress onPress) {
        super(posx, posy, sizeX, sizeY, component, onPress);

    }

    public SolarForgeButtonYellow(int p_i232255_1_, int p_i232255_2_, Component p_i232255_5_, OnPress p_i232255_6_) {
        super(p_i232255_1_, p_i232255_2_, 65,15, p_i232255_5_, p_i232255_6_);

    }

    public SolarForgeButtonYellow(int p_i232256_1_, int p_i232256_2_, int p_i232256_3_, int p_i232256_4_, Component p_i232256_5_, OnPress p_i232256_6_, Button.OnTooltip onTooltip) {
        super(p_i232256_1_, p_i232256_2_, p_i232256_3_, p_i232256_4_, p_i232256_5_, p_i232256_6_,onTooltip);
        this.tool = onTooltip;
    }

    public SolarForgeButtonYellow(int p_i232256_1_, int p_i232256_2_, Component p_i232256_5_, OnPress p_i232256_6_, Button.OnTooltip onTooltip) {
        super(p_i232256_1_, p_i232256_2_,65,15, p_i232256_5_, p_i232256_6_,onTooltip);
        this.tool = onTooltip;
    }

    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
    }

    @Override
    public void renderButton(PoseStack matrices, int mx, int my, float pticks) {
        if (this.isHovered && tool != null) {
            this.renderToolTip(matrices, mx, my);

        }
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;

        ClientHelpers.bindText(WIDGETS_SOLARFORGE);

        RenderSystem.setShaderColor(1,1,1,alpha);
        int i = this.getYImage(this.isHovered);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrices, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(matrices, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(matrices, minecraft, mx, my);
        int j = getFGColor();
        drawCenteredString(matrices, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void renderToolTip(PoseStack matrices, int mx, int my) {
        if (tool != null) {
            this.tool.onTooltip(this, matrices, mx, my);
        }
    }
}
