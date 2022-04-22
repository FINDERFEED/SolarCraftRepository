package com.finderfeed.solarforge.compat.jei;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.compat.jei.recipe_categories.InfusingRecipeCategory;
import com.finderfeed.solarforge.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
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

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        registration.addRecipeCategories(new InfusingRecipeCategory(registration.getJeiHelpers()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(SolarForge.INFUSER_ITEM.get().getDefaultInstance(),JeiRecipeTypes.INFUSING_RECIPE);


    }
}
