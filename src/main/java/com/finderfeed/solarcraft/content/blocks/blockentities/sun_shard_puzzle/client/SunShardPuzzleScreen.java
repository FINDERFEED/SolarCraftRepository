package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity.SunShardPuzzleBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTile;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_tiles.PuzzleTileType;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzlePutTilePacket;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzleTakeTilePacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class SunShardPuzzleScreen extends DefaultScreen {

    private Puzzle localPuzzle;
    private PuzzleTile heldTile = null;
    private BlockPos tilePos;
    private Map<PuzzleTileType,List<RenderedPuzzleTile>> renderedTiles = new HashMap<>();


    public SunShardPuzzleScreen(Puzzle puzzle, BlockPos tilePosition){
        this.localPuzzle = puzzle;
        this.tilePos = tilePosition;
    }



    @Override
    protected void init() {
        super.init();
        this.reinitPuzzle();
        this.addSCComponent("remaining_tiles",new SunShardPuzzleRemainingTilesComponent(
                this,localPuzzle,relX + 200,relY,3
        ));
    }

    public void reinitPuzzle(){
        this.renderedTiles.clear();
        for (int y = 0; y < Puzzle.PUZZLE_SIZE;y++){
            for (int x = 0; x < Puzzle.PUZZLE_SIZE;x++){
                PuzzleTile tile = localPuzzle.getTileAtPos(x,y);
                if (tile != null) {
                    int xi = relX + x * 16;
                    int yi = relY + y * 16;
//                    System.out.println(tile);
                    RenderedPuzzleTile rt = new RenderedPuzzleTile(tile, xi, yi,tile.getRotation());
                    List<RenderedPuzzleTile> tiles = renderedTiles.get(tile.getTileType());
                    if (tiles != null) {
                        tiles.add(rt);
                    } else {
                        renderedTiles.put(tile.getTileType(),new ArrayList<>(List.of(rt)));
                    }
                }
            }
        }

    }


    @Override
    public void tick() {
        super.tick();
        Level level = Minecraft.getInstance().level;
        if (!(level.getBlockEntity(tilePos) instanceof SunShardPuzzleBlockEntity)){
//            Minecraft.getInstance().setScreen(null);
        }
    }



    @Override
    public boolean mouseClicked(double x, double y, int action) {
        int xi = ((int)x - relX)/16;
        int yi = ((int)y - relY)/16;
        if (xi >= 0 && xi < Puzzle.PUZZLE_SIZE &&
                yi >= 0 && yi < Puzzle.PUZZLE_SIZE){
            PuzzleTile tile = localPuzzle.getTileAtPos(xi,yi);
            if (tile != null && !tile.isFixed()){
                heldTile = tile;

                localPuzzle.setTileAtPos(null,xi,yi);
             //   SCPacketHandler.INSTANCE.sendToServer(new SunShardPuzzleTakeTilePacket(this.tilePos,xi,yi));
                this.reinitPuzzle();
            }
        }
        return super.mouseClicked(x, y, action);
    }

    @Override
    public boolean mouseReleased(double x, double y, int action) {
        if (heldTile != null) {
            int xi = ((int) x - relX) / 16;
            int yi = ((int) y - relY) / 16;
            if (xi >= 0 && xi < Puzzle.PUZZLE_SIZE &&
                    yi >= 0 && yi < Puzzle.PUZZLE_SIZE) {
                if (localPuzzle.putTileAtPos(heldTile, xi, yi)) {
               //     SCPacketHandler.INSTANCE.sendToServer(new SunShardPuzzlePutTilePacket(this.tilePos, heldTile,xi,yi));
                    heldTile = null;
                    this.reinitPuzzle();
                }else{
                    localPuzzle.getRemainingTiles().add(heldTile);
                    heldTile = null;
                }
            }else{
                localPuzzle.getRemainingTiles().add(heldTile);
                heldTile = null;
            }
        }
        return super.mouseReleased(x, y, action);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (heldTile != null){
            if (key == GLFW.GLFW_KEY_R){
                heldTile.rotate();
            }
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public void render(PoseStack matrices, int mx, int my, float pticks) {
        super.render(matrices, mx, my, pticks);
        fill(matrices,relX,relY,relX + getScreenWidth(),relY + getScreenHeight(),0xff111111);
        this.renderPuzzle(matrices,mx,my,pticks);
        this.renderHeldTile(matrices,mx,my,pticks);
        this.renderComponents(matrices,mx,my,pticks,"remaining_tiles");

    }

    private void renderHeldTile(PoseStack matrices,int mx,int my,float pticks){
        if (heldTile != null){
            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            ClientHelpers.bindText(new ResourceLocation(SolarCraft.MOD_ID,
                    "textures/misc/solar_shard_puzzle_tiles/" + heldTile.getTileType().getName() + ".png"));
            matrices.pushPose();
            matrices.translate(mx,my,0);
            matrices.mulPose(Vector3f.ZP.rotationDegrees(heldTile.getRotation()));
            float time = RenderingTools.getTime(Minecraft.getInstance().level,pticks);
            matrices.mulPose(Vector3f.ZP.rotationDegrees(((float)Math.sin(time))*10));
            matrices.translate(-8,-8,0);
            Matrix4f m = matrices.last().pose();
            int x = 0;
            int y = 0;
            builder.vertex(m,x,y+16,this.getBlitOffset() - 10).uv(0,1).color(1f,1,1,1).endVertex();
            builder.vertex(m,x+16,y+16,this.getBlitOffset() - 10).uv(1,1).color(1f,1,1,1).endVertex();
            builder.vertex(m,x + 16,y,this.getBlitOffset() - 10).uv(1,0).color(1f,1,1,1).endVertex();
            builder.vertex(m,x,y,this.getBlitOffset() - 10).uv(0,0).color(1f,1,1,1).endVertex();



            matrices.popPose();
            BufferUploader.drawWithShader(builder.end());
        }
    }

    private void renderPuzzle(PoseStack matrices,int xm,int my,float pticks){
        for (var entry : renderedTiles.entrySet()){
            PuzzleTileType tileType = entry.getKey();
            List<RenderedPuzzleTile> tiles = entry.getValue();
            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            ClientHelpers.bindText(new ResourceLocation(SolarCraft.MOD_ID,
                    "textures/misc/solar_shard_puzzle_tiles/" + tileType.getName() + ".png"));
            for (RenderedPuzzleTile tile : tiles){
                matrices.pushPose();
                matrices.translate(tile.x + 8,tile.y + 8,0);
                matrices.mulPose(Vector3f.ZP.rotationDegrees(tile.rotation()));
                Matrix4f m = matrices.last().pose();
                int x = 0;
                int y = 0;
                builder.vertex(m,-8,8,this.getBlitOffset()).uv(0,1).color(1f,1,1,1).endVertex();
                builder.vertex(m,8,8,this.getBlitOffset()).uv(1,1).color(1f,1,1,1).endVertex();
                builder.vertex(m,8,-8,this.getBlitOffset()).uv(1,0).color(1f,1,1,1).endVertex();
                builder.vertex(m,-8,-8,this.getBlitOffset()).uv(0,0).color(1f,1,1,1).endVertex();



                matrices.popPose();
            }
            BufferUploader.drawWithShader(builder.end());
        }
    }

    @Override
    public int getScreenWidth() {
        return 192;
    }

    @Override
    public int getScreenHeight() {
        return 192;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void setHeldTile(PuzzleTile heldTile) {
        this.heldTile = heldTile;
    }

    public PuzzleTile getHeldTile() {
        return heldTile;
    }



    public record RenderedPuzzleTile(PuzzleTile tile, int x, int y, int rotation){}
}
