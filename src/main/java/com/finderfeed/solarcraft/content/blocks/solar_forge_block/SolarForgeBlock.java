package com.finderfeed.solarcraft.content.blocks.solar_forge_block;

import com.finderfeed.solarcraft.SolarCraft;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;


import java.util.function.Consumer;

import net.minecraftforge.network.NetworkHooks;


import javax.annotation.Nullable;

public class SolarForgeBlock extends Block implements EntityBlock {




    public SolarForgeBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public void onRemove(BlockState p_196243_1_, Level p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        BlockEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

        if (te instanceof SolarForgeBlockEntity){
            SolarForgeBlockEntity ent = (SolarForgeBlockEntity) te;
            ItemStack stacks = ent.getItems().get(0);
            popResource(p_196243_2_,p_196243_3_,stacks);
            stacks = ent.getItems().get(1);
            popResource(p_196243_2_,p_196243_3_,stacks);
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }





    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player user, InteractionHand hand, BlockHitResult rayTraceResult) {

        if (!world.isClientSide()) {

            BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof SolarForgeBlockEntity) {
                Consumer<FriendlyByteBuf> cons = x -> { x.writeBlockPos(pos);
                };
                NetworkHooks.openScreen((ServerPlayer) user, (SolarForgeBlockEntity) entity, cons);


            };
        }


        return InteractionResult.SUCCESS;
    }





    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarCraft.SOLAR_FORGE_BLOCKENTITY.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            SolarForgeBlockEntity.tick(level,blockPos,blockState,(SolarForgeBlockEntity) t);
        });
    }

//    @Override
//    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> list, ITooltipFlag tooltip) {
//        list.add(new StringTextComponent("ALL ENERGY IS LOST WHEN BROKEN!").withStyle(TextFormatting.RED));
//    }
}
