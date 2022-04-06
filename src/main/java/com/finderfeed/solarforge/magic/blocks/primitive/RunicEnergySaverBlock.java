package com.finderfeed.solarforge.magic.blocks.primitive;

import com.finderfeed.solarforge.magic.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarforge.magic.blocks.blockentities.runic_energy.IRunicEnergySaver;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class RunicEnergySaverBlock extends Block {

    public RunicEnergySaverBlock(Properties p_49795_) {
        super(p_49795_);
    }


//    @Override
//    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState idk, boolean idkToo) {
//        super.onRemove(state, level, pos, idk, idkToo);
//        if (!level.isClientSide && level.getBlockEntity(pos) instanceof AbstractRunicEnergyContainer container){
//            ItemStack stack = new ItemStack(this.asItem());
//            IRunicEnergySaver.defaultSave(stack,container);
//            ItemEntity entity = new ItemEntity(level,pos.getX() + 0.5,pos.getY() + 0.5,pos.getZ() + 0.5,stack);
//            level.addFreshEntity(entity);
//        }
//    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder context) {
        ItemStack stack = new ItemStack(this.asItem());
        if (context.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof AbstractRunicEnergyContainer container){
            IRunicEnergySaver.defaultSave(stack,container);
        }
        return List.of(stack);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity e, ItemStack stack) {
        super.setPlacedBy(level, pos, state, e, stack);
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof AbstractRunicEnergyContainer container){
            IRunicEnergySaver.defaultLoad(stack,container);
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> components, TooltipFlag p_49819_) {
        IRunicEnergySaver.addHoverText(stack,components);
        super.appendHoverText(stack, getter, components, p_49819_);
    }
}
