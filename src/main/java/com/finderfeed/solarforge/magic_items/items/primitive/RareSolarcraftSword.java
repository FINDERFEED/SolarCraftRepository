package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.magic_items.items.primitive.solacraft_item_classes.SolarcraftSword;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public class RareSolarcraftSword extends SolarcraftSword implements ICustomTooltip {
    public RareSolarcraftSword(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_43269_, p_43270_, p_43271_, p_43272_,fragmentSupplier);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
