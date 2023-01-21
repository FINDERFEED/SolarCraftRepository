package com.finderfeed.solarcraft.registries.wand_actions;

import com.finderfeed.solarcraft.content.items.solar_wand.SolarWandItem;
import com.finderfeed.solarcraft.content.items.solar_wand.WandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.CheckSolarEnergyWandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.RENetworkConnectivityWandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.DefaultWandBlockUseAction;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.REDrainWandAction;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.solar_network.SolarNetworkBinderWandAction;

public class SolarCraftWandActionRegistry {


    public static final DefaultWandBlockUseAction ON_BLOCK_USE = register(new DefaultWandBlockUseAction());

    public static final REDrainWandAction RUNIC_ENERGY_DRAIN = register(new REDrainWandAction());

    public static final RENetworkConnectivityWandAction CHECK_RUNIC_NETWORK_CONNECTIVITY = register(new RENetworkConnectivityWandAction());

    public static final CheckSolarEnergyWandAction CHECK_SOLAR_ENERGY_WAND_ACTION = register(new CheckSolarEnergyWandAction());

    public static final SolarNetworkBinderWandAction SOLAR_NETWORK_BINDER_WAND_ACTION = register(new SolarNetworkBinderWandAction());


    public static <T extends WandAction<?>> T register(T action){
        SolarWandItem.registerWandAction(action.getRegistryName(),action);
        return action;
    }

    public static void init(){}

}
