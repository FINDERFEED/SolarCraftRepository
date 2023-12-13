package com.finderfeed.solarcraft.content.items.solar_wand.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class SolarWandRenderProperties implements IClientItemExtensions {

    public static final SolarWandRenderProperties INSTANCE = new SolarWandRenderProperties();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new SolarWandISTER(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    }
}
