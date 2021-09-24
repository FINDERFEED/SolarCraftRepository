package com.finderfeed.solarforge.SolarAbilities.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.for_future_library.custom_registries.RegistryDelegate;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen.SolarForgeButton;
import com.finderfeed.solarforge.registries.SolarcraftRegistries;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class AbilityBuyScreen extends Screen {


    public AbstractAbility currentAbility;
    public boolean showText = false;
    public int ticker;
    public int relX;
    public int relY;
    private final ResourceLocation LOC = new ResourceLocation("solarforge","textures/gui/ability_buy_screen.png");

    public AbilityBuyScreen() {
        super(new TextComponent(""));
    }


    @Override
    protected void init() {
        super.init();
        this.ticker = 0;
        int width = minecraft.getWindow().getWidth();
        int height = minecraft.getWindow().getHeight();
        int scale = (int) minecraft.getWindow().getGuiScale();
        this.relX = (width/scale - 183)/2;
        this.relY = (height - 218*scale)/2/scale;

        int count = 0;
        int maxPages = 0;

        for (AbstractAbility ability : RegistryDelegate.getAllRegisteredEntriesFor(SolarcraftRegistries.ABILITIES)){

            int yOffset = (count%7)*20;
            int xOffset = ((int)Math.floor((float)count/7))*70;


            addRenderableWidget(new SolarForgeButton(relX-10+xOffset,relY+35+yOffset,65,15,new TranslatableComponent("solarcraft_button."+ability.id),
                    (button)->{
                        this.showText = false;
                        this.ticker = 0;
                        this.currentAbility = ability;
                    }
            ));
            count++;
        }
    }

    @Override
    public void tick() {
        if (this.showText) {
            this.ticker++;
        }else{
            this.ticker = 0;
        }
        super.tick();
    }



    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        ClientHelpers.bindText(LOC);
        blit(matrices,relX-20,relY+5+20,0,0,256,256);
        if (currentAbility != null){
            ClientHelpers.bindText(new ResourceLocation("solarforge","textures/abilities/"+currentAbility.id+".png"));
            blit(matrices,relX+150,relY+30,0,0,38,38,38,38);
            drawCenteredString(matrices, minecraft.font,new TranslatableComponent("name."+currentAbility.id),relX+119,relY+30,0xffffff);
            drawCenteredString(matrices, minecraft.font,new TranslatableComponent("cost."+currentAbility.id),relX+119,relY+50,0xffffff);
            doText(matrices,new TranslatableComponent("desc."+currentAbility.id).getString(),20,relX,relY);
        }
        super.render(matrices, mousex, mousey, partialTicks);
    }



    private void doText(PoseStack matrices,String str,int maxlength,int posX,int posY){
        this.showText = true;
        List<String> strings = new ArrayList<>();
        StringBuilder builder = new StringBuilder(str);
        while (true){
            try {
                String sub = builder.substring(0,maxlength);
                if (sub.charAt(sub.length()-1) != ' '){
                    int index = findNearest_Index(sub,sub.length()-1);
                    if (index != -1) {
                        strings.add(sub.substring(0, index));
                        builder.delete(0, index);
                    }else{
                        strings.add(sub);
                        builder.delete(0,maxlength);
                    }
                }else{
                    strings.add(sub);
                    builder.delete(0,maxlength);
                }
            }catch (IndexOutOfBoundsException exc){
                strings.add(builder.toString());
             break;
            }
        }
        int remainingChars = FinderfeedMathHelper.clamp(0,ticker,str.length()-1);
        int kolvoStrok =0;
        for (String s : strings){
            if (remainingChars >= maxlength){
                drawString(matrices,font,s,posX,posY+kolvoStrok*10,0xffffff);
                kolvoStrok++;
                remainingChars-=s.length();
            }else{
                drawString(matrices,font,s.substring(0,remainingChars),posX,posY+kolvoStrok*10,0xffffff);
            }
        }

    }

    private int findNearest_Index(String origstring,int end){
        for (int i = end;i > 0;i--){
            if (origstring.charAt(i) == ' '){
                return i;
            }
        }
        return -1;
    }



}
