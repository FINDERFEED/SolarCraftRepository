package com.finderfeed.solarforge.misc_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

public abstract class AbstractUnlockProgressionItem extends Item {
    public Achievement ach;
    public AbstractUnlockProgressionItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!p_77663_2_.isClientSide && p_77663_3_ instanceof PlayerEntity){
            PlayerEntity entity = (PlayerEntity) p_77663_3_;

            if ((Helpers.canPlayerUnlock(getAchivement(),entity)) && !Helpers.hasPlayerUnlocked(getAchivement(),entity)){
                Helpers.setAchievementStatus(getAchivement(),entity,true);
                SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(getAchivement().getId()), ((ServerPlayerEntity)entity).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

    public abstract Achievement getAchivement();
}
