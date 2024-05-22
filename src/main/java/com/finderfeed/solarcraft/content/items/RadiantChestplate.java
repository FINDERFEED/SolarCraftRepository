package com.finderfeed.solarcraft.content.items;


import com.finderfeed.solarcraft.content.items.primitive.solacraft_item_classes.SolarcraftArmorItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.registries.SCConfigs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;


import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class RadiantChestplate extends SolarcraftArmorItem {
    public RadiantChestplate(ArmorMaterial p_i48534_1_, Type p_i48534_2_, Properties p_i48534_3_, Supplier<AncientFragment> fragmentSupplier) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_,fragmentSupplier);

    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean inHand) {

        if (Helpers.ARMOR_SLOTS.contains(slot)){
            if (!world.isClientSide){
                AABB box = new AABB(-6,-3,-6,6,3,6).move(entity.position().add(0,1.5,0));
                if (entity.tickCount % 20 == 0) {
                    world.getEntitiesOfClass(LivingEntity.class,box,(e)-> !e.equals(entity) && !(e instanceof Player)).forEach((e)->{


                            ((ServerLevel)world).sendParticles(ParticleTypes.FLAME,e.getX(),e.getY()+e.getBbHeight()*0.5,e.getZ(),5,0,0,0,0.05);
                            e.hurt(world.damageSources().magic(), SCConfigs.ITEMS.radiantChestplateDamage);
                            e.setSecondsOnFire(2);
                            e.invulnerableTime = 0;


                    });
                }

            }
        }

        super.inventoryTick(stack, world, entity, slot, inHand);
    }

//    @Override
//    public void onArmorTick(ItemStack stack, Level world, Player player) {
//        if (!world.isClientSide){
//            AABB box = new AABB(-6,-3,-6,6,3,6).move(player.position().add(0,1.5,0));
//            world.getEntitiesOfClass(LivingEntity.class,box,(players)-> !players.equals(player)).forEach((entity)->{
//                if (player.tickCount % 20 == 0) {
//
//                    ((ServerLevel)world).sendParticles(ParticleTypes.FLAME,entity.getX(),entity.getY()+entity.getBbHeight()*0.5,entity.getZ(),5,0,0,0,0.05);
//                    entity.hurt(world.damageSources().magic(), 1.5f);
//                    entity.setSecondsOnFire(2);
//                    entity.invulnerableTime=0;
//
//                }
//            });
//
//        }
//        super.onArmorTick(stack, world, player);
//    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable Level p_77624_2_, List<Component> p_77624_3_, TooltipFlag p_77624_4_) {
        String chance = "%.1f".formatted(SCConfigs.ITEMS.radiantChestplateEvasionChance * 100);
        p_77624_3_.add(Component.translatable("solarcraft.radiant_chestplate",chance).withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
