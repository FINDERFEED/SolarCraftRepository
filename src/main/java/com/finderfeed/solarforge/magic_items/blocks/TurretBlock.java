package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.magic_items.blocks.blockentities.TurretTileEntity;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TurretBlock extends Block {
    protected static final VoxelShape AABB = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    public static String SUBTAG = "turret";
    public static String LEVEL_TAG = "turret_level";

    public TurretBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.TURRET_TILE_ENTITY.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World world, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
            if (!world.isClientSide
                    && world.getBlockEntity(pos) instanceof TurretTileEntity
                    && p_225533_5_.equals(Hand.MAIN_HAND)
                    && (player.getMainHandItem().getItem().equals(((TurretTileEntity) world.getBlockEntity(pos)).getUpgradeItem())
            )){
                if (((TurretTileEntity) world.getBlockEntity(pos)).upgrade()){
                    player.getMainHandItem().grow(-1);
                    world.playSound(null,player, SoundEvents.ANVIL_USE, SoundCategory.AMBIENT,1,1);
                    return ActionResultType.SUCCESS;
                }
            }
        return super.use(p_225533_1_, world, pos,player, p_225533_5_, p_225533_6_);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean hz) {
        if (!world.isClientSide) {

            TileEntity test = world.getBlockEntity(pos);
            if (test instanceof TurretTileEntity) {
                TurretTileEntity tile = (TurretTileEntity) test;
                ItemStack stack = ItemsRegister.TURRET_BLOCK.get().getDefaultInstance();
                stack.getOrCreateTagElement(SUBTAG).putInt(LEVEL_TAG, tile.turretLevel);
                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
            }
        }

        super.onRemove(state, world, pos, state2, hz);
    }

    @Override
    public void setPlacedBy(World world, BlockPos pos, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack stack) {

        if (!world.isClientSide) {
            TileEntity test = world.getBlockEntity(pos);
           
            if (test instanceof TurretTileEntity) {
                TurretTileEntity tile = (TurretTileEntity) test;
                CompoundNBT nbt = stack.getTagElement(TurretBlock.SUBTAG);
                if (nbt != null) {
                    tile.turretLevel = nbt.getInt(TurretBlock.LEVEL_TAG);
                }
            }
        }
        super.setPlacedBy(world, pos, p_180633_3_, p_180633_4_, stack);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return AABB;
    }
}
