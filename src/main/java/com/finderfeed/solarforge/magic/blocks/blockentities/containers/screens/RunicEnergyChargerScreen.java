package com.finderfeed.solarforge.magic.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.RunicEnergyChargerContainer;
import com.finderfeed.solarforge.magic.items.RuneItem;
import com.finderfeed.solarforge.magic.items.runic_energy.IRunicEnergyUser;
import com.finderfeed.solarforge.magic.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class RunicEnergyChargerScreen extends AbstractScrollableContainerScreen<RunicEnergyChargerContainer> {

    private static final RunicEnergyContainer R_CONTAINER = new RunicEnergyContainer();
    public static final ResourceLocation LOCATION = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/runic_energy_charger_new.png");
    private int ticker = 0;

    public RunicEnergyChargerScreen(RunicEnergyChargerContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
        this.inventoryLabelX = 6;
        this.inventoryLabelY = 70;
    }


    @Override
    public void render(PoseStack stack, int rouseX, int rouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, rouseX, rouseY, partialTicks);
        this.renderTooltip(stack,rouseX,rouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            R_CONTAINER.set(type,(float)menu.tile.getRunicEnergy(type));
        }
        ticker++;
    }

    @Override
    protected void renderBg(PoseStack matrices, float pticks, int mousex, int mousey) {
        ClientHelpers.bindText(LOCATION);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        int xOffset = 32 + a;
        int yOffset = 14 ;
        blit(matrices,relX + xOffset,relY + yOffset,0,0,176,256,256,256);
//        List<Component> cmps = RenderingTools.renderRunicEnergyGui(matrices,relX-72 + xOffset,relY + yOffset,
//                mousex,mousey,R_CONTAINER,null,(float)menu.tile.getRunicEnergyLimit());
//        renderTooltip(matrices,cmps, Optional.empty(),mousex,mousey);

        float maxRE = (float)menu.tile.getRunicEnergyLimit()*8;
        boolean shouldRenderRuneCharging = menu.tile.runeSlot().getItem() instanceof RuneItem item && menu.tile.getRunicEnergy(item.type) < menu.tile.getRunicEnergyLimit();
        boolean shouldRenderItemCharging = menu.tile.chargeSlot().getItem() instanceof IRunicEnergyUser user && !ItemRunicEnergy.isFullyCharged(menu.tile.chargeSlot(),user);
        int t = ticker*2 % 38;
        if (shouldRenderRuneCharging){
            blit(matrices,relX+44 + xOffset + t,relY+43 + yOffset,177+t,0,5,24,256,256);
        }
        if (shouldRenderItemCharging){
            blit(matrices,relX+97 + xOffset + t,relY+43 + yOffset,177+t,24,5,24,256,256);
        }
        float curEnergy = 0;
        List<Component> components = new ArrayList<>();
        for (RunicEnergy.Type type : RunicEnergy.Type.getAll()){
            float re = menu.tile.getRunicEnergy(type);
            components.add(new TextComponent(type.id.toUpperCase(Locale.ROOT)).append(new TextComponent(": "+ re + "/" + menu.tile.getRunicEnergyLimit()).withStyle(ChatFormatting.GOLD)));
            curEnergy += re;
        }
        float percent = curEnergy/maxRE;
        blit(matrices,relX+71 + xOffset,relY+38 + yOffset + (int)(34*(1-percent)),176,48 + (int)(34*(1-percent)),34,(int)Math.ceil(34*percent),256,256);

        if (RenderingTools.isMouseInBorders(mousex,mousey,relX + 80 + xOffset,relY + 47 + yOffset,relX + 98 + xOffset,relY + 47 + 18 + yOffset )){
            renderTooltip(matrices,components,Optional.empty(),mousex,mousey,font);
        }
        drawCenteredString(matrices,font,"Runic Energy Charger",relX + 89 + xOffset,relY + 9 + yOffset,0xff1111);
    }

    @Override
    protected int getScrollValue() {
        return 0;
    }

    @Override
    protected int getMaxYDownScrollValue() {
        return 0;
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
}
