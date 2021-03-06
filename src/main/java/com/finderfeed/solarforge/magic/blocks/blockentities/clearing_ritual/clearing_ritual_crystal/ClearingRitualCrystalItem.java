package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarforge.SolarForge;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class ClearingRitualCrystalItem extends BlockItem {
    public ClearingRitualCrystalItem(Block block, Properties props) {
        super(block, props);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        CompoundTag tileTag = stack.getTagElement(SolarForge.MOD_ID + "_tile_saved_data");
        if (tileTag != null){
            CompoundTag tag = tileTag.getCompound("data");
            if (tag.contains("retype")){
                components.add(new TextComponent(tag.getString("retype").toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.GOLD));
            }
        }
        super.appendHoverText(stack, world, components, flag);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(RenderProperties.INSTANCE);
    }
}
class RenderProperties implements IItemRenderProperties{

    public static final RenderProperties INSTANCE = new RenderProperties();


    @Override
    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
        return new ClearingRitualCrystalISTER(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    }
}
