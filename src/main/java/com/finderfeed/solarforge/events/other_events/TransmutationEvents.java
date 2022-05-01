package com.finderfeed.solarforge.events.other_events;

import com.finderfeed.solarforge.abilities.AbilityHelper;
import com.finderfeed.solarforge.magic.items.runic_energy.ItemRunicEnergy;
import com.finderfeed.solarforge.magic.items.vein_miner.IllidiumPickaxe;
import com.finderfeed.solarforge.misc_things.RunicEnergy;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TransmutationEvents {

    @SubscribeEvent
    public static void breakBlock(final BlockEvent.BreakEvent event){
        if (event.getPlayer() instanceof ServerPlayer player) {
            if (AbilitiesRegistry.ALCHEMIST.isToggled(player) && !event.getPlayer().isDeadOrDying() ) {
                if (AbilityHelper.isAbilityUsable(player,AbilitiesRegistry.ALCHEMIST)) {
                    LevelAccessor world = event.getWorld();
                    BlockPos pos = event.getPos();
                    event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
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
