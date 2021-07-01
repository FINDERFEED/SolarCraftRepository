package com.finderfeed.solarforge.magic_items.items;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.items.projectiles.UltraCrossbowProjectile;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class UltraCrossbowItem extends Item implements ManaConsumer {

    public boolean isBeingUsed = false;

    public UltraCrossbowItem(Properties p_i50040_1_) {
        super(p_i50040_1_);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        isBeingUsed = true;
        if (!player.level.isClientSide && (player instanceof PlayerEntity) ) {
            if (count % 20 == 0){
                player.level.playSound(null,player, Sounds.CROSSBOW_CHARGING.get(), SoundCategory.AMBIENT,1f,1f);
            }

            if ((float)(72000-count)/20*3 < 120) {
                ((PlayerEntity) player).displayClientMessage(new StringTextComponent("-" + String.format("%.1f", (float) (72000 - count) / 20 * 3) + "-").withStyle(TextFormatting.GOLD), true);
            }else{
                ((PlayerEntity) player).displayClientMessage(new StringTextComponent("-" + 120.0 + "-").withStyle(TextFormatting.GOLD), true);
            }
        }

        super.onUsingTick(stack, player, count);
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity player, int remainingSeconds) {
        isBeingUsed = false;
        if (!world.isClientSide){

            if (player instanceof PlayerEntity && (Helpers.canCast((PlayerEntity) player,200))) {
            UltraCrossbowProjectile proj = new UltraCrossbowProjectile(Projectiles.ULTRA_CROSSBOW_SHOT.get(),world);
            proj.setPos(player.getX() + player.getLookAngle().x,player.getY()+1.5f+player.getLookAngle().y,player.getZ()+player.getLookAngle().z);
            player.level.playSound(null,player, Sounds.CROSSBOW_SHOOT_SOUND.get(), SoundCategory.AMBIENT,1,1);
            proj.setYAW(player.yRot);
            proj.setPITCH(player.xRot);
            if ((float)(72000 - remainingSeconds)/20*3 < 120) {
                proj.setDamage((float) (72000 - remainingSeconds) / 20 * 3);
            }else {
                proj.setDamage(120);
            }
            proj.setDeltaMovement(player.getLookAngle().multiply(4,4,4));
            world.addFreshEntity(proj);

                Helpers.spendMana((PlayerEntity) player,200 );

            }
        }
        if (player instanceof PlayerEntity && (Helpers.canCast((PlayerEntity)player,getManacost()))){
            player.push(-player.getLookAngle().x*2,-player.getLookAngle().y*2,-player.getLookAngle().z*2);
        }

        super.releaseUsing(stack,world, player, remainingSeconds);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        p_77659_2_.startUsingItem(p_77659_3_);
        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }


    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarforge.ultra_crossbow").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }


    @Override
    public double getManacost() {
        return 200;
    }
}
