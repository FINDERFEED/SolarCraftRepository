package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.content.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftSword;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public class IllidiumSword extends RareSolarcraftSword {
    public IllidiumSword(Item.Properties properties) {
        super(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER, 3, -2.4F,properties ,()-> AncientFragment.ILLIDIUM_SWORD);
    }

    @Nonnull
    @Override
    public AABB getSweepHitBox(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull Entity target) {
        return super.getSweepHitBox(stack, player, target);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_77644_1_, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
        if (p_77644_3_.level.random.nextDouble() > 0.9) {
            p_77644_1_.hurt(-1, p_77644_3_.level.random, null);

        }

        p_77644_2_.setSecondsOnFire(4);

        return super.hurtEnemy(p_77644_1_, p_77644_2_, p_77644_3_);
    }


    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(Component.translatable("illidium.sword").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
