package com.finderfeed.solarcraft.content.blocks.render;

import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarcraft.content.blocks.render.abstracts.TileEntityRenderer;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import java.util.ArrayList;
import java.util.Optional;

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
                float rotationModifier = 0;
                float distanceModifier = 0;

                Optional<InfusingCraftingRecipe> optional = tile.getLevel().getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING_CRAFTING.get(), tile.phantomInv, tile.getLevel());
                if (optional.isPresent() && tile.isRecipeInProgress()){
                    InfusingCraftingRecipe recipe = optional.get();

                    int recipeTime = recipe.getTime();
                    int maxOutput = tile.calculateMaximumRecipeOutput(recipe);
                    int remainingRecipeTime = recipeTime*maxOutput - tile.getCurrentTime();
                    int tm = Math.min(recipeTime*maxOutput,InfusingTableTile.ANIM_TIME);
                    if (remainingRecipeTime < tm){
                        float t = tm-remainingRecipeTime;
                        rotationModifier = ((tm-remainingRecipeTime)/(float)tm)*1600f + pticks;
                        distanceModifier = Mth.lerp((float)t/tm,0,1);

                    }
                }

//                if (tile.getRemainingRecipeTime() != -1){
//                    int t = ClientEventsHandler.getTickerValueOrAddANewOne("ticker_animation"+tile.toString(),100);
//                    rotationModifier = 1 + ((float)t/InfusingTableTile.ANIM_TIME)*4;
////                            Mth.lerp((float)t/(InfusingTableTile.ANIM_TIME),1,5);
//                    distanceModifier = Mth.lerp((float)t/(InfusingTableTile.ANIM_TIME+pticks),0,1);
//                }
                int count = items.size();
                matrices.pushPose();
                matrices.translate(0.5,1.4,0.5);
//                matrices.mulPose(Vector3f.YN.rotationDegrees(time+rotationModifier));
                matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),time+rotationModifier));
                matrices.scale(0.4f,0.4f,0.4f);
                for (int i = 0 ;i < count;i++){
                    double h = i*(360f/count);
                    double x = (radius-distanceModifier)*Math.sin(Math.toRadians(h));
                    double z = (radius-distanceModifier)*Math.cos(Math.toRadians(h));

                    matrices.pushPose();
                    matrices.translate(x,0,z);
//                    matrices.mulPose(Vector3f.YP.rotationDegrees((float)(h) % 360));
                    matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),(float)(h) % 360));
                    ItemStack toRender = items.get(i);
                    renderer.render(toRender, ItemDisplayContext.FIXED,false,matrices,src, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                            renderer.getModel(toRender,null,null,0));
                    matrices.popPose();
                }
                matrices.popPose();
            }
        }
        if (handler != null) {
            ItemStack stack =handler.getStackInSlot(9);
            if (!stack.isEmpty()) {
                matrices.pushPose();
                matrices.translate(0.5, 1.4, 0.5);
                matrices.scale(0.4f, 0.4f, 0.4f);
//                matrices.mulPose(Vector3f.YN.rotationDegrees(time % 360));
                matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YN(),time % 360));
                renderer.render(stack, ItemDisplayContext.FIXED, false, matrices, src, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                        renderer.getModel(stack, null, null, 0));
                matrices.popPose();
            }
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
