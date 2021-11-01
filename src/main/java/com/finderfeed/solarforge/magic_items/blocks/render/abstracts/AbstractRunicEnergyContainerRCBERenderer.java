package com.finderfeed.solarforge.magic_items.blocks.render.abstracts;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.runic_energy.AbstractRunicEnergyContainerRCBE;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractRunicEnergyContainerRCBERenderer<T extends AbstractRunicEnergyContainerRCBE> implements BlockEntityRenderer<T> {
    @Override
    public void render(T tile, float pticks, PoseStack matrices, MultiBufferSource buffer, int light, int overlay) {
        if ((tile.nullOrGiverPositionForClient != null) && (!Helpers.equalsBlockPos(BlockPos.ZERO,tile.nullOrGiverPositionForClient))){
            Vec3 tilepos = new Vec3(tile.getBlockPos().getX() +0.5,tile.getBlockPos().getY() +0.5,tile.getBlockPos().getZ() +0.5);
            Vec3 vector = Helpers.getBlockCenter(tile.nullOrGiverPositionForClient).subtract(tilepos);
            RenderingTools.renderRay(matrices,buffer,0.25f,(float)vector.length(),(mat)->{
                RenderingTools.applyMovementMatrixRotations(mat,vector.normalize());
            },false,0,pticks);
        }
    }
}
