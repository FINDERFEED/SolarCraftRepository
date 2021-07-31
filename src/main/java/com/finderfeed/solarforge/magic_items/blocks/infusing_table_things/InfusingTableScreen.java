package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class InfusingTableScreen extends AbstractContainerScreen<InfusingTableContainer> {
    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarforge","textures/gui/energy_bar.png");
    private static final ResourceLocation GUI_TEXT = new ResourceLocation("solarforge","textures/gui/solar_infuser_gui.png");
    public int relX;
    public int relY;
    public InfusingTableScreen(InfusingTableContainer container, Inventory inv, Component text) {
        super(container, inv, text);
        this.leftPos = 60;
        this.topPos = 0;
        this.height = 170;
        this.width = 256;
        this.imageHeight = 170;
        this.imageWidth = 256;

    }


    @Override
    public void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        this.inventoryLabelX = 1000;
        this.inventoryLabelY = 1000;
        this.titleLabelX = 89;
        this.titleLabelY = -30;

    }
    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks){

        this.renderBackground(stack);

        super.render(stack,rouseX,rouseY,partialTicks);
        this.renderTooltip(stack,rouseX,rouseY);



    }
    @Override
    protected void renderBg(PoseStack matrices, float partialTicks, int x, int y) {
        matrices.pushPose();
        ClientHelpers.bindText(GUI_TEXT);
        int a= 0;
        if ((int)minecraft.getWindow().getGuiScale() == 2){
            a = -1;
        }
        this.blit(matrices, relX+4+a, relY-8, 0, 0, 190, 230);
        ItemRenderer renderd = minecraft.getItemRenderer();
        InfusingTableTileEntity tile = this.menu.te;

        renderItemAndTooltip(tile.getItem(1),relX+137+a,relY+58,x,y,matrices);
        renderItemAndTooltip(tile.getItem(2),relX+123+a,relY+19,x,y,matrices);
        renderItemAndTooltip(tile.getItem(3),relX+84+a,relY+5,x,y,matrices);
        renderItemAndTooltip(tile.getItem(4),relX+45+a,relY+19,x,y,matrices);
        renderItemAndTooltip(tile.getItem(5),relX+31+a,relY+58,x,y,matrices);
        renderItemAndTooltip(tile.getItem(6),relX+45+a,relY+97,x,y,matrices);
        renderItemAndTooltip(tile.getItem(7),relX+84+a,relY+111,x,y,matrices);
        renderItemAndTooltip(tile.getItem(8),relX+123+a,relY+97,x,y,matrices);
        Optional<InfusingRecipe> recipe = minecraft.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,tile,minecraft.level);

        if (recipe.isPresent()){
            renderItemAndTooltip(recipe.get().output,relX+159+a,relY+2,x,y,matrices);
            
        }
        matrices.popPose();
        matrices.pushPose();
        ClientHelpers.bindText(REQ_ENERGY);

        matrices.translate(relX+22+a,relY+80,0);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        float percent = (float)tile.energy / 100000;

        blit(matrices,0,0,0,0,16,(int)(percent*33),16,33);
        matrices.popPose();
    }
    private void renderItemAndTooltip(ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices){
        minecraft.getItemRenderer().renderGuiItem(toRender,place1,place2);
        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            renderTooltip(matrices,toRender,mousex,mousey);
            matrices.popPose();
        }
    }
}
