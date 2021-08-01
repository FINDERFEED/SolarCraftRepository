package com.finderfeed.solarforge.magic_items.runic_network.repeater;

import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.runic_network.algorithms.FindingAlgorithms;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO:Runic energy network
public class RunicNetworkRepeater extends Block implements EntityBlock {
    public RunicNetworkRepeater(Properties p_49795_) {
        super(p_49795_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return TileEntitiesRegistry.REPEATER.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            BaseRepeaterTile.tick(level,blockPos,blockState,(BaseRepeaterTile) t);
        });
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult res) {
        if (!level.isClientSide && hand.equals(InteractionHand.MAIN_HAND)){
            if (level.getBlockEntity(blockPos) instanceof BaseRepeaterTile tile){
                Map<BlockPos, List<BlockPos>> graph = FindingAlgorithms.findAllConnectedPylons(tile,new ArrayList<>(),new HashMap<>());

                FindingAlgorithms.sortBestPylon(graph,level);
                List<BlockPos> bestWay = FindingAlgorithms.findConnectionAStar(graph,tile.getBlockPos(),level);
                for (int i = 0; i < bestWay.size();i++){
                    ((ServerLevel)level).sendParticles(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),bestWay.get(i).getX(),bestWay.get(i).getY()+1,bestWay.get(i).getZ(),1,0,0,0,0);
                }
//                bestWay.forEach((bp)->{
//                    ((ServerLevel)level).sendParticles(ParticlesList.SOLAR_STRIKE_PARTICLE.get(),bp.getX(),bp.getY()+1,bp.getZ(),1,0,0,0,0);
//                });
                System.out.println(bestWay);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
