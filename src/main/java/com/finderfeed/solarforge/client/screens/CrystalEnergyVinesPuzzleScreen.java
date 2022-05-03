package com.finderfeed.solarforge.client.screens;

import com.finderfeed.solarforge.magic.blocks.blockentities.CrystalEnergyVinesTile;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.crystal_energy_vines_puzzle.PuzzleActionPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import org.lwjgl.glfw.GLFW;

public class CrystalEnergyVinesPuzzleScreen extends SolarCraftScreen {

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

    private void sendMovementPacket(int moveType){
        SolarForgePacketHandler.INSTANCE.sendToServer(new PuzzleActionPacket(moveType,tile.getBlockPos()));
    }

    @Override
    public void render(PoseStack matrices, int mx, int my, float pTicks) {
        int[][] pattern = tile.getPuzzlePattern();
        if (pattern == null) return;
        for (int i = 0; i < pattern.length;i++){
            for (int j = 0; j < pattern[i].length;j++){
                Gui.drawString(matrices,font,""+pattern[i][j],relY + j * 10,relX + i * 10 - 30,0xffffff);
            }
        }

        Gui.drawString(matrices,font,tile.getRemainingTries()+" - tries",relX,relY+ 200,0xffffff);
        super.render(matrices, mx, my, pTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
