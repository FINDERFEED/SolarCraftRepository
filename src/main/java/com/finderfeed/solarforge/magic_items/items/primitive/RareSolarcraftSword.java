package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class RareSolarcraftSword extends SwordItem implements ICustomTooltip {
    public RareSolarcraftSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
