package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.TurretTileEntity;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class TurretBlock extends Block implements EntityBlock {
    protected static final VoxelShape AABB = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    public static String SUBTAG = "turret";
    public static String LEVEL_TAG = "turret_level";

    public TurretBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }




    @Override
    public InteractionResult use(BlockState p_225533_1_, Level world, BlockPos pos, Player player, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
            if (!world.isClientSide
                    && world.getBlockEntity(pos) instanceof TurretTileEntity
                    && p_225533_5_.equals(InteractionHand.MAIN_HAND)
                    && (player.getMainHandItem().getItem().equals(((TurretTileEntity) world.getBlockEntity(pos)).getUpgradeItem())
            )){
                if (((TurretTileEntity) world.getBlockEntity(pos)).upgrade()){
                    player.getMainHandItem().grow(-1);
                    world.playSound(null,player, SoundEvents.ANVIL_USE, SoundSource.AMBIENT,1,1);
                    return InteractionResult.SUCCESS;
                }
            }
        return super.use(p_225533_1_, world, pos,player, p_225533_5_, p_225533_6_);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState state2, boolean hz) {
        if (!world.isClientSide) {

            BlockEntity test = world.getBlockEntity(pos);
            if (test instanceof TurretTileEntity) {
                TurretTileEntity tile = (TurretTileEntity) test;
                ItemStack stack = SCItems.TURRET_BLOCK.get().getDefaultInstance();
                stack.getOrCreateTagElement(SUBTAG).putInt(LEVEL_TAG, tile.turretLevel);
                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
            }
        }

        super.onRemove(state, world, pos, state2, hz);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack stack) {

        if (!world.isClientSide) {
            BlockEntity test = world.getBlockEntity(pos);
           
            if (test instanceof TurretTileEntity) {
                TurretTileEntity tile = (TurretTileEntity) test;
                CompoundTag nbt = stack.getTagElement(TurretBlock.SUBTAG);
                if (nbt != null) {
                    tile.turretLevel = nbt.getInt(TurretBlock.LEVEL_TAG);
                }
            }
        }
        super.setPlacedBy(world, pos, p_180633_3_, p_180633_4_, stack);
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
        return AABB;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SCTileEntities.TURRET_TILE_ENTITY.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            TurretTileEntity.tick(level,blockPos,blockState,(TurretTileEntity) t);
        });
    }
}
