package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.client.screens.ThreeDStructureViewScreen;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

import static com.finderfeed.solarcraft.content.items.solar_lexicon.screen.InformationScreen.getScreenFromFragment;


public class StructureScreen extends Screen {

    public final ResourceLocation STRUCTURE_GUI = new ResourceLocation("solarcraft","textures/gui/structure_screen.png");
    public static final ResourceLocation BUTTONS = new ResourceLocation("solarcraft","textures/misc/page_buttons.png");
    public final ResourceLocation THREEDSCREENBTN = new ResourceLocation("solarcraft","textures/misc/button.png");
    public int currentPage;
    private List<List<BlockAndRelxRely>> structureBlocks = new ArrayList<>();


    private final RenderingTools.OptimizedBlockstateItemRenderer optimizedRenderer = new RenderingTools.OptimizedBlockstateItemRenderer();

    public int structWidth;
    public int structHeightAndPageCount;
    public MultiblockStructure structure;
    public  int relX;
    public  int relY;
    public AncientFragment fragment;
    private float structureScale = 1f;

    public StructureScreen(AncientFragment fragment,MultiblockStructure structure) {
        super(Component.literal(""));
        this.structure = structure;
        this.fragment = fragment;
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
        structHeightAndPageCount = structure.pattern.length;
        structWidth = structure.pattern[0].length / 2;

        addRenderableWidget(new ImageButton(relX+216,relY+16,16,16,0,0,0,BUTTONS,16,32,(button)->{
            if ((currentPage+1 <= structHeightAndPageCount) ){
                currentPage+=1;
            }
        }){
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
            }
        });
        addRenderableWidget(new ImageButton(relX+216,relY+32,16,16,0,16,0,BUTTONS,16,32,(button)->{
            if ((currentPage-1 > 0)){
                currentPage-=1;
            }
        }){
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
            }
        });
        addRenderableWidget(new ImageButton(relX+216,relY,16,16,0,0,0,THREEDSCREENBTN,16,16,(button)->{
            Minecraft.getInstance().setScreen(new ThreeDStructureViewScreen(fragment,structure));
        },(btn,poseStack,mx,my)->{
            renderTooltip(poseStack,Component.literal("3D View"),mx,my);
        },Component.literal("3D")) {
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(), 1, 1));
            }
        });

        addRenderableWidget(new ItemStackTabButton(relX+217,relY+52,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackTabButton(relX+217,relY+52 + 18,17,17,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f));

        super.init();


//        int a = 0;
//        if (structWidth*2 > 16){
//            a = structWidth*2-16;
//        }
//        for (int heights = 0;heights < structHeightAndPageCount;heights++) {
//            ArrayList<BlockAndRelxRely> toAdd = new ArrayList<>();
//                for (int i = -structWidth; i <= structWidth;i++){
//                    for (int g = -structWidth; g <= structWidth;g++){
//
//                            BlockState state = structure.getBlockByCharacter(structure.pattern[heights][i + structWidth].charAt(g + structWidth));
//                            if (!state.isAir()) {
//                                toAdd.add(new BlockAndRelxRely(state, relX + 100 + g * (13 - a), relY + 100 + i * (14 - a)));
//                            }
//                        }
//
//                }
//            this.structureBlocks.add(toAdd);
//        }
        int sX = structure.pattern[0][0].length();
        int sZ = structure.pattern[0].length;
        int max = Math.max(sX,sZ);
        float structScale = 1f;
        if (max * 16 > 180) {
            structScale = 180f / (16 * max);
        }
        this.structureScale = structScale;

        int xShift = Math.round((float)sX / 2 * 16 * structScale);
        int zShift = Math.round((float)sZ / 2 * 16 * structScale);

        for (int h = 0; h < structure.pattern.length;h++) {
            ArrayList<BlockAndRelxRely> toAdd = new ArrayList<>();
            for (int i = 0; i < sX; i++) {
                for (int g = 0; g < sZ; g++) {
                    BlockState state = structure.getBlockByCharacter(structure.pattern[h][g].charAt(i));
                    if (!state.isAir()){
                        toAdd.add(new BlockAndRelxRely(state,
                                relX + 116 + Math.round(i * 16 * structScale) - xShift,
                                relY + 120 + Math.round(g * 16 * structScale) - zShift
                        ));
                    }
                }
            }
            structureBlocks.add(toAdd);
        }

        int h = 0;
        IItemHandler items = SolarLexiconRecipesScreen.getLexiconInventory();
        if (items != null) {
            List<AncientFragment> refs = new ArrayList<>();
            for (int i = 0; i < items.getSlots();i++){
                ItemStack item = items.getStackInSlot(i);
                AncientFragment iFrag = ProgressionHelper.getFragmentFromItem(item);
                if (fragment.getReferences().contains(iFrag)){
                    refs.add(iFrag);
                }
            }

            for (AncientFragment ref : refs) {
                ItemStackTabButton button1 = new ItemStackTabButton(relX + 217, relY + 25 + 18 + 3 + h * 18 + 60, 17, 17, b -> {
                    Minecraft.getInstance().setScreen(getScreenFromFragment(ref));
                }, ref.getIcon().getDefaultInstance(), 0.7f, (buttons, matrices, b, c) -> {
                    renderTooltip(matrices, ref.getTranslation(), b, c);
                });
                h++;
                addRenderableWidget(button1);
            }
        }

    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {


        matrices.pushPose();
        ClientHelpers.bindText(STRUCTURE_GUI);
        blit(matrices,relX,relY,0,0,256,256);


        drawCenteredString(matrices, minecraft.font,Component.literal(currentPage+ "/" + structHeightAndPageCount),relX+108,relY+14,0xffffff);
        Helpers.drawBoundedText(matrices,relX+14,relY+10,7,Component.translatable("solarcraft.structure." + structure.getId()).getString(),0xffffff);


        for (BlockAndRelxRely obj : structureBlocks.get(currentPage-1)){
            renderItemAndTooltip(obj.block,obj.posx,obj.posy,mousex,mousey,matrices);
        }

        matrices.popPose();

        super.render(matrices, mousex, mousey, partialTicks);
    }


    private void renderItemAndTooltip(BlockState toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices){
        ItemStack stack = toRender.getBlock().asItem().getDefaultInstance();
        if (toRender.getBlock() != SolarcraftBlocks.SOLAR_STONE_BRICKS.get()) {
//            minecraft.getItemRenderer().renderGuiItem(stack, place1, place2);
            RenderingTools.renderScaledGuiItemCentered(stack,place1,place2,structureScale,0);
        }else{
            optimizedRenderer.renderGuiItemScaled(stack,place1,place2,structureScale);
        }

        float detectSize = structureScale * 8;

        if (((mousex >= place1-detectSize) && (mousex <= place1+detectSize)) && ((mousey >= place2-detectSize) && (mousey <= place2+detectSize)) && !stack.getItem().equals(Items.AIR)){
            matrices.pushPose();
            List<Component> comp = stack.getTooltipLines(Minecraft.getInstance().player, TooltipFlag.Default.NORMAL);
            ArrayList<Component> list = new ArrayList<>(comp);
            if (!toRender.getProperties().isEmpty()){
                list.add(Component.translatable("blockstate_solarcraft.properties").withStyle(ChatFormatting.GOLD));
                for (Property<?> prop : toRender.getProperties()) {
                    list.add(Component.literal(prop.getName() + ": " + toRender.getValue(prop)).withStyle(ChatFormatting.UNDERLINE));
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

