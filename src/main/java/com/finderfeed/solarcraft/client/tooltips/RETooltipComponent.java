package com.finderfeed.solarcraft.client.tooltips;

import com.finderfeed.solarcraft.content.items.runic_energy.RunicEnergyCost;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;

public class RETooltipComponent implements TooltipComponent {

    private RunicEnergyCost cost;

    public RETooltipComponent(RunicEnergyCost cost){
        this.cost = cost;
    }

    public RunicEnergyCost getCost() {
        return cost;
    }
}
