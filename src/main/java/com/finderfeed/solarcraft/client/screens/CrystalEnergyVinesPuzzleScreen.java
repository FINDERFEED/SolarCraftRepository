package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.other.EaseInOut;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle.PuzzleActionPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class CrystalEnergyVinesPuzzleScreen extends SolarCraftScreen {

    public static final ResourceLocation TRIES = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/wooden_window.png");
    public static final ResourceLocation MAIN_GUI = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/crystal_energy_vines_puzzle.png");
    public static final ResourceLocation TWIGS = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/twigs.png");
    public static final ResourceLocation NODE = new ResourceLocation(SolarCraft.MOD_ID,"textures/particle/solar_strike_particle.png");
    public static final EaseInOut VALUE = new EaseInOut(0,1,20,2);

    private int ticker = 0;

    public CrystalEnergyVinesTile tile;

    public CrystalEnergyVinesPuzzleScreen(CrystalEnergyVinesTile tile){
        this.tile = tile;
    }

    @Override
    public boolean keyPressed(int keyCode, int idk, int type) {
        if (keyCode == GLFW.GLFW_KEY_UP || keyCode == GLFW.GLFW_KEY_W){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_UP);
        }else if (keyCode == GLFW.GLFW_KEY_DOWN || keyCode == GLFW.GLFW_KEY_S){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_DOWN);
        }else if (keyCode == GLFW.GLFW_KEY_LEFT || keyCode == GLFW.GLFW_KEY_A){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_LEFT);
        }else if (keyCode == GLFW.GLFW_KEY_RIGHT || keyCode == GLFW.GLFW_KEY_D){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_RIGHT);
        }else if (keyCode == GLFW.GLFW_KEY_R){
            sendMovementPacket(CrystalEnergyVinesTile.RESET);
        }

        return super.keyPressed(keyCode, idk, type);

    }

    @Override
    protected void init() {
        super.init();
        VALUE.reset();
        InfoButton.Wooden button = new InfoButton.Wooden(relX- 30 ,relY ,13,13,(btn,graphics,mx,my)->{
            graphics.renderTooltip(font,font.split(Component.translatable("solarcraft.energy_vines_screen"),200),mx,my);
        });
        addRenderableWidget(button);
    }

    @Override
    public void tick() {
        super.tick();
        ticker++;
        VALUE.tick();
        if (tile.getPuzzlePattern() != null && tile.isWin()){
            Minecraft.getInstance().setScreen(null);
        }
    }

    private void sendMovementPacket(int moveType){
        SCPacketHandler.INSTANCE.sendToServer(new PuzzleActionPacket(moveType,tile.getBlockPos()));
    }

    private static final float[][] NODE_COLORS = {
            {0,0,0,0},
            {1,0,0,1},
            {1,1,0,1},
            {0,1,0,1}
    };

    @Override
    public void render(GuiGraphics graphics, int mx, int my, float pTicks) {
        PoseStack matrices = graphics.pose();
        int[][] pattern = tile.getPuzzlePattern();
        ClientHelpers.bindText(MAIN_GUI);
        int offsY = -14;
        RenderingTools.blitWithBlend(matrices,relX,relY + offsY,0,0,256,256,256,256,0,1f);

        ClientHelpers.bindText(TRIES);
        RenderingTools.blitWithBlend(matrices,relX - 70 - 10,relY + offsY + 110,0,0,60,18,60,18,0,1f);
        graphics.drawString(font,"Moves: "+tile.getRemainingTries(),relX - 64 - 10,relY + offsY + 115,0xffffff);

        if (pattern == null) return;
        ClientHelpers.bindText(NODE);
        RenderSystem.enableBlend();
        float p = ((float)Math.sin(ticker/10f)*0.5f + 1);
        for (int i = 0; i < pattern.length;i++){
            for (int j = 0; j < pattern[i].length;j++){
                int num = pattern[i][j];
                if (num == 0) continue;
                RenderSystem.setShaderColor(NODE_COLORS[num][0], NODE_COLORS[num][1], NODE_COLORS[num][2], 1f);
                if (Helpers.isIn2DArrayBounds(pattern,i,j+1)) {
                    int right = pattern[i][j + 1];

                    if (right == num) {
                        int initX = relX + j * 14 + 6 + 6;
                        float initY = relY + i * 14 - 8 + 3.5f;

                        RenderingTools.gradientBarHorizontal(matrices, initX, initY, initX + 14, initY + 5, 1, 1, 1, (float)FDMathHelper.clamp(0,p,1));
                    }
                }
                if (Helpers.isIn2DArrayBounds(pattern,i+1,j)) {
                    int down = pattern[i + 1][j];
                    if (down == num) {
                        int initX = relX + j * 14 + 6 + 4;
                        int initY = relY + i * 14 - 8 + 6;
                        RenderingTools.gradientBarVertical(matrices, initX, initY, initX + 4, initY + 14, 1, 1, 1, (float)FDMathHelper.clamp(0,p,1));
                    }
                }
                int node = pattern[i][j];
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(NODE_COLORS[node][0],NODE_COLORS[node][1],NODE_COLORS[node][2],NODE_COLORS[node][3] * (p + 0.3f) );
                RenderingTools.blitWithBlend(matrices, relX + j * 14 + 20 - 14, relY + i * 14 - 8, 0, 0, 12, 12, 12, 12,0,1f);
            }
        }

        RenderSystem.disableBlend();



        RenderSystem.setShaderColor(1,1,1,1);
        ClientHelpers.bindText(TWIGS);
        double v = VALUE.getValue();
        for (int i = 0; i < 6; i ++ ){
            int xp = (int)(relX + (i+1) * 23* (1-v));
            RenderingTools.blitWithBlend(matrices,xp - 20,relY - 18,(i % 3) *40,0,40,256,256,256,0,1f);
            int xp2 = (int)((i+1) * 23* (1-v));
            RenderingTools.blitWithBlend(matrices,relX + 230 - xp2,relY - 18,(i % 3) *40 + 120,0,40,256,256,256,0,1f);
        }


        super.render(graphics, mx, my, pTicks);
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
