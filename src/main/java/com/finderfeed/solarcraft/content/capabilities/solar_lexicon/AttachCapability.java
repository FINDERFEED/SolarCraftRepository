package com.finderfeed.solarcraft.content.capabilities.solar_lexicon;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.blockentities.*;
import com.finderfeed.solarcraft.content.capabilities.InfusingTableInventory;
import com.finderfeed.solarcraft.content.capabilities.ItemHandlerInventory;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStandTileEntity;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AttachCapabilitiesEvent;

@Mod.EventBusSubscriber(modid="solarcraft",bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachCapability {


    @SubscribeEvent
    public static void attachItemStackCapabilities(final AttachCapabilitiesEvent<ItemStack> event){
        if (event.getObject().getItem() instanceof SolarLexicon) {
            InventoryProvider prov = new InventoryProvider();
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"lexicon_inventory"),prov);
//            event.addListener(prov::invalidate);
        }
    }

    @SubscribeEvent
    public static void attachTileCapabilities(final AttachCapabilitiesEvent<BlockEntity> event){
        if (event.getObject() instanceof InfusingTableTile tile){
            InfusingTableInventory inv = new InfusingTableInventory();
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"crafting_table_infuser"),inv);
        }else if (event.getObject() instanceof RunicTableTileEntity tableTileEntity){
            RunicTableInventory inventory = new RunicTableInventory();
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"runic_table_inventory"),inventory);
        }else if (event.getObject() instanceof InfuserTileEntity tileEntity){
            ItemHandlerInventory inventory = new ItemHandlerInventory(14);
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"infuser_inventory"),inventory);
        }else if (event.getObject() instanceof InfusingStandTileEntity tileEntity){
            ItemHandlerInventory inventory = new ItemHandlerInventory(1);
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"infuser_stand_inventory"),inventory);
        }else if (event.getObject() instanceof EnchanterBlockEntity tileEntity){
            ItemHandlerInventory inventory = new ItemHandlerInventory(1);
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"enchanter_inventory"),inventory);
        }else if (event.getObject() instanceof RunicEnergyChargerTileEntity tile){
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"charger_inventory"),new ItemHandlerInventory(2));
        }else if (event.getObject() instanceof ElementWeaverTileEntity){
            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"element_weaver_inventory"),new ItemHandlerInventory(2));
        }
    }
}
