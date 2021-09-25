package com.finderfeed.solarforge.SolarAbilities.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.for_future_library.custom_registries.RegistryDelegate;
import com.finderfeed.solarforge.for_future_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen.SolarForgeButton;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.BuyAbilityPacket;
import com.finderfeed.solarforge.packet_handler.packets.RequestAbilityScreen;
import com.finderfeed.solarforge.registries.SolarcraftRegistries;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class AbilityBuyScreen extends Screen {

    private Button BUY;
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
        this.relY = (height - 218*scale)/2/scale-20;

        int count = 0;
        int maxPages = 0;
        SolarForgeButton b = new SolarForgeButton(relX+124,relY+192,65,15,new TranslatableComponent("ability.buy_ability"),(button)->{
            if (currentAbility != null) {
                SolarForgePacketHandler.INSTANCE.sendToServer(new BuyAbilityPacket(currentAbility.id));
                SolarForgePacketHandler.INSTANCE.sendToServer(new RequestAbilityScreen(true));
            }
        });
        this.BUY = b;
        b.visible =false;
        for (AbstractAbility ability : RegistryDelegate.getAllRegisteredEntriesFor(SolarcraftRegistries.ABILITIES)){

            int yOffset = (count%9)*20;
            int xOffset = ((int)Math.floor((float)count/9))*68;


            addRenderableWidget(new SolarForgeButton(relX-35+xOffset,relY+35+yOffset,65,15,new TranslatableComponent("solarcraft_button."+ability.id),
                    (button)->{
                        this.showText = false;
                        this.ticker = 0;
                        this.currentAbility = ability;
                        this.BUY.visible = true;
                    }
            ));

            addRenderableWidget(b);
            count++;
        }
    }

    @Override
    public void tick() {
        if (this.showText) {
            this.ticker+=2;
        }else{
            this.ticker = 0;
        }
        super.tick();
    }



    @Override
    public void render(PoseStack matrices, int mousex, int mousey, float partialTicks) {
        ClientHelpers.bindText(LOC);
        blit(matrices,relX-40,relY+5+20,0,0,256,256);
        if (currentAbility != null){
            ClientHelpers.bindText(new ResourceLocation("solarforge","textures/abilities/"+currentAbility.id+".png"));
            blit(matrices,relX+157-20,relY+33,0,0,38,38,38,38);
            drawCenteredString(matrices, minecraft.font,new TranslatableComponent("name."+currentAbility.id),relX+176-20,relY+72,0xffffff);
            drawCenteredString(matrices, minecraft.font,new TranslatableComponent("cost."+currentAbility.id).append(String.valueOf(currentAbility.buyCost)),relX+176-20,relY+82,0xffffff);
            doText(matrices,new TranslatableComponent("desc."+currentAbility.id).getString(),17,relX+176-20,relY+92);
        }
        drawCenteredString(matrices,font,String.valueOf(minecraft.player.getPersistentData().getInt(SolarCraftTags.RAW_SOLAR_ENERGY)),relX+87,relY+218,0xff0000);
        super.render(matrices, mousex, mousey, partialTicks);
    }


    private void doText(PoseStack matrices,String str,int maxlength,int posX,int posY){
        this.showText = true;
        List<String> strings = RenderingTools.splitString(str,maxlength);
        int maxSymbols = 0;
        for (String s : strings){
            maxSymbols+=s.length();
        }
        int remainingChars = FinderfeedMathHelper.clamp(0,this.ticker,maxSymbols);
        int count = 0;
        for (String s : strings){
            if (remainingChars > s.length()){
                drawCenteredString(matrices,font,s,posX,posY + count*10,0xffffff);
                count++;
                remainingChars -= s.length();
            }else{
                drawCenteredString(matrices,font,s.substring(0,remainingChars),posX,posY + count*10,0xffffff);
                count++;
                remainingChars = 0;
            }
        }
    }





}
