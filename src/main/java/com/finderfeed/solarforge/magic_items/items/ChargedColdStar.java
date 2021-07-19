package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.Helpers;

import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

public class ChargedColdStar extends Item {
    public ChargedColdStar(Properties p_i48487_1_) {
        super(p_i48487_1_);

    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!p_77663_2_.isClientSide && p_77663_3_ instanceof PlayerEntity){
            Helpers.fireProgressionEvent((PlayerEntity) p_77663_3_,Achievement.ACQUIRE_COLD_STAR_ACTIVATED);
        }
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

}
