package com.finderfeed.solarforge;

import com.finderfeed.solarforge.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.events.my_events.ProgressionUnlockEvent;
import com.finderfeed.solarforge.misc_things.Multiblock;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.packets.ReloadChunks;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.solar_lexicon.achievement_tree.AchievementTree;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import com.finderfeed.solarforge.solar_lexicon.packets.UpdateProgressionOnClient;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkDirection;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class Helpers {

    public static final String FRAGMENT = "solar_forge_fragment_";
    public static final BlockClusterFeatureConfig SOLAR_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).add(BlocksRegistry.ALGADIUM_BLOCK.get().defaultBlockState(), 2), SimpleBlockPlacer.INSTANCE)).tries(64).build();
    public static double GRAVITY_METRES_PER_SEC = 20;
    public static double GRAVITY_VELOCITY = 0.05;
    public static String PROGRESSION = "solar_forge_progression_";
    public static BlockPos NULL_POS = new BlockPos(0,-100,0);
    public static void drawBoundedText(MatrixStack matrices,int posx,int posy,int bound,String s){
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
            AbstractGui.drawString(matrices, Minecraft.getInstance().font, strings,posx,posy+y,0xffffff);
            y+=10;
        }

    }

    public static String getAchievementDescription(Achievement ach){
        switch (ach){
            case CRAFT_SOLAR_FORGE: return "The magical power of this machine allows me to get powerful abilities. Can it do more than just that?";
            case CRAFT_SOLAR_INFUSER: return "So now what? There was no instructions on how to use it...";
            case FIND_SOLAR_STONE: return "This magical stone shines brighter than sun.There should be a way to use it";
            case USE_SOLAR_INFUSER: return "Oh useful shiny things! I am feeling myself a mage more right  now.";
            case ACQUIRE_SOLAR_DUST: return "I have finally acquired solar dust! But where to use it?";
            case ACQUIRE_COLD_STAR: return "The power of this item is very big. According to my experiments it can focus on light.";
            case FIND_INFUSER_DUNGEON: return "Is this some sort of an ancient altar?";
            case FIND_KEY_LOCK_DUNGEON: return "A very strange building... There should be more to it...";
            case FIND_KEY_SOURCE: return "A key? But for what?";
            case CRAFT_SOLAR_LENS: return "With the power of this magic piece of a star i can create better materials now!";
            case ACQUIRE_COLD_STAR_ACTIVATED: return "This altar just made it increase the power of the focused light! I need to find a usage for it.";
            case CRAFT_SOLAR_ENERGY_GENERATOR: return "This generator is much stronger than the one that i had before!";
            case TRANSMUTE_GEM: return "My guesses were true, lava in this forest is full of magic energy.";
            case FIND_INCINERATED_FOREST: return "It doesnt seem that this was caused by a natural disaster...";
            case TRADE_FOR_BLUE_GEM: return "What a rude man! At least he loves to share information... For money...";
            case DIMENSIONAL_SHARD_DUNGEON: return "The ancient civilizations definitely dont want me to reveal their secrets.";
        }
        return "ERROR:Tell mod author to fix it";
    }

    public static boolean hasPlayerUnlocked(Achievement ach, PlayerEntity entity){

        return ach == null ? true : entity.getPersistentData().getBoolean("solar_forge_progression_"+ach.getAchievementCode());
    }

    public static boolean canPlayerUnlock(Achievement ach, PlayerEntity entity){
        AchievementTree tree = AchievementTree.loadTree();
        for (Achievement a : tree.getAchievementRequirements(ach)){
            if (!entity.getPersistentData().getBoolean("solar_forge_progression_"+a.getAchievementCode())){
                return false;
            }
        }
        return true;
    }

    public static void setAchievementStatus(Achievement ach, PlayerEntity pe, boolean a){
        pe.getPersistentData().putBoolean("solar_forge_progression_"+ach.getAchievementCode(),a);
    }



    public static void drawLine(MatrixStack stack,int x1,int y1,int x2,int y2,float red,float green,float blue){
        GL11.glLineWidth(2F);

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4f(red,green,blue,255);
        GL11.glVertex2i(x1, y1);
        GL11.glVertex2i(x2, y2);

        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void spendMana(PlayerEntity playerEntity,double count){
        if (!playerEntity.isDeadOrDying() && playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).isPresent() && !playerEntity.isCreative()){
            if (count < playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).getMana()) {
                playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).setMana(playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).getMana() - count);
            }
        }
    }
    public static boolean canCast(PlayerEntity playerEntity,double count){
        if (!playerEntity.isDeadOrDying() && playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).isPresent() && !playerEntity.isCreative()) {
            if (count < playerEntity.getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(Error::new).getMana()) {
                return true;
            }
        }else if (playerEntity.isCreative()){
            return true;

        }
        return false;
    }


    public static Chunk[] getSurroundingChunks(World level,BlockPos worldPosition){
        return new Chunk[]{level.getChunkAt(worldPosition),level.getChunkAt(worldPosition.offset(16,0,0)),level.getChunkAt(worldPosition.offset(0,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,0)),level.getChunkAt(worldPosition.offset(0,0,-16)),level.getChunkAt(worldPosition.offset(16,0,16)),
                level.getChunkAt(worldPosition.offset(-16,0,-16)),level.getChunkAt(worldPosition.offset(16,0,-16)),level.getChunkAt(worldPosition.offset(-16,0,16))};
    }

    public static double getGipotenuza(double a,double b){
        return Math.sqrt(a*a + b*b);
    }


    public static boolean isReachable(World world, BlockPos pos1, BlockPos pos2,int radius){
        Vector3d vec1 = new Vector3d(pos1.getX()+0.5f,pos1.getY()+0.5f,pos1.getZ()+0.5f);
        Vector3d vec2 = new Vector3d(pos2.getX()+0.5f,pos2.getY()+0.5f,pos2.getZ()+0.5f);
        Vector3d vector = new Vector3d(vec2.x - vec1.x,vec2.y - vec1.y,vec2.z - vec1.z);
        RayTraceContext ctx = new RayTraceContext(vec1.add(vector.normalize()),vec2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE,null);
        BlockRayTraceResult result = world.clip(ctx);


        if (world.getBlockState(result.getBlockPos()).getBlock() == world.getBlockState(pos2).getBlock() && result.getBlockPos().equals(pos2) && vector.length() <= radius){
            return true;
        }
        return false;
    }


    //structure towards north, initPos is the pos at the lowest by y lowest by z left corner

    public static boolean checkStructure(World world,BlockPos initPos,Multiblock struct){
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
    public static boolean isEntityReachable(World world, BlockPos pos1, BlockPos pos2){
        Vector3d vec1 = new Vector3d(pos1.getX()+0.5f,pos1.getY()+0.5f,pos1.getZ()+0.5f);
        Vector3d vec2 = new Vector3d(pos2.getX()+0.5f,pos2.getY()+1.25f,pos2.getZ()+0.5f);
        Vector3d vector = new Vector3d(vec2.x - vec1.x,vec2.y - vec1.y,vec2.z - vec1.z);
        RayTraceContext ctx = new RayTraceContext(vec2,vec1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE,null);
        BlockRayTraceResult result = world.clip(ctx);

        if (result.getBlockPos().equals(pos1)){
            return true;
        }

        return false;
    }




    public static Vector3d calculateVelocity(Vector3d pos1,Vector3d pos2){
        return new Vector3d(pos2.x - pos1.x,pos2.y - pos1.y,pos2.z - pos1.z).normalize();
    }

    public static Vector3d getBlockCenter(BlockPos pos){
        return new Vector3d(pos.getX() +0.5,pos.getY()+0.5,pos.getZ()+0.5);
    }


    public static void updateProgression(ServerPlayerEntity player){
        for (Achievement a : Achievement.ALL_ACHIEVEMENTS) {

            SolarForgePacketHandler.INSTANCE.sendTo(new UpdateProgressionOnClient(a.getAchievementCode(),hasPlayerUnlocked(a,player)),
                    ((ServerPlayerEntity) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public static void forceChunksReload(ServerPlayerEntity playerEntity){
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

    public static void triggerToast(Achievement ach, PlayerEntity player){
        SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(ach.getId()), ((ServerPlayerEntity)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void fireProgressionEvent(PlayerEntity playerEntity, Achievement ach){
        MinecraftForge.EVENT_BUS.post(new ProgressionUnlockEvent(playerEntity,ach));
    }
}
