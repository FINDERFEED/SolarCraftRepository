package com.finderfeed.solarcraft.mixins;

import com.finderfeed.solarcraft.content.entities.ShadowZombie;
import com.finderfeed.solarcraft.content.items.SolarGodSword;
import com.finderfeed.solarcraft.events.other_events.event_handler.ModuleEventsHandler;
import com.finderfeed.solarcraft.registries.damage_sources.SCDamageSources;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Shadow protected abstract void actuallyHurt(DamageSource p_21240_, float p_21241_);

    @Inject(method = "hurt",at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"))
    public void hurt(DamageSource srce, float amount, CallbackInfoReturnable<Boolean> cir){
        LivingEntity entity = (LivingEntity) (Object) this;
        if (srce.getEntity() instanceof ServerPlayer player){
            ItemStack item = player.getMainHandItem();
            float modifier = player.getAttackStrengthScale(0);
            if (item.getItem() instanceof SwordItem sword && ModuleEventsHandler.hasModule(SCItems.MAGIC_DAMAGE_MODULE_5.get(),item)) {
                DamageSource src = SCDamageSources.playerArmorPierce(player);
                if (entity instanceof ShadowZombie) {
                    src = SCDamageSources.RUNIC_MAGIC;
                }
                actuallyHurt(src, modifier * 5);
            }
            if (item.getItem() instanceof SolarGodSword sword && sword.getItemLevel(item) >= 1){
                DamageSource src = SCDamageSources.playerArmorPierce(player);
                if (entity instanceof ShadowZombie) {
                    src = SCDamageSources.RUNIC_MAGIC;
                }
                actuallyHurt(src, modifier * 5);
            }
        }
    }

}
