package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.TriggerToastPacket;
import com.finderfeed.solarforge.content.items.solar_lexicon.progressions.Progression;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;


public abstract class AbstractUnlockProgressionItem extends Item {
    public Progression ach;
    public AbstractUnlockProgressionItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public void inventoryTick(ItemStack p_77663_1_, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!p_77663_2_.isClientSide && p_77663_3_ instanceof Player){
            Player entity = (Player) p_77663_3_;

            if ((Helpers.canPlayerUnlock(getAchivement(),entity)) && !Helpers.hasPlayerCompletedProgression(getAchivement(),entity)){
                Helpers.setProgressionCompletionStatus(getAchivement(),entity,true);
                SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(getAchivement().getId()), ((ServerPlayer)entity).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

    public abstract Progression getAchivement();
}
