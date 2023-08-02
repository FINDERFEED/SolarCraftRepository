package com.finderfeed.solarcraft.content.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public abstract class SingleQuadRenderer<T extends Entity> extends EntityRenderer<T> {

    protected float r = 1f;
    protected float g = 1f;
    protected float b = 1f;
    protected float a = 1f;
    protected int light = -1;

    protected SingleQuadRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(T living, float p_114486_, float pticks, PoseStack matrices, MultiBufferSource src, int light) {
        matrices.pushPose();

        float height = living.getBbHeight();
        matrices.translate(0,height/2,0);
        VertexConsumer vertex = this.getRenderType(src);
        float size = quadSize();
        Quaternionf q = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        matrices.mulPose(q);
        Matrix3f normalMatrix = matrices.last().normal();

        Vector3f normal = normalMatrix.transform(new Vector3f(0,0,1));

        int l = this.light == -1 ? light : this.light;
        Matrix4f m = matrices.last().pose();
        vertex.vertex(m, -size,size,0).color(r,g,b,a).uv(0, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(normal.x,normal.y,normal.z).endVertex();
        vertex.vertex(m,  size,size,0).color(r,g,b,a).uv(1, 1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(normal.x,normal.y,normal.z).endVertex();
        vertex.vertex(m,  size,-size,0).color(r,g,b,a).uv(1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(normal.x,normal.y,normal.z).endVertex();
        vertex.vertex(m,  -size,-size,0).color(r,g,b,a).uv(0, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(l).normal(normal.x,normal.y,normal.z).endVertex();
        matrices.popPose();
    }

    public abstract VertexConsumer getRenderType(MultiBufferSource source);

    public abstract float quadSize();
}
