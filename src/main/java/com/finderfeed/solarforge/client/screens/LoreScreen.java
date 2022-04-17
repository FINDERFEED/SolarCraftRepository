package com.finderfeed.solarforge.client.screens;


import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.magic.items.solar_lexicon.screen.ItemStackButton;
import com.finderfeed.solarforge.magic.items.solar_lexicon.screen.SolarLexiconRecipesScreen;
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
        this.relX = (width/scale - 183)/2 - 40;
        this.relY = (height - 218*scale)/2/scale;
        addRenderableWidget(new ItemStackButton(relX+74+53,relY+9,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackButton(relX+61+53,relY+9,12,12,(button)->{
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
        int stringColor = 0xee2222;
        ClientHelpers.bindText(MAIN);
        blit(matrices,relX,relY,0,0,256,256,256,256);
        ClientHelpers.bindText(IMAGE_LOCATION);
        blit(matrices,relX+19,relY+19,0,0,60,60,60,60);


        int posX = relX+14;
        int posY = relY+100;
        RenderingTools.drawBoundedText(matrices,posX,posY,43,lore.getString(),stringColor);





        super.render(matrices, mousex, mousey, partialTicks);
    }
}
