package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.AbilityClasses.DisarmAbility;
import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.magic_items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.registries.effects.EffectsRegister;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class QualadiumSword extends SwordItem {
    public QualadiumSword(IItemTier tier,Properties p_i48460_4_) {
        super(tier, 2, -2.4f, p_i48460_4_);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level.random.nextFloat() >= 0.8){
            stack.hurt(-2,attacker.level.random,null);

        }
        target.setSecondsOnFire(8);
        return super.hurtEnemy(stack, target, attacker);
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide){
            AxisAlignedBB box = new AxisAlignedBB(-4,-1,-4,4,1,4).move(player.position());
            for (LivingEntity a : world.getEntitiesOfClass(LivingEntity.class,box,(entity) -> !entity.equals(player) )){
                a.addEffect(new EffectInstance(SolarForge.SOLAR_STUN.get(),160,0));

            }
            player.getCooldowns().addCooldown(this,600);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {

        p_77624_3_.add(new TranslationTextComponent("solarforge.qualaium_sword").withStyle(TextFormatting.GOLD));


        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
