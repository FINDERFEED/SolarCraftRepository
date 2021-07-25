package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.List;

public abstract class AbstractStructureBlockentity extends BlockEntity  {

    public final Achievement a;
    public final AABB box;

    public AbstractStructureBlockentity(BlockEntityType<?> p_i48289_1_, Achievement a, AABB box) {
        super(p_i48289_1_);
        this.a = a;
        this.box = box;
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide){
            List<Player> list = this.level.getEntitiesOfClass(Player.class,box.move(this.worldPosition));
            for (Player a :list){
                Helpers.fireProgressionEvent(a,this.a);
            }
        }
    }
}
