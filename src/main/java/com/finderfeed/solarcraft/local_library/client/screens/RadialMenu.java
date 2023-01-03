package com.finderfeed.solarcraft.local_library.client.screens;

import com.finderfeed.solarcraft.client.particles.screen.SolarStrikeParticleScreen;
import com.finderfeed.solarcraft.client.rendering.CoreShaders;
import com.finderfeed.solarcraft.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadialMenu {

    private RadialMenuShaderSettings settings;
    private Runnable onPress;
    private IRenderable onHover;
    public float centerX;
    public float centerY;
    public float radius;
    private List<RadialMenuSection> sections;

    public RadialMenu(RadialMenuShaderSettings settings, float x, float y, float radius, List<RadialMenuSection> sections){
        this.settings = settings;
        this.sections = sections;
        this.settings.sections = sections.size();
        this.centerX = x;
        this.centerY = y;
        this.radius = radius;
    }

    public void mouseClicked(float x,float y){
        int i = getSectionUnderMouse(x,y);
        RadialMenuSection section = getSection(i);
        if (section != null && section.isPressable()){
            section.onPress.run();
            section.playDownSound();
        }
    }

    @Nullable
    public RadialMenuSection getSection(int i){
        if (i < 0 || i >= sections.size()){
            return null;
        }
        return sections.get(i);
    }

    public void render(PoseStack matrices,int mouseX,int mouseY,float pTicks,float zOffset){

        int hoveredSection = getSectionUnderMouse(mouseX,mouseY);
        drawMenu(matrices,mouseX,mouseY,pTicks,zOffset,hoveredSection);
        renderSectionsAndSetHovered(matrices,mouseX,mouseY,pTicks,zOffset,hoveredSection);


    }

    public void renderSectionsAndSetHovered(PoseStack matrices,int mouseX,int mouseY,float pTicks,float zOffset,int hoveredSection){
        double sectionAngle = Math.PI * 2 / settings.sections;
        double offsI = settings.innerRadius / 0.5 * radius;
        double offsO = settings.outRadius / 0.5 * radius;
        double sr = (offsI + offsO) / 2;
        for (int i = 0; i < sections.size(); i++){
            Vec2 offPos = rotateVector(new Vec2(0,(float)sr),(float)(sectionAngle*i + sectionAngle/2));

            RadialMenuSection section = sections.get(i);
            section.icon.render(matrices,centerX + offPos.x,centerY + offPos.y);
            section.setHovered(false);

        }
        RadialMenuSection s = getSection(hoveredSection);
        if (s != null){
            s.setHovered(true);
            s.onTooltip.render(matrices,mouseX,mouseY);
        }
    }

    public void drawMenu(PoseStack matrices,int mouseX,int mouseY,float pTicks,float zOffset,int section){
        RenderSystem.enableBlend();
        Matrix4f m = matrices.last().pose();
        RenderSystem.setShader(()-> CoreShaders.RADIAL_MENU);
        settings.applyUniforms(CoreShaders.RADIAL_MENU,section);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        builder.vertex(m,centerX - radius,centerY - radius,zOffset).uv(0,0).endVertex();
        builder.vertex(m,centerX - radius,centerY + radius,zOffset).uv(0,1).endVertex();
        builder.vertex(m,centerX + radius,centerY + radius,zOffset).uv(1,1).endVertex();
        builder.vertex(m,centerX + radius,centerY - radius,zOffset).uv(1,0).endVertex();
        BufferUploader.drawWithShader(builder.end());
        RenderSystem.disableBlend();
    }


    private Vec2 rotateVector(Vec2 r,float angle){
        return new Vec2(
                Mth.cos(angle)*r.x - Mth.sin(angle)*r.y,
                Mth.sin(angle)*r.x + Mth.cos(angle)*r.y
        );
    }

    public int getSectionUnderMouse(float mx,float my){
        //relative mouse coords
        Vec2 mouse = new Vec2(mx - centerX,my - centerY);

        //getting current section id
        Vec2 mt = rotateVector(mouse,(float)Math.PI/2);
        double angle = Mth.atan2(mt.y,mt.x) + Math.PI;
        double secAngle = Math.PI * 2 / settings.sections;
        int sectionId = (int)Math.floor(angle / secAngle);

        //getting section init point
        double cAngle = sectionId * secAngle + secAngle / 2d;
        float rad = settings.distFromCenter / 0.5f * radius;
        Vec2 initPoint = rotateVector(new Vec2(0,rad),(float)cAngle);

        //relative to initPoint mouse coords
        Vec2 ri = rotateVector(new Vec2(mouse.x - initPoint.x ,mouse.y - initPoint.y),(float)Math.PI/2);

        //computing the angle again
        angle = Mth.atan2(ri.y,ri.x) + Math.PI;

        //compute distance from center to mouse
        float distSqr = mouse.x * mouse.x + mouse.y*mouse.y;
        double inRadReal = settings.innerRadius / 0.5 * radius;
        double outRadReal = settings.outRadius / 0.5 * radius;

        if (Math.abs(angle - cAngle) <= secAngle/2d && distSqr <= outRadReal*outRadReal && distSqr >= inRadReal*inRadReal){
            return sectionId;
        }

        return -1;
    }

    public static class RadialMenuSection{

        public boolean isHovered = false;
        public Runnable onPress;
        public IRenderable onTooltip;
        public IRenderable icon;
        private boolean isPressable = true;


        public RadialMenuSection(Runnable onPress,IRenderable icon,IRenderable onTooltip) {
            this.onPress = onPress;
            this.onTooltip = onTooltip;
            this.icon = icon;
        }


        public void setHovered(boolean hovered) {
            isHovered = hovered;
        }

        public void setPressable(boolean pressable) {
            isPressable = pressable;
        }

        public boolean isPressable() {
            return isPressable;
        }

        public boolean isHovered() {
            return isHovered;
        }

        public void playDownSound() {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
        }
    }


    public static class RadialMenuShaderSettings{

        private int sections;
        private float outRadius;
        private float innerRadius;
        private float[] uCol;
        private float[] sCol;
        private float distFromCenter;

        /**
         * @param sections is being set automatically by radial menu class
         * @param outRadius a value between inner radius and 0.5
         * @param innerRadius a value between distFromCenter and outRadius
         * @param unselectedColor name says for itself
         * @param selectedColor name says for itself
         * @param distFromCenter distance from center to separate sections
         */
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
