package com.finderfeed.solarcraft.client.particles;

import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public abstract class ShaderParticle extends Particle {


    private float quadSize;
    private RenderStateShard.ShaderStateShard shaderStateShard;

    public ShaderParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, float size, int time, RenderStateShard.ShaderStateShard shaderStateShard) {
        super(level, x, y, z, xd, yd, zd);
        this.x = x;
        this.y = y;
        this.z = z;
        this.lifetime = time;
        this.quadSize = size;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.shaderStateShard = shaderStateShard;
    }

    @Override
    public void render(VertexConsumer useless, Camera cam, float pticks) {
        MultiBufferSource src = Minecraft.getInstance().renderBuffers().bufferSource();

        VertexConsumer vertex = src.getBuffer(SolarCraftRenderTypes.shaderRendertype(shaderStateShard));

        PoseStack matrices = new PoseStack();
        matrices.pushPose();
        Vec3 vec3 = cam.getPosition();
        float f = (float)(Mth.lerp((double)pticks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)pticks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)pticks, this.zo, this.z) - vec3.z());
        Quaternion quaternion = cam.rotation();
        matrices.translate(f,f1,f2);
        matrices.mulPose(quaternion);

        Matrix4f mat = matrices.last().pose();
        float size = quadSize/2;

        vertex.vertex(mat,-size, size, 0).uv(0,1).endVertex();
        vertex.vertex(mat, size, size, 0).uv(1,1).endVertex();
        vertex.vertex(mat, size,-size, 0).uv(1,0).endVertex();
        vertex.vertex(mat,-size,-size, 0).uv(0,0).endVertex();

        vertex.vertex(mat,-size,-size, 0).uv(0,0).endVertex();
        vertex.vertex(mat, size,-size, 0).uv(1,0).endVertex();
        vertex.vertex(mat, size, size, 0).uv(1,1).endVertex();
        vertex.vertex(mat,-size, size, 0).uv(0,1).endVertex();


        Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
        matrices.popPose();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


}
