package com.finderfeed.solarcraft.client.screens;


import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
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

public class LoreScreen extends Screen {

    private final Component lore;
    private final ResourceLocation IMAGE_LOCATION;
    private final ResourceLocation MAIN = new ResourceLocation("solarcraft","textures/gui/lore_screen_new.png");

    public int relX;
    public int relY;

    private int ticker = 0;

    public LoreScreen(Component lore,String image) {
        super(Component.literal(""));
        this.lore = lore;
        this.IMAGE_LOCATION = new ResourceLocation(SolarCraft.MOD_ID,"textures/lore_images/"+image+".png");
    }

    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 40;
        this.relY = (height - 218*scale)/2/scale;
        addRenderableWidget(new ItemStackTabButton(relX+255,relY+27,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackTabButton(relX+255,relY+27 + 21,17,17,(button)->{
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
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {

        PoseStack matrices = graphics.pose();

        ClientHelpers.bindText(MAIN);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
        ClientHelpers.bindText(IMAGE_LOCATION);
        RenderingTools.blitWithBlend(matrices,relX+21,relY+19,0,0,60,60,60,60,0,1f);


        int posX = relX+14;
        int posY = relY+100;
        RenderingTools.drawBoundedTextObfuscated(graphics,posX,posY,40,lore, SolarLexiconScreen.TEXT_COLOR,ticker*4);




        super.render(graphics, mousex, mousey, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        this.ticker++;
    }
}
