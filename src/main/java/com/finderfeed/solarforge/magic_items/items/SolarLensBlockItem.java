package com.finderfeed.solarforge.magic_items.items;


import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

public class SolarLensBlockItem extends BlockItem {
    public SolarLensBlockItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);

    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!p_77663_2_.isClientSide && p_77663_3_ instanceof PlayerEntity){

            Helpers.fireProgressionEvent((PlayerEntity) p_77663_3_,Achievement.CRAFT_SOLAR_LENS);
        }
        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }

}
