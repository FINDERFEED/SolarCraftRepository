package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class RareSolarcraftBlockItem extends BlockItem implements ICustomTooltip {
    public RareSolarcraftBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
