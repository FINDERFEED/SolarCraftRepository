package com.finderfeed.solarcraft.content.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InfusingTableInventory implements ICapabilitySerializable<CompoundTag> {

    private final ItemStackHandler INFUSING_TABLE_INV = new ItemStackHandler(10);
    private final LazyOptional<ItemStackHandler> INFUSING_TABLE_INV_OPTIONAL = LazyOptional.of(()->INFUSING_TABLE_INV);
    public void invalidate(){
        INFUSING_TABLE_INV_OPTIONAL.invalidate();
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
        return INFUSING_TABLE_INV.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        INFUSING_TABLE_INV.deserializeNBT(nbt);
    }
}
