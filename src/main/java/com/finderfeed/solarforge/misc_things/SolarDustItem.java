package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.content.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;

import java.util.function.Supplier;

public class SolarDustItem extends SolarcraftItem {

    public SolarDustItem(Properties p_i48487_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48487_1_,fragmentSupplier);
    }

//    @Override
//    public void inventoryTick(ItemStack p_77663_1_, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
//        if (!p_77663_2_.isClientSide && p_77663_3_ instanceof Player){
//            Player entity = (Player) p_77663_3_;
//
//            Helpers.fireProgressionEvent(entity, Progression.ACQUIRE_SOLAR_DUST);
//        }
//        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
//    }



}
