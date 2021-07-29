package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.magic_items.items.isters.EnergyPylonISTER;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class EnergyPylonBlockItem extends BlockItem {
    public EnergyPylonBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(com.finderfeed.solarforge.magic_items.items.Properties.INSTANCE);
    }
}
class Properties implements IItemRenderProperties{

    public static Properties INSTANCE = new Properties();

    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {

        return new EnergyPylonISTER(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    }
}