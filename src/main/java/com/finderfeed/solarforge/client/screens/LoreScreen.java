package com.finderfeed.solarforge.client.screens;


import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.SolarLexiconRecipesScreen;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class LoreScreen extends Screen {

    private final TranslatableComponent lore;
    private final ResourceLocation IMAGE_LOCATION;
    private final ResourceLocation MAIN = new ResourceLocation("solarforge","textures/gui/lore_screen_new.png");

    public int relX;
    public int relY;

    private int ticker = 0;

    public LoreScreen(TranslatableComponent lore,String image) {
        super(new TextComponent(""));
        this.lore = lore;
        this.IMAGE_LOCATION = new ResourceLocation(SolarForge.MOD_ID,"textures/lore_images/"+image+".png");
    }

    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 40;
        this.relY = (height - 218*scale)/2/scale;
        addRenderableWidget(new ItemStackTabButton(relX+258,relY+27,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackTabButton(relX+258,relY+27 + 21,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {

        ClientHelpers.bindText(MAIN);
        blit(matrices,relX,relY,0,0,256,256,256,256);
        ClientHelpers.bindText(IMAGE_LOCATION);
        blit(matrices,relX+21,relY+19,0,0,60,60,60,60);


        int posX = relX+14;
        int posY = relY+100;
        RenderingTools.drawBoundedTextObfuscated(matrices,posX,posY,40,lore, SolarLexiconScreen.TEXT_COLOR,ticker*4);




        super.render(matrices, mousex, mousey, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        this.ticker++;
    }
}
