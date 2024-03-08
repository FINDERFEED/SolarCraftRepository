package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

public class SunRayButton extends FDButton {

    public static ResourceLocation LEFT_UP = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/memory_puzzle/sun_ray_up_left.png");
    public static ResourceLocation LEFT_DOWN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/memory_puzzle/sun_ray_down_left.png");
    public static ResourceLocation RIGHT_UP = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/memory_puzzle/sun_ray_up_right.png");
    public static ResourceLocation RIGHT_DOWN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/memory_puzzle/sun_ray_down_right.png");
    private ResourceLocation texture;
    private Mode currentMode = Mode.RED;
    private int modeTicker = 0;
    private int id;
    public SunRayButton(int posX, int posY, int id,ResourceLocation tex, OnPress press) {
        super(posX - 4, posY - 4, 8, 8,Component.empty(), press);
        this.id = id;
        this.texture = tex;
    }


    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float pticks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        if (modeTicker <= 0){
            if (this.isHovered){
                renderWithMode(Mode.HIGHLIGHT,matrices);
            }else{
                renderWithMode(Mode.NORMAL,matrices);
            }
        }else{
            renderWithMode(currentMode,matrices);
        }

        matrices.popPose();
    }

    private void renderWithMode(Mode mode,PoseStack matrices){
        ClientHelpers.bindText(texture);
        int offset = mode.offset;
        RenderingTools.blitWithBlend(matrices,x-4,y-4,0,offset,16,16,16,64,0,1f);
    }

    public void tick(){
        modeTicker = Mth.clamp(modeTicker - 1,0,Integer.MAX_VALUE);
    }

    public void setMode(Mode mode){
        this.currentMode = mode;
    }

    public void glowWithMode(Mode mode,int time){
        this.currentMode = mode;
        this.modeTicker = time;
    }

    @Override
    public void playDownSound(SoundManager manager) {
        manager.play(SimpleSoundInstance.forUI(SCSounds.BUTTON_PRESS2.get(), 1.0F));
    }

    public int getId() {
        return id;
    }

    public enum Mode{
        NORMAL(0),
        GREEN(16),
        RED(32),
        HIGHLIGHT(48);
        private int offset;

        Mode(int offset){
            this.offset = offset;
        }
    }
}
