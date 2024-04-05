package com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons;

import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
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

    public ItemStackButton(int x, int y, int xsize, int ysize, OnPress press,ItemStack stack,float scaleFactor) {
        super(x,y,xsize,ysize,Component.literal(""),press);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
    }


    public ItemStackButton(int x, int y, int xsize, int ysize, OnPress press,ItemStack stack,float scaleFactor,FDButton.OnTooltip tooltip) {
        super(x,y,xsize,ysize,Component.literal(""),press,tooltip);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();

        RenderingTools.renderScaledGuiItemCentered(graphics,stack,(int) x + width/2f, (int) y + height/2f,scaleFactor, 300);


        RenderSystem.setShaderTexture(0,LOC);
        RenderSystem.enableBlend();
        if (this.isHovered){
            graphics.fill(x,y,x + (int)(16*scaleFactor),y + (int)(16*scaleFactor),0x99ffffff);

        }
        matrices.popPose();
    }

    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks,double zOffset){
        PoseStack matrices = graphics.pose();
        this.isHovered = RenderingTools.isMouseInBorders(mousex,mousey,x,y,x + width,y + height);
        matrices.pushPose();


        RenderingTools.renderScaledGuiItemCentered(graphics,stack,(int) x + width/2f - 0.5f, (int) y + height/2f - 0.5f,scaleFactor,zOffset + 300);

//        RenderSystem.setShaderTexture(0,LOC);
        if (this.isHovered){
            graphics.fill(x,y,x + (int)(width*scaleFactor),y + (int)(height*scaleFactor),0xaaffffff);
        }
        matrices.popPose();
        if (this.isHovered){
            this.renderTooltip(graphics,mousex,mousey);
        }
    }

    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(SCSounds.BUTTON_PRESS2.get(),1,1));
    }

}
