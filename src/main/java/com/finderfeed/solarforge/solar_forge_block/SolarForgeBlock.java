package com.finderfeed.solarforge.solar_forge_block;

import com.finderfeed.solarforge.SolarForge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SolarForgeBlock extends Block {




    public SolarForgeBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public void onRemove(BlockState p_196243_1_, World p_196243_2_, BlockPos p_196243_3_, BlockState p_196243_4_, boolean p_196243_5_) {

        TileEntity te = p_196243_2_.getBlockEntity(p_196243_3_);

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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity user, Hand hand, BlockRayTraceResult rayTraceResult) {

        if (!world.isClientSide()) {

            TileEntity entity = world.getBlockEntity(pos);
            if (entity instanceof SolarForgeBlockEntity) {
                Consumer<PacketBuffer> cons = x -> { x.writeBlockPos(pos);
                };
                NetworkHooks.openGui((ServerPlayerEntity) user, (SolarForgeBlockEntity) entity, cons);


                     };
            }


        return super.use(state, world, pos, user, hand, rayTraceResult);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return SolarForge.SOLAR_FORGE_BLOCKENTITY.get().create();
    }
    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

//    @Override
//    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> list, ITooltipFlag tooltip) {
//        list.add(new StringTextComponent("ALL ENERGY IS LOST WHEN BROKEN!").withStyle(TextFormatting.RED));
//    }
}
