package com.finderfeed.solarforge.magic.projectiles.renderers;

import com.finderfeed.solarforge.client.rendering.Shaders;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarforge.entities.BallLightningProjectile;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class BallLightningRenderer extends EntityRenderer<BallLightningProjectile> {
    public BallLightningRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BallLightningProjectile projectile, float smth, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        if (Minecraft.getInstance().level != null) {
            Shaders.AOE_GUN_PROJECTILE_SHADER.safeGetUniform("time").set(RenderingTools.getTime(Minecraft.getInstance().level, pticks)/10f);
        }
        Shaders.AOE_GUN_PROJECTILE_SHADER.safeGetUniform("definedColor").set(1f,0f,0f);


        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.shaderRendertype(Shaders.AOE_GUN_PROJECTILE_STATE_SHARD));
        Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();
        matrices.pushPose();
        Quaternion quaternion = cam.rotation();
        matrices.translate(0,0.2,0);
        matrices.mulPose(quaternion);
        Matrix4f mat = matrices.last().pose();
        float size = 0.5f;

        vertex.vertex(mat,-size, size, 0).uv(0,1).endVertex();
        vertex.vertex(mat, size, size, 0).uv(1,1).endVertex();
        vertex.vertex(mat, size,-size, 0).uv(1,0).endVertex();
        vertex.vertex(mat,-size,-size, 0).uv(0,0).endVertex();

        vertex.vertex(mat,-size,-size, 0).uv(0,0).endVertex();
        vertex.vertex(mat, size,-size, 0).uv(1,0).endVertex();
        vertex.vertex(mat, size, size, 0).uv(1,1).endVertex();
        vertex.vertex(mat,-size, size, 0).uv(0,1).endVertex();
        matrices.popPose();
        super.render(projectile, smth, pticks, matrices, src, light);
    }

    @Override
    public ResourceLocation getTextureLocation(BallLightningProjectile p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
