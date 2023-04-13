package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamReflectorTile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BeamReflectorBlock extends Block implements EntityBlock {

    public BeamReflectorBlock(Properties p_49795_) {
        super(p_49795_);

    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof BeamReflectorTile tile && hand == InteractionHand.MAIN_HAND){
            if (!player.isCrouching()) {
                tile.onUse(res.getDirection());
                player.swing(hand);
            }else{
//                tile.getDirections().add(res.getDirection());
//                Helpers.updateTile(tile);
            }
        }
        return super.use(state, level, pos, player, hand, res);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.BEAM_REFLECTOR.get().create(pos,state);
    }
}
