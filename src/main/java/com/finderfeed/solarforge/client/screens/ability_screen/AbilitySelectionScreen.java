package com.finderfeed.solarforge.client.screens.ability_screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.client.screens.SolarCraftScreen;
import com.finderfeed.solarforge.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class AbilitySelectionScreen extends SolarCraftScreen {


    private InterpolatedValueAbilityScreen value = new InterpolatedValueAbilityScreen();
    private List<AbilityScreenButton> btns = new ArrayList<>();
    private int currentShift = 0;
    private int currentSelectedAbilityIndex = 0;
    private AbstractAbility selectedAbility = null;

    @Override
    protected void init() {
        super.init();
        value = new InterpolatedValueAbilityScreen();
        btns.clear();
        value.setNewValue(0);
        currentShift = 0;
        currentSelectedAbilityIndex = 0;
        int iter = 0;
        for (AbstractAbility ability : AbilitiesRegistry.getAllAbilities()){
            AbilityScreenButton button = new AbilityScreenButton(ability,relX + 75,relY + 60 + iter*150,
                    100,100,
                    (btn)->{

                    });

            addRenderableWidget(button);
            btns.add(button);
            iter++;
        }
        selectedAbility = btns.get(0).ability;
    }


    @Override
    public boolean keyPressed(int keyCode, int idk, int type) {


        return super.keyPressed(keyCode, idk, type);
    }


    @Override
    public boolean mouseScrolled(double mousePosX, double mousePosY, double delta) {
        if (delta > 0){
            currentShift = Mth.clamp(currentShift - 150,0,(btns.size()-1) * 150);
            currentSelectedAbilityIndex = Mth.clamp(currentSelectedAbilityIndex - 1,0,btns.size()-1);
            value.setNewValue(currentShift);
        }else{
            currentShift = Mth.clamp(currentShift + 150,0,(btns.size()-1) * 150);
            currentSelectedAbilityIndex = Mth.clamp(currentSelectedAbilityIndex + 1,0,btns.size()-1);
            value.setNewValue(currentShift);
        }
        selectedAbility = btns.get(currentSelectedAbilityIndex).ability;
        return super.mouseScrolled(mousePosX, mousePosY, delta);
    }

    @Override
    public void tick() {
        super.tick();
        value.tick();
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float pTicks) {
        for (AbilityScreenButton button : btns){
            button.y = button.getInitY() - (int)value.getCurrentValue(pTicks);
        }
        super.render(matrices, mousex, mousey, pTicks);

        RenderingTools.renderTextField(matrices,relX + 210,relY + 10 ,120,210);
        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("name."+selectedAbility.id),
                relX+270,relY+15,SolarLexiconScreen.TEXT_COLOR);
        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("solarcraft.buy_cost").append(": "+ selectedAbility.buyCost),
                relX+270,relY+210, SolarLexiconScreen.TEXT_COLOR);
        drawCenteredString(matrices,font,
                "Player energy: "+ minecraft.player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY),
                relX+270,relY+198,SolarLexiconScreen.TEXT_COLOR);
    }





}
