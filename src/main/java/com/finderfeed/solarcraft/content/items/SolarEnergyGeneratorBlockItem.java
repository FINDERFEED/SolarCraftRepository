package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftBlockItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class SolarEnergyGeneratorBlockItem extends RareSolarcraftBlockItem {

    public SolarEnergyGeneratorBlockItem(Item.Properties p) {
        super(SolarcraftBlocks.SOLAR_ENERGY_GENERATOR.get(),p,()-> AncientFragment.SOLAR_ENERGY_GENERATOR);
    }

    @Override
    public void inventoryTick(ItemStack item, Level level, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        if ((!level.isClientSide) && (entity instanceof Player) ){
            Helpers.fireProgressionEvent((Player) entity, Progression.CRAFT_SOLAR_ENERGY_GENERATOR);
        }
        super.inventoryTick(item, level, entity, p_77663_4_, p_77663_5_);
    }
}
