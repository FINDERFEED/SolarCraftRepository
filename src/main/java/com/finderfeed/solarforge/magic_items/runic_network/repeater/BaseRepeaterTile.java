package com.finderfeed.solarforge.magic_items.runic_network.repeater;

import com.finderfeed.solarforge.for_future_library.FinderfeedMathHelper;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

//NEVER GONNA GIVE YOU UP
public class BaseRepeaterTile extends BlockEntity {

    public static double NULL = -1000000;

    //rune energy pylon position
    private BlockPos FINAL_POSITION;
    private RunicEnergy.Type ENERGY_TYPE;
    private BlockPos CONNECTED_TO;

    public BaseRepeaterTile( BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntitiesRegistry.REPEATER.get(), p_155229_, p_155230_);
    }


    public double extractEnergy(double maxAmount,RunicEnergy.Type type){
        if (hasConnection()){
            if (level.getBlockEntity(getFinalPos()) instanceof IRunicEnergyContainer cont){
                double flag = cont.extractEnergy(type,maxAmount);
                return flag;
            }else{
                return NULL;
            }
        }else {
            if (CONNECTED_TO != null) {
                if (level.getBlockEntity(CONNECTED_TO) instanceof BaseRepeaterTile tile) {
                    return tile.extractEnergy(maxAmount,type);
                } else {
                    return NULL;
                }
            } else {
                return NULL;
            }
        }
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


    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return super.getUpdatePacket();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return super.getUpdateTag();
    }

}
