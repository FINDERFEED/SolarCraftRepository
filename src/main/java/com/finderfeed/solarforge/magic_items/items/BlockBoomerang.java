package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.magic_items.items.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;

import cpw.mods.jarhandling.impl.SecureJarVerifier;
import net.minecraft.world.item.TooltipFlag;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class BlockBoomerang extends Item {

    public BlockBoomerang(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide){

            if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof BlockItem) {
                BlockItem item = (BlockItem) player.getItemInHand(InteractionHand.OFF_HAND).getItem();

                BlockBoomerangProjectile proj = new BlockBoomerangProjectile(Projectiles.BLOCK_BOOMERANG.get(), world);
                proj.setPos(player.getX() + player.getLookAngle().x * 2, player.getY()+1.5 + player.getLookAngle().y, player.getZ() + player.getLookAngle().z * 2);
                proj.setOwner(player.getUUID());
                proj.setBlockToPlace(item.getBlock());
                player.getItemInHand(InteractionHand.OFF_HAND).grow(-1);
                proj.setDeltaMovement(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
                world.addFreshEntity(proj);
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
        }
        return super.use(world, player, hand);
    }



    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslatableComponent("solarforge.block_boomerang").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
