package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.entities.ShadowZombie;
import com.finderfeed.solarcraft.content.entities.models.ShadowZombieModel;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModelInfo;
import com.finderfeed.solarcraft.local_library.bedrock_loader.model_components.FDModel;
import com.finderfeed.solarcraft.registries.SCBedrockModels;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ShadowZombieRenderer extends MobRenderer<ShadowZombie, ShadowZombieModel> {




    public ShadowZombieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,new ShadowZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE)), 0.0f);
        this.addLayer(new ShadowZombieModel.ShadowZombieLayer(this));
    }

    @Override
    public void render(ShadowZombie zombie, float p_115456_, float p_115457_, PoseStack matrices, MultiBufferSource src, int p_115460_) {
        if (!zombie.isDeadOrDying()) {
            if (false) {
                super.render(zombie, p_115456_, p_115457_, matrices, src, p_115460_);
            }
            FDModel model = new FDModel(SCBedrockModels.TEST_MODEL);

            model.render(matrices,src.getBuffer(RenderType.entityTranslucent(new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/texture2.png"))),
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,1f,1f,1f,1f);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowZombie p_114482_) {
        return ShadowZombieModel.MODEL;
    }
}
