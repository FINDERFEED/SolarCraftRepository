package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.client.screens.ThreeDStructureViewScreen;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDImageButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.items.IItemHandler;
import java.util.ArrayList;
import java.util.List;

import static com.finderfeed.solarcraft.content.items.solar_lexicon.screen.InformationScreen.getScreenFromFragment;


public class StructureScreen extends LexiconScreen {

    public final ResourceLocation STRUCTURE_GUI = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/structure_screen.png");
    public static final ResourceLocation BUTTONS = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/page_buttons.png");
    public final ResourceLocation THREEDSCREENBTN = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/button.png");
    public int currentPage;
    private List<List<BlockAndRelxRely>> structureBlocks = new ArrayList<>();


    private final RenderingTools.OptimizedBlockstateItemRenderer optimizedRenderer = new RenderingTools.OptimizedBlockstateItemRenderer();

    public int structWidth;
    public int structHeightAndPageCount;
    public MultiblockStructure structure;
    public AncientFragment fragment;
    private float structureScale = 1f;

    public StructureScreen(AncientFragment fragment,MultiblockStructure structure) {
        super();
        this.structure = structure;
        this.fragment = fragment;
    }


    @Override
    protected void init() {
        super.init();
        structureBlocks.clear();
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
        addRenderableWidget(new FDImageButton(relX+216,relY,16,16,0,0,0,THREEDSCREENBTN,16,16,(button)->{
            Minecraft.getInstance().setScreen(new ThreeDStructureViewScreen(fragment,structure));
        },(btn,graphics,mx,my)->{
            graphics.renderTooltip(font,Component.literal("3D View"),mx,my);
        },Component.literal("3D")) {
            @Override
            public void playDownSound(SoundManager p_93665_) {
                p_93665_.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(), 1, 1));
            }
        });

//        addRenderableWidget(new ItemStackTabButton(relX+217,relY+52,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
//                (buttons, graphics, b, c) -> {
//                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
//                }));
//        addRenderableWidget(new ItemStackTabButton(relX+217,relY+52 + 18,17,17,(button)->{
//            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();
//
//        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
//            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
//        }));



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
    public int getScreenWidth() {
        return 217;
    }

    @Override
    public int getScreenHeight() {
        return 217;
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {

        PoseStack matrices = graphics.pose();

        matrices.pushPose();
        ClientHelpers.bindText(STRUCTURE_GUI);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,256,256,256,0,1f);


        graphics.drawCenteredString(minecraft.font,Component.literal(currentPage+ "/" + structHeightAndPageCount),relX+108,relY+14,0xffffff);
        Helpers.drawBoundedText(graphics,relX+14,relY+10,7,Component.translatable("solarcraft.structure." + structure.getId()).getString(),0xffffff);


        matrices.translate(0,0,200);
        for (BlockAndRelxRely obj : structureBlocks.get(currentPage-1)){
            renderItemAndTooltip(graphics,obj.block,obj.posx,obj.posy,mousex,mousey,matrices);
        }

        matrices.popPose();

        super.render(graphics, mousex, mousey, partialTicks);
    }


    private void renderItemAndTooltip(GuiGraphics graphics,BlockState toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices){
        ItemStack stack = toRender.getBlock().asItem().getDefaultInstance();
        if (toRender.getBlock() != SCBlocks.SOLAR_STONE_BRICKS.get() && toRender.getBlock() != SCBlocks.MAGISTONE_BRICKS.get()) {
//            minecraft.getItemRenderer().renderGuiItem(stack, place1, place2);
            RenderingTools.renderScaledGuiItemCentered(graphics,stack,place1,place2,structureScale,0);
        }else{
            optimizedRenderer.renderGuiItemScaled(graphics,stack,place1,place2,structureScale);
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

            graphics.renderTooltip(font, list, stack.getTooltipImage(),mousex,mousey);
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

