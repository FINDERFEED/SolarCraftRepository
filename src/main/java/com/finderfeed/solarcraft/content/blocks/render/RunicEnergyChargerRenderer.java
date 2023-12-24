package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.client.rendering.CoreShaders;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarcraft.content.blocks.RunicEnergyChargerBlock;
import com.finderfeed.solarcraft.content.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.AbstractRunicEnergyContainerRenderer;
import com.finderfeed.solarcraft.content.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Matrix4f;
import static com.finderfeed.solarcraft.local_library.helpers.RenderingTools.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
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
        Direction dir = tile.getBlockState().getValue(RunicEnergyChargerBlock.FACING);
        float rot = dir.toYRot();
        if (!ItemRunicEnergy.isFullyCharged(stack,(IRunicEnergyUser)stack.getItem())) {

            matrices.pushPose();
            VertexConsumer vertex = buffer.getBuffer(SolarCraftRenderTypes.shaderRendertype2(CoreShaders.RUNIC_ENERGY_FLOW_STATE_SHARD));
            CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("time").set((tile.getLevel().getGameTime() + pticks) / 10f);
            CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("definedColor").set(1f, 0.65f, 0.0f);
            CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("innerColor").set(1f, 1f, 0f);
            CoreShaders.RUNIC_ENERGY_FLOW_SHADER.safeGetUniform("modifier").set(0.5f);
            float size = 0.14f;
            for (int i = 0; i < 2; i++) {
                matrices.pushPose();
                matrices.translate(0.5, 0.555, 0.5);
//                matrices.mulPose(Vector3f.YP.rotationDegrees(180 * i + rot));
                matrices.mulPose(rotationDegrees(YP(),180 * i + rot));
                matrices.translate(0.2, 0, 0);

                Matrix4f mat = matrices.last().pose();
                vertex.vertex(mat, -size, size / 2, 0).uv(0, 1).color(1f, 1, 1, 1).endVertex();
                vertex.vertex(mat, size, size / 2, 0).uv(1, 1).color(1f, 1, 1, 1).endVertex();
                vertex.vertex(mat, size, -size / 2, 0).uv(1, 0).color(1f, 1, 1, 1).endVertex();
                vertex.vertex(mat, -size, -size / 2, 0).uv(0, 0).color(1f, 1, 1, 1).endVertex();

                vertex.vertex(mat, -size, -size / 2, 0).uv(0, 0).color(1f, 1, 1, 1).endVertex();
                vertex.vertex(mat, size, -size / 2, 0).uv(1, 0).color(1f, 1, 1, 1).endVertex();
                vertex.vertex(mat, size, size / 2, 0).uv(1, 1).color(1f, 1, 1, 1).endVertex();
                vertex.vertex(mat, -size, size / 2, 0).uv(0, 1).color(1f, 1, 1, 1).endVertex();
                matrices.popPose();
            }
            matrices.popPose();
        }
        matrices.pushPose();
        matrices.translate(0.5,0.555 + Math.sin((tile.getLevel().getGameTime() + pticks)/10 )*0.01,0.5);
//        matrices.mulPose(Vector3f.YP.rotationDegrees(rot));
        matrices.mulPose(rotationDegrees(YP(),rot));
        matrices.scale(0.4f,0.4f,0.4f);
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        RenderingTools.renderItemStack(stack, ItemDisplayContext.FIXED,false,matrices,buffer,light, OverlayTexture.NO_OVERLAY,
                renderer.getModel(stack,tile.getLevel(),null,1));
        matrices.popPose();
    }


//    public void render(ItemStack stack, ItemTransforms.TransformType trns, boolean p_115146_, PoseStack matrices, MultiBufferSource src, int light, int overlay, BakedModel model) {
//        if (!stack.isEmpty()) {
//            matrices.pushPose();
//            boolean flag = trns == ItemTransforms.TransformType.GUI || trns == ItemTransforms.TransformType.GROUND || trns == ItemTransforms.TransformType.FIXED;
//            if (flag) {
//                if (stack.is(Items.TRIDENT)) {
//                    model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
//                } else if (stack.is(Items.SPYGLASS)) {
//                    model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
//                }
//            }
//
//            model = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrices, model, trns, p_115146_);
//            matrices.translate(-0.5D, -0.5D, -0.5D);
//            if (!model.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
//                boolean flag1;
//                if (trns != ItemTransforms.TransformType.GUI && !trns.firstPerson() && stack.getItem() instanceof BlockItem) {
//                    Block block = ((BlockItem)stack.getItem()).getBlock();
//                    flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
//                } else {
//                    flag1 = true;
//                }
//                if (model.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(Minecraft.getInstance().getItemRenderer(), model, stack, matrices, src, light, overlay, flag1); }
//                else {
//                    RenderType rendertype = SolarCraftRenderTypes.test(TextureAtlas.LOCATION_BLOCKS);
//                    VertexConsumer vertexconsumer;
//                    if (stack.is(Items.COMPASS) && stack.hasFoil()) {
//                        matrices.pushPose();
//                        PoseStack.Pose posestack$pose = matrices.last();
//                        if (trns == ItemTransforms.TransformType.GUI) {
//                            posestack$pose.pose().multiply(0.5F);
//                        } else if (trns.firstPerson()) {
//                            posestack$pose.pose().multiply(0.75F);
//                        }
//
//                        if (flag1) {
//                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBufferDirect(src, rendertype, posestack$pose);
//                        } else {
//                            vertexconsumer = Minecraft.getInstance().getItemRenderer().getCompassFoilBuffer(src, rendertype, posestack$pose);
//                        }
//
//                        matrices.popPose();
//                    } else if (flag1) {
//                        vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBufferDirect(src, rendertype, true, stack.hasFoil());
//                    } else {
//                        vertexconsumer = Minecraft.getInstance().getItemRenderer().getFoilBuffer(src, rendertype, true, stack.hasFoil());
//                    }
//
//                    Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, light, overlay, matrices, vertexconsumer);
//                }
//            } else {
//                net.minecraftforge.client.RenderProperties.get(stack).getCustomRenderer().renderByItem(stack, trns, matrices, src, light, overlay);
//            }
//
//            matrices.popPose();
//        }
//    }
}
