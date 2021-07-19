package com.finderfeed.solarforge.world_generation.structures.blocks;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ColdStarInfuser extends Block {
    public ColdStarInfuser(Properties p_i48440_1_) {
        super(p_i48440_1_);

    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntitiesRegistry.COLD_STAR_INFUSER.get().create();
    }

    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {

        if (p_225533_4_.getItemInHand(p_225533_5_).getItem() == ItemsRegister.COLD_STAR_PIECE.get()){
            for (int i = 0; i < 96;i++){
                p_225533_2_.addParticle(ParticleTypes.FLAME,p_225533_3_.getX()+0.5f + Math.cos(Math.toRadians(i*30)),p_225533_3_.getY() + i * 0.5,p_225533_3_.getZ()+0.5f + Math.sin(Math.toRadians(i*30)),0,0,0);
            }
        }

        if (!p_225533_2_.isClientSide){
            if (p_225533_4_.getItemInHand(p_225533_5_).getItem() == ItemsRegister.COLD_STAR_PIECE.get()){
                p_225533_4_.setItemInHand(p_225533_5_,new ItemStack(ItemsRegister.COLD_STAR_PIECE_ACTIVATED.get(),1));

                p_225533_2_.destroyBlock(p_225533_3_,false);

            }
        }


        return super.use(p_225533_1_, p_225533_2_, p_225533_3_, p_225533_4_, p_225533_5_, p_225533_6_);
    }
}
