package com.finderfeed.solarcraft.content.items;


import com.finderfeed.solarcraft.content.items.isters.EnergyPylonISTER;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class PylonItemPlaceholder extends Item {
    public PylonItemPlaceholder(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(PylonRenderProperties.INSTANCE);
    }
}

class PylonRenderProperties implements IClientItemExtensions {

    public static PylonRenderProperties INSTANCE = new PylonRenderProperties();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {

        return new EnergyPylonISTER(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    }
}
