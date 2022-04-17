package com.finderfeed.solarforge.magic.items.solar_lexicon.screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.misc_things.RunicEnergy;

import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.List;

public class InfusingRecipeEnergyScreen extends Screen {
    public final ResourceLocation BUTTONS = new ResourceLocation("solarforge","textures/misc/page_buttons.png");
    public final ResourceLocation MAIN_SCREEN_OPENED = new ResourceLocation("solarforge","textures/gui/runic_and_solar_energy_costs.png");
    private int currentPage = 0;
    private final int maxPages;
    private final List<InfusingRecipe> recipes;
    private final RunicEnergy.Type[] ORDERED_TYPES = {
      RunicEnergy.Type.GIRO, RunicEnergy.Type.ULTIMA, RunicEnergy.Type.ZETA, RunicEnergy.Type.ARDO, RunicEnergy.Type.TERA, RunicEnergy.Type.FIRA, RunicEnergy.Type.URBA
      , RunicEnergy.Type.KELDA
    };
    private int relX;
    private int relY;

    public InfusingRecipeEnergyScreen(InfusingRecipe recipe) {
        super(new TextComponent(""));
        this.recipes = List.of(recipe);
        maxPages = 0;
    }

    public InfusingRecipeEnergyScreen(List<InfusingRecipe> recipes) {
        super(new TextComponent(""));
        this.recipes = recipes;
        maxPages = recipes.size()-1;
    }


    public InfusingRecipeEnergyScreen(List<InfusingRecipe> recipes,int currentPage) {
        super(new TextComponent(""));
        this.recipes = recipes;
        maxPages = recipes.size()-1;
        this.currentPage = currentPage;
    }

    @Override
    protected void init() {
        super.init();
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2 - 12;
        this.relY = (height - 218*scale)/2/scale;



        if (maxPages != 0) {
            addRenderableWidget(new ImageButton(relX + 180, relY + 9, 16, 16, 0, 0, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage + 1 <= maxPages)) {
                    currentPage += 1;

                }
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Next recipe"),mousex,mousey);
            },new TextComponent("")));
            addRenderableWidget(new ImageButton(relX + 164, relY + 9, 16, 16, 0, 16, 0, BUTTONS, 16, 32, (button) -> {
                if ((currentPage - 1 >= 0)) {
                    currentPage -= 1;

                }
            },(button,matrices,mousex,mousey)->{
                renderTooltip(matrices,new TextComponent("Previous recipe"),mousex,mousey);
            },new TextComponent("")));
        }


        int xoffs = 37;
        addRenderableWidget(new ItemStackButton(relX+48+xoffs,relY+9,12,12,(button)->{minecraft.setScreen(new InfusingRecipeScreen(recipes,currentPage));}, SolarForge.INFUSER_ITEM.get().getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackButton(relX+74+xoffs,relY+9,12,12,(button)->{minecraft.setScreen(new SolarLexiconRecipesScreen());}, Items.CRAFTING_TABLE.getDefaultInstance(),0.7f));
        addRenderableWidget(new ItemStackButton(relX+61+xoffs,relY+9,12,12,(button)->{
            Minecraft mc = Minecraft.getInstance();
            SolarLexicon lexicon = (SolarLexicon) mc.player.getMainHandItem().getItem();
            lexicon.currentSavedScreen = this;
            minecraft.setScreen(null);
        }, Items.WRITABLE_BOOK.getDefaultInstance(),0.7f));
    }



    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        InfusingRecipe recipe = recipes.get(currentPage);

        matrices.pushPose();
        ClientHelpers.bindText(MAIN_SCREEN_OPENED);
        blit(matrices,relX,relY,0,0,256,256);
        matrices.popPose();

        //float percent = (float)recipe.get(currentPage).requriedEnergy / 100000;
        matrices.pushPose();
        drawCenteredString(matrices,font,new TranslatableComponent("solarcraft.total_energy"),relX+102,relY+126,0xff0000);
        matrices.popPose();
        matrices.pushPose();
        int iter = 0;

        for (RunicEnergy.Type type : ORDERED_TYPES){
            renderEnergyBar(matrices,relX+64+iter*17,relY+90,type,recipe);
            iter++;
        }
        int solaren = Math.round((float)recipe.requriedEnergy / 100000 * 64);
        fill(matrices,relX+15,relY+94-solaren,relX+25,relY+94,0xddffff00);
        double totalEnergy = recipe.requriedEnergy;
        for (double cost : recipe.RUNIC_ENERGY_COST.getCosts()){
            totalEnergy+=cost;
        }
        int totaltext = Math.round((float)totalEnergy / 900000 * 175);
        fill(matrices,relX+15,relY+145,relX+15+totaltext,relY+145+6,0xddffff00);
        drawString(matrices,font,new TranslatableComponent("solarcraft.total_solar_energy"),relX+16,relY+160,0xff0000);
        drawCenteredString(matrices,font,""+recipe.requriedEnergy,relX+160,relY+161,0xff0000);

        drawString(matrices,font,new TranslatableComponent("solarcraft.total_runic_energy"),relX+16,relY+160+21,0xff0000);
        drawCenteredString(matrices,font,""+(int)(totalEnergy-recipe.requriedEnergy),relX+160,relY+161+21,0xff0000);
        matrices.popPose();
        super.render(matrices, mousex, mousey, partialTicks);
    }


    private void renderEnergyBar(PoseStack matrices, int offsetx, int offsety, RunicEnergy.Type type,InfusingRecipe recipe){
        matrices.pushPose();
        double energyCostPerItem = recipe.RUNIC_ENERGY_COST.get(type);
        int xtexture =  ( Math.round( (float)energyCostPerItem/100000*60));
        fill(matrices,offsetx,offsety-xtexture,offsetx+6,offsety,0xddffff00);
        matrices.popPose();
    }
}
