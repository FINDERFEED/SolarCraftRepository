package com.finderfeed.solarforge.content.blocks.blockentities;

import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.other.InterpolatedValue;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class SolarcraftBlockEntity extends BlockEntity {

    private Map<String, InterpolatedValue> ANIMATION_VALUES = new HashMap<>();

    public SolarcraftBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Nullable
    public InterpolatedValue getAnimationValue(String id){
        return ANIMATION_VALUES.get(id);
    }

    public void addAnimationValue(String id,InterpolatedValue value){
        if (!ANIMATION_VALUES.containsKey(id)){
            ANIMATION_VALUES.put(id,value);
        }
    }

    public void deleteAnimationValue(String id){
        ANIMATION_VALUES.remove(id);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return Helpers.createTilePacket(this,tag);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
        super.onDataPacket(net, pkt);
    }
}
