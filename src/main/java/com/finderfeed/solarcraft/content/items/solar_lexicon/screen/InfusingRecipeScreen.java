package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDImageButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.other.ItemRator;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;

import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InfusingRecipeScreen extends LexiconScreen {
    public final ResourceLocation BUTTONS = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/page_buttons.png");
    public final ResourceLocation MAIN_SCREEN_OPENED = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_infusing_recipe_with_catalysts_new.png");
    public final ResourceLocation MAIN_SCREEN_UNOPENED = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_infusing_recipe_without_catalysts_new.png");
    //60*6
    private int[][] runicEnergySymbolsRenderPositions = new int[12][2];
    public final List<InfusingRecipe> recipe;


    public List<ItemRator> itemRators;
    private boolean catalystsUnlocked = false;
    private int ticker = 0;

    public InfusingRecipeScreen(InfusingRecipe a) {
        super();
        this.recipe = List.of(a);
//        maxPages = 0;
    }
    public InfusingRecipeScreen(List<InfusingRecipe> a) {
        super();
        this.recipe = a;
//        maxPages = recipe.size()-1;
    }

    public InfusingRecipeScreen(List<InfusingRecipe> a,int page) {
        super();
        this.recipe = a;
        this.currentPage = page;
    }

    private void addStack(Ingredient ingr, List<ItemStack> stacks){
        if (!ingr.isEmpty()){
            stacks.add(ingr.getItems()[0]);
        }else{
            stacks.add(ItemStack.EMPTY);
        }
    }

    private void fillItemRators(){
        InfusingRecipe currentRecipe = recipe.get(currentPage);
        for (int i = 0; i < currentRecipe.oneRowPattern.length();i++){
            char c = currentRecipe.oneRowPattern.charAt(i);
            this.itemRators.add(new ItemRator(currentRecipe.INGR_MAP.get(c)));
        }
    }

    @Override
    protected void init() {
        super.init();
        fillArray();
        itemRators = new ArrayList<>();
        this.catalystsUnlocked = Helpers.hasPlayerCompletedProgression(Progression.CATALYSTS,Minecraft.getInstance().player);
        fillItemRators();
        if (this.getPagesCount() != 1) {
            addRenderableWidget(new FDImageButton(relX + 193 + 19, relY + 55 + 14  , 16, 16, 0, 0, 0, BUTTONS, 16, 32, (button) -> {
                this.nextPage();
            },(button,graphics,mousex,mousey)->{
                graphics.renderTooltip(font,Component.literal("Next recipe"),mousex,mousey);
            },Component.literal("")){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
            addRenderableWidget(new FDImageButton(relX + 193 + 19, relY + 16 + 55 + 14 , 16, 16, 0, 16, 0, BUTTONS, 16, 32, (button) -> {
                this.previousPage();
            },(button,graphics,mousex,mousey)->{
                graphics.renderTooltip(font ,Component.literal("Previous recipe"),mousex,mousey);
            },Component.literal("")){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
        }

        //13

        boolean d = false;
        for (int i = 0; i < this.getPagesCount();i++){
            if (recipe.get(i).requriedSolarEnergy != 0 || InfuserTileEntity.doRecipeRequiresRunicEnergy(recipe.get(i).RUNIC_ENERGY_COST)){
                d = true;
                break;
            }
        }
        if (d) {

            addRenderableWidget(new ItemStackTabButton(relX + 211, relY + 9 + 6, 17, 17, (button) -> {
                minecraft.setScreen(new InfusingRecipeEnergyScreen(recipe, currentPage));
            }, SCItems.SOLAR_WAND.get().getDefaultInstance(), 0.7f));
        }
        addRenderableWidget(new ItemStackTabButton(relX + 211,relY+28 + 6 - 1,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
                (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
                }));
        addRenderableWidget(new ItemStackTabButton(relX + 211,relY+28 + 6 - 1 + 18,17,17,(button)->{
            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();

        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
        }));

    }

    @Override
    public void tick() {
        super.tick();
        if (ticker++ % 40 == 0){
            itemRators.forEach(ItemRator::next);
        }
    }

    @Override
    public int getScreenWidth() {
        return 208;
    }

    @Override
    public int getScreenHeight() {
        return 208;
    }

    @Override
    public int getPagesCount() {
        return recipe.size();
    }

    @Override
    public void onPageChanged(int newPage) {
        super.onPageChanged(newPage);
        this.itemRators.clear();
        fillItemRators();
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        matrices.pushPose();
        if (catalystsUnlocked) {
            ClientHelpers.bindText(MAIN_SCREEN_OPENED);
        }else{
            ClientHelpers.bindText(MAIN_SCREEN_UNOPENED);
        }
        InfusingRecipe currentRecipe = recipe.get(currentPage);
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,208,256,256,0,1f);


        RenderingTools.renderItemAndTooltip(itemRators.get(0).getCurrentStack(),graphics,relX+50    -1,relY+51  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(1).getCurrentStack(),graphics,relX+96    -1,relY+44  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(2).getCurrentStack(),graphics,relX+142   -1,relY+51  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(3).getCurrentStack(),graphics,relX+70    -1,relY+71  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(4).getCurrentStack(),graphics,relX+122   -1,relY+71  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(5).getCurrentStack(),graphics,relX+43    -1,relY+97  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(6).getCurrentStack(),graphics,relX+96    -1,relY+97  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(7).getCurrentStack(),graphics,relX+149   -1,relY+97  -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(8).getCurrentStack(),graphics,relX+70    -1,relY+123 -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(9).getCurrentStack(),graphics,relX+122   -1,relY+123 -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(10).getCurrentStack(),graphics,relX+50   -1,relY+143 -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(11).getCurrentStack(),graphics,relX+96   -1,relY+150 -1,mousex,mousey,100);
        RenderingTools.renderItemAndTooltip(itemRators.get(12).getCurrentStack(),graphics,relX+142  -1,relY+143 -1,mousex,mousey,100);

        ItemStack res = currentRecipe.getResultItem(Minecraft.getInstance().level.registryAccess()).copy();
        int count = recipe.get(currentPage).count;
        res.setCount(count);
        RenderingTools.renderItemAndTooltip(res,graphics,relX+20,relY+21,mousex,mousey,100);


        graphics.drawCenteredString(minecraft.font,Component.literal(recipe.get(currentPage).infusingTime / 20 +" ").append(Component.translatable("solarcraft.seconds2")),relX+170,relY+25,
                SolarLexiconScreen.TEXT_COLOR);


        super.render(graphics,mousex,mousey,partialTicks);
        matrices.popPose();



        if (catalystsUnlocked) {
            matrices.pushPose();
            InfusingRecipe r = recipe.get(currentPage);
            if (r.getDeserializedCatalysts() != null) {
                int iterator = 0;
                for (int[] pos : runicEnergySymbolsRenderPositions) {
                    Block block = r.getDeserializedCatalysts()[iterator];
                    if (block != null && block.defaultBlockState().is(Tags.CATALYST)) {
                        matrices.pushPose();
                        RunicEnergy.Type type = RunicEnergy.BLOCK_TO_RUNE_ENERGY_TYPE.get(block);
                        int p1 = pos[0];
                        int p2 = pos[1];
                        bindTypeTexture(type);
                        RenderingTools.blitWithBlend(matrices, p1, p2, 0, 0, 16, 16, 16, 16,0,1f);
                        if (RenderingTools.isMouseInBorders(mousex,mousey,p1,p2,p1+ 16,p2 + 16)){
                            graphics.renderTooltip(font,Component.literal(type.id.toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.GOLD),mousex,mousey);

                        }
                        matrices.popPose();

                    }

                    iterator++;
                }
            }
            matrices.popPose();
        }

    }


//    public void renderItemAndTooltip(GuiGraphics graphics,ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices, boolean last){
//        if (!last) {
//            graphics.renderItem(toRender, place1, place2);
//        }else{
//            ItemStack renderThis = toRender.copy();
//            renderThis.setCount(recipe.get(currentPage).count);
//            graphics.renderItem(renderThis, place1, place2);
//            graphics.renderItemDecorations(font,renderThis,place1,place2);
//        }
//
//
//        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
//            matrices.pushPose();
//            graphics.renderTooltip(font,toRender,mousex,mousey);
//            matrices.popPose();
//        }
//    }

    private void bindTypeTexture(RunicEnergy.Type type){
        ClientHelpers.bindText(new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/tile_energy_pylon_" + type.id + ".png"));
    }

//    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, RunicEnergy.Type type){
//        matrices.pushPose();
//        double energyCostPerItem = recipe.get(currentPage).RUNIC_ENERGY_COST.get(type);
//        int xtexture =  ( Math.round( (float)energyCostPerItem/100000*60));
//        blit(matrices,relX+offsetx,relY+offsety,0,0,xtexture,6);
//        matrices.popPose();
//    }

    private void fillArray(){
        runicEnergySymbolsRenderPositions[0][0] = relX+67 -1;
        runicEnergySymbolsRenderPositions[0][1] = relY+22 -1;
        runicEnergySymbolsRenderPositions[1][0] = relX+96 -1;
        runicEnergySymbolsRenderPositions[1][1] = relY+22 -1;
        runicEnergySymbolsRenderPositions[2][0] = relX+125-1;
        runicEnergySymbolsRenderPositions[2][1] = relY+22 -1;
        runicEnergySymbolsRenderPositions[3][0] = relX+171-1;
        runicEnergySymbolsRenderPositions[3][1] = relY+68 -1;
        runicEnergySymbolsRenderPositions[4][0] = relX+171-1;
        runicEnergySymbolsRenderPositions[4][1] = relY+97 -1;
        runicEnergySymbolsRenderPositions[5][0] = relX+171-1;
        runicEnergySymbolsRenderPositions[5][1] = relY+126-1;
        runicEnergySymbolsRenderPositions[6][0] = relX+125-1;
        runicEnergySymbolsRenderPositions[6][1] = relY+172-1;
        runicEnergySymbolsRenderPositions[7][0] = relX+96 -1;
        runicEnergySymbolsRenderPositions[7][1] = relY+172-1;
        runicEnergySymbolsRenderPositions[8][0] = relX+67 -1;
        runicEnergySymbolsRenderPositions[8][1] = relY+172-1;
        runicEnergySymbolsRenderPositions[9][0] = relX+21 -1;
        runicEnergySymbolsRenderPositions[9][1] = relY+126-1;
        runicEnergySymbolsRenderPositions[10][0] = relX+21-1;
        runicEnergySymbolsRenderPositions[10][1] = relY+97-1;
        runicEnergySymbolsRenderPositions[11][0] = relX+21-1;
        runicEnergySymbolsRenderPositions[11][1] = relY+68-1;
    }
}
