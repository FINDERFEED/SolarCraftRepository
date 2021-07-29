package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.RenderingTools;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.components.Button.OnPress;

public class ItemStackButton extends Button {

    public final ItemStack stack;
    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/item_stack_button.png");
    public final ResourceLocation LOC2 = new ResourceLocation("solarforge","textures/misc/question_mark.png");
    public final float scaleFactor;
    public final boolean qMark;
    public ItemStackButton(int x, int y, int xLoc, int yLoc, OnPress press,ItemStack stack,float scaleFactor,boolean qMark) {
        super(x,y,xLoc,yLoc,new TextComponent(""),press);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
        this.qMark = qMark;
    }


    public ItemStackButton(int x, int y, int xLoc, int yLoc, OnPress press,ItemStack stack,float scaleFactor,boolean qMark,Button.OnTooltip tooltip) {
        super(x,y,xLoc,yLoc,new TextComponent(""),press,tooltip);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
        this.qMark = qMark;
    }


    @Override
    public void playDownSound(SoundManager p_230988_1_) {
        p_230988_1_.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
    }

    @Override
    public void renderButton(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        matrices.pushPose();
        if (this.isHovered){

            this.renderToolTip(matrices,mousex,mousey);
        }
        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
//        GL11.glScalef(scaleFactor,scaleFactor,scaleFactor);

        //if (!qMark) {
        RenderingTools.renderScaledGuiItem(stack,(int) x, (int) y,scaleFactor);
//        mc.getItemRenderer().renderGuiItem(stack, (int) (x / scaleFactor), (int) (y / scaleFactor));
        //}else{


        //}


//        GL11.glScalef(1/scaleFactor,1/scaleFactor,1/scaleFactor);
        RenderSystem.setShaderTexture(0,LOC);
        RenderSystem.enableBlend();
        if (this.isHovered){
            this.blit(matrices,(int)(x/scaleFactor),(int)(y/scaleFactor),0,0,16,16);

        }
        matrices.popPose();
    }
}
