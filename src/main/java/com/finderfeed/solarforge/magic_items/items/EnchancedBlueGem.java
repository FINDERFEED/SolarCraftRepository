package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchancedBlueGem extends ItemWithGlint{

    public EnchancedBlueGem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!p_77663_2_.isClientSide && (p_77663_3_ instanceof PlayerEntity) ){
            if (!Helpers.hasPlayerUnlocked(Achievement.TRANSMUTE_GEM,(PlayerEntity) p_77663_3_) && Helpers.canPlayerUnlock(Achievement.TRANSMUTE_GEM,(PlayerEntity) p_77663_3_)){
                Helpers.setAchievementStatus(Achievement.TRANSMUTE_GEM,(PlayerEntity) p_77663_3_,true);
                Helpers.triggerToast(Achievement.TRANSMUTE_GEM,(PlayerEntity) p_77663_3_);
                Helpers.updateProgression((ServerPlayerEntity)p_77663_3_ );
                Helpers.forceChunksReload((ServerPlayerEntity) p_77663_3_);
            }
        }
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }
}
