package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
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
import org.joml.Matrix4f;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

import java.util.Map;

public class RuneEnergyPylonRenderer implements BlockEntityRenderer<RuneEnergyPylonTile> {
    public final ResourceLocation MAIN = new ResourceLocation("solarcraft","textures/misc/tile_energy_pylon.png");
    private final ResourceLocation SHADER_LOCATION = new ResourceLocation("solarcraft","shaders/post/energy_pylon.json");

    public static PostChainPlusUltra SHADER;

    public RuneEnergyPylonRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(RuneEnergyPylonTile tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        Vec3 d1 = new Vec3(tile.getBlockPos().getX(),tile.getBlockPos().getY(),tile.getBlockPos().getZ());
        if (FDMathHelper.canSeeTileEntity(tile,Minecraft.getInstance().player) && (Minecraft.getInstance().cameraEntity != null)
        && RenderingTools.isBoxVisible(new AABB(d1,d1.add(1,1,1)))) {

            Vec3 playerPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();


            Vec3 tilePos = new Vec3(tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY() + 0.5, tile.getBlockPos().getZ() + 0.5);


            float dist = ((float) new Vec3(tilePos.x - playerPos.x, tilePos.y - playerPos.y, tilePos.z - playerPos.z).length()
                     ) * 50f ;


            matrices.pushPose();
            matrices.translate(0.5, 0.5, 0.5);
            Matrix4f modelview = matrices.last().pose();
            this.loadShader(tile,SHADER_LOCATION, new UniformPlusPlus(Map.of(
                    "projection", RenderSystem.getProjectionMatrix(),
                    "modelview", new Matrix4f(modelview),
                    "distance", dist,
                    "intensity", 0.5f
            )));
            matrices.popPose();
        }
        if (tile.getEnergyType() != null) {
            VertexConsumer vertex = buffer.getBuffer(RenderType.text(new ResourceLocation("solarcraft", "textures/misc/tile_energy_pylon_" + tile.getEnergyType().id + ".png")));
            float time = (Minecraft.getInstance().level.getGameTime()+partialTicks);
            for (int i = 0; i < 8; i ++) {
                matrices.pushPose();

                matrices.translate(0.5, 0.5, 0.5f);
                matrices.scale(0.7f,0.7f,0.7f);
//                matrices.mulPose(Vector3f.YP.rotationDegrees(i*45+time%360));
                matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),i*45+time%360));
                Matrix4f matrix1 = matrices.last().pose();
                vertex.vertex(matrix1, -0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, -0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

                vertex.vertex(matrix1, -0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, -0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, 0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                vertex.vertex(matrix1, -0.5f, 0.5f, 1.5f).color(255, 255, 255, 255).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
                matrices.popPose();
            }
        }


        matrices.pushPose();
        VertexConsumer vertex = buffer.getBuffer(RenderType.text(MAIN));
        matrices.translate(0.5,0.5,0.5);



        Quaternionf quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();

        float time = (Minecraft.getInstance().level.getGameTime()+partialTicks)*5;

        matrices.mulPose(quaternion);
//        matrices.mulPose(Vector3f.ZP.rotationDegrees(time%360));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZP(),time%360));
        matrices.scale(1.5f,1.5f,1.5f);
        Matrix4f matrix = matrices.last().pose();
        vertex.vertex(matrix, -0.5f,0.5f,0).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,0.5f,0).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  0.5f,-0.5f,0).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix,  -0.5f,-0.5f,0).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.5,0.5,0.5);
        matrices.mulPose(quaternion);
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.ZN(),time%360));

        Matrix4f matrix2 = matrices.last().pose();
        matrices.scale(1.5f,1.5f,1.5f);
        vertex.vertex(matrix2, -0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  -0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        matrices.popPose();
    }


    private void loadShader(BlockEntity tile,ResourceLocation LOC, UniformPlusPlus uniforms){
        if (SHADER == null){
            try {
                SHADER = new PostChainPlusUltra(LOC,uniforms);
                SHADER.resize(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight());
                RenderingTools.addActivePostShader(tile.toString(),uniforms,SHADER);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Failed to load shader in RuneEnergyPylonRenderer.java");
            }

        }else{
            RenderingTools.addActivePostShader(tile.toString(),uniforms,SHADER);
        }
    }
}
