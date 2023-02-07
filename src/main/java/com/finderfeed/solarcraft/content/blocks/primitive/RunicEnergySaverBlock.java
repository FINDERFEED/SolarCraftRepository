package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.IRunicEnergySaver;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class RunicEnergySaverBlock extends Block {

    private static final List<RunicEnergy.Type> ALL = List.of(
            RunicEnergy.Type.ARDO,
            RunicEnergy.Type.KELDA,
            RunicEnergy.Type.URBA,
            RunicEnergy.Type.ZETA,
            RunicEnergy.Type.TERA,
            RunicEnergy.Type.FIRA,
            RunicEnergy.Type.ULTIMA,
            RunicEnergy.Type.GIRO
    );

    public RunicEnergySaverBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder context) {
        ItemStack stack = new ItemStack(this.asItem());
        if (context.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof AbstractRunicEnergyContainer container){
            IRunicEnergySaver.defaultSave(stack,container);
        }
        List<ItemStack> items = new ArrayList<>();
        items.add(stack);
        return items;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity e, ItemStack stack) {
        super.setPlacedBy(level, pos, state, e, stack);
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof AbstractRunicEnergyContainer container){
            IRunicEnergySaver.defaultLoad(stack,container);
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter getter, List<Component> components, TooltipFlag flag) {
        IRunicEnergySaver.addHoverText(stack,components,getTooltipEnergies());
        super.appendHoverText(stack, getter, components, flag);
    }

    public List<RunicEnergy.Type> getTooltipEnergies(){
        return ALL;
    }
}
