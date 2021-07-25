package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class SolarForgeBlockEntityRenderer implements BlockEntityRenderer<SolarForgeBlockEntity> {

    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/solar_forge_block.png");
    public final ResourceLocation LOCPETALS = new ResourceLocation("solarforge","textures/misc/solar_forge_petals.png");
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/ray_into_skyy.png");
    public final SolarForgeBlockModelTrue model = new SolarForgeBlockModelTrue();
    public final SolarForgePetalsTrue petals = new SolarForgePetalsTrue();
    public final SolarForgePetalsTrue petals2 = new SolarForgePetalsTrue();
    public final ModelPart ray = new ModelPart(16,16,0,0);

    public SolarForgeBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {

        ray.addBox(-4,0,-4,4,1600,4,1);

    }

    @Override
    public boolean shouldRenderOffScreen(SolarForgeBlockEntity p_188185_1_) {
        return true;
    }

    @Override
    public void render(SolarForgeBlockEntity entity, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light2, int light) {

        //matrices.mulPose(Vector3f.ZN.rotationDegrees(180));
        if (entity.getLevel().getDayTime() % 24000 <= 13000 && entity.getLevel().canSeeSky(entity.getBlockPos().above())) {
            matrices.pushPose();
            ray.setPos(1.78f, 0, 1.78f);
            matrices.translate(0.5f, 0, 0.5f);
            if (entity.getLevel().getDayTime() % 24000 <= 13000) {

                matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime() % 360 + partialTicks) * 2));
            }
            VertexConsumer vertex = buffer.getBuffer(RenderType.text(RAY));
            // vertex = buffer.getBuffer(RenderType.text(RAY));
            ray.render(matrices, vertex, light2, light);
            matrices.popPose();
        }
        matrices.pushPose();
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(LOC));

        matrices.mulPose(Vector3f.ZN.rotationDegrees(180));
        matrices.translate(-0.5f,-1.5,0.5f);
        model.renderToBuffer(matrices,vertex,light2,light,255,255,255,255);
        matrices.translate(0,-1.5f,0);
        matrices.scale(1.3f,1.3f,1.3f);
        if (entity.getLevel().getDayTime() % 24000 <= 13000 && entity.getLevel().canSeeSky(entity.getBlockPos().above())){
            matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime()%360+partialTicks)*2));
        }
        vertex = buffer.getBuffer(RenderType.text(LOCPETALS));
        petals.renderToBuffer(matrices,vertex,light2,light,255,255,255,255);
        matrices.mulPose(Vector3f.YN.rotationDegrees(90));
        petals2.renderToBuffer(matrices,vertex,light2,light,255,255,255,255);

        matrices.popPose();

    }


}
