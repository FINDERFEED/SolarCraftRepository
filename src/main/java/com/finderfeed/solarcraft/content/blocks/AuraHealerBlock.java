package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.content.blocks.blockentities.AuraHealerTile;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class AuraHealerBlock extends Block implements EntityBlock {
    public AuraHealerBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }




    @Override
    public RenderShape getRenderShape(BlockState p_149645_1_) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, Level p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        //if (p_180655_4_.nextInt(5) == 0) {
            Direction direction = Direction.UP;
            BlockPos blockpos = p_180655_3_.relative(direction);
            BlockState blockstate = p_180655_2_.getBlockState(blockpos);
            //if (!p_180655_1_.canOcclude() || !blockstate.isFaceSturdy(p_180655_2_, blockpos, direction.getOpposite())) {
                double d0 = direction.getStepX() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepX() * 0.6D;
                double d1 = direction.getStepY() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepY() * 0.6D;
                double d2 = direction.getStepZ() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.6D;
                p_180655_2_.addParticle(SCParticleTypes.HEAL_PARTICLE.get(), (double)p_180655_3_.getX() + d0, (double)p_180655_3_.getY() + d1+new Random().nextFloat()*1.5-1, (double)p_180655_3_.getZ() + d2, 0, 0.02, 0.0D);
             d0 = direction.getStepX() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepX() * 0.6D;
             d1 = direction.getStepY() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepY() * 0.6D;
             d2 = direction.getStepZ() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.6D;
            p_180655_2_.addParticle(SCParticleTypes.HEAL_PARTICLE.get(), (double)p_180655_3_.getX() + d0, (double)p_180655_3_.getY() + d1+new Random().nextFloat()*1.5-1, (double)p_180655_3_.getZ() + d2, 0.0D, 0.02, 0.0D);
            //}
        //}
    }


    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable BlockGetter p_190948_2_, List<Component> p_190948_3_, TooltipFlag p_190948_4_) {
        p_190948_3_.add(Component.translatable("solarcraft.aura_healer_block").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return SolarcraftTileEntityTypes.AURA_HEALER_TILE.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return ((level, blockPos, blockState, t) -> {
            AuraHealerTile.tick(level,blockPos,blockState,(AuraHealerTile) t);
        });
    }
}
