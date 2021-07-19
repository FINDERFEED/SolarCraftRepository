package com.finderfeed.solarforge.magic_items.blocks.blockentities;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic_items.blocks.BlueGemDoorBlock;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class TrapControllerTile extends TileEntity implements ITickableTileEntity {

    public final AxisAlignedBB BOX = new AxisAlignedBB(worldPosition.offset(7,-5,3),
            worldPosition.offset(-7,-1,-3));
    public List<BlockPos> TRAP_BLOCK_POSITIONS = new ArrayList<>();
    public List<BlockPos> ENTRANCE_BLOCK_POSITIONS = new ArrayList<>();
    public List<BlockPos> RESULT_BLOCK_POSITIONS = new ArrayList<>();
    public boolean IS_ATTACKING_PLAYER = false;
    public boolean ALREADY_ACTIVATED = false;
    public boolean DESTROYED_BLOCKS = false;
    public boolean HAS_ALREADY_RESET = false;
    public int TICKS = 0;

    public TrapControllerTile() {
        super(TileEntitiesRegistry.TRAP_STRUCT_CONTROLLER.get());

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


    @Override
    public void tick() {
        if (!level.isClientSide && !ALREADY_ACTIVATED){
            if (!DESTROYED_BLOCKS){
                fillEntrancePositions();
                fillResultPositions();
                fillTrapPositions();
                DESTROYED_BLOCKS = true;
            }
            AxisAlignedBB box = new AxisAlignedBB(worldPosition.offset(8,-5,4),
                    worldPosition.offset(-7,-1,-3));
            List<PlayerEntity> list = level.getEntitiesOfClass(PlayerEntity.class,box);
            list.forEach((player)->{
                Helpers.fireProgressionEvent(player,Achievement.DIMENSIONAL_SHARD_DUNGEON);
            });
            if (!list.isEmpty()){
                if (!ALREADY_ACTIVATED) {
                    IS_ATTACKING_PLAYER = true;
                }

            //3590   64   105
            }else{
                reset();
            }

            if (IS_ATTACKING_PLAYER){
                TICKS++;

                for (BlockPos pos : ENTRANCE_BLOCK_POSITIONS){

                    if ((level.getBlockState(pos).getBlock() != BlocksRegistry.INVINCIBLE_STONE.get()) || (level.getBlockState(pos).getBlock() != BlocksRegistry.BLUE_GEM_DOOR_BLOCK.get())){
                        if ((!Helpers.equalsBlockPos(pos,worldPosition.offset(8,-4,-1)))
                        && (!Helpers.equalsBlockPos(pos,worldPosition.offset(8,-4,1)))) {
                            level.setBlock(pos, BlocksRegistry.INVINCIBLE_STONE.get().defaultBlockState(), Constants.BlockFlags.DEFAULT);
                        }else {
                            level.setBlock(pos, BlocksRegistry.BLUE_GEM_DOOR_BLOCK.get().defaultBlockState()
                                    .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)
                                    .setValue(BlueGemDoorBlock.UNLOCKED, false), Constants.BlockFlags.DEFAULT);
                        }

                    }
                }
                if (TICKS % 80 == 0) {

                List<BlockPos> activateThis = new ArrayList<>();

                    for (int i = 0; i < TRAP_BLOCK_POSITIONS.size() / 1.5; i++) {
                        BlockPos randompos = TRAP_BLOCK_POSITIONS.get(level.random.nextInt(TRAP_BLOCK_POSITIONS.size()));
                        if (!activateThis.contains(randompos)) {
                            activateThis.add(randompos);
                        } else {
                            while (true) {
                                BlockPos anotherrandompos = TRAP_BLOCK_POSITIONS.get(level.random.nextInt(TRAP_BLOCK_POSITIONS.size()));
                                if (!activateThis.contains(anotherrandompos)) {
                                    activateThis.add(anotherrandompos);
                                    break;
                                }
                            }
                        }
                    }


                    activateThis.forEach((pos) -> {
                        TileEntity test = level.getBlockEntity(pos);
                        if (test instanceof RayTrapTileEntity) {
                            RayTrapTileEntity tile = (RayTrapTileEntity) test;
                            tile.triggerTrap();
                        }
                    });
                }

                if (TICKS >= 1800){
                    IS_ATTACKING_PLAYER = false;
                    ALREADY_ACTIVATED = true;
                    ENTRANCE_BLOCK_POSITIONS.forEach((pos) -> level.destroyBlock(pos, false));
                    RESULT_BLOCK_POSITIONS.forEach((pos) -> level.destroyBlock(pos, false));
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
    public CompoundNBT save(CompoundNBT p_189515_1_) {
        p_189515_1_.putBoolean("activated_",ALREADY_ACTIVATED);
        p_189515_1_.putBoolean("attacking_",IS_ATTACKING_PLAYER);

        p_189515_1_.putBoolean("has_already_reset",HAS_ALREADY_RESET);
        p_189515_1_.putInt("attack_ticks",TICKS);
        return super.save(p_189515_1_);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT c) {
        ALREADY_ACTIVATED = c.getBoolean("activated_");
        IS_ATTACKING_PLAYER = c.getBoolean("attacking_");

        HAS_ALREADY_RESET = c.getBoolean("has_already_reset");
        TICKS = c.getInt("attack_ticks");
        super.load(p_230337_1_, c);
    }
}
