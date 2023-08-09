package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.IStructureOwner;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.local_library.OwnedBlock;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;

import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ZapTurretTile extends BlockEntity implements OwnedBlock, IStructureOwner {

    private int attackTick = 0;
    private UUID owner;
    private boolean attack = false;
    private List<Vec3> targets = new ArrayList<>();

    public ZapTurretTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.ZAP_TURRET_TILE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockPos pos, BlockState state,ZapTurretTile tile){
        if (tile.isWorking()) {
            if (!world.isClientSide && tile.attackTick++ > 60) {
                tile.attackTick = 0;
                tile.targets.clear();
                tile.attack = false;
                List<LivingEntity> targets = FDMathHelper.TargetFinding.getAllValidTargetsFromVec(
                        LivingEntity.class,
                        20,
                        world,
                        FDMathHelper.TileEntityThings.getTileEntityCenter(tile),
                        (entity) -> {
                            return !(entity instanceof Player);
                        }
                );
                if (targets.size() != 0) {
                    LivingEntity lastTarget = targets.get(world.random.nextInt(targets.size()));
                    lastTarget.hurt(tile.level.damageSources().magic(), 5);
                    List<LivingEntity> invalidTargets = new ArrayList<>();
                    invalidTargets.add(lastTarget);
                    for (int i = 0; i < 4; i++) {
                        List<LivingEntity> secondaryTargets = FDMathHelper.TargetFinding.getAllValidTargetsFromVec(
                                LivingEntity.class,
                                20,
                                world,
                                lastTarget.position().add(0, lastTarget.getBbHeight() * 1.1 / 2, 0),
                                (entity) -> {
                                    return !(entity instanceof Player) && !invalidTargets.contains(entity);
                                }
                        );
                        if (secondaryTargets.size() != 0) {
                            lastTarget = secondaryTargets.get(world.random.nextInt(secondaryTargets.size()));
                            lastTarget.hurt(tile.level.damageSources().magic(), 5);
                            invalidTargets.add(lastTarget);
                        } else {
                            break;
                        }
                    }

                    tile.targets.add(FDMathHelper.TileEntityThings.getTileEntityCenter(pos));

                    invalidTargets.forEach((trg) -> {
                        tile.targets.add(trg.position().add(0, trg.getBbHeight() / 2, 0));
                    });

                    world.playSound(null,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f, SolarcraftSounds.ZAP_TURRET_SHOT.get(), SoundSource.AMBIENT,1f,0.7f);
                    tile.attack = true;
                    world.sendBlockUpdated(pos, state, state, 3);

                }
            }

            if (world.isClientSide) {
                if (tile.attack) {
                    tile.attack = false;

                    for (int i = 0; i < tile.targets.size() - 1; i++) {
                        Vec3 between = tile.targets.get(i + 1).subtract(tile.targets.get(i));
                        float multiplier = 3;
                        Vec3 normal = between.normalize().multiply(1 / multiplier, 1 / multiplier, 1 / multiplier);
                        for (float g = 1; g < between.length() * multiplier; g++) {
                            Vec3 position = tile.targets.get(i).add(normal.multiply(g, g, g));
                            world.addParticle(SCParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(), position.x, position.y, position.z, 0, 0, 0);
                        }
                        if (i != 0) {
                            Helpers.createSmallSolarStrikeParticleExplosion(world, tile.targets.get(i),2,0.04f,1f);
                        }
                    }
                    Helpers.createSmallSolarStrikeParticleExplosion(world, tile.targets.get(tile.targets.size()-1),2,0.04f,1f);

                    tile.targets.clear();
                }
            }
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());

    }

    @Override
    public void load(CompoundTag cmp) {
        int len = cmp.getInt("list_length");
        attackTick = cmp.getInt("attackTick");
        for (int i = 0;i < len;i++){
            targets.add(CompoundNBTHelper.getVec3("vec"+i,cmp));
        }
        attack = cmp.getBoolean("attack");
        super.load(cmp);
    }

    @Override
    public void saveAdditional(CompoundTag cmp) {
        cmp.putInt("list_length",targets.size());
        for (int i = 0;i < targets.size();i++){
            CompoundNBTHelper.writeVec3("vec"+i,targets.get(i),cmp);
        }
        cmp.putBoolean("attack",attack);
        cmp.putInt("attackTick",attackTick);
        super.saveAdditional(cmp);
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID OWNER) {
        this.owner = OWNER;
    }

    public boolean isWorking(){
        return Multiblocks.ZAP_TURRET.check(level,worldPosition,true);
    }

    @Override
    public List<MultiblockStructure> getMultiblocks() {
        return List.of(Multiblocks.ZAP_TURRET);
    }
}
