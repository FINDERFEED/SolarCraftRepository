package com.finderfeed.solarforge.magic.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.SolarcraftPickaxe;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public class RareSolarcraftPickaxe extends SolarcraftPickaxe implements ICustomTooltip {
    public RareSolarcraftPickaxe(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_42961_, p_42962_, p_42963_, p_42964_,fragmentSupplier);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
