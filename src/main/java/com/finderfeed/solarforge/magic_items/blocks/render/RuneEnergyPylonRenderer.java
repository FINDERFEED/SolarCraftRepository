package com.finderfeed.solarforge.magic_items.blocks.render;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.RenderingTools;
import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarforge.rendering.shaders.Shaders;
import com.finderfeed.solarforge.rendering.shaders.Uniform;
import com.finderfeed.solarforge.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.rendering.shaders.post_chains.UniformPlusPlus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;

import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;

import com.mojang.math.Vector3f;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.CallbackI;

import java.util.Map;

public class RuneEnergyPylonRenderer implements BlockEntityRenderer<RuneEnergyPylonTile> {
    public final ResourceLocation MAIN = new ResourceLocation("solarforge","textures/misc/tile_energy_pylon.png");
    private final ResourceLocation SHADER_LOCATION = new ResourceLocation("solarforge","shaders/post/energy_pylon.json");

    public static PostChainPlusUltra SHADER;

    public RuneEnergyPylonRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(RuneEnergyPylonTile tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        Vec3 playerPos = Minecraft.getInstance().player.position().add(0,Minecraft.getInstance().player.getBbHeight()/2,0);
        Vec3 tilePos = new Vec3(tile.getBlockPos().getX()+0.5,tile.getBlockPos().getY()+0.5,tile.getBlockPos().getZ()+0.5);
            float dist = (float) new Vec3(tilePos.x - playerPos.x, tilePos.y - playerPos.y, tilePos.z - playerPos.z).length() * 50f;
            Matrix4f projection = Minecraft.getInstance().gameRenderer.getProjectionMatrix(Minecraft.getInstance().gameRenderer.getFov(
                    Minecraft.getInstance().gameRenderer.getMainCamera(),
                    1,
                    true
            ));

            matrices.pushPose();
            matrices.translate(0.5, 0.5, 0.5);
            Matrix4f modelview = matrices.last().pose();
            this.loadShader(SHADER_LOCATION, new UniformPlusPlus(Map.of(
                    "projection", projection,
                    "modelview", modelview,
                    "distance", dist,
                    "intensity", 0.5f
            )));
            matrices.popPose();

        if (tile.getEnergyType() != null) {
            VertexConsumer vertex = buffer.getBuffer(RenderType.text(new ResourceLocation("solarforge", "textures/misc/tile_energy_pylon_" + tile.getEnergyType().id + ".png")));
            float time = (Minecraft.getInstance().level.getGameTime()+partialTicks);
            for (int i = 0; i < 8; i ++) {
                matrices.pushPose();

                matrices.translate(0.5, 0.5, 0.5f);
                matrices.scale(0.7f,0.7f,0.7f);
                matrices.mulPose(Vector3f.YP.rotationDegrees(i*45+time%360));
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



        Quaternion quaternion = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();

        float time = (Minecraft.getInstance().level.getGameTime()+partialTicks)*5;

        matrices.mulPose(quaternion);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(time%360));
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
        matrices.mulPose(Vector3f.ZN.rotationDegrees(time%360));
        Matrix4f matrix2 = matrices.last().pose();
        matrices.scale(1.5f,1.5f,1.5f);
        vertex.vertex(matrix2, -0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  0.5f,0.5f,0.001f).color(255, 255, 0, 200).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();
        vertex.vertex(matrix2,  -0.5f,-0.5f,0.001f).color(255, 255, 0, 200).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).endVertex();

        matrices.popPose();
    }


    private void loadShader(ResourceLocation LOC, UniformPlusPlus uniforms){
        if (SHADER == null){
            try {
                SHADER = new PostChainPlusUltra(LOC,uniforms);
                SHADER.resize(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight());
                RenderingTools.addActivePostShader(uniforms,SHADER);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Failed to load shader in RuneEnergyPylonRenderer.java");
            }

        }else{
            RenderingTools.addActivePostShader(uniforms,SHADER);
        }
    }
}
