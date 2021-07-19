package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.RayTrapBlock;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.UpdateLaserTrapTile;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class RayTrapTileEntity extends TileEntity implements ITickableTileEntity {


    public String direction = "up";
    public int attackTick = 0;
    public boolean activated = false;
    public int CLIENT_TRIGGER_INTEGER = 0;

    public RayTrapTileEntity() {
        super(TileEntitiesRegistry.RAY_TRAP_TILE_ENTITY.get());
    }


    @Override
    public void tick() {
        BlockState state = level.getBlockState(worldPosition);

        if (state.getBlock() instanceof RayTrapBlock) {
            this.direction = state.getValue(BlockStateProperties.FACING).getName();
            if (!level.isClientSide) {


                if (activated) {
                    attackTick++;

                    if (attackTick == 1){

                        Helpers.getBlockPositionsByDirection(Direction.byName(direction),worldPosition,1).forEach((pos)->{
                            ((ServerWorld)level).sendParticles(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,1,0,0,0,0);
                        });
                    }

                  if (attackTick >= getAttackWhen()){
                      SolarForgePacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),20,level.dimension())),
                              new UpdateLaserTrapTile(1,this.worldPosition));
//                      Helpers.getBlockPositionsByDirection(Direction.byName(direction),worldPosition,5).forEach((pos)->{
//                          ((ServerWorld)level).sendParticles(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,1,0,0,0,0);
//                      });


                      List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,getBoxByDirection());
                      for  (LivingEntity a : entities){

                          a.hurt(DamageSource.MAGIC,1.5f);
                          a.invulnerableTime = 0;
                      }

                      if (attackTick >= getAttackWhen()+5){
                          activated = false;
                          attackTick = 0;
                      }
                  }
                }
            }
        }
        if (level.isClientSide) {
            if (CLIENT_TRIGGER_INTEGER >= 1){

                CLIENT_TRIGGER_INTEGER++;
            }
            if (CLIENT_TRIGGER_INTEGER >= 30){
                CLIENT_TRIGGER_INTEGER = 0;
            }
        }
    }



    public int getAttackWhen(){
        return 25;
    }

    public void triggerTrap(){
        this.activated = true;
    }


    private AxisAlignedBB getBoxByDirection(){
        Direction dir = Direction.byName(direction);

        if (dir.equals(Direction.UP)){

            return new AxisAlignedBB(worldPosition,worldPosition.offset(1,5,1));
        }else if (dir.equals(Direction.DOWN)){
            return new AxisAlignedBB(worldPosition,worldPosition.offset(1,-5,1));
        }else if (dir.equals(Direction.NORTH)) {
            return new AxisAlignedBB(worldPosition, worldPosition.offset(1, 1, -5));
        }else if (dir.equals(Direction.SOUTH)){
            return new AxisAlignedBB(worldPosition,worldPosition.offset(1,1,5));
        }else if (dir.equals(Direction.EAST)){
            return new AxisAlignedBB(worldPosition,worldPosition.offset(5,1,1));
        }else{
            return new AxisAlignedBB(worldPosition,worldPosition.offset(1,5,1));
        }


    }

    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.putInt("attack_tick",attackTick);
        p_189515_1_.putBoolean("activated_or_not",activated);
        return super.save(p_189515_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        this.attackTick = p_230337_2_.getInt("attack_tick");
        this.activated = p_230337_2_.getBoolean("activated_or_not");
        super.load(p_230337_1_, p_230337_2_);
    }


    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(worldPosition.offset(-6,-6,-6),worldPosition.offset(6,6,6));
    }
}
