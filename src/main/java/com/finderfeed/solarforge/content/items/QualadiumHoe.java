package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
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
import java.util.function.Supplier;

import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;

public class QualadiumHoe extends IllidiumHoe{
    public QualadiumHoe(Tier p_i231595_1_, int p_i231595_2_, float p_i231595_3_, Properties p_i231595_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i231595_1_, p_i231595_2_, p_i231595_3_, p_i231595_4_,fragmentSupplier);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (ctx.getPlayer().isCrouching()){
            if (ctx.getLevel().getBlockState(ctx.getClickedPos()).getBlock() instanceof BonemealableBlock &&
                    ItemRunicEnergy.isEnough(this.getCost(),ctx.getItemInHand(),this,ctx.getPlayer())){
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
    public void appendHoverText(ItemStack item, @Nullable Level world, List<Component> components, TooltipFlag p_77624_4_) {
        components.add(new TranslatableComponent("solarforge.qualadium_hoe").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(item, world, components, p_77624_4_);
    }
}
