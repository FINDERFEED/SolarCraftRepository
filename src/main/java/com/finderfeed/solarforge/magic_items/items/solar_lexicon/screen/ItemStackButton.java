package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

public class ItemStackButton extends Button {

    public final ItemStack stack;
    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/item_stack_button.png");
    public final ResourceLocation LOC2 = new ResourceLocation("solarforge","textures/misc/question_mark.png");
    public final float scaleFactor;
    public final boolean qMark;
    public ItemStackButton(int x, int y, int xLoc, int yLoc, IPressable press,ItemStack stack,float scaleFactor,boolean qMark) {
        super(x,y,xLoc,yLoc,new StringTextComponent(""),press);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
        this.qMark = qMark;
    }


    public ItemStackButton(int x, int y, int xLoc, int yLoc, IPressable press,ItemStack stack,float scaleFactor,boolean qMark,Button.ITooltip tooltip) {
        super(x,y,xLoc,yLoc,new StringTextComponent(""),press,tooltip);
        this.stack = stack;
        this.scaleFactor = scaleFactor;
        this.qMark = qMark;
    }


    @Override
    public void playDownSound(SoundHandler p_230988_1_) {
        p_230988_1_.play(SimpleSound.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
    }

    @Override
    public void renderButton(MatrixStack matrices, int mousex, int mousey, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        matrices.pushPose();
        if (this.isHovered){

            this.renderToolTip(matrices,mousex,mousey);
        }
        matrices.scale(scaleFactor,scaleFactor,scaleFactor);
        GL11.glScalef(scaleFactor,scaleFactor,scaleFactor);
        //if (!qMark) {
            mc.getItemRenderer().renderGuiItem(stack, (int) (x / scaleFactor), (int) (y / scaleFactor));
        //}else{


        //}


        GL11.glScalef(1/scaleFactor,1/scaleFactor,1/scaleFactor);
        mc.getTextureManager().bind(LOC);
        RenderSystem.enableBlend();
        if (this.isHovered){
            this.blit(matrices,(int)(x/scaleFactor),(int)(y/scaleFactor),0,0,16,16);

        }
        matrices.popPose();
    }
}
