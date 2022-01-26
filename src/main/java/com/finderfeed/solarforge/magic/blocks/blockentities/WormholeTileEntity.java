package com.finderfeed.solarforge.magic.blocks.blockentities;

import com.finderfeed.solarforge.local_library.helpers.EntityHelper;
import com.finderfeed.solarforge.local_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WormholeTileEntity extends BlockEntity {



    public WormholeTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.WORMHOLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockState state, BlockPos pos, WormholeTileEntity tile){

        world.getEntitiesOfClass(LivingEntity.class,new AABB(-10,-10,-10,10,10,10).move(pos), (entity)->{
            if (entity.canChangeDimensions() && Math.sqrt(entity.distanceToSqr(FinderfeedMathHelper.TileEntityThings.getTileEntityCenter(pos))) <= 10){
                return true;
            }else{
                return false;
            }
        }).forEach((living)->{

            Vec3 vec = EntityHelper.getVecBetweenEntityAndBlock(living,pos).reverse();
            double factor = 10 - vec.length();
            Vec3 baseSpeed = vec.normalize().multiply(0.01,0.01,0.01);
            living.setDeltaMovement(living.getDeltaMovement().add(baseSpeed.multiply(factor,factor,factor)));
        });
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(getBlockPos(),getBlockPos().offset(1,1,1));
    }
}
