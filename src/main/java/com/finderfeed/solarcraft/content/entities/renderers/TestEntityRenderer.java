package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.TestEntity;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.registries.SCBedrockModels;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class TestEntityRenderer extends EntityRenderer<TestEntity> {

    private FDModel model;

    public TestEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.model = new FDModel(SCBedrockModels.TEST_MODEL);
    }


    @Override
    public void render(TestEntity entity, float idk, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();

        entity.getAnimationManager().getAsClientManager().applyAnimations(model,pticks);

        model.render(matrices,src.getBuffer(RenderType.entityTranslucent(new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/texture2.png"))),
                light, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);


        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(TestEntity p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
