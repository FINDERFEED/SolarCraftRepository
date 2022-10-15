package com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.ArrayList;
import java.util.List;

public class ComponentSequence extends BaseComponent{

    private List<ComponentSequenceFloor> sequence;

    public ComponentSequence(ComponentSequenceBuilder builder) {
        super(builder.alignment, builder.maxX, builder.maxY);
        this.sequence = builder.sequence;
    }

    @Override
    public void render(PoseStack matrices, int x, int y, float pTicks, int mouseX, int mouseY,int ticker,int animationLength) {
        int[] xy = this.getAlignment().getCoords(this,x,y);
        xy[0] += this.getInnerBorder();
        xy[1] += this.getInnerBorder();
        int yOffset = 0;
        for (ComponentSequenceFloor floor : sequence){
            int xOffset = 0;
            for (BaseComponent component : floor.components()){
                component.render(matrices,xy[0] + xOffset,xy[1] + yOffset,pTicks,mouseX,mouseY,ticker,animationLength);
                xOffset += component.getXSize();
            }
            yOffset += floor.ySize();
        }
    }


    public static class ComponentSequenceBuilder{
        private ContentAlignment alignment = ContentAlignment.NO_ALIGNMENT;
        private List<ComponentSequenceFloor> sequence = new ArrayList<>();
        private List<BaseComponent> currentBuildingState = new ArrayList<>();
        private int currentY = 0;
        private int currentX = 0;
        private int maxX = 0;
        private int maxY = 0;

        public ComponentSequenceBuilder setAlignment(ContentAlignment alignment){
            this.alignment = alignment;
            return this;
        }

        public ComponentSequenceBuilder addComponent(BaseComponent component){
            this.currentBuildingState.add(component);
            this.currentX += component.getXSize();
            if (this.currentY < component.getYSize()){
                this.currentY = component.getYSize();
            }
            return this;
        }

        public ComponentSequenceBuilder nextLine(){
            this.sequence.add(new ComponentSequenceFloor(new ArrayList<>(currentBuildingState), currentY));
            this.maxY += currentY;
            this.currentY = 0;
            if (this.currentX > this.maxX){
                this.maxX = this.currentX;
            }
            this.currentX = 0;
            this.currentBuildingState.clear();
            return this;
        }
        //                                      /\
        // Yes i know this two methods are similar, i just want to have build() at the end.
        //                                      \/
        public ComponentSequenceBuilder build(){
            this.sequence.add(new ComponentSequenceFloor(new ArrayList<>(currentBuildingState), currentY));
            this.maxY += currentY;
            this.currentY = 0;
            if (this.currentX > this.maxX){
                maxX = this.currentX;
            }
            this.currentX = 0;
            this.currentBuildingState.clear();
            return this;
        }

    }


    public record ComponentSequenceFloor(List<BaseComponent> components,int ySize){}
}
