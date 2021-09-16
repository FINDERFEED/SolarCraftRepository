package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.for_future_library.CompoundNBTHelper;
import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.for_future_library.OwnedBlock;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ZapTurretTile extends BlockEntity implements OwnedBlock {

    private int attackTick = 0;
    private UUID OWNER;
    private boolean attack = false;
    private List<Vec3> targets = new ArrayList<>();

    public ZapTurretTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.ZAP_TURRET_TILE.get(), p_155229_, p_155230_);
    }


    public static void tick(Level world,BlockPos pos, BlockState state,ZapTurretTile tile){
        if (!world.isClientSide && tile.attackTick++ > 60) {
            tile.attackTick = 0;
            tile.targets.clear();
            tile.attack = false;
            List<LivingEntity> targets = FinderfeedMathHelper.TargetFinding.getAllValidTargetsFromVec(
                    LivingEntity.class,
                    20,
                    world,
                    FinderfeedMathHelper.TileEntityThings.getTileEntityCenter(tile),
                    (entity) -> {
                        return !(entity instanceof Player);
                    }
            );
            if (targets.size() != 0) {
                LivingEntity lastTarget = targets.get(world.random.nextInt(targets.size()));
                lastTarget.hurt(DamageSource.MAGIC, 4);
                List<LivingEntity> invalidTargets = new ArrayList<>();
                invalidTargets.add(lastTarget);
                for (int i = 0; i < 4; i++) {
                    List<LivingEntity> secondaryTargets = FinderfeedMathHelper.TargetFinding.getAllValidTargetsFromVec(
                            LivingEntity.class,
                            20,
                            world,
                            lastTarget.position().add(0, lastTarget.getBbHeight()*1.1 / 2, 0),
                            (entity) -> {
                                return !(entity instanceof Player) && !invalidTargets.contains(entity);
                            }
                    );
                    if (secondaryTargets.size() != 0) {
                        lastTarget = secondaryTargets.get(world.random.nextInt(secondaryTargets.size()));
                        lastTarget.hurt(DamageSource.MAGIC, 5);
                        invalidTargets.add(lastTarget);
                    }else{
                        break;
                    }
                }

                tile.targets.add(FinderfeedMathHelper.TileEntityThings.getTileEntityCenter(pos));
                invalidTargets.forEach((trg) -> {
                    tile.targets.add(trg.position().add(0, trg.getBbHeight() / 2, 0));
                });
                tile.attack = true;
                world.sendBlockUpdated(pos, state, state, 3);

            }
        }

        if (world.isClientSide){
            if (tile.attack){
                tile.attack = false;
                for (int i = 0;i < tile.targets.size()-1;i++){
                    Vec3 between = tile.targets.get(i+1).subtract(tile.targets.get(i));
                    float multiplier = 3;
                    Vec3 normal = between.normalize().multiply(1/multiplier,1/multiplier,1/multiplier);
                    for (float g = 1;g < between.length()*multiplier;g++){
                        Vec3 position = tile.targets.get(i).add(normal.multiply(g,g,g));
                        world.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),position.x,position.y,position.z,0,0,0);
                    }
                }
                tile.targets.clear();
            }
        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(worldPosition,3,this.save(new CompoundTag()));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
        super.onDataPacket(net, pkt);
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
    public CompoundTag save(CompoundTag cmp) {
        cmp.putInt("list_length",targets.size());
        for (int i = 0;i < targets.size();i++){
            CompoundNBTHelper.writeVec3("vec"+i,targets.get(i),cmp);
        }
        cmp.putBoolean("attack",attack);
        cmp.putInt("attackTick",attackTick);
        return super.save(cmp);
    }

    @Override
    public UUID getOwner() {
        return OWNER;
    }

    @Override
    public void setOwner(UUID OWNER) {
        this.OWNER = OWNER;
    }

}
