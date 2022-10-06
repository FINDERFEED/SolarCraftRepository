package com.finderfeed.solarforge.events;


import com.finderfeed.solarforge.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarforge.helpers.multiblock.Multiblocks;
import com.finderfeed.solarforge.misc_things.IScrollable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class ScrollThings {

    @SubscribeEvent
    public static void listenToHotkeys(final InputEvent.KeyInputEvent event){
        if (Minecraft.getInstance().screen instanceof IScrollable){
            ((IScrollable) Minecraft.getInstance().screen).performScroll(event.getScanCode());

        }


    }

    @SubscribeEvent
    public static void initMaps(final ClientPlayerNetworkEvent.LoggedInEvent event){
        for (MultiblockStructure structure : Multiblocks.STRUCTURES.values()){
            structure.getBlocks().forEach((pbt)->{
                if (pbt.tile != null){
                    pbt.tile.setLevel(event.getPlayer().level);
                }
            });
        }

    }

//    @SubscribeEvent
//    public static void initRecipes(final RecipesUpdatedEvent event){
////        ProgressionHelper.initInfRecipesMap(event.getRecipeManager());
////        ProgressionHelper.initSmeltingRecipesMap(event.getRecipeManager());
////        ProgressionHelper.initInfusingCraftingRecipes(event.getRecipeManager());
//    }

}
