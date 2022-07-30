package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.magic.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.InfusingTableTileContainer;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
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
import net.minecraftforge.network.NetworkHooks;


import javax.annotation.Nullable;

public class InfusingTableBlock extends Block implements EntityBlock {
    public InfusingTableBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.INFUSING_CRAFTING_TABLE.get().create(pos,state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {

        if (!level.isClientSide
                && hand == InteractionHand.MAIN_HAND
                && !player.getItemInHand(hand).is(SolarcraftItems.SOLAR_WAND.get())){
            BlockEntity e = level.getBlockEntity(pos);
            if (e instanceof  InfusingTableTile tile) {
                if (tile.getOwner() != null && (level.getPlayerByUUID(tile.getOwner()) == player)) {
                    NetworkHooks.openGui((ServerPlayer) player, new InfusingTableTileContainer.Provider(tile), (buf ->
                            buf.writeBlockPos(pos)
                    ));
                }else {
                    player.sendMessage(new TextComponent("You are not the owner!").withStyle(ChatFormatting.RED),player.getUUID());
                }
            }
        }
        if (player.getItemInHand(hand).is(SolarcraftItems.SOLAR_WAND.get())){
               return InteractionResult.FAIL;
        }else {
            return InteractionResult.SUCCESS;
        }
    }


    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean p_51542_) {
        BlockEntity blockentity = world.getBlockEntity(pos);
        if (blockentity instanceof InfusingTableTile tile) {
            Containers.dropContents(world, pos,new PhantomInventory(tile.getInventory()));
            world.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, world, pos, state2, p_51542_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return (level,pos,state,tile)->{
            InfusingTableTile.tick(level,pos,state,(InfusingTableTile) tile);
        };
    }
}
