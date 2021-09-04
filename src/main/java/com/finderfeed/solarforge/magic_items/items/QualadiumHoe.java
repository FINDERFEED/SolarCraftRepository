package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.item.TooltipFlag;

import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class QualadiumHoe extends IllidiumHoe{
    public QualadiumHoe(Tier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Properties p_i231595_4_) {
        super(p_i231595_1_, p_i231595_2_, p_i231595_3_, p_i231595_4_);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (ctx.getPlayer().isCrouching()){
            if (ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock() instanceof BonemealableBlock && Helpers.canCast(ctx.getPlayer(), getManacost())){
                for (BlockPos pos : Helpers.getSurroundingBlockPositionsHorizontal(ctx.getClickedPos())) {
                    if  (ctx.getLevel().getBlockState(pos).getBlock() instanceof BonemealableBlock) {

                        BoneMealItem.applyBonemeal(Items.BONE_MEAL.getDefaultInstance(), ctx.getLevel(), pos, ctx.getPlayer());
                        ctx.getLevel().sendBlockUpdated(pos.above(),ctx.getLevel().getBlockState(pos.above()),ctx.getLevel().getBlockState(pos.above()),3);
                    }
                }
            }
        }
        return super.useOn(ctx);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslatableComponent("solarforge.qualadium_hoe").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
