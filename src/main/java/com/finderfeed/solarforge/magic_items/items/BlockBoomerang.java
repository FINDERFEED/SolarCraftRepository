package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.magic_items.items.projectiles.BlockBoomerangProjectile;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockBoomerang extends Item {

    public BlockBoomerang(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide){
            if (player.getItemInHand(Hand.OFF_HAND).getItem() instanceof BlockItem) {
                BlockItem item = (BlockItem) player.getItemInHand(Hand.OFF_HAND).getItem();

                BlockBoomerangProjectile proj = new BlockBoomerangProjectile(Projectiles.BLOCK_BOOMERANG.get(), world);
                proj.setPos(player.getX() + player.getLookAngle().x * 2, player.getY()+1.5 + player.getLookAngle().y, player.getZ() + player.getLookAngle().z * 2);
                proj.setOwner(player.getUUID());
                proj.setBlockToPlace(item.getBlock());
                player.getItemInHand(Hand.OFF_HAND).grow(-1);
                proj.setDeltaMovement(player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
                world.addFreshEntity(proj);
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
        }
        return super.use(world, player, hand);
    }



    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarforge.block_boomerang").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
