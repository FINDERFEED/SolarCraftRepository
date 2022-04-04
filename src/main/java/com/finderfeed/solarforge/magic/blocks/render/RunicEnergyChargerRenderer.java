package com.finderfeed.solarforge.magic.blocks.render;

import com.finderfeed.solarforge.client.rendering.CoreShaders;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarforge.magic.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarforge.magic.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;

public class RunicEnergyChargerRenderer extends AbstractRunicEnergyContainerRenderer<RunicEnergyChargerTileEntity> {
    public RunicEnergyChargerRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RunicEnergyChargerTileEntity tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        super.render(tile, pticks, matrices, buffer, light, overlay);
        if (tile.getInventory() == null) return;
        ItemStack stack = tile.chargeSlot();
        if (stack.isEmpty())return;
        matrices.pushPose();
        VertexConsumer vertex = buffer.getBuffer(SolarCraftRenderTypes.shaderRendertypetest(CoreShaders.RUNIC_ENERGY_FLOW_STATE_SHARD));
        CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("time").set((tile.getLevel().getGameTime() + pticks)/10f );
        CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("definedColor").set(1f,0.65f,0.0f);
        CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("innerColor").set(1f,1f,0f);
        CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("modifier").set(0.5f);
        float size = 0.14f;
        for (int i = 0;i < 4;i++) {
            matrices.pushPose();
            matrices.translate(0.5,0.555,0.5);
            matrices.mulPose(Vector3f.YP.rotationDegrees(90*i));
            matrices.translate(0.2,0,0);

            Matrix4f mat = matrices.last().pose();
            vertex.vertex(mat, -size, size / 2, 0).uv(0, 1).color(1f,1,1,1).endVertex();
            vertex.vertex(mat, size, size / 2, 0).uv(1, 1).color(1f,1,1,1).endVertex();
            vertex.vertex(mat, size, -size / 2, 0).uv(1, 0).color(1f,1,1,1).endVertex();
            vertex.vertex(mat, -size, -size / 2, 0).uv(0, 0).color(1f,1,1,1).endVertex();

            vertex.vertex(mat, -size, -size / 2, 0).uv(0, 0).color(1f,1,1,1).endVertex();
            vertex.vertex(mat, size, -size / 2, 0).uv(1, 0).color(1f,1,1,1).endVertex();
            vertex.vertex(mat, size, size / 2, 0).uv(1, 1).color(1f,1,1,1).endVertex();
            vertex.vertex(mat, -size, size / 2, 0).uv(0, 1).color(1f,1,1,1).endVertex();
            matrices.popPose();
        }
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.5,0.555 + Math.sin((tile.getLevel().getGameTime() + pticks)/10 )*0.01,0.5);
        matrices.scale(0.4f,0.4f,0.4f);
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        renderer.render(stack, ItemTransforms.TransformType.FIXED,false,matrices,buffer,light, OverlayTexture.NO_OVERLAY,
                renderer.getModel(stack,tile.getLevel(),null,1));
        matrices.popPose();
    }
}
