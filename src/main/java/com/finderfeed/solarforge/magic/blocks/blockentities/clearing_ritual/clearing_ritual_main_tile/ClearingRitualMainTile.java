package com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.ClearingRitual;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ClearingRitualMainTile extends BlockEntity {


    public ClearingRitual ritual = new ClearingRitual(this);


    public ClearingRitualMainTile(BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.CLEARING_RITUAL_MAIN_BLOCK.get(), pos, state);
    }

    public static void tick(ClearingRitualMainTile tile, BlockPos pos, BlockState state, Level world){
        tile.ritual.tick();
    }

    public void startRitual(){
        ritual.tryStartRitual();
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
        ritual.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ritual.load(tag);
    }
}
