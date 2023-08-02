package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.content.entities.projectiles.ThrownLightProjectile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ThrownLight extends BlockItem {


    public ThrownLight(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND){
            ThrownLightProjectile projectile = new ThrownLightProjectile(SCEntityTypes.THROWN_LIGHT.get(),level);
            projectile.setPos(Helpers.getPlayerShootPos(player));
//            projectile.setDeltaMovement(player.getLookAngle());
            projectile.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F, 1.5F, 0.0F);
            level.addFreshEntity(projectile);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
        }
        return super.use(level, player, hand);
    }


}
