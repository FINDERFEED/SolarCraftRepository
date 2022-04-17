package com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents;

import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

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
        for (int i = 0; i < text.size();i++){
            int yOffset = i*TEXT_HEIGHT;
            Gui.drawString(matrices, Minecraft.getInstance().font, text.get(i),xy[0],xy[1] + yOffset,textColor);
        }
        matrices.popPose();
    }


    public FDTextComponent setText(TranslatableComponent component,int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getString(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT);
        this.setXSize(this.getXSize() * 5);
        return this;
    }

    public FDTextComponent setText(TextComponent component,int textColor){
        this.textColor = textColor;
        this.text = RenderingTools.splitString(component.getText(),this.getXSize());
        this.setYSize(this.text.size()*TEXT_HEIGHT);
        this.setXSize(this.getXSize()*5);
        return this;
    }
}
