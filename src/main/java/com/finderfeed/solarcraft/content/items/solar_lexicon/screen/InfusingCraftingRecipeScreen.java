package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDImageButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class InfusingCraftingRecipeScreen extends LexiconScreen {
    public final ResourceLocation BUTTONS = new ResourceLocation(SolarCraft.MOD_ID,"textures/misc/page_buttons.png");

    private static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_crafting_recipe_screen.png");


    private final List<InfusingCraftingRecipe> recipes;


    public InfusingCraftingRecipeScreen(InfusingCraftingRecipe recipe) {
        super();
        this.recipes = List.of(recipe);

    }


    public InfusingCraftingRecipeScreen(List<InfusingCraftingRecipe> recipe) {
        super();
        this.recipes = recipe;
    }

    @Override
    protected void init() {
        super.init();
        int xoffs = 111;
        if (this.getPagesCount() != 1) {
            addRenderableWidget(new FDImageButton(relX + 180 - 10, relY + 36, 16, 16, 0, 0, 0, BUTTONS, 16, 32, (button) -> {
                this.nextPage();
            },(button,graphics,mousex,mousey)->{
                graphics.renderTooltip(font,Component.literal("Next recipe"),mousex,mousey);
            },Component.literal("")){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
            addRenderableWidget(new FDImageButton(relX + 164 - 10, relY + 36, 16, 16, 0, 16, 0, BUTTONS, 16, 32, (button) -> {
                this.previousPage();
            },(button,graphics,mousex,mousey)->{
                graphics.renderTooltip(font,Component.literal("Previous recipe"),mousex,mousey);
            },Component.literal("")){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
        }
//        addRenderableWidget(new ItemStackTabButton(relX+97+xoffs,relY+26,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
//                (buttons, graphics, b, c) -> {
//                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
//                }));
//        addRenderableWidget(new ItemStackTabButton(relX+97+xoffs,relY+26 + 19,17,17,(button)->{
//            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();
//
//        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
//            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
//        }));

    }

    @Override
    public int getScreenWidth() {
        return 204;
    }

    @Override
    public int getScreenHeight() {
        return 208;
    }


    @Override
    public int getPagesCount() {
        return recipes.size();
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        ClientHelpers.bindText(MAIN_SCREEN);
        RenderingTools.blitWithBlend(matrices, relX, relY, 0, 0, 256, 256, 256, 256,0,1f);
        InfusingCraftingRecipe currentRecipe = recipes.get(currentPage);
        if (currentRecipe != null){
            Item[][] r = dissolvePattern(currentRecipe);
            int iteratorHeight = 0;
            for (Item[] arr : r){
                int iteratorLength = 0;
                for (Item item : arr){
                    if (item != null) {
                        RenderingTools.renderItemAndTooltip(item.getDefaultInstance(),graphics, relX + iteratorLength * 18 + 20, relY + iteratorHeight * 18 + 16, mousex, mousey,100);
                    }
                    iteratorLength++;
                }
                iteratorHeight++;
                iteratorLength = 0;
            }
            ItemStack item = currentRecipe.getOutput().copy();
            item.setCount(currentRecipe.getOutputCount());
            RenderingTools.renderItemAndTooltip(currentRecipe.getOutput(),graphics,relX+81,relY+34,mousex,mousey,100);

            List<Item> uniqueItems = new ArrayList<>();
            Integer[] counts = new Integer[9];
            for (int i = 0 ; i < 3; i ++){
                for (int g = 0 ; g < 3; g ++){
                    Item t = r[i][g];
                    if (t != null) {
                        if (t != Items.AIR && !uniqueItems.contains(t)) {
                            uniqueItems.add(t);
                            int index = uniqueItems.indexOf(t);
                            counts[index] = 1;
                        } else if (uniqueItems.contains(t)) {
                            int index = uniqueItems.indexOf(t);
                            counts[index]++;
                        }
                    }

                }
            }
            int iter = 0;

            for (Item i : uniqueItems){
                int in = uniqueItems.indexOf(i);
                graphics.drawString(font,Component.literal(counts[in]+" x: ").append(i.getName(i.getDefaultInstance())),relX+13,relY+84+iter*9,SolarLexiconScreen.TEXT_COLOR);
                iter++;
            }


        }




        super.render(graphics, mousex, mousey, partialTicks);
    }

    private void renderItemAndTooltip(GuiGraphics graphics,ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices, boolean last){
        if (!last) {
            graphics.renderItem(toRender, place1, place2);
        }else{
            ItemStack renderThis = toRender.copy();
            renderThis.setCount(recipes.get(currentPage).getOutputCount());
            graphics.renderItem(renderThis, place1, place2);
            graphics.renderItemDecorations(font,renderThis,place1,place2);
        }


        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            graphics.renderTooltip(font,toRender,mousex,mousey);
            matrices.popPose();
        }
    }


    private Item[][] dissolvePattern(InfusingCraftingRecipe recipe){
        String[] pat = recipe.getPattern();
        int rows = pat.length;
        int cols = pat[0].length();
        Item[][] r = new Item[3][3];

        for (int i = 0; i < 3; i ++ ){
            for (int g = 0; g < 3 ; g++){
                if (i < rows && g < cols){
                    r[i][g] = recipe.getDefinitions().get(pat[i].charAt(g));
                }else{
                    r[i][g] = null;
                }
            }
        }
        return r;
    }


}
