package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.SolarAbilities.Abilities;
import com.finderfeed.solarforge.SolarAbilities.AbilityClasses.AbstractAbility;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.fmllegacy.network.NetworkEvent;


import java.util.function.Supplier;



public class AbilityBuyPacket {
    private final String str;
    private final BlockPos pos;
    private final int amount;
    public AbilityBuyPacket(FriendlyByteBuf buf){
    str = buf.readUtf(20);
    pos = buf.readBlockPos();
    amount = buf.readInt();
    }
    public AbilityBuyPacket(String str,BlockPos pos,int amount){
        this.str = str;
        this.pos = pos;
        this.amount = amount;

    }
    public void toBytes(FriendlyByteBuf buf){
    buf.writeUtf(str);
    buf.writeBlockPos(pos);
    buf.writeInt(amount);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ServerPlayer enti = ctx.get().getSender();
            Player entity = (Player)enti;

            if (!enti.getPersistentData().getBoolean("solar_forge_can_player_use_"+str) && enti.level.getBlockEntity(pos) != null && enti.level.getBlockEntity(pos) instanceof SolarForgeBlockEntity) {
                SolarForgeBlockEntity blockEntity = (SolarForgeBlockEntity) enti.level.getBlockEntity(pos);
                AbstractAbility ability = Abilities.BY_IDS.get(str).getAbility();
                if (blockEntity.getCurrentEnergy() >= ability.buyCost) {
                    blockEntity.SOLAR_ENERGY_LEVEL-=ability.buyCost;
                    enti.getPersistentData().putBoolean("solar_forge_can_player_use_" + str, true);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
