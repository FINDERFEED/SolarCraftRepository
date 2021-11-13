package com.finderfeed.solarforge.magic_items.blocks.render;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarforge.magic_items.blocks.render.abstracts.TileEntityRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;

public class InfusingTableTileRenderer extends TileEntityRenderer<InfusingTableTile> {
    public InfusingTableTileRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(InfusingTableTile tile, float pticks, PoseStack matrices, MultiBufferSource src, int light, int overlay) {
        IItemHandler handler = tile.getInventory();
        float time = RenderingTools.getTime(tile.getLevel(),pticks);
        double radius = 1.5;
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        if (handler != null){
            ArrayList<ItemStack> items = getItemsToRender(handler);
            if (!items.isEmpty()){
                float rotationModifier = 1;
                float distanceModifirer = 0;
                int t = tile.getAnimationTime();
                //TODO:normal animation
                if (t != -1) {
                    float d = (float) (t)/ (InfusingTableTile.ANIM_TIME + pticks);
                    rotationModifier = Mth.lerp(d, 1, 10f);
                    distanceModifirer = Mth.lerp(d,0,1);
                }


                int count = items.size();
                matrices.pushPose();
                matrices.translate(0.5,1.4,0.5);
                matrices.mulPose(Vector3f.YN.rotationDegrees(((time)%360)*rotationModifier));
                matrices.scale(0.4f,0.4f,0.4f);
                for (int i = 0 ;i < count;i++){
                    double h = i*(360f/count);
                    double x = (radius-distanceModifirer)*Math.sin(Math.toRadians(h));
                    double z = (radius-distanceModifirer)*Math.cos(Math.toRadians(h));

                    matrices.pushPose();
                    matrices.translate(x,0,z);
                    matrices.mulPose(Vector3f.YP.rotationDegrees((float)(h) % 360));
                    ItemStack toRender = items.get(i);
                    renderer.render(toRender, ItemTransforms.TransformType.FIXED,false,matrices,src, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                            renderer.getModel(toRender,null,null,0));
                    matrices.popPose();
                }
                matrices.popPose();
            }
        }

        ItemStack stack = tile.getInventory().getStackInSlot(9);
        if (!stack.isEmpty()){
            matrices.pushPose();
            matrices.translate(0.5,1.4,0.5);
            matrices.scale(0.4f,0.4f,0.4f);
            matrices.mulPose(Vector3f.YN.rotationDegrees(time%360));
            renderer.render(stack, ItemTransforms.TransformType.FIXED,false,matrices,src, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                    renderer.getModel(stack,null,null,0));
            matrices.popPose();
        }
    }

    private ArrayList<ItemStack> getItemsToRender(IItemHandler handler){
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < 9;i ++){
            ItemStack s = handler.getStackInSlot(i);
            if (!s.isEmpty()){
                stacks.add(s);
            }
        }
        return stacks;
    }
}
