package com.finderfeed.solarforge.infusing_table_things;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.items.SolarNetworkBinder;
import com.finderfeed.solarforge.solar_forge_block.SolarForgeBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Consumer;

public class InfusingTableBlock extends Block {
    public InfusingTableBlock(Properties prop) {
        super(prop);
    }


    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    }
    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return SolarForge.INFUSING_STAND_BLOCKENTITY.get().create();
    }

    @Override
    public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        TileEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

        if (te instanceof InfusingTableTileEntity){
            InfusingTableTileEntity ent = (InfusingTableTileEntity) te;
            ItemStack stacks = ent.getItems().get(0);
            popResource(p_196243_2_,p_196243_3_,stacks);
            stacks = ent.getItems().get(9);
            popResource(p_196243_2_,p_196243_3_,stacks);
        }
        super.onRemove(p_196243_1_, p_196243_2_, p_196243_3_, p_196243_4_, p_196243_5_);
    }


    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity user, Hand hand, BlockRayTraceResult rayTraceResult) {

        if (!world.isClientSide()) {

            TileEntity entity = world.getBlockEntity(pos);
            if (entity instanceof InfusingTableTileEntity) {
                InfusingTableTileEntity tile = (InfusingTableTileEntity) entity;
                if (!(user.getMainHandItem().getItem() instanceof SolarWandItem) && !(user.getMainHandItem().getItem() instanceof SolarNetworkBinder)) {
                    Consumer<PacketBuffer> cons = x -> { x.writeBlockPos(pos);
                    };
                    NetworkHooks.openGui((ServerPlayerEntity) user, (InfusingTableTileEntity) entity, cons);

                }

            };
        }
        if ((user.getItemInHand(hand).getItem() instanceof  SolarWandItem) || (user.getItemInHand(hand).getItem() instanceof  SolarNetworkBinder) ){
            return super.use(state,world,pos,user,hand,rayTraceResult);
        }else{
            return ActionResultType.CONSUME;
        }

    }
}
