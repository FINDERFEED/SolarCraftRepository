package com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.InfusingTableTileContainer;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

import java.util.Optional;

public class InfusingTableScreen extends AbstractContainerScreen<InfusingTableTileContainer> {

    private static final ResourceLocation LOC = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/infuser_crafting_table.png");

    int relX;
    int relY;
    private Item result = null;

    public InfusingTableScreen(InfusingTableTileContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }


    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;


    }

    @Override
    public void render(PoseStack stack,int rouseX,int rouseY,float partialTicks){
        this.renderBackground(stack);
        super.render(stack,rouseX,rouseY,partialTicks);
        this.renderTooltip(stack,rouseX,rouseY);

    }

    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int mousex, int mousey) {
        ClientHelpers.bindText(LOC);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        blit(matrices, relX + 3+a, relY+26, 0, 0, 256, 256);


        Level world = Minecraft.getInstance().level;
        IItemHandler stacks = menu.getInventory();
        Optional<InfusingCraftingRecipe> opt = world.getRecipeManager().getRecipeFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE,new PhantomInventory(stacks),world);
        if (opt.isPresent()){
            result = opt.get().getResultItem().getItem();
            renderItemAndTooltip(result.getDefaultInstance(),relX+154,relY+36,mousex,mousey,matrices,menu.tile.calculateMaximumRecipeOutput(opt.get()));
        }else{
            result = null;
        }

    }
    private void renderItemAndTooltip(ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices,int count){

        ItemStack renderThis = toRender.copy();
        renderThis.setCount(count);
        minecraft.getItemRenderer().renderGuiItem(renderThis, place1, place2);
        minecraft.getItemRenderer().renderGuiItemDecorations(font,renderThis,place1,place2);
        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            renderTooltip(matrices,toRender,mousex,mousey);
            matrices.popPose();
        }
    }
}
