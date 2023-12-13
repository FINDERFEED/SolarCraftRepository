package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.content.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarcraft.helpers.ClientHelpers;

import com.finderfeed.solarcraft.local_library.client.screens.DefaultScreen;
import com.finderfeed.solarcraft.local_library.client.screens.screen_coomponents.ScissoredTextBox;
import com.finderfeed.solarcraft.local_library.custom_registries.RegistryDelegate;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.registries.SolarCraftClientRegistries;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class InformationScreen extends LexiconScreen {


    private final ResourceLocation LOC = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/solar_lexicon_info_screen_new.png");

    private Item icon;
    private Screen screen;

    private AncientFragment fragment;
    private int ticker = 0;
    public InformationScreen(AncientFragment fragment,InfusingRecipeScreen screen) {
        this.screen = screen;
        this.icon = SCItems.INFUSER_ITEM.get();
        this.fragment = fragment;
    }

    public InformationScreen(AncientFragment fragment,InfusingCraftingRecipeScreen screen) {
        this.screen = screen;
        this.icon = SCItems.INFUSING_TABLE.get();
        this.fragment = fragment;
    }

    public InformationScreen(AncientFragment fragment,CraftingRecipeScreen screen) {
        this.screen = screen;
        this.icon = Items.CRAFTING_TABLE;
        this.fragment = fragment;
    }

    @Override
    public void tick() {
        super.tick();
        ticker++;
    }

    @Override
    protected void init() {
        super.init();
        ticker = 0;

        ItemStackButton button = new ItemStackTabButton(relX+255,relY+25 + 8   ,17,17,(buttons)->{
            if (screen != null) {
                Minecraft.getInstance().setScreen(screen);
            }
        }, icon.getDefaultInstance(),0.7f,(buttons,graphics,b,c)->{
            graphics.renderTooltip(font,Component.translatable("solarcraft.screens.buttons.crafting_recipe"),b,c);

        });
        if (screen != null){
            addRenderableWidget(button);
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
                ItemStackTabButton button1 = new ItemStackTabButton(relX + 255, relY + 25 + 18  + h * 18 + 40, 17, 17, b -> {
                    Minecraft.getInstance().setScreen(getScreenFromFragment(ref));
                }, ref.getIcon().getDefaultInstance(), 0.7f, (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, ref.getTranslation(), b, c);
                });
                h++;
                addRenderableWidget(button1);
            }
        }

//            addRenderableWidget(new ItemStackTabButton(relX + 255, relY + 28 - 3, 17, 17, (buttons) -> {
//                minecraft.setScreen(new SolarLexiconRecipesScreen());
//            },
//                    Items.CRAFTING_TABLE.getDefaultInstance(), 0.7f, (buttons, graphics, b, c) -> {
//                graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
//            }));




        //232 119 228
        ScissoredTextBox textBox = new ScissoredTextBox(this,relX + 14,relY + 81,228,115,
                SolarLexiconScreen.TEXT_COLOR,0xff3A3A3A,0xff999999,
                fragment.getType() == AncientFragment.Type.INFORMATION ? fragment.getLore() : fragment.getItemDescription());
        textBox.setFocused(true);


        this.addFDComponent("textBox",textBox);
    }

    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        PoseStack matrices = graphics.pose();
        ClientHelpers.bindText(LOC);
        matrices.pushPose();
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,256,209,256,256,0,1f);

        graphics.drawString(Minecraft.getInstance().font,fragment.getTranslation(), relX+60,relY+35,0xffffff);

        this.renderComponents(graphics,mousex,mousey,partialTicks,"textBox");

        RenderingTools.renderScaledGuiItem(graphics,fragment.getIcon().getDefaultInstance(),relX + 32, relY + 32,1f,0);
        matrices.popPose();

        super.render(graphics, mousex, mousey, partialTicks);
    }

    public static Screen getScreenFromFragment(AncientFragment fragment){
        switch (fragment.getType()){
            case ITEMS -> {
                Level world = Minecraft.getInstance().level;
                RecipeType<?> type = fragment.getRecipeType();
                if (type == SolarcraftRecipeTypes.INFUSING.get()){
                    List<InfusingRecipe> recipes = new ArrayList<>();
                    for (AncientFragment.ItemWithRecipe i : fragment.getStacks()){
                        recipes.add((InfusingRecipe) world.getRecipeManager().byKey(i.getRecipeLocation()).orElseThrow());
                    }
                    return new InformationScreen(fragment,new InfusingRecipeScreen(recipes));
                }else if (type == SolarcraftRecipeTypes.INFUSING_CRAFTING.get()){
                    List<InfusingCraftingRecipe> recipes = new ArrayList<>();
                    for (AncientFragment.ItemWithRecipe i : fragment.getStacks()){
                        recipes.add((InfusingCraftingRecipe) world.getRecipeManager().byKey(i.getRecipeLocation()).orElseThrow());
                    }
                    return new InformationScreen(fragment,new InfusingCraftingRecipeScreen(recipes));
                }else if (type == SolarcraftRecipeTypes.SMELTING.get()){
                    SolarSmeltingRecipe r =  (SolarSmeltingRecipe)world.getRecipeManager().byKey(fragment.getStacks().get(0).getRecipeLocation()).orElseThrow();
                    return new SmeltingRecipeScreen(r);
                }else if (type == RecipeType.CRAFTING){
                    List<CraftingRecipe> recipes = new ArrayList<>();
                    for (AncientFragment.ItemWithRecipe i : fragment.getStacks()){
                        recipes.add((CraftingRecipe) world.getRecipeManager().byKey(i.getRecipeLocation()).orElseThrow());
                    }
                    return new InformationScreen(fragment,new CraftingRecipeScreen(recipes));
                }
                return null;
            }
            case INFORMATION -> {
                return new InformationScreen(fragment,(InfusingRecipeScreen) null);
            }
            case CUSTOM -> {
                Supplier<Screen> s = RegistryDelegate.getObject(SolarCraftClientRegistries.SCREENS,new ResourceLocation(SolarCraft.MOD_ID,fragment.getScreenID()));
                return s.get();
            }
            case STRUCTURE -> {
                return new StructureScreen(fragment,fragment.getStructure());
            }
            default -> {
                return null;
            }
        }
    }


    @Override
    public int getScreenWidth() {
        return 256;
    }

    @Override
    public int getScreenHeight() {
        return 209;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
