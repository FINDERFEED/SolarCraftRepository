package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.tooltips.AnimatedTooltip;
import com.finderfeed.solarcraft.local_library.client.tooltips.BlackBackgroundTooltip;
import com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents.*;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.misc_things.IScrollable;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3d;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SolarLexiconScreen extends Screen implements IScrollable,PostRenderTooltips {
    private int ticker = 0;
    private int OFFSET_X = 50;
    private int OFFSET_Y = 50;

    public static int TEXT_COLOR = 0x5500dd;

    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_main_page_new.png");
    public final ResourceLocation FRAME = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/frame.png");
    public final ResourceLocation QMARK = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/question_mark.png");
    public final ResourceLocation MAIN_SCREEN_SCROLLABLE = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_main_page_scrollablep.png");
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
    public ItemStackButton justForge = new ItemStackButton(relX+100,relY+100,12,12,(button)->{}, SolarcraftItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f);
    public ItemStackButton retainFragmentsScreen = new ItemStackButton(relX+100,relY+100,12,12,(button)->{minecraft.setScreen(new RetainFragmentsScreen());}, Items.PAPER.getDefaultInstance(),0.7f);

    public InfoButton info;

    public HashMap<Integer,List<Progression>> map = new HashMap<>();

    private List<ItemStackButtonAnimatedTooltip> locked;
    private List<ItemStackButtonAnimatedTooltip> unlocked;

    private List<Runnable> postRender = new ArrayList<>();
    public SolarLexiconScreen() {
        super(Component.literal("screen_solar_lexicon"));
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
                && !(scrollY -4 < -1000)){
            scrollY-=4;
        }else if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_D))
                && !(scrollX -4 < -180)){
            scrollX-=4;
        }
        if (this.prevscrollX != scrollX){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.remove(toggleRecipesScreen);
            list.remove(justForge);
            list.remove(stagesPage);
            list.remove(info);
            list.remove(retainFragmentsScreen);
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
            list.remove(info);
            list.remove(retainFragmentsScreen);
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

        currAch = Component.literal("");
        int offsetX = 0;
        int offsetY = 0;

        for (Progression a : Progression.allProgressions){
            int tier = a.getAchievementTier();
            map.get(tier).add(a);
            offsetX = (map.get(tier).size()-1) * OFFSET_X;


            offsetY = (a.getAchievementTier() -1)* OFFSET_Y;
            boolean c = Helpers.canPlayerUnlock(a,minecraft.player);
            LocalPlayer player = Minecraft.getInstance().player;
            ItemStackButtonAnimatedTooltip button = new ItemStackButtonAnimatedTooltip(relX+21+offsetX,relY+21+offsetY,16,16,(btn)->{
                if (Helpers.hasPlayerCompletedProgression(a,player)){
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
            MutableComponent preText;
            MutableComponent afterText;
            boolean g = Helpers.canPlayerUnlock(a,player);
            if (Helpers.hasPlayerCompletedProgression(a,player)){
                preText = Component.literal(a.getPretext().getString());
                afterText = Component.literal(a.afterText.getString());
            }else if (g){
                preText = Component.literal(a.getPretext().getString());
                afterText = Component.literal("???");
            }else{
                afterText = Component.literal("???");
                preText = Component.literal("???");
            }
            Collection<Progression> parents = ProgressionTree.INSTANCE.getProgressionRequirements(a);
            AnimatedTooltip tooltip = new BlackBackgroundTooltip(-1000,-1000,relX + 230,relY + 200,7,5)
                    .setStartYOpeness(16).addComponents(new ComponentSequence(new ComponentSequence.ComponentSequenceBuilder()
                    .setAlignment(ContentAlignment.NO_ALIGNMENT)
                            .addComponent(new FDTextComponent(ContentAlignment.NO_ALIGNMENT,30,0).setText(a.getTranslation(),0xffffff).setInnerBorder(3))
                            .addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16,16,(graphics,x,y,pTicks,mouseX,mouseY,ticker,animationLength)->{
                        RenderSystem.disableDepthTest();
                        if (g) {
                            graphics.renderItem(a.getIcon(), x, y);

                        }else{
                            ClientHelpers.bindText(QMARK);
                            RenderingTools.blitWithBlend(graphics.pose(),x,y,0,0,16,16,16,16,0,1f);
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
                    .addComponent(new FDTextComponent(ContentAlignment.NO_ALIGNMENT,20,0).setText(Component.literal("Parent progressions:"),0xffffff).setInnerBorder(3))
                    .addComponent(new CustomRenderComponent(ContentAlignment.NO_ALIGNMENT,16,16,(graphics,x,y,pTicks,mouseX,mouseY,ticker,animationLength)->{
                        if (!parents.isEmpty()) {
                            RenderSystem.disableDepthTest();
                            int offset = 0;
                            for (Progression p : parents) {
                                if (Helpers.hasPlayerCompletedProgression(p,player)) {
                                    graphics.renderItem(p.getIcon(), x + offset, y-1);
                                }else{
                                    ClientHelpers.bindText(QMARK);
                                    RenderingTools.blitWithBlend(graphics.pose(),x,y,0,0,16,16,16,16,0,1f);
                                }
                                offset += 18;
                            }
                            RenderSystem.disableDepthTest();
                        }else{
                            MultiBufferSource.BufferSource source = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
                            font.drawInBatch("None",x,y + 3,0xffffff,true,graphics.pose().last().pose(),source, Font.DisplayMode.NORMAL,
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
                                                            //nice
        this.info = new InfoButton(relX + 206 + 35,relY + 69 + 15 + 20,13,13,(button,graphics,mx,my)->{
            graphics.renderTooltip(font,font.split(Component.translatable("solarcraft.solar_lexicon_screen_info"),200),mx,my);
        });

        addRenderableWidget(this.info);
        addRenderableWidget(toggleRecipesScreen);
        addRenderableWidget(justForge);
        addRenderableWidget(stagesPage);
        addRenderableWidget(retainFragmentsScreen);
        toggleRecipesScreen.x = relX +207+35;
        toggleRecipesScreen.y = relY + 184 - 137;
        justForge.x = relX +207+35;
        justForge.y = relY + 164 - 137;
        stagesPage.x = relX + 207 + 35;
        stagesPage.y = relY + 67;
        retainFragmentsScreen.x = relX + 207 + 35;
        retainFragmentsScreen.y = relY + 87;

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
        list.remove(retainFragmentsScreen);
        list.remove(info);

        for (AbstractWidget widget : list){
            widget.active = widget.x >= relX - 5 && widget.x <= relX + 230 && widget.y >= relY - 5 && widget.y <= relY + 195;
        }



    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();

        matrices.pushPose();


        ClientHelpers.bindText(MAIN_SCREEN_SCROLLABLE);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();

        GL11.glScissor(width/2-((30+83)*scale),height/2-(89*scale),((188+33)*scale),189*scale);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
        float time = RenderingTools.getTime(Minecraft.getInstance().level,partialTicks);
        double s = Math.sin(time/10);
        int offs = 30;
        ClientHelpers.bindText(FRAME);
        for (Progression a : tree.PROGRESSION_TREE.keySet()) {
            Point first = new Point(relX+scrollX+offs+map.get(a.getAchievementTier()).indexOf(a)*OFFSET_X,relY+scrollY+offs+(a.getAchievementTier()-1)*OFFSET_Y);
            for (Progression b : tree.getProgressionRequirements(a)){
                Point second = new Point(relX+scrollX+offs+map.get(b.getAchievementTier()).indexOf(b)*OFFSET_X,relY+scrollY+offs+(b.getAchievementTier()-1)*OFFSET_Y);
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
            Point first = new Point(relX+scrollX+offs-3+map.get(a.getAchievementTier()).indexOf(a)*OFFSET_X,relY+scrollY+offs-3+(a.getAchievementTier()-1)*OFFSET_Y);
            RenderingTools.blitWithBlend(matrices,first.x-12,first.y-12,0,0,28,28,28,28,0,1f);
        }




        for (ItemStackButtonAnimatedTooltip button : unlocked){
            button.render(graphics,mousex,mousey,partialTicks,-30);
        }

        ClientHelpers.bindText(QMARK);
        for (ItemStackButtonAnimatedTooltip button : locked){
            RenderingTools.blitWithBlend(matrices,button.x+1,button.y+1,0,0,14,14,14,14,0,1f);
            button.renderTooltip(graphics,mousex,mousey,partialTicks);
        }
        for (Runnable runnable : postRender){
            runnable.run();
        }
        postRender.clear();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        ClientHelpers.bindText(MAIN_SCREEN);

        matrices.pushPose();
        matrices.translate(0,0,100);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
        matrices.popPose();

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

        toggleRecipesScreen.render(graphics,mousex,mousey,partialTicks,101);
        justForge.render(graphics,mousex,mousey,partialTicks,101);
        stagesPage.render(graphics,mousex,mousey,partialTicks,101);
        retainFragmentsScreen.render(graphics,mousex,mousey,partialTicks,101);
        info.render(graphics,mousex,mousey,partialTicks);
        matrices.popPose();



    }



    private void drawLine(PoseStack stack,int x1,int y1,int x2,int y2,int red,int green,int blue){

//        GlStateManager._disableTexture();
        GlStateManager._depthMask(false);
        GlStateManager._disableCull();
        RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
        Tesselator var4 = RenderSystem.renderThreadTesselator();
        BufferBuilder var5 = var4.getBuilder();
        RenderSystem.lineWidth(4.5F);
        var5.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
        Vector3d vector3f = new Vector3d(x2-x1,y2-y1,0);
        Vector3d vector3f2 = new Vector3d(x1-x2,y1-y2,0);
        var5.vertex(x1, y1, 0.0D).color(red,green,blue, 255).normal((float)vector3f.x, (float)vector3f.y, 0.0F).endVertex();
        var5.vertex((double)x2, y2, 0.0D).color(red, green, blue, 255).normal((float)vector3f2.x, (float)vector3f2.y, 0.0F).endVertex();


        var4.end();

        GlStateManager._enableCull();
        GlStateManager._depthMask(true);
//        GlStateManager._enableTexture();
    }

    @Override
    public void addPostRenderTooltip(Runnable runnable) {
        this.postRender.add(runnable);
    }
}
