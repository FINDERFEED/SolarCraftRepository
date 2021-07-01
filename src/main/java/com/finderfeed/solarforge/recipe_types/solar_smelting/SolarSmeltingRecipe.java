package com.finderfeed.solarforge.recipe_types.solar_smelting;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.recipe_types.InfusingRecipeSerializer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class SolarSmeltingRecipe implements IRecipe<PhantomInventory> {
    public final ResourceLocation id;
    public final NonNullList<Ingredient> list;
    public final ItemStack output;
    public final String child;
    public final String category;
    public final int smeltingTime;
    public static final SolarSmeltingRecipeSerializer serializer = new SolarSmeltingRecipeSerializer();
    public SolarSmeltingRecipe(ResourceLocation id, NonNullList<Ingredient> list, ItemStack output, int infusingTime, String child, String category) {
        this.id = id;
        this.list = list;
        this.output = output;
        this.smeltingTime = infusingTime;
        this.child = child;
        this.category = category;
    }

    public int getInfusingTime(){
        return smeltingTime;
    }

    @Override
    public boolean matches(PhantomInventory inv, World world) {

        NonNullList<Item> lists = NonNullList.withSize(4, Items.AIR);
        for (int i = 0;i < 4;i++){
            lists.set(i,inv.INVENTORY.get(i).getItem());
        }

        if (lists.contains(list.get(0).getItems()[0].getItem()) &&
                lists.contains(list.get(1).getItems()[0].getItem())&&
                lists.contains(list.get(2).getItems()[0].getItem())&&
                lists.contains(list.get(3).getItems()[0].getItem())){
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(PhantomInventory inv) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public IRecipeType<?> getType() {
        return SolarForge.SOLAR_SMELTING;
    }
}
