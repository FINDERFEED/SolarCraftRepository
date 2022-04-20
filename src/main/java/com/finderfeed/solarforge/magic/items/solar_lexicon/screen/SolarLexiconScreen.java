package com.finderfeed.solarforge.magic.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.client.tooltips.AnimatedTooltip;
import com.finderfeed.solarforge.local_library.client.tooltips.BlackBackgroundTooltip;
import com.finderfeed.solarforge.local_library.client.tooltips.animatable_omponents.*;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.misc_things.IScrollable;
import com.finderfeed.solarforge.magic.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;


import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SolarLexiconScreen extends Screen implements IScrollable,PostRenderTooltips {
    private int ticker = 0;
    private int OFFSET_X = 40;
    private int OFFSET_Y = 40;

    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarforge","textures/gui/solar_lexicon_main_page_new.png");
    public final ResourceLocation FRAME = new ResourceLocation("solarforge","textures/misc/frame.png");
    public final ResourceLocation QMARK = new ResourceLocation("solarforge","textures/misc/question_mark.png");
    public final ResourceLocation MAIN_SCREEN_SCROLLABLE = new ResourceLocation("solarforge","textures/gui/solar_lexicon_main_page_scrollablep.png");
    public String currentText = "";
    private String afterTxt = "";
    public int relX;
    public int relY;
    public final ProgressionTree tree = ProgressionTree.INSTANCE;
    public Component currAch;
    public Progression currentProgression = null;
    private List<Runnable> postLinesRender = new ArrayList<>();

    public int prevscrollX = 0;
    public int prevscrollY = 0;
    public int scrollX = 0;
    public int scrollY = 0;

    public ItemStackButton stagesPage = new ItemStackButton(relX+100,relY + 40,12,12,(button)->{minecraft.setScreen(new StagesScreen());},Items.BEACON.getDefaultInstance(),0.7f);
    public ItemStackButton toggleRecipesScreen = new ItemStackButton(relX+100,relY+100,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f);
    public ItemStackButton justForge = new ItemStackButton(relX+100,relY+100,12,12,(button)->{}, SolarForge.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f);

    public HashMap<Integer,List<Progression>> map = new HashMap<>();

    private List<ItemStackButtonAnimatedTooltip> locked;
    private List<ItemStackButtonAnimatedTooltip> unlocked;

    private List<Runnable> postRender = new ArrayList<>();
    public SolarLexiconScreen() {
        super(new TextComponent("screen_solar_lexicon"));
        this.width = 256;
        this.height = 256;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void performScroll(int keyCode) {

        if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_LEFT) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_A))
                && !(scrollX +4 > 0)){
            scrollX+=4;
        } else if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_W))
                && !(scrollY +4 > 0)){
            scrollY+=4;
        }else if((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_S))
                && !(scrollY -4 < -340)){
            scrollY-=4;
        }else if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_D))
                && !(scrollX -4 < -80)){
            scrollX-=4;
        }
        if (this.prevscrollX != scrollX){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.remove(toggleRecipesScreen);
            list.remove(justForge);
            list.remove(stagesPage);
            for (AbstractWidget a : list) {
                if (prevscrollX < scrollX) {
                    a.x += 4;
                } else {
                    a.x -= 4;
                }

            }
            this.prevscrollX = scrollX;
        }
        if (this.prevscrollY != scrollY){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.remove(toggleRecipesScreen);
            list.remove(justForge);
            list.remove(stagesPage);
            for (AbstractWidget a : list) {
                if (prevscrollY < scrollY) {

                    a.y += 4;
                } else {

                    a.y -= 4;
                }


            }
            this.prevscrollY = scrollY;
        }
    }

    @Override
    public int getCurrentScrollX() {
        return scrollX;
    }

    @Override
    public int getCurrentScrollY() {
        return scrollY;
    }




    private void initMap(int tiersCount){
        map.clear();
        for (int i = 1;i < tiersCount+1;i++){
            map.put(i,new ArrayList<>());
        }
    }


    @Override
    protected void init() {
        super.init();
        postRender.clear();
        locked = new ArrayList<>();
        unlocked = new ArrayList<>();
        this.prevscrollX = 0;
        this.prevscrollY = 0;
        this.scrollX = 0;
        this.scrollY = 0;
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 30;
        this.relY = (height - 218*scale)/2/scale;
        initMap(15);


        currentText = "Select Progression";

        currAch = new TextComponent("");
        int offsetX = 0;
        int offsetY = 0;

        for (Progression a : Progression.allProgressions){
            int tier = a.getAchievementTier();
            map.get(tier).add(a);
            offsetX = (map.get(tier).size()-1) * OFFSET_X;


            offsetY = (a.getAchievementTier() -1)* OFFSET_Y;
            boolean c = Helpers.canPlayerUnlock(a,minecraft.player);
            LocalPlayer player = Minecraft.getInstance().player;
            ItemStackButtonAnimatedTooltip button = new ItemStackButtonAnimatedTooltip(relX+12+offsetX,relY+12+offsetY,16,16,(btn)->{
                if (Helpers.hasPlayerUnlocked(a,player)){
                    currentText = a.getPretext().getString();
                    afterTxt = a.afterText.getString();
                }else if (Helpers.canPlayerUnlock(a,player)){
                    currentText = a.getPretext().getString();
                    afterTxt = "???";
                }
                else{
                    afterTxt = "???";
                    currentText = "???";
                }
                currAch = a.getTranslation();
                currentProgression = a;

            },a.getIcon(),1);
            TextComponent preText;
            TextComponent afterText;
            boolean g = Helpers.canPlayerUnlock(a,player);
            if (Helpers.hasPlayerUnlocked(a,player)){
                preText = new TextComponent(a.getPretext().getString());
                afterText = new TextComponent(a.afterText.getString());
            }else if (g){
                preText = new TextComponent(a.getPretext().getString());
                afterText = new TextComponent("???");
            }else{
                afterText = new TextComponent("???");
                preText = new TextComponent("???");
            }
            Collection<Progression> parents = ProgressionTree.INSTANCE.getAchievementRequirements(a);
            AnimatedTooltip tooltip = new BlackBackgroundTooltip(-1000,-1000,relX + 230,relY + 200,15,5)
                    .setStartYOpeness(16).addComponents(new ComponentSequence(new ComponentSequence.ComponentSequenceBuilder()
                    .setAlignment(ContentAlignment.NO_ALIGNMENT)
                            .addComponent(new FDTextComponent(ContentAlignment.NO_ALIGNMENT,30,0).setText(a.getTranslation(),0xffffff).setInnerBorder(3))
                            .addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16,16,(matrices,x,y,pTicks,mouseX,mouseY,ticker,animationLength)->{
                        RenderSystem.disableDepthTest();
                        if (g) {
                            Minecraft.getInstance().getItemRenderer().renderGuiItem(a.getIcon(), x, y);
                        }else{
                            ClientHelpers.bindText(QMARK);
                            blit(matrices,x,y,0,0,16,16,16,16);
                        }
                        RenderSystem.disableDepthTest();
                    }))
                    .nextLine()
                    .addComponent(new EmptySpaceComponent(0,10))
                    .nextLine()
                    .addComponent(new FDTextComponent(ContentAlignment.NO_ALIGNMENT,30,0).setText(preText,0xffffff).setInnerBorder(3))
                    .nextLine()
                    .addComponent(new EmptySpaceComponent(0,10))
                    .nextLine()
                    .addComponent(new FDTextComponent(ContentAlignment.NO_ALIGNMENT,30,0).setText(afterText,0xffffff).setInnerBorder(3))
                    .nextLine()
                    .addComponent(new EmptySpaceComponent(0,5))
                    .nextLine()
                    .addComponent(new FDTextComponent(ContentAlignment.NO_ALIGNMENT,20,0).setText(new TextComponent("Parent progressions:"),0xffffff).setInnerBorder(3))
                    .addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16,16,(matrices,x,y,pTicks,mouseX,mouseY,ticker,animationLength)->{
                        if (!parents.isEmpty()) {
                            RenderSystem.disableDepthTest();
                            int offset = 0;
                            for (Progression p : parents) {
                                if (Helpers.hasPlayerUnlocked(p,player)) {
                                    Minecraft.getInstance().getItemRenderer().renderGuiItem(p.getIcon(), x + offset, y-1);
                                }else{
                                    ClientHelpers.bindText(QMARK);
                                    blit(matrices,x,y,0,0,16,16,16,16);
                                }
                                offset += 18;
                            }
                            RenderSystem.disableDepthTest();
                        }else{
                            MultiBufferSource.BufferSource source = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                            font.drawInBatch("None",x,y + 3,0xffffff,true,matrices.last().pose(),source,true,
                                    0, 15728880,font.isBidirectional());
                            source.endBatch();
                        }
                    }).forceXSize(parents.isEmpty() ? font.width("None") : parents.size()*18))
                    .build()));
            button.setTooltip(tooltip);
            if (c){
                unlocked.add(button);
            }else{
                locked.add(button);
            }
            addRenderableWidget(button);
        }
        addRenderableWidget(toggleRecipesScreen);
        addRenderableWidget(justForge);
        addRenderableWidget(stagesPage);
        toggleRecipesScreen.x = relX +207+35;
        toggleRecipesScreen.y = relY + 184 - 137;
        justForge.x = relX +207+35;
        justForge.y = relY + 164 - 137;
        stagesPage.x = relX + 207 + 35;
        stagesPage.y = relY + 67;
    }



    @Override
    public void tick() {
        super.tick();
        for (ItemStackButtonAnimatedTooltip btn : unlocked){
            btn.tick();
        }
        for (ItemStackButtonAnimatedTooltip btn : locked){
            btn.tick();
        }
        if (ticker++ < 5) return;
        ticker = 0;
        List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
        list.remove(justForge);
        list.remove(stagesPage);
        list.remove(toggleRecipesScreen);

        for (AbstractWidget widget : list){
            widget.active = widget.x >= relX - 5 && widget.x <= relX + 230 && widget.y >= relY - 5 && widget.y <= relY + 195;
        }



    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        matrices.pushPose();


        ClientHelpers.bindText(MAIN_SCREEN_SCROLLABLE);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();

        GL11.glScissor(width/2-((30+83)*scale),height/2-(89*scale),((188+35)*scale),196*scale);
        blit(matrices,relX,relY,0,0,256,256);
        float time = RenderingTools.getTime(Minecraft.getInstance().level,partialTicks);
        double s = Math.sin(time/10);
        ClientHelpers.bindText(FRAME);
        for (Progression a : tree.PROGRESSION_TREE.keySet()) {
            Point first = new Point(relX+scrollX+21+map.get(a.getAchievementTier()).indexOf(a)*OFFSET_X,relY+scrollY+21+(a.getAchievementTier()-1)*OFFSET_Y);
            for (Progression b : tree.getAchievementRequirements(a)){
                Point second = new Point(relX+scrollX+21+map.get(b.getAchievementTier()).indexOf(b)*OFFSET_X,relY+scrollY+21+(b.getAchievementTier()-1)*OFFSET_Y);
                if (currentProgression != null && (currentProgression == b || currentProgression == a) ) {
                    postLinesRender.add(()->{
                        drawLine(matrices, first.x, first.y, second.x, second.y,255,255,255);
                    });
                }
                else {

                    drawLine(matrices, first.x, first.y, second.x, second.y,45 + (int)(s*10),45 + (int)(s*10),45 + (int)(s*10));
                }

            }

        }

        postLinesRender.forEach(Runnable::run);
        postLinesRender.clear();
        for (Progression a : tree.PROGRESSION_TREE.keySet()) {
            Point first = new Point(relX+scrollX+18+map.get(a.getAchievementTier()).indexOf(a)*OFFSET_X,relY+scrollY+18+(a.getAchievementTier()-1)*OFFSET_Y);
            blit(matrices,first.x-8,first.y-8,0,0,20,20,20,20);
        }



        for (ItemStackButtonAnimatedTooltip button : unlocked){
            button.render(matrices,mousex,mousey,partialTicks);
        }
        ClientHelpers.bindText(QMARK);
        for (ItemStackButtonAnimatedTooltip button : locked){
            blit(matrices,button.x,button.y,0,0,16,16,16,16);
            button.renderTooltip(matrices,mousex,mousey,partialTicks);
        }
        for (Runnable runnable : postRender){
            runnable.run();
        }
        postRender.clear();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        ClientHelpers.bindText(MAIN_SCREEN);

        RenderSystem.setShaderColor(0.75f,0.75f,0.75f,1f);
        blit(matrices,relX,relY,0,0,256,256);


//        drawString(matrices,minecraft.font,currAch,relX+12,relY+124,stringColor);
//        if (currentText != null && (currentText.length() != 0)) {
//            List<String> toRender1 = RenderingTools.splitString(currentText, 40);
//            int y = 0;
//            for (String s : toRender1) {
//                drawString(matrices, font, s, relX + 12, relY + 134 + y, stringColor);
//                y += 8;
//            }
//        }
//        if ((afterTxt != null) && (afterTxt.length() != 0)) {
//            List<String> toRender2 = RenderingTools.splitString(afterTxt, 40);
//            int yOffset = (toRender2.size()-1)*8;
//            int y = 0;
//            for (String s : toRender2) {
//                drawString(matrices, font, s, relX + 12, relY + 187 + y - yOffset, stringColor);
//                y += 8;
//            }
//        }

        toggleRecipesScreen.render(matrices,mousex,mousey,partialTicks);
        justForge.render(matrices,mousex,mousey,partialTicks);
        stagesPage.render(matrices,mousex,mousey,partialTicks);
        matrices.popPose();



    }



    private void drawLine(PoseStack stack,int x1,int y1,int x2,int y2,int red,int green,int blue){

        GlStateManager._disableTexture();
        GlStateManager._depthMask(false);
        GlStateManager._disableCull();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        Tesselator var4 = RenderSystem.renderThreadTesselator();
        BufferBuilder var5 = var4.getBuilder();
        RenderSystem.lineWidth(5F);
        var5.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        Vector3d vector3f = new Vector3d(x2-x1,y2-y1,0);
        Vector3d vector3f2 = new Vector3d(x1-x2,y1-y2,0);
        var5.vertex(x1, y1, 0.0D).color(red,green,blue, 255).normal((float)vector3f.x, (float)vector3f.y, 0.0F).endVertex();
        var5.vertex((double)x2, y2, 0.0D).color(red, green, blue, 255).normal((float)vector3f2.x, (float)vector3f2.y, 0.0F).endVertex();


        var4.end();

        GlStateManager._enableCull();
        GlStateManager._depthMask(true);
        GlStateManager._enableTexture();
    }

    @Override
    public void addPostRenderTooltip(Runnable runnable) {
        this.postRender.add(runnable);
    }
}
