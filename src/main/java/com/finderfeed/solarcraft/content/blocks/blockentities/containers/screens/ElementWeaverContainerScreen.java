package com.finderfeed.solarcraft.content.blocks.blockentities.containers.screens;

import com.finderfeed.solarcraft.content.blocks.blockentities.containers.ElementWeaverContainer;
import com.finderfeed.solarcraft.local_library.client.screens.DefaultContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ElementWeaverContainerScreen extends DefaultContainerScreen<ElementWeaverContainer> {


    public ElementWeaverContainerScreen(ElementWeaverContainer menu, Inventory inv, Component cmp) {
        super(menu, inv, cmp);
        inventoryLabelX = 1000000;

    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack matrix, float partialTicks, int mx, int my) {

    }

    @Override
    public int getScreenWidth() {
        return 256;
    }

    @Override
    public int getScreenHeight() {
        return 256;
    }

}
