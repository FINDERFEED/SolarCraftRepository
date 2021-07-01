package com.finderfeed.solarforge.magic_items.items;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import java.util.Optional;

public class BlueGemItem extends Item {

    public BlueGemItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level.isClientSide){
            World world = entity.level;
            Optional<MutableRegistry<Biome>> reg = world.registryAccess().registry(Registry.BIOME_REGISTRY);
            if (reg.isPresent()) {
                if ((world.getBiome(entity.blockPosition()).equals(reg.get().get(FeaturesRegistry.MOLTEN_BIOME_KEY)))
                        && entity.level.getBlockState(entity.blockPosition()).is(Blocks.LAVA)) {
                    int ticks = entity.getPersistentData().getInt("transmutation_ticks")+1;
                    entity.getPersistentData().putInt("transmutation_ticks", ticks);

                    if (ticks >= 1000){
                        for (int i = 0; i < stack.getCount();i++) {
                            world.addFreshEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(ItemsRegister.BLUE_GEM_ENCHANCED.get(), 1)));
                        }
                        entity.remove();
                    }

                } else {

                    entity.getPersistentData().putInt("transmutation_ticks", 0);
                }
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }


}
