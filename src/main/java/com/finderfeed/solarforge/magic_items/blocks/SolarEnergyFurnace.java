package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.SolarEnergyFurnaceTile;
import com.finderfeed.solarforge.magic_items.items.SolarNetworkBinder;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class SolarEnergyFurnace extends GlazedTerracottaBlock {

    public SolarEnergyFurnace(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.SOLAR_FURNACE_TILE_ENTITY.get().create();
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World world, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
            if (!world.isClientSide && (world.getBlockEntity(p_225533_3_) instanceof SolarEnergyFurnaceTile) ){
                if (!(p_225533_4_.getMainHandItem().getItem() instanceof SolarNetworkBinder)) {
                    NetworkHooks.openGui((ServerPlayerEntity) p_225533_4_, (SolarEnergyFurnaceTile) world.getBlockEntity(p_225533_3_),
                            (buf) -> buf.writeBlockPos(p_225533_3_)
                    );
                    return ActionResultType.SUCCESS;
                }
            }
        return super.use(p_225533_1_, world, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean hz) {
        TileEntity test = world.getBlockEntity(pos);
        if (!world.isClientSide && (test instanceof SolarEnergyFurnaceTile)){
            InventoryHelper.dropContents(world,pos,(SolarEnergyFurnaceTile) test);
        }
        super.onRemove(state, world, pos, state2, hz);
    }
}
