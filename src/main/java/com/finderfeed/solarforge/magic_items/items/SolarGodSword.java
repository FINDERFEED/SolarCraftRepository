package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.misc_things.ITagUser;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SolarGodSword extends SwordItem implements ITagUser {
    public SolarGodSword(IItemTier p_i48460_1_, int p_i48460_2_, float p_i48460_3_, Properties p_i48460_4_) {
        super(p_i48460_1_, p_i48460_2_, p_i48460_3_, p_i48460_4_);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean inhand) {
        if (!world.isClientSide){
            if (stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG) == null){
                stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG);
                stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,1);
            }
        }

        super.inventoryTick(stack, world, entity, slot, inhand);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
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
                    return ActionResult.success(player.getItemInHand(hand));
                }

            }
        }
        return super.use(world,player,hand);
    }

    @Override
    public boolean verifyTagAfterLoad(CompoundNBT p_179215_1_) {
        return true;
    }



    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
        if (getSwordLevel(stack) >= 2){
            p_77644_2_.hurt(DamageSource.MAGIC,5);
        }
        return super.hurtEnemy(stack, p_77644_2_, p_77644_3_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag flag) {
        if ((stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG) != null)) {

            int level = stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG);
            text.add(new TranslationTextComponent("solarcraft.solar_god_sword_desc").append(String.valueOf(level)).withStyle(TextFormatting.GOLD));
            if (level >= 2){
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_2").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC));

            }else{
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_2").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.STRIKETHROUGH));
            }

            if (level >= 3){
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_3").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC));

            }else{
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_3").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.STRIKETHROUGH));
            }

            if (level >= 4){
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_4").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC));

            }else{
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_4").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.STRIKETHROUGH));
            }

            if (level >= 5){
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_5").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC));
            }else{
                text.add(new TranslationTextComponent("solarcraft.solar_god_sword_level_5").withStyle(TextFormatting.GOLD).withStyle(TextFormatting.ITALIC).withStyle(TextFormatting.STRIKETHROUGH));
            }

        }
        super.appendHoverText(stack, world, text, flag);
    }

    @Override
    public void doThingsWithTag(ItemStack prev,ItemStack stack,String tag) {
        if (prev.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG)+1 <= 5) {
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,
                    prev.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG) + 1);
        }else{
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,
                    prev.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG));
        }
    }


    public int getSwordLevel(ItemStack stack){
        return stack.getTagElement(SolarCraftTags.SOLAR_GOD_SWORD_TAG).getInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG);
    }
}
