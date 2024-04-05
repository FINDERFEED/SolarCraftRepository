package com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Comparator;
import java.util.List;

public class FDCenteredTextComponent extends BaseComponent{

    private static final int TEXT_HEIGHT = 9;

    private List<String> text;
    private int textColor;

    public FDCenteredTextComponent(ContentAlignment alignment,int xSize, int ySize) {
        super(alignment,xSize, ySize);
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y, float pTicks, int mx, int my, int ticker, int animationLength) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        int[] xy = this.getAlignment().getCoords(this,x,y);
        xy[0] += getInnerBorder();
        xy[1] += getInnerBorder();
        Font font = Minecraft.getInstance().font;
        for (int i = 0; i < text.size();i++){
            int yOffset = i*TEXT_HEIGHT;
            String s = text.get(i);
            MultiBufferSource.BufferSource source = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            font.drawInBatch(s,xy[0] - font.width(s)/2f,xy[1] + yOffset,0xffffff,true,matrices.last().pose(),source, Font.DisplayMode.NORMAL,
                    0, 15728880,font.isBidirectional());
            source.endBatch();
        }
        matrices.popPose();
    }


    public FDCenteredTextComponent setText(Component component, int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getString(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(Minecraft.getInstance().font.width(this.text.stream().max(Comparator.comparingInt(String::length)).get()) + this.getInnerBorder()*2);
        return this;
    }

    public FDCenteredTextComponent setText(MutableComponent component, int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getString(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(Minecraft.getInstance().font.width(this.text.stream().max(Comparator.comparingInt(String::length)).get()) + this.getInnerBorder()*2);
        return this;
    }

    public FDCenteredTextComponent setText(Component component,int forceX, int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getString(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(forceX * 5 + this.getInnerBorder()*2);
        return this;
    }

    public FDCenteredTextComponent setText(MutableComponent component,int forceX, int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getString(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(forceX * 5 + this.getInnerBorder()*2);
        return this;
    }
}
