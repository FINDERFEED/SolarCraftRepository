package com.finderfeed.solarforge.magic_items.items;

import net.minecraft.client.shader.ShaderInstance;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


import javax.annotation.Nullable;
import java.util.List;

public class RadiantChestplate extends ArmorItem {
    public RadiantChestplate(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
    }


    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        if (!world.isClientSide){
            AxisAlignedBB box = new AxisAlignedBB(-6,-3,-6,6,3,6).move(player.position().add(0,1.5,0));
            world.getEntitiesOfClass(LivingEntity.class,box,(players)-> !players.equals(player)).forEach((entity)->{
                if (player.tickCount % 20 == 0) {

                    ((ServerWorld)world).sendParticles(ParticleTypes.FLAME,entity.getX(),entity.getY()+entity.getBbHeight()*0.5,entity.getZ(),5,0,0,0,0.05);
                    entity.hurt(DamageSource.MAGIC, 1.5f);
                    entity.setSecondsOnFire(2);
                    entity.invulnerableTime=0;

                }
            });

        }
        super.onArmorTick(stack, world, player);
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
            p_77624_3_.add(new TranslationTextComponent("solarforge.radiant_chestplate").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }
}
