package com.finderfeed.solarcraft.content.blocks.primitive;

import com.finderfeed.solarcraft.helpers.Helpers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.CommonHooks;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

public abstract class GrowableBush extends DeadBushBlock implements BonemealableBlock {

    public static final Property<Integer> AGE_3 = BlockStateProperties.AGE_3;

    public GrowableBush(Properties p_52417_) {
        super(p_52417_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(AGE_3,3));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return ctx.getPlayer() != null ? this.defaultBlockState().setValue(AGE_3,0) : this.defaultBlockState();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE_3) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource src) {
        int i = state.getValue(AGE_3);
        int br = level.getRawBrightness(pos.above(), 0);
        if (i < 3 && br >= 9 && CommonHooks.onCropsGrowPre(level, pos, state, src.nextInt(1) == 0)) {
            BlockState blockstate = state.setValue(AGE_3, Integer.valueOf(i + 1));
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
            CommonHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND){
            int age = state.getValue(AGE_3);
            if (age >= 3){
                ItemStack item = this.getDroppedItem(level.random).copy();
                ItemEntity entity = new ItemEntity(level,pos.getX() + 0.5,pos.getY() + 0.5,pos.getZ() + 0.5,item);
                level.addFreshEntity(entity);
                BlockState s = state.setValue(AGE_3,0);
                level.setBlock(pos, s, 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, s));
                player.swing(InteractionHand.MAIN_HAND);
                level.playSound(null,pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,1f,1f);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, res);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!level.isClientSide && state.getValue(AGE_3) >= 3){
            ItemStack item = this.getDroppedItem(level.random).copy();
            ItemEntity entity = new ItemEntity(level,pos.getX() + 0.5,pos.getY() + 0.5,pos.getZ() + 0.5,item);
            level.addFreshEntity(entity);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p) {
        super.createBlockStateDefinition(p);
        p.add(AGE_3);
    }

    public abstract ItemStack getDroppedItem(RandomSource source);

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource src, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE_3) < 3;
    }

    @Override
    public void performBonemeal(ServerLevel lvl, RandomSource src, BlockPos pos, BlockState state) {
        lvl.setBlock(pos, state.setValue(AGE_3,Mth.clamp(state.getValue(AGE_3)+1,0,3)),2);
    }
}
