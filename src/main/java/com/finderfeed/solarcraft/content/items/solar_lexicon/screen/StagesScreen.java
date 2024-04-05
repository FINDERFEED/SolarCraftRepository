package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.ProgressionStage;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class StagesScreen extends ScrollableLexiconScreen {
    public final Component STAGES_CMP = Component.translatable("solarcraft.stages");
    public final ResourceLocation FRAME = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/frame.png");
    public final ResourceLocation QMARK = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/question_mark.png");
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/stages_page.png");
    public final ResourceLocation BG = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_main_page_scrollablep.png");
    private ArrayList<PostRender> RENDER_QMARKS = new ArrayList<>();
    private ArrayList<PostRender> RENDER_QMARKS_TOOLTIPS = new ArrayList<>();
    private ArrayList<PostRender> RENDER_FRAMES = new ArrayList<>();
    private ArrayList<PostRender> RENDER_BORDERS = new ArrayList<>();

//    public ItemStackButton stagesPage;
    public InfoButton infoButton;
    private List<AbstractWidget> moveableWidgets = new ArrayList<>();


    public static final int DISTANCE_BETWEEN_STAGES_Y = 60;
    public static final int STAGES_OFFSET_FROM_EDGE = 25;

    public StagesScreen() {

    }
    @Override
    protected void init() {
        super.init();
        moveableWidgets.clear();
        infoButton = new InfoButton(relX +  206 + 37,relY + 43,13,13,(btn1, graphics, mx, my)->{
            graphics.renderTooltip(font,font.split(STAGES_CMP.copy(),200),mx,my);
        });
//        stagesPage = new ItemStackTabButton(relX+98,relY + 18,17,17,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SCItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f);

        RENDER_QMARKS.clear();
        RENDER_QMARKS_TOOLTIPS.clear();
        RENDER_FRAMES.clear();
        RENDER_BORDERS.clear();
        for (int i = 0; i < ProgressionStage.STAGES_IN_ORDER.length;i++){
            ProgressionStage stage = ProgressionStage.STAGES_IN_ORDER[i];
            int y = i * DISTANCE_BETWEEN_STAGES_Y + relY + STAGES_OFFSET_FROM_EDGE;
            for (int g = 0; g < stage.SELF_PROGRESSIONS.length;g++){
                int x = g * 40 + relX + STAGES_OFFSET_FROM_EDGE;
                Progression progression = stage.SELF_PROGRESSIONS[g];
                if (Helpers.canPlayerUnlock(progression, Minecraft.getInstance().player)){
                    Button b;
                    addRenderableWidget(b = new ItemStackButton(x+getCurrentScrollX(),y+getCurrentScrollY(),16,16,(btn)->{},progression.icon,1,
                            (button,graphics,mousex,mousey)->{
                                if (Helpers.hasPlayerCompletedProgression(progression,Minecraft.getInstance().player)) {
                                    graphics.renderComponentTooltip(font, List.of(progression.translation,Component.translatable("solarcraft.completed").withStyle(ChatFormatting.GREEN)), mousex, mousey);
                                }else{
                                    graphics.renderComponentTooltip(font, List.of(progression.translation,Component.translatable("solarcraft.not_completed").withStyle(ChatFormatting.RED)), mousex, mousey);
                                }
                            }));
                    this.moveableWidgets.add(b);
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
            RENDER_BORDERS.add((graphics, mousex, mousey, partialTicks) -> {
                RenderingTools.drawFancyBorder(graphics.pose(),relX + getCurrentScrollX() + 25 - 13,y + getCurrentScrollY() - 13,stage.SELF_PROGRESSIONS.length * 40 + 2,42,0);
            });
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
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float pticks) {

        this.renderEscapeText(graphics);
        PoseStack matrices = graphics.pose();
        ClientHelpers.bindText(BG);
        RenderingTools.blitWithBlend(matrices,relX+ 10,relY+ 10,0,0,220,188,512,512,0,1f);
        RenderingTools.scissor(relX + 10,relY + 10,220,187);
        List<AbstractWidget> btns = moveableWidgets;

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
        RenderSystem.disableScissor();

        ClientHelpers.bindText(MAIN_SCREEN);
        RenderingTools.blitWithBlend(matrices,relX,relY ,0,0,256,256,256,256,0,1f);

        for (PostRender xy :RENDER_QMARKS_TOOLTIPS){
            xy.doRender(graphics,mousex,mousey,pticks);
        }

//        stagesPage.render(graphics,mousex,mousey,pticks);
        infoButton.render(graphics,mousex,mousey,pticks);

    }

    @Override
    public int getScrollValue() {
        return 4;
    }

    @Override
    public int getXRightScroll() {
        return 100;
    }

    @Override
    public int getYDownScroll() {
        return (ProgressionStage.STAGES_IN_ORDER.length - 1) * DISTANCE_BETWEEN_STAGES_Y;
    }

    @Override
    public List<AbstractWidget> getMovableWidgets() {
        return moveableWidgets;
    }


}