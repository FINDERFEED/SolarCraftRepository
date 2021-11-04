package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import net.minecraft.world.item.Item;

public class RareSolarcraftItem extends Item implements ICustomTooltip {
    public static final CustomTooltip SOLARCRAFT_ITEM = new CustomTooltip("solarcraft_item_tooltip",
            58,13,
            46,9,
            6,
            0xFFE2C60B, 0xFFBF6A09,0xf0100010);

    public RareSolarcraftItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public CustomTooltip getTooltip() {
        return SOLARCRAFT_ITEM;
    }
}
