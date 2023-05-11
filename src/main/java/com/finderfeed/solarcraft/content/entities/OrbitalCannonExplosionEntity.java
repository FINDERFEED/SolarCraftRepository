package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class OrbitalCannonExplosionEntity extends Entity {
    private int radius;
    private int depth;
    private int blockCorrosionRadius;
    private List<BlockPos> blocksToExplode;
    private CompletableFuture<Boolean> blocksCompleter;
    private Random random = new Random();
    private HashSet<LevelChunk> chunksToUpdate = new HashSet<>();


    public OrbitalCannonExplosionEntity(EntityType<?> type, Level level){
        super(type,level);
    }

    public OrbitalCannonExplosionEntity(Level level,int maxBlockRadius,int maxExplosionDepth,int blockCorrosionRadius) {
        this(SolarcraftEntityTypes.ORBITAL_EXPLOSION.get(), level);
        this.radius = maxBlockRadius;
        this.depth = maxExplosionDepth;
        this.blockCorrosionRadius = blockCorrosionRadius;
    }


    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (blocksToExplode == null || blocksCompleter == null) {
                blocksCompleter = CompletableFuture.supplyAsync(()->{
                    this.initExplodePositions();
                    return true;
                });
            }
            if (blocksCompleter.isDone()){
                this.explode((ServerLevel) level);
            }
        }

    }

    private void initExplodePositions(){
        this.blocksToExplode = new ArrayList<>();
        BlockPos.betweenClosed(-radius,-depth,-radius,radius,depth,radius).forEach(pos->{
            if (this.shouldPosBeAdded(pos)){
                this.blocksToExplode.add(new BlockPos(pos).offset(blockPosition()));
            }
        });
    }
    private boolean shouldPosBeAdded(BlockPos pos){
        boolean a = FDMathHelper.isBetweenEllipses(
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                radius - blockCorrosionRadius,
                depth - blockCorrosionRadius,
                radius,
                depth
        );
        if (a && this.random.nextFloat() < 0.3){
            return false;
        }

        return FDMathHelper.isInEllipse(pos.getX() + 0.5f,pos.getY() + 0.5f,pos.getZ() + 0.5f, radius,depth);
    }

    private void explode(ServerLevel serverLevel){
        for (BlockPos pos : blocksToExplode){
            this.processBlockPos(pos);
        }
        for (LevelChunk chunk : chunksToUpdate){
            chunk.setUnsaved(true);
            Vec3 chunkPos = new Vec3(chunk.getPos().getMiddleBlockX(),0,chunk.getPos().getMiddleBlockZ());
            for (ServerPlayer player : serverLevel.getPlayers((p)->{
                return p.position().multiply(1,0,1).distanceTo(chunkPos) < 300;
            })){
                player.connection.send(new ClientboundLevelChunkWithLightPacket(chunk, serverLevel.getLightEngine(),
                        (BitSet)null, (BitSet)null, true));

            }
        }
        this.remove(RemovalReason.DISCARDED);
    }
    private void processBlockPos(BlockPos pos){
        if (!level.getBlockState(pos).hasBlockEntity()){
            if (!level.isOutsideBuildHeight(pos)){
                LevelChunk chunk = level.getChunkAt(pos);
                int index = chunk.getSectionIndex(pos.getY());
                LevelChunkSection section = chunk.getSection(index);
                int x = pos.getX() & 15;
                int y = pos.getY() & 15;
                int z = pos.getZ() & 15;
                section.setBlockState(
                          x,y,z,
                        getExplosionState(pos)
                );
                chunksToUpdate.add(chunk);
            }
        }else{
            level.destroyBlock(pos,true);
        }
    }

    private BlockState getExplosionState(BlockPos pos){
        boolean a = FDMathHelper.isBetweenEllipses(
                pos.getX() - this.blockPosition().getX(),
                pos.getY() - this.blockPosition().getY(),
                pos.getZ() - this.blockPosition().getZ(),
                radius - blockCorrosionRadius,
                depth - blockCorrosionRadius,
                radius,
                depth
        );

        if (!a) {
            return Blocks.AIR.defaultBlockState();
        }else{
            if (this.shouldBeObsidianOrMagma(pos)) {
                float c = this.random.nextFloat();

                if (c < 0.33) {
                    return Blocks.OBSIDIAN.defaultBlockState();
                } else if (c < 0.66) {
                    return Blocks.MAGMA_BLOCK.defaultBlockState();
                }else{
                    return Blocks.AIR.defaultBlockState();
                }
            }else{
                BlockState below = level.getBlockState(pos.below());
                if (!below.isAir()){
                    float c = this.random.nextFloat();
                    if (c > 0.5){
                        return Blocks.FIRE.defaultBlockState();
                    }else{
                        return Blocks.AIR.defaultBlockState();
                    }
                }else{
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
    }

    //Version 1 below





    private boolean shouldBeObsidianOrMagma(BlockPos pos){
        BlockState below = level.getBlockState(pos.below());
        if (!below.isAir() && !below.is(Blocks.OBSIDIAN)  && !below.is(Blocks.MAGMA_BLOCK)){
            return true;
        }
        BlockState above = level.getBlockState(pos.above());
        if (!above.isAir() && !above.is(Blocks.OBSIDIAN)  && !above.is(Blocks.MAGMA_BLOCK)){
            return true;
        }
        return false;
    }



    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.radius = tag.getInt("explosion_radius");
//        this.explosionTick = tag.getInt("explosion_tick");
//        this.ringTick = tag.getInt("ring_tick");
        this.depth = tag.getInt("explosion_depth");
        this.blockCorrosionRadius = tag.getInt("corrosion");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("explosion_radius",this.radius);
//        tag.putInt("explosion_tick",this.explosionTick);
//        tag.putInt("ring_tick",this.ringTick);
        tag.putInt("explosion_depth",this.depth);
        tag.putInt("corrosion",this.blockCorrosionRadius);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
