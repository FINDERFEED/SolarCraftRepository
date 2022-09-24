package com.finderfeed.solarforge.content.blocks.render;

import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.content.blocks.blockentities.WormholeTileEntity;
import com.finderfeed.solarforge.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class WormholeRenderer implements BlockEntityRenderer<WormholeTileEntity> {

    private final ResourceLocation SHADER_LOCATION = new ResourceLocation("solarforge","shaders/post/gravitational_lens.json");
    public static PostChainPlusUltra SHADER;


    public WormholeRenderer(BlockEntityRendererProvider.Context ctx){

    }


    @Override
    public void render(WormholeTileEntity tile, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int light2) {
        matrices.pushPose();
        RenderingTools.renderObjModel(OBJModels.HOLE_MODEL,matrices,buffer,light, OverlayTexture.NO_OVERLAY,(matrix)->{
            matrix.translate(0.5f,0.5f,0.5f);
            matrix.scale(0.4f,0.4f,0.4f);

        });

        matrices.popPose();
        doShader(matrices,tile);

    }
    private void doShader(PoseStack matrices, WormholeTileEntity tile){
        if (FDMathHelper.canSeeTileEntity(tile, Minecraft.getInstance().player) && Minecraft.getInstance().cameraEntity != null) {
            Vec3 playerPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            Vec3 tilePos = new Vec3(tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY() + 0.5, tile.getBlockPos().getZ() + 0.5);
            float dist = (float) new Vec3(tilePos.x - playerPos.x, tilePos.y - playerPos.y, tilePos.z - playerPos.z).length()*50;



            matrices.pushPose();
            matrices.translate(0.5, 0.5, 0.5);
            Matrix4f modelview = matrices.last().pose();
            this.loadShader(SHADER_LOCATION, new UniformPlusPlus(Map.of(
                    "projection", RenderSystem.getProjectionMatrix(),
                    "modelview", modelview,
                    "distance", dist,
                    "intensity", 3.5f,
                    "innerControl",6f,
                    "outerControl",0.02f,
                    "effectRadius",1f
            )));
            matrices.popPose();
        }
    }


    private void loadShader(ResourceLocation LOC, UniformPlusPlus uniforms){
        if (SHADER == null){
            try {
                SHADER = new PostChainPlusUltra(LOC,uniforms);
                SHADER.resize(Minecraft.getInstance().getWindow().getScreenWidth(),Minecraft.getInstance().getWindow().getScreenHeight());
                RenderingTools.addActivePostShader(uniforms,SHADER);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Failed to load shader in WormholeRenderer.java");
            }

        }else{
            RenderingTools.addActivePostShader(uniforms,SHADER);
        }
    }
}
