package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class RareSolarcraftHoe extends HoeItem implements ICustomTooltip {
    public RareSolarcraftHoe(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_) {
        super(p_41336_, p_41337_, p_41338_, p_41339_);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
