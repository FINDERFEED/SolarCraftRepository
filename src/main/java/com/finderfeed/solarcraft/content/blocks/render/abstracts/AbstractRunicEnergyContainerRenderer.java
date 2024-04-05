package com.finderfeed.solarcraft.content.blocks.render.abstracts;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractRunicEnergyContainerRenderer<T extends AbstractRunicEnergyContainer> implements BlockEntityRenderer<T> {

    public AbstractRunicEnergyContainerRenderer(BlockEntityRendererProvider.Context ctx){

    }
    @Override
    public void render(T tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        for (BlockPos pos : tile.getNullOrGiverPositionForClient()) {
            Vec3 tilepos = new Vec3(tile.getBlockPos().getX() + 0.5, tile.getBlockPos().getY() + 0.5, tile.getBlockPos().getZ() + 0.5);
            Vec3 vector = Helpers.getBlockCenter(pos).subtract(tilepos);
            RenderingTools.renderRay(matrices, buffer, 0.25f, (float) vector.length(), (mat) -> {
                RenderingTools.applyMovementMatrixRotations(mat, vector.normalize()); }, false, 0, pticks);
        }
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return new AABB(
                -blockEntity.getMaxRange(),
                -blockEntity.getMaxRange(),
                -blockEntity.getMaxRange(),
                blockEntity.getMaxRange(),
                blockEntity.getMaxRange(),
                blockEntity.getMaxRange())
                .move(blockEntity.getBlockPos());
    }
}
