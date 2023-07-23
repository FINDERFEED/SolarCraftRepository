package com.finderfeed.solarcraft.content.items;


import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.IImbuableItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RuneBase extends Item implements IImbuableItem {

    private int energyPerRune = 10;

    public RuneBase(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public double getCost() {
        return 10;
    }

    @Override
    public int getImbueTime() {
        return 300;
    }

    @Override
    public boolean imbue(ItemEntity entity, RuneEnergyPylonTile tile) {
        if (entity.getItem().getItem() == this){
            setCurrentTime(entity,getCurrentTime(entity)+1);
            int flag = getCurrentTime(entity);
            IImbuableItem item = (IImbuableItem) entity.getItem().getItem();
            int maxTime = item.getImbueTime();
            double neededEnergy = item.getCost();

            if (flag >= maxTime) {
                ItemStack stack = entity.getItem();
                int maxRunes = (int) Math.floor(tile.getCurrentEnergy() / neededEnergy);
                if (maxRunes > stack.getCount()) {
                    ItemEntity entity1 = new ItemEntity(tile.getLevel(), entity.position().x, entity.position().y, entity.position().z,
                            new ItemStack(AncientFragmentHelper.RUNES_MAP.get(tile.getEnergyType()), stack.getCount()));
                    tile.getLevel().addFreshEntity(entity1);
                    entity.remove(Entity.RemovalReason.DISCARDED);

                } else {
                    ItemEntity entity1 = new ItemEntity(tile.getLevel(), entity.position().x, entity.position().y, entity.position().z,
                            new ItemStack(AncientFragmentHelper.RUNES_MAP.get(tile.getEnergyType()), maxRunes));
                    tile.getLevel().addFreshEntity(entity1);
                    entity.getItem().setCount(stack.getCount() - maxRunes);
                    entity.getPersistentData().putInt(SolarCraftTags.IMBUE_TIME_TAG, 0);
                }
                if (entity.getOwner() instanceof Player player) {

                        Helpers.fireProgressionEvent(player, Progression.SOLAR_RUNE);
                }
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
