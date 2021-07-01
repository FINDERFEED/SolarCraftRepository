package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;
import net.minecraft.block.IGrowable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class QualadiumHoe extends IllidiumHoe{
    public QualadiumHoe(IItemTier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Properties p_i231595_4_) {
        super(p_i231595_1_, p_i231595_2_, p_i231595_3_, p_i231595_4_);
    }

    @Override
    public ActionResultType useOn(ItemUseContext ctx) {
        if (ctx.getPlayer().isCrouching()){
            if (ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock() instanceof IGrowable && Helpers.canCast(ctx.getPlayer(), getManacost())){
                for (BlockPos pos : Helpers.getSurroundingBlockPositionsHorizontal(ctx.getClickedPos())) {
                    if  (ctx.getLevel().getBlockState(pos).getBlock() instanceof IGrowable) {

                        BoneMealItem.applyBonemeal(Items.BONE_MEAL.getDefaultInstance(), ctx.getLevel(), pos, ctx.getPlayer());

                    }
                }
            }
        }
        return super.useOn(ctx);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarforge.qualadium_hoe").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
