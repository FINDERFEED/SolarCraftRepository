package com.finderfeed.solarforge.content.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.content.items.primitive.solacraft_item_classes.SolarcraftShield;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;

import java.util.function.Supplier;

public class RareSolarcraftShieldItem extends SolarcraftShield implements ICustomTooltip {
    public RareSolarcraftShieldItem(Properties p_43089_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_43089_,fragmentSupplier);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
