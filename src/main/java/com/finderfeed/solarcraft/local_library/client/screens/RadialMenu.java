package com.finderfeed.solarcraft.local_library.client.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ShaderInstance;

public class RadialMenu {

    private RadialMenuShaderSettings settings;
    private Runnable onPress;
    private IRenderable onHover;
    public int x;
    public int y;
    public int sizeX;
    public int sizeY;
    public boolean isHovered;


    public RadialMenu(RadialMenuShaderSettings settings, float x, float y, float sizeX, float sizeY){
        this.settings = settings;

    }

    public void mouseClicked(){

    }

    public void render(PoseStack matrices,int mouseX,int mouseY,float pTicks,float zOffset){

    }

    public record RadialMenuEntry(){}


    public static class RadialMenuShaderSettings{

        private int sections;
        private float outRadius;
        private float innerRadius;
        private float[] uCol;
        private float[] sCol;
        private float distFromCenter;

        public RadialMenuShaderSettings(int sections, float outRadius, float innerRadius, float[] unselectedColor, float[] selectedColor, float distFromCenter){
            if (selectedColor.length != 4 || unselectedColor.length != 4){
                throw new IllegalStateException("Radial menu colors should be 4 in length");
            }
            this.sections = sections;
            this.outRadius = outRadius;
            this.innerRadius = innerRadius;
            this.uCol = unselectedColor;
            this.sCol = selectedColor;
            this.distFromCenter = distFromCenter;
        }

        public void applyUniforms(ShaderInstance sh,int selectedSectionId){
            sh.safeGetUniform("color").set(sCol[0],sCol[1],sCol[2],sCol[3]);
            sh.safeGetUniform("sColor").set(uCol[0],uCol[1],uCol[2],uCol[3]);
            sh.safeGetUniform("distFromCenter").set(distFromCenter);
            sh.safeGetUniform("innerRadius").set(innerRadius);
            sh.safeGetUniform("outRadius").set(outRadius);
            sh.safeGetUniform("sectionCount").set(sections);
            sh.safeGetUniform("selectedSection").set(selectedSectionId);
        }
    }
}
