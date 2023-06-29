package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.content.blocks.solar_energy.SolarEnergyGeneratorTile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.rendering_models.SolarEnergyGeneratorModel;
import com.finderfeed.solarcraft.registries.ModelLayersRegistry;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;


import java.util.Map;

public class EnergyGeneratorTileRender implements BlockEntityRenderer<SolarEnergyGeneratorTile> {
    public final ResourceLocation RAY = new ResourceLocation("solarcraft","textures/misc/ray_into_sky.png");
    public final ResourceLocation BLOCK = new ResourceLocation("solarcraft","textures/block/solar_energy_generator.png");
    public final ResourceLocation RAYY = new ResourceLocation("solarcraft","textures/misc/ray_into_skyy.png");
    public final SolarEnergyGeneratorModel model;
    private final ResourceLocation SHADER_LOCATION = new ResourceLocation("solarcraft","shaders/post/lens.json");
    public static PostChainPlusUltra SHADER;

    public EnergyGeneratorTileRender(BlockEntityRendererProvider.Context ctx) {
        model = new SolarEnergyGeneratorModel(ctx.bakeLayer(ModelLayersRegistry.SOLAR_ENERGY_GEN_LAYER));
    }

    @Override
    public void render(SolarEnergyGeneratorTile entity, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light1, int light2) {



        for (BlockPos a : entity.getBindedTiles()) {
                matrices.pushPose();
                matrices.translate(0.5f, 0.6f, 0.5f);
                Vec3 parentPos = new Vec3(entity.getBlockPos().getX() - 0.5d, entity.getBlockPos().getY() + 0.5d, entity.getBlockPos().getZ() - 0.5d);
                Vec3 childPos = new Vec3(a.getX() - 0.5d, a.getY() + 0.4d, a.getZ() - 0.5d);
                Vec3 vector = new Vec3((childPos.x - parentPos.x), (childPos.y - parentPos.y), (childPos.z - parentPos.z));
                Vec3 horizontalVector = new Vec3(childPos.x - parentPos.x, 0, childPos.z - parentPos.z);
//                Vec3 verticalVector = new Vec3(0, childPos.y - parentPos.y, 0).normalize();

                if (horizontalVector.x >= 0) {
//                    matrices.mulPose(Vector3f.YN.rotationDegrees((float) Math.toDegrees(Math.acos(-horizontalVector.normalize().z))));
                    matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),(float) Math.toDegrees(Math.acos(-horizontalVector.normalize().z))));
                } else {
//                    matrices.mulPose(Vector3f.YN.rotationDegrees(180 + (float) Math.toDegrees(Math.acos(horizontalVector.normalize().z))));
                    matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),180 + (float) Math.toDegrees(Math.acos(horizontalVector.normalize().z))));
                }

                matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XN(),(float) Math.toDegrees(Math.acos(vector.normalize().y))));
//                matrices.mulPose(Vector3f.XN.rotationDegrees((float) Math.toDegrees(Math.acos(vector.normalize().y))));


                float percent = (float) (Helpers.getGipotenuza(Helpers.getGipotenuza(vector.x, vector.z), vector.y));

                VertexConsumer vertex = buffer.getBuffer(RenderType.text(RAY));
                Matrix4f matrix = matrices.last().pose();
                float mod = 0.15f;

                //1
                vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

                vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                //2
                vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

                vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                //3
                vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

                vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                //4
                vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

                vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, 0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, percent, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                matrices.popPose();
        }
        VertexConsumer bufferbuilder = buffer.getBuffer(RenderType.text(BLOCK));
        matrices.pushPose();

        float time = (entity.getLevel().getGameTime() + partialTicks)*8%360;
        matrices.translate(0.5,0.6,0.5);
        boolean flag = Multiblocks.ENERGY_GENERATOR.check(entity.getLevel(),entity.getBlockPos(),true) && Helpers.isDay(entity.getLevel())
                && entity.getLevel().canSeeSky(entity.getBlockPos().above());
        if (flag) {
//            matrices.mulPose(Vector3f.YP.rotationDegrees(time));
//
//            matrices.mulPose(Vector3f.ZP.rotationDegrees(-time));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),-time));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),time));
        }
        model.renderCore(matrices,bufferbuilder,light1,light2,255,255,255,255);
        matrices.popPose();



        matrices.pushPose();
        matrices.translate(0.5,0.6,0.5);
        if (flag) {
//            matrices.mulPose(Vector3f.YP.rotationDegrees(-time));

//            matrices.mulPose(Vector3f.ZP.rotationDegrees(time));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),-time));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),time));
        }
        matrices.scale(0.8f,0.8f,0.8f);
        model.renderRing2(matrices,bufferbuilder,light1,light2,255,255,255,255);
        matrices.popPose();



        matrices.pushPose();
        matrices.translate(0.5,0.6,0.5);
        if (flag) {
//            matrices.mulPose(Vector3f.YP.rotationDegrees(time));
//
//            matrices.mulPose(Vector3f.ZP.rotationDegrees(-time));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),time));
            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),-time));
        }
        model.renderRing2(matrices,bufferbuilder,light1,light2,255,255,255,255);
        matrices.popPose();


        matrices.pushPose();
//        matrices.mulPose(Vector3f.XP.rotationDegrees(180));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XP(),180));
        matrices.translate(0.5,-1.5,-0.5);
        model.renderBase(matrices,bufferbuilder,light1,light2,255,255,255,255);
        matrices.popPose();

        if (flag) {
            doShader(matrices,entity);
            matrices.pushPose();

            matrices.translate(0.5f, 1.3f, 0.5f);


//            matrices.mulPose(Vector3f.YP.rotationDegrees((entity.getLevel().getGameTime() % 360 + partialTicks) * 2));

            matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),(entity.getLevel().getGameTime() % 360 + partialTicks) * 2));


            VertexConsumer vertex = buffer.getBuffer(RenderType.text(RAYY));

            Matrix4f matrix = matrices.last().pose();
            float mod = 0.15f;

            //1
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            //2
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            //3
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, -0.5F * mod, 0, 0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, 0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            //4
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

            vertex.vertex(matrix, 0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, 0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 1600, -0.5F * mod).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            vertex.vertex(matrix, -0.5F * mod, 0, -0.5F * mod).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
            matrices.popPose();
        }

    }


    private void doShader(PoseStack matrices,SolarEnergyGeneratorTile tile){
        Vec3 d1 = new Vec3(tile.getBlockPos().getX(),tile.getBlockPos().getY(),tile.getBlockPos().getZ());
        if (!RenderingTools.isBoxVisible(new AABB(d1,d1.add(1,1,1)))) return;
        if (FDMathHelper.canSeeTileEntity(tile,Minecraft.getInstance().player) && Minecraft.getInstance().cameraEntity != null) {
            Vec3 playerPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            Vec3 tilePos = new Vec3(tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY() + 0.5, tile.getBlockPos().getZ() + 0.5);
            float dist = (float) new Vec3(tilePos.x - playerPos.x, tilePos.y - playerPos.y, tilePos.z - playerPos.z).length()*50;



            matrices.pushPose();
            matrices.translate(0.5, 0.6, 0.5);
            Matrix4f modelview = matrices.last().pose();
            this.loadShader(tile,SHADER_LOCATION, new UniformPlusPlus(Map.of(
                    "projection", RenderSystem.getProjectionMatrix(),
                    "modelview", modelview,
                    "distance", dist,
                    "intensity", 2f,
                    "innerControl",4f,
                    "outerControl",0.045f
            )));
            matrices.popPose();
        }
    }


    private void loadShader(BlockEntity tile,ResourceLocation LOC, UniformPlusPlus uniforms){
        if (SHADER == null){
            try {
                SHADER = new PostChainPlusUltra(LOC,uniforms);
                SHADER.resize(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight());
                RenderingTools.addActivePostShader(tile.toString(),uniforms,SHADER);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Failed to load shader in EnergyGeneratorTileRender.java");
            }

        }else{
            RenderingTools.addActivePostShader(tile.toString(),uniforms,SHADER);
        }
    }

    @Override
    public boolean shouldRenderOffScreen(SolarEnergyGeneratorTile p_188185_1_) {
        return true;
    }
}
