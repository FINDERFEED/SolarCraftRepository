package com.finderfeed.solarforge.capabilities.solar_lexicon;


import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexicon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachCapability {


    @SubscribeEvent
    public static void attachItemStackCapabilities(final AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().getItem() instanceof SolarLexicon) {
            InventoryProvider prov = new InventoryProvider();
            event.addCapability(new ResourceLocation("solarforge","lexicon_inventory"),prov);
            event.addListener(prov::invalidate);
        }
    }

    @SubscribeEvent
    public static void attachTileCapabilities(final AttachCapabilitiesEvent<BlockEntity> event){

    }
}
