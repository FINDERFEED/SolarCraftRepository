package com.finderfeed.solarforge.client.screens.ability_screen;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.client.screens.SolarCraftScreen;
import com.finderfeed.solarforge.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarforge.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeButtonYellow;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.InfoButton;
import com.finderfeed.solarforge.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.AbilityIndexSetPacket;
import com.finderfeed.solarforge.packet_handler.packets.BuyAbilityPacket;
import com.finderfeed.solarforge.packet_handler.packets.RequestAbilityScreenPacket;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AbilitySelectionScreen extends SolarCraftScreen {

    public static final int SLIDE_IN_TIME = 20;

    private InterpolatedValueAbilityScreen value = new InterpolatedValueAbilityScreen(10);
    private InterpolatedValueAbilityScreen slideInValue = new InterpolatedValueAbilityScreen(SLIDE_IN_TIME);
    private List<AbilityScreenButton> btns = new ArrayList<>();
    private int currentShift = 0;
    private int currentSelectedAbilityIndex = 0;
    private AbstractAbility selectedAbility = null;
    private int ticker = 0;
    private int energy = 0;
    private int selectedAbilityId = -1;


    public List<AbstractAbility> bindedAbilities;
    public List<AbilityScreenButton> bindingButtons = new ArrayList<>();
    private List<ButtonAndInitPos> rightButtons = new ArrayList<>();
    private List<ButtonAndInitPos> leftButtons = new ArrayList<>();

    public AbilitySelectionScreen(String[] abils){
        setupBindedAbilities(abils);
    }

    public void setupBindedAbilities(String[] abils){
        bindedAbilities = new ArrayList<>();
        for (String str : abils){
            bindedAbilities.add(AbilitiesRegistry.getAbilityByID(str));
        }
        if (!bindingButtons.isEmpty()){
            for (int i = 0; i < 4; i ++){
                bindingButtons.get(i).setAbility(bindedAbilities.get(i));
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        rightButtons.clear();
        leftButtons.clear();
        value = new InterpolatedValueAbilityScreen(10);
        slideInValue = new InterpolatedValueAbilityScreen(20);
        slideInValue.setNewValue(20);
        btns.clear();
        value.setNewValue(0);
        currentShift = 0;
        currentSelectedAbilityIndex = 0;
        int iter = 0;
        int winX = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int winY = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        for (AbstractAbility ability : AbilitiesRegistry.getAllAbilities()){
            AbilityScreenButton button = new AbilityScreenButton(ability,relX + 75,relY + 60 + iter*150,
                    100,100,
                    (btn)->{
                        if (btn instanceof AbilityScreenButton b && b.isBindingMode() && selectedAbilityId > 0 && selectedAbilityId < 5){
                            SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityIndexSetPacket(selectedAbilityId,b.ability.id));
                            for (AbilityScreenButton button1 : btns){
                                button1.setBindingMode(false);
                            }
                            SolarForgePacketHandler.INSTANCE.sendToServer(new RequestAbilityScreenPacket(true));
                        }
                    });

            addRenderableWidget(button);
            btns.add(button);
            iter++;
        }
        bindingButtons.clear();
        for (int i = 1; i <= 4;i++){
            int finalI = i;
            AbilityScreenButton button = new AbilityScreenButton(bindedAbilities.get(i-1),
                    23 + (i-1) * 30,23,20,20,(b)->{
                this.selectedAbilityId = finalI;
                for (AbilityScreenButton button1 : btns){
                    button1.setBindingMode(true);
                }
            });
            addRenderableWidget(button);
            bindingButtons.add(button);
        }


        SolarForgeButtonYellow b = new SolarForgeButtonYellow(winX - 107,
                winY - 40,65,15,new TranslatableComponent("ability.buy_ability"),(button)->{
            if (selectedAbility != null) {
                SolarForgePacketHandler.INSTANCE.sendToServer(new BuyAbilityPacket(selectedAbility.id));
                SolarForgePacketHandler.INSTANCE.sendToServer(new RequestAbilityScreenPacket(true));
            }
        });
        InfoButton info = new InfoButton(135,27,12,12,(but,matrix,mx,my)->{
           renderTooltip(matrix,
                   font.split(new TranslatableComponent("solarcraft.bind_guide"),250),
                   mx,my);
        });
        addRenderableWidget(b);
        addRenderableWidget(info);


        leftButtons.add(new ButtonAndInitPos(info));
        for (Button button : bindingButtons){
            leftButtons.add(new ButtonAndInitPos(button));
        }

        rightButtons.add(new ButtonAndInitPos(b));

        selectedAbility = btns.get(0).ability;
    }


    @Override
    public boolean keyPressed(int keyCode, int idk, int type) {
        if (keyCode == GLFW.GLFW_KEY_W){
            currentShift = Mth.clamp(currentShift - 150,0,(btns.size()-1) * 150);
            currentSelectedAbilityIndex = Mth.clamp(currentSelectedAbilityIndex - 1,0,btns.size()-1);
            value.setNewValue(currentShift);
            ticker = 0;
            selectedAbility = btns.get(currentSelectedAbilityIndex).ability;
        }else if (keyCode == GLFW.GLFW_KEY_S){
            currentShift = Mth.clamp(currentShift + 150,0,(btns.size()-1) * 150);
            currentSelectedAbilityIndex = Mth.clamp(currentSelectedAbilityIndex + 1,0,btns.size()-1);
            value.setNewValue(currentShift);
            ticker = 0;
            selectedAbility = btns.get(currentSelectedAbilityIndex).ability;
        }

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
        slideInValue.tick();
        this.energy = ClientHelpers.getClientPlayer().getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY);
    }

    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float pTicks) {
        double slide = slideInValue.getCurrentValue(pTicks)/(double) SLIDE_IN_TIME;
        int slideAmount = 180;
        int xShiftRight = (int)(slideAmount - slide * slideAmount);
        int xShiftLeft = (int) (-slideAmount + slide*slideAmount);

        for (ButtonAndInitPos b : leftButtons){
            Button button = b.button;
            int xP = b.x;
            button.x = xP + xShiftLeft;
        }
        for (ButtonAndInitPos b : rightButtons){
            Button button = b.button;
            int xP = b.x;
            button.x = xP + xShiftRight;
        }

        for (AbilityScreenButton button : btns){
            button.y = button.getInitY() - (int)value.getCurrentValue(pTicks);
        }
        matrices.pushPose();
        int winX = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int winY = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        fill(matrices,0,0,winX,winY,0xaa000000);

        RenderingTools.renderTextField(matrices,xShiftRight + winX - 130 - 7,20 ,120,winY - 40);
        int textXStartRight = xShiftRight + winX - 69 - 7;
        int texXStartLeft = 20 + xShiftLeft;
        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("name."+selectedAbility.id),
                textXStartRight,30,SolarLexiconScreen.TEXT_COLOR);
        drawCenteredString(matrices, minecraft.font,new TranslatableComponent("solarcraft.buy_cost")
                        .append(": "+ selectedAbility.buyCost),
                textXStartRight,winY - 50, SolarLexiconScreen.TEXT_COLOR);
        drawCenteredString(matrices,font,
                new TranslatableComponent("solarcraft.raw_solar_energy"),
                textXStartRight,winY - 70,SolarLexiconScreen.TEXT_COLOR);
        drawCenteredString(matrices,font,
                new TranslatableComponent("solarcraft.yours").append(": "+ energy),
                textXStartRight,winY - 60,SolarLexiconScreen.TEXT_COLOR);
        RenderingTools.drawCenteredBoundedTextObfuscated(matrices,textXStartRight + 1, 50,20,
                new TranslatableComponent("desc."+selectedAbility.id),SolarLexiconScreen.TEXT_COLOR,ticker*5);

        RenderingTools.renderTextField(matrices,texXStartLeft,20 ,130,27);
        RenderingTools.renderTextField(matrices,texXStartLeft,winY - 125 ,130,105);
        int iter = 0;
        drawString(matrices,font,new TranslatableComponent("solarcraft.cast_cost"),texXStartLeft + 6,
                winY - 120,SolarLexiconScreen.TEXT_COLOR);
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            drawString(matrices,font,type.toString().toUpperCase(Locale.ROOT)+": " + selectedAbility.cost.get(type),
                    texXStartLeft + 6,
                    winY - 110 + iter*11,SolarLexiconScreen.TEXT_COLOR);
            iter++;
        }

        matrices.popPose();


        super.render(matrices, mousex, mousey, pTicks);
        this.runPostEntries();
    }



    private static class ButtonAndInitPos{

        int x;
        int y;
        Button button;

        ButtonAndInitPos(Button button){
            this.button = button;
            this.x = button.x;
            this.y = button.y;
        }
    }

}
