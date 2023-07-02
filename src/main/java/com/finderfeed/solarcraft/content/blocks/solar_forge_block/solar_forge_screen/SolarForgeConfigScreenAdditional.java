package com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.abilities.ability_classes.AbstractAbility;
import com.finderfeed.solarcraft.client.screens.ScrollableScreen;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.AbilityIndexSetPacket;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;


import net.minecraft.client.Minecraft;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;


public class SolarForgeConfigScreenAdditional extends ScrollableScreen {

    private static final int WIDTH = 77;
    private static final int HEIGHT = 180;
    private final int ids;
    private final ResourceLocation GUI = new ResourceLocation("solarcraft","textures/gui/solar_config_gui.png");
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
            addSolarButton(relX + 82,relY + 30,10 + iter * 20,ability.id,Component.translatable("name." + ability.id).getString());
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
        addRenderableWidget(new SolarCraftButtonRed(relX + 6, relY-4+offset, 65, 15, Component.literal(string), button -> {
            SCPacketHandler.INSTANCE.sendToServer(new AbilityIndexSetPacket(ids,abilityId));
            Minecraft.getInstance().setScreen(new SolarForgeAbilityConfigScreen());
        }));
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

