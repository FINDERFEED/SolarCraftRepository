package com.finderfeed.solarforge.client.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;

public abstract class ScreenParticle extends Particle {
    protected ScreenParticle(ClientLevel p_107234_, double p_107235_, double p_107236_, double p_107237_) {
        super(p_107234_, p_107235_, p_107236_, p_107237_);
    }

    @Override
    public void render(VertexConsumer vertex, Camera camera, float pticks) {
        PoseStack stack = new PoseStack();

    }


}
