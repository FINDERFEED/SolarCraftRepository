package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntity;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class AuraHealerTile extends BlockEntity  {
    public int HEAL_TICK = 0;

    public AuraHealerTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.AURA_HEALER_TILE.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, AuraHealerTile tile) {
        if  (!tile.level.isClientSide){
            tile.HEAL_TICK++;


            if (tile.HEAL_TICK > 400 ) {
                tile.HEAL_TICK = 0;

                if (Helpers.checkStructure(tile.level,tile.worldPosition.offset(-1,-2,-1), Multiblocks.AURA_HEALER.getM(),true)) {
                    List<Player> players = tile.level.getEntitiesOfClass(Player.class, new AABB(-20, -5, -20, 20, 5, 20).move(tile.worldPosition), null);
                    for (Player a : players) {
                        if (a.getHealth() != a.getMaxHealth()) {
                            a.heal(4);
                            for (int i = 10; i < 16; i++) {
                                ((ServerLevel) tile.level).sendParticles(ParticlesList.HEAL_PARTICLE.get(), a.position().x, a.position().y + 1.35f, a.position().z, 5, 0, 0.3, 0, 0.02);
                            }
                        }
                    }
                }
            }
        }
        
    }


    @Override
    public CompoundTag save(CompoundTag p_189515_1_) {
        p_189515_1_.putInt("heal_tick",HEAL_TICK);
        return super.save(p_189515_1_);
    }

    @Override
    public void load( CompoundTag p_230337_2_) {
        HEAL_TICK = p_230337_2_.getInt("heal_tick");
        super.load( p_230337_2_);
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
