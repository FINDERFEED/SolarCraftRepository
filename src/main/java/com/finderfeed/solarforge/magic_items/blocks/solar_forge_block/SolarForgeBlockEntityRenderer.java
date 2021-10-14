package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block;

import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.registries.ModelLayersRegistry;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class SolarForgeBlockEntityRenderer implements BlockEntityRenderer<SolarForgeBlockEntity> {

    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/solar_forge_block.png");
    public final ResourceLocation LOCPETALS = new ResourceLocation("solarforge","textures/misc/solar_forge_petals.png");
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/ray_into_skyy.png");
    public final SolarForgeBlockModelTrue model;
    public final SolarForgePetalsTrue petals;
    public final SolarForgePetalsTrue petals2;


    public SolarForgeBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        SolarForgePetalsTrue modelp = new SolarForgePetalsTrue(ctx.bakeLayer(ModelLayersRegistry.SOLAR_FORGE_PETALS));
        petals = modelp;
        petals2 = modelp;
        model = new SolarForgeBlockModelTrue(ctx.bakeLayer(ModelLayersRegistry.SOLAR_FORGE_MAIN_MODEL));
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

            RenderingTools.renderRay(matrices,buffer,0.4f,100, Direction.UP,true,2,partialTicks);
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
