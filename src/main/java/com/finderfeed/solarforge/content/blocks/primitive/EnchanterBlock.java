package com.finderfeed.solarforge.content.blocks.primitive;

import com.finderfeed.solarforge.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarforge.content.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarforge.content.blocks.blockentities.containers.EnchanterContainer;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class EnchanterBlock extends RunicEnergySaverBlock implements EntityBlock {

    private static final VoxelShape shape = Block.box(3,0,3,13,16,13);

    public EnchanterBlock(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (!level.isClientSide){
            if (level.getBlockEntity(pos) instanceof EnchanterBlockEntity enchanter && hand == InteractionHand.MAIN_HAND){


                if (player.getUUID().equals(enchanter.getOwner())){

                    enchanter.setChanged();
                    level.sendBlockUpdated(pos,state,state,3);
                    enchanter.loadConfigIfNecessary();
                    String configString = EnchanterConfigInit.SERVERSIDE_JSON.toString();
                    NetworkHooks.openScreen((ServerPlayer) player,new EnchanterContainer.Provider(pos,configString),(buf)->{
                        buf.writeBlockPos(pos);
                        buf.writeUtf(configString);
                    });
                }else {
                    player.sendSystemMessage(Component.literal("You are not the owner!").withStyle(ChatFormatting.RED));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return shape;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean smth) {
        if (world.getBlockEntity(pos) instanceof EnchanterBlockEntity enchanterBlockEntity){
            enchanterBlockEntity.onRemove();
            Containers.dropContents(world,pos,enchanterBlockEntity.wrapInContainer());
        }
        super.onRemove(state, world, pos, state2, smth);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.ENCHANTER.get().create(pos,state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (level,position,state,tile)->{
            EnchanterBlockEntity.tick(level,state,position,(EnchanterBlockEntity) tile);
        };
    }
}
