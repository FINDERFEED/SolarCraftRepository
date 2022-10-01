package com.finderfeed.solarforge.content.blocks.render;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarforge.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarforge.content.blocks.blockentities.DimensionCoreTile;
import com.finderfeed.solarforge.content.blocks.render.abstracts.TileEntityRenderer;
import com.finderfeed.solarforge.events.other_events.OBJModels;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class DimensionCoreRenderer extends TileEntityRenderer<DimensionCoreTile> {

    private final ResourceLocation SHADER_LOCATION = new ResourceLocation(SolarForge.MOD_ID,
            "shaders/post/dimension_portal.json");
//    private final ResourceLocation SHADER_LOCATION = new ResourceLocation("solarforge","shaders/post/energy_pylon.json");


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
        matrices.mulPose(Vector3f.YN.rotationDegrees(time));
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
        RenderingTools.renderObjModel(OBJModels.PORTAL_SPHERE,matrices,src,r,gr,b,light,overlay);

        matrices.popPose();

        if (FDMathHelper.canSeeBlock(tile.getBlockPos().above(3),Minecraft.getInstance().player) &&
                RenderingTools.isBoxVisible(tile.getRenderBoundingBox()) && (Minecraft.getInstance().cameraEntity != null)) {
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
}
