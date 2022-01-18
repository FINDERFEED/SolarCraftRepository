package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.magic_items.items.primitive.solacraft_item_classes.SolarcraftBlockItem;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class RareSolarcraftBlockItem extends SolarcraftBlockItem implements ICustomTooltip {
    public RareSolarcraftBlockItem(Block p_40565_, Properties p_40566_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40565_, p_40566_,fragmentSupplier);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
