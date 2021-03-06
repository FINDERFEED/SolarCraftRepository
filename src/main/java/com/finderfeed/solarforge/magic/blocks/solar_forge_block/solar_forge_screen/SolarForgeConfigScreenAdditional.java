package com.finderfeed.solarforge.magic.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarforge.client.screens.ScrollableScreen;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.AbilityIndexSetPacket;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;


import net.minecraft.client.Minecraft;


import net.minecraft.client.gui.screens.Screen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;


public class SolarForgeConfigScreenAdditional extends ScrollableScreen {

    private static final int WIDTH = 77;
    private static final int HEIGHT = 180;
    private final int ids;
    private final ResourceLocation GUI = new ResourceLocation("solarforge","textures/gui/solar_config_gui.png");
    public int page;
    private int maxPages;
    private int previousPage;


    public SolarForgeConfigScreenAdditional(int index) {
        super();
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

        this.page = 1;
        this.maxPages = 1;
        this.previousPage = 1;
        int iter = 0;
        for (AbstractAbility ability : AbilitiesRegistry.getAllAbilities()) {
            addSolarButton(relX + 82,relY + 30,10 + iter * 20,ability.id,new TranslatableComponent("name." + ability.id).getString());
            iter += 1;
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected int getScrollValue() {
        return 4;
    }

    @Override
    protected int getMaxYDownScrollValue() {
        return 20;
    }

    @Override
    protected int getMaxXRightScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxYUpScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxXLeftScrollValue() {
        return 0;
    }


    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks){

        ClientHelpers.bindText(GUI);

        this.blit(stack, relX + 82, relY + 30, 0, 0, WIDTH, HEIGHT);

        int scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int scaledHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();
        int scale = (int)Minecraft.getInstance().getWindow().getGuiScale();
        RenderSystem.enableScissor((width - WIDTH*scale)/2,(height - HEIGHT*scale + 15*scale)/2,WIDTH*scale,(HEIGHT - 22)*scale);

        super.render(stack,rouseX,rouseY,partialTicks);
        RenderSystem.disableScissor();
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

