package com.finderfeed.solarcraft.content.capabilities.solar_lexicon;


import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.registries.SCAttachmentTypes;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

import net.neoforged.neoforge.items.ItemStackHandler;

@Mod.EventBusSubscriber(modid=SolarCraft.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttachCapability {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
//        event.registerItem(Capabilities.ItemHandler.ITEM,(item,ctx)->{
//            return item.getData(SCAttachmentTypes.LEXICON_INVENTORY);
//        },SCItems.SOLAR_LEXICON.get());
//        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level,pos,state,be,block)->{
//            return new ItemStackHandler(10);
//        }, SCBlocks.INFUSING_CRAFTING_TABLE_BLOCK.get());
//        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level,pos,state,be,block)->{
//            return new ItemStackHandler(9);
//        }, SCBlocks.RUNIC_TABLE.get());
//        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level,pos,state,be,block)->{
//            return new ItemStackHandler(14);
//        }, SCBlocks.INFUSER.get());
//        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level,pos,state,be,block)->{
//            return new ItemStackHandler(1);
//        }, SCBlocks.INFUSER_STAND.get());
//        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level,pos,state,be,block)->{
//            return new ItemStackHandler(1);
//        }, SCBlocks.ENCHANTER.get());
//        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level,pos,state,be,block)->{
//            return new ItemStackHandler(2);
//        }, SCBlocks.RUNIC_ENERGY_CHARGER.get());
//        event.registerBlock(Capabilities.ItemHandler.BLOCK,(level,pos,state,be,block)->{
//            return new ItemStackHandler(2);
//        }, SCBlocks.ELEMENT_WEAVER.get());
    }

//    @SubscribeEvent
//    public static void attachItemStackCapabilities(final AttachCapabilitiesEvent<ItemStack> event){
//        if (event.getObject().getItem() instanceof SolarLexicon) {
//            InventoryProvider prov = new InventoryProvider();
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"lexicon_inventory"),prov);
////            event.addListener(prov::invalidate);
//        }
//    }
//
//    @SubscribeEvent
//    public static void attachTileCapabilities(final AttachCapabilitiesEvent<BlockEntity> event){
//        if (event.getObject() instanceof InfusingTableTile tile){
//            InfusingTableInventory inv = new InfusingTableInventory();
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"crafting_table_infuser"),inv);
//        }else if (event.getObject() instanceof RunicTableTileEntity tableTileEntity){
//            RunicTableInventory inventory = new RunicTableInventory();
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"runic_table_inventory"),inventory);
//        }else if (event.getObject() instanceof InfuserTileEntity tileEntity){
//            ItemHandlerInventory inventory = new ItemHandlerInventory(14);
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"infuser_inventory"),inventory);
//        }else if (event.getObject() instanceof InfusingStandTileEntity tileEntity){
//            ItemHandlerInventory inventory = new ItemHandlerInventory(1);
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"infuser_stand_inventory"),inventory);
//        }else if (event.getObject() instanceof EnchanterBlockEntity tileEntity){
//            ItemHandlerInventory inventory = new ItemHandlerInventory(1);
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"enchanter_inventory"),inventory);
//        }else if (event.getObject() instanceof RunicEnergyChargerTileEntity tile){
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"charger_inventory"),new ItemHandlerInventory(2));
//        }else if (event.getObject() instanceof ElementWeaverTileEntity){
//            event.addCapability(new ResourceLocation(SolarCraft.MOD_ID,"element_weaver_inventory"),new ItemHandlerInventory(2));
//        }

}
