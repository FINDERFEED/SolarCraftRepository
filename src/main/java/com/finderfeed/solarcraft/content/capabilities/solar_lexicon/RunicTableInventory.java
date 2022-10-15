package com.finderfeed.solarcraft.content.capabilities.solar_lexicon;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RunicTableInventory implements ICapabilitySerializable<CompoundTag> {
    private final ItemStackHandler RUNIC_TABLE_INVENTORY = new ItemStackHandler(9);
    private final LazyOptional<ItemStackHandler> RUNIC_TABLE_INV_OPT = LazyOptional.of(()-> RUNIC_TABLE_INVENTORY);
    public void invalidate(){
        RUNIC_TABLE_INV_OPT.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap != ForgeCapabilities.ITEM_HANDLER){
            return LazyOptional.empty();
        }
        return RUNIC_TABLE_INV_OPT.cast();
    }

    @Override
    public CompoundTag serializeNBT() {
        return RUNIC_TABLE_INVENTORY.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        RUNIC_TABLE_INVENTORY.deserializeNBT(nbt);
    }
}
