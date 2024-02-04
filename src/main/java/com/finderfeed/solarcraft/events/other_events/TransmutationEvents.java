package com.finderfeed.solarcraft.events.other_events;

import com.finderfeed.solarcraft.content.abilities.AbilityHelper;
import com.finderfeed.solarcraft.content.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarcraft.content.items.vein_miner.IllidiumPickaxe;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;


@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TransmutationEvents {

    @SubscribeEvent
    public static void breakBlock(final BlockEvent.BreakEvent event){
        if (event.getPlayer() instanceof ServerPlayer player) {
            if (AbilitiesRegistry.ALCHEMIST.isToggled(player) && !event.getPlayer().isDeadOrDying() ) {
                if (AbilityHelper.isAbilityUsable(player,AbilitiesRegistry.ALCHEMIST,false)) {
                    LevelAccessor world = event.getLevel();
                    BlockPos pos = event.getPos();
                    world.setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
                    world.addFreshEntity(new ExperienceOrb((Level) world, pos.getX(), pos.getY(), pos.getZ(), 10));
                }
            }
        }
        ItemStack stack = event.getPlayer().getMainHandItem();
        if (stack.getItem() instanceof IllidiumPickaxe pick){
            if (!ItemRunicEnergy.spendEnergy(pick.getCost(),stack,pick, event.getPlayer())){
                event.setCanceled(true);
            }
        }
    }


}
