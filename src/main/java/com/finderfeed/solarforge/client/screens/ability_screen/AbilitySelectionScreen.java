package com.finderfeed.solarforge.client.screens.ability_screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.client.screens.SolarCraftScreen;
import com.finderfeed.solarforge.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarforge.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeButton;
import com.finderfeed.solarforge.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeButtonYellow;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.BuyAbilityPacket;
import com.finderfeed.solarforge.packet_handler.packets.RequestAbilityScreen;
import com.finderfeed.solarforge.packet_handler.packets.RequestAbilityScreenPacket;
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
    private int ticker = 0;
    private int energy = 0;

    public List<AbstractAbility> bindedAbilities;

    public AbilitySelectionScreen(String[] abils){
        setupBindedAbilities(abils);
    }

    public void setupBindedAbilities(String[] abils){
        bindedAbilities = new ArrayList<>();
        for (String str : abils){
            bindedAbilities.add(AbilitiesRegistry.getAbilityByID(str));
        }
    }

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

        SolarForgeButtonYellow b = new SolarForgeButtonYellow(relX+235,
                relY+195,65,15,new TranslatableComponent("ability.buy_ability"),(button)->{
            if (selectedAbility != null) {
                SolarForgePacketHandler.INSTANCE.sendToServer(new BuyAbilityPacket(selectedAbility.id));
                SolarForgePacketHandler.INSTANCE.sendToServer(new RequestAbilityScreenPacket(true));
            }
        });
        addRenderableWidget(b);

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
        ticker = 0;
        selectedAbility = btns.get(currentSelectedAbilityIndex).ability;
        return super.mouseScrolled(mousePosX, mousePosY, delta);
    }

    @Override
    public void tick() {
        super.tick();
        value.tick();
        ticker++;
        this.energy = ClientHelpers.getClientPlayer().getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY);
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float pTicks) {
        int xShift = -7;
        for (AbilityScreenButton button : btns){
            button.y = button.getInitY() - (int)value.getCurrentValue(pTicks);
        }
        matrices.pushPose();
        RenderingTools.renderTextField(matrices,xShift + relX + 210,relY + 10 ,120,210);
        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("name."+selectedAbility.id),
                xShift + relX+270,relY+15,SolarLexiconScreen.TEXT_COLOR);
        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("solarcraft.buy_cost").append(": "+ selectedAbility.buyCost),
                xShift + relX+270,relY+210 - 30, SolarLexiconScreen.TEXT_COLOR);
        drawCenteredString(matrices,font,
                "Player energy: "+ energy,
                xShift + relX+270,relY+198 - 30,SolarLexiconScreen.TEXT_COLOR);
        RenderingTools.drawCenteredBoundedTextObfuscated(matrices,xShift + relX + 270, relY + 25,20,
                new TranslatableComponent("desc."+selectedAbility.id),SolarLexiconScreen.TEXT_COLOR,ticker*5);
        matrices.popPose();
        super.render(matrices, mousex, mousey, pTicks);
    }





}
