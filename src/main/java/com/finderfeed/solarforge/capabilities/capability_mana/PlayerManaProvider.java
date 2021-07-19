package com.finderfeed.solarforge.capabilities.capability_mana;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerManaProvider implements ICapabilitySerializable<CompoundNBT> {

    private final SolarManaCapability solar_mana = new SolarManaCapability();
    private final LazyOptional<SolarForgeMana> solar_mana_optional = LazyOptional.of(()->solar_mana);
    public void invalidate(){
        solar_mana_optional.invalidate();
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return solar_mana_optional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CapabilitySolarMana.SOLAR_MANA_PLAYER == null){
            return new CompoundNBT();

        }else{
            return (CompoundNBT) CapabilitySolarMana.SOLAR_MANA_PLAYER.writeNBT(solar_mana,null);
        }

    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CapabilitySolarMana.SOLAR_MANA_PLAYER !=null){
            CapabilitySolarMana.SOLAR_MANA_PLAYER.readNBT(solar_mana,null,nbt);
        }
    }
}
