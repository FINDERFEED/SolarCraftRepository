package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarcraft.content.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.misc_things.IUpgradable;
import com.finderfeed.solarcraft.registries.damage_sources.SolarcraftDamageSources;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SolarGodBow extends RareSolarcraftItem implements IUpgradable {

    public static final int UPGRADE_COUNT = 5;

    private static AABB aoe = new AABB(-15,-5,-15,15,5,15);

    public SolarGodBow(Properties p_41383_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_41383_,fragmentSupplier);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        p_41433_.startUsingItem(p_41434_);
        return super.use(p_41432_, p_41433_, p_41434_);
    }



    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTime) {
        if (entity instanceof ServerPlayer) {
            stack.hurtAndBreak(1,entity,(l)->{
                l.broadcastBreakEvent(l.getUsedItemHand());
            });
        }
        if (!level.isClientSide){
            int i = this.getUseDuration(stack) - remainingTime;
            float power = BowItem.getPowerForTime(i);

            int damage = 10;
            if (getItemLevel(stack) >= 1){
                damage+=5;
            }
            damage *= power;
            Consumer<EntityHitResult> cons = (ctx)-> {
                Entity entity1 = ctx.getEntity();
                if (entity1 instanceof LivingEntity ent) {
                    if (getItemLevel(stack) >= 2){
                        ent.setSecondsOnFire(8);
                    }
                    if (getItemLevel(stack) >= 3) {
                        ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
                    }
                    if (getItemLevel(stack) >= 5){
                        List<LivingEntity> targets = ent.level.getEntitiesOfClass(LivingEntity.class,aoe.move(ent.position()),(target)->{
                           if (target.equals(ent) || target.equals(entity)) {
                               return false;
                           }else{
                               return true;
                           }
                        });
                        targets.forEach((target)->{
                            Vec3 origPos = ent.position().add(0,ent.getBbHeight()/1.2,0);
                            Vec3 targetPos = target.position().add(0,target.getBbHeight()/1.2,0);
                            Vec3 velocity = new Vec3(targetPos.x - origPos.x,targetPos.y - origPos.y,targetPos.z - origPos.z);
                            AbstractTurretProjectile proj = new AbstractTurretProjectile(level,new AbstractTurretProjectile.Constructor()
                                    .setPosition(origPos)
                                    .setVelocity(velocity.normalize().multiply(3,3,3))
                                    .setDamageSource(SolarcraftDamageSources.livingArmorPierceProjectile(entity))
                                    .setDamage(15*power)
                                    .addOnHitEntityEffect((sideContext)->{
                                        if (sideContext.getEntity() instanceof LivingEntity t){
                                            t.setSecondsOnFire(8);
                                            t.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
                                        }
                                    })
                            );
                            ctx.getEntity().level.addFreshEntity(proj);
                        });
                    }
                }
            };
            AbstractTurretProjectile proj = new AbstractTurretProjectile(level,new AbstractTurretProjectile.Constructor()
            .setPosition(entity.position().add(entity.getLookAngle().x,entity.getBbHeight()/1.4 + entity.getLookAngle().y,entity.getLookAngle().z))
                    .setVelocity(entity.getLookAngle().multiply(3,3,3))
                    .setDamageSource(SolarcraftDamageSources.livingArmorPierceProjectile(entity))
                    .setDamage(damage)
                    .addOnHitEntityEffect(cons)
            );

            if ((getItemLevel(stack) >= 4) && power >= 0.8){
                proj.explosionPower = 5;
            }
            level.addFreshEntity(proj);
        }

        super.releaseUsing(stack, level, entity, remainingTime);
    }

//    @Override
//    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
//        if (this.allowedIn(tab)){
////            ItemStack stack = new ItemStack(this);
////            setItemLevel(stack,0);
////            stacks.add(stack);
////            ItemStack stack2 = new ItemStack(this);
////            setItemLevel(stack2,getMaxUpgrades());
////            stacks.add(stack2);
//            for (int i = 0; i <= getMaxUpgrades();i++){
//                ItemStack stack = new ItemStack(this);
//                setItemLevel(stack,i);
//                stacks.add(stack);
//            }
//        }
//    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }



    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> comp, TooltipFlag p_41424_) {
        addComponents(stack,comp);
        super.appendHoverText(stack, level, comp, p_41424_);
    }

    @Override
    public String getUpgradeTagString() {
        return SolarCraftTags.SOLAR_GOD_BOW_TAG;
    }

    @Override
    public int getMaxUpgrades() {
        return UPGRADE_COUNT;
    }

    @Override
    public List<Component> getUpgradeDescriptions() {
        return List.of(
                Component.translatable("solarcraft.god_bow_upgrade_2"),
                Component.translatable("solarcraft.god_bow_upgrade_3"),
                Component.translatable("solarcraft.god_bow_upgrade_4"),
                Component.translatable("solarcraft.god_bow_upgrade_5"),
                Component.translatable("solarcraft.god_bow_upgrade_6")
        );
    }
}
