package com.finderfeed.solarforge.magic.items.solar_disc_gun;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.items.primitive.solacraft_item_classes.SolarcraftItem;
import com.finderfeed.solarforge.magic.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class SolarDiscGunItem extends SolarcraftItem implements ManaConsumer {
    public SolarDiscGunItem(Properties p_i48487_1_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48487_1_,fragmentSupplier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {

        if (!p_77659_1_.isClientSide && Helpers.canCast(p_77659_2_,getManacost())){
            SolarDiscProjectile projectile = new SolarDiscProjectile(EntityTypes.SOLAR_DISC.get(),p_77659_1_);
            projectile.setPos(p_77659_2_.position().x,p_77659_2_.position().y+1.4,p_77659_2_.position().z);
            projectile.setDeltaMovement(p_77659_2_.getLookAngle().multiply(1.5,1.5,1.5));

            projectile.pitch = p_77659_2_.getXRot();
            projectile.yaw = p_77659_2_.yHeadRot;


            p_77659_1_.addFreshEntity(projectile);
            Helpers.spendMana(p_77659_2_,getManacost());
        }

        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslatableComponent("solar_disk.launcher").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public double getManacost() {
        return 20;
    }
}
