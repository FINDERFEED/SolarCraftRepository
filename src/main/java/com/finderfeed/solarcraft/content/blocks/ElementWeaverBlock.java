package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.ElementWeaverTileEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.ElementWeaverContainer;
import com.finderfeed.solarcraft.content.blocks.primitive.RunicEnergySaverBlock;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ElementWeaverBlock extends RunicEnergySaverBlock implements EntityBlock {
    public ElementWeaverBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if (player instanceof ServerPlayer serverPlayer && world.getBlockEntity(pos) instanceof ElementWeaverTileEntity tile){
            if (!player.isCrouching()){
                Helpers.updateTile(tile);
                NetworkHooks.openScreen(serverPlayer,new ElementWeaverContainer.Provider(pos), buf->{
                    buf.writeBlockPos(pos);
                });
            }else{
                tile.onUse();
            }
        }
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.ELEMENT_WEAVER.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (a,b,c,d)->{
            ElementWeaverTileEntity.tick((ElementWeaverTileEntity) d);
        };
    }
}
