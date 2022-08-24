package com.finderfeed.solarforge.content.capabilities.capability_mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Deprecated
public class PlayerManaProvider implements ICapabilitySerializable<CompoundTag> {

    private final SolarManaCapability solar_mana = new SolarManaCapability();
    private final LazyOptional<SolarForgeMana> solar_mana_optional = LazyOptional.of(()->solar_mana);
    public void invalidate(){
        solar_mana_optional.invalidate();
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilitySolarMana.SOLAR_MANA_PLAYER ? solar_mana_optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        if (CapabilitySolarMana.SOLAR_MANA_PLAYER == null){
            return new CompoundTag();

        }else{
            return (CompoundTag) writeNBT(CapabilitySolarMana.SOLAR_MANA_PLAYER,solar_mana,null);
        }

    }
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (CapabilitySolarMana.SOLAR_MANA_PLAYER !=null){
            readNBT(CapabilitySolarMana.SOLAR_MANA_PLAYER,solar_mana,null,nbt);
        }
    }
    public Tag writeNBT(Capability<SolarForgeMana> capability, SolarForgeMana instance, Direction side) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("solar_mana",instance.getMana());
        return tag;
    }
    public void readNBT(Capability<SolarForgeMana> capability, SolarForgeMana instance, Direction side, Tag nbt) {
        double solar_mana = ((CompoundTag)nbt).getDouble("solar_mana");
        instance.setMana(solar_mana);
    }
}
