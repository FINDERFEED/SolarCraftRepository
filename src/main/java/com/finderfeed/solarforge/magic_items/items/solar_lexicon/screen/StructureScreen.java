package com.finderfeed.solarforge.magic_items.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.client.screens.PositionBlockStateTileEntity;
import com.finderfeed.solarforge.client.screens.ThreeDStructureViewScreen;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.lwjgl.openal.AL;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StructureScreen extends Screen {

    public final ResourceLocation STRUCTURE_GUI = new ResourceLocation("solarforge","textures/gui/structure_screen.png");
    public final ResourceLocation BUTTONS = new ResourceLocation("solarforge","textures/misc/page_buttons.png");
    public final ResourceLocation THREEDSCREENBTN = new ResourceLocation("solarforge","textures/misc/button.png");
    public int currentPage;
    private List<List<BlockAndRelxRely>> structureBlocks = new ArrayList<>();


    private final RenderingTools.OptimizedBlockstateItemRenderer optimizedRenderer = new RenderingTools.OptimizedBlockstateItemRenderer();

    public int structWidth;
    public int structHeightAndPageCount;
    public Multiblock structure;
    public  int relX;
    public  int relY;
    public StructureScreen(Multiblock structure) {
        super(new TextComponent(""));
        this.structure = structure;
    }


    @Override
    protected void init() {
        structureBlocks.clear();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2-15;
        this.relY = (height - 218*scale)/2/scale;
        currentPage = 1;
        structHeightAndPageCount = structure.getStruct().length;
        structWidth = structure.getStruct()[0].length / 2;
        addRenderableWidget(new ImageButton(relX+216,relY+16,16,16,0,0,0,BUTTONS,16,32,(button)->{
            if ((currentPage+1 <= structHeightAndPageCount) ){
                currentPage+=1;
            }
        }){
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
            }
        });
        addRenderableWidget(new ImageButton(relX+216,relY+32,16,16,0,16,0,BUTTONS,16,32,(button)->{
            if ((currentPage-1 > 0)){
                currentPage-=1;
            }
        }){
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(),1,1));
            }
        });
        addRenderableWidget(new ImageButton(relX+216,relY,16,16,0,0,0,THREEDSCREENBTN,16,16,(button)->{
            Minecraft.getInstance().setScreen(new ThreeDStructureViewScreen(structure));
        },(btn,poseStack,mx,my)->{
            renderTooltip(poseStack,new TextComponent("3D View"),mx,my);
        },new TextComponent("3D")) {
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(Sounds.BUTTON_PRESS2.get(), 1, 1));
            }
        });

        addRenderableWidget(new ItemStackButton(relX+186+10,relY+9,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,false));
        addRenderableWidget(new ItemStackButton(relX+174+10,relY+9,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,false));

        super.init();


        int a = 0;
        if (structWidth*2 > 16){
            a = structWidth*2-16;
        }
        for (int heights = 0;heights < structHeightAndPageCount;heights++) {
            ArrayList<BlockAndRelxRely> toAdd = new ArrayList<>();
                for (int i = -structWidth; i <= structWidth;i++){
                    for (int g = -structWidth; g <= structWidth;g++){

                            BlockState state = structure.getBlockByCharacter(structure.struct[heights][i + structWidth].charAt(g + structWidth));
                            if (!state.isAir()) {
                                toAdd.add(new BlockAndRelxRely(state, relX + 100 + g * (13 - a), relY + 100 + i * (14 - a)));
                            }
                        }

                }
            this.structureBlocks.add(toAdd);
        }

    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {


        matrices.pushPose();
        ClientHelpers.bindText(STRUCTURE_GUI);
        blit(matrices,relX,relY,0,0,256,256);

        String[] struct = structure.struct[currentPage-1];
//        int a = 0;
//        if (structWidth*2 > 16){
//            a = structWidth*2-16;
//        }
        drawCenteredString(matrices, minecraft.font,new TextComponent(currentPage+ "/" + structHeightAndPageCount),relX+108,relY+14,0xffffff);
        //drawCenteredString(matrices, minecraft.font,new TranslationTextComponent(structure.getName()),relX+20,relY+10,0xffffff);
        Helpers.drawBoundedText(matrices,relX+14,relY+10,7,new TranslatableComponent(structure.getName()).getString(),0xffffff);


        ItemRenderer ren = Minecraft.getInstance().getItemRenderer();
//        for (int i = -structWidth; i <= structWidth;i++){
//            for (int g = -structWidth; g <= structWidth;g++){
//
//                renderItemAndTooltip(structure.getBlockByCharacter(struct[i+structWidth].charAt(g+structWidth)),relX+100+g*(13-a),relY+100+i*(14-a),mousex,mousey,matrices);
//            }
//        }
        for (BlockAndRelxRely obj : structureBlocks.get(currentPage-1)){
            renderItemAndTooltip(obj.block,obj.posx,obj.posy,mousex,mousey,matrices);
        }

        matrices.popPose();

        super.render(matrices, mousex, mousey, partialTicks);
    }


    private void renderItemAndTooltip(BlockState toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices){
        ItemStack stack = toRender.getBlock().asItem().getDefaultInstance();
        if (toRender.getBlock() != BlocksRegistry.SOLAR_STONE_BRICKS.get()) {
            minecraft.getItemRenderer().renderGuiItem(stack, place1, place2);
        }else{
            optimizedRenderer.renderGuiItem(stack,place1,place2);
        }

        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !stack.getItem().equals(Items.AIR)){
            matrices.pushPose();
            List<Component> comp = stack.getTooltipLines(Minecraft.getInstance().player, TooltipFlag.Default.NORMAL);
            ArrayList<Component> list = new ArrayList<>(comp);
            if (!toRender.getProperties().isEmpty()){
                list.add(new TranslatableComponent("blockstate_solarforge.properties").withStyle(ChatFormatting.GOLD));
                for (Property<?> prop : toRender.getProperties()) {
                    list.add(new TextComponent(prop.getName() + ": " + toRender.getValue(prop)).withStyle(ChatFormatting.UNDERLINE));
                }
            }

            renderTooltip(matrices, list, stack.getTooltipImage(),mousex,mousey);
            matrices.popPose();
        }
    }
}
class BlockAndRelxRely{

    protected BlockState block;
    protected int posx;
    protected int posy;

    protected BlockAndRelxRely(BlockState block,int posX,int posY){
        this.block = block;
        this.posx = posX;
        this.posy = posY;
    }

}

