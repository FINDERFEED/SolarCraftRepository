package com.finderfeed.solarforge.magic_items.items.solar_disc_gun;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SolarDiscGunItem extends Item implements ManaConsumer {
    public SolarDiscGunItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {

        if (!p_77659_1_.isClientSide && Helpers.canCast(p_77659_2_,getManacost())){
            SolarDiscProjectile projectile = new SolarDiscProjectile(Projectiles.SOLAR_DISC.get(),p_77659_1_);
            projectile.setPos(p_77659_2_.position().x,p_77659_2_.position().y+1.4,p_77659_2_.position().z);
            projectile.setDeltaMovement(p_77659_2_.getLookAngle().multiply(1.5,1.5,1.5));

            projectile.pitch = p_77659_2_.xRot;
            projectile.yaw = p_77659_2_.yHeadRot;


            p_77659_1_.addFreshEntity(projectile);
            Helpers.spendMana(p_77659_2_,getManacost());
        }

        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solar_disk.launcher").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public double getManacost() {
        return 20;
    }
}
