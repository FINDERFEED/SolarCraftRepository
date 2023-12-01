package com.finderfeed.solarcraft.client.screens;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarCraftButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.ScrollableLexiconScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockVisualizer;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDImageButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconRecipesScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.StructureScreen;
import com.finderfeed.solarcraft.misc_things.IScrollable;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.finderfeed.solarcraft.content.items.solar_lexicon.screen.InformationScreen.getScreenFromFragment;


public class ThreeDStructureViewScreen extends ScrollableLexiconScreen {

//    private  Button b;
//    private  Button c;
    public final ResourceLocation THREEDSCREENBTN = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/button.png");
    public final ResourceLocation STRUCTURE_GUI = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/structure_screen.png");
    private double xDragPos=0;
    private double yDragPos=0;
    private float structScale = 1;
    private double dragLeftRight=0;
    private double dragUpDown=0;
    private MultiblockStructure struct;
    private BlockAndTintGetter getter = new Getter();
    private AncientFragment fragment;

    private List<PositionBlockStateTileEntity> POS_STATE_TILEENTITY = new ArrayList<>();


    public ThreeDStructureViewScreen(AncientFragment fragment,MultiblockStructure structure) {
        super();
        this.struct = structure;
        this.fragment = fragment;
    }


    @Override
    protected void init() {
        super.init();

//        b = new ItemStackTabButton(0,0,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
//                (buttons, graphics, b, c) -> {
//                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
//                });
//
//        c = new ItemStackTabButton(0,0,17,17,(button)->{
//            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();
//
//        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
//            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
//        });
//        int width = minecraft.getWindow().getWidth();
//        int height = minecraft.getWindow().getHeight();
//        int scale = (int) minecraft.getWindow().getGuiScale();
//        this.relX = (width/scale - 183)/2-15;
//        this.relY = (height - 218*scale)/2/scale;
        POS_STATE_TILEENTITY.clear();
        structScale = 10f/Math.max(struct.pattern.length,struct.pattern[0].length);
        this.POS_STATE_TILEENTITY = RenderingTools.StructureRenderer.prepareList(struct);
//        addRenderableWidget(new FDImageButton(relX+216,relY,16,16,0,0,0,THREEDSCREENBTN,16,16,(button)->{
//            Minecraft.getInstance().setScreen(new StructureScreen(fragment,struct));
//        },(btn,graphics,mx,my)->{
//            graphics.renderTooltip(font,Component.literal("2D View"),mx,my);
//        },Component.literal("2D")){
//            @Override
//            public void playDownSound(SoundManager p_93665_) {
//                p_93665_.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
//            }
//        });
        addRenderableWidget(new SolarCraftButton(relX + 5,relY + 198,60,16,Component.translatable("solarcraft.visualize"),(btn)->{
            MultiblockVisualizer.setMultiblock(this.struct);
            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();
        },(btn,graphics,mx,my)->{
            graphics.renderTooltip(font,font.split(Component.translatable("solarcraft.visualize_guide"),200),
                    mx,my);
        }));

//        addRenderableWidget(b);
//        addRenderableWidget(c);
//        b.x = relX+216;
//        b.y = relY+20;
//        c.x = relX+216;
//        c.y = relY+20+18;


        int h = 0;
        IItemHandler items = SolarLexiconRecipesScreen.getLexiconInventory();
        if (items != null) {
            List<AncientFragment> refs = new ArrayList<>();
            for (int i = 0; i < items.getSlots();i++){
                ItemStack item = items.getStackInSlot(i);
                AncientFragment iFrag = AncientFragmentHelper.getFragmentFromItem(item);
                if (fragment.getReferences().contains(iFrag)){
                    refs.add(iFrag);
                }
            }

            for (AncientFragment ref : refs) {
                ItemStackTabButton button1 = new ItemStackTabButton(relX + 217, relY + 25 + 18 + 3 + h * 18 + 55, 17, 17, b -> {
                    Minecraft.getInstance().setScreen(getScreenFromFragment(ref));
                }, ref.getIcon().getDefaultInstance(), 0.7f, (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, ref.getTranslation(), b, c);
                });
                h++;
                addRenderableWidget(button1);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double p_94686_, double p_94687_, double delta) {
        double d = Math.ceil(delta);
        if (structScale + d*0.05  >= 0) {
            this.structScale += d * 0.05;
        }

        return super.mouseScrolled(p_94686_, p_94687_, delta);
    }

    @Override
    public int getScreenWidth() {
        return 217;
    }

    @Override
    public int getScreenHeight() {
        return 217;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public boolean mouseDragged(double xPos, double yPos, int button, double dragLeftRight, double dragUpDown) {
        xDragPos = xPos;
        yDragPos = yPos;
        this.dragLeftRight+=dragLeftRight/2;
        this.dragUpDown-=dragUpDown/2;
        return super.mouseDragged(xPos, yPos, button, dragLeftRight, dragUpDown);
    }


    @Override
    public void renderBackground(GuiGraphics p_96557_) {
        super.renderBackground(p_96557_);
    }

    @Override
    public void render(GuiGraphics graphics, int p_96563_, int p_96564_, float partialTicks) {

        PoseStack matrices = graphics.pose();

        matrices.pushPose();
        ClientHelpers.bindText(STRUCTURE_GUI);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);
        super.render(graphics, p_96563_, p_96564_, partialTicks);
        Helpers.drawBoundedText( graphics,relX+14,relY+10,7,Component.translatable("solarcraft.structure."+struct.getId()).getString(),0xffffff);
        matrices.popPose();

        matrices.pushPose();
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.XN(),-45+(int)this.dragUpDown));
        matrices.mulPose(RenderingTools.rotationDegrees(RenderingTools.YP(),45+(int)this.dragLeftRight));
        matrices.scale(structScale,structScale,structScale);
        //this renders the structure

        RenderingTools.StructureRenderer.render(matrices,POS_STATE_TILEENTITY,partialTicks,getter,relX+112,relY+108);

        matrices.popPose();

        matrices.pushPose();

        matrices.popPose();


    }



//    @Override
//    public void performScroll(int keyCode) {
//        double drag = 3;
//        if (hasShiftDown()){
//            drag = 4.5;
//        }
//        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP)){
//            this.dragUpDown+=drag;
//        }
//        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN)){
//            this.dragUpDown-=drag;
//        }
//        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_LEFT)){
//            this.dragLeftRight-=drag;
//        }
//        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT)){
//            this.dragLeftRight+=drag;
//        }
//    }

    @Override
    public void onScrollLeft(int delta) {
        super.onScrollLeft(delta);
        this.dragLeftRight += delta;
    }

    @Override
    public void onScrollRight(int delta) {
        super.onScrollRight(delta);
        this.dragLeftRight += delta;
    }

    @Override
    public void onScrollDown(int delta) {
        super.onScrollDown(delta);
        this.dragUpDown += delta;
    }

    @Override
    public void onScrollUp(int delta) {
        super.onScrollUp(delta);
        this.dragUpDown += delta;
    }

    @Override
    public int getScrollValue() {
        return 3;
    }

    @Override
    public int getXRightScroll() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getYDownScroll() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getYUpScroll() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getXLeftScroll() {
        return Integer.MAX_VALUE;
    }

    @Override
    public List<AbstractWidget> getMovableWidgets() {
        return List.of();
    }


}



class Getter implements BlockAndTintGetter {

    @Override
    public float getShade(Direction p_45522_, boolean p_45523_) {
        return 50f;
    }

    @Override
    public LevelLightEngine getLightEngine() {
        return null;
    }

    @Override
    public int getBlockTint(BlockPos pos, ColorResolver resolver) {
        return  resolver.getColor(Minecraft.getInstance().level.getBiome(pos).value(),pos.getX(),pos.getZ());
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        return Minecraft.getInstance().level.getBlockEntity(pos);
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return Minecraft.getInstance().level.getBlockState(pos);
    }

    @Override
    public FluidState getFluidState(BlockPos p_45569_) {
        return null;
    }

    @Override
    public int getHeight() {
        return 256;
    }

    @Override
    public int getMinBuildHeight() {
        return 0;
    }

    @Override
    public int getRawBrightness(BlockPos p_45525_, int p_45526_) {
        return 15-p_45526_;
    }

    @Override
    public int getBrightness(LightLayer p_45518_, BlockPos p_45519_) {
        return 15;
    }
}
