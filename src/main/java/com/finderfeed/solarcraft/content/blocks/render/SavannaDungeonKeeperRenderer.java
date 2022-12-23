package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.blocks.blockentities.SavannaDungeonKeeperTile;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SavannaDungeonKeeperRenderer extends TileEntityRenderer<SavannaDungeonKeeperTile> {

    public final ResourceLocation MAIN = new ResourceLocation("solarcraft","textures/misc/savanna_dungeon_keeper.png");


    public static final AABB BOX = new AABB(-3.5,-4.5,-3.5,4.5,-0.45,4.5);
    public static final AABB BOX2 = new AABB(-0.05,-2.95,-0.05,1.05,-2.05,1.05);

    public SavannaDungeonKeeperRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SavannaDungeonKeeperTile tile, float partialTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
//        RenderingTools.renderBox(matrices,src,BOX,1f, 228/255f, 138/255f,0.5f);

        this.renderTile(tile,partialTicks,matrices,src,light,overlay);

        if (tile.isActive()){
            this.renderEffects(tile,partialTicks,matrices,src,light,overlay);
        }
    }

    private void renderTile(SavannaDungeonKeeperTile tile, float partialTicks, PoseStack matrices, MultiBufferSource src, int light, int overlay){
        matrices.pushPose();
        VertexConsumer vertex = src.getBuffer(RenderType.text(MAIN));
        matrices.translate(0.5,0.5,0.5);
        Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();

        float time = (Minecraft.getInstance().level.getGameTime()+partialTicks)*5;

        float scale = (float)(Math.sin(time/30d) * 0.5f + 0.5f)*0.25f+0.25f;
        matrices.mulPose(quaternion);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(time%360));
        matrices.scale(1.5f*scale,1.5f*scale,1.5f*scale);
        Matrix4f matrix = matrices.last().pose();
        vertex.vertex(matrix, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.5,0.5,0.5);
        matrices.mulPose(quaternion);
        scale = (float)(Math.sin(time/30d + Math.PI) * 0.5f + 0.5f)*0.25f+0.25f;
        matrices.mulPose(Vector3f.ZP.rotationDegrees((time+45)%360));
        Matrix4f matrix2 = matrices.last().pose();
        matrices.scale(1.5f*scale,1.5f*scale,1.5f*scale);
        vertex.vertex(matrix2, -0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  -0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        matrices.popPose();
    }

    private void renderEffects(SavannaDungeonKeeperTile tile, float idk, PoseStack matrices, MultiBufferSource src, int light, int overlay){
        RenderingTools.renderBox(matrices,src,BOX,1f, 228/255f, 138/255f,0.5f);
//        RenderingTools.renderBox(RenderType.lightning(),matrices,src,BOX2,1f, 228/255f, 138/255f,0.25f);
        int t = tile.getActiveTime() % SavannaDungeonKeeperTile.SPAWN_EACH;

        Random random = new Random(tile.getLevel().getGameTime());
        if (t >= 0 && t <= 10){
            Vec3 base = new Vec3(0.5,0.5,0.5);
            for (BlockPos offs : SavannaDungeonKeeperTile.MONSTER_OFFSETS){
                Vec3 f = base.add(offs.getX(),offs.getY(),offs.getZ());
                RenderingTools.Lightning2DRenderer.renderLightning(matrices,
                        src,3,0.25f,0.15f,base,f,random,1f, 228/255f, 138/255f);
            }
            matrices.pushPose();
            matrices.mulPose(Vector3f.XN.rotationDegrees(90));
            for (Player pl : tile.playersInRange()){
                Vec3 offs = pl.position().subtract(Helpers.posToVec(tile.getBlockPos()).add(base));
                Vec3 f = base.add(offs.x,offs.y + pl.getBbHeight()/2f,offs.z);

                RenderingTools.Lightning2DRenderer.renderLightning(matrices,
                        src,3,0.25f,0.15f,base,f,random,1f, 228/255f, 138/255f);
            }
            matrices.popPose();
        }

    }


}
