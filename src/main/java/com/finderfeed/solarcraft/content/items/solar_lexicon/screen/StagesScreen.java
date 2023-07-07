package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.client.screens.ScrollableScreen;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.ProgressionStage;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StagesScreen extends ScrollableScreen {
    public final Component STAGES_CMP = Component.translatable("solarcraft.stages");
    public final ResourceLocation FRAME = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/frame.png");
    public final ResourceLocation QMARK = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/question_mark.png");
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/stages_page.png");
    public final ResourceLocation BG = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_main_page_scrollablep.png");
    private ArrayList<PostRender> RENDER_QMARKS = new ArrayList<>();
    private ArrayList<PostRender> RENDER_QMARKS_TOOLTIPS = new ArrayList<>();
    private ArrayList<PostRender> RENDER_FRAMES = new ArrayList<>();
    private ArrayList<PostRender> RENDER_BORDERS = new ArrayList<>();

    public ItemStackButton stagesPage;
    public InfoButton infoButton;
    public StagesScreen() {

    }


    @Override
    protected void init() {
        super.init();
        infoButton = new InfoButton(relX +  206 + 35,relY + 43,13,13,(btn1, graphics, mx, my)->{
            graphics.renderTooltip(font,font.split(STAGES_CMP.copy(),200),mx,my);
        });
        stagesPage = new ItemStackTabButton(relX+98,relY + 18,17,17,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SolarcraftItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f);
        setAsStaticWidget(stagesPage);
        setAsStaticWidget(infoButton);
        RENDER_QMARKS.clear();
        RENDER_QMARKS_TOOLTIPS.clear();
        RENDER_FRAMES.clear();
        RENDER_BORDERS.clear();
        for (int i = 0; i < ProgressionStage.STAGES_IN_ORDER.length;i++){
            ProgressionStage stage = ProgressionStage.STAGES_IN_ORDER[i];
            int y = i * 60 + getRelY() + 15 +10;
            for (int g = 0; g < stage.SELF_PROGRESSIONS.length;g++){
                int x = g * 40 + getRelX() + 15 +10;
                Progression progression = stage.SELF_PROGRESSIONS[g];
                if (Helpers.canPlayerUnlock(progression, Minecraft.getInstance().player)){
                    addRenderableWidget(new ItemStackButton(x+getCurrentScrollX(),y+getCurrentScrollY(),16,16,(btn)->{},progression.icon,1,
                            (button,graphics,mousex,mousey)->{
                                if (Helpers.hasPlayerCompletedProgression(progression,Minecraft.getInstance().player)) {
                                    graphics.renderComponentTooltip(font, List.of(progression.translation,Component.translatable("solarcraft.completed").withStyle(ChatFormatting.GREEN)), mousex, mousey);
                                }else{
                                    graphics.renderComponentTooltip(font, List.of(progression.translation,Component.translatable("solarcraft.not_completed").withStyle(ChatFormatting.RED)), mousex, mousey);
                                }
                            }));
                }else{
                    RENDER_QMARKS.add(((graphics, mousex, mousey, partialTicks) -> {
                        RenderingTools.blitWithBlend(graphics.pose(),x + getCurrentScrollX(),y + getCurrentScrollY(),0,0,16,16,16,16,0,1f);
                    }));
                    RENDER_QMARKS_TOOLTIPS.add(((graphics, mousex, mousey, partialTicks) -> {
                        if ((mousex  >= x + getCurrentScrollX() && mousex  <= x + getCurrentScrollX()+16)
                                && (mousey  >= y + getCurrentScrollY() && mousey <= y + 16 + getCurrentScrollY()) ) {
                            graphics.renderComponentTooltip(font, List.of(progression.translation,
                                    Component.translatable("solarcraft.cannot_be_completed").withStyle(ChatFormatting.GRAY)), mousex , mousey );
                        }
                    }));
                }

                RENDER_FRAMES.add(((graphics, mousex, mousey, partialTicks) -> {
                    RenderingTools.blitWithBlend(graphics.pose(),x + getCurrentScrollX() - 4,y + getCurrentScrollY() - 4,0,0,24,24,24,24,0,1f);
                }));

            }
            int l = stage.SELF_PROGRESSIONS.length;
            RENDER_BORDERS.add((graphics, mousex, mousey, partialTicks) -> {
                RenderingTools.drawFancyBorder(graphics.pose(),getRelX() + getCurrentScrollX() + 25 - 13,y + getCurrentScrollY() - 13,stage.SELF_PROGRESSIONS.length * 40 + 2,42,0);
            });
        }

        addRenderableWidget(stagesPage);
        addRenderableWidget(infoButton);
        stagesPage.x = relX + 207 + 35 - 3;
        stagesPage.y = relY + 164 - 137 - 3;

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float pticks) {

        PoseStack matrices = graphics.pose();

        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();
        ClientHelpers.bindText(BG);
        RenderingTools.blitWithBlend(matrices,getRelX()+ 10,getRelY()+ 10,0,0,220,188,512,512,0,1f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(width/2-((30+83)*scale),height/2-(88*scale),((188+35)*scale),187*scale);
        List<AbstractWidget> btns = ClientHelpers.getScreenButtons(this);
        btns.removeAll(getStaticWidgets());

        for (AbstractWidget w : btns){
            w.render(graphics,mousex,mousey,pticks);
        }
        ClientHelpers.bindText(FRAME);
        for (PostRender xy : RENDER_FRAMES){
            xy.doRender(graphics,mousex,mousey,pticks);
        }
        ClientHelpers.bindText(QMARK);
        for (PostRender xy : RENDER_QMARKS){
            xy.doRender(graphics,mousex,mousey,pticks);
        }
        for (PostRender xy : RENDER_BORDERS){
            xy.doRender(graphics,mousex,mousey,pticks);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        ClientHelpers.bindText(MAIN_SCREEN);
        RenderingTools.blitWithBlend(matrices,getRelX(),getRelY(),0,0,256,256,256,256,0,1f);

        for (PostRender xy :RENDER_QMARKS_TOOLTIPS){
            xy.doRender(graphics,mousex,mousey,pticks);
        }

        stagesPage.render(graphics,mousex,mousey,pticks);
        infoButton.render(graphics,mousex,mousey,pticks);

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
        return 100;
    }

    @Override
    protected int getMaxYUpScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxXLeftScrollValue() {
        return 0;
    }


}
@FunctionalInterface
interface PostRender{

    void doRender(GuiGraphics graphics, int mousex, int mousey, float partialTicks);

}

