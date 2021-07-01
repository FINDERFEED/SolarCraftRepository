package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class IllidiumSword extends SwordItem {
    public IllidiumSword() {
        super(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER, 3, -2.4F, (new Item.Properties()).stacksTo(1).tab(SolarForge.SOLAR_GROUP_TOOLS).rarity(Rarity.RARE).fireResistant());
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
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("illidium.sword").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
