package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ExplosionBlockerBlockEntity extends SolarcraftBlockEntity implements IStructureOwner {

    public static final int DEFENDING_RADIUS = 30;
    private boolean shouldRenderShield = true;

    public ExplosionBlockerBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.EXPLOSTION_BLOCKER.get(), p_155229_, p_155230_);
    }


    public static void tick(ExplosionBlockerBlockEntity b, Level world){
        if (b.shouldRenderShield) {

            ClientHelpers.Particles.horizontalXCircle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    Helpers.getBlockCenter(b.getBlockPos()), 3, 2, new float[]{0, 0, 0},
                    () -> 87, () -> 202, () -> 255, 0.4f, 2f, 0);
            ClientHelpers.Particles.horizontalZCircle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),
                    Helpers.getBlockCenter(b.getBlockPos()), 3, 2, new float[]{0, 0, 0},
                    () -> 87, () -> 202, () -> 255, 0.4f, 2f, 90);
        }
        if (world.getGameTime() % 20 == 0){

            b.shouldRenderShield = Multiblocks.EXPLOSION_BLOCKER.check(b.level,b.worldPosition,true);
        }
    }


    public void setShieldRenderingState(boolean a){
        shouldRenderShield = a;
    }

    public boolean shouldRenderShield() {
        return shouldRenderShield;
    }



    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("shouldRenderShield",shouldRenderShield);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        shouldRenderShield = tag.getBoolean("shouldRenderShield");
//        if (level != null){
//            this.setChanged();
//            level.sendBlockUpdated(worldPosition,getBlockState(),getBlockState(),3);
//        }
    }

//    @Override
//    public AABB getRenderBoundingBox() {
//        return new AABB(-40,-40,-40,40,40,40).move(Helpers.getBlockCenter(worldPosition));
//    }


    public boolean isFunctioning(){
        return Multiblocks.EXPLOSION_BLOCKER.check(level,worldPosition,true);
    }


    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(Multiblocks.EXPLOSION_BLOCKER);
    }
}
