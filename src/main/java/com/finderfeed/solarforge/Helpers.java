package com.finderfeed.solarforge;

import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.packet_handler.packets.ReloadChunks;
import com.finderfeed.solarforge.packet_handler.packets.TriggerProgressionShaderPacket;
import com.finderfeed.solarforge.packet_handler.packets.UpdateEnergyOnClientPacket;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.achievement_tree.AchievementTree;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.UpdateProgressionOnClient;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import org.lwjgl.opengl.GL11;

import java.util.*;



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

    public static String getAchievementDescription(Achievement ach){
        return switch (ach) {
            case CRAFT_SOLAR_FORGE -> "The magical power of this machine allows me to get powerful abilities. Can it do more than just that?";
            case CRAFT_SOLAR_INFUSER -> "So now what? There was no instructions on how to use it...";
            case FIND_SOLAR_STONE -> "This magical stone shines brighter than sun.There should be a way to use it";
            case USE_SOLAR_INFUSER -> "Oh useful shiny things! I am feeling myself a mage more right  now.";
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
            case ENTER_NETHER -> "Despite its evil nature there are still some friendly creatures here, but i still need to be careful.";
            case SOLAR_RUNE -> "Maybe this runes will help me to decrypt the fragments?";
            case RUNE_ENERGY_CLAIM -> "Turns out i can collect this energy inside...myself?";
            case RUNE_ENERGY_DEPOSIT -> "It seems that this pylon contains some sort of energy.";
        };
    }

    public static boolean hasPlayerUnlocked(Achievement ach, Player entity){

        return ach == null ? true : entity.getPersistentData().getBoolean("solar_forge_progression_"+ach.getAchievementCode());
    }

    public static boolean canPlayerUnlock(Achievement ach, Player entity){
        AchievementTree tree = AchievementTree.loadTree();
        for (Achievement a : tree.getAchievementRequirements(ach)){
            if (!entity.getPersistentData().getBoolean("solar_forge_progression_"+a.getAchievementCode())){
                return false;
            }
        }
        return true;
    }

    public static void setAchievementStatus(Achievement ach, Player pe, boolean a){
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


        if (world.getBlockState(result.getBlockPos()).getBlock() == world.getBlockState(pos2).getBlock() && result.getBlockPos().equals(pos2) && vector.length() <= radius){
            return true;
        }
        return false;
    }


    //structure towards north, initPos is the pos at the lowest by y lowest by z left corner

    public static boolean checkStructure(Level world,BlockPos initPos,Multiblock struct){
        BlockPos pos = initPos;
        String[][] structure = struct.struct;
        for (int i = 0;i < structure.length;i++){
            for (int g = 0;g < structure[i].length;g++){
                String line = structure[i][g];
                    for (int k = 0;k < line.length();k++){
                        //here the checking begins
                        if (world.getBlockState(initPos.offset(k,i,g)).getBlock() != struct.blockMap.get(line.charAt(k))){
                            return false;
                        }
                        //here ends
                    }
            }
        }
        return true;
    }

    public static Item[] ALL_OBJECTS_FOR_CRAFTING = {
            ItemsRegister.SOLAR_WAND.get(),
            ItemsRegister.SOLAR_STONE_BRICKS.get(),
            ItemsRegister.SOLAR_STONE_CHISELED.get(),
            ItemsRegister.SOLAR_STONE_COLLUMN.get(),
            ItemsRegister.SOLAR_STONE_COLLUMN_HORIZONTAL.get()
    };
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
        for (Achievement a : Achievement.ALL_ACHIEVEMENTS) {

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

    public static void triggerToast(Achievement ach, Player player){
        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(ach.getId()), ((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void fireProgressionEvent(Player playerEntity, Achievement ach){
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
                        w.setBlockAndUpdate(startingPos.offset(offsetX,offsetY,offsetZ),multiblock.blockMap.get(row.charAt(f)).defaultBlockState());
                    }

            }
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
