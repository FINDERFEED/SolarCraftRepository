package com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.algorithms.a_star.AStar;
import com.finderfeed.solarforge.local_library.algorithms.a_star.IPosition;
import com.finderfeed.solarforge.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalTile;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CrystalEnergyVinesTile extends BlockEntity {

    public static final int PUZZLE_SIZE_Y = 17;
    public static final int PUZZLE_SIZE_X = 17;
    public static final IPosition[] MOVEMENTS = {
            new IPosition(1,0,0),
            new IPosition(-1,0,0),
            new IPosition(0,-1,0),
            new IPosition(0,1,0)
    };
    public static final int MOVE_DOWN = 0;
    public static final int MOVE_UP = 1;
    public static final int MOVE_LEFT = 2;
    public static final int MOVE_RIGHT = 3;
    public static final int RESET = 4;

    public static final int FREE_WAY = 0;
    public static final int BLOCKED_WAY = 1;
    public static final int PLAYER_WAY = 2;
    public static final int FINAL_POS = 3;
    //x down y right
    private int[][] puzzlePattern;
    private int[][] initPuzzlePattern;
    private int initRemainingTries;
    private int initPos;
    private int endPos;
    private int remainingTries;
    private int[] currentPosition;

    public CrystalEnergyVinesTile( BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.CRYSTAL_ENERGY_VINES.get(), pos, state);
    }



    public boolean generatePattern() {
        if (puzzlePattern != null) return true;
        Random random = new Random();
        int initPos = random.nextInt(PUZZLE_SIZE_Y);
        int endPos = random.nextInt(PUZZLE_SIZE_Y);
        if (initPos % 2 != 0) initPos = initPos != PUZZLE_SIZE_Y ? initPos + 1 : initPos - 1;
        int tries = 0;
        boolean success = false;
        while (tries++ < 200){
            int[][] pat = generateMaze(initPos);
            List<IPosition> path = AStar.pathFrom2DArrayNoDiagonals(pat,new IPosition(PUZZLE_SIZE_X-1,initPos,0),new IPosition(0,endPos,0));
            if (path != null){
                remainingTries = path.size() - 1;
                this.endPos = endPos;
                this.initPos = initPos;
                this.initRemainingTries = remainingTries;
                pat[PUZZLE_SIZE_X-1][initPos] = PLAYER_WAY;
                pat[0][endPos] = FINAL_POS;
                puzzlePattern = pat;
                initPuzzlePattern = new int[PUZZLE_SIZE_X][PUZZLE_SIZE_Y];
                cloneArray(puzzlePattern,initPuzzlePattern);
//                initPuzzlePattern = pat.clone();
                currentPosition = new int[]{PUZZLE_SIZE_X -1,initPos};
                success = true;
                break;
            }
        }
        return success;
    }


    public boolean handleAction(int actionType){
        if (puzzlePattern == null || remainingTries == 0 || actionType > 4) return false;
        if (isWin()) return true;
        if (actionType == RESET){
            cloneArray(initPuzzlePattern,puzzlePattern);
//            this.puzzlePattern = initPuzzlePattern.clone();
            this.remainingTries = initRemainingTries;
            this.currentPosition = new int[]{PUZZLE_SIZE_X -1,initPos};
            return false;
        }
        IPosition targetPos = new IPosition(currentPosition[0],currentPosition[1],0).add(MOVEMENTS[actionType]);
        if (isValid(targetPos)){
            this.currentPosition = new int[]{targetPos.x(),targetPos.y()};
            this.puzzlePattern[currentPosition[0]][currentPosition[1]] = PLAYER_WAY;
            this.remainingTries--;
            if (isWin()){
                if (level.getBlockEntity(worldPosition.above(2)) instanceof ClearingRitualCrystalTile tile){
                    tile.overload();
                }
            }
            return isWin();
        }else{
            return false;
        }
    }

    public boolean isWin(){
        return currentPosition[0] == 0 && currentPosition[1] == endPos;
    }

    private boolean isValid(IPosition target){

        return target.x() >= 0 && target.x() < PUZZLE_SIZE_X &&
                target.y() >= 0 && target.y() < PUZZLE_SIZE_Y &&
                puzzlePattern[target.x()][target.y()] != BLOCKED_WAY && puzzlePattern[target.x()][target.y()] != PLAYER_WAY;
    }



    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return Helpers.createTilePacket(this,tag);
    }


    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net,pkt);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (this.puzzlePattern != null){
            CompoundNBTHelper.save2DIntArray(puzzlePattern,"puzzlePattern",tag);
            CompoundNBTHelper.save2DIntArray(initPuzzlePattern,"puzzlePatternClone",tag);
            tag.putInt("endPos",endPos);
            tag.putInt("tries",remainingTries);
            tag.putInt("initTries",initRemainingTries);
            tag.putInt("initPos",initPos);
            tag.putInt("posx",currentPosition[0]);
            tag.putInt("posy",currentPosition[1]);
        }

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("puzzlePattern")){
            this.puzzlePattern = CompoundNBTHelper.load2DArray("puzzlePattern",tag);
            this.initPuzzlePattern = CompoundNBTHelper.load2DArray("puzzlePatternClone",tag);
            this.endPos = tag.getInt("endPos");
            this.remainingTries = tag.getInt("tries");
            this.initRemainingTries = tag.getInt("initTries");
            this.initPos = tag.getInt("initPos");
            this.currentPosition = new int[]{tag.getInt("posx"),tag.getInt("posy")};
        }
        super.load(tag);
    }

    public int getRemainingTries() {
        return remainingTries;
    }

    public int[][] getPuzzlePattern() {
        return puzzlePattern;
    }


    private int[][] generateMaze(int beginningPos){
        Random random = new Random();

        int[][] maze = new int[PUZZLE_SIZE_X][PUZZLE_SIZE_Y];
        for (int[] ints : maze) {
            Arrays.fill(ints, 1);
        }
        Stack<Pair<Integer,Integer>> visited = new Stack<>();
        visited.add(new Pair<>(PUZZLE_SIZE_X-1,beginningPos));
        maze[PUZZLE_SIZE_X-1][beginningPos] = FREE_WAY;

        while(!visited.isEmpty()){
            Pair<Integer,Integer> t = visited.peek();
            List<IPosition> neighbours = getNeighbours(maze,t.getFirst(),t.getSecond());
            if (neighbours.isEmpty()){ visited.pop(); continue;}
            IPosition pos = neighbours.get(random.nextInt(neighbours.size()));
            int mmax = Math.max(t.getFirst(),pos.x());
            int mmin = Math.min(t.getFirst(),pos.x());
            int ymax = Math.max(t.getSecond(),pos.y());
            int ymin = Math.min(t.getSecond(),pos.y());
            for (int x = mmin;x <= mmax;x++){
                for (int y = ymin;y <= ymax;y++){
                    maze[x][y] = 0;
                }
            }
            visited.push(new Pair<>(pos.x(),pos.y()));
        }
        return maze;
    }

    private final int[][] movements = {
            {2,0},
            {-2,0},
            {0,2},
            {0,-2}
    };

    public List<IPosition> getNeighbours(int[][] maze,int x,int y){
        List<IPosition> positions = new ArrayList<>();
        for (int[] m : movements){
            int xp = x + m[0];
            int yp = y + m[1];
            if (Helpers.isIn2DArrayBounds(maze,xp,yp) && maze[xp][yp] != 0){
                positions.add(new IPosition(xp,yp,0));
            }
        }
        return positions;
    }


    private void cloneArray(int[][] initArray,int[][] cloneTo){
        for (int i = 0;i < initArray.length;i++){
            for (int g = 0;g < initArray[i].length;g++){
                cloneTo[i][g] = initArray[i][g];
            }
        }
    }
}
