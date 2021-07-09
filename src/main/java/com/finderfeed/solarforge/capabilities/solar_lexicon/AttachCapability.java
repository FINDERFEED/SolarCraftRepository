package com.finderfeed.solarforge.capabilities.solar_lexicon;


import com.finderfeed.solarforge.capability_mana.PlayerManaProvider;
import com.finderfeed.solarforge.solar_lexicon.SolarLexicon;
import com.finderfeed.solarforge.solar_lexicon.unlockables.AncientFragment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@Mod.EventBusSubscriber(modid="solarforge",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachCapability {


    @SubscribeEvent
    public static void attachCapabilities(final AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().getItem() instanceof SolarLexicon) {
            InventoryProvider prov = new InventoryProvider();
            event.addCapability(new ResourceLocation("solarforge","lexicon_inventory"),prov);
            event.addListener(prov::invalidate);
        }
    }

}
