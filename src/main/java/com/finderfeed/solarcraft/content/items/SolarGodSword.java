package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftSword;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.IUpgradable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SolarGodSword extends RareSolarcraftSword implements IUpgradable {
    public SolarGodSword(Tier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_,fragmentSupplier);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack inhand = player.getItemInHand(hand);
            int level = getItemLevel(inhand);
            if (level >= 2) {
                AbstractTurretProjectile.Constructor constructor = new AbstractTurretProjectile.Constructor()
                        .setPosition(player.position().add(0, 1.3, 0))
                        .setVelocity(player.getLookAngle().multiply(2,2,2))
                        .setDamage(5);
                if (level >= 3){
                    constructor.editDamage(10);
                }
                if (level >= 4){
                    constructor.editExplosionPower(3);
                }
                AbstractTurretProjectile proj = new AbstractTurretProjectile(world,constructor);
                world.addFreshEntity(proj);
                player.getCooldowns().addCooldown(this,200);
                return InteractionResultHolder.success(player.getItemInHand(hand));
            }


        }
        return super.use(world,player,hand);
    }




    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if ((getItemLevel(stack) >= 1) && (attacker instanceof Player player)){
            target.invulnerableTime = 0;
            target.hurt(DamageSource.playerAttack(player).setMagic().bypassArmor(),5);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> text, TooltipFlag flag) {
        addComponents(stack,text);
        super.appendHoverText(stack, world, text, flag);
    }


    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> item) {
        if (this.allowedIn(tab)) {
            ItemStack def = new ItemStack(this);
            setItemLevel(def,0);
            item.add(def);

            ItemStack sword = new ItemStack(this);
            setItemLevel(sword,getMaxUpgrades());
            item.add(sword);
        }
    }



    @Override
    public String getUpgradeTagString() {
        return SolarCraftTags.SOLAR_GOD_SWORD_TAG;
    }

    @Override
    public int getMaxUpgrades() {
        return 4;
    }

    @Override
    public List<Component> getUpgradeDescriptions() {
        return List.of(
                Component.translatable("solarcraft.solar_god_sword_level_2"),
                Component.translatable("solarcraft.solar_god_sword_level_3"),
                Component.translatable("solarcraft.solar_god_sword_level_4"),
                Component.translatable("solarcraft.solar_god_sword_level_5")
        );
    }
}
