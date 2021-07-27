package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.capabilities.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.magic_items.items.vein_miner.VeinMiner;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.TriggerToastPacket;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.achievements.Achievement;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ExperienceOrb;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TransmutationEvents {

    @SubscribeEvent
    public static void breakBlock(final BlockEvent.BreakEvent event){

        if (event.getPlayer().getPersistentData().getBoolean("is_alchemist_toggled") && !event.getPlayer().isDeadOrDying() &&
                event.getPlayer().getPersistentData().getBoolean("solar_forge_can_player_use_alchemist")) {
            if (event.getPlayer().getCapability(CapabilitySolarMana.SOLAR_MANA_PLAYER).orElseThrow(RuntimeException::new).getMana() >= 0.5) {
                LevelAccessor world = event.getWorld();
                BlockPos pos = event.getPos();
                event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), Constants.BlockFlags.DEFAULT);
                world.addFreshEntity(new ExperienceOrb((Level) world, pos.getX(), pos.getY(), pos.getZ(), 10));
            }
        }

        if (event.getState().getBlock() == BlocksRegistry.SOLAR_STONE.get() ){
            Helpers.fireProgressionEvent(event.getPlayer(),Achievement.FIND_SOLAR_STONE);
        }

        if (event.getPlayer().getMainHandItem().getItem() instanceof VeinMiner){
            if (!Helpers.canCast(event.getPlayer(),((VeinMiner) event.getPlayer().getMainHandItem().getItem()).getManacost())){
                event.setCanceled(true);
            }
        }
    }


}
