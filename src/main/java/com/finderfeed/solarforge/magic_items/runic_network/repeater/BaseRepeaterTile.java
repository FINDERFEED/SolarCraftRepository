package com.finderfeed.solarforge.magic_items.runic_network.repeater;

import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

//NEVER GONNA GIVE YOU UP
public class BaseRepeaterTile extends BlockEntity {

    //rune energy pylon position
    private BlockPos FINAL_POSITION;
    private RunicEnergy.Type ENERGY_TYPE;
    private BlockPos CONNECTED_TO;

    public BaseRepeaterTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.REPEATER.get(), p_155229_, p_155230_);
    }



    public static void tick(Level world,BlockPos pos,BlockState state,BaseRepeaterTile tile){

    }


    public double getMaxRange(){
        return 25;
    }


    public BlockPos getRepeaterConnection(){
        return CONNECTED_TO;
    }

    public void setRepeaterConnection(BlockPos pos){
        this.CONNECTED_TO = pos;
    }

    public void setFinalPos(BlockPos FINAL_POSITION) {
        this.FINAL_POSITION = FINAL_POSITION;
    }

    public BlockPos getFinalPos() {
        return FINAL_POSITION;
    }

    public void setEnergyType(RunicEnergy.Type type){
        this.ENERGY_TYPE = type;
    }

    public RunicEnergy.Type getEnergyType(){
        return RunicEnergy.Type.ARDO;
    }
    public boolean hasConnection(){
        return FINAL_POSITION != null;
    }
}
