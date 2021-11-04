package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import net.minecraft.world.item.ShieldItem;

public class RareSolarcraftShieldItem extends ShieldItem implements ICustomTooltip {
    public RareSolarcraftShieldItem(Properties p_43089_) {
        super(p_43089_);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
