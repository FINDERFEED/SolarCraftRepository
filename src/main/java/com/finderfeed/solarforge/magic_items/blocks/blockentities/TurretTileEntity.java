package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class TurretTileEntity extends TileEntity implements ITickableTileEntity {

    public int turretLevel = 1;
    public int attackTick = 0;

    public TurretTileEntity() {
        super(TileEntitiesRegistry.TURRET_TILE_ENTITY.get());
    }


    @Override
    public void tick() {
        if (!level.isClientSide){
            attackTick++;
            if (attackTick >= getAttackRate()){
                attackTick = 0;
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class,new AxisAlignedBB(-10,-4,-10,10,4,10)
                        .move(worldPosition),(entity) -> !(entity instanceof PlayerEntity));

                    sortList(list);
                if (!list.isEmpty()) {

                    LivingEntity entity = list.get(level.random.nextInt(list.size()));
                    Vector3d velocity = Helpers.calculateVelocity(Helpers.getBlockCenter(worldPosition), entity.position().add(0, 0.7f, 0));
                    AbstractTurretProjectile projectile = new AbstractTurretProjectile(level, new AbstractTurretProjectile.Constructor()
                            .setDamage(turretLevel * 5)
                            .setPosition(Helpers.getBlockCenter(worldPosition)
                                    .add(velocity.multiply(0.5,0.5,0.5))
                                    .add(0,-0.1,0))
                            .setVelocity(velocity)
                    );

                    level.addFreshEntity(projectile);
                }
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.putInt("turretlevel",turretLevel);
        p_189515_1_.putInt("attack_tick",attackTick);
        return super.save(p_189515_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        attackTick = p_230337_2_.getInt("attack+tick");
        turretLevel = p_230337_2_.getInt("turretlevel");
        super.load(p_230337_1_, p_230337_2_);
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
