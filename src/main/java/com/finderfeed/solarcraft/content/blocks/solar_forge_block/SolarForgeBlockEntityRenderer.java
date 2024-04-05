package com.finderfeed.solarcraft.content.blocks.solar_forge_block;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.ModelLayersRegistry;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import static com.finderfeed.solarcraft.local_library.helpers.RenderingTools.*;

public class SolarForgeBlockEntityRenderer implements BlockEntityRenderer<SolarForgeBlockEntity> {

    public final ResourceLocation LOC = new ResourceLocation("solarcraft","textures/misc/solar_forge_block.png");
    public final ResourceLocation LOCPETALS = new ResourceLocation("solarcraft","textures/misc/solar_forge_petals.png");
    public final ResourceLocation RAY = new ResourceLocation("solarcraft","textures/misc/ray_into_skyy.png");
    public final SolarForgeBlockModel mainmodel;
    public final SolarForgePetalsModel petals;
    public final SolarForgePetalsModel petals2;





    public SolarForgeBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        SolarForgePetalsModel modelp = new SolarForgePetalsModel(ctx.bakeLayer(ModelLayersRegistry.SOLAR_FORGE_PETALS));
        petals = modelp;
        petals2 = modelp;
        mainmodel = new SolarForgeBlockModel(ctx.bakeLayer(ModelLayersRegistry.SOLAR_FORGE_MAIN_MODEL));
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

//        matrices.mulPose(Vector3f.ZN.rotationDegrees(180));
        matrices.mulPose(rotationDegrees(ZN(),180));
        matrices.translate(-0.5f,-1.5,0.5f);
        mainmodel.renderToBuffer(matrices,vertex,light2,light,255,255,255,255);
        matrices.translate(0,-1.5f,0);
        matrices.scale(1.3f,1.3f,1.3f);
        if (entity.getLevel().getDayTime() % 24000 <= 13000 && entity.getLevel().canSeeSky(entity.getBlockPos().above())){
//            matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime()%360+partialTicks)*2));
            matrices.mulPose(rotationDegrees(YP(),(entity.getLevel().getGameTime()%360+partialTicks)*2));
        }
        vertex = buffer.getBuffer(RenderType.text(LOCPETALS));
        petals.renderToBuffer(matrices,vertex,light2,light,255,255,255,255);
//        matrices.mulPose(Vector3f.YN.rotationDegrees(90));
        matrices.mulPose(rotationDegrees(YN(),90));
        petals2.renderToBuffer(matrices,vertex,light2,light,255,255,255,255);

        matrices.popPose();

    }


}
