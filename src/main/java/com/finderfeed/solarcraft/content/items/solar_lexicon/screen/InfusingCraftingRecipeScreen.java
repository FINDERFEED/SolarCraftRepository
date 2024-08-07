package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDImageButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.other.ItemRator;
import com.finderfeed.solarcraft.misc_things.SCLocations;
import com.finderfeed.solarcraft.registries.sounds.SCSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class InfusingCraftingRecipeScreen extends LexiconScreen {


    private static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_crafting_recipe_screen.png");


    private final List<InfusingCraftingRecipe> recipes;

    public ItemRator[][] itemRators;


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
            addRenderableWidget(new FDImageButton(relX + 180 - 10, relY + 36, 16, 16,
                    RenderingTools.singleWidgetSprite(SCLocations.NEXT_PAGE), (button) -> {
                this.nextPage();
            },(button,graphics,mousex,mousey)->{
                graphics.renderTooltip(font,Component.literal("Next recipe"),mousex,mousey);
            }){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SCSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
            addRenderableWidget(new FDImageButton(relX + 164 - 10, relY + 36, 16, 16,
                    RenderingTools.singleWidgetSprite(SCLocations.PREV_PAGE), (button) -> {
                this.previousPage();
            },(button,graphics,mousex,mousey)->{
                graphics.renderTooltip(font,Component.literal("Previous recipe"),mousex,mousey);
            }){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SCSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
        }
        this.itemRators = dissolvePattern(recipes.get(this.currentPage));
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
            ItemRator[][] r = this.itemRators;
            int iteratorHeight = 0;
            for (ItemRator[] arr : r){
                int iteratorLength = 0;
                for (ItemRator item : arr){
                    if (item != null) {
                        RenderingTools.renderItemAndTooltip(item.getCurrentStack(),graphics, relX + iteratorLength * 18 + 20, relY + iteratorHeight * 18 + 16, mousex, mousey,100);
                    }
                    iteratorLength++;
                }
                iteratorHeight++;
                iteratorLength = 0;
            }
            ItemStack item = currentRecipe.getOutput().copy();
            item.setCount(currentRecipe.getOutputCount());
            RenderingTools.renderItemAndTooltip(item,graphics,relX+81,relY+34,mousex,mousey,100);

            List<Item> uniqueItems = new ArrayList<>();
            Integer[] counts = new Integer[9];
            for (int i = 0 ; i < 3; i ++){
                for (int g = 0 ; g < 3; g ++){
                    ItemRator t = r[i][g];
                    if (t != null) {
                        Item it = t.getCurrentStack().getItem();
                        if (t.getCurrentStack().getItem() != Items.AIR && !uniqueItems.contains(it)) {
                            uniqueItems.add(t.getCurrentStack().getItem());
                            int index = uniqueItems.indexOf(it);
                            counts[index] = 1;
                        } else if (uniqueItems.contains(it)) {
                            int index = uniqueItems.indexOf(it);
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

    @Override
    public void onPageChanged(int newPage) {
        InfusingCraftingRecipe recipe = recipes.get(newPage);
        this.itemRators = dissolvePattern(recipe);
    }

    private void renderItemAndTooltip(GuiGraphics graphics, ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices, boolean last){
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


    private ItemRator[][] dissolvePattern(InfusingCraftingRecipe recipe){
        String[] pat = recipe.getPattern();
        int rows = pat.length;
        int cols = pat[0].length();
        ItemRator[][] r = new ItemRator[3][3];

        for (int i = 0; i < 3; i ++ ){
            for (int g = 0; g < 3 ; g++){
                if (i < rows && g < cols){
                    Ingredient ingredient = recipe.getDefinitions().get(pat[i].charAt(g));
                    ItemRator itemRator = new ItemRator(ingredient);
                    r[i][g] = itemRator;
                }else{
                    r[i][g] = null;
                }
            }
        }
        return r;
    }

    private int i;

    @Override
    public void tick() {
        super.tick();
        i++;
        if (itemRators != null){
            for (ItemRator[] itemRators : itemRators){
                for (ItemRator itemRator : itemRators){
                    if (i % 30 == 0 && itemRator != null) {
                        itemRator.next();
                    }
                }
            }
        }
    }
}
