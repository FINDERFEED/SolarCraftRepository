package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.TurretProjectile;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class TurretTileEntity extends BlockEntity  {

    public int turretLevel = 1;
    public int attackTick = 0;

    public TurretTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.TURRET_TILE_ENTITY.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos post, BlockState blockState, TurretTileEntity tile) {
        if (!tile.level.isClientSide){
            tile.attackTick++;
            if (tile.attackTick >= tile.getAttackRate()){
                tile.attackTick = 0;
                List<LivingEntity> list = tile.level.getEntitiesOfClass(LivingEntity.class,new AABB(-10,-4,-10,10,4,10)
                        .move(tile.worldPosition),(entity) -> {
                    return entity instanceof Monster;
                });

                    tile.sortList(list);
                if (!list.isEmpty()) {

                    LivingEntity entity = list.get(tile.level.random.nextInt(list.size()));
                    Vec3 velocity = Helpers.calculateVelocity(Helpers.getBlockCenter(tile.worldPosition), entity.position().add(0, 0.7f, 0));
                    TurretProjectile projectile = new TurretProjectile(SCEntityTypes.TURRET_PROJECTILE.get(),tile.level);
                    projectile.damage = tile.turretLevel * 5;
                    projectile.setPos(Helpers.getBlockCenter(tile.worldPosition)
                            .add(velocity.multiply(0.5,0.5,0.5))
                            .add(0,-0.1,0));
                    projectile.setDeltaMovement(velocity);

                    tile.level.addFreshEntity(projectile);
                }
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag p_189515_1_) {
        p_189515_1_.putInt("turretlevel",turretLevel);
        p_189515_1_.putInt("attack_tick",attackTick);
        super.saveAdditional(p_189515_1_);
    }

    @Override
    public void load( CompoundTag p_230337_2_) {
        attackTick = p_230337_2_.getInt("attack+tick");
        turretLevel = p_230337_2_.getInt("turretlevel");
        super.load( p_230337_2_);
    }

    public int getAttackRate(){
        return 40;
    }

    public void sortList(List<LivingEntity> list){
        List<LivingEntity> toRemove = new ArrayList<>();

        for (LivingEntity ent : list){
            if (!Helpers.isEntityReachable(level,worldPosition,ent.blockPosition())){
                toRemove.add(ent);
            }
        }
        list.removeAll(toRemove);
    }

    public Item getUpgradeItem(){
        return SCItems.CHARGED_QUALADIUM_INGOT.get();
    }

    public int getMaxTurretLevel(){
        return 5;
    }

    public boolean upgrade(){
        if (turretLevel < getMaxTurretLevel()) {
            this.turretLevel++;
            return true;
        }else{
            return false;
        }
    }
}
