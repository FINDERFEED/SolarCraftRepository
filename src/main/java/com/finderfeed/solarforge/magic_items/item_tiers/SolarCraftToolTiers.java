package com.finderfeed.solarforge.magic_items.item_tiers;

import com.finderfeed.solarforge.registries.items.ItemsRegister;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum SolarCraftToolTiers implements IItemTier {
    ILLIDIUM_TOOLS_TIER(5, 2500, 9.5f, 6.0F, 20,()-> Ingredient.of(ItemsRegister.ILLIDIUM_INGOT.get())),
    QUALADIUM_TOOLS_TIER(5, 4000, 11f, 13.0F, 25,()-> Ingredient.of(ItemsRegister.QUALADIUM_INGOT.get())),
    CHARGED_QUALADIUM_TOOLS_TIER(5, 4200, 11.5f, 13.5F, 40,()-> Ingredient.of(ItemsRegister.QUALADIUM_INGOT.get())),
    SOLAR_GOD_TOOL_TIER(6, 8000, 17f, 17F, 45,()-> Ingredient.of(ItemsRegister.CHARGED_QUALADIUM_INGOT.get()));


    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyValue<Ingredient> repairIngredient;

    SolarCraftToolTiers(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
        this.level = p_i48458_3_;
        this.uses = p_i48458_4_;
        this.speed = p_i48458_5_;
        this.damage = p_i48458_6_;
        this.enchantmentValue = p_i48458_7_;
        this.repairIngredient = new LazyValue<>(p_i48458_8_);
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

}
