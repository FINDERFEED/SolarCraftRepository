package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile;

import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ClearingRitualTileRenderer extends TileEntityRenderer<ClearingRitualMainTile> {
    public ClearingRitualTileRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ClearingRitualMainTile tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        this.renderModel(matrices,buffer,light,overlay,pticks);
    }


    private void renderModel(PoseStack matrices,MultiBufferSource src,int light,int overlay,float pticks){
        matrices.pushPose();
        float time = RenderingTools.getTime(Minecraft.getInstance().level,pticks);
        matrices.translate(0.5,0,0.5);
        matrices.scale(0.5f,0.5f,0.5f);
        matrices.pushPose();
        RenderingTools.renderObjModel(OBJModels.CLEARING_RITUAL_MAIN_BLOCK_LOWER,matrices,src,light,overlay,(a)->{});
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0,2,0);
        matrices.mulPose(Vector3f.YP.rotationDegrees(time % 360));
        RenderingTools.renderObjModel(OBJModels.CLEARING_RITUAL_MAIN_BLOCK_PETALS,matrices,src,light,overlay,(a)->{});
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0,4,0);
        matrices.scale(0.7f,0.7f,0.7f);
        matrices.mulPose(Vector3f.YP.rotationDegrees(-time % 360));
        RenderingTools.renderObjModel(OBJModels.CLEARING_RITUAL_MAIN_BLOCK_TOP,matrices,src,light,overlay,(a)->{});
        matrices.popPose();
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0,2,0);

        Random random = new Random(Minecraft.getInstance().level.getGameTime());
        random.nextFloat();


        matrices.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ClearingRitualMainTile tile) {
        return true;
    }

    @Override
    public boolean shouldRender(ClearingRitualMainTile tile, Vec3 idk) {
        return true;
    }
}
