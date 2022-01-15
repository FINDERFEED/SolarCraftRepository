package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.screens.ScrollableScreen;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.ProgressionStage;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class StagesScreen extends ScrollableScreen {
    public final TranslatableComponent STAGES_CMP = new TranslatableComponent("solarcraft.stages");
    public final ResourceLocation FRAME = new ResourceLocation(SolarForge.MOD_ID,"textures/misc/frame.png");
    public final ResourceLocation QMARK = new ResourceLocation(SolarForge.MOD_ID,"textures/misc/question_mark.png");
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/stages_page.png");
    private ArrayList<PostRender> RENDER_QMARKS = new ArrayList<>();
    private ArrayList<PostRender> RENDER_QMARKS_TOOLTIPS = new ArrayList<>();
    private ArrayList<PostRender> RENDER_FRAMES = new ArrayList<>();

    public ItemStackButton stagesPage = new ItemStackButton(relX+100,relY + 20,12,12,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f,false);

    public StagesScreen() {

    }


    @Override
    protected void init() {
        super.init();
        setAsStaticWidget(stagesPage);
        RENDER_QMARKS.clear();
        RENDER_QMARKS_TOOLTIPS.clear();
        RENDER_FRAMES.clear();
        for (int i = 0; i < ProgressionStage.STAGES_IN_ORDER.length;i++){
            ProgressionStage stage = ProgressionStage.STAGES_IN_ORDER[i];
            int y = i * 30 + getRelY() + 15;
            for (int g = 0; g < stage.SELF_PROGRESSIONS.length;g++){
                int x = g * 20 + getRelX() + 15;
                Progression progression = stage.SELF_PROGRESSIONS[g];
                if (Helpers.canPlayerUnlock(progression, Minecraft.getInstance().player)){
                    addRenderableWidget(new ItemStackButton(x+getCurrentScrollX(),y+getCurrentScrollY(),16,16,(btn)->{},progression.icon,1,false,
                            (button,matrix,mousex,mousey)->{
                                if (Helpers.hasPlayerUnlocked(progression,Minecraft.getInstance().player)) {
                                    renderComponentTooltip(matrix, List.of(progression.translation,new TranslatableComponent("solarcraft.completed").withStyle(ChatFormatting.GREEN)), mousex, mousey);
                                }else{
                                    renderComponentTooltip(matrix, List.of(progression.translation,new TranslatableComponent("solarcraft.not_completed").withStyle(ChatFormatting.RED)), mousex, mousey);
                                }
                            }));
                }else{
                    RENDER_QMARKS.add(((matrices, mousex, mousey, partialTicks) -> {
                        blit(matrices,x + getCurrentScrollX(),y + getCurrentScrollY(),0,0,16,16,16,16);
                    }));
                    RENDER_QMARKS_TOOLTIPS.add(((matrices, mousex, mousey, partialTicks) -> {
                        if ((mousex  >= x + getCurrentScrollX() && mousex  <= x + getCurrentScrollX()+16)
                                && (mousey  >= y + getCurrentScrollY() && mousey <= y + 16 + getCurrentScrollY()) ) {
                            renderComponentTooltip(matrices, List.of(progression.translation,
                                    new TranslatableComponent("solarcraft.cannot_be_completed").withStyle(ChatFormatting.GRAY)), mousex , mousey );
                        }
                    }));
                }
                RENDER_FRAMES.add(((matrices, mousex, mousey, partialTicks) -> {
                    blit(matrices,x + getCurrentScrollX(),y + getCurrentScrollY(),0,0,16,16,16,16);
                }));
            }
        }
        addRenderableWidget(stagesPage);
        stagesPage.x = relX + 207 + 35;
        stagesPage.y = relY + 13;

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float pticks) {


        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();
        ClientHelpers.bindText(MAIN_SCREEN);
        blit(matrices,getRelX(),getRelY(),0,0,256,256);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(width/2-((30+83)*scale),height/2-(89*scale),((188+35)*scale),189*scale);
        List<AbstractWidget> btns = ClientHelpers.getScreenButtons(this);
        btns.removeAll(getStaticWidgets());
        for (AbstractWidget w : btns){
            w.render(matrices,mousex,mousey,pticks);
        }
        ClientHelpers.bindText(FRAME);
        for (PostRender xy : RENDER_FRAMES){
            xy.doRender(matrices,mousex,mousey,pticks);
        }
        ClientHelpers.bindText(QMARK);
        for (PostRender xy : RENDER_QMARKS){
            xy.doRender(matrices,mousex,mousey,pticks);
        }
        for (PostRender xy :RENDER_QMARKS_TOOLTIPS){
            xy.doRender(matrices,mousex,mousey,pticks);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        List<String> splittedString = RenderingTools.splitString(STAGES_CMP.getString(),20);
        for (int i = 0; i < splittedString.size();i++){
            int y = i * 9;
            drawString(matrices,font,splittedString.get(i),getRelX() + 120,getRelY() + y + 19,0xff0000);
        }


        stagesPage.render(matrices,mousex,mousey,pticks);
    }

    @Override
    protected int getScrollValue() {
        return 4;
    }

    @Override
    protected int getMaxYDownScrollValue() {
        return 200;
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


}
@FunctionalInterface
interface PostRender{

    void doRender(PoseStack matrices,int mousex,int mousey,float partialTicks);

}

