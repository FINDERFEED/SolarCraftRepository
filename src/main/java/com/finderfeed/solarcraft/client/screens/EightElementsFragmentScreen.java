package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.ClientHelpers;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconRecipesScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class EightElementsFragmentScreen extends ScreenWithPages{
    private int ticker = 0;
    public static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/lore_screen_new.png");
    public static final ResourceLocation IMAGE_LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/lore_images/elements.png");
    public static final ResourceLocation IMAGE_LOCATION_2 = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/all_elements.png");
    private final ResourceLocation MAIN_SCREEN_2 = new ResourceLocation("solarcraft","textures/gui/solar_lexicon_info_screen_new.png");

    public EightElementsFragmentScreen() {
        super(9);
    }

    @Override
    protected void init() {
        super.init();
        ticker = 0;
        addRenderableWidget(new ItemStackTabButton(relX+258,relY+28,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackTabButton(relX+258,relY+28 + 18,17,17,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f));
    }


    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        int stringColor = SolarLexiconScreen.TEXT_COLOR;
        if (getCurrentPage() == 1){

            ClientHelpers.bindText(MAIN_SCREEN);
            RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
            ClientHelpers.bindText(IMAGE_LOCATION);
            RenderingTools.blitWithBlend(matrices,relX+21,relY+19,0,0,60,60,60,60,0,1f);


            int posX = relX+14;
            int posY = relY+100;

            RenderingTools.drawBoundedTextObfuscated(graphics,posX,posY,45,Component.translatable("eight_elements.lore"),stringColor,ticker*4);
        }else{
            ClientHelpers.bindText(MAIN_SCREEN_2);
            RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);

            ClientHelpers.bindText(IMAGE_LOCATION_2);
            RenderingTools.blitWithBlend(matrices,relX+32,relY+32,(getCurrentPage()-2) * 16,0,16,16,128,16,0,1f);

            int posX = relX+14;
            int posY = relY+82;
            RenderingTools.drawBoundedTextObfuscated(graphics,posX,posY,45,Component.translatable("solarcraft.rune_element_"+(getCurrentPage()-1)),stringColor,ticker*4);
//            RenderingTools.drawBoundedText(matrices,posX,posY,45,Component.translatable("solarcraft.rune_element_"+(getCurrentPage()-1)).getString(),stringColor);
        }


        super.render(graphics, mousex, mousey, partialTicks);
    }

    @Override
    protected int getScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxYDownScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxXRightScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxYUpScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxXLeftScrollValue() {
        return 0;
    }

    @Override
    public int[] getPageButtonsCoords() {
        return new int[]{212,11};
    }

    @Override
    public void tick() {
        super.tick();
        ticker++;
    }

    @Override
    public void nextPage(boolean resetScrolls) {
        super.nextPage(resetScrolls);
        ticker = 0;
    }

    @Override
    public void previousPage(boolean resetScrolls) {
        super.previousPage(resetScrolls);
        ticker = 0;
    }
}
