package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import java.util.Optional;

import net.minecraft.world.item.Item.Properties;
import org.lwjgl.system.CallbackI;

public class BlueGemItem extends Item {

    public BlueGemItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level.isClientSide){

            Level world = entity.level;
            Optional<? extends Registry<Biome>> reg = world.registryAccess().registry(Registry.BIOME_REGISTRY);
            if (reg.isPresent()) {
                if ((world.getBiome(entity.blockPosition()).equals(reg.get().get(FeaturesRegistry.MOLTEN_BIOME_KEY)))
                        && entity.level.getBlockState(entity.blockPosition()).is(Blocks.LAVA)) {
                    int ticks = entity.getPersistentData().getInt("transmutation_ticks")+1;
                    entity.getPersistentData().putInt("transmutation_ticks", ticks);

                    if (ticks >= 1000){
                        for (int i = 0; i < stack.getCount();i++) {
                            world.addFreshEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(ItemsRegister.BLUE_GEM_ENCHANCED.get(), 1)));
                        }
                        entity.remove(Entity.RemovalReason.KILLED);
                    }

                } else {

                    entity.getPersistentData().putInt("transmutation_ticks", 0);
                }
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }


}
