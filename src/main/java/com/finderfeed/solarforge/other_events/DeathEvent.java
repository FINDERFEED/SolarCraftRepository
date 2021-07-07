package com.finderfeed.solarforge.other_events;


import com.finderfeed.solarforge.magic_items.items.TotemOfImmortality;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packets.ProcImmortalityTotemAnimation;
import com.finderfeed.solarforge.registries.effects.EffectsRegister;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeathEvent {


    @SubscribeEvent
    public static void procImmortalityTotem(final LivingDeathEvent event){
        if (event.getEntityLiving() instanceof PlayerEntity && !event.getEntityLiving().level.isClientSide){
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            int slot = findImmortalityTotem(player);
            if (slot != -10000){
                player.addEffect(new EffectInstance(EffectsRegister.IMMORTALITY_EFFECT.get(),400,0));
                player.setHealth(player.getMaxHealth());

                player.inventory.setItem(slot, ItemStack.EMPTY);
                ServerWorld world = (ServerWorld)player.level;
                world.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.TOTEM_USE, SoundCategory.AMBIENT,0.5f,0.5f);
                SolarForgePacketHandler.INSTANCE.sendTo(new ProcImmortalityTotemAnimation(),((ServerPlayerEntity)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);

                event.setCanceled(true);
            }
        }

    }


    public static int findImmortalityTotem(PlayerEntity player){

            for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                if (player.inventory.getItem(i).getItem() instanceof TotemOfImmortality) {
                    return i;
                }
            }



        return -10000;
    }
}
