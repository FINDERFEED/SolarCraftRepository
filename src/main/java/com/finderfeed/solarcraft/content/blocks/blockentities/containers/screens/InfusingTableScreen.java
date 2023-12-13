package com.finderfeed.solarcraft.content.blocks.blockentities.containers.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.InfusingTableTileContainer;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.PhantomInventory;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.Optional;

public class InfusingTableScreen extends AbstractContainerScreen<InfusingTableTileContainer> {

    private static final ResourceLocation LOC = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/infuser_crafting_table.png");

    int relX;
    int relY;
    private Item result = null;

    public InfusingTableScreen(InfusingTableTileContainer p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        inventoryLabelX = 1000000;
    }


    @Override
    protected void init() {
        super.init();
        titleLabelX += 35;
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;


    }

    @Override
    public void render(GuiGraphics graphics, int rouseX, int rouseY, float partialTicks){
        this.renderBackground(graphics);
        super.render(graphics,rouseX,rouseY,partialTicks);
        this.renderTooltip(graphics,rouseX,rouseY);

    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mousex, int mousey) {
        PoseStack matrices = graphics.pose();
        ClientHelpers.bindText(LOC);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        RenderingTools.blitWithBlend(matrices, relX + 3+a, relY+26, 0, 0, 256, 256,256,256,0,1f);


        Level world = Minecraft.getInstance().level;
        IItemHandler stacks = menu.getInventory();
        Optional<InfusingCraftingRecipe> opt = world.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING_CRAFTING.get(),new PhantomInventory(stacks),world);
        if (opt.isPresent()){
            result = opt.get().getResultItem(world.registryAccess()).getItem();
            renderItemAndTooltip(graphics,result.getDefaultInstance(),relX+153+a,relY+36,mousex,mousey,matrices,menu.tile.calculateMaximumRecipeOutput(opt.get())*opt.get().getOutputCount());
        }else{
            result = null;
        }


    }
    private void renderItemAndTooltip(GuiGraphics graphics,ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices,int count){

        ItemStack renderThis = toRender.copy();
        renderThis.setCount(count);
        graphics.renderItem(renderThis, place1, place2);
        graphics.renderItemDecorations(font,renderThis,place1,place2);
        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            graphics.renderTooltip(font,toRender,mousex,mousey);
            matrices.popPose();
        }
    }
}
