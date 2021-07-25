package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;

import net.minecraft.client.gui.components.Button.OnPress;

public class SolarForgeButton extends Button  {
    protected  Button.OnTooltip tool;
    public static final ResourceLocation WIDGETS_SOLARFORGE = new ResourceLocation("solarforge","textures/gui/widgets_solarforge.png");
    public SolarForgeButton(int p_i232255_1_, int p_i232255_2_, int p_i232255_3_, int p_i232255_4_, Component p_i232255_5_, OnPress p_i232255_6_) {
      super(p_i232255_1_, p_i232255_2_, p_i232255_3_, p_i232255_4_, p_i232255_5_, p_i232255_6_);

    }

    @Override
    public void playDownSound(SoundManager p_230988_1_) {
        p_230988_1_.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
    }



    public SolarForgeButton(int p_i232256_1_, int p_i232256_2_, int p_i232256_3_, int p_i232256_4_, Component p_i232256_5_, OnPress p_i232256_6_, Button.OnTooltip onTooltip) {
        super(p_i232256_1_, p_i232256_2_, p_i232256_3_, p_i232256_4_, p_i232256_5_, p_i232256_6_,onTooltip);
    this.tool = onTooltip;
    }
    @Override
    public void renderButton(PoseStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        if (this.isHovered() && tool != null) {
            this.renderToolTip(p_230431_1_, p_230431_2_, p_230431_3_);

        }
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(WIDGETS_SOLARFORGE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(p_230431_1_, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(p_230431_1_, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(p_230431_1_, minecraft, p_230431_2_, p_230431_3_);
        int j = getFGColor();
        drawCenteredString(p_230431_1_, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void renderToolTip(PoseStack p_230443_1_, int p_230443_2_, int p_230443_3_) {
        if (tool != null) {
            this.tool.onTooltip(this, p_230443_1_, p_230443_2_, p_230443_3_);
        }
    }

}
