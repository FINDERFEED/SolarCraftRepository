package com.finderfeed.solarforge.client.screens;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen.ItemStackButton;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen.SolarLexiconRecipesScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;

public class LoreScreen extends Screen {

    private final TranslatableComponent lore;
    private final ResourceLocation IMAGE_LOCATION;
    private final ResourceLocation MAIN = new ResourceLocation("solarforge","textures/gui/lore_screen.png");

    public int relX;
    public int relY;

    public LoreScreen(TranslatableComponent lore,String image) {
        super(new TextComponent(""));
        this.lore = lore;
        this.IMAGE_LOCATION = new ResourceLocation("solarforge","textures/lore_images/"+image+".png");
    }

    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;
        addRenderableWidget(new ItemStackButton(relX+74+72,relY+9,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        addRenderableWidget(new ItemStackButton(relX+61+72,relY+9,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,false));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
//        BlockState state = Blocks.ACACIA_LOG.defaultBlockState();
//        BlockRenderDispatcher disp = Minecraft.getInstance().getBlockRenderer();
//        BakedModel model = disp.getBlockModel(state);
//        VertexConsumer cons = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(ItemBlockRenderTypes.getRenderType(state,false));
//        matrices.pushPose();
//        matrices.translate(2,2,-2);
//        int i = Minecraft.getInstance().getBlockColors().getColor(state, (BlockAndTintGetter)null, (BlockPos)null, 0);
//        float f = (float)(i >> 16 & 255) / 255.0F;
//        float f1 = (float)(i >> 8 & 255) / 255.0F;
//        float f2 = (float)(i & 255) / 255.0F;
//        disp.getModelRenderer().renderModel(matrices.last(), cons
//                ,state,model,f,f1,f2,4, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
//        matrices.popPose();


        int stringColor = 0xee2222;
        ClientHelpers.bindText(MAIN);
        blit(matrices,relX,relY,0,0,256,256,256,256);
        ClientHelpers.bindText(IMAGE_LOCATION);
        blit(matrices,relX+111,relY+25,0,0,70,70,70,70);


        int posX = relX+18;
        int posY = relY+20;

        for (String str : RenderingTools.splitString(lore.getString(),13)){
            drawString(matrices,font,str,posX,posY,stringColor);
            posY+=8;
            if (posY >= 195){
                posY=relY+110;
                posX+=90;
            }
        }




        super.render(matrices, mousex, mousey, partialTicks);
    }
}
