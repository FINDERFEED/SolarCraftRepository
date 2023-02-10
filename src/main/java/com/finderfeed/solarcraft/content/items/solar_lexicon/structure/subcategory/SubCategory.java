package com.finderfeed.solarcraft.content.items.solar_lexicon.structure.subcategory;

import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.local_library.custom_registries.RegistryDelegate;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.*;
import com.finderfeed.solarcraft.content.items.solar_lexicon.structure.category.Category;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.IScrollable;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarcraft.registries.SolarCraftClientRegistries;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

public class SubCategory {

    private Category category;
    public static final int FONT_HEIGHT = 8;
    public static final int BUTTONS_SIZE = 25;

    private List<AncientFragment> fragments = new ArrayList<>();
    private List<Button> buttonsToAdd = new ArrayList<>();

    private final SubCategoryBase base;


    private Integer sizeX;
    private Integer sizeY;

    public SubCategory(SubCategoryBase base) {
        this.base = base;
    }

    public SubCategoryBase getBase() {
        return base;
    }


    public void initAtPos(int x, int y) {
        for (int i = 0; i < fragments.size(); i++) {
            int buttonPosX = x + (i % 6) * BUTTONS_SIZE;
            int buttonPosY = y + (int)Math.floor((float) i / 6) * BUTTONS_SIZE;
            AncientFragment frag = fragments.get(i);
            AncientFragment.Type type = frag.getType();
//            if (type == AncientFragment.Type.ITEM){
//                if (frag.getRecipeType() == SolarcraftRecipeTypes.INFUSING.get()){
//                    buttonsToAdd.add(constructInfusingRecipeButton(frag, getInfusingRecipeFromFragment(frag),buttonPosX,buttonPosY));
//                }else if (frag.getRecipeType() == SolarcraftRecipeTypes.SMELTING.get()){
//                    buttonsToAdd.add(constructSmeltingRecipeButton(getSmeltingRecipeFromFragment(frag),buttonPosX,buttonPosY));
//                }else if (frag.getRecipeType() == SolarcraftRecipeTypes.INFUSING_CRAFTING.get()){
//                    buttonsToAdd.add(constructInfusingCraftingRecipeButton(frag,getInfusingCraftingRecipeFromFragment(frag),buttonPosX,buttonPosY));
//                }else if (frag.getRecipeType() == RecipeType.CRAFTING){
//                    buttonsToAdd.add(constructCraftingRecipeButton(frag,List.of(getCraftingRecipeFromFragment(frag)),buttonPosX,buttonPosY));
//
//                }
//            }else
            if (type == AncientFragment.Type.INFORMATION){
                buttonsToAdd.add(constructInformationButton(frag.getIcon().getDefaultInstance(),buttonPosX,buttonPosY,frag));
            }else if (type == AncientFragment.Type.ITEMS){
                if (frag.getRecipeType() == SolarcraftRecipeTypes.INFUSING.get()) {
                    buttonsToAdd.add(constructInfusingRecipeButton(frag, getInfusingRecipesFromFragment(frag), buttonPosX, buttonPosY));
                }else if (frag.getRecipeType() == SolarcraftRecipeTypes.INFUSING_CRAFTING.get()){
                    buttonsToAdd.add(constructInfusingCraftingRecipeButton(frag, getInfusingCraftingRecipesFromFragment(frag), buttonPosX, buttonPosY));
                }else if (frag.getRecipeType() == RecipeType.CRAFTING){
                    buttonsToAdd.add(constructCraftingRecipeButton(frag, getCraftingRecipesFromFragment(frag), buttonPosX, buttonPosY));
                }else if (frag.getRecipeType() == SolarcraftRecipeTypes.SMELTING.get()){
                    buttonsToAdd.add(constructSmeltingRecipeButton(getSmeltingRecipeFromFragment(frag),buttonPosX,buttonPosY));
                }
            }else if (type == AncientFragment.Type.STRUCTURE){
                buttonsToAdd.add(constructStructureButton(frag.getStructure(),buttonPosX,buttonPosY,frag));
            }
//            else if (type == AncientFragment.Type.UPGRADE){
//                buttonsToAdd.add(constructInfusingRecipeButton(frag,getInfusingRecipeFromFragment(frag),buttonPosX,buttonPosY));
//            }
            else if (type == AncientFragment.Type.CUSTOM){
                buttonsToAdd.add(constructCustomButton(buttonPosX,buttonPosY,frag));
            }
        }
    }


    private InfusingRecipe getInfusingRecipeFromFragment(AncientFragment fragment){
        Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(fragment.getItem().getRecipeLocation());
        if (recipe.isEmpty()) throw new IllegalStateException("Incorrect recipe in fragment: " + fragment.getId().toUpperCase(Locale.ROOT) +", unable to find recipe: " + fragment.getItem().getRecipeLocation());
        return (InfusingRecipe) recipe.get();
    }
    private List<InfusingRecipe> getInfusingRecipesFromFragment(AncientFragment fragment){
        List<InfusingRecipe> recipes = new ArrayList<>();
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        for (AncientFragment.ItemWithRecipe item : fragment.getStacks()){
            Optional<? extends Recipe<?>> recipe = manager.byKey(item.getRecipeLocation());
            if (recipe.isEmpty()) throw new IllegalStateException("Incorrect recipe in fragment: " + fragment.getId().toUpperCase(Locale.ROOT) +", unable to find recipe: " + item.getRecipeLocation());
            recipes.add((InfusingRecipe) recipe.get());
        }
        return recipes;
    }

    private InfusingCraftingRecipe getInfusingCraftingRecipeFromFragment(AncientFragment fragment){
        Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(fragment.getItem().getRecipeLocation());
        if (recipe.isEmpty()) throw new IllegalStateException("Incorrect recipe in fragment: " + fragment.getId().toUpperCase(Locale.ROOT) +", unable to find recipe: " + fragment.getItem().getRecipeLocation());
        return (InfusingCraftingRecipe) recipe.get();
    }
    private List<InfusingCraftingRecipe> getInfusingCraftingRecipesFromFragment(AncientFragment fragment){
        List<InfusingCraftingRecipe> recipes = new ArrayList<>();
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        for (AncientFragment.ItemWithRecipe item : fragment.getStacks()){
            Optional<? extends Recipe<?>> recipe = manager.byKey(item.getRecipeLocation());
            if (recipe.isEmpty()) throw new IllegalStateException("Incorrect recipe in fragment: " + fragment.getId().toUpperCase(Locale.ROOT) +", unable to find recipe: " + item.getRecipeLocation());
            recipes.add((InfusingCraftingRecipe) recipe.get());
        }
        return recipes;
    }

    private CraftingRecipe getCraftingRecipeFromFragment(AncientFragment fragment){
        Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(fragment.getItem().getRecipeLocation());
        if (recipe.isEmpty()) throw new IllegalStateException("Incorrect recipe in fragment: " + fragment.getId().toUpperCase(Locale.ROOT) +", unable to find recipe: " + fragment.getItem().getRecipeLocation());
        if (!(recipe.get() instanceof CraftingRecipe)) throw new IllegalStateException("Recipe is not shaped/shapeless: " + fragment.getId().toUpperCase(Locale.ROOT) + ", recipe: " + fragment.getItem().getRecipeLocation());
        return (CraftingRecipe) recipe.get();
    }
    private List<CraftingRecipe> getCraftingRecipesFromFragment(AncientFragment fragment){
        List<CraftingRecipe> recipes = new ArrayList<>();
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        for (AncientFragment.ItemWithRecipe item : fragment.getStacks()){
            Optional<? extends Recipe<?>> recipe = manager.byKey(item.getRecipeLocation());
            if (recipe.isEmpty()) throw new IllegalStateException("Incorrect recipe in fragment: " + fragment.getId().toUpperCase(Locale.ROOT) +", unable to find recipe: " + item.getRecipeLocation());
            if (!(recipe.get() instanceof CraftingRecipe)) throw new IllegalStateException("Recipe is not shaped/shapeless: " + fragment.getId().toUpperCase(Locale.ROOT) + ", recipe: " + fragment.getItem().getRecipeLocation());

            recipes.add((CraftingRecipe) recipe.get());
        }
        return recipes;
    }

    private SolarSmeltingRecipe getSmeltingRecipeFromFragment(AncientFragment fragment){
        Optional<? extends Recipe<?>> recipe = Minecraft.getInstance().level.getRecipeManager().byKey(fragment.getStacks().get(0).getRecipeLocation());
        if (recipe.isEmpty()) throw new IllegalStateException("Incorrect recipe in fragment: " + fragment.getId().toUpperCase(Locale.ROOT) +", unable to find recipe: " + fragment.getStacks().get(0).getRecipeLocation());
        return (SolarSmeltingRecipe) recipe.get();
    }


    public void renderAtPos(PoseStack matrices,int x, int y) {
        if (this.category != null) {
            int r = this.getCategory().getLinesRGB()[0];
            int g = this.getCategory().getLinesRGB()[1];
            int b = this.getCategory().getLinesRGB()[2];
            drawRectangle(matrices, getSize()[0], getSize()[1], new Point(x, y), r, g, b);
        }else{
            drawRectangle(matrices, getSize()[0], getSize()[1], new Point(x, y), 255, 255, 255);
        }
        int scrollX = 0;
        int scrollY = 0;
        if (Minecraft.getInstance().screen instanceof IScrollable scrollable) {
            scrollX = scrollable.getCurrentScrollX();
            scrollY = scrollable.getCurrentScrollY();
        }
        Gui.drawString(matrices,Minecraft.getInstance().font,base.getTranslation(),x+scrollX,y-FONT_HEIGHT+scrollY,0xffffff);
    }

    public void putAncientFragment(AncientFragment frag) {
        fragments.add(frag);
    }


    public int[] getSize() {
        if (sizeX == null) {
            int x;
            int y;

            if (fragments.size() >= 6) {
                x = 6 * BUTTONS_SIZE;
            } else {
                x = fragments.size() * BUTTONS_SIZE;
            }

            if (fragments.size() >= 6) {
                y = (int)Math.ceil((float)fragments.size() / 6) * BUTTONS_SIZE;
            } else {
                y = BUTTONS_SIZE;
            }
            this.sizeX = x;
            this.sizeY = y;
            return new int[]{x, y};
        } else {
            return new int[]{sizeX, sizeY};
        }
    }

    public List<Button> getButtonsToAdd() {
        return buttonsToAdd;
    }

    public ItemStackButton constructInfusingRecipeButton(AncientFragment fragment, List<InfusingRecipe> recipe, int x , int y){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new InformationScreen(fragment,new InfusingRecipeScreen(recipe)));
        },fragment.getIcon().getDefaultInstance(),1.5f,(button,matrices,mx,my)->{
            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }

    public ItemStackButton constructCraftingRecipeButton(AncientFragment fragment, List<CraftingRecipe> recipe, int x , int y){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new InformationScreen(fragment,new CraftingRecipeScreen(recipe)));
        },fragment.getIcon().getDefaultInstance(),1.5f,(button,matrices,mx,my)->{
            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }

    public ItemStackButton constructInfusingRecipeButton(AncientFragment fragment,InfusingRecipe recipe,int x , int y){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new InformationScreen(fragment,new InfusingRecipeScreen(recipe)));
        },fragment.getIcon().getDefaultInstance(),1.5f,(button,matrices,mx,my)->{
//            if (button.isHovered()){
//                this.onHovered();
//            }
            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }

    public ItemStackButton constructInfusingCraftingRecipeButton(AncientFragment fragment, List<InfusingCraftingRecipe> recipe, int x , int y){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new InformationScreen(fragment,new InfusingCraftingRecipeScreen(recipe)));
        },fragment.getIcon().getDefaultInstance(),1.5f,(button,matrices,mx,my)->{
//            if (button.isHovered()){
//                this.onHovered();
//            }
            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }


    public ItemStackButton constructInfusingCraftingRecipeButton(AncientFragment fragment, InfusingCraftingRecipe recipe, int x , int y){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new InformationScreen(fragment,new InfusingCraftingRecipeScreen(recipe)));
        },fragment.getIcon().getDefaultInstance(),1.5f,(button,matrices,mx,my)->{
//            if (button.isHovered()){
//                this.onHovered();
//            }
            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }


    public ItemStackButton constructSmeltingRecipeButton(SolarSmeltingRecipe recipe, int x , int y){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new SmeltingRecipeScreen(recipe));
        },recipe.output,1.5f,(button,matrices,mx,my)->{
//            if (button.isHovered()){
//                this.onHovered();
//            }

            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, recipe.output.getHoverName(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, recipe.output.getHoverName(), mx, my);
            }
        });
    }


    public ItemStackButton constructInformationButton(ItemStack logo, int x , int y, AncientFragment fragment){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new InformationScreen(fragment, (InfusingRecipeScreen) null));
        },logo,1.5f, (button,matrices,mx,my)->{

            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }

    public ItemStackButton constructStructureButton(MultiblockStructure structure, int x , int y, AncientFragment fragment){
        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(new StructureScreen(fragment,structure));
        },structure.mainBlock.getBlock().asItem().getDefaultInstance(),1.5f, (button,matrices,mx,my)->{

            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }

    private ItemStackButton constructCustomButton(int x,int y,AncientFragment fragment){

        Supplier<Screen> sp = RegistryDelegate.getObject(SolarCraftClientRegistries.SCREENS,new ResourceLocation("solarcraft",fragment.getScreenID()));

        return new ItemStackButton(x,y,24,24,(button)->{
            Minecraft.getInstance().setScreen(sp.get());
        },fragment.getIcon().getDefaultInstance(),1.5f,(button,matrices,mx,my)->{

            if (Minecraft.getInstance().screen instanceof SolarLexiconRecipesScreen screen) {
                screen.postRender.add(()->screen.renderTooltip(matrices, fragment.getTranslation(), mx, my));

            }else{
                Minecraft.getInstance().screen.renderTooltip(matrices, fragment.getTranslation(), mx, my);
            }
        });
    }



    /**
     * @param matrices Just a posestack
     * @param x x size
     * @param y y size
     * @param p initial point
     */

    public static void drawRectangle(PoseStack matrices, int x, int y, Point p,int r,int g,int b){
        if (Minecraft.getInstance().screen instanceof IScrollable scrollable) {
            int scrollX = scrollable.getCurrentScrollX();
            int scrollY = scrollable.getCurrentScrollY();
            RenderingTools.drawLine(matrices, p.x + scrollX, p.y + scrollY, p.x + x + scrollX, p.y + scrollY, r, g, b);
            RenderingTools.drawLine(matrices, p.x + x + scrollX, p.y + scrollY, p.x + x + scrollX, p.y + y + scrollY, r, g, b);
            RenderingTools.drawLine(matrices, p.x + scrollX, p.y + y + scrollY, p.x + x + scrollX, p.y + y + scrollY, r, g, b);
            RenderingTools.drawLine(matrices, p.x + scrollX, p.y + scrollY, p.x + scrollX, p.y + y + scrollY, r, g, b);
        }else{
            RenderingTools.drawLine(matrices, p.x , p.y , p.x + x , p.y , r, g, b);
            RenderingTools.drawLine(matrices, p.x + x , p.y , p.x + x , p.y + y , r, g, b);
            RenderingTools.drawLine(matrices, p.x , p.y + y , p.x + x , p.y + y , r, g, b);
            RenderingTools.drawLine(matrices, p.x , p.y, p.x , p.y + y , r, g, b);
        }
    }



    public void tick(){

    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
