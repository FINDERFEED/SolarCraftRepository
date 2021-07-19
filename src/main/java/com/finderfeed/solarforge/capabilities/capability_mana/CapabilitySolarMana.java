package com.finderfeed.solarforge.capabilities.capability_mana;


import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class CapabilitySolarMana {

    @CapabilityInject(SolarForgeMana.class)
    public static Capability<SolarForgeMana> SOLAR_MANA_PLAYER = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(SolarForgeMana.class,new ManaStorage(),SolarManaCapability::new);
    }

    public static LazyOptional<SolarForgeMana> getSolarMana(final LivingEntity ent){
        return ent.getCapability(SOLAR_MANA_PLAYER,null);
    }


    public static class ManaStorage implements Capability.IStorage<SolarForgeMana>{

        @Nullable
        @Override
        public INBT writeNBT(Capability<SolarForgeMana> capability, SolarForgeMana instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putDouble("solar_mana",instance.getMana());
            return tag;
        }

        @Override
        public void readNBT(Capability<SolarForgeMana> capability, SolarForgeMana instance, Direction side, INBT nbt) {
            double solar_mana = ((CompoundNBT)nbt).getDouble("solar_mana");
            instance.setMana(solar_mana);
        }
    }
}
