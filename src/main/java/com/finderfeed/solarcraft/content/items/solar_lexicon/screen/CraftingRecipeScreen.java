package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.local_library.client.screens.buttons.FDImageButton;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.local_library.other.ItemRator;
import com.finderfeed.solarcraft.misc_things.PhantomInventory;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CraftingRecipeScreen extends LexiconScreen implements PlaceRecipe<Ingredient> {
    public final ResourceLocation BUTTONS = new ResourceLocation("solarcraft","textures/misc/page_buttons.png");

    private static final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_crafting_recipe_screen.png");


    private PhantomInventory phantomInv = new PhantomInventory(9);
    private List<Slot> phantomSlots = new ArrayList<>();
    private List<ItemRator> itemRators = new ArrayList<>();
    private final List<CraftingRecipe> recipes;

    private int time = 0;

    public CraftingRecipeScreen(CraftingRecipe recipe) {
        super();
        this.recipes = List.of(recipe);
    }


    public CraftingRecipeScreen(List<CraftingRecipe> recipe) {
        super();
        this.recipes = recipe;
    }

    @Override
    protected void init() {
        super.init();
        int xoffs = 111;
        this.prepareSlots();
        this.setupGhostRecipe(recipes.get(0));
        if (this.getPagesCount() != 0) {
            addRenderableWidget(new FDImageButton(relX + 180 - 10, relY + 36, 16, 16, 0, 0, 0, BUTTONS, 16, 32, (button) -> {
                this.nextPage();
                //                if ((currentPage + 1 <= maxPages)) {
//                    currentPage += 1;
//                }
//                this.setupGhostRecipe(recipes.get(currentPage));
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
                //                if ((currentPage - 1 >= 0)) {
//                    currentPage -= 1;
//                }
//                this.setupGhostRecipe(recipes.get(currentPage));
            },(button,graphics,mousex,mousey)->{
                graphics.renderTooltip(font,Component.literal("Previous recipe"),mousex,mousey);
            },Component.literal("")){
                @Override
                public void playDownSound(SoundManager manager) {
                    manager.play(SimpleSoundInstance.forUI(SolarcraftSounds.BUTTON_PRESS2.get(),1,1));
                }
            });
        }
        addRenderableWidget(new ItemStackTabButton(relX+97+xoffs,relY+29 - 3,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
                (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
                }));
        addRenderableWidget(new ItemStackTabButton(relX+97+xoffs,relY+29 - 3 + 19,17,17,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
        }));

    }


    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {

        PoseStack matrices = graphics.pose();

        ClientHelpers.bindText(MAIN_SCREEN);
        RenderingTools.blitWithBlend(matrices, relX, relY, 0, 0, 256, 256, 256, 256,0,1f);
        if (!itemRators.isEmpty()){

            List<ItemStack> uniqueItems = new ArrayList<>();
            int[] counts = new int[9];
            int iter = -1;

            for (ItemRator itemRator : itemRators){
//                renderItemAndTooltip(graphics,itemRator.getCurrentStack(),itemRator.getX(),itemRator.getY(),mousex,mousey,matrices,false);
                RenderingTools.renderItemAndTooltip(itemRator.getCurrentStack(),graphics,itemRator.getX(),itemRator.getY(),mousex,mousey,100);
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
//            renderItemAndTooltip(graphics,itemRator.getCurrentStack(),itemRator.getX(),itemRator.getY(),mousex,mousey,matrices,true);
            RenderingTools.renderItemAndTooltip(itemRator.getCurrentStack(),graphics,itemRator.getX(),itemRator.getY(),mousex,mousey,100);
            for (ItemStack i : uniqueItems){
                graphics.drawString(font,Component.literal(counts[uniqueItems.indexOf(i)]+" x: ").append(i.getItem().getName(i)),relX+13,relY+84+iter*9,SolarLexiconScreen.TEXT_COLOR);
                iter++;
            }
        }




        super.render(graphics, mousex, mousey, partialTicks);
    }

    private void renderItemAndTooltip(GuiGraphics graphics,ItemStack toRender, int place1, int place2, int mousex, int mousey, PoseStack matrices, boolean last){
        if (!last) {
            graphics.renderItem(toRender, place1, place2);
        }else{
            ItemStack renderThis = recipes.get(currentPage).getResultItem(Minecraft.getInstance().level.registryAccess());
            graphics.renderItem(renderThis, place1, place2);
            graphics.renderItemDecorations(font,renderThis,place1,place2);
        }


        if (((mousex >= place1) && (mousex <= place1+16)) && ((mousey >= place2) && (mousey <= place2+16)) && !toRender.getItem().equals(Items.AIR)){
            matrices.pushPose();
            graphics.renderTooltip(font,toRender,mousex,mousey);
            matrices.popPose();
        }
    }


    public void setupGhostRecipe(Recipe<?> recipe) {
        itemRators.clear();
        itemRators.add(new ItemRator(Ingredient.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())),relX + 81,relY + 34));
        this.placeRecipe(3,3, Integer.MAX_VALUE, recipe, recipe.getIngredients().iterator(), 0);
    }

    @Override
    public void addItemToSlot(Iterator<Ingredient> iter, int slotIdx, int notUsed, int notUsed1, int notUsed2) {
        Ingredient ingredient = iter.next();
        if (!ingredient.isEmpty()) {
            Slot slot = phantomSlots.get(slotIdx);
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
        return recipes.size() - 1;
    }

    @Override
    public void onPageChanged(int newPage) {
        this.setupGhostRecipe(recipes.get(newPage));
    }
}
