package com.finderfeed.solarforge.magic.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.SolarcraftAxe;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public class RareSolarcraftAxe extends SolarcraftAxe implements ICustomTooltip {
    public RareSolarcraftAxe(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40521_, p_40522_, p_40523_, p_40524_,fragmentSupplier);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
