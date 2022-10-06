package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.content.items.isters.EnergyPylonISTER;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;


import java.util.function.Consumer;

public class EnergyPylonBlockItem extends BlockItem {
    public EnergyPylonBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(com.finderfeed.solarforge.content.items.Properties.INSTANCE);
    }
}
class Properties implements IClientItemExtensions{

    public static Properties INSTANCE = new Properties();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {

        return new EnergyPylonISTER(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    }
}