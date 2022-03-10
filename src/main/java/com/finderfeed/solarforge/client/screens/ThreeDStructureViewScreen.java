package com.finderfeed.solarforge.client.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.magic.items.solar_lexicon.screen.ItemStackButton;
import com.finderfeed.solarforge.magic.items.solar_lexicon.screen.SolarLexiconRecipesScreen;
import com.finderfeed.solarforge.magic.items.solar_lexicon.screen.StructureScreen;
import com.finderfeed.solarforge.misc_things.IScrollable;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;



public class ThreeDStructureViewScreen extends Screen implements IScrollable {

    private final Button b = new ItemStackButton(0,0,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false);
    private final Button c = new ItemStackButton(0,0,12,12,(button)->{
        Minecraft mc = Minecraft.getInstance();
        SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
        lexicon.currentSavedScreen = this;
        minecraft.setScreen(null);
    }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,false);
    public final ResourceLocation THREEDSCREENBTN = new ResourceLocation("solarforge","textures/misc/button.png");
    public final ResourceLocation STRUCTURE_GUI = new ResourceLocation("solarforge","textures/gui/structure_screen.png");
    private double xDragPos=0;
    private double yDragPos=0;
    private float structScale = 1;
    private double dragLeftRight=0;
    private double dragUpDown=0;
    private Multiblock struct;
    private BlockAndTintGetter getter = new Getter();

    private List<PositionBlockStateTileEntity> POS_STATE_TILEENTITY = new ArrayList<>();


    int relX = 0;
    int relY = 0;
    public ThreeDStructureViewScreen(Multiblock structure) {
        super(new TextComponent(""));
        this.struct = structure;
    }


    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2-15;
        this.relY = (height - 218*scale)/2/scale;
        POS_STATE_TILEENTITY.clear();
        structScale = 10f/Math.max(struct.getStruct().length,struct.getStruct()[0].length);
        this.POS_STATE_TILEENTITY = RenderingTools.StructureRenderer.prepareList(struct);
        addRenderableWidget(new ImageButton(relX+216,relY,16,16,0,0,0,THREEDSCREENBTN,16,16,(button)->{
            Minecraft.getInstance().setScreen(new StructureScreen(struct));
        },(btn,poseStack,mx,my)->{
            renderTooltip(poseStack,new TextComponent("2D View"),mx,my);
        },new TextComponent("2D")){
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
            }
        });

        addRenderableWidget(b);
        addRenderableWidget(c);
        b.x = relX+186+10;
        b.y = relY+9;
        c.x = relX+174+10;
        c.y = relY + 9;
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
    public void renderBackground(PoseStack p_96557_) {
        super.renderBackground(p_96557_);
    }

    @Override
    public void render(PoseStack matrices, int p_96563_, int p_96564_, float partialTicks) {

        matrices.pushPose();
        ClientHelpers.bindText(STRUCTURE_GUI);
        blit(matrices,relX,relY,0,0,256,256);
        super.render(matrices, p_96563_, p_96564_, partialTicks);
        Helpers.drawBoundedText(matrices,relX+14,relY+10,7,new TranslatableComponent(struct.getName()).getString(),0xffffff);
        matrices.popPose();

        matrices.pushPose();
        matrices.mulPose(Vector3f.XN.rotationDegrees(-45+(int)this.dragUpDown));
        matrices.mulPose(Vector3f.YP.rotationDegrees(45+(int)this.dragLeftRight));
        matrices.scale(structScale,structScale,structScale);
        //this renders the structure

        RenderingTools.StructureRenderer.render(matrices,POS_STATE_TILEENTITY,partialTicks,getter,relX+105,relY+100);

        matrices.popPose();

        matrices.pushPose();

        matrices.popPose();


    }



    @Override
    public void performScroll(int keyCode) {
        double drag = 3;
        if (hasShiftDown()){
            drag = 4.5;
        }
        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_UP)){
            this.dragUpDown+=drag;
        }
        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_DOWN)){
            this.dragUpDown-=drag;
        }
        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_LEFT)){
            this.dragLeftRight-=drag;
        }
        if (keyCode == GLFW.glfwGetKeyScancode(GLFW.GLFW_KEY_RIGHT)){
            this.dragLeftRight+=drag;
        }
    }

    @Override
    public int getCurrentScrollX() {
        return 0;
    }

    @Override
    public int getCurrentScrollY() {
        return 0;
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
