package com.finderfeed.solarforge.solar_forge_block;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.solar_lexicon.achievements.Achievement;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nullable;
import java.util.List;

public class SolarForgeBlockItem extends BlockItem {
    public SolarForgeBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }



    @Override
    public void onCraftedBy(ItemStack stack, World world, PlayerEntity pe) {
        if (!world.isClientSide){
            if (!Helpers.hasPlayerUnlocked(Achievement.CRAFT_SOLAR_FORGE,pe) && Helpers.canPlayerUnlock(Achievement.CRAFT_SOLAR_FORGE,pe)){
                pe.getPersistentData().putBoolean(Helpers.PROGRESSION+ Achievement.CRAFT_SOLAR_FORGE.getAchievementCode(),true);
                SolarForgePacketHandler.INSTANCE.sendTo(new TriggerToastPacket(Achievement.CRAFT_SOLAR_FORGE.getId()), ((ServerPlayerEntity) pe).connection.connection, NetworkDirection.PLAY_TO_CLIENT);

            }
        }

        super.onCraftedBy(stack,world,pe);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> list, ITooltipFlag p_77624_4_) {

        list.add(new StringTextComponent("ALL ENERGY IS LOST WHEN BROKEN!").withStyle(TextFormatting.RED));
        super.appendHoverText(p_77624_1_, p_77624_2_, list, p_77624_4_);
    }
}
