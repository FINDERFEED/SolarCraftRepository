package com.finderfeed.solarforge.content.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.client.rendering.PreparedShapedRecipe;
import com.finderfeed.solarforge.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.local_library.other.ItemRator;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.GhostRecipe;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CraftingRecipeScreen extends Screen implements PlaceRecipe<Ingredient> {
    public final ResourceLocation BUTTONS = new ResourceLocation("solarforge","textures/misc/page_buttons.png");

    private static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/solar_lexicon_crafting_recipe_screen.png");


    private PhantomInventory phantomInv = new PhantomInventory(9);
    private List<Slot> phantomSlots = new ArrayList<>();
    private List<ItemRator> itemRators = new ArrayList<>();
    private int relX;
    private int relY;
    private final List<CraftingRecipe> recipes;
    private int currentPage = 0;
    private int maxPages;
    private int time = 0;

    public CraftingRecipeScreen(CraftingRecipe recipe) {
        super(new TextComponent(""));
        this.recipes = List.of(recipe);
        this.maxPages = 0;
    }


    public CraftingRecipeScreen(List<CraftingRecipe> recipe) {
        super(new TextComponent(""));
        this.recipes = recipe;
        this.maxPages = recipe.size()-1;
    }

    @Override
    protected void init() {
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2-12;
        this.relY = (height - 218*scale)/2/scale;
        int xoffs = 111;
        this.prepareSlots();
        this.setupGhostRecipe(recipes.get(0));
        if (maxPages != 0) {
            addRenderableWidget(new ImageButton(relX + 180 - 10, relY + 36, 16, 16, 0, 0, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage + 1 <= maxPages)) {
                    currentPage += 1;
                }
                this.setupGhostRecipe(recipes.get(currentPage));
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Next recipe"),mousex,mousey);
            },new TextComponent("")){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
            addRenderableWidget(new ImageButton(relX + 164 - 10, relY + 36, 16, 16, 0, 16, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage - 1 >= 0)) {
                    currentPage -= 1;
                }
                this.setupGhostRecipe(recipes.get(currentPage));
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Previous recipe"),mousex,mousey);
            },new TextComponent("")){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
        }
        addRenderableWidget(new ItemStackTabButton(relX+97+xoffs,relY+29,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackTabButton(relX+97+xoffs,relY+29 + 19,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f));
        super.init();
    }


    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        ClientHelpers.bindText(MAIN_SCREEN);
        blit(matrices, relX, relY, 0, 0, 256, 256, 256, 256);
        if (!itemRators.isEmpty()){

            List<ItemStack> uniqueItems = new ArrayList<>();
            int[] counts = new int[9];
            int iter = -1;

            for (ItemRator itemRator : itemRators){
                renderItemAndTooltip(itemRator.getCurrentStack(),itemRator.getX(),itemRator.getY(),mousex,mousey,matrices,false);
                if (iter == -1){
                    iter = 0;
                    continue;
                }
                int index;
                if ((index = uniqueItems.indexOf(itemRator.getCurrentStack())) == -1){
                    uniqueItems.add(itemRator.getCurrentStack());
                    counts[uniqueItems.size()-1] = 1;
                }else{
                    counts[index]++;
                }
            }

            ItemRator itemRator = itemRators.get(0);
            renderItemAndTooltip(itemRator.getCurrentStack(),itemRator.getX(),itemRator.getY(),mousex,mousey,matrices,false);
            for (ItemStack i : uniqueItems){
                drawString(matrices,font,new TextComponent(counts[uniqueItems.indexOf(i)]+" x: ").append(i.getItem().getName(i)),relX+13,relY+84+iter*9,SolarLexiconScreen.TEXT_COLOR);
                iter++;
            }
        }




        super.render(matrices, mousex, mousey, partialTicks);
    }

    private void renderItemAndTooltip(ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices, boolean last){
        if (!last) {
            minecraft.getItemRenderer().renderGuiItem(toRender, place1, place2);
        }else{
            ItemStack renderThis = recipes.get(currentPage).getResultItem();
            minecraft.getItemRenderer().renderGuiItem(renderThis, place1, place2);
            minecraft.getItemRenderer().renderGuiItemDecorations(font,renderThis,place1,place2);
        }


        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            renderTooltip(matrices,toRender,mousex,mousey);
            matrices.popPose();
        }
    }


    public void setupGhostRecipe(Recipe<?> recipe) {
        itemRators.clear();
        itemRators.add(new ItemRator(Ingredient.of(recipe.getResultItem()),relX + 81,relY + 34));
        this.placeRecipe(3,3, Integer.MAX_VALUE, recipe, recipe.getIngredients().iterator(), 0);
    }

    @Override
    public void addItemToSlot(Iterator<Ingredient> iter, int slotIdx, int notUsed, int notUsed1, int notUsed2) {
        Ingredient ingredient = iter.next();
        if (!ingredient.isEmpty()) {
            Slot slot = phantomSlots.get(slotIdx);
//            this.ghostRecipe.addIngredient(ingredient, slot.x, slot.y);
            this.itemRators.add(new ItemRator(ingredient,slot.x,slot.y));
        }

    }

    private void prepareSlots(){
        phantomSlots.clear();
        int x = relX + 20;
        int y = relY + 16;
        for (int i = 0; i < 3;i ++){
            for (int g = 0; g < 3;g ++){
                Slot slot = new Slot(phantomInv,i + g,x + g * 18,y + i * 18);
                phantomSlots.add(slot);
            }
        }
    }


    @Override
    public void tick() {
        super.tick();
        if (time++ % 30 == 0){
            itemRators.forEach(ItemRator::next);
        }
    }
}
