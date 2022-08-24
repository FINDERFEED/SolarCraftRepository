package com.finderfeed.solarforge.content.capabilities.capability_mana;


import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilitySolarMana {


    public static Capability<SolarForgeMana> SOLAR_MANA_PLAYER = CapabilityManager.get(new CapabilityToken<SolarForgeMana>(){});

//    public static void register(){
//        CapabilityManager.INSTANCE.register(SolarForgeMana.class);
//    }

    public static LazyOptional<SolarForgeMana> getSolarMana(final LivingEntity ent){
        return ent.getCapability(SOLAR_MANA_PLAYER,null);
    }


}

