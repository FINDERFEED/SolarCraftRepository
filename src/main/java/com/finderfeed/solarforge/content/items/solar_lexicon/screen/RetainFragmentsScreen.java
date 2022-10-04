package com.finderfeed.solarforge.content.items.solar_lexicon.screen;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.screens.ScrollableScreen;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.RetainFragmentPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class RetainFragmentsScreen extends ScrollableScreen {

    public final TranslatableComponent RETAIN_FRAGMENTS = Component.translatable("solarcraft.retain_fragments");

    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/stages_page.png");
    public final ResourceLocation BG = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/solar_lexicon_main_page_scrollablep.png");

    public ItemStackButton stagesPage = new ItemStackTabButton(relX+100,relY + 20,12,12,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f);
    public InfoButton infoButton;

    @Override
    protected void init() {
        super.init();
        infoButton = new InfoButton(relX +  206 + 35,relY + 43,13,13,(btn1, matrices1, mx, my)->{
            renderTooltip(matrices1,font.split(RETAIN_FRAGMENTS.copy(),200),mx,my);
        });

        setAsStaticWidget(stagesPage);
        setAsStaticWidget(infoButton);

        int y = 0;
        int x = 0;
        for (AncientFragment fragment : AncientFragment.getAllFragments()){
            if (ProgressionHelper.doPlayerHasFragment(Minecraft.getInstance().player,fragment)){
                ItemStackButton button = new ItemStackButton(relX + 17 + x * 30,relY + 17 + y * 30,24,24,(b)->{
                    SolarForgePacketHandler.INSTANCE.sendToServer(new RetainFragmentPacket(fragment.getId()));
                },fragment.getIcon().getDefaultInstance(),1.5f,(b,matrices,mx,my)->{
                    addPostRenderEntry(()->Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));
                });
                addRenderableWidget(button);
                x += 1;
                if (x >= 7){
                    y += 1;
                    x = 0;
                }
            }
        }

        addRenderableWidget(stagesPage);
        addRenderableWidget(infoButton);
        stagesPage.x = relX + 207 + 35;
        stagesPage.y = relY + 164 - 137;

    }


    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float pticks) {


        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();
        ClientHelpers.bindText(BG);
        blit(matrices,getRelX()+ 10,getRelY()+ 10,0,0,220,188,512,512);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(width/2-((30+83)*scale),height/2-(88*scale),((188+35)*scale),187*scale);
        List<AbstractWidget> btns = ClientHelpers.getScreenButtons(this);
        btns.removeAll(getStaticWidgets());

        for (AbstractWidget w : btns){
            w.render(matrices,mousex,mousey,pticks);
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        ClientHelpers.bindText(MAIN_SCREEN);
        blit(matrices,getRelX(),getRelY(),0,0,256,256);
        stagesPage.render(matrices,mousex,mousey,pticks);
        infoButton.render(matrices,mousex,mousey,pticks);
        this.runPostEntries();


    }
    @Override
    protected int getScrollValue() {
        return 4;
    }

    @Override
    protected int getMaxYDownScrollValue() {
        return 600;
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
    public boolean isPauseScreen() {
        return false;
    }
}
