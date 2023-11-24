package com.finderfeed.solarcraft.content.items.solar_lexicon.screen;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.buttons.ItemStackTabButton;
import com.finderfeed.solarcraft.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class SmeltingRecipeScreen extends LexiconScreen {
    public final ResourceLocation MAIN_SCREEN = new ResourceLocation(SolarCraft.MOD_ID,"textures/gui/smelting_recipe_gui.png");
    public final SolarSmeltingRecipe recipe;

    public List<ItemStack> stacks ;

    public SmeltingRecipeScreen(SolarSmeltingRecipe a) {
        super();
        this.recipe = a;
    }

    private void addStack(Ingredient ingr, List<ItemStack> stacks){
        if (!ingr.isEmpty()){
            stacks.add(ingr.getItems()[0]);
        }else{
            stacks.add(ItemStack.EMPTY);
        }
    }


    @Override
    protected void init() {
        super.init();
        stacks = new ArrayList<>();
        stacks.add(recipe.output);


        addRenderableWidget(new ItemStackTabButton(relX+202,relY+9 + 20,17,17,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());},
                Items.CRAFTING_TABLE.getDefaultInstance(),0.7f,
                (buttons, graphics, b, c) -> {
                    graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.recipes_screen"), b, c);
                }));
        addRenderableWidget(new ItemStackTabButton(relX + 202,relY+27 + 20,17,17,(button)->{
            ClientEventsHandler.SOLAR_LEXICON_SCREEN_HANDLER.memorizeAndClose();

        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f,(buttons, graphics, b, c) -> {
            graphics.renderTooltip(font, Component.translatable("solarcraft.screens.buttons.memorize_page"), b, c);
        }));

    }

    @Override
    public int getScreenWidth() {
        return 204;
    }

    @Override
    public int getScreenHeight() {
        return 207;
    }


    @Override
    public void render(GuiGraphics graphics, int mousex, int mousey, float partialTicks) {
        ClientHelpers.bindText(MAIN_SCREEN);
        PoseStack matrices = graphics.pose();
        RenderingTools.blitWithBlend(matrices,relX,relY,0,0,this.getScreenWidth(),this.getScreenHeight(),256,256,0,1f);

        int iter = 0;

        for (ItemStack item : recipe.getStacks()){

            int posX = (iter % 3) * 17;
            int posY = (int)Math.floor((float)iter / 3) * 17;

            RenderingTools.renderItemAndTooltip(item,graphics,relX + 77 + posX , relY + 118 + posY,mousex,mousey,100);

            iter++;
        }

        ItemStack result = recipe.getResultItem(Minecraft.getInstance().level.registryAccess());
        RenderingTools.renderItemAndTooltip(result,graphics,relX+94,relY+169,mousex,mousey,100);
        super.render(graphics,mousex,mousey,partialTicks);
    }
}
