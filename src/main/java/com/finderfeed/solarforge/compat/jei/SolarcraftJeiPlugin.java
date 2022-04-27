package com.finderfeed.solarforge.compat.jei;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.jei.recipe_categories.InfusingCraftingRecipeCategory;
import com.finderfeed.solarforge.compat.jei.recipe_categories.InfusingRecipeCategory;
import com.finderfeed.solarforge.compat.jei.recipe_categories.SmeltingRecipeCategory;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.screens.EnchanterContainerScreen;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@JeiPlugin
public class SolarcraftJeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SolarForge.MOD_ID,"jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        ClientLevel world = Minecraft.getInstance().level;
        List<InfusingRecipe> w = world.getRecipeManager().getAllRecipesFor(SolarForge.INFUSING_RECIPE_TYPE);
        registration.addRecipes(JeiRecipeTypes.INFUSING_RECIPE,w);
        List<InfusingCraftingRecipe> w1 = world.getRecipeManager().getAllRecipesFor(SolarForge.INFUSING_CRAFTING_RECIPE_TYPE);
        List<SolarSmeltingRecipe> sm = world.getRecipeManager().getAllRecipesFor(SolarForge.SOLAR_SMELTING);
        registration.addRecipes(JeiRecipeTypes.INFUSING_CRAFTING_RECIPE,w1);
        registration.addRecipes(JeiRecipeTypes.SMELTING,sm);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
        registration.addGuiContainerHandler(EnchanterContainerScreen.class, new IGuiContainerHandler<EnchanterContainerScreen>() {
            @Override
            public List<Rect2i> getGuiExtraAreas(EnchanterContainerScreen containerScreen) {
                return List.of(new Rect2i(0,0,10000,10000));
            }
        });
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        registration.addRecipeCategories(new InfusingRecipeCategory(registration.getJeiHelpers()));
        registration.addRecipeCategories(new InfusingCraftingRecipeCategory(registration.getJeiHelpers()));
        registration.addRecipeCategories(new SmeltingRecipeCategory(registration.getJeiHelpers()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(SolarForge.INFUSER_ITEM.get().getDefaultInstance(),JeiRecipeTypes.INFUSING_RECIPE);
        registration.addRecipeCatalyst(ItemsRegister.SOLAR_LENS.get().getDefaultInstance(),JeiRecipeTypes.SMELTING);
        registration.addRecipeCatalyst(ItemsRegister.INFUSING_TABLE.get().getDefaultInstance(),JeiRecipeTypes.INFUSING_CRAFTING_RECIPE);


    }
}
