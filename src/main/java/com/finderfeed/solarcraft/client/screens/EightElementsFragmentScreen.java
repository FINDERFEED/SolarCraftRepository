package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.*;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;

import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class EightElementsFragmentScreen extends LexiconScreen {
    private int ticker = 0;
    public static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/lore_screen_new.png");
    public static final ResourceLocation IMAGE_LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/lore_images/elements.png");
    public static final ResourceLocation IMAGE_LOCATION_2 = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/all_elements.png");
    private final ResourceLocation MAIN_SCREEN_2 = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_info_screen_new.png");

    public EightElementsFragmentScreen() {
        super();
    }

    @Override
    protected void init() {
        super.init();
        ticker = 0;
        addRenderableWidget(new ItemStackTabButton(relX+258,relY+28,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
                (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
                }));
        addRenderableWidget(new ItemStackTabButton(relX+258,relY+28 + 18,17,17,(button)->{
            Minecraft mc = Minecraft.getInstance();
//            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
//            lexicon.currentSavedScreen = this;
            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();
//            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
        }));

        addRenderableWidget(new ImageButton(relX + 212 + 16, relY + 11, 16, 16, 0, 0, 0, StructureScreen.BUTTONS, 16, 32, (button) -> {
            this.nextPage();
        }) {
            @Override
            public void playDownSound(SoundManager smanager) {
                smanager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(), 1, 1));
            }
        });
        addRenderableWidget(new ImageButton(relX + 212 , relY+11, 16, 16, 0, 16, 0, StructureScreen.BUTTONS, 16, 32, (button) -> {
            this.previousPage();
        }) {
            @Override
            public void playDownSound(SoundManager smanager) {
                smanager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(), 1, 1));
            }
        });
    }


    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        int stringColor = SolarLexiconScreen.TEXT_COLOR;
        if (currentPage == 0){

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
            RenderingTools.blitWithBlend(matrices,relX+32,relY+32,(currentPage-1) * 16,0,16,16,128,16,0,1f);

            int posX = relX+14;
            int posY = relY+82;
            RenderingTools.drawBoundedTextObfuscated(graphics,posX,posY,45,Component.translatable("solarcraft.rune_element_"+(currentPage)),stringColor,ticker*4);
        }


        super.render(graphics, mousex, mousey, partialTicks);
    }



    @Override
    public void tick() {
        super.tick();
        ticker++;
    }

    @Override
    public int getScreenWidth() {
        return 256;
    }

    @Override
    public int getScreenHeight() {
        return 209;
    }

    @Override
    public int getPagesCount() {
        return 9;
    }

    @Override
    public void onPageChanged(int newPage) {
        super.onPageChanged(newPage);
        ticker = 0;
    }
}
