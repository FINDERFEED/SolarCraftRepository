package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.UpdateChunkPacket;
import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class OrbitalCannonExplosionEntity extends Entity {

    public static final EntityDataAccessor<Integer> RADIUS = SynchedEntityData.defineId(OrbitalCannonExplosionEntity.class,
            EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> DEPTH = SynchedEntityData.defineId(OrbitalCannonExplosionEntity.class,
            EntityDataSerializers.INT);

    public static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.defineId(OrbitalCannonExplosionEntity.class,
            EntityDataSerializers.INT);


    private int radius;
    private int depth;
    private int blockCorrosionRadius;
    private int explosionTimer = 20;
    private int minExplosionDurationTicker = 200;

    private HashMap<ChunkPos,List<BlockPos>> blocksToExplode;

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
            this.entityData.set(RADIUS,radius);
            this.entityData.set(DEPTH,depth);
            this.entityData.set(TIMER,explosionTimer);
            this.damageMobs();
            if (blocksToExplode == null || blocksCompleter == null) {

                blocksCompleter = CompletableFuture.supplyAsync(()->{
                    this.initExplodePositions();
                    return true;
                });
            }
            if (blocksCompleter.isDone()){

                this.onCompleterFinish();
            }
        }else{

        }

    }

    private void onCompleterFinish(){
        if (!blocksToExplode.isEmpty()){
            this.progressExplosion();
        }else{
            this.waitForRemovalAndRemove((ServerLevel) level);
        }
        minExplosionDurationTicker--;
    }

    private void waitForRemovalAndRemove(ServerLevel serverLevel){
        if (explosionTimer <= 0 && minExplosionDurationTicker <= 0){
            PlayerList list = serverLevel.getServer().getPlayerList();
            ThreadedLevelLightEngine lightEngine = serverLevel.getChunkSource().chunkMap.lightEngine;
            for (LevelChunk chunk : chunksToUpdate){
                chunk.setUnsaved(true);
                List<ServerPlayer> serverPlayers = serverLevel.getChunkSource().chunkMap.getPlayers(chunk.getPos(),false);

                lightEngine.lightChunk(chunk,true).thenRun(()->{
                    List<ServerPlayer> players = serverLevel.getChunkSource().chunkMap.getPlayers(chunk.getPos(),false);
                    for (ServerPlayer player : players) {
                        player.connection.send(new ClientboundLightUpdatePacket(chunk.getPos(), serverLevel.getLightEngine(),
                                (BitSet) null, (BitSet) null));
                    }
                });
                for (ServerPlayer player : serverPlayers){
                    SCPacketHandler.INSTANCE.sendTo(new UpdateChunkPacket(chunk),player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                }
            }
            this.remove(RemovalReason.DISCARDED);
            System.out.println("finished!");
            ChunkPos pos = new ChunkPos(this.getOnPos());
            Helpers.loadChunkAtPos((ServerLevel) level,new BlockPos(pos.getMinBlockX(),0,pos.getMinBlockZ()),false,true);
        }else{
            explosionTimer--;
        }
    }

    private void progressExplosion(){
        if (explosionTimer <= 0) {
            System.out.println("exploded");
            this.explode((ServerLevel) level);
            explosionTimer = 30;
        }else{
            explosionTimer--;
        }
    }

    private void spawnParticles(){
        int radius = this.entityData.get(RADIUS);

    }


    private void damageMobs(){
        AABB box = new AABB(-radius,-depth,-radius,radius,depth,radius).move(this.position());
        for (Entity e : level.getEntitiesOfClass(Entity.class,box,entity->{
            return entity.position().multiply(1,0,1).distanceTo(this.position().multiply(1,0,1)) < radius &&
                    entity.position().multiply(0,1,0).distanceTo(this.position().multiply(0,1,0)) < depth;
        })){
            if (e instanceof LivingEntity living) {
                living.hurt(SolarcraftDamageSources.ORBITAL_EXPLOSION, 1000000f);
            }else if (e instanceof ItemEntity item){
                item.remove(RemovalReason.KILLED);
            }
        }
    }

    public int getTimer(){
        return this.entityData.get(TIMER);
    }

    private void initExplodePositions(){
        this.blocksToExplode = new HashMap<>();
        BlockPos.betweenClosed(-radius,-depth,-radius,radius,depth,radius).forEach(pos->{
            if (this.shouldPosBeAdded(pos)){
                BlockPos toAdd = new BlockPos(pos).offset(blockPosition());
                ChunkPos cPos = new ChunkPos(toAdd);
                List<BlockPos> l = this.blocksToExplode.get(cPos);
                if (l == null){
                    blocksToExplode.put(cPos, Lists.newArrayList(toAdd));
                }else{
                    l.add(toAdd);
                }
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

        Iterator<ChunkPos> posi = blocksToExplode.keySet().iterator();
        int iters = 75;
        long time = System.nanoTime();
        while (posi.hasNext() && iters > 0){
            ChunkPos pos = posi.next();
            for (BlockPos p : blocksToExplode.get(pos)){
                this.processBlockPos(serverLevel,p);
            }
            posi.remove();
            iters--;
        }
        System.out.println("time spent: " + (System.nanoTime() - time)/1000000000f);




    }
    private void processBlockPos(ServerLevel level, BlockPos pos){
        if (!level.getBlockState(pos).hasBlockEntity()){
            if (!level.isOutsideBuildHeight(pos)){

                LevelChunk chunk = level.getChunkAt(pos);
                int index = chunk.getSectionIndex(pos.getY());
                LevelChunkSection section = chunk.getSection(index);
                int x = pos.getX() & 15;
                int y = pos.getY() & 15;
                int z = pos.getZ() & 15;
                BlockState state = getExplosionState(pos);
                section.setBlockState(
                          x,y,z,
                        state
                );


                List<Heightmap.Types> l = List.of(
                        Heightmap.Types.MOTION_BLOCKING,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        Heightmap.Types.OCEAN_FLOOR,
                        Heightmap.Types.WORLD_SURFACE
                );
                for (var m : chunk.getHeightmaps()){
                    if (l.contains(m.getKey())){
                        m.getValue().update(x,pos.getY(),z,state);
                    }
                }


                chunksToUpdate.add(chunk);
            }
        }else{
            level.destroyBlock(pos,true);
        }
    }

    private BlockState getExplosionState(BlockPos pos){
        if (level.getBlockState(pos).getBlock() == Blocks.BEDROCK){
            return Blocks.BEDROCK.defaultBlockState();
        }
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
        this.entityData.define(RADIUS,this.radius);
        this.entityData.define(DEPTH,this.depth);
        this.entityData.define(TIMER,this.explosionTimer);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.radius = tag.getInt("explosion_radius");
        this.depth = tag.getInt("explosion_depth");
        this.blockCorrosionRadius = tag.getInt("corrosion");
        this.entityData.set(RADIUS,radius);
        this.entityData.set(DEPTH,depth);
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return true;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double dist) {
        return true;
    }
}
