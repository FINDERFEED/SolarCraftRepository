package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.client.particles.SCParticleTypes;

import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class AuraHealerTile extends BlockEntity implements IStructureOwner {
    public int healTick = 0;

    public AuraHealerTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.AURA_HEALER_TILE.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world, BlockPos pos, BlockState blockState, AuraHealerTile tile) {
        if  (!tile.level.isClientSide){
            tile.healTick++;
            if (tile.healTick > 400 ) {
                tile.healTick = 0;

                if (Multiblocks.AURA_HEALER.check(world,pos,false)) {
                    List<Player> players = tile.level.getEntitiesOfClass(Player.class, new AABB(-20, -5, -20, 20, 5, 20).move(tile.worldPosition));
                    for (Player a : players) {
                        if (a.getHealth() != a.getMaxHealth()) {
                            a.heal(4);
                            for (int i = 10; i < 16; i++) {
                                ((ServerLevel) tile.level).sendParticles(SCParticleTypes.HEAL_PARTICLE.get(), a.position().x, a.position().y + 1.35f, a.position().z, 5, 0, 0.3, 0, 0.02);
                            }
                        }
                    }
                }
            }
        }
        
    }



    @Override
    public void saveAdditional(CompoundTag p_189515_1_) {
        p_189515_1_.putInt("heal_tick", healTick);

    }

    @Override
    public void load( CompoundTag p_230337_2_) {
        healTick = p_230337_2_.getInt("heal_tick");
        super.load( p_230337_2_);
    }

    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(Multiblocks.AURA_HEALER);
    }

}
