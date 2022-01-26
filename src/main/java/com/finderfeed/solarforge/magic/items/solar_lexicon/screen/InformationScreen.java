package com.finderfeed.solarforge.magic.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.TextComponent;




public class InformationScreen extends Screen {

    public int relX;
    public int relY;
    private final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_lexicon_info_screen_new.png");
    private InfusingRecipeScreen screen;
    private InfusingCraftingRecipeScreen screenCrafting;
    private AncientFragment fragment;

    public InformationScreen(AncientFragment fragment,InfusingRecipeScreen screen) {
        super(new TextComponent(""));
        this.screen = screen;
        this.fragment = fragment;
    }

    public InformationScreen(AncientFragment fragment,InfusingCraftingRecipeScreen screen) {
        super(new TextComponent(""));
        this.screenCrafting = screen;
        this.fragment = fragment;
    }

    @Override
    protected void init() {

        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 40;
        this.relY = (height - 218*scale)/2/scale;

        Item i = screen != null ? SolarForge.INFUSER_ITEM.get() : ItemsRegister.INFUSING_TABLE.get();

        ItemStackButton button = new ItemStackButton(relX+232,relY+9,16,16,(buttons)->{
            if (screen != null) {
                Minecraft.getInstance().setScreen(screen);
            }else{
                Minecraft.getInstance().setScreen(screenCrafting);
            }
        }, i.getDefaultInstance(),1,false,(buttons,matrices,b,c)->{
            renderTooltip(matrices,new TextComponent("Craft"),b,c);
        });
        if (screen != null || screenCrafting != null){
            addRenderableWidget(button);
        }
        addRenderableWidget(new ItemStackButton(relX+236,relY+187,12,12,(buttons)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        super.init();
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        int stringColor = 0xee2222;
        ClientHelpers.bindText(LOC);
        blit(matrices,relX,relY,0,0,256,256);
        drawString(matrices,Minecraft.getInstance().font,fragment.getTranslation(), relX+60,relY+35,0xffffff);
        if (fragment.getType() == AncientFragment.Type.INFORMATION) {
            RenderingTools.drawBoundedText(matrices, relX + 12, relY + 80, 45, fragment.getLore().getString(),stringColor);
//            Helpers.drawBoundedText(matrices, relX + 12, relY + 80, 28, fragment.getLore().getString(),stringColor);
        }else{
            RenderingTools.drawBoundedText(matrices, relX + 12, relY + 80, 45, fragment.getItemDescription().getString(),stringColor);
//            Helpers.drawBoundedText(matrices, relX + 12, relY + 80, 28, fragment.getItemDescription().getString(),stringColor);
        }
        renderGuiItem(fragment.getIcon().getDefaultInstance(),relX+32,relY+32,Minecraft.getInstance().getItemRenderer().getModel(fragment.getIcon().getDefaultInstance(),null,null,0),1.5,1.5,1.5);
        super.render(matrices, mousex, mousey, partialTicks);
    }



    protected void renderGuiItem(ItemStack stack, int tx, int ty, BakedModel model,double x,double y,double z) {
        Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)tx, (double)ty, (double)(100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
        posestack.translate(8.0D, 8.0D, 0.0D);
        posestack.scale(1.0F, -1.0F, 1.0F);
        posestack.scale(16.0F, 16.0F, 16.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.scale((float)x,(float)y,(float)z);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !model.usesBlockLight();
        if (flag) {
            Lighting.setupForFlatItems();
        }

        Minecraft.getInstance().getItemRenderer().render(stack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, model);
        multibuffersource$buffersource.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            Lighting.setupFor3DItems();
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
    }
}
