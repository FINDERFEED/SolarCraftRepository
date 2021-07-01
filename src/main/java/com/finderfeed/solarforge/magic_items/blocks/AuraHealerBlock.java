package com.finderfeed.solarforge.magic_items.blocks;

import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class AuraHealerBlock extends Block {
    public AuraHealerBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.AURA_HEALER_TILE.get().create();
    }


    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
        //if (p_180655_4_.nextInt(5) == 0) {
            Direction direction = Direction.UP;
            BlockPos blockpos = p_180655_3_.relative(direction);
            BlockState blockstate = p_180655_2_.getBlockState(blockpos);
            //if (!p_180655_1_.canOcclude() || !blockstate.isFaceSturdy(p_180655_2_, blockpos, direction.getOpposite())) {
                double d0 = direction.getStepX() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepX() * 0.6D;
                double d1 = direction.getStepY() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepY() * 0.6D;
                double d2 = direction.getStepZ() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.6D;
                p_180655_2_.addParticle(ParticlesList.HEAL_PARTICLE.get(), (double)p_180655_3_.getX() + d0, (double)p_180655_3_.getY() + d1+new Random().nextFloat()*1.5-1, (double)p_180655_3_.getZ() + d2, 0, 0.02, 0.0D);
             d0 = direction.getStepX() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepX() * 0.6D;
             d1 = direction.getStepY() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepY() * 0.6D;
             d2 = direction.getStepZ() == 0 ? p_180655_4_.nextDouble() : 0.5D + (double)direction.getStepZ() * 0.6D;
            p_180655_2_.addParticle(ParticlesList.HEAL_PARTICLE.get(), (double)p_180655_3_.getX() + d0, (double)p_180655_3_.getY() + d1+new Random().nextFloat()*1.5-1, (double)p_180655_3_.getZ() + d2, 0.0D, 0.02, 0.0D);
            //}
        //}
    }


    @Override
    public void appendHoverText(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> p_190948_3_, ITooltipFlag p_190948_4_) {
        p_190948_3_.add(new TranslationTextComponent("solarforge.aura_healer_block").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_190948_1_, p_190948_2_, p_190948_3_, p_190948_4_);
    }

}
