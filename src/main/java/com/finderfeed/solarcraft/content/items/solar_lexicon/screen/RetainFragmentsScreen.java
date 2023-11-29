package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.screens.ScrollableScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.RetainFragmentPacket;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RetainFragmentsScreen extends ScrollableLexiconScreen {

    public final Component RETAIN_FRAGMENTS = Component.translatable("solarcraft.retain_fragments");

    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/stages_page.png");
    public final ResourceLocation BG = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_main_page_scrollablep.png");

//    public ItemStackButton stagesPage;
    public InfoButton infoButton;

    private List<AbstractWidget> moveable = new ArrayList<>();

    @Override
    protected void init() {
        super.init();
        moveable.clear();
        infoButton = new InfoButton(relX +  206 + 35,relY + 43,13,13,(btn1, graphics, mx, my)->{
            graphics.renderTooltip(font,font.split(RETAIN_FRAGMENTS.copy(),200),mx,my);
        });
//        stagesPage  = new ItemStackTabButton(relX+98,relY + 20,17,17,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SCItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f);


        int y = 0;
        int x = 0;
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            if (AncientFragmentHelper.doPlayerHasFragment(Minecraft.getInstance().player,fragment)){
                ItemStackButton button = new ItemStackButton(relX + 17 + x * 30,relY + 17 + y * 30,24,24,(b)->{
                    SCPacketHandler.INSTANCE.sendToServer(new RetainFragmentPacket(fragment.getId()));
                },fragment.getIcon().getDefaultInstance(),1.5f,(b,graphics,mx,my)->{
                    addPostRenderEntry((gr,mousex,mousey,partialTicks)->graphics.renderTooltip(font, fragment.getTranslation(), mx, my));
                });
                addWidget(button);
                moveable.add(button);
                x += 1;
                if (x >= 7){
                    y += 1;
                    x = 0;
                }
            }
        }

//        addRenderableWidget(stagesPage);
        addRenderableWidget(infoButton);
//        stagesPage.x = relX + 207 + 35 - 3;
//        stagesPage.y = relY + 164 - 137 - 3;

    }

    @Override
    public int getScreenWidth() {
        return 240;
    }

    @Override
    public int getScreenHeight() {
        return 207;
    }


    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float pticks) {

        renderEscapeText(graphics);
        PoseStack matrices = graphics.pose();

        ClientHelpers.bindText(BG);
        RenderingTools.blitWithBlend(matrices,relX+ 10,relY+ 10,0,0,220,188,512,512,0,1f);
        RenderingTools.scissor(relX+10,relY+10,220,187);

        for (AbstractWidget w : moveable){
            w.render(graphics,mousex,mousey,pticks);
        }
        RenderSystem.disableScissor();

        ClientHelpers.bindText(MAIN_SCREEN);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
//        stagesPage.render(graphics,mousex,mousey,pticks);
        infoButton.render(graphics,mousex,mousey,pticks);
        this.runPostEntries(graphics,mousex,mousey,pticks);


    }
    @Override
    public int getScrollValue() {
        return 4;
    }

    @Override
    public int getXRightScroll() {
        return 0;
    }

    @Override
    public int getYDownScroll() {
        return 600;
    }

    @Override
    public List<AbstractWidget> getMovableWidgets() {
        return moveable;
    }



    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
