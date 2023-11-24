package com.finderfeed.solarcraft.client.screens;


import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.LexiconScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.client.screens.FDSizedScreenComponent;
import com.finderfeed.solarcraft.local_library.client.screens.screen_coomponents.ScissoredTextBox;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconRecipesScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class LoreScreen extends LexiconScreen {

    private final Component lore;
    private final ResourceLocation IMAGE_LOCATION;
    private final ResourceLocation MAIN = new ResourceLocation("solarcraft","textures/gui/lore_screen_new.png");

    private int ticker = 0;

    public LoreScreen(Component lore,String image) {
        this.lore = lore;
        this.IMAGE_LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/lore_images/"+image+".png");
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ItemStackTabButton(relX+255,relY+27,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
                (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
                }));
        addRenderableWidget(new ItemStackTabButton(relX+255,relY+27 + 21,17,17,(button)->{
            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();

        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
        }));

        ScissoredTextBox textBox = new ScissoredTextBox(this,relX + 14,relY + 101,228,95,
                SolarLexiconScreen.TEXT_COLOR,0xff3A3A3A,0xff999999,
                lore);
        textBox.setFocused(true);
        this.addFDComponent("textBox",textBox);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {

        PoseStack matrices = graphics.pose();

        ClientHelpers.bindText(MAIN);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
        ClientHelpers.bindText(IMAGE_LOCATION);
        RenderingTools.blitWithBlend(matrices,relX+21,relY+19,0,0,60,60,60,60,0,1f);

        this.renderComponents(graphics,mousex,mousey,partialTicks,"textBox");



        super.render(graphics, mousex, mousey, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        this.ticker++;
    }

    @Override
    public int getScreenWidth() {
        return 256;
    }

    @Override
    public int getScreenHeight() {
        return 209;
    }
}
