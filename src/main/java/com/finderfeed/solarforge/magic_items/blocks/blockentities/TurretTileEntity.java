package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
        super(TileEntitiesRegistry.TURRET_TILE_ENTITY.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos post, BlockState blockState, TurretTileEntity tile) {
        if (!tile.level.isClientSide){
            tile.attackTick++;
            if (tile.attackTick >= tile.getAttackRate()){
                tile.attackTick = 0;
                List<LivingEntity> list = tile.level.getEntitiesOfClass(LivingEntity.class,new AABB(-10,-4,-10,10,4,10)
                        .move(tile.worldPosition),(entity) -> !(entity instanceof Player));

                    tile.sortList(list);
                if (!list.isEmpty()) {

                    LivingEntity entity = list.get(tile.level.random.nextInt(list.size()));
                    Vec3 velocity = Helpers.calculateVelocity(Helpers.getBlockCenter(tile.worldPosition), entity.position().add(0, 0.7f, 0));
                    AbstractTurretProjectile projectile = new AbstractTurretProjectile(tile.level, new AbstractTurretProjectile.Constructor()
                            .setDamage(tile.turretLevel * 5)
                            .setPosition(Helpers.getBlockCenter(tile.worldPosition)
                                    .add(velocity.multiply(0.5,0.5,0.5))
                                    .add(0,-0.1,0))
                            .setVelocity(velocity)
                    );

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
        return ItemsRegister.CHARGED_QUALADIUM_INGOT.get();
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
