package com.finderfeed.solarforge.helpers;

import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.ClearingRitual;
import com.finderfeed.solarforge.content.entities.CrystalBossEntity;
import com.finderfeed.solarforge.content.entities.RunicElementalBoss;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.local_library.OwnedBlock;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.RadiantLandCleanedData;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.Progression;

import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.misc_things.StateAndTag;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.*;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.progression_tree.ProgressionTree;
import com.finderfeed.solarforge.content.items.solar_lexicon.packets.UpdateProgressionOnClient;
import com.finderfeed.solarforge.registries.SolarcraftGamerules;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.network.NetworkDirection;

import java.util.*;
import java.util.function.Predicate;


public class Helpers {



    public static final Random RANDOM = new Random();
    public static final String FRAGMENT = "solar_forge_fragment_";
    public static double GRAVITY_METRES_PER_SEC = 20;
    public static double GRAVITY_VELOCITY = 0.05;
    public static String PROGRESSION = "solar_forge_progression_";
    public static BlockPos NULL_POS = new BlockPos(0,-100,0);



    public static boolean isRadiantLandCleanedServer(ServerLevel level){
        RadiantLandCleanedData data = level.getServer().overworld()
                .getDataStorage()
                .computeIfAbsent(RadiantLandCleanedData::load,()->new RadiantLandCleanedData(false),"is_radiant_land_cleaned");
        return data.isCleaned();
    }

    public static boolean isSpellGriefingEnabled(ServerLevel level){
        return level.getGameRules().getBoolean(SolarcraftGamerules.SPELL_GRIEFING);
    }


    public static void drawBoundedText(PoseStack matrices,int posx,int posy,int bound,String s,int color){
        StringBuilder str = new StringBuilder(s);
        for (int a = 0;a < s.length();a++) {
            if (a % bound == 0 ){
                if (String.valueOf(str.charAt(a)).equals(" ")) {
                    str.insert(a, "=");
                }else{

                        int k = a;
                        while (!String.valueOf(str.charAt(k)).equals(" ")) {
                            k--;
                            if (k == -1){
                                break;
                            }
                        }
                        if (k != -1) {
                            str.insert(k+1, "=");
                        }

                }
            }
        }

        String[] string = str.toString().split("=");

        int y = 0;
        for (String strings : string){
            GuiComponent.drawString(matrices, Minecraft.getInstance().font, strings,posx,posy+y,color);
            y+=10;
        }

    }

    public static List<LevelChunk> getChunksInRadius(Level level,BlockPos pos,int radius){
        List<LevelChunk> chunks = new ArrayList<>();
        for (int i = -radius;i <= radius;i++){
            for (int g = -radius;g <= radius;g++){
                chunks.add(level.getChunkAt(pos.offset(i*16,0,g*16)));
            }
        }
        return chunks;
    }

    public static <T extends BlockEntity> List<T> collectTilesInChunks(BlockEntityType<T> type, Level world, BlockPos pos, int radius){
        List<LevelChunk> chunks = getChunksInRadius(world,pos,radius);
        List<T> tiles = new ArrayList<>();
        chunks.forEach(chunk->chunk.getBlockEntities().forEach((p,tile)->{
            if (tile.getType() == type){
                tiles.add((T)tile);
            }
        }));
        return tiles;
    }

    public static boolean isVulnerable(Entity ent){
        return ent.invulnerableTime == 0;
    }

    public static boolean hasPlayerCompletedProgression(Progression ach, Player entity){

        return ach == null ? true : entity.getPersistentData().getBoolean("solar_forge_progression_"+ach.getAchievementCode());
    }

    public static boolean canPlayerUnlock(Progression ach, Player entity){
        ProgressionTree tree = ProgressionTree.INSTANCE;
        for (Progression a : tree.getAchievementRequirements(ach)){
            if (!entity.getPersistentData().getBoolean("solar_forge_progression_"+a.getAchievementCode())){
                return false;
            }
        }
        return true;
    }

    public static void setProgressionCompletionStatus(Progression ach, Player pe, boolean a){
        pe.getPersistentData().putBoolean("solar_forge_progression_"+ach.getAchievementCode(),a);
    }


    public static LevelChunk[] getSurroundingChunks(Level level,BlockPos worldPosition){
        return new LevelChunk[]{level.getChunkAt(worldPosition),level.getChunkAt(worldPosition.offset(16,0,0)),level.getChunkAt(worldPosition.offset(0,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,0)),level.getChunkAt(worldPosition.offset(0,0,-16)),level.getChunkAt(worldPosition.offset(16,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,-16)),level.getChunkAt(worldPosition.offset(16,0,-16)),level.getChunkAt(worldPosition.offset(-16,0,16))};
    }

    public static double getGipotenuza(double a,double b){
        return Math.sqrt(a*a + b*b);
    }


    public static boolean isReachable(Level world, BlockPos pos1, BlockPos pos2,double radius){
        Vec3 vec1 = new Vec3(pos1.getX()+0.5f,pos1.getY()+0.5f,pos1.getZ()+0.5f);
        Vec3 vec2 = new Vec3(pos2.getX()+0.5f,pos2.getY()+0.5f,pos2.getZ()+0.5f);
        Vec3 vector = new Vec3(vec2.x - vec1.x,vec2.y - vec1.y,vec2.z - vec1.z);
        ClipContext ctx = new ClipContext(vec1.add(vector.normalize()),vec2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
        BlockHitResult result = world.clip(ctx);
        boolean first = world.getBlockState(result.getBlockPos()).getBlock() == world.getBlockState(pos2).getBlock();
        boolean second = Helpers.equalsBlockPos(result.getBlockPos(),pos2);
//        result.getBlockPos().equals(pos2);
        boolean third = vector.length() <= radius;

        if (first && second && third){
            return true;
        }
        return false;
    }


    //structure towards north, initPos is the pos at the lowest by y lowest by z left corner

//    public static boolean checkStructure(Level world,BlockPos initPos,Multiblock struct,boolean ignoreOtherBlocks){
//        BlockPos pos = initPos;
//        String[][] structure = struct.struct;
//        for (int i = 0;i < structure.length;i++){
//            for (int g = 0;g < structure[i].length;g++){
//                String line = structure[i][g];
//                    for (int k = 0;k < line.length();k++){
//                        //here the checking begins
//                        char c = line.charAt(k);
//
//                        if (c != ' ') {
//                                if (!checkBlock(world,pos.offset(k,i,g),struct.getStateAndTag(c))){
//                                    return false;
//                                }
//                        }else{
//                            if (!ignoreOtherBlocks){
//                                if (!checkBlock(world,pos.offset(k,i,g),struct.getStateAndTag(c))){
//                                    return false;
//                                }
//                            }
//                        }
//                        //here ends
//                    }
//            }
//        }
//        return true;
//    }
//    world.getBlockState(initPos.offset(k, i, g))
//    private static boolean checkBlock(Level world,BlockPos pos, StateAndTag stateAndTag){
//        TagKey<Block> tag;
//        if ((tag = stateAndTag.getTag()) == null){
//            return StateAndTag.checkBlockState(world.getBlockState(pos),stateAndTag.getState(),stateAndTag.isIgnoreFacing());
//        }else{
//            return world.getBlockState(pos).is(tag);
//        }
//    }

    public static double blocksPerSecondToVelocity(double a){
        return a*0.05;
    }


     public static Collection<BlockPos> getSurroundingBlockPositionsVertical(BlockPos pos, Direction dir){
        List<BlockPos> positions = new ArrayList<>();
        positions.add(pos.above());
        positions.add(pos.below());
        if (dir.equals(Direction.WEST) || dir.equals(Direction.EAST)){
            positions.add(pos.offset(0,1,1));
            positions.add(pos.offset(0,1,-1));
            positions.add(pos.offset(0,0,1));
            positions.add(pos.offset(0,0,-1));
            positions.add(pos.offset(0,-1,1));
            positions.add(pos.offset(0,-1,-1));

        }
        if (dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH)){
            positions.add(pos.offset(1,1,0));
            positions.add(pos.offset(-1,1,0));
            positions.add(pos.offset(1,0,0));
            positions.add(pos.offset(-1,0,0));
            positions.add(pos.offset(1,-1,0));
            positions.add(pos.offset(-1,-1,0));
        }


        return positions;
     }

    public static Collection<BlockPos> getSurroundingBlockPositionsHorizontal(BlockPos pos){
        List<BlockPos> positions = new ArrayList<>();
        positions.add(pos.west());
        positions.add(pos.east());
        positions.add(pos.north());
        positions.add(pos.south());
        positions.add(pos.offset(1,0,1));
        positions.add(pos.offset(1,0,-1));
        positions.add(pos.offset(-1,0,1));
        positions.add(pos.offset(-1,0,-1));
        return positions;
    }
    public static boolean isEntityReachable(Level world, BlockPos pos1, BlockPos pos2){
        Vec3 vec1 = new Vec3(pos1.getX()+0.5f,pos1.getY()+0.5f,pos1.getZ()+0.5f);
        Vec3 vec2 = new Vec3(pos2.getX()+0.5f,pos2.getY()+1.25f,pos2.getZ()+0.5f);
        ClipContext ctx = new ClipContext(vec2,vec1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,null);
        BlockHitResult result = world.clip(ctx);

        if (result.getBlockPos().equals(pos1)){
            return true;
        }

        return false;
    }




    public static Vec3 calculateVelocity(Vec3 pos1,Vec3 pos2){
        return new Vec3(pos2.x - pos1.x,pos2.y - pos1.y,pos2.z - pos1.z).normalize();
    }

    public static Vec3 getBlockCenter(BlockPos pos){
        return new Vec3(pos.getX() +0.5,pos.getY()+0.5,pos.getZ()+0.5);
    }


    public static void updateProgression(ServerPlayer player){
        for (Progression a : Progression.allProgressions) {

            SolarForgePacketHandler.INSTANCE.sendTo(new UpdateProgressionOnClient(a.getAchievementCode(), hasPlayerCompletedProgression(a,player)),
                    player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void updateFragmentsOnClient(ServerPlayer player){
        SolarForgePacketHandler.INSTANCE.sendTo(new UpdateFragmentsOnClient(player),
                player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void updateClientRadiantLandStateForPlayer(ServerPlayer player){
        SolarForgePacketHandler.INSTANCE.sendTo(new SetClientRadiantLandStatePacket(ClearingRitual.getRLState((ServerLevel) player.level)),
                player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void updateClientRadiantLandStateForPlayer(ServerPlayer player,boolean state){
        SolarForgePacketHandler.INSTANCE.sendTo(new SetClientRadiantLandStatePacket(state),
                player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void forceChunksReload(ServerPlayer playerEntity){

        SolarForgePacketHandler.INSTANCE.sendTo(new ReloadChunks(),playerEntity.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
    public static List<BlockPos> getBlockPositionsByDirection(Direction dir,BlockPos mainpos,int count){
        List<BlockPos> pos = new ArrayList<>();
        for (int i = 0;i <= count;i++){
            if (dir.equals(Direction.UP)){
                pos.add(mainpos.offset(0,i,0));
            }else if (dir.equals(Direction.DOWN)){
                pos.add(mainpos.offset(0,-i,0));
            }else if (dir.equals(Direction.NORTH)){
                pos.add(mainpos.offset(0,0,-i));
            }else if (dir.equals(Direction.SOUTH)){
                pos.add(mainpos.offset(0,0,i));
            }else if (dir.equals(Direction.WEST)){
                pos.add(mainpos.offset(-i,0,0));
            }else{
                pos.add(mainpos.offset(i,0,0));
            }
        }

        return pos;
    }

    public static boolean equalsBlockPos(BlockPos pos1,BlockPos pos2){
        return (pos1.getX() == pos2.getX()) && (pos1.getY() == pos2.getY()) && (pos1.getZ() == pos2.getZ());
    }

    public static Direction getRandomHorizontalDirection(boolean exclude, Direction whatToExclude, Random rnd){
        Direction[] horizontal = {Direction.EAST,Direction.NORTH,Direction.SOUTH,Direction.WEST};

        if (exclude){
            Direction direction = horizontal[rnd.nextInt(4)];
            while (direction.equals(whatToExclude)){
                direction = horizontal[rnd.nextInt(4)];
            }
            return direction;
        }
        return horizontal[rnd.nextInt(4)];
    }

    public static void triggerToast(Progression ach, Player player){
        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(ach.getId()), ((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void fireProgressionEvent(Player playerEntity, Progression ach){
        if (!Helpers.hasPlayerCompletedProgression(ach,playerEntity) && Helpers.canPlayerUnlock(ach,playerEntity)) {
            MinecraftForge.EVENT_BUS.post(new ProgressionUnlockEvent(playerEntity, ach));
        }
    }


    public static void updateRunicEnergyOnClient(RunicEnergy.Type type,float amount,Player player){
        SolarForgePacketHandler.INSTANCE.sendTo(new UpdateEnergyOnClientPacket(type, amount),
                ((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void triggerProgressionShader(Player playerEntity){
        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerProgressionShaderPacket(),
                ((ServerPlayer)playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }






    public static List<LevelChunk> getSurroundingChunks5Radius(BlockPos pos,Level world){
        List<LevelChunk> chunks = new ArrayList<>();
        for (int i = -2; i <=2; i++){
            for (int g = -2; g <=2; g++){
                chunks.add(world.getChunkAt(pos.offset(i*16,0,g*16)));
            }
        }
        return chunks;
    }

    public static boolean isDay(Level world){
        return world.getDayTime() % 24000 <= 12000;
    }

    public static void createSmallSolarStrikeParticleExplosion(Level world,Vec3 position,int intensity,float speedFactor,float spawnDistanceFactor){
        for (int x = -intensity; x < intensity+1;x++){
            for (int y = -intensity; y < intensity+1;y++){
                for (int z = -intensity; z < intensity+1;z++){
                    Vec3 offset = new Vec3(x,y,z).normalize().multiply(spawnDistanceFactor,spawnDistanceFactor,spawnDistanceFactor);
                    Vec3 finalpos = position.add(offset);
                    world.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),finalpos.x,finalpos.y,finalpos.z,offset.x*speedFactor,offset.y*speedFactor,offset.z*speedFactor);

                }
            }
        }
    }

    public static void createSmallSolarStrikeParticleExplosionWithLines(Level world,Vec3 position,int intensity,float speedFactor,float spawnDistanceFactor){
        for (int x = -intensity; x < intensity+1;x++){
            for (int y = -intensity; y < intensity+1;y++){
                for (int z = -intensity; z < intensity+1;z++){
                    Vec3 offset = new Vec3(x,y,z).normalize().multiply(spawnDistanceFactor,spawnDistanceFactor,spawnDistanceFactor);
                    Vec3 finalpos = position.add(offset);
                    world.addParticle(SolarcraftParticleTypes.SMALL_SOLAR_STRIKE_PARTICLE.get(),finalpos.x,finalpos.y,finalpos.z,offset.x*speedFactor,offset.y*speedFactor,offset.z*speedFactor);
                    world.addParticle(SolarcraftParticleTypes.SOLAR_EXPLOSION_PARTICLE.get(),finalpos.x,finalpos.y,finalpos.z,offset.x*(speedFactor+0.3),offset.y*(speedFactor+0.3),offset.z*(speedFactor+0.3));

                }
            }
        }
    }

    public static Vec3 randomVector(){
        Random rnd = new Random();
        return new Vec3(rnd.nextDouble()*2-1,rnd.nextDouble()*2-1,rnd.nextDouble()*2-1).normalize();
    }



    public static List<Vec3> findRandomGroundPositionsAround(Level world,Vec3 mainpos,int amount,int radius){
        List<Vec3> toreturn = new ArrayList<>();
        List<BlockPos> pos = new ArrayList<>();
        for (int i = 0; i > -2;i--){
            List<BlockPos> positions = findNormalBlockPositionsOnPlane(world,radius,new BlockPos(mainpos.x,mainpos.y+i,mainpos.z));
            pos.addAll(positions);
        }
        for (int i = 0; i < amount;i++){
            if (pos.size() == 0){
                break;
            }
            BlockPos toAdd = pos.get(world.random.nextInt(pos.size()));
            toreturn.add(getBlockCenter(toAdd).add(0,0.5,0));
            pos.remove(toAdd);
        }
        return toreturn;
    }

    public static void sendEnergyTypeToast(ServerPlayer player,RunicEnergy.Type type){
        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerEnergyTypeToast(type.id),player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
    }

    private static List<BlockPos> findNormalBlockPositionsOnPlane(Level world,int radius,BlockPos mainpos){
        List<BlockPos> toReturn = new ArrayList<>();
        for (int i = -radius; i <= radius;i++){
            for (int g = -radius; g <= radius;g++){
                if (FDMathHelper.isInCircle(i,g,radius)) {
                    BlockPos check = mainpos.offset(i, 0, g);
                    if (isNormal(world, check)) {
                        toReturn.add(check);
                    }
                }
            }
        }
        return toReturn;
    }
    private static boolean isNormal(Level world,BlockPos pos){
        return world.getBlockState(pos.above()).isAir() && !world.getBlockState(pos).isAir();
    }

    public static EntityHitResult getHitResult(Level level, Vec3 startPos, Vec3 endPos, Predicate<Entity> pr){
        double radius = endPos.subtract(startPos).length();
        AABB bb = new AABB(-radius*1.5f,-radius*1.5f,-radius*1.5f,radius*1.5f,radius*1.5f,radius*1.5f).move(startPos);
        return ProjectileUtil.getEntityHitResult(level,null,startPos,endPos,bb,pr,1);
    }

    public static void updateTile(BlockEntity tile){
        tile.setChanged();
        BlockState state = tile.getLevel().getBlockState(tile.getBlockPos());
        tile.getLevel().sendBlockUpdated(tile.getBlockPos(),state,state,3);
    }


    public static void setServerPlayerSpeed(ServerPlayer player,Vec3 speed){
        player.setDeltaMovement(speed);

        SolarForgePacketHandler.INSTANCE.sendTo(new SetSpeedPacket(speed),player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
    }

    public static boolean playerInBossfight(Player pl){
        return !pl.level.getEntitiesOfClass(LivingEntity.class,new AABB(-20,-20,-20,20,20,20)
                .move(pl.position()),(l)-> l instanceof CrystalBossEntity || l instanceof RunicElementalBoss).isEmpty();
    }



    public static class RunicEnergyRequestConstructor{

        private Map<RunicEnergy.Type,Double> costs = new HashMap<>();

        public RunicEnergyRequestConstructor add(RunicEnergy.Type type,double request){
            this.costs.put(type,request);
            return this;
        }

        public Map<RunicEnergy.Type,Double> build(){
            return costs;
        }
    }



    public static boolean isIn2DArrayBounds(int[][] arr,int x,int y){
        return x >= 0 && x < arr.length && y >= 0 && y < arr[0].length;
    }

    public static ClientboundBlockEntityDataPacket createTilePacket(BlockEntity tile, CompoundTag tag){
        return ClientboundBlockEntityDataPacket.create(tile,(til)-> tag);
    }

    public static Vec3 getPlayerShootPos(Player player){
        return player.position().add(0,player.getStandingEyeHeight(player.getPose(),player.getDimensions(player.getPose())),0);
    }


    public static List<BlockPos> getValidSpawningPositionsAround(Level world,BlockPos initPos,double radius,int maxHeightCheck,int maxYCheck){
        int rad = (int)Math.round(radius);
        List<BlockPos> candidates = new ArrayList<>();
        byte[][] xz = new byte[rad*2+1][rad*2+1];
        for (int x = -rad; x <= rad;x++){
            for (int z = -rad; z <= rad;z++) {
                for (int y = -maxYCheck;y <= maxYCheck;y++) {
                    if (xz[x + rad][z + rad] == 1) break;
                    if (x * x + z * z <= radius * radius) {
                        BlockPos checkPos = initPos.offset(x, y, z);
                        if (!world.getBlockState(checkPos).isAir()) {
                            if (isValidSpawnPos(world, checkPos, maxHeightCheck)) {
                                xz[x + rad][z + rad] = 1;
                                candidates.add(checkPos);
                            }
                        }
                    }
                }
            }
        }
        candidates.remove(initPos);
        return candidates;
    }

    public static boolean isValidSpawnPos(Level world,BlockPos pos,int heightCheck){
        for (int i = 0; i < heightCheck;i++){
            if (!world.getBlockState(pos.above(i+1)).isAir()){
                return false;
            }
        }
        return true;
    }


    public static AABB createAABBWithRadius(Vec3 pos,double horizontal,double vertical){
        return new AABB(-horizontal,-vertical,-horizontal,horizontal,vertical,horizontal).move(pos);
    }


    public static Vec3 posToVec(BlockPos pos){
        return new Vec3(pos.getX(),pos.getY(),pos.getZ());
    }

    public static BlockPos vecToPos(Vec3 pos){
        return new BlockPos(pos.x,pos.y,pos.z);
    }

    public static void sendDimBreak(ServerLevel world){
        world.getPlayers((e)->true).forEach((player)->{
            SolarForgePacketHandler.INSTANCE.sendTo(new DimensionBreakPacket(),player.connection.connection,NetworkDirection.PLAY_TO_CLIENT);
        });
    }
}
