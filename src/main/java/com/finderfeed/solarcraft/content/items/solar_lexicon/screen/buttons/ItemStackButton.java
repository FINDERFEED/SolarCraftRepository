package com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons;

import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class ItemStackButton extends FDButton {

    public final ItemStack stack;
    public final ResourceLocation LOC = new ResourceLocation("solarcraft","textures/misc/item_stack_button.png");
    public final ResourceLocation LOC2 = new ResourceLocation("solarcraft","textures/misc/question_mark.png");
    public final float scaleFactor;

    public ItemStackButton(int x, int y, int xLoc, int yLoc, OnPress press,ItemStack stack,float scaleFactor) {
        super(x,y,xLoc,yLoc,Component.literal(""),press);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
    }


    public ItemStackButton(int x, int y, int xLoc, int yLoc, OnPress press,ItemStack stack,float scaleFactor,FDButton.OnTooltip tooltip) {
        super(x,y,xLoc,yLoc,Component.literal(""),press,tooltip);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
//        if (this.isHovered){
//            this.renderToolTip(matrices,mousex,mousey);
//        }
        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor,0);

        RenderSystem.setShaderTexture(0,LOC);
        RenderSystem.enableBlend();
        if (this.isHovered){
            RenderingTools.blitWithBlend(matrices,(int)(x/scaleFactor),(int)(y/scaleFactor),0,0,16,16,16,16,0,1f);

        }
        matrices.popPose();
    }

    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks,double zOffset){
        PoseStack matrices = graphics.pose();
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x + width,y + height);
        matrices.pushPose();
//        if (this.isHovered){
//            this.renderTooltip(graphics,mousex,mousey);
//        }
        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor,zOffset);

        RenderSystem.setShaderTexture(0,LOC);
        RenderSystem.enableBlend();
        if (this.isHovered){
            RenderingTools.blitWithBlend(matrices,(int)(x/scaleFactor),(int)(y/scaleFactor),0,0,16,16,16,16,0,1f);
        }
        matrices.popPose();
    }

    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
    }

}
