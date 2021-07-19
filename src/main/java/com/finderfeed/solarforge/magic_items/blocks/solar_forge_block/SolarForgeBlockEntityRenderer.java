package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block;

import com.mojang.blaze3d.matrix.MatrixStack;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class SolarForgeBlockEntityRenderer extends TileEntityRenderer<SolarForgeBlockEntity> {

    public final ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/solar_forge_block.png");
    public final ResourceLocation LOCPETALS = new ResourceLocation("solarforge","textures/misc/solar_forge_petals.png");
    public final ResourceLocation RAY = new ResourceLocation("solarforge","textures/misc/ray_into_skyy.png");
    public final SolarForgeBlockModelTrue model = new SolarForgeBlockModelTrue();
    public final SolarForgePetalsTrue petals = new SolarForgePetalsTrue();
    public final SolarForgePetalsTrue petals2 = new SolarForgePetalsTrue();
    public final ModelRenderer ray = new ModelRenderer(16,16,0,0);

    public SolarForgeBlockEntityRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
        ray.addBox(-4,0,-4,4,1600,4,1);

    }

    @Override
    public boolean shouldRenderOffScreen(SolarForgeBlockEntity p_188185_1_) {
        return true;
    }

    @Override
    public void render(SolarForgeBlockEntity entity, float partialTicks, MatrixStack matrices, IRenderTypeBuffer buffer, int light2, int light) {

        //matrices.mulPose(Vector3f.ZN.rotationDegrees(180));
        if (entity.getLevel().getDayTime() % 24000 <= 13000 && entity.getLevel().canSeeSky(entity.getBlockPos().above())) {
            matrices.pushPose();
            ray.setPos(1.78f, 0, 1.78f);
            matrices.translate(0.5f, 0, 0.5f);
            if (entity.getLevel().getDayTime() % 24000 <= 13000) {

                matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime() % 360 + partialTicks) * 2));
            }
            IVertexBuilder vertex = buffer.getBuffer(RenderType.text(RAY));
            // vertex = buffer.getBuffer(RenderType.text(RAY));
            ray.render(matrices, vertex, light2, light);
            matrices.popPose();
        }
        matrices.pushPose();
        IVertexBuilder vertex = buffer.getBuffer(RenderType.text(LOC));

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
