package com.finderfeed.solarcraft.content.entities.projectiles.renderers;

import com.finderfeed.solarcraft.client.rendering.CoreShaders;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.content.entities.not_alive.BallLightningProjectile;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class BallLightningRenderer extends EntityRenderer<BallLightningProjectile> {

    public static boolean test = false;

    public BallLightningRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(BallLightningProjectile projectile, float smth, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        if (Minecraft.getInstance().level != null) {
            CoreShaders.AOE_GUN_PROJECTILE_SHADER.safeGetUniform("time")
                    .set(RenderingTools.getTime(Minecraft.getInstance().level, pticks)/10f);
        }
        CoreShaders.AOE_GUN_PROJECTILE_SHADER.safeGetUniform("definedColor")
                .set(1f,1f,0f);


        VertexConsumer vertex = src.getBuffer(SCRenderTypes.shaderRendertype(CoreShaders.AOE_GUN_PROJECTILE_STATE_SHARD));
        Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();
        matrices.pushPose();
        Quaternionf quaternion = cam.rotation();
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
