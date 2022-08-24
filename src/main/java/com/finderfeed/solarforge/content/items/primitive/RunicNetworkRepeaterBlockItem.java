package com.finderfeed.solarforge.content.items.primitive;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.content.items.primitive.solacraft_item_classes.SolarcraftBlockItem;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class RunicNetworkRepeaterBlockItem extends SolarcraftBlockItem {
    public RunicNetworkRepeaterBlockItem(Block p_40565_, Properties p_40566_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_40565_, p_40566_,fragmentSupplier);
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level world, Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (!world.isClientSide){
            if (p_41406_ instanceof Player player) {
                if (world.getGameTime() % 20 ==0 ) {
                    Helpers.fireProgressionEvent(player, Progression.RUNIC_ENERGY_REPEATER);
                }
            }
        }
        super.inventoryTick(p_41404_, world, p_41406_, p_41407_, p_41408_);
    }
}
