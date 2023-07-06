package com.finderfeed.solarcraft.local_library.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TileEntityDataSaverBlock extends Block {

    private String modid;

    public TileEntityDataSaverBlock(Properties props,String modid) {
        super(props);
        this.modid = modid;
    }


    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder ctx) {

        List<ItemStack> stacks = super.getDrops(state,ctx);
        for (ItemStack stack : stacks){
            if (stack.getItem() == this.asItem()){
                BlockEntity tile = ctx.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
                if (tile != null){
                    CompoundTag tag = tile.saveWithoutMetadata();
                    stack.getOrCreateTagElement(modid + "_tile_saved_data").put("data",tag);
                }
                break;
            }
        }

        return stacks;
    }


    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity living, ItemStack stack) {
        if (!world.isClientSide) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile != null) {
                tile.load(stack.getOrCreateTagElement(modid + "_tile_saved_data").getCompound("data"));
            }
        }
        super.setPlacedBy(world, pos, state, living, stack);
    }


    @Nullable
    public CompoundTag getTileTag(ItemStack stack){
        CompoundTag t = stack.getTagElement(modid + "_tile_saved_data");
        return t == null ? t : t.getCompound("data");
    }
}
