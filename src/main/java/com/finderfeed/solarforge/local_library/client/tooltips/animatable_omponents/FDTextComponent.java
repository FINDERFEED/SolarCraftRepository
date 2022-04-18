package com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Comparator;
import java.util.List;

public class FDTextComponent extends BaseComponent{

    private static final int TEXT_HEIGHT = 9;

    private List<String> text;
    private int textColor;

    public FDTextComponent(ContentAlignment alignment,int xSize, int ySize) {
        super(alignment,xSize, ySize);
    }

    @Override
    public void render(PoseStack matrices, int x, int y, float pTicks,int mx,int my,int ticker,int animationLength) {
        matrices.pushPose();
        int[] xy = this.getAlignment().getCoords(this,x,y);
        xy[0] += getInnerBorder();
        xy[1] += getInnerBorder();
        Font font = Minecraft.getInstance().font;
        for (int i = 0; i < text.size();i++){
            int yOffset = i*TEXT_HEIGHT;
            MultiBufferSource.BufferSource source = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            font.drawInBatch(text.get(i),xy[0],xy[1] + yOffset,0xffffff,true,matrices.last().pose(),source,true,
                    0, 15728880,font.isBidirectional());
            source.endBatch();
        }
        matrices.popPose();
    }


    public FDTextComponent setText(TranslatableComponent component,int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getString(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(Minecraft.getInstance().font.width(this.text.stream().max(Comparator.comparingInt(String::length)).get()) + this.getInnerBorder()*2);
        return this;
    }

    public FDTextComponent setText(TextComponent component,int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getText(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(Minecraft.getInstance().font.width(this.text.stream().max(Comparator.comparingInt(String::length)).get()) + this.getInnerBorder()*2);
        return this;
    }

    public FDTextComponent setText(TranslatableComponent component,int forceX, int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getString(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(forceX * 5 + this.getInnerBorder()*2);
        return this;
    }

    public FDTextComponent setText(TextComponent component,int forceX, int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getText(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT + this.getInnerBorder()*2);
        this.setXSize(forceX * 5 + this.getInnerBorder()*2);
        return this;
    }
}
