package com.finderfeed.solarcraft.content.blocks.blockentities;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;

import net.minecraft.nbt.CompoundTag;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;


import java.util.ArrayList;
import java.util.List;

public class TrapControllerTile extends BlockEntity  {

    public final AABB BOX = new AABB(worldPosition.offset(7,-5,3),
            worldPosition.offset(-7,-1,-3));
    public List<BlockPos> trapPositions = new ArrayList<>();
    public List<BlockPos> entrancePositions = new ArrayList<>();
    public List<BlockPos> resultBlockPositions = new ArrayList<>();
    public boolean isAttackingPlayer = false;
    public boolean isActivated = false;
    public boolean destroyedBlocks = false;
    public boolean hasReset = false;
    public int ticks = 0;

    public TrapControllerTile( BlockPos p_155229_, BlockState p_155230_) {
        super(SCTileEntities.TRAP_STRUCT_CONTROLLER.get(), p_155229_, p_155230_);
    }


    public void fillTrapPositions(){
        for (int x = -7;x < 8;x++){
            for (int z = -3;z < 4;z++){
                trapPositions.add(worldPosition.offset(x,-1,z));
            }
        }
    }

    public void fillEntrancePositions(){
        entrancePositions.add(worldPosition.offset(8,-3,0));
        entrancePositions.add(worldPosition.offset(8,-4,0));
        entrancePositions.add(worldPosition.offset(8,-5,0));
        entrancePositions.add(worldPosition.offset(8,-3,-1));
        entrancePositions.add(worldPosition.offset(8,-4,-1));
        entrancePositions.add(worldPosition.offset(8,-5,-1));
        entrancePositions.add(worldPosition.offset(8,-3,1));
        entrancePositions.add(worldPosition.offset(8,-4,1));
        entrancePositions.add(worldPosition.offset(8,-5,1));
    }
    public void fillResultPositions(){
        resultBlockPositions.add(worldPosition.offset(-8,-4,0));
        resultBlockPositions.add(worldPosition.offset(-8,-5,0));
    }



    public static void tick(Level world, BlockPos post, BlockState blockState, TrapControllerTile tile) {
        if (!tile.level.isClientSide && !tile.isActivated){
            if (!tile.destroyedBlocks){
                tile.fillEntrancePositions();
                tile.fillResultPositions();
                tile.fillTrapPositions();
                tile.destroyedBlocks = true;
            }
            AABB box = new AABB(tile.worldPosition.offset(8,-5,4),
                    tile.worldPosition.offset(-7,-1,-3));
            List<Player> list = tile.level.getEntitiesOfClass(Player.class,box);
            list.forEach((player)->{
                Helpers.fireProgressionEvent(player, Progression.DIMENSIONAL_SHARD_DUNGEON);
            });
            if (!list.isEmpty()){
                if (!tile.isActivated) {
                    tile.isAttackingPlayer = true;
                }

            //3590   64   105
            }else{
                tile.reset();
            }

            if (tile.isAttackingPlayer){
                tile.ticks++;

//                for (BlockPos pos : tile.entrancePositions){
//
//                    if ((tile.level.getBlockState(pos).getBlock() != SCBlocks.INVINCIBLE_STONE.get()) || (tile.level.getBlockState(pos).getBlock() != SCBlocks.BLUE_GEM_DOOR_BLOCK.get())){
//                        if ((!Helpers.equalsBlockPos(pos,tile.worldPosition.offset(8,-4,-1)))
//                        && (!Helpers.equalsBlockPos(pos,tile.worldPosition.offset(8,-4,1)))) {
//                            tile.level.setBlock(pos, SCBlocks.INVINCIBLE_STONE.get().defaultBlockState(), 3);
//                        }else {
//                            tile.level.setBlock(pos, SCBlocks.BLUE_GEM_DOOR_BLOCK.get().defaultBlockState()
//                                    .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
//                                    .setValue(BlueGemDoorBlock.UNLOCKED, false), 3);
//                        }
//
//                    }
//                }
                if (tile.ticks % 80 == 0) {

                List<BlockPos> activateThis = new ArrayList<>();

                    for (int i = 0; i < tile.trapPositions.size() / 1.5; i++) {
                        BlockPos randompos = tile.trapPositions.get(tile.level.random.nextInt(tile.trapPositions.size()));
                        if (!activateThis.contains(randompos)) {
                            activateThis.add(randompos);
                        } else {
                            while (true) {
                                BlockPos anotherrandompos = tile.trapPositions.get(tile.level.random.nextInt(tile.trapPositions.size()));
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

                if (tile.ticks >= 1800){
                    tile.isAttackingPlayer = false;
                    tile.isActivated = true;
                    tile.entrancePositions.forEach((pos) -> tile.level.destroyBlock(pos, false));
                    tile.resultBlockPositions.forEach((pos) -> tile.level.destroyBlock(pos, false));
                }
            }

        }
    }


    public void reset(){
        if (!level.isClientSide ){
            isAttackingPlayer = false;
            ticks = 0;
        }
    }


    @Override
    public void saveAdditional(CompoundTag p_189515_1_) {
        p_189515_1_.putBoolean("activated_", isActivated);
        p_189515_1_.putBoolean("attacking_", isAttackingPlayer);

        p_189515_1_.putBoolean("has_already_reset", hasReset);
        p_189515_1_.putInt("attack_ticks", ticks);
        super.saveAdditional(p_189515_1_);
    }

    @Override
    public void load( CompoundTag c) {
        isActivated = c.getBoolean("activated_");
        isAttackingPlayer = c.getBoolean("attacking_");

        hasReset = c.getBoolean("has_already_reset");
        ticks = c.getInt("attack_ticks");
        super.load( c);
    }
}
