package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.content.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.content.items.primitive.RareSolarcraftSword;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.ITagUser;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SolarGodSword extends RareSolarcraftSword implements ITagUser {
    public SolarGodSword(Tier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_,fragmentSupplier);
    }


    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean inhand) {
        if (!world.isClientSide){
            if (stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG) == null){
                stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG);
                stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,1);
            }
        }

        super.inventoryTick(stack, world, entity, slot, inhand);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack inhand = player.getItemInHand(hand);
            if (inhand.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG) != null) {
                int level = getSwordLevel(inhand);
                if (level >= 3) {
                    AbstractTurretProjectile.Constructor constructor = new AbstractTurretProjectile.Constructor()
                            .setPosition(player.position().add(0, 1.3, 0))
                            .setVelocity(player.getLookAngle().multiply(2,2,2))
                            .setDamage(5);
                    if (level >= 4){
                        constructor.editDamage(10);
                    }
                    if (level >= 5){
                        constructor.editExplosionPower(3);
                    }
                    AbstractTurretProjectile proj = new AbstractTurretProjectile(world,constructor);
                    world.addFreshEntity(proj);
                    player.getCooldowns().addCooldown(this,200);
                    return InteractionResultHolder.success(player.getItemInHand(hand));
                }

            }
        }
        return super.use(world,player,hand);
    }




    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if ((getSwordLevel(stack) >= 2) && (attacker instanceof Player player)){
            target.invulnerableTime = 0;
            target.hurt(DamageSource.playerAttack(player).setMagic().bypassArmor(),5);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> text, TooltipFlag flag) {
        if ((stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG) != null)) {

            int level = stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG);
            text.add(new TranslatableComponent("solarcraft.solar_god_sword_desc").append(String.valueOf(level)).withStyle(ChatFormatting.GOLD));
            if (level >= 2){
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_2").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));

            }else{
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_2").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
            }

            if (level >= 3){
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_3").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));

            }else{
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_3").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
            }

            if (level >= 4){
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_4").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));

            }else{
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_4").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
            }

            if (level >= 5){
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_5").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
            }else{
                text.add(new TranslatableComponent("solarcraft.solar_god_sword_level_5").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.STRIKETHROUGH));
            }

        }
        super.appendHoverText(stack, world, text, flag);
    }

    @Override
    public void doThingsWithTag(ItemStack prev,ItemStack stack,String tag) {
        if (stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG) == null){
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG);
            stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,1);
        }
        if (prev.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG)+1 <= 5) {
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,
                    prev.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG) + 1);
        }else{
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,
                    prev.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG));
        }
    }

    @Override
    public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
        super.fillItemCategory(p_41391_, p_41392_);
        if (this.allowdedIn(p_41391_)) {
            ItemStack sword = new ItemStack(this);
            sword.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,5);
            p_41392_.add(sword);
        }
    }

    public int getSwordLevel(ItemStack stack){
        return stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG);
    }
}
