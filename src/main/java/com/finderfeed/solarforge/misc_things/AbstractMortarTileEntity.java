package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.PlaySoundPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.PacketDistributor;


import java.util.List;

public abstract class AbstractMortarTileEntity extends TileEntity implements ITickableTileEntity {

    public double attackTick = 0;

    public AbstractMortarTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    @Override
    public void tick() {
        if (!level.isClientSide ){
            attackTick++;
            if (attackTick >= getAttackInterval() ) {
                if (getConditionToFunction()){
                    AxisAlignedBB box = new AxisAlignedBB(-getAttackRadius(), -20, -getAttackRadius(), getAttackRadius(), 0, getAttackRadius()).move(worldPosition);
                    List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box, (entity) -> (entity instanceof MonsterEntity));
                    if (!list.isEmpty()) {
                        LivingEntity attackThis = list.get(level.random.nextInt(list.size()));
                        Vector3d position = attackThis.position();
                        AbstractMortarProjectile proj = getMortarProjectile();
                        Vector3d projectilePos = new Vector3d(this.worldPosition.getX() + 0.5d, this.worldPosition.getY() + 1.5d, this.worldPosition.getZ() + 0.5d);
                        proj.setPos(projectilePos.x, projectilePos.y, projectilePos.z);
                        double x0 = projectilePos.y;
                        double x = attackThis.getY();
                        double t = 0;
                        double velocitystart = 50;
                        if (x0 < x) {
                            t = (-velocitystart + Math.sqrt(velocitystart * velocitystart - 4 * (x0 - x) * (-Helpers.GRAVITY_METRES_PER_SEC / 2))) / -Helpers.GRAVITY_METRES_PER_SEC;
                        } else {
                            t = (-velocitystart - Math.sqrt(velocitystart * velocitystart - 4 * (x0 - x) * (-Helpers.GRAVITY_METRES_PER_SEC / 2))) / -Helpers.GRAVITY_METRES_PER_SEC;
                        }
                        Vector3d vec = new Vector3d(position.x - projectilePos.x, 0, position.z - projectilePos.z);
                        double s = vec.length();
                        double v = s / t;
                        vec = vec.normalize();
                        double velX = vec.x * v;
                        double velZ = vec.z * v;
                        proj.setDeltaMovement(Helpers.blocksPerSecondToVelocity(velX), Helpers.blocksPerSecondToVelocity(velocitystart), Helpers.blocksPerSecondToVelocity(velZ));
                        level.addFreshEntity(proj);

                        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 50, level.dimension())),
                                new PlaySoundPacket(20,1,1,worldPosition.above()));
                    }
                }
                attackTick = 0;
            }

        }
    }

    public abstract boolean getConditionToFunction();
    public abstract double getAttackRadius();
    public abstract double getAttackInterval();
    public abstract AbstractMortarProjectile getMortarProjectile();
}
