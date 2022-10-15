package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftSword;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class QualadiumSword extends RareSolarcraftSword {
    public QualadiumSword(Tier tier, Properties p_i48460_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(tier, 2, -2.4f, p_i48460_4_,fragmentSupplier);
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide){
            AABB box = new AABB(-4,-1,-4,4,1,4).move(player.position());
            for (LivingEntity a : world.getEntitiesOfClass(LivingEntity.class,box,(entity) -> !entity.equals(player) )){
                a.addEffect(new MobEffectInstance(SolarCraft.SOLAR_STUN.get(),160,0));

            }
            player.getCooldowns().addCooldown(this,600);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {

        p_77624_3_.add(Component.translatable("solarcraft.qualaium_sword").withStyle(ChatFormatting.GOLD));


        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
