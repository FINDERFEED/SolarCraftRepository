package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.content.blocks.blockentities.DimensionCoreTile;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.finderfeed.solarcraft.events.other_events.OBJModels;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.Map;

public class DimensionCoreRenderer extends TileEntityRenderer<DimensionCoreTile> {

    private final ResourceLocation SHADER_LOCATION = new ResourceLocation(SolarCraft.MOD_ID,
            "shaders/post/dimension_portal.json");
//    private final ResourceLocation SHADER_LOCATION = new ResourceLocation("solarcraft","shaders/post/energy_pylon.json");


    public static PostChainPlusUltra SHADER;

    public DimensionCoreRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(DimensionCoreTile tile, float pticks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        if (tile.getLevel() == null || !tile.isStructureCorrect()) return;
        matrices.pushPose();
        matrices.translate(0.5,3.5,0.5);
        matrices.scale(0.95f,0.95f,0.95f);
        float time = RenderingTools.getTime(tile.getLevel(),pticks);
//        matrices.mulPose(Vector3f.YN.rotationDegrees(time));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),time));
        float r;
        float gr;
        float b;
        if (Helpers.isDay(tile.getLevel())) {
            r = 1;
            gr = 1;
            b = 0;
        } else {
            r = 0.5f;
            gr = 0;
            b = 1f;
        }
        RenderingTools.renderBlockObjModel(OBJModels.PORTAL_SPHERE,matrices,src,r,gr,b, LightTexture.FULL_BRIGHT,overlay);

        matrices.popPose();

        if (FDMathHelper.canSeeBlock(tile.getBlockPos().above(3),Minecraft.getInstance().player) &&
                RenderingTools.isBoxVisible(this.getRenderBoundingBox(tile)) && (Minecraft.getInstance().cameraEntity != null)) {
            Vec3 playerPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();


            Vec3 tilePos = new Vec3(tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY() + 3.5, tile.getBlockPos().getZ() + 0.5);


            float dist = ((float) tilePos.subtract(playerPos).length());



            matrices.pushPose();
            matrices.translate(0.5, 3.5, 0.5);
            Matrix4f modelview = matrices.last().pose();
            this.loadShader(tile,SHADER_LOCATION, new UniformPlusPlus(Map.of(
                    "projection", RenderSystem.getProjectionMatrix(),
                    "modelview", modelview,
                    "distance", dist,
                    "time", time/4f,
                    "radius",0.21f,
                    "deltaR",0.075f,
                    "sinValue",10f,
                    "sinSpread",0.01f
            )));
            matrices.popPose();
        }
    }


    private void loadShader(BlockEntity tile,ResourceLocation LOC, UniformPlusPlus uniforms){
        if (SHADER == null){
            try {
                SHADER = new PostChainPlusUltra(LOC,uniforms);
                SHADER.resize(Minecraft.getInstance().getWindow().getScreenWidth(),
                        Minecraft.getInstance().getWindow().getScreenHeight());
                RenderingTools.addActivePostShader(tile.toString(),uniforms,SHADER);
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Failed to load shader in DimensionCoreRenderer.java");
            }

        }else{
            RenderingTools.addActivePostShader(tile.toString(),uniforms,SHADER);
        }
    }

    @Override
    public AABB getRenderBoundingBox(DimensionCoreTile blockEntity) {
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(blockEntity.getBlockPos()).add(0,3,0),3,3);
    }
}
