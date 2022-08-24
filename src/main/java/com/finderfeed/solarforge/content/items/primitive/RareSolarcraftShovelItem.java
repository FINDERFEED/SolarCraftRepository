package com.finderfeed.solarforge.content.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.content.items.primitive.solacraft_item_classes.SolarcraftShovel;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public class RareSolarcraftShovelItem extends SolarcraftShovel implements ICustomTooltip {
    public RareSolarcraftShovelItem(Tier p_43114_, float p_43115_, float p_43116_, Properties p_43117_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_43114_, p_43115_, p_43116_, p_43117_,fragmentSupplier);
    }


    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
