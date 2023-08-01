package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.TestEntity;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.SCBedrockModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.List;

public class TestEntityRenderer extends EntityRenderer<TestEntity> {

    private FDModel model;

    public TestEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.model = new FDModel(SCBedrockModels.ULDERA_CRYSTAL);
    }


    @Override
    public void render(TestEntity entity, float idk, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();

        entity.getAnimationManager().getAsClientManager().applyAnimations(model,pticks);

//        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),RenderingTools.getTime(entity.level,pticks)));

        matrices.scale(3,3,3);
        model.render(matrices,src.getBuffer(RenderType.entityTranslucentCull(new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/uldera_crystal_inner.png"))),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        model.render(matrices,src.getBuffer(RenderType.eyes(new ResourceLocation(SolarCraft.MOD_ID,"textures/entities/uldera_crystal_glow.png"))),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);

        matrices.popPose();

    }


    @Override
    public boolean shouldRender(TestEntity p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(TestEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
