package com.finderfeed.solarcraft.content.items.primitive;

import com.finderfeed.solarcraft.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarcraft.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;

import java.util.function.Supplier;

public class RareSolarcraftItem extends SolarcraftItem implements ICustomTooltip {
    public static final CustomTooltip SOLARCRAFT_ITEM = new CustomTooltip("solarcraft_item_tooltip",
            58,13,
            46,9,
            6,
            0xFFE2C60B, 0xFFBF6A09,0xf0100010);

    public RareSolarcraftItem(Properties p_41383_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_41383_,fragmentSupplier);
    }

    @Override
    public CustomTooltip getTooltip() {
        return SOLARCRAFT_ITEM;
    }
}
