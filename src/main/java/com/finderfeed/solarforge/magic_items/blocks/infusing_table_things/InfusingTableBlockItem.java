package com.finderfeed.solarforge.magic_items.blocks.infusing_table_things;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

public class InfusingTableBlockItem extends BlockItem {
    public InfusingTableBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }


    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity pe) {
        if (!world.isClientSide){
            if (!Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_INFUSER,pe) && Helpers.canPlayerUnlock(Achievement.CRAFT_SOLAR_INFUSER,pe)){
                pe.getPersistentData().putBoolean(Helpers.PROGRESSION+ Achievement.CRAFT_SOLAR_INFUSER.getAchievementCode(),true);
                SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(Achievement.CRAFT_SOLAR_INFUSER.getId()), ((ServerPlayerEntity) pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }
        }
        super.onCraftedBy(stack,world,pe);
    }
}
