package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.PlaySoundPacket;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.fmllegacy.network.PacketDistributor;


import java.util.List;

public abstract class AbstractMortarTileEntity extends BlockEntity  {

    public double attackTick = 0;

    public AbstractMortarTileEntity(BlockEntityType<?>type, BlockPos p_155229_, BlockState p_155230_) {
        super(type, p_155229_, p_155230_);
    }


    public static void tick(Level world, BlockPos pos, BlockState blockState, AbstractMortarTileEntity tile) {
        if (!tile.level.isClientSide ){
            tile.attackTick++;
            if (tile.attackTick >= tile.getAttackInterval() ) {
                if (tile.getConditionToFunction()){
                    AABB box = new AABB(-tile.getAttackRadius(), -20, -tile.getAttackRadius(), tile.getAttackRadius(), 0, tile.getAttackRadius()).move(tile.worldPosition);
                    List<LivingEntity> list = tile.level.getEntitiesOfClass(LivingEntity.class, box,
                            (entity) -> (entity instanceof Monster && world.canSeeSky(entity.blockPosition().above(3))));
                    if (!list.isEmpty()) {
                        LivingEntity attackThis = list.get(tile.level.random.nextInt(list.size()));
                        Vec3 position = attackThis.position();
                        AbstractMortarProjectile proj = tile.getMortarProjectile();
                        Vec3 projectilePos = new Vec3(tile.worldPosition.getX() + 0.5d, tile.worldPosition.getY() + 1.5d, tile.worldPosition.getZ() + 0.5d);
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
                        Vec3 vec = new Vec3(position.x - projectilePos.x, 0, position.z - projectilePos.z);
                        double s = vec.length();
                        double v = s / t;
                        vec = vec.normalize();
                        double velX = vec.x * v;
                        double velZ = vec.z * v;
                        proj.setDeltaMovement(Helpers.blocksPerSecondToVelocity(velX), Helpers.blocksPerSecondToVelocity(velocitystart), Helpers.blocksPerSecondToVelocity(velZ));
                        tile.level.addFreshEntity(proj);

                        SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(), tile.worldPosition.getY(), tile.worldPosition.getZ(), 50, tile.level.dimension())),
                                new PlaySoundPacket(20,1,1,tile.worldPosition.above()));
                    }
                }
                tile.attackTick = 0;
            }

        }
    }

    public abstract boolean getConditionToFunction();
    public abstract double getAttackRadius();
    public abstract double getAttackInterval();
    public abstract AbstractMortarProjectile getMortarProjectile();
}
