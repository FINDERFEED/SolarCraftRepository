package com.finderfeed.solarforge.magic_items.items.primitive;

import com.finderfeed.solarforge.client.custom_tooltips.CustomTooltip;
import com.finderfeed.solarforge.client.custom_tooltips.ICustomTooltip;
import com.finderfeed.solarforge.magic_items.items.primitive.solacraft_item_classes.SolarcraftHoe;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public class RareSolarcraftHoe extends SolarcraftHoe implements ICustomTooltip {
    public RareSolarcraftHoe(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_41336_, p_41337_, p_41338_, p_41339_,fragmentSupplier);
    }

    @Override
    public CustomTooltip getTooltip() {
        return RareSolarcraftItem.SOLARCRAFT_ITEM;
    }
}
