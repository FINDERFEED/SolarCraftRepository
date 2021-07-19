package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.List;

public abstract class AbstractStructureBlockentity extends TileEntity implements ITickableTileEntity {

    public final Achievement a;
    public final AxisAlignedBB box;

    public AbstractStructureBlockentity(TileEntityType<?> p_i48289_1_, Achievement a, AxisAlignedBB box) {
        super(p_i48289_1_);
        this.a = a;
        this.box = box;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide){
            List<PlayerEntity> list = this.level.getEntitiesOfClass(PlayerEntity.class,box.move(this.worldPosition));
            for (PlayerEntity a :list){
                Helpers.fireProgressionEvent(a,this.a);
            }
        }
    }
}
