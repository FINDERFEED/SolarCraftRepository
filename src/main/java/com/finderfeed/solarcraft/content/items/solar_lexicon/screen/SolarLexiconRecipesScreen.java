package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.structure.Book;
import com.finderfeed.solarcraft.local_library.client.screens.IRenderable;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.misc_things.IScrollable;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.*;
import java.util.List;

public class SolarLexiconRecipesScreen extends Screen implements IScrollable {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation("solarcraft","textures/gui/solar_lexicon_recipes_page_new.png");
    public final ResourceLocation FRAME = new ResourceLocation("solarcraft","textures/misc/frame.png");
    public final ResourceLocation MAIN_SCREEN_SCROLLABLE = new ResourceLocation("solarcraft","textures/gui/solar_lexicon_main_page_scrollablep.png");

    private List<AncientFragment> FRAGMENTS = new ArrayList<>();
    private Book BOOK;

    private boolean showNoFragmentsMessage = true;

    public IItemHandler handler;
    public final ItemStackButton goBack = new ItemStackButton(0,10,12,12,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SolarcraftItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f);
    public final ItemStackButton nothing = new ItemStackButton(0,10,12,12,(button)->{}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f);
    public InfoButton infoButton;

    public List<Runnable> postRender = new ArrayList<>();

    private EditBox searchBox;


//    public Point structures;

    public int scrollX;
    public int scrollY;
    public int prevscrollX;
    public int prevscrollY;
    public int relX;
    public int relY;

    @Override
    public void performScroll(int keyCode) {

        int scroll = 4;
        if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_LEFT) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_A))
                && !(scrollX +scroll > 0)){
            scrollX+=scroll;
        } else if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_W)) && !(scrollY +scroll > 0)){
            scrollY+=scroll;
        }else if((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_S)) && !(scrollY -scroll < -700)){
            scrollY-=scroll;
        }else if ((keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT) || keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_D)) && !(scrollX -scroll < -700)){
            scrollX-=scroll;
        }

        if (this.prevscrollX != scrollX){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.remove(goBack);
            list.remove(nothing);
            list.remove(infoButton);
            for (AbstractWidget a : list) {
                if (prevscrollX < scrollX) {
                    a.x += scroll;
                } else {
                    a.x -= scroll;
                }

            }
            this.prevscrollX = scrollX;
        }
        if (this.prevscrollY != scrollY){
            List<AbstractWidget> list = ClientHelpers.getScreenButtons(this);
            list.remove(goBack);
            list.remove(nothing);
            list.remove(infoButton);
            for (AbstractWidget a : list) {
                if (prevscrollY < scrollY) {

                    a.y += scroll;
                } else {

                    a.y -= scroll;
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

    public SolarLexiconRecipesScreen() {
        super(Component.literal(""));
    }


    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2-30;
        this.relY = (height - 218*scale)/2/scale;
        infoButton = new InfoButton(relX  +206+35,relY + 64,13,13,(btn1, graphics, mx, my)->{
            graphics.renderTooltip(font,font.split(Component.translatable("solarcraft.recipes_screen_info"),200),mx,my);
        });


        FRAGMENTS.clear();
        handler = getLexiconInventory();
        collectFragments();


        scrollX = 0;
        scrollY = 0;
        prevscrollX = 0;
        prevscrollY = 0;
        BOOK = new Book(relX+25,relY+25);
        Book.initializeBook(BOOK,FRAGMENTS);
        BOOK.init();
        BOOK.getButtons().forEach(this::addRenderableWidget);

        this.searchBox = new EditBox(this.font,relX,relY,100,20,Component.literal("Fragment name")){
            @Override
            public boolean charTyped(char character, int smth) {
                if (super.charTyped(character,smth)){

                }
                return super.charTyped(character,smth);
            }
        };


        addRenderableWidget(goBack);
        addRenderableWidget(nothing);

        //nothing.x = relX +207+35;
        //nothing.y = relY + 184;
        //goBack.x = relX +207+35;
        //goBack.y = relY + 164;
        nothing.x = relX +207+35;
        nothing.y = relY + 184 - 137;
        goBack.x = relX +207+35;
        goBack.y = relY + 164 - 137;
    }


    private void collectFragments(){
        for (int i = 0;i < handler.getSlots();i++){
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.getItem() == SolarcraftItems.INFO_FRAGMENT.get()){
                if (stack.getTagElement(ProgressionHelper.TAG_ELEMENT) != null) {
                    showNoFragmentsMessage = false;
                    AncientFragment frag = AncientFragment.getFragmentByID(stack.getTagElement(ProgressionHelper.TAG_ELEMENT).getString(ProgressionHelper.FRAG_ID));
                    if (frag != null){
                        FRAGMENTS.add(frag);
                    }
                }
            }
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (this.BOOK != null){
            this.BOOK.tick();
        }
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }




    private boolean isButtonPressable(int x,int y){
        if (((x + 24 > relX+7) && (x  < relX+7+220)) && ((y + 24 > relY+7) && (y  < relY+7+193))){
            return true;
        }
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {

        PoseStack matrices = graphics.pose();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int)minecraft.getWindow().getGuiScale();
        GL11.glScissor(width/2-((113)*scale),height/2-(89*scale),(221*scale),189*scale);

        ClientHelpers.bindText(MAIN_SCREEN_SCROLLABLE);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);

        ClientHelpers.bindText(FRAME);
        if (BOOK != null){
            BOOK.render(graphics);
        }
        super.render(graphics,mousex,mousey,partialTicks);
        if (showNoFragmentsMessage){
            graphics.drawString(font,"No fragments present :(",relX+20+scrollX,relY+40+scrollY,0xffffff);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);


        ClientHelpers.bindText(MAIN_SCREEN);

        matrices.pushPose();
        matrices.translate(0,0,400);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
        matrices.popPose();

        goBack.render(graphics,mousex,mousey,partialTicks,300);
        nothing.render(graphics,mousex,mousey,partialTicks,300);
        infoButton.render(graphics,mousex,mousey,partialTicks);

        this.renderables.forEach((widget)->{

                if (widget instanceof AbstractWidget button) {
                    boolean a = isButtonPressable(button.x, button.y);
                    button.active = a;
                    button.visible = a;
                }

        });
        goBack.active = true;
        nothing.active = true;
        goBack.visible = true;
        nothing.visible = true;
        infoButton.visible = true;
        infoButton.active = true;

        for (Runnable r : postRender){
            r.run();
        }
        postRender.clear();

    }

    public static IItemHandler getLexiconInventory(){
        return Minecraft.getInstance().player.getMainHandItem().getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
    }

}
