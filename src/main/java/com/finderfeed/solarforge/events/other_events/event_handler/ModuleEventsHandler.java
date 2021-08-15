package com.finderfeed.solarforge.events.other_events.event_handler;

import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.finderfeed.solarforge.magic_items.items.ModuleItem;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModuleEventsHandler {


    @SubscribeEvent
    public static void handleAOEAttackAbility(PlayerInteractEvent.RightClickItem event){
        Player player = event.getPlayer();
        Level world = player.level;
        InteractionHand hand = event.getHand();
        if (!world.isClientSide){
            ItemStack stack = player.getItemInHand(hand);
            if ((stack.getItem() instanceof SwordItem sword) &&  hasModule(ItemsRegister.SWORD_AOE_ATTACK.get(),stack)){
                AABB aabb = new AABB(player.position().add(-2.5,0,-2.5),player.position().add(2.5,player.getBbHeight(),2.5));
                world.getEntitiesOfClass(LivingEntity.class,aabb,(entity)-> !entity.equals(player)).forEach((livingEntity) -> {
                    livingEntity.hurt(DamageSource.playerAttack(player),sword.getDamage()/2);
                });
                player.getCooldowns().addCooldown(sword,100);
                ((ServerLevel)world).playSound(null,player, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.AMBIENT,0.5f,1);
            }
        }
    }

    @SubscribeEvent
    public static void handleSwordAutohealEvent(LivingAttackEvent event){
        DamageSource src = event.getSource();
        if (src != null){
            if ((src.getEntity() instanceof Player player) && !player.level.isClientSide ){
                ItemStack stack = player.getMainHandItem();
                if (!stack.isEmpty()){
                    if (hasModule(ItemsRegister.SWORD_AUTOHEAL_MODULE.get(),stack)){
                        if (player.level.random.nextFloat() <= ((float) SolarcraftConfig.AUTOHEAL_CHANCE.get()/100) ){
                            stack.hurt(-1,player.level.random,(ServerPlayer) player);

                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void handle10PhysicalDefenceModule(LivingDamageEvent event){
        DamageSource src = event.getSource();

        if (!src.isMagic() && !src.isBypassArmor() ){
            LivingEntity entity = event.getEntityLiving();
            entity.getArmorSlots().forEach((stack)->{
                if (hasModule(ItemsRegister.PHYSICAL_DEFENCE_MODULE_10.get(),stack)){
                    float amount = event.getAmount();
                    event.setAmount(amount*0.9f);
                }
            });
        }
    }


    public static boolean hasModule(ModuleItem item, ItemStack target){
        return target.getTagElement(item.getSubTag()) != null;
    }
}
