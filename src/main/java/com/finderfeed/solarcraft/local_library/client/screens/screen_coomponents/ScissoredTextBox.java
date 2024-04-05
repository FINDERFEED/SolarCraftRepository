package com.finderfeed.solarcraft.local_library.client.screens.screen_coomponents;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.screens.FDScreenComponent;
import com.finderfeed.solarcraft.local_library.client.screens.FDSizedScreenComponent;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import java.util.List;

public class ScissoredTextBox extends FDSizedScreenComponent {

    private List<FormattedCharSequence> text;
    private int scroll;
    private int maxScroll;
    private int textColor;
    private int scrollBarColorInner;
    private int scrollBarColorOuter;

    public ScissoredTextBox(Screen screen, int x, int y,int width,int height,int textColor,int scrollBarColorInner,int scrollBarColorOuter, Component text) {
        super(screen, x, y,width,height);
        this.setText(text);
        this.scrollBarColorInner = scrollBarColorInner;
        this.scrollBarColorOuter = scrollBarColorOuter;
        this.textColor = textColor;
    }

    @Override
    public void init() {

    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float pticks) {
        PoseStack matrices = graphics.pose();
        Font font = Minecraft.getInstance().font;
        RenderingTools.scissor(this.x,this.y,this.getWidth(),this.getHeight());

        matrices.pushPose();

        for (int i = 0; i < text.size(); i++){
            FormattedCharSequence sequence = text.get(i);
            graphics.drawString(font,sequence,x,y + i * font.lineHeight - scroll,textColor);
        }
        this.renderScrollbar(graphics);

        RenderSystem.disableScissor();
        matrices.popPose();
    }

    private void renderScrollbar(GuiGraphics graphics){
        float outA = RenderingTools.extractAlpha(scrollBarColorOuter) / 255f;
        float outR = RenderingTools.extractRed(scrollBarColorOuter) / 255f;
        float outG = RenderingTools.extractGreen(scrollBarColorOuter) / 255f;
        float outB = RenderingTools.extractBlue(scrollBarColorOuter) / 255f;
        int xPos = this.x + this.getWidth() - 2;
        if (maxScroll == 0) {
            RenderingTools.fill(graphics.pose(),xPos,this.y,xPos + 2,this.y + this.getHeight(),outR,outG,outB,outA);
        }else{
            float h = (float)this.getHeight();
            float factor = (this.maxScroll + h) / h;
            float scrollBarYSize = h / factor;
            float scrollBarYPos = scroll / factor + y;

            float inA = RenderingTools.extractAlpha(scrollBarColorInner) / 255f;
            float inR = RenderingTools.extractRed(scrollBarColorInner) / 255f;
            float inG = RenderingTools.extractGreen(scrollBarColorInner) / 255f;
            float inB = RenderingTools.extractBlue(scrollBarColorInner) / 255f;
            RenderingTools.fill(graphics.pose(),xPos,this.y,xPos + 2,this.y + this.getHeight(),inR,inG,inB,inA);
            RenderingTools.fill(graphics.pose(),xPos,scrollBarYPos,xPos + 2,scrollBarYPos + scrollBarYSize,outR,outG,outB,outA);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void mouseClicked(double x, double y, int action) {

    }

    @Override
    public void mouseReleased(double x, double y, int action) {

    }

    @Override
    public void mouseDragged(double xPos, double yPos, int button, double dragLeftRight, double dragUpDown) {

    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {

    }

    @Override
    public void mouseScrolled(double mousePosX, double mousePosY, double delta) {
        if (this.isFocused() && RenderingTools.isMouseInBorders((int)mousePosX,(int)mousePosY,this)){
            this.scroll = Mth.clamp((int)(this.scroll - delta * 5),0,this.maxScroll);
        }
    }

    public void setText(Component text) {
        Font font = Minecraft.getInstance().font;
        this.text = font.split(text,this.getWidth() - 3);
        this.maxScroll = Mth.clamp(font.lineHeight * this.text.size() - this.getHeight(),0,Integer.MAX_VALUE);
        this.scroll = 0;
    }

    public List<FormattedCharSequence> getText() {
        return text;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }
}
