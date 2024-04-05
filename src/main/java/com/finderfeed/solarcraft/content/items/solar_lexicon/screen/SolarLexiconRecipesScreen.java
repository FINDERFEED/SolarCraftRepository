package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.InfoButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.structure.Book;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.SCAttachmentTypes;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.items.IItemHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import java.util.*;

public class SolarLexiconRecipesScreen extends ScrollableLexiconScreen {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_recipes_page_new.png");
    public final ResourceLocation FRAME = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/frame.png");
    public final ResourceLocation MAIN_SCREEN_SCROLLABLE = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_main_page_scrollablep.png");

    private List<AncientFragment> fragments = new ArrayList<>();
    private Book BOOK;

    private boolean showNoFragmentsMessage = true;

    public IItemHandler handler;
//    public ItemStackButton goBack;
//    public ItemStackButton nothing;
    public InfoButton infoButton;

    public List<Runnable> postRender = new ArrayList<>();

    public List<AbstractWidget> moveable = new ArrayList<>();



    public SolarLexiconRecipesScreen() {
        super();
    }


    @Override
    protected void init() {
        super.init();
        moveable.clear();
//        goBack = new ItemStackTabButton(0,10,16,16,(button)->{minecraft.setScreen(new SolarLexiconScreen());}, SCItems.SOLAR_FORGE_ITEM.get().getDefaultInstance(),0.7f,
//                ((button, graphics, mouseX, mouseY) -> {
//                    graphics.renderTooltip(font,Component.translatable("solarcraft.screens.buttons.progression_screen"),mouseX,mouseY);
//                }));
        //nothing = new ItemStackTabButton(0,10,16,16,(button)->{}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f);

        infoButton = new InfoButton(relX + this.getScreenWidth() + 10,relY + 18,14,14,(btn1, graphics, mx, my)->{
            graphics.renderTooltip(font,font.split(Component.translatable("solarcraft.recipes_screen_info"),200),mx,my);
        });


        fragments.clear();
        handler = getLexiconInventory();
        collectFragments();


        BOOK = new Book(relX+15,relY+15);
        Book.initializeBook(BOOK, fragments);
        BOOK.init();
        for (Button b : BOOK.getButtons()){
            this.addRenderableWidget(b);
            this.moveable.add(b);
        }

    }

    @Override
    public int getScrollValue() {
        return 4;
    }

    @Override
    public int getXRightScroll() {
        return 700;
    }

    @Override
    public int getYDownScroll() {
        return 700;
    }

    @Override
    public List<AbstractWidget> getMovableWidgets() {
        return moveable;
    }


    private void collectFragments(){
        for (int i = 0;i < handler.getSlots();i++){
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.getItem() == SCItems.INFO_FRAGMENT.get()){
                if (stack.getTagElement(AncientFragmentHelper.TAG_ELEMENT) != null) {
                    showNoFragmentsMessage = false;
                    AncientFragment frag = AncientFragment.getFragmentByID(stack.getTagElement(AncientFragmentHelper.TAG_ELEMENT).getString(AncientFragmentHelper.FRAG_ID));
                    if (frag != null){
                        fragments.add(frag);
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
    public int getScreenWidth() {
        return 256;
    }

    @Override
    public int getScreenHeight() {
        return 220;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }




    private boolean isButtonPressable(int x,int y){
        return RenderingTools.isMouseInBorders(x,y,relX - 20,relY - 20,relX + this.getScreenWidth() + 20,relY + this.getScreenHeight() + 20);
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {

        PoseStack matrices = graphics.pose();
        this.renderEscapeText(graphics);


        RenderingTools.scissor(relX ,relY,this.getScreenWidth(),this.getScreenHeight());

        RenderingTools.renderBasicLexiconPageBackground(matrices,relX,relY,this.getScreenWidth(),this.getScreenHeight());

        ClientHelpers.bindText(FRAME);
        if (BOOK != null){
            BOOK.render(graphics);
        }
        super.render(graphics,mousex,mousey,partialTicks);
        if (showNoFragmentsMessage){
            graphics.drawString(font,"No fragments present :(",relX+20+scrollX,relY+40+scrollY,0xffffff);
        }
        RenderSystem.disableScissor();



        matrices.pushPose();
        matrices.translate(0,0,400);
        RenderingTools.renderBasicLexiconPageOutline(matrices,relX,relY,this.getScreenWidth(),this.getScreenHeight());
        matrices.popPose();


        infoButton.render(graphics,mousex,mousey,partialTicks);

        this.renderables.forEach((widget)->{

                if (widget instanceof AbstractWidget button) {
                    boolean a = isButtonPressable(button.x, button.y);
                    button.active = a;
                    button.visible = a;
                }

        });

        infoButton.visible = true;
        infoButton.active = true;

        for (Runnable r : postRender){
            matrices.pushPose();
            matrices.translate(0,0,300);
            r.run();
            matrices.popPose();
        }
        postRender.clear();

    }

    public static IItemHandler getLexiconInventory(){
        return Minecraft.getInstance().player.getMainHandItem().getData(SCAttachmentTypes.LEXICON_INVENTORY);
    }

}
