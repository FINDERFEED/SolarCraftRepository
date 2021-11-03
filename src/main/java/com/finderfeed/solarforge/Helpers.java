package com.finderfeed.solarforge;

import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.capabilities.capability_mana.SolarForgeMana;
import com.finderfeed.solarforge.entities.CrystalBossEntity;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.for_future_library.helpers.FinderfeedMathHelper;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Progression;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.*;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.achievement_tree.AchievementTree;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.UpdateProgressionOnClient;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.NetworkDirection;

import java.util.*;
import java.util.function.Predicate;


public class Helpers {

    public static final String FRAGMENT = "solar_forge_fragment_";
//    public static final RandomPatchConfiguration SOLAR_FLOWER_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder((new WeightedStateProvider()).add(BlocksRegistry.ALGADIUM_BLOCK.get().defaultBlockState(), 2), SimpleBlockPlacer.INSTANCE)).tries(64).build();
    public static double GRAVITY_METRES_PER_SEC = 20;
    public static double GRAVITY_VELOCITY = 0.05;
    public static String PROGRESSION = "solar_forge_progression_";
    public static BlockPos NULL_POS = new BlockPos(0,-100,0);
    public static void drawBoundedText(PoseStack matrices,int posx,int posy,int bound,String s){
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
            GuiComponent.drawString(matrices, Minecraft.getInstance().font, strings,posx,posy+y,0xffffff);
            y+=10;
        }

    }

    public static boolean isVulnerable(Entity ent){
        return ent.invulnerableTime == 0;
    }

    public static String getAchievementDescription(Progression ach){
        return switch (ach) {
            case CRAFT_SOLAR_FORGE -> "The magical power of this machine allows me to get powerful abilities. Can it do more than just that?";
            case CRAFT_SOLAR_INFUSER -> "So now what? There was no instructions on how to use it...";
            case FIND_SOLAR_STONE -> "This magical stone shines brighter than sun.There should be a way to use it";
            case USE_SOLAR_INFUSER -> "It wasnt as hard as i thought it would be.";
            case ACQUIRE_SOLAR_DUST -> "I have finally acquired solar dust! But where to use it?";
            case ACQUIRE_COLD_STAR -> "The power of this item is very big. According to my experiments it can focus on light.";
            case FIND_INFUSER_DUNGEON -> "Is this some sort of an ancient altar?";
            case FIND_KEY_LOCK_DUNGEON -> "A very strange building... There should be more to it...";
            case FIND_KEY_SOURCE -> "A key? But for what?";
            case CRAFT_SOLAR_LENS -> "With the power of this magic piece of a star i can create better materials now!";
            case ACQUIRE_COLD_STAR_ACTIVATED -> "This altar just made it increase the power of the focused light! I need to find a usage for it.";
            case CRAFT_SOLAR_ENERGY_GENERATOR -> "This generator is much stronger than the one that i had before!";
            case TRANSMUTE_GEM -> "My guesses were true, lava in this forest is full of magic energy.";
            case FIND_INCINERATED_FOREST -> "It doesnt seem that this was caused by a natural disaster...";
            case TRADE_FOR_BLUE_GEM -> "What a rude man! At least he loves to share information... For money...";
            case DIMENSIONAL_SHARD_DUNGEON -> "They definitely dont want me to reveal their secrets.";
            case ENTER_NETHER -> "Despite its evil nature there are still some friendly creatures here, but anyway i need to be careful.";
            case SOLAR_RUNE -> "Maybe this runes will help me to decrypt the fragments?";
            case RUNE_ENERGY_CLAIM -> "Turns out i can collect this energy inside...myself?";
            case RUNE_ENERGY_DEPOSIT -> "It seems that this pylon contains some sort of energy.";
            case RUNIC_ENERGY_REPEATER -> "Energy from those pylons can be a solution of this problem!";
            case DIMENSION_CORE -> "A cursed world in all its grace.";
            case KILL_CRYSTAL_BOSS -> "High-tech defensive nightmare with a very valuable core.";
        };
    }

    public static boolean hasPlayerUnlocked(Progression ach, Player entity){

        return ach == null ? true : entity.getPersistentData().getBoolean("solar_forge_progression_"+ach.getAchievementCode());
    }

    public static boolean canPlayerUnlock(Progression ach, Player entity){
        AchievementTree tree = AchievementTree.loadTree();
        for (Progression a : tree.getAchievementRequirements(ach)){
            if (!entity.getPersistentData().getBoolean("solar_forge_progression_"+a.getAchievementCode())){
                return false;
            }
        }
        return true;
    }

    public static void setAchievementStatus(Progression ach, Player pe, boolean a){
        pe.getPersistentData().putBoolean("solar_forge_progression_"+ach.getAchievementCode(),a);
    }





    public static void spendMana(Player playerEntity,double count){
        if (!playerEntity.isDeadOrDying() && playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).isPresent() && !playerEntity.isCreative()){
            if (count < playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).getMana()) {
                playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).setMana(playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).getMana() - count);
            }
        }
    }
    public static boolean canCast(Player playerEntity,double count){
        if (!playerEntity.isDeadOrDying() && playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).isPresent() && !playerEntity.isCreative()) {
            if (count < playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).getMana()) {
                return true;
            }
        }else if (playerEntity.isCreative()){
            return true;

        }
        return false;
    }


    public static LevelChunk[] getSurroundingChunks(Level level,BlockPos worldPosition){
        return new LevelChunk[]{level.getChunkAt(worldPosition),level.getChunkAt(worldPosition.offset(16,0,0)),level.getChunkAt(worldPosition.offset(0,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,0)),level.getChunkAt(worldPosition.offset(0,0,-16)),level.getChunkAt(worldPosition.offset(16,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,-16)),level.getChunkAt(worldPosition.offset(16,0,-16)),level.getChunkAt(worldPosition.offset(-16,0,16))};
    }

    public static double getGipotenuza(double a,double b){
        return Math.sqrt(a*a + b*b);
    }


    public static boolean isReachable(Level world, BlockPos pos1, BlockPos pos2,int radius){
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

    public static boolean checkStructure(Level world,BlockPos initPos,Multiblock struct,boolean ignoreOtherBlocks){
        BlockPos pos = initPos;
        String[][] structure = struct.struct;
        for (int i = 0;i < structure.length;i++){
            for (int g = 0;g < structure[i].length;g++){
                String line = structure[i][g];
                    for (int k = 0;k < line.length();k++){
                        //here the checking begins
                        if (world.getBlockState(initPos.offset(k,i,g)) != struct.blockMap.get(line.charAt(k))){
                            return false;
                        }
                        //here ends
                    }
            }
        }
        return true;
    }

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
        Vec3 vector = new Vec3(vec2.x - vec1.x,vec2.y - vec1.y,vec2.z - vec1.z);
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

            SolarForgePacketHandler.INSTANCE.sendTo(new UpdateProgressionOnClient(a.getAchievementCode(),hasPlayerUnlocked(a,player)),
                    ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
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
        MinecraftForge.EVENT_BUS.post(new ProgressionUnlockEvent(playerEntity,ach));
    }


    public static void updateRunicEnergyOnClient(RunicEnergy.Type type,float amount,Player player){
        SolarForgePacketHandler.INSTANCE.sendTo(new UpdateEnergyOnClientPacket(type, amount),
                ((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void triggerProgressionShader(Player playerEntity){
        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerProgressionShaderPacket(),
                ((ServerPlayer)playerEntity).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }



    public static void constructMultiblock(Player player,Multiblock multiblock){
        Level w = player.level;
        BlockPos startingPos = player.getOnPos();
        for (int i = 0; i < multiblock.struct.length; i++){
            for (int g = 0; g < multiblock.struct[i].length; g++){
                String row = multiblock.struct[i][g];
                    for (int f = 0;f < row.length();f++){
                        int offsetX = f;
                        int offsetZ = g;
                        int offsetY = i;
//                        w.setBlock(startingPos.offset(offsetX,offsetY,offsetZ),multiblock.blockMap.get(row.charAt(f)).defaultBlockState(), Constants.BlockFlags.DEFAULT);
                        w.setBlockAndUpdate(startingPos.offset(offsetX,offsetY,offsetZ),multiblock.blockMap.get(row.charAt(f)));
                    }

            }
        }
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


    public static class ManaHandler{
        public static boolean spendMana(Player player,double amount){
            LazyOptional<SolarForgeMana> cap = getCap(player);
            if (cap.isPresent() ){
                if (player.isCreative()){
                    return true;
                }else{
                    Optional<SolarForgeMana> op = cap.resolve();
                    if (op.isPresent()){
                        SolarForgeMana capability = op.get();
                        double mana = capability.getMana();
                        if (mana >= amount){
                            capability.setMana(mana-amount);
                            return true;
                        }else {
                            return false;
                        }
                    }else{
                        return false;
                    }
                }
            }else{
                return false;
            }
        }

        private static LazyOptional<SolarForgeMana> getCap(Player player){
            return player.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER);
        }
    }


    public static void createSmallSolarStrikeParticleExplosion(Level world,Vec3 position,int intensity,float speedFactor,float spawnDistanceFactor){
        for (int x = -intensity; x < intensity+1;x++){
            for (int y = -intensity; y < intensity+1;y++){
                for (int z = -intensity; z < intensity+1;z++){
                    Vec3 offset = new Vec3(x,y,z).normalize().multiply(spawnDistanceFactor,spawnDistanceFactor,spawnDistanceFactor);
                    Vec3 finalpos = position.add(offset);
                    world.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),finalpos.x,finalpos.y,finalpos.z,offset.x*speedFactor,offset.y*speedFactor,offset.z*speedFactor);

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
                    world.addParticle(ParticlesList.SMALL_SOLAR_STRIKE_PARTICLE.get(),finalpos.x,finalpos.y,finalpos.z,offset.x*speedFactor,offset.y*speedFactor,offset.z*speedFactor);
                    world.addParticle(ParticlesList.SOLAR_EXPLOSION_PARTICLE.get(),finalpos.x,finalpos.y,finalpos.z,offset.x*(speedFactor+0.3),offset.y*(speedFactor+0.3),offset.z*(speedFactor+0.3));

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

    private static List<BlockPos> findNormalBlockPositionsOnPlane(Level world,int radius,BlockPos mainpos){
        List<BlockPos> toReturn = new ArrayList<>();
        for (int i = -radius; i <= radius;i++){
            for (int g = -radius; g <= radius;g++){
                if (FinderfeedMathHelper.isInCircle(i,g,radius)) {
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
        return !pl.level.getEntitiesOfClass(CrystalBossEntity.class,new AABB(-20,-20,-20,20,20,20).move(pl.position())).isEmpty();
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


    public static class HashMapConstructor<T,E>{
        private Map<T,E> MAP = new HashMap<>();

        public HashMapConstructor(){}

        public HashMapConstructor<T,E> addEntry(T key,E entry){
            MAP.put(key,entry);
            return this;
        }

        public Map<T,E> build(){
            return MAP;
        }

    }


}
