package com.finderfeed.solarforge.content.blocks.render;

import com.finderfeed.solarforge.content.blocks.blockentities.DimensionCoreTile;
import com.finderfeed.solarforge.content.blocks.render.abstracts.TileEntityRenderer;
import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class DimensionCoreRenderer extends TileEntityRenderer<DimensionCoreTile> {
    public DimensionCoreRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(DimensionCoreTile tile, float pticks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        if (tile.getLevel() == null || !tile.isStructureCorrect()) return;
        matrices.pushPose();
        matrices.translate(0.5,3.5,0.5);
        matrices.scale(0.95f,0.95f,0.95f);
        float time = RenderingTools.getTime(tile.getLevel(),pticks);
        matrices.mulPose(Vector3f.YN.rotationDegrees(time));
        float r;
        float gr;
        float b;
        if (Helpers.isDay(tile.getLevel())) {
            r = 1;
            gr = 1;
            b = 0;
        } else {
            r = 0.5f;
            gr = 0;
            b = 1f;
        }
        RenderingTools.renderObjModel(OBJModels.PORTAL_SPHERE,matrices,src,r,gr,b,light,overlay);

        matrices.popPose();
    }
}
