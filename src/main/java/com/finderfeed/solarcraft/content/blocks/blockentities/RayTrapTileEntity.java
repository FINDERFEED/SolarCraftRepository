package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.RayTrapBlock;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.UpdateLaserTrapTile;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.server.level.ServerLevel;
import java.util.List;

public class RayTrapTileEntity extends BlockEntity  {


    public String direction = "up";
    public int attackTick = 0;
    public boolean activated = false;
    public int clientTicker = 0;

    public RayTrapTileEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.RAY_TRAP_TILE_ENTITY.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world, BlockPos posi, BlockState blockState, RayTrapTileEntity tile) {
        BlockState state = tile.level.getBlockState(tile.worldPosition);

        if (state.getBlock() instanceof RayTrapBlock) {
            tile.direction = state.getValue(BlockStateProperties.FACING).getName();
            if (!tile.level.isClientSide) {


                if (tile.activated) {
                    tile.attackTick++;

                    if (tile.attackTick == 1){

                        Helpers.getBlockPositionsByDirection(Direction.byName(tile.direction),tile.worldPosition,1).forEach((pos)->{
                            ((ServerLevel)tile.level).sendParticles(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,1,0,0,0,0);
                        });
                    }

                  if (tile.attackTick >= tile.getAttackWhen()){
                      PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(tile.worldPosition.getX(),tile.worldPosition.getY(),tile.worldPosition.getZ(),20,tile.level.dimension()).get()).send(
                              new UpdateLaserTrapTile(1,tile.worldPosition));
//                      Helpers.getBlockPositionsByDirection(Direction.byName(direction),worldPosition,5).forEach((pos)->{
//                          ((ServerWorld)level).sendParticles(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,1,0,0,0,0);
//                      });


                      List<LivingEntity> entities = tile.level.getEntitiesOfClass(LivingEntity.class,tile.getBoxByDirection());
                      for  (LivingEntity a : entities){

                          a.hurt(world.damageSources().magic(),1.5f);
                          a.invulnerableTime = 0;
                      }

                      if (tile.attackTick >= tile.getAttackWhen()+5){
                          tile.activated = false;
                          tile.attackTick = 0;
                      }
                  }
                }
            }
        }
        if (tile.level.isClientSide) {
            if (tile.clientTicker >= 1){

                tile.clientTicker++;
            }
            if (tile.clientTicker >= 30){
                tile.clientTicker = 0;
            }
        }
    }



    public int getAttackWhen(){
        return 25;
    }

    public void triggerTrap(){
        this.activated = true;
    }


    private AABB getBoxByDirection(){
        Direction dir = Direction.byName(direction);

        Vec3 pos = Helpers.posToVec(worldPosition);
        if (dir.equals(Direction.UP)){
            return new AABB(pos,pos.add(1,5,1));
        }else if (dir.equals(Direction.DOWN)){
            return new AABB(pos,pos.add(1,-5,1));
        }else if (dir.equals(Direction.NORTH)) {
            return new AABB(pos, pos.add(1, 1, -5));
        }else if (dir.equals(Direction.SOUTH)){
            return new AABB(pos,pos.add(1,1,5));
        }else if (dir.equals(Direction.EAST)){
            return new AABB(pos,pos.add(5,1,1));
        }else{
            return new AABB(pos,pos.add(1,5,1));
        }


    }

    @Override
    public void saveAdditional(CompoundTag p_189515_1_) {
        p_189515_1_.putInt("attack_tick",attackTick);
        p_189515_1_.putBoolean("activated_or_not",activated);
         super.saveAdditional(p_189515_1_);
    }

    @Override
    public void load( CompoundTag p_230337_2_) {
        this.attackTick = p_230337_2_.getInt("attack_tick");
        this.activated = p_230337_2_.getBoolean("activated_or_not");
        super.load( p_230337_2_);
    }


}
