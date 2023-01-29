package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action;

import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * If you wish to let player drain runic energy out of your block entities you can use this interface on them
 */
public interface IREWandDrainable {

    /**
     * return how much energy should player get
     */
    float drainEnergy(RunicEnergy.Type type,Player player,float amount);

    /**
     * if player has maximum energy the remains will go here
     */
    float returnEnergy(RunicEnergy.Type type,Player player,float amount);

    /**
     * Max energy per use tick
     */
    float getMaxEnergyDrain();

    /**
     * What types are allowed to be drained out of your tile
     */
    List<RunicEnergy.Type> allowedDrainableTypes();

    /**
     * Should your tile automatically switch wand drain runic energy type (like pylons do)
     */
    boolean shouldAutomaticallySwitchWandType();

}
