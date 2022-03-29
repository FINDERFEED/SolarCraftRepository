package com.finderfeed.solarforge.capabilities.solar_lexicon;


import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.capabilities.InfusingTableInventory;
import com.finderfeed.solarforge.capabilities.ItemHandlerInventory;
import com.finderfeed.solarforge.magic.blocks.blockentities.EnchanterBlockEntity;
import com.finderfeed.solarforge.magic.blocks.blockentities.InfusingTableTile;
import com.finderfeed.solarforge.magic.blocks.blockentities.RunicEnergyChargerTileEntity;
import com.finderfeed.solarforge.magic.blocks.blockentities.RunicTableTileEntity;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.infusing_pool.InfusingStand;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.infusing_pool.InfusingStandTileEntity;
import com.finderfeed.solarforge.magic.items.solar_lexicon.SolarLexicon;
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
        if (event.getObject() instanceof InfusingTableTile tile){
            InfusingTableInventory inv = new InfusingTableInventory();
            event.addCapability(new ResourceLocation(SolarForge.MOD_ID,"crafting_table_infuser"),inv);
        }else if (event.getObject() instanceof RunicTableTileEntity tableTileEntity){
            RunicTableInventory inventory = new RunicTableInventory();
            event.addCapability(new ResourceLocation(SolarForge.MOD_ID,"runic_table_inventory"),inventory);
        }else if (event.getObject() instanceof InfuserTileEntity tileEntity){
            ItemHandlerInventory inventory = new ItemHandlerInventory(14);
            event.addCapability(new ResourceLocation(SolarForge.MOD_ID,"infuser_inventory"),inventory);
        }else if (event.getObject() instanceof InfusingStandTileEntity tileEntity){
            ItemHandlerInventory inventory = new ItemHandlerInventory(1);
            event.addCapability(new ResourceLocation(SolarForge.MOD_ID,"infuser_stand_inventory"),inventory);
        }else if (event.getObject() instanceof EnchanterBlockEntity tileEntity){
            ItemHandlerInventory inventory = new ItemHandlerInventory(1);
            event.addCapability(new ResourceLocation(SolarForge.MOD_ID,"enchanter_inventory"),inventory);
        }else if (event.getObject() instanceof RunicEnergyChargerTileEntity tile){
            event.addCapability(new ResourceLocation(SolarForge.MOD_ID,"charger_inventory"),new ItemHandlerInventory(2));
        }
    }
}
