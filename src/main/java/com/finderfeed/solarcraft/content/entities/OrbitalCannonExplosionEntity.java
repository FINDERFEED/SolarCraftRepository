package com.finderfeed.solarcraft.content.entities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.FDMathHelper;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class OrbitalCannonExplosionEntity extends Entity {

    private static final int RADIUS_OF_RING = 5;
    private static final int EXPLOSION_TICK = 10;

    private int radius;
    private int depth;
    private int blockCorrosionRadius;
    private int explosionTick = 0;
    private int ringTick = 0;
    private List<ExplosionRing> rings;
    private CompletableFuture<Boolean> ringCompleter;
    private Random random = new Random();


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
            if (rings == null || ringCompleter == null) {
                ringCompleter = CompletableFuture.supplyAsync(()->{
                    this.initRings();
                    return true;
                });
            }
            if (ringCompleter.isDone()){
                this.explode2((ServerLevel) level);
            }
            /*
            if (ringCompleter.isDone()) {
                this.explode(explosionTick);
                if (ringTick == 0) {
                    explosionTick++;
                }
            }
             */
        }

    }

    private HashSet<LevelChunk> chunks = new HashSet<>();
//        for (LevelChunk chunk : chunks){
//            for (ServerPlayer player : serverLevel.getPlayers((p)->true)){
//                player.connection.send(new ClientboundLevelChunkWithLightPacket(chunk, serverLevel.getLightEngine(),
//                        (BitSet)null, (BitSet)null, true));
//
//            }
//        }
    private record SectionData(ShortSet set,SectionPos pos){}
    private HashMap<LevelChunkSection, SectionData> sections = new HashMap<>();
    //Version 2

    private void explode2(ServerLevel serverLevel){
        for (ExplosionRing ring : rings){
            for (List<BlockPos> list : ring.splittedRing){
                for (BlockPos pos : list){
                    processBlockPos(pos);
                }
            }
        }

//        for (Map.Entry<LevelChunkSection,SectionData> entry : sections.entrySet()){
//            SectionData data = entry.getValue();
//            for (ServerPlayer player : serverLevel.getPlayers(p->true)){
//                player.connection.send(new ClientboundSectionBlocksUpdatePacket(data.pos(),data.set(),entry.getKey(),true));
//            }
//        }
        for (LevelChunk chunk : chunks){
            chunk.setUnsaved(true);
            for (ServerPlayer player : serverLevel.getPlayers((p)->true)){
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
                        Blocks.AIR.defaultBlockState()
                );
                //((ServerLevel)level).getLightEngine().checkBlock(pos);
                chunks.add(chunk);
//                if (sections.containsKey(section)){
//                    sections.get(section).set().add(SectionPos.sectionRelativePos(pos));
//                }else {
//                    ShortOpenHashSet set = new ShortOpenHashSet();
//                    set.add(SectionPos.sectionRelativePos(pos));
//                    sections.put(section,new SectionData(set,SectionPos.of(pos)));
//                }
            }
        }else{
            level.destroyBlock(pos,true);
        }
    }

    //Version 1 below

    private void explode(int tick){
        if (explosionTick % EXPLOSION_TICK == 0){
            int ringN = tick / EXPLOSION_TICK;
            if (ringN < rings.size()) {
                ExplosionRing ring = rings.get(ringN);
                List<List<BlockPos>> ringParts = ring.splittedRing();
                if (ringTick < ringParts.size()) {
                    List<BlockPos> toRemove = ringParts.get(ringTick);
                    for (BlockPos pos : toRemove){
                        this.tryDestroyBlock(pos);
                    }
                    ringTick++;
                }else{
                    ringTick = 0;
                }
            }else{
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    private void tryDestroyBlock(BlockPos pos){
        boolean a = FDMathHelper.isBetweenEllipses(
                pos.getX() - this.blockPosition().getX(),
                pos.getY() - this.blockPosition().getY(),
                pos.getZ() - this.blockPosition().getZ(),
                radius - blockCorrosionRadius,
                depth - blockCorrosionRadius,
                radius,
                depth
        );
        int flag = Block.UPDATE_ALL;
        if (!a) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), flag);
        }else{
            if (this.shouldBeObsidianOrMagma(pos)) {
                float c = this.random.nextFloat();

                if (c < 0.33) {
                    level.setBlock(pos, Blocks.OBSIDIAN.defaultBlockState(), flag);
                } else if (c < 0.66) {
                    level.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), flag);
                }else{

                    level.setBlock(pos,Blocks.AIR.defaultBlockState(),flag);
                }
            }else{
                BlockState below = level.getBlockState(pos.below());
                if (!below.isAir()){
                    float c = this.random.nextFloat();
                    if (c > 0.5){
                        level.setBlock(pos,Blocks.FIRE.defaultBlockState(),flag);
                    }else{
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), flag);
                    }
                }else{
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), flag);
                }


            }
        }
    }

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

    private void initRings(){
        rings = new ArrayList<>();
        if (radius > depth){
            float mod = 1f / radius;
            float nDepth = depth*mod;
            int rm = this.radius;
            for (int i = 0; rm > 0; i++){
                int rad = Math.min(RADIUS_OF_RING,rm);
                float innerRad = i*RADIUS_OF_RING;
                float innerDepth = i*RADIUS_OF_RING*nDepth;
                this.rings.add(constructRing(innerRad,innerDepth, innerRad + rad,innerDepth + rad*nDepth));
                rm -= rad;
            }
        }else{
            float mod = 1f / depth;
            float nRad = radius*mod;
            int rm = this.depth;
            for (int i = 0; rm > 0; i++){
                int rad = Math.min(RADIUS_OF_RING,rm);
                float innerRad = i*RADIUS_OF_RING*nRad;
                float innerDepth = i*RADIUS_OF_RING;
                this.rings.add(constructRing(innerRad,innerDepth, innerRad + rad*nRad,innerDepth + rad));
                rm -= rad;
            }

        }
    }

    private ExplosionRing constructRing(float innerRad,float innerDepth,float endRad,float endDepth){
        AABB outerBox = new AABB(-endRad,-endDepth,-endRad,endRad,endDepth,endRad);
        ExplosionRing ring = new ExplosionRing(new ArrayList<>());
        ring.splittedRing().add(new ArrayList<>());

        int ringPartLimit = 400;
        AtomicInteger index = new AtomicInteger(0);
        BlockPos.betweenClosedStream(outerBox).forEach(pos->{
            if (this.shouldPosBeAdded(pos,innerRad,innerDepth,endRad,endDepth)){
                BlockPos ps = new BlockPos(pos.getX(),pos.getY(),pos.getZ()).offset(blockPosition());
                List<BlockPos> list = ring.splittedRing.get(index.get());
                if (list.size() >= ringPartLimit){
                    List<BlockPos> newList = new ArrayList<>();
                    newList.add(ps);
                    ring.splittedRing.add(newList);
                    index.incrementAndGet();
                }else{
                    list.add(ps);
                }
            }
        });

        return ring;
    }

    private boolean shouldPosBeAdded(BlockPos pos,float innerRad,float innerDepth,float endRad,float endDepth){
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
        return FDMathHelper.isBetweenEllipses(pos.getX() + 0.5f,pos.getY() + 0.5f,pos.getZ() + 0.5f,
                innerRad,innerDepth,endRad,endDepth);
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
        this.explosionTick = tag.getInt("explosion_tick");
        this.ringTick = tag.getInt("ring_tick");
        this.depth = tag.getInt("explosion_depth");
        this.blockCorrosionRadius = tag.getInt("corrosion");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("explosion_radius",this.radius);
        tag.putInt("explosion_tick",this.explosionTick);
        tag.putInt("ring_tick",this.ringTick);
        tag.putInt("explosion_depth",this.depth);
        tag.putInt("corrosion",this.blockCorrosionRadius);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public record ExplosionRing(List<List<BlockPos>> splittedRing){ }
}
