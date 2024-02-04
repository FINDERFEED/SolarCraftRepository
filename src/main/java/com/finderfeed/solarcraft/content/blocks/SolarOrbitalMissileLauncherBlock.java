package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarOrbitalMissileLauncherTileEntity;
import com.finderfeed.solarcraft.content.blocks.primitive.RunicEnergySaverBlock;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SolarOrbitalMissileLauncherBlock extends RunicEnergySaverBlock implements EntityBlock {

    public SolarOrbitalMissileLauncherBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (hand == InteractionHand.MAIN_HAND){
            if (level.isClientSide){
                ClientHelpers.openOrbitalMissileLauncherScreen(pos);
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState p_60515_, Level world, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        if (world instanceof ServerLevel sLevel){
            Helpers.loadChunkAtPos(sLevel,pos,false,true);
        }
        super.onRemove(p_60515_, world, pos, p_60518_, p_60519_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SCTileEntities.ORBITAL_MISSILE_LAUNCHER.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (a,b,c,d)->{
            SolarOrbitalMissileLauncherTileEntity.tick((SolarOrbitalMissileLauncherTileEntity) d);
        };
    }
}
