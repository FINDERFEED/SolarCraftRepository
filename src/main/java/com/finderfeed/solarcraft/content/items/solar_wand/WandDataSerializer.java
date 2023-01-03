package com.finderfeed.solarcraft.content.items.solar_wand;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface WandDataSerializer<T extends WandData<T>> {

    T deserialize(CompoundTag item);

    void serialize(CompoundTag item,T data);

    default void hackySerialize(CompoundTag tag,WandData<?> data){
        this.serialize(tag,(T)data);
    }

    /**
     * returns a tag to save data to
     */
    default CompoundTag getTag(ItemStack stack){
        return stack.getOrCreateTag().getCompound(this.getDataName().toString());
    }

    ResourceLocation getDataName();
}
