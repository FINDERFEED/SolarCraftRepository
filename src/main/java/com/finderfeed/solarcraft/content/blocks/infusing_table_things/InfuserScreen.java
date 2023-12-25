package com.finderfeed.solarcraft.content.blocks.infusing_table_things;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import com.finderfeed.solarcraft.misc_things.PhantomInventory;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InfuserScreen extends AbstractContainerScreen<InfuserContainer> {
    private InfuserTileEntity.Tier tier = null;

    public final ResourceLocation REQ_ENERGY = new ResourceLocation("solarcraft","textures/gui/energy_bar.png");
    private static final ResourceLocation GUI_TEXT = new ResourceLocation("solarcraft","textures/gui/solar_infuser_gui.png");
    private static final ResourceLocation GUI_TEXT_TIER_1 = new ResourceLocation("solarcraft","textures/gui/solar_infuser_tier_1.png");
    private static final ResourceLocation ENERGY_GUI = new ResourceLocation("solarcraft","textures/gui/infuser_energy_gui.png");
    //60*6
    public final ResourceLocation RUNIC_ENERGY_BAR = new ResourceLocation("solarcraft","textures/gui/runic_energy_bar.png");
    public final ResourceLocation RUNIC_ENERGY_BAR_T = new ResourceLocation("solarcraft","textures/gui/runic_energy_bar_t.png");
    public int relX;
    public int relY;

    private static final RunicEnergyCost ZERO_COST = new RunicEnergyCost();

    private List<Runnable> postRender = new ArrayList<>();
    public InfuserScreen(InfuserContainer container, Inventory inv, Component text) {
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
        menu.te.calculateTier();
        this.tier = menu.te.getTier();
    }
    @Override
    public void render(GuiGraphics graphics, int rouseX, int rouseY, float partialTicks){

        this.renderBackground(graphics,rouseX,rouseY,partialTicks);

        super.render(graphics,rouseX,rouseY,partialTicks);
        this.renderTooltip(graphics,rouseX,rouseY);



    }
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int x, int y) {
        InfuserTileEntity tile = this.menu.te;
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        int offs = -3;
        if (tier == InfuserTileEntity.Tier.SOLAR_ENERGY) {
            ClientHelpers.bindText(GUI_TEXT);
        }else{
            ClientHelpers.bindText(GUI_TEXT_TIER_1);
        }
        int a= 0;
        if ((int)minecraft.getWindow().getGuiScale() == 2){
            a = -1;
        }
        RenderingTools.blitWithBlend(matrices, relX+4+a + offs, relY-8, 0, 0, 190, 230,256,256,0,1f);



//        renderItemAndTooltip(tile.getItem(1),relX+137+a,relY+58,x,y,matrices);
//        renderItemAndTooltip(tile.getItem(2),relX+123+a,relY+19,x,y,matrices);
//        renderItemAndTooltip(tile.getItem(3),relX+84+a,relY+5,x,y,matrices);
//        renderItemAndTooltip(tile.getItem(4),relX+45+a,relY+19,x,y,matrices);
//        renderItemAndTooltip(tile.getItem(5),relX+31+a,relY+58,x,y,matrices);
//        renderItemAndTooltip(tile.getItem(6),relX+45+a,relY+97,x,y,matrices);
//        renderItemAndTooltip(tile.getItem(7),relX+84+a,relY+111,x,y,matrices);
//        renderItemAndTooltip(tile.getItem(8),relX+123+a,relY+97,x,y,matrices);
        Optional<RecipeHolder<InfusingRecipe>> recipe = minecraft.level.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING.get(),new PhantomInventory(tile.getInventory()),minecraft.level);

//        if (recipe.isPresent()){
//            renderItemAndTooltip(recipe.get().output,relX+159+a,relY+2,x,y,matrices);
//
//        }
        matrices.popPose();

        if (tier == InfuserTileEntity.Tier.SOLAR_ENERGY) {
            float percent = (float) tile.solarEnergy / tile.getMaxSolarEnergy()*33;

            if (recipe.isPresent()) {
                float percentNeeded = (float) recipe.get().value().requriedSolarEnergy / (float)tile.getMaxSolarEnergy() * 33;
                RenderingTools.fill(matrices,relX + 11 + a,relY + 80,relX + 21 + a,relY + (int)(80 - percentNeeded),1,1,0,0.5f);
            }
            if (RenderingTools.isMouseInBorders(x,y,relX + 11 + a,relY + 80 - 33,relX + 21 + a,relY + 80)){
                postRender.add(()->{
                    graphics.renderTooltip(font,Component.literal(tile.solarEnergy + "/" + tile.getMaxSolarEnergy()),x,y);
                });
            }
            RenderingTools.fill(matrices,relX + 11 + a,relY + 80,relX + 21 + a,relY + (int)(80 - percent),1,1,0,1);
        }

        if (tier == InfuserTileEntity.Tier.RUNIC_ENERGY || tier == InfuserTileEntity.Tier.SOLAR_ENERGY ) {
            matrices.pushPose();

            if (recipe.isPresent()) {
                InfusingRecipe recipe1 = recipe.get().value();
                List<Component> components = RenderingTools.renderRunicEnergyGui(graphics,relX - 74 + a +offs,relY - 8,x,y,tile.getRunicEnergyContainer(),recipe1.RUNIC_ENERGY_COST, (float) tile.getRunicEnergyLimit());

                for (Component component : components){
                    graphics.renderTooltip(font,component,x,y);
                }
            }else{
                List<Component> components = RenderingTools.renderRunicEnergyGui(graphics,relX - 74 + a +offs,relY - 8,x,y,tile.getRunicEnergyContainer(),null, (float) tile.getRunicEnergyLimit());
                for (Component component : components){
                    graphics.renderTooltip(font,component,x,y);
                }
            }


        }

        renderItemAndTooltip(tile.getItem(0) , relX + 7 + a + offs + 34,    relY - 8 + a + 20,x,y,graphics);
        renderItemAndTooltip(tile.getItem(1) , relX + 7 + a + offs + 80,    relY - 8 + a + 13,x,y,graphics);
        renderItemAndTooltip(tile.getItem(2) , relX + 7 + a + offs + 126,   relY - 8 + a + 20,x,y,graphics);
        renderItemAndTooltip(tile.getItem(3) , relX + 7 + a + offs + 54,    relY - 8 + a + 40,x,y,graphics);
        renderItemAndTooltip(tile.getItem(4) , relX + 7 + a + offs + 106,   relY - 8 + a + 40,x,y,graphics);
        renderItemAndTooltip(tile.getItem(5) , relX + 7 + a + offs + 27,    relY - 8 + a + 66,x,y,graphics);
        renderItemAndTooltip(tile.getItem(7) , relX + 7 + a + offs + 133,   relY - 8 + a + 66,x,y,graphics);
        renderItemAndTooltip(tile.getItem(8) , relX + 7 + a + offs + 54,    relY - 8 + a + 92,x,y,graphics);
        renderItemAndTooltip(tile.getItem(9) , relX + 7 + a  + offs+ 106,   relY - 8 + a + 92,x,y,graphics);
        renderItemAndTooltip(tile.getItem(10),relX +  7 + a + offs + 34,    relY - 8 + a + 112,x,y,graphics);
        renderItemAndTooltip(tile.getItem(11),relX +  7 + a + offs + 80,    relY - 8 + a + 119,x,y,graphics);
        renderItemAndTooltip(tile.getItem(12),relX +  7 + a + offs + 126,   relY - 8 + a + 112,x,y,graphics);

        if (recipe.isPresent()){
            renderItemAndTooltip(recipe.get().value().output.copy(),relX+159+a + offs + 3,relY+2,x,y,graphics);

        }


        matrices.popPose();
        if (!postRender.isEmpty()){
            postRender.forEach(Runnable::run);
            postRender.clear();
        }
    }
    private void renderItemAndTooltip(ItemStack toRender, int place1, int place2, int mousex, int mousey, GuiGraphics graphics){
        PoseStack matrices = graphics.pose();
        graphics.renderItem(toRender,place1,place2);
        graphics.renderItemDecorations(font,toRender,place1,place2);
        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            graphics.renderTooltip(font,toRender,mousex,mousey);

            matrices.popPose();
        }
    }


    //runic energy bar texture is binded before it
//    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, double energyAmount,boolean simulate,int mousex,int mousey){
//
//        matrices.pushPose();
//
//        int texturex = Math.round((float)energyAmount/(float)menu.te.getRunicEnergyLimit()*60);
//        matrices.translate(offsetx,offsety,0);
//        matrices.mulPose(Vector3f.ZN.rotationDegrees(90));
//        if (!simulate) {
//            blit(matrices, 0, 0, 0, 0, texturex, 6);
//            if (mousex+2 > offsetx && mousex+2 < offsetx + 7 && mousey > offsety-60 && mousey < offsety){
//                postRender.add(()->{
//                    renderTooltip(matrices,Component.literal((float) energyAmount + "/" + menu.te.getRunicEnergyLimit()),mousex-3,mousey+3);
//                });
//            }
//        }else{
//
//            blitm(matrices, 0, 0, 0, 0, texturex, 6,60,6);
//        }
//        matrices.popPose();
//    }




    //those are just to render transparent texture, copied from vanilla and a bit modified
    public static void blitm(PoseStack p_93161_, int p_93162_, int p_93163_, int p_93164_, int p_93165_, float p_93166_, float p_93167_, int p_93168_, int p_93169_, int p_93170_, int p_93171_) {
        innerBlitm(p_93161_, p_93162_, p_93162_ + p_93164_, p_93163_, p_93163_ + p_93165_, 0, p_93168_, p_93169_, p_93166_, p_93167_, p_93170_, p_93171_);
    }

    public static void blitm(PoseStack p_93134_, int p_93135_, int p_93136_, float p_93137_, float p_93138_, int p_93139_, int p_93140_, int p_93141_, int p_93142_) {
        blitm(p_93134_, p_93135_, p_93136_, p_93139_, p_93140_, p_93137_, p_93138_, p_93139_, p_93140_, p_93141_, p_93142_);
    }

    private static void innerBlitm(PoseStack p_93188_, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_) {
        innerBlitm(p_93188_.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float)p_93198_, (p_93196_ + (float)p_93194_) / (float)p_93198_, (p_93197_ + 0.0F) / (float)p_93199_, (p_93197_ + (float)p_93195_) / (float)p_93199_);
    }

    private static void innerBlitm(Matrix4f p_93113_, int p_93114_, int p_93115_, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        BufferBuilder var10 = Tesselator.getInstance().getBuilder();
        var10.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        var10.vertex(p_93113_, (float)p_93114_, (float)p_93117_, (float)p_93118_).uv(p_93119_, p_93122_).color(1,1,1,0.5f).endVertex();
        var10.vertex(p_93113_, (float)p_93115_, (float)p_93117_, (float)p_93118_).uv(p_93120_, p_93122_).color(1,1,1,0.5f).endVertex();
        var10.vertex(p_93113_, (float)p_93115_, (float)p_93116_, (float)p_93118_).uv(p_93120_, p_93121_).color(1,1,1,0.5f).endVertex();
        var10.vertex(p_93113_, (float)p_93114_, (float)p_93116_, (float)p_93118_).uv(p_93119_, p_93121_).color(1,1,1,0.5f).endVertex();
//        var10.end();
        BufferUploader.drawWithShader(var10.end());

    }

}
