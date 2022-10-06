package com.finderfeed.solarforge.content.items;

import com.finderfeed.solarforge.SolarCraftTags;
import com.finderfeed.solarforge.content.blocks.blockentities.projectiles.AbstractTurretProjectile;
import com.finderfeed.solarforge.content.items.primitive.RareSolarcraftItem;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarforge.misc_things.ITagUser;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
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

public class SolarGodBow extends RareSolarcraftItem implements ITagUser {

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
            if (getLevel(stack) >= 2){
                damage+=5;
            }
            damage *= power;
            Consumer<EntityHitResult> cons = (ctx)-> {
                Entity entity1 = ctx.getEntity();
                if (entity1 instanceof LivingEntity ent) {
                    if (getLevel(stack) >= 3){
                        ent.setSecondsOnFire(8);
                    }
                    if (getLevel(stack) >= 4) {
                        ent.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
                    }
                    if (getLevel(stack) >= 6){
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
                                    .setDamageSource(DamageSource.MAGIC)
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
                    .setDamageSource(DamageSource.MAGIC)
                    .setDamage(damage)
                    .addOnHitEntityEffect(cons)
            );

            if ((getLevel(stack) >= 5) && power >= 0.8){
                proj.explosionPower = 5;
            }
            level.addFreshEntity(proj);
        }

        super.releaseUsing(stack, level, entity, remainingTime);
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stacks) {
        if (this.allowdedIn(tab)){
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_BOW_TAG).putInt(SolarCraftTags.SOLAR_GOD_BOW_LEVEL_TAG,1);
            stacks.add(stack);
            ItemStack stack2 = new ItemStack(this);
            stack2.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_BOW_TAG).putInt(SolarCraftTags.SOLAR_GOD_BOW_LEVEL_TAG,6);
            stacks.add(stack2);

        }
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }

    @Override
    public void doThingsWithTag(ItemStack prev, ItemStack stack, String tag) {
        if (getTag(prev) != null){
            stack.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_BOW_TAG);
            CompoundTag newtag = getTag(stack);
            if (getLevel(prev) < 6){
                newtag.putInt(SolarCraftTags.SOLAR_GOD_BOW_LEVEL_TAG,getLevel(prev)+1);
            }
        }else {
            prev.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_BOW_TAG).putInt(SolarCraftTags.SOLAR_GOD_BOW_LEVEL_TAG,2);
        }
    }

    private CompoundTag getTag(ItemStack stack){
        return stack.getTagElement(SolarCraftTags.SOLAR_GOD_BOW_TAG);
    }

    private int getLevel(ItemStack stack){
        return stack.getTagElement(SolarCraftTags.SOLAR_GOD_BOW_TAG).getInt(SolarCraftTags.SOLAR_GOD_BOW_LEVEL_TAG);
    }

    @Override
    public void inventoryTick(ItemStack p_41404_, Level p_41405_, Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (getTag(p_41404_) == null){
            p_41404_.getOrCreateTagElement(SolarCraftTags.SOLAR_GOD_BOW_TAG).putInt(SolarCraftTags.SOLAR_GOD_SWORD_LEVEL_TAG,1);
        }
        super.inventoryTick(p_41404_, p_41405_, p_41406_, p_41407_, p_41408_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> comp, TooltipFlag p_41424_) {
        CompoundTag tag = getTag(stack);
        if (tag != null){
            comp.add(Component.translatable("solarcraft.god_bow_upgrade").withStyle(ChatFormatting.GOLD).append(Component.literal(" "+getLevel(stack))));
            addLevelDesc(stack,comp,Component.translatable("solarcraft.god_bow_upgrade_2"),2);
            addLevelDesc(stack,comp,Component.translatable("solarcraft.god_bow_upgrade_3"),3);
            addLevelDesc(stack,comp,Component.translatable("solarcraft.god_bow_upgrade_4"),4);
            addLevelDesc(stack,comp,Component.translatable("solarcraft.god_bow_upgrade_5"),5);
            addLevelDesc(stack,comp,Component.translatable("solarcraft.god_bow_upgrade_6"),6);
        }
        super.appendHoverText(stack, level, comp, p_41424_);
    }

    private void addLevelDesc(ItemStack stack,List<Component> toAdd, Component desc, int reqLevel){
        if (getLevel(stack) >= reqLevel){
            toAdd.add(desc.withStyle(ChatFormatting.GOLD));
        }else{
            toAdd.add(desc.withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.STRIKETHROUGH).withStyle(ChatFormatting.ITALIC));
        }
    }
}
