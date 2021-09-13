package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ZapTurretTile extends BlockEntity implements OwnedBlock {

    private UUID OWNER;

    public ZapTurretTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    public static void tick(BlockPos pos, BlockState state, Level world,ZapTurretTile tile){
        if (!world.isClientSide) {
            List<LivingEntity> targets = FinderfeedMathHelper.TargetFinding.getAllValidTargetsFromVec(
                    LivingEntity.class,
                    20,
                    world,
                    FinderfeedMathHelper.TileEntityThings.getTileEntityCenter(tile),
                    (entity) -> {
                        return !(entity instanceof Player);
                    }
            );
            LivingEntity lastTarget = null;
            List<LivingEntity> invalidTargets = new ArrayList<>();
            for (int i = 0; i < 5; i++) {

            }
        }

    }




    @Override
    public void load(CompoundTag p_155245_) {
        super.load(p_155245_);
    }

    @Override
    public CompoundTag save(CompoundTag p_58888_) {
        return super.save(p_58888_);
    }

    @Override
    public UUID getOwner() {
        return OWNER;
    }

    @Override
    public void setOwner(UUID OWNER) {
        this.OWNER = OWNER;
    }

}
