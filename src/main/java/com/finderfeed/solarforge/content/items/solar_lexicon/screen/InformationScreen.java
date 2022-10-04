package com.finderfeed.solarforge.content.items.solar_lexicon.screen;

import com.finderfeed.solarforge.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;

import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.registries.items.SolarcraftItems;
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

import net.minecraft.network.chat.Component;




public class InformationScreen extends Screen {

    public int relX;
    public int relY;
    private final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_lexicon_info_screen_new.png");
    private InfusingRecipeScreen screen;
    private InfusingCraftingRecipeScreen screenInfusingCrafting;
    private CraftingRecipeScreen craftingScreen;
    private AncientFragment fragment;
    private int ticker = 0;
    public InformationScreen(AncientFragment fragment,InfusingRecipeScreen screen) {
        super(Component.literal(""));
        this.screen = screen;
        this.fragment = fragment;
    }

    public InformationScreen(AncientFragment fragment,InfusingCraftingRecipeScreen screen) {
        super(Component.literal(""));
        this.screenInfusingCrafting = screen;
        this.fragment = fragment;
    }

    public InformationScreen(AncientFragment fragment,CraftingRecipeScreen screen) {
        super(Component.literal(""));
        this.craftingScreen = screen;
        this.fragment = fragment;
    }

    @Override
    public void tick() {
        super.tick();
        ticker++;
    }

    @Override
    protected void init() {
        ticker = 0;
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 40;
        this.relY = (height - 218*scale)/2/scale;

        Item i = screen != null ? SolarForge.INFUSER_ITEM.get() : screenInfusingCrafting != null ? SolarcraftItems.INFUSING_TABLE.get() : Items.CRAFTING_TABLE.asItem();

        ItemStackButton button = new ItemStackTabButton(relX+260,relY+25 + 18 + 3,12,12,(buttons)->{
            if (screen != null) {
                Minecraft.getInstance().setScreen(screen);
            }else if (screenInfusingCrafting != null){
                Minecraft.getInstance().setScreen(screenInfusingCrafting);
            }else{
                Minecraft.getInstance().setScreen(craftingScreen);
            }
        }, i.getDefaultInstance(),0.7f,(buttons,matrices,b,c)->{
            renderTooltip(matrices,Component.literal("Crafting Recipe"),b,c);
        });
        if (screen != null || screenInfusingCrafting != null || craftingScreen != null){
            addRenderableWidget(button);
        }
        addRenderableWidget(new ItemStackTabButton(relX+257 + 3,relY+28,12,12,(buttons)->{minecraft.setScreen(new SolarLexiconRecipesScreen());},
                Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,(buttons,matrices,b,c)->{
            renderTooltip(matrices,Component.literal("Go back"),b,c);
        }));
        super.init();
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        ClientHelpers.bindText(LOC);
        blit(matrices,relX,relY,0,0,256,209,256,256);

        drawString(matrices,Minecraft.getInstance().font,fragment.getTranslation(), relX+60,relY+35,0xffffff);
        if (fragment.getType() == AncientFragment.Type.INFORMATION) {
            RenderingTools.drawBoundedTextObfuscated(matrices, relX + 14, relY + 81, 43, fragment.getLore(),SolarLexiconScreen.TEXT_COLOR,ticker*4);
        }else{
            RenderingTools.drawBoundedTextObfuscated(matrices, relX + 14, relY + 81, 43, fragment.getItemDescription(),SolarLexiconScreen.TEXT_COLOR,ticker*4);
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
