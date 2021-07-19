package com.finderfeed.solarforge.packet_handler;

import com.finderfeed.solarforge.capabilities.capability_mana.UpdateManaPacket;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.UpdateProgressOnClientPacket;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.UpdateStacksOnClientTable;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.UpdateStacksOnClientPacketPool;
import com.finderfeed.solarforge.packet_handler.packets.*;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.OpenScreenPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.UpdateAllProgressionOnClient;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.UpdateInventoryPacket;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.packets.UpdateProgressionOnClient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SolarForgePacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("solarforge", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int ID = 0;
    public static int nextID() {
        return ID++;
    }

    public static void registerMessages(){

        INSTANCE.registerMessage(nextID(), CastAbilityPacket.class, CastAbilityPacket::toBytes, CastAbilityPacket::new, CastAbilityPacket::handle);
        INSTANCE.registerMessage(nextID(), AbilityBuyPacket.class, AbilityBuyPacket::toBytes, AbilityBuyPacket::new, AbilityBuyPacket::handle);
        INSTANCE.registerMessage(nextID(), AbilityIndexSetPacket.class, AbilityIndexSetPacket::toBytes, AbilityIndexSetPacket::new, AbilityIndexSetPacket::handle);
        INSTANCE.registerMessage(nextID(), ResetAllAbilitiesPacket.class, ResetAllAbilitiesPacket::toBytes, ResetAllAbilitiesPacket::new, ResetAllAbilitiesPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateManaPacket.class, UpdateManaPacket::toBytes, UpdateManaPacket::new, UpdateManaPacket::handle);
        INSTANCE.registerMessage(nextID(), ToggleAlchemistPacket.class, ToggleAlchemistPacket::toBytes, ToggleAlchemistPacket::new, ToggleAlchemistPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateProgressOnClientPacket.class, UpdateProgressOnClientPacket::toBytes, UpdateProgressOnClientPacket::new, UpdateProgressOnClientPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateStacksOnClientPacketPool.class, UpdateStacksOnClientPacketPool::toBytes, UpdateStacksOnClientPacketPool::new, UpdateStacksOnClientPacketPool::handle);
        INSTANCE.registerMessage(nextID(), UpdateStacksOnClientTable.class, UpdateStacksOnClientTable::toBytes, UpdateStacksOnClientTable::new, UpdateStacksOnClientTable::handle);
        INSTANCE.registerMessage(nextID(), UpdateProgressionOnClient.class, UpdateProgressionOnClient::toBytes, UpdateProgressionOnClient::new, UpdateProgressionOnClient::handle);
        INSTANCE.registerMessage(nextID(), TriggerToastPacket.class, TriggerToastPacket::toBytes, TriggerToastPacket::new, TriggerToastPacket::handle);
        INSTANCE.registerMessage(nextID(), OpenScreenPacket.class, OpenScreenPacket::toBytes, OpenScreenPacket::new, OpenScreenPacket::handle);
        INSTANCE.registerMessage(nextID(), TileEnergyGeneratorUpdate.class, TileEnergyGeneratorUpdate::toBytes, TileEnergyGeneratorUpdate::new, TileEnergyGeneratorUpdate::handle);
        INSTANCE.registerMessage(nextID(), RepeaterParentUpdateOnClient.class, RepeaterParentUpdateOnClient::toBytes, RepeaterParentUpdateOnClient::new, RepeaterParentUpdateOnClient::handle);
        INSTANCE.registerMessage(nextID(), UpdateCoreOnClient.class, UpdateCoreOnClient::toBytes, UpdateCoreOnClient::new, UpdateCoreOnClient::handle);
        INSTANCE.registerMessage(nextID(), SpawnHealParticles.class, SpawnHealParticles::toBytes, SpawnHealParticles::new, SpawnHealParticles::handle);
        INSTANCE.registerMessage(nextID(), ProcImmortalityTotemAnimation.class, ProcImmortalityTotemAnimation::toBytes, ProcImmortalityTotemAnimation::new, ProcImmortalityTotemAnimation::handle);
        INSTANCE.registerMessage(nextID(), PlaySoundPacket.class, PlaySoundPacket::toBytes, PlaySoundPacket::new, PlaySoundPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateAllProgressionOnClient.class, UpdateAllProgressionOnClient::toBytes, UpdateAllProgressionOnClient::new, UpdateAllProgressionOnClient::handle);
        INSTANCE.registerMessage(nextID(), UpdateLaserTrapTile.class, UpdateLaserTrapTile::toBytes, UpdateLaserTrapTile::new, UpdateLaserTrapTile::handle);
        INSTANCE.registerMessage(nextID(), ReloadChunks.class, ReloadChunks::toBytes, ReloadChunks::new, ReloadChunks::handle);
        INSTANCE.registerMessage(nextID(), UpdateInventoryPacket.class, UpdateInventoryPacket::toBytes, UpdateInventoryPacket::new, UpdateInventoryPacket::handle);
        INSTANCE.registerMessage(nextID(), RunicTablePacket.class, RunicTablePacket::toBytes, RunicTablePacket::new, RunicTablePacket::handle);
        INSTANCE.registerMessage(nextID(), UpdatePatternOnScreen.class, UpdatePatternOnScreen::toBytes, UpdatePatternOnScreen::new, UpdatePatternOnScreen::handle);
        INSTANCE.registerMessage(nextID(), UpdateTypeOnClientPacket.class, UpdateTypeOnClientPacket::toBytes, UpdateTypeOnClientPacket::new, UpdateTypeOnClientPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateEnergyOnClientPacket.class, UpdateEnergyOnClientPacket::toBytes, UpdateEnergyOnClientPacket::new, UpdateEnergyOnClientPacket::handle);
        INSTANCE.registerMessage(nextID(), TriggerProgressionShaderPacket.class, TriggerProgressionShaderPacket::toBytes, TriggerProgressionShaderPacket::new, TriggerProgressionShaderPacket::handle);
    }
    //RepeaterParentUpdateOnClient
}
