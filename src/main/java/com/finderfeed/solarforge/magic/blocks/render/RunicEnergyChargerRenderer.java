package com.finderfeed.solarforge.magic.blocks.render;

import com.finderfeed.solarforge.client.rendering.CoreShaders;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarforge.magic.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class RunicEnergyChargerRenderer extends AbstractRunicEnergyContainerRenderer<RunicEnergyChargerTileEntity> {
    public RunicEnergyChargerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RunicEnergyChargerTileEntity tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        super.render(tile, pticks, matrices, buffer, light, overlay);
        matrices.pushPose();
        VertexConsumer vertex = buffer.getBuffer(SolarCraftRenderTypes.shaderRendertype(CoreShaders.RUNIC_ENERGY_FLOW_STATE_SHARD));
        matrices.translate(0,2,0);

        CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("time").set((tile.getLevel().getDayTime() + pticks)/5f );
        CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("definedColor").set(1f,1f,0.0f);


        Matrix4f mat = matrices.last().pose();
        float size = 1.0f;

        vertex.vertex(mat,-size, size/2, 0).uv(0,1).endVertex();
        vertex.vertex(mat, size, size/2, 0).uv(1,1).endVertex();
        vertex.vertex(mat, size,-size/2, 0).uv(1,0).endVertex();
        vertex.vertex(mat,-size,-size/2, 0).uv(0,0).endVertex();

        vertex.vertex(mat,-size,-size/2, 0).uv(0,0).endVertex();
        vertex.vertex(mat, size,-size/2, 0).uv(1,0).endVertex();
        vertex.vertex(mat, size, size/2, 0).uv(1,1).endVertex();
        vertex.vertex(mat,-size, size/2, 0).uv(0,1).endVertex();

        matrices.popPose();
    }
}
