package com.finderfeed.solarforge.entities.renderers;

import com.finderfeed.solarforge.entities.LegendaryItem;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class LegendaryItemRenderer extends EntityRenderer<LegendaryItem> {

    public LegendaryItemRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }


    @Override
    public void render(LegendaryItem item, float idk, float partialticks, PoseStack matrices, MultiBufferSource buffer, int light) {
        ItemStack toRender = item.getItem();

        float time = RenderingTools.getTime(item.level,partialticks);
        matrices.pushPose();
        matrices.translate(0,0.125,0);
        matrices.mulPose(Vector3f.YP.rotationDegrees(time%360));
        matrices.mulPose(Vector3f.ZP.rotationDegrees(time%360));
        matrices.scale(0.1f,0.1f,0.1f);
        RenderingTools.DragonEffect.render(matrices,75,buffer);
        matrices.popPose();
        matrices.pushPose();
        matrices.mulPose(Vector3f.YP.rotationDegrees(time*3 % 360));
        matrices.translate(0,-0.125,0);
        matrices.scale(2,2,2);
        Minecraft.getInstance().getItemRenderer().render(toRender, ItemTransforms.TransformType.GROUND,true,matrices,buffer,
                light, OverlayTexture.NO_OVERLAY,Minecraft.getInstance().getItemRenderer().getModel(toRender,item.level,null,light));

        matrices.popPose();
        super.render(item, idk, partialticks, matrices, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(LegendaryItem p_114482_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
