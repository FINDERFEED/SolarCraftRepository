package com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.AbilityIndexSetPacket;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;


import net.minecraft.client.Minecraft;


import net.minecraft.client.gui.screens.Screen;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;


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
        addSolarButton(relX,relY,10,1,"Fireball");
        addSolarButton(relX,relY,30,2,"Lightning");
        addSolarButton(relX,relY,50,4,"Disarm");
        addSolarButton(relX,relY,70,3,"Solar Strike");
        addSolarButton(relX,relY,90,5,"Meteorite");
        addSolarButton(relX,relY,110,6,"Heal");
        addSolarButton(relX,relY,130,7,"Alchemist");
        addSolarButton(relX,relY,150,8,"Dispel");
//        addSolarButton(relX,relY,10-this.height,9,"Tornado");
    }

    @Override
    public void tick() {
        if (page != previousPage){
            List<AbstractWidget> all = this.buttons;
            if (page > previousPage) {
                System.out.println(all.get(0).y);
                for (int i = 0; i < all.size(); i++) {
                    all.get(i).y += this.height;
                }

                System.out.println(all.get(0).y);
                previousPage = page;
            }else {
                System.out.println(all.get(0).y);
                for (int i = 0; i < all.size(); i++) {
                    all.get(i).y -= this.height;
                }
                System.out.println(all.get(0).y);
                previousPage = page;
            }

        }

    }

    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks){

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;


        this.blit(stack, relX, relY, 0, 0, WIDTH, HEIGHT);
        drawCenteredString(stack,minecraft.font,"Page "+page+"/"+maxPages,relX,relY,0xffffff);

       // GL11.glScissor((int)(relX*scale), (int)(relY*scale), scissorsWidth, scissorsHeight-120);

        super.render(stack,rouseX,rouseY,partialTicks);

    }



    public void addSolarButton(int relX, int relY, int offset, int abilityId, String string){
        addButton(new SolarForgeButton(relX + 6, relY-4+offset, 65, 15, new TextComponent(string), button -> {
            SolarForgePacketHandler.INSTANCE.sendToServer(new AbilityIndexSetPacket(new int[]{ids,abilityId}));
            Minecraft.getInstance().setScreen(new SolarForgeAbilityConfigScreen());
        }));
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

