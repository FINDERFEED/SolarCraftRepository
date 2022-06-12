package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.events.other_events.event_handler.EventHandler;
import com.finderfeed.solarforge.magic.blocks.blockentities.SolarcraftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.LevelData;
import org.jetbrains.annotations.Nullable;

public class ClearingRitualMainTile extends BlockEntity {


    public static final int MAX_RITUAL_TIME = 1000;
    private int ritualTime = -1;


    public ClearingRitualMainTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public static void tick(ClearingRitualMainTile tile, BlockPos pos, BlockState state, Level world){
        if (!world.isClientSide && tile.isRitualStarted()){
            tile.ritualTime++;

        }
    }

    private void cleanWorld(){
        if (this.level != null && !this.level.isClientSide && this.level.dimension() == EventHandler.RADIANT_LAND_KEY){

            RadiantLandCleanedData data = ((ServerLevel)level).getServer().overworld()
                    .getDataStorage()
                    .computeIfAbsent(RadiantLandCleanedData::load,()->new RadiantLandCleanedData(false),"is_radiant_land_cleaned");
            if (!data.isCleaned()){
                /*
                TODO: teleport all players out of radiant land, send them packets notifying that radiant land has been cleaned
                 */
                data.setCleaned(true);
                data.setDirty();
            }
        }
    }

    public boolean isRitualStarted(){
        return ritualTime != -1;
    }

    public void toggleRitual(boolean bool){
        /*
        TODO:Make structure checking here
         */
        this.ritualTime = bool ? 0 : -1;
        Helpers.updateTile(this);
    }



    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return Helpers.createTilePacket(this,this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("ritualTime",ritualTime);
    }


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.ritualTime = tag.getInt("ritualTime");

    }
}
