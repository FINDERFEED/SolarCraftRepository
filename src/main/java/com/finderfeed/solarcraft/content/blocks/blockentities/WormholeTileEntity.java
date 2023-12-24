package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.local_library.helpers.EntityHelper;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WormholeTileEntity extends BlockEntity {



    public WormholeTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.WORMHOLE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockState state, BlockPos pos, WormholeTileEntity tile){

        world.getEntitiesOfClass(LivingEntity.class,new AABB(-10,-10,-10,10,10,10).move(pos), (entity)->{
            if (entity.canChangeDimensions() && Math.sqrt(entity.distanceToSqr(FDMathHelper.TileEntityThings.getTileEntityCenter(pos))) <= 10){
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

//    @Override
//    public AABB getRenderBoundingBox() {
//        return new AABB(getBlockPos(),getBlockPos().offset(1,1,1));
//    }
}
