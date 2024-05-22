package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftAxe;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.BlockTags;

import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class IllidiumAxe extends RareSolarcraftAxe {
    public IllidiumAxe(Tier p_i48530_1_, float p_i48530_2_, float p_i48530_3_, Properties p_i48530_4_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48530_1_, p_i48530_2_, p_i48530_3_, p_i48530_4_,fragmentSupplier);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity player) {
        if (state.is(BlockTags.LOGS) && (world.random.nextFloat() <= SCConfigs.ITEMS.illidiumAxeCharcoalDropChance)) {
            world.playSound(null,pos.getX(),pos.getY(),pos.getZ(), SoundEvents.GENERIC_BURN, SoundSource.AMBIENT,0.1f,1f);
            world.addFreshEntity(new ItemEntity(world,pos.getX()+0.5,pos.getY(),pos.getZ()+0.5, Items.CHARCOAL.getDefaultInstance()));
        }

        return super.mineBlock(stack, world, state, pos, player);
    }


    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        p_77624_3_.add(Component.translatable("solarcraft.illidium_axe").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
