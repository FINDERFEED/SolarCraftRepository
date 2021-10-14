package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarAbilities.Abilities;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.AbilityIndexSetPacket;
import com.mojang.blaze3d.vertex.PoseStack;


import net.minecraft.client.Minecraft;


import net.minecraft.client.gui.screens.Screen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;


public class SolarForgeConfigScreenAdditional extends Screen {

    private static final int WIDTH = 77;
    private static final int HEIGHT = 180;
    private final int ids;
    private final ResourceLocation GUI = new ResourceLocation("solarforge","textures/gui/solar_config_gui.png");
    public int page;
    private int maxPages;
    private int previousPage;


    public SolarForgeConfigScreenAdditional(int index) {
        super(new TranslatableComponent("test.screen.thing"));
        this.ids = index;
    }
    @Override
    public boolean mouseScrolled(double mousePosx, double mousePosy, double delta) {
        if (delta > 0 && page != maxPages){
            page+=1;
        }
        if (delta < 0 && page != 1){
            page-=1;
        }
        return super.mouseScrolled(mousePosx,mousePosy,delta);
    }
    @Override
    public void init(){
        super.init();
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.page = 1;
        this.maxPages = 1;
        this.previousPage = 1;
        addSolarButton(relX,relY,10, Abilities.FIREBALL.getAbility().id,"Fireball");
        addSolarButton(relX,relY,30,Abilities.LIGHTNING.getAbility().id,"Lightning");
        addSolarButton(relX,relY,50,Abilities.DISARM.getAbility().id,"Disarm");
        addSolarButton(relX,relY,70,Abilities.SOLAR_STRIKE.getAbility().id,"Solar Strike");
        addSolarButton(relX,relY,90,Abilities.METEORITE.getAbility().id,"Meteorite");
        addSolarButton(relX,relY,110,Abilities.HEAL.getAbility().id,"Heal");
        addSolarButton(relX,relY,130,Abilities.ALCHEMIST.getAbility().id,"Alchemist");
        addSolarButton(relX,relY,150,Abilities.DISPEL.getAbility().id,"Dispel");
    }



    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks){

        ClientHelpers.bindText(GUI);

        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;


        this.blit(stack, relX, relY, 0, 0, WIDTH, HEIGHT);
//        drawCenteredString(stack,minecraft.font,"Page "+page+"/"+maxPages,relX,relY,0xffffff);

       // GL11.glScissor((int)(relX*scale), (int)(relY*scale), scissorsWidth, scissorsHeight-120);

        super.render(stack,rouseX,rouseY,partialTicks);

    }



    public void addSolarButton(int relX, int relY, int offset, String abilityId, String string){
        addRenderableWidget(new SolarForgeButton(relX + 6, relY-4+offset, 65, 15, new TextComponent(string), button -> {
            SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityIndexSetPacket(ids,abilityId));
            Minecraft.getInstance().setScreen(new SolarForgeAbilityConfigScreen());
        }));
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

