package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ClearingRitualCrystalISTER extends BlockEntityWithoutLevelRenderer {
    public ClearingRitualCrystalISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
        super(p_172550_, p_172551_);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transform, PoseStack matrices, MultiBufferSource src, int light, int overlay) {

        matrices.pushPose();
        matrices.translate(0.5,0.5,0.5);
        if (transform == ItemDisplayContext.GUI){
            matrices.translate(0,0,1);
        }
        if (transform != ItemDisplayContext.GROUND) {
            if (Minecraft.getInstance().level != null) {
                matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),RenderingTools.getTime(Minecraft.getInstance().level, Minecraft.getInstance().getFrameTime())));
//                matrices.mulPose(Vector3f.YP.rotationDegrees(RenderingTools.getTime(Minecraft.getInstance().level, Minecraft.getInstance().getFrameTime())));
            }
        }
        matrices.scale(0.25f,0.25f,0.25f);
        RenderingTools.renderEntityObjModel(OBJModels.CLEARING_RITUAL_CRYSTAL,matrices,src,light,overlay,(m)->{});

        matrices.popPose();
    }
}
