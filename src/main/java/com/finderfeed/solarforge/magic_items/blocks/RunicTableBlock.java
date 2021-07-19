package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.unlockables.ProgressionHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class RunicTableBlock extends Block {
    public RunicTableBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.RUNIC_TABLE_TILE.get().create();
    }


    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity pe, Hand hand, BlockRayTraceResult trace) {
        if (!world.isClientSide && (world.getBlockEntity(pos) instanceof RunicTableTileEntity) ){
            if (!ProgressionHelper.doPlayerAlreadyHasPattern(pe)){
                ProgressionHelper.generateRandomPatternForPlayer(pe);
            }
                NetworkHooks.openGui((ServerPlayerEntity) pe, (RunicTableTileEntity) world.getBlockEntity(pos),
                        (buf) -> {
                            buf.writeBlockPos(pos);
                            for (int a : ProgressionHelper.getPlayerPattern(pe)){
                                buf.writeInt(a);
                            }
                        }
                );
                return ActionResultType.SUCCESS;
        }
        return super.use(state, world, pos, pe, hand, trace);
    }
}
