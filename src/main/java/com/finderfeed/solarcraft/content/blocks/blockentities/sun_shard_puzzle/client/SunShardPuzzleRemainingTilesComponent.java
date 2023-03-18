package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.client.screens.FDScreenComponent;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SunShardPuzzleRemainingTilesComponent extends FDScreenComponent {

    private Puzzle localPuzzle;
    private int frameThickness;
    private double deltaY;

    protected SunShardPuzzleRemainingTilesComponent(DefaultScreen screen,Puzzle puzzle, int x, int y, int frameThickness){
        super(screen,x,y);
        this.localPuzzle = puzzle;
        this.frameThickness = frameThickness;
    }


    @Override
    public void init() {

    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float pticks) {
        var tiles = localPuzzle.getRemainingTiles();

        for (int i = 0; i < tiles.size(); i++){
            PuzzleTile tile = tiles.get(i);

            int xi = ((i+2) % 2) * (16 + frameThickness * 2);
            int yi = (i / 2) * (16 + frameThickness * 2) - (int)deltaY;

            int hwidth = 8 + frameThickness;

            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            ClientHelpers.bindText(new ResourceLocation(SolarCraft.MOD_ID,
                    "textures/misc/solar_shard_puzzle_tiles/" + tile.getTileType().getName() + ".png"));
            matrices.pushPose();
            matrices.translate(x + xi + hwidth,y + yi + hwidth,0);
            matrices.mulPose(Vector3f.ZP.rotationDegrees(tile.getRotation()));



            Matrix4f m = matrices.last().pose();
            builder.vertex(m,-8,8,screen.getBlitOffset() - 10).uv(0,1).color(1f,1,1,1).endVertex();
            builder.vertex(m,8,8,screen.getBlitOffset() - 10).uv(1,1).color(1f,1,1,1).endVertex();
            builder.vertex(m,8,-8,screen.getBlitOffset() - 10).uv(1,0).color(1f,1,1,1).endVertex();
            builder.vertex(m,-8,-8,screen.getBlitOffset() - 10).uv(0,0).color(1f,1,1,1).endVertex();
            matrices.popPose();
            BufferUploader.drawWithShader(builder.end());
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void mouseClicked(double x, double y, int action) {
        if (this.screen instanceof SunShardPuzzleScreen screen &&
                this.isMouseInBounds(x,y,(16 + frameThickness * 2)*2,300)){
            int tile = getTileUnderMouse(x,y);
            if (tile != -1){
                screen.setHeldTile(localPuzzle.getRemainingTiles().get(tile));
                localPuzzle.getRemainingTiles().remove(tile);
            }
        }
    }

    private int getTileUnderMouse(double x,double y){
        int xi = (int)(x);
        int yi = (int)(y + deltaY);
        int id = (yi / (16 + frameThickness * 2)) * 2 + xi / (16 + frameThickness * 2);
        var tiles = localPuzzle.getRemainingTiles();
        if (id < tiles.size()){
            return id;
        }else{
            return -1;
        }
    }

    @Override
    public void mouseReleased(double x, double y, int action) {

    }

    @Override
    public void mouseDragged(double xPos, double yPos, int button, double dragLeftRight, double dragUpDown) {

    }

    @Override
    public void keyPressed(int key, int scanCode, int modifiers) {

    }

    @Override
    public void mouseScrolled(double mousePosX, double mousePosY, double delta) {
        this.deltaY = Mth.clamp(deltaY-delta*5,0,10000);
    }
}
