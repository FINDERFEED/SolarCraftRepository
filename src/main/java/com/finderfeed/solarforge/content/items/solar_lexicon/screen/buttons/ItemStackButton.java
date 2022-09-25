package com.finderfeed.solarforge.content.items.solar_lexicon.screen.buttons;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;

public class ItemStackButton extends Button {

    public final ItemStack stack;
    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/item_stack_button.png");
    public final ResourceLocation LOC2 = new ResourceLocation("solarforge","textures/misc/question_mark.png");
    public final float scaleFactor;

    public ItemStackButton(int x, int y, int xLoc, int yLoc, OnPress press,ItemStack stack,float scaleFactor) {
        super(x,y,xLoc,yLoc,new TextComponent(""),press);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
    }


    public ItemStackButton(int x, int y, int xLoc, int yLoc, OnPress press,ItemStack stack,float scaleFactor,Button.OnTooltip tooltip) {
        super(x,y,xLoc,yLoc,new TextComponent(""),press,tooltip);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public void renderButton(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        matrices.pushPose();
        if (this.isHovered){
            this.renderToolTip(matrices,mousex,mousey);
        }
        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor,0);

        RenderSystem.setShaderTexture(0,LOC);
        RenderSystem.enableBlend();
        if (this.isHovered){
            this.blit(matrices,(int)(x/scaleFactor),(int)(y/scaleFactor),0,0,16,16);

        }
        matrices.popPose();
    }

    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks,double zOffset){
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x + width,y + height);
        matrices.pushPose();
        if (this.isHovered){
            this.renderToolTip(matrices,mousex,mousey);
        }
        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor,zOffset);

        RenderSystem.setShaderTexture(0,LOC);
        RenderSystem.enableBlend();
        if (this.isHovered){
            this.blit(matrices,(int)(x/scaleFactor),(int)(y/scaleFactor),0,0,16,16);

        }
        matrices.popPose();
    }

    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
    }

}
