package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.BlueGemDoorBlock;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;


import java.util.ArrayList;
import java.util.List;

public class TrapControllerTile extends BlockEntity  {

    public final AABB BOX = new AABB(worldPosition.offset(7,-5,3),
            worldPosition.offset(-7,-1,-3));
    public List<BlockPos> TRAP_BLOCK_POSITIONS = new ArrayList<>();
    public List<BlockPos> ENTRANCE_BLOCK_POSITIONS = new ArrayList<>();
    public List<BlockPos> RESULT_BLOCK_POSITIONS = new ArrayList<>();
    public boolean IS_ATTACKING_PLAYER = false;
    public boolean ALREADY_ACTIVATED = false;
    public boolean DESTROYED_BLOCKS = false;
    public boolean HAS_ALREADY_RESET = false;
    public int TICKS = 0;

    public TrapControllerTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SolarcraftTileEntityTypes.TRAP_STRUCT_CONTROLLER.get(), p_155229_, p_155230_);
    }


    public void fillTrapPositions(){
        for (int x = -7;x < 8;x++){
            for (int z = -3;z < 4;z++){
                TRAP_BLOCK_POSITIONS.add(worldPosition.offset(x,-1,z));
            }
        }
    }

    public void fillEntrancePositions(){
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-3,0));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-4,0));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-5,0));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-3,-1));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-4,-1));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-5,-1));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-3,1));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-4,1));
        ENTRANCE_BLOCK_POSITIONS.add(worldPosition.offset(8,-5,1));
    }
    public void fillResultPositions(){
        RESULT_BLOCK_POSITIONS.add(worldPosition.offset(-8,-4,0));
        RESULT_BLOCK_POSITIONS.add(worldPosition.offset(-8,-5,0));
    }



    public static void tick(Level world, BlockPos post, BlockState blockState, TrapControllerTile tile) {
        if (!tile.level.isClientSide && !tile.ALREADY_ACTIVATED){
            if (!tile.DESTROYED_BLOCKS){
                tile.fillEntrancePositions();
                tile.fillResultPositions();
                tile.fillTrapPositions();
                tile.DESTROYED_BLOCKS = true;
            }
            AABB box = new AABB(tile.worldPosition.offset(8,-5,4),
                    tile.worldPosition.offset(-7,-1,-3));
            List<Player> list = tile.level.getEntitiesOfClass(Player.class,box);
            list.forEach((player)->{
                Helpers.fireProgressionEvent(player, Progression.DIMENSIONAL_SHARD_DUNGEON);
            });
            if (!list.isEmpty()){
                if (!tile.ALREADY_ACTIVATED) {
                    tile.IS_ATTACKING_PLAYER = true;
                }

            //3590   64   105
            }else{
                tile.reset();
            }

            if (tile.IS_ATTACKING_PLAYER){
                tile.TICKS++;

                for (BlockPos pos : tile.ENTRANCE_BLOCK_POSITIONS){

                    if ((tile.level.getBlockState(pos).getBlock() != SCBlocks.INVINCIBLE_STONE.get()) || (tile.level.getBlockState(pos).getBlock() != SCBlocks.BLUE_GEM_DOOR_BLOCK.get())){
                        if ((!Helpers.equalsBlockPos(pos,tile.worldPosition.offset(8,-4,-1)))
                        && (!Helpers.equalsBlockPos(pos,tile.worldPosition.offset(8,-4,1)))) {
                            tile.level.setBlock(pos, SCBlocks.INVINCIBLE_STONE.get().defaultBlockState(), 3);
                        }else {
                            tile.level.setBlock(pos, SCBlocks.BLUE_GEM_DOOR_BLOCK.get().defaultBlockState()
                                    .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
                                    .setValue(BlueGemDoorBlock.UNLOCKED, false), 3);
                        }

                    }
                }
                if (tile.TICKS % 80 == 0) {

                List<BlockPos> activateThis = new ArrayList<>();

                    for (int i = 0; i < tile.TRAP_BLOCK_POSITIONS.size() / 1.5; i++) {
                        BlockPos randompos = tile.TRAP_BLOCK_POSITIONS.get(tile.level.random.nextInt(tile.TRAP_BLOCK_POSITIONS.size()));
                        if (!activateThis.contains(randompos)) {
                            activateThis.add(randompos);
                        } else {
                            while (true) {
                                BlockPos anotherrandompos = tile.TRAP_BLOCK_POSITIONS.get(tile.level.random.nextInt(tile.TRAP_BLOCK_POSITIONS.size()));
                                if (!activateThis.contains(anotherrandompos)) {
                                    activateThis.add(anotherrandompos);
                                    break;
                                }
                            }
                        }
                    }


                    activateThis.forEach((pos) -> {
                        BlockEntity test = tile.level.getBlockEntity(pos);
                        if (test instanceof RayTrapTileEntity) {
                            RayTrapTileEntity tilex = (RayTrapTileEntity) test;
                            tilex.triggerTrap();
                        }
                    });
                }

                if (tile.TICKS >= 1800){
                    tile.IS_ATTACKING_PLAYER = false;
                    tile.ALREADY_ACTIVATED = true;
                    tile.ENTRANCE_BLOCK_POSITIONS.forEach((pos) -> tile.level.destroyBlock(pos, false));
                    tile.RESULT_BLOCK_POSITIONS.forEach((pos) -> tile.level.destroyBlock(pos, false));
                }
            }

        }
    }


    public void reset(){
        if (!level.isClientSide ){
            IS_ATTACKING_PLAYER = false;
            TICKS = 0;
        }
    }


    @Override
    public void saveAdditional(CompoundTag p_189515_1_) {
        p_189515_1_.putBoolean("activated_",ALREADY_ACTIVATED);
        p_189515_1_.putBoolean("attacking_",IS_ATTACKING_PLAYER);

        p_189515_1_.putBoolean("has_already_reset",HAS_ALREADY_RESET);
        p_189515_1_.putInt("attack_ticks",TICKS);
        super.saveAdditional(p_189515_1_);
    }

    @Override
    public void load( CompoundTag c) {
        ALREADY_ACTIVATED = c.getBoolean("activated_");
        IS_ATTACKING_PLAYER = c.getBoolean("attacking_");

        HAS_ALREADY_RESET = c.getBoolean("has_already_reset");
        TICKS = c.getInt("attack_ticks");
        super.load( c);
    }
}
