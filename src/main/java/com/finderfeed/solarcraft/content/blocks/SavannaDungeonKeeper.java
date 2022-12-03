package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.SavannaDungeonKeeperTile;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SavannaDungeonKeeper extends Block implements EntityBlock{
    public SavannaDungeonKeeper(Properties props) {
        super(props);
    }

        @Nullable
        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return SolarcraftTileEntityTypes.SAVANNA_DUNGEON_KEEPER.get().create(pos,state);
        }

        @Nullable
        @Override
        public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
            return (a,b,c,d)->{
                SavannaDungeonKeeperTile.tick((SavannaDungeonKeeperTile) d,a,c,b);
            };
        }


}
