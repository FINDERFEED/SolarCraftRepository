package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.system.CallbackI;


//TODO:INFO SCREEN
public class InformationScreen extends Screen {

    public int relX;
    public int relY;
    private final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/solar_lexicon_info_screen.png");
    private InfusingRecipeScreen screen;
    private AncientFragment fragment;

    public InformationScreen(AncientFragment fragment,InfusingRecipeScreen screen) {
        super(new StringTextComponent(""));
        this.screen = screen;
        this.fragment = fragment;
    }


    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;

        ItemStackButton button = new ItemStackButton(relX+180,relY+9,16,16,(buttons)->{
            Minecraft.getInstance().setScreen(screen);
        }, SolarForge.INFUSING_STAND_ITEM.get().getDefaultInstance(),1,false,(buttons,matrices,b,c)->{
            renderTooltip(matrices,new StringTextComponent("Craft"),b,c);
        });
        if (screen != null){
            addButton(button);
        }
        addButton(new ItemStackButton(relX+185,relY+172,12,12,(buttons)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mousex, int mousey, float partialTicks) {
        Minecraft.getInstance().getTextureManager().bind(LOC);
        blit(matrices,relX,relY,0,0,256,256);
        drawString(matrices,Minecraft.getInstance().font,fragment.getTranslation(), relX+60,relY+35,0xffffff);
        if (fragment.getType() == AncientFragment.Type.INFORMATION) {
            Helpers.drawBoundedText(matrices, relX + 10, relY + 80, 28, fragment.getLore().getString());
        }else{
            Helpers.drawBoundedText(matrices, relX + 10, relY + 80, 28, fragment.getItemDescription().getString());
        }
        renderGuiItem(fragment.getIcon().getDefaultInstance(),relX+32,relY+32,Minecraft.getInstance().getItemRenderer().getModel(fragment.getIcon().getDefaultInstance(),null,null),1.5,1.5,1.5);
        super.render(matrices, mousex, mousey, partialTicks);
    }



    protected void renderGuiItem(ItemStack p_191962_1_, int p_191962_2_, int p_191962_3_, IBakedModel p_191962_4_,double x,double y,double z) {
        RenderSystem.pushMatrix();
        Minecraft.getInstance().getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.translatef((float)p_191962_2_, (float)p_191962_3_, 100.0F + Minecraft.getInstance().getItemRenderer().blitOffset);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean flag = !p_191962_4_.usesBlockLight();
        if (flag) {
            RenderHelper.setupForFlatItems();
        }
        matrixstack.scale((float) x,(float) y,(float) z);
        Minecraft.getInstance().getItemRenderer().render(p_191962_1_, ItemCameraTransforms.TransformType.GUI, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, p_191962_4_);
        irendertypebuffer$impl.endBatch();
        RenderSystem.enableDepthTest();
        if (flag) {
            RenderHelper.setupFor3DItems();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }
}
