package com.finderfeed.solarforge.magic_items.blocks.render;

import com.finderfeed.solarforge.events.other_events.ModelRegistryEvents;
import com.finderfeed.solarforge.for_future_library.RenderingTools;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RadiantPortalCreatorTile;
import com.finderfeed.solarforge.magic_items.blocks.rendering_models.RadiantPortal;
import com.finderfeed.solarforge.registries.ModelLayersRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PortalCreatorRenderer implements BlockEntityRenderer<RadiantPortalCreatorTile> {

    private static final ResourceLocation LOCATION = new ResourceLocation("solarforge","textures/block/radiant_portal.png");

    private RadiantPortal MODEL;

    public PortalCreatorRenderer(BlockEntityRendererProvider.Context ctx){
        MODEL = new RadiantPortal(ctx.bakeLayer(ModelLayersRegistry.RADIANT_PORTAL_CREATOR_MODEL));
    }

    @Override
    public void render(RadiantPortalCreatorTile radiantPortalCreatorTile, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.ZN.rotationDegrees(180));
        poseStack.translate(-0.5f,-1.5f,0.5f);
        MODEL.renderToBuffer(poseStack,multiBufferSource.getBuffer(RenderType.text(LOCATION)),i,OverlayTexture.NO_OVERLAY,1,1,1,1);
        poseStack.popPose();
    }
}
