package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.magic.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * ONLY FOR ItemStackHandler!
 */
public abstract class REItemHandlerBlockEntity extends AbstractRunicEnergyContainer {
    public REItemHandlerBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }



    public ItemStackHandler getInventory(){
        return (ItemStackHandler) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
    }


    public void setStackInSlot(int i, ItemStack stack){
        ItemStackHandler handler = getInventory();
        if (handler == null) return;
        this.getInventory().setStackInSlot(i,stack);
    }

    public Container wrapInContainer(){
        ItemStackHandler handler = getInventory();
        if (handler == null) return null;
        return new PhantomInventory(handler);
    }

    public ItemStack getStackInSlot(int i){
        ItemStackHandler handler = getInventory();
        if (handler == null) return null;
        return handler.getStackInSlot(i);
    }

    public int getSize(){
        ItemStackHandler handler = getInventory();
        if (handler == null) return 0;
        return  handler.getSlots();
    }
}
