package com.finderfeed.solarcraft.content.items.item_tiers;

import com.finderfeed.solarcraft.SolarCraftTags;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum SolarCraftToolTiers implements Tier {
    ILLIDIUM_TOOLS_TIER(5, 2500, 9.5f, 6.0F, 20,()-> Ingredient.of(SolarcraftItems.ILLIDIUM_INGOT.get()), SolarCraftTags.ILLIDIUM_TAG),
    QUALADIUM_TOOLS_TIER(5, 4000, 11f, 13.0F, 25,()-> Ingredient.of(SolarcraftItems.QUALADIUM_INGOT.get()),SolarCraftTags.QUALADIUM_TAG),
    CHARGED_QUALADIUM_TOOLS_TIER(5, 4200, 11.5f, 13.5F, 40,()-> Ingredient.of(SolarcraftItems.QUALADIUM_INGOT.get()),SolarCraftTags.CHARGED_QUALADIUM_TAG),
    SOLAR_GOD_TOOL_TIER(6, 8000, 17f, 17F, 45,()-> Ingredient.of(SolarcraftItems.CHARGED_QUALADIUM_INGOT.get()),SolarCraftTags.SOLAR_GOD_TAG),
    DIVINE_TIER(10, 16000, 30f, 25F, 50,()-> Ingredient.EMPTY,SolarCraftTags.DIVINE);



    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final TagKey<Block> blockTag;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    SolarCraftToolTiers(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_,TagKey<Block> tag) {
        this.level = p_i48458_3_;
        this.uses = p_i48458_4_;
        this.speed = p_i48458_5_;
        this.damage = p_i48458_6_;
        this.enchantmentValue = p_i48458_7_;
        this.repairIngredient = new LazyLoadedValue<>(p_i48458_8_);
        this.blockTag = tag;

    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Nullable
    @Override
    public TagKey<Block> getTag() {
        return blockTag;
    }
}
