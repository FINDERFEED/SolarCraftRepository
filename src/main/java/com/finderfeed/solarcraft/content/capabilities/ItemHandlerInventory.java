package com.finderfeed.solarcraft.content.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.ICapabilitySerializable;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemHandlerInventory implements ICapabilitySerializable<CompoundTag> {

    private final ItemStackHandler INVENTORY;
    private final LazyOptional<ItemStackHandler> INFUSING_TABLE_INV_OPTIONAL ;
    public void invalidate(){
        INFUSING_TABLE_INV_OPTIONAL.invalidate();
    }

    public ItemHandlerInventory(int size){
        this.INVENTORY =  new ItemStackHandler(size);
        INFUSING_TABLE_INV_OPTIONAL = LazyOptional.of(()-> INVENTORY);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap != ForgeCapabilities.ITEM_HANDLER){
            return LazyOptional.empty();
        }
        return INFUSING_TABLE_INV_OPTIONAL.cast();
    }

    @Override
    public CompoundTag serializeNBT() {
        return INVENTORY.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        INVENTORY.deserializeNBT(nbt);
    }
}
