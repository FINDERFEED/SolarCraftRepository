package com.finderfeed.solarforge.client.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.blockentities.CrystalEnergyVinesTile;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.crystal_energy_vines_puzzle.PuzzleActionPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public class CrystalEnergyVinesPuzzleScreen extends SolarCraftScreen {

    public static final ResourceLocation MAIN_GUI = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/crystal_energy_vines_puzzle.png");
    public static final ResourceLocation NODE = new ResourceLocation(SolarForge.MOD_ID,"textures/particle/solar_strike_particle.png");

    private int ticker = 0;

    public CrystalEnergyVinesTile tile;

    public CrystalEnergyVinesPuzzleScreen(CrystalEnergyVinesTile tile){
        this.tile = tile;
    }

    @Override
    public boolean keyPressed(int keyCode, int idk, int type) {
        if (keyCode == GLFW.GLFW_KEY_UP){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_UP);
        }else if (keyCode == GLFW.GLFW_KEY_DOWN){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_DOWN);
        }else if (keyCode == GLFW.GLFW_KEY_LEFT){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_LEFT);
        }else if (keyCode == GLFW.GLFW_KEY_RIGHT){
            sendMovementPacket(CrystalEnergyVinesTile.MOVE_RIGHT);
        }

        return super.keyPressed(keyCode, idk, type);

    }

    @Override
    public void tick() {
        super.tick();
        ticker++;
    }

    private void sendMovementPacket(int moveType){
        SolarForgePacketHandler.INSTANCE.sendToServer(new PuzzleActionPacket(moveType,tile.getBlockPos()));
    }

    private static final float[][] NODE_COLORS = {
            {0,0,0,0},
            {1,0,0,1},
            {1,1,0,1},
            {0,1,0,1}
    };

    @Override
    public void render(PoseStack matrices, int mx, int my, float pTicks) {
        int[][] pattern = tile.getPuzzlePattern();
        ClientHelpers.bindText(MAIN_GUI);
        int offsY = -10;
        blit(matrices,relX,relY + offsY,0,0,256,256);

        if (pattern == null) return;
        ClientHelpers.bindText(NODE);
        RenderSystem.enableBlend();
        for (int i = 0; i < pattern.length;i++){
            for (int j = 0; j < pattern[i].length;j++){
                int node = pattern[i][j];
                if (node != 0) {
                    RenderSystem.setShaderColor(NODE_COLORS[node][0],NODE_COLORS[node][1],NODE_COLORS[node][2],NODE_COLORS[node][3] * ((float)Math.sin(ticker/10f)*0.5f + 1));
                    blit(matrices, relX + j * 14 + 20 - 14, relY + i * 14 - 4, 0, 0, 12, 12, 12, 12);

                }
            }
        }
        RenderSystem.disableBlend();

        Gui.drawString(matrices,font,tile.getRemainingTries()+" - tries",relX + 200,relY+ 200,0xffffff);
        super.render(matrices, mx, my, pTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
