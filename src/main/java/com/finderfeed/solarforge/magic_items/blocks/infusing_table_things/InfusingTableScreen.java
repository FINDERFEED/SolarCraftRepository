package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;

import java.util.Optional;

public class InfusingTableScreen extends ContainerScreen<InfusingTableContainer> {
    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarforge","textures/gui/energy_bar.png");
    private static final ResourceLocation GUI_TEXT = new ResourceLocation("solarforge","textures/gui/solar_infuser_gui.png");
    public int relX;
    public int relY;
    public InfusingTableScreen(InfusingTableContainer container, PlayerInventory inv, ITextComponent text) {
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
    public void render(MatrixStack stack, int rouseX, int rouseY, float partialTicks){

        this.renderBackground(stack);

        super.render(stack,rouseX,rouseY,partialTicks);
        this.renderTooltip(stack,rouseX,rouseY);



    }
    @Override
    protected void renderBg(MatrixStack matrices, float partialTicks, int x, int y) {
        matrices.pushPose();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI_TEXT);
        int a= 0;
        if ((int)minecraft.getWindow().getGuiScale() == 2){
            a = -1;
        }
        this.blit(matrices, relX+4+a, relY-8, 0, 0, 190, 230);
        ItemRenderer renderd = minecraft.getItemRenderer();
        InfusingTableTileEntity tile = this.menu.te;

        renderd.renderGuiItem(tile.getItem(1),relX+137+a,relY+58);
        renderd.renderGuiItem(tile.getItem(2),relX+123+a,relY+19);
        renderd.renderGuiItem(tile.getItem(3),relX+84+a,relY+5);
        renderd.renderGuiItem(tile.getItem(4),relX+45+a,relY+19);
        renderd.renderGuiItem(tile.getItem(5),relX+31+a,relY+58);
        renderd.renderGuiItem(tile.getItem(6),relX+45+a,relY+97);
        renderd.renderGuiItem(tile.getItem(7),relX+84+a,relY+111);
        renderd.renderGuiItem(tile.getItem(8),relX+123+a,relY+97);
        Optional<InfusingRecipe> recipe = minecraft.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,tile,minecraft.level);

        if (recipe.isPresent()){
            renderd.renderGuiItem(recipe.get().output,relX+159+a,relY+2);
            
        }
        matrices.popPose();
        matrices.pushPose();
        minecraft.getTextureManager().bind(REQ_ENERGY);
        matrices.translate(relX+22+a,relY+80,0);
        matrices.mulPose(Vector3f.ZP.rotationDegrees(180));
        float percent = (float)tile.energy / 100000;

        blit(matrices,0,0,0,0,16,(int)(percent*33),16,33);
        matrices.popPose();
    }

}
