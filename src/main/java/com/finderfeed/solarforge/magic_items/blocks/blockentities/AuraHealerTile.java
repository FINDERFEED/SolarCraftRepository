package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packets.RepeaterParentUpdateOnClient;
import com.finderfeed.solarforge.packets.SpawnHealParticles;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.Random;

public class AuraHealerTile extends TileEntity implements ITickableTileEntity {
    public int HEAL_TICK = 0;
    public AuraHealerTile() {
        super(TileEntitiesRegistry.AURA_HEALER_TILE.get());
    }

    @Override
    public void tick() {
        if  (!this.level.isClientSide){
            HEAL_TICK++;


            if (HEAL_TICK > 400 ) {
                HEAL_TICK = 0;

                if (Helpers.checkStructure(level,worldPosition.offset(-1,-2,-1), Multiblocks.AURA_HEALER.getM())) {
                    List<PlayerEntity> players = level.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(-20, -5, -20, 20, 5, 20).move(worldPosition), null);
                    for (PlayerEntity a : players) {
                        if (a.getHealth() != a.getMaxHealth()) {
                            a.heal(4);
                            for (int i = 10; i < 16; i++) {
                                ((ServerWorld) level).sendParticles(ParticlesList.HEAL_PARTICLE.get(), a.position().x, a.position().y + 1.35f, a.position().z, 5, 0, 0.3, 0, 0.02);
                            }
                        }
                    }
                }
            }
        }
        
    }


    @Override
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.putInt("heal_tick",HEAL_TICK);
        return super.save(p_189515_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
        HEAL_TICK = p_230337_2_.getInt("heal_tick");
        super.load(p_230337_1_, p_230337_2_);
    }

//    public void spawnParticles(Vector3f position){
//        for (int i = 0; i < 16;i++){
//            float xRandom = new Random().nextFloat()*1.25f -0.5f;
//            float yRandom = new Random().nextFloat()*1.5f -1;
//            float zRandom = new Random().nextFloat()*1.25f -0.5f;
//
//            level.addParticle(ParticlesList.HEAL_PARTICLE.get(),true,position.x()+xRandom,position.y()+yRandom,position.z()+zRandom,0,0.02,0);
//        }
//    }
}
