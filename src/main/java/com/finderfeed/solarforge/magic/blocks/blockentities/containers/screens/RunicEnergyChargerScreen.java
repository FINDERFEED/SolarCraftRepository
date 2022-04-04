package com.finderfeed.solarforge.magic.blocks.blockentities.containers.screens;

import com.finderfeed.solarforge.ClientHelpers;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.local_library.helpers.RenderingTools;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.RunicEnergyChargerContainer;
import com.finderfeed.solarforge.magic.items.runic_energy.RunicEnergyContainer;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

public class RunicEnergyChargerScreen extends AbstractScrollableContainerScreen<RunicEnergyChargerContainer> {

    private static final RunicEnergyContainer R_CONTAINER = new RunicEnergyContainer();
    public static final ResourceLocation LOCATION = new ResourceLocation(SolarForge.MOD_ID,"textures/gui/runic_energy_charger.png");


    public RunicEnergyChargerScreen(RunicEnergyChargerContainer container, Inventory inventory, Component component) {
        super(container, inventory, component);
        this.inventoryLabelX = 37;
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
    }

    @Override
    protected void renderBg(PoseStack matrices, float pticks, int mousex, int mousey) {
        ClientHelpers.bindText(LOCATION);
        int scale = (int) minecraft.getWindow().getGuiScale();
        int a = 1;
        if (scale == 2) {
            a = 0;
        }
        int xOffset = 63 + a;
        int yOffset = 14 ;
        blit(matrices,relX + xOffset,relY + yOffset,0,0,256,256,256,256);
        List<Component> cmps = RenderingTools.renderRunicEnergyGui(matrices,relX-72 + xOffset,relY + yOffset,
                mousex,mousey,R_CONTAINER,null,(float)menu.tile.getRunicEnergyLimit());
        renderTooltip(matrices,cmps, Optional.empty(),mousex,mousey);
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
