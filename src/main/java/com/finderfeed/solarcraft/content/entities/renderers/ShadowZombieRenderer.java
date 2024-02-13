package com.finderfeed.solarcraft.content.entities.renderers;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.entities.ShadowZombie;
import com.finderfeed.solarcraft.content.entities.models.ShadowZombieModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class ShadowZombieRenderer extends MobRenderer<ShadowZombie, ShadowZombieModel> {



    public ShadowZombieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx,new ShadowZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE)), 0.0f);
        this.addLayer(new ShadowZombieModel.ShadowZombieLayer(this));
    }

    @Override
    public void render(ShadowZombie zombie, float p_115456_, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        if (!zombie.isDeadOrDying()) {
            if (true) {
                matrices.pushPose();
                matrices.translate(0, 2, 0);
                Matrix4f m = matrices.last().pose();
                ResourceLocation t = new ResourceLocation(SolarCraft.MOD_ID, "textures/items/amethyst_core.png");
                VertexConsumer c = src.getBuffer(SolarCraftRenderTypes.TEXT_GLOW.apply(t));

                c.vertex(m, 0, 0, 0).color(1f, 1f, 1f, 1f).uv(0, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                c.vertex(m, 0, 1, 0).color(1f, 1f, 1f, 1f).uv(0, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                c.vertex(m, 1, 1, 0).color(1f, 1f, 1f, 1f).uv(1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                c.vertex(m, 1, 0, 0).color(1f, 1f, 1f, 1f).uv(1, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                matrices.popPose();
            }

            if (true) {
                matrices.pushPose();
                matrices.translate(0, 2, 0);
                Matrix4f m = matrices.last().pose();
                ResourceLocation t = new ResourceLocation(SolarCraft.MOD_ID, "textures/items/amethyst_core.png");
                VertexConsumer c = src.getBuffer(SolarCraftRenderTypes.text(t));

                c.vertex(m, 0, 0, 0).color(1f, 1f, 1f, 1f).uv(0, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                c.vertex(m, 0, 1, 0).color(1f, 1f, 1f, 1f).uv(0, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                c.vertex(m, 1, 1, 0).color(1f, 1f, 1f, 1f).uv(1, 1).uv2(LightTexture.FULL_BRIGHT).endVertex();
                c.vertex(m, 1, 0, 0).color(1f, 1f, 1f, 1f).uv(1, 0).uv2(LightTexture.FULL_BRIGHT).endVertex();
                matrices.popPose();
            }
                super.render(zombie, p_115456_, pticks, matrices, src, light);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowZombie p_114482_) {
        return ShadowZombieModel.MODEL;
    }
}
