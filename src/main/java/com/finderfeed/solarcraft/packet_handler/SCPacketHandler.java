package com.finderfeed.solarcraft.packet_handler;

import com.finderfeed.solarcraft.content.blocks.infusing_table_things.UpdateProgressOnClientPacket;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.UpdateStacksOnClientTable;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.UpdateStacksOnClientPacketPool;
import com.finderfeed.solarcraft.local_library.entities.bossbar.server.CustomBossEventInitPacket;
import com.finderfeed.solarcraft.local_library.entities.bossbar.server.ServerBossEventUpdateProgress;
import com.finderfeed.solarcraft.packet_handler.packets.*;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.OpenScreenPacket;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.UpdateAllProgressionOnClient;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.UpdateInventoryPacket;
import com.finderfeed.solarcraft.content.items.solar_lexicon.packets.UpdateProgressionOnClient;
import com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle.OpenPuzzleScreenPacket;
import com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle.PuzzleActionPacket;
import com.finderfeed.solarcraft.packet_handler.packets.misc_packets.BallLightningSpawnLightningParticles;
import com.finderfeed.solarcraft.packet_handler.packets.misc_packets.ExplosionParticlesPacket;
import com.finderfeed.solarcraft.packet_handler.packets.misc_packets.SolarStrikeEntityDoExplosion;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzleOpenScreen;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzlePutTilePacket;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzleTakeTilePacket;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SCPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("solarcraft", "main"),
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
        INSTANCE.registerMessage(nextID(), AbilityIndexSetPacket.class, AbilityIndexSetPacket::toBytes, AbilityIndexSetPacket::new, AbilityIndexSetPacket::handle);
        INSTANCE.registerMessage(nextID(), ToggleAlchemistPacket.class, ToggleAlchemistPacket::toBytes, ToggleAlchemistPacket::new, ToggleAlchemistPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateProgressOnClientPacket.class, UpdateProgressOnClientPacket::toBytes, UpdateProgressOnClientPacket::new, UpdateProgressOnClientPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateStacksOnClientPacketPool.class, UpdateStacksOnClientPacketPool::toBytes, UpdateStacksOnClientPacketPool::new, UpdateStacksOnClientPacketPool::handle);
        INSTANCE.registerMessage(nextID(), UpdateStacksOnClientTable.class, UpdateStacksOnClientTable::toBytes, UpdateStacksOnClientTable::new, UpdateStacksOnClientTable::handle);
        INSTANCE.registerMessage(nextID(), UpdateProgressionOnClient.class, UpdateProgressionOnClient::toBytes, UpdateProgressionOnClient::new, UpdateProgressionOnClient::handle);
        INSTANCE.registerMessage(nextID(), TriggerToastPacket.class, TriggerToastPacket::toBytes, TriggerToastPacket::new, TriggerToastPacket::handle);
        INSTANCE.registerMessage(nextID(), OpenScreenPacket.class, OpenScreenPacket::toBytes, OpenScreenPacket::new, OpenScreenPacket::handle);
        INSTANCE.registerMessage(nextID(), SpawnHealParticles.class, SpawnHealParticles::toBytes, SpawnHealParticles::new, SpawnHealParticles::handle);
        INSTANCE.registerMessage(nextID(), ProcImmortalityTotemAnimation.class, ProcImmortalityTotemAnimation::toBytes, ProcImmortalityTotemAnimation::new, ProcImmortalityTotemAnimation::handle);
        INSTANCE.registerMessage(nextID(), PlaySoundPacket.class, PlaySoundPacket::toBytes, PlaySoundPacket::new, PlaySoundPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateAllProgressionOnClient.class, UpdateAllProgressionOnClient::toBytes, UpdateAllProgressionOnClient::new, UpdateAllProgressionOnClient::handle);
        INSTANCE.registerMessage(nextID(), UpdateLaserTrapTile.class, UpdateLaserTrapTile::toBytes, UpdateLaserTrapTile::new, UpdateLaserTrapTile::handle);
        INSTANCE.registerMessage(nextID(), ReloadChunks.class, ReloadChunks::toBytes, ReloadChunks::new, ReloadChunks::handle);
        INSTANCE.registerMessage(nextID(), UpdateInventoryPacket.class, UpdateInventoryPacket::toBytes, UpdateInventoryPacket::new, UpdateInventoryPacket::handle);
        INSTANCE.registerMessage(nextID(), RunicTablePacket.class, RunicTablePacket::toBytes, RunicTablePacket::new, RunicTablePacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateTypeOnClientPacket.class, UpdateTypeOnClientPacket::toBytes, UpdateTypeOnClientPacket::new, UpdateTypeOnClientPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateEnergyOnClientPacket.class, UpdateEnergyOnClientPacket::toBytes, UpdateEnergyOnClientPacket::new, UpdateEnergyOnClientPacket::handle);
        INSTANCE.registerMessage(nextID(), TriggerProgressionShaderPacket.class, TriggerProgressionShaderPacket::toBytes, TriggerProgressionShaderPacket::new, TriggerProgressionShaderPacket::handle);
        INSTANCE.registerMessage(nextID(), BuyAbilityPacket.class, BuyAbilityPacket::toBytes, BuyAbilityPacket::new, BuyAbilityPacket::handle);
        INSTANCE.registerMessage(nextID(), TakeEnergyFromForgePacket.class, TakeEnergyFromForgePacket::toBytes, TakeEnergyFromForgePacket::new, TakeEnergyFromForgePacket::handle);
        INSTANCE.registerMessage(nextID(), SetSpeedPacket.class, SetSpeedPacket::toBytes, SetSpeedPacket::new, SetSpeedPacket::handle);
        INSTANCE.registerMessage(nextID(), TriggerEnergyTypeToast.class, TriggerEnergyTypeToast::toBytes, TriggerEnergyTypeToast::new, TriggerEnergyTypeToast::handle);
        INSTANCE.registerMessage(nextID(), SolarStrikeEntityDoExplosion.class, SolarStrikeEntityDoExplosion::toBytes, SolarStrikeEntityDoExplosion::new, SolarStrikeEntityDoExplosion::handle);
        INSTANCE.registerMessage(nextID(), UpdateFragmentsOnClient.class, UpdateFragmentsOnClient::toBytes, UpdateFragmentsOnClient::new, UpdateFragmentsOnClient::handle);
        INSTANCE.registerMessage(nextID(), UpdateRunePattern.class, UpdateRunePattern::toBytes, UpdateRunePattern::new, UpdateRunePattern::handle);
        INSTANCE.registerMessage(nextID(), BallLightningSpawnLightningParticles.class, BallLightningSpawnLightningParticles::toBytes, BallLightningSpawnLightningParticles::new, BallLightningSpawnLightningParticles::handle);
        INSTANCE.registerMessage(nextID(), SendFragmentsToClientPacket.class, SendFragmentsToClientPacket::toBytes, SendFragmentsToClientPacket::new, SendFragmentsToClientPacket::handle);
        INSTANCE.registerMessage(nextID(), EnchanterPacket.class, EnchanterPacket::toBytes, EnchanterPacket::new, EnchanterPacket::handle);
        INSTANCE.registerMessage(nextID(), ExplosionParticlesPacket.class, ExplosionParticlesPacket::toBytes, ExplosionParticlesPacket::new, ExplosionParticlesPacket::handle);
        INSTANCE.registerMessage(nextID(), TeleportEntityPacket.class, TeleportEntityPacket::toBytes, TeleportEntityPacket::new, TeleportEntityPacket::handle);
        INSTANCE.registerMessage(nextID(), DisablePlayerFlightPacket.class, DisablePlayerFlightPacket::toBytes, DisablePlayerFlightPacket::new, DisablePlayerFlightPacket::handle);
        INSTANCE.registerMessage(nextID(), ShadowBoltExplosionPacket.class, ShadowBoltExplosionPacket::toBytes, ShadowBoltExplosionPacket::new, ShadowBoltExplosionPacket::handle);
        INSTANCE.registerMessage(nextID(), ToggleableAbilityPacket.class, ToggleableAbilityPacket::toBytes, ToggleableAbilityPacket::new, ToggleableAbilityPacket::handle);
        INSTANCE.registerMessage(nextID(), OpenPuzzleScreenPacket.class, OpenPuzzleScreenPacket::toBytes, OpenPuzzleScreenPacket::new, OpenPuzzleScreenPacket::handle);
        INSTANCE.registerMessage(nextID(), PuzzleActionPacket.class, PuzzleActionPacket::toBytes, PuzzleActionPacket::new, PuzzleActionPacket::handle);
        INSTANCE.registerMessage(nextID(), SetClientRadiantLandStatePacket.class, SetClientRadiantLandStatePacket::toBytes, SetClientRadiantLandStatePacket::new, SetClientRadiantLandStatePacket::handle);
        INSTANCE.registerMessage(nextID(), RequestAbilityScreenPacket.class, RequestAbilityScreenPacket::toBytes, RequestAbilityScreenPacket::new, RequestAbilityScreenPacket::handle);
        INSTANCE.registerMessage(nextID(), OpenAbilityScreenPacket.class, OpenAbilityScreenPacket::toBytes, OpenAbilityScreenPacket::new, OpenAbilityScreenPacket::handle);
        INSTANCE.registerMessage(nextID(), DimensionBreakPacket.class, DimensionBreakPacket::toBytes, DimensionBreakPacket::new, DimensionBreakPacket::handle);
        INSTANCE.registerMessage(nextID(), RetainFragmentPacket.class, RetainFragmentPacket::toBytes, RetainFragmentPacket::new, RetainFragmentPacket::handle);
        INSTANCE.registerMessage(nextID(), CustomBossEventInitPacket.class, CustomBossEventInitPacket::toBytes, CustomBossEventInitPacket::new, CustomBossEventInitPacket::handle);
        INSTANCE.registerMessage(nextID(), ServerBossEventUpdateProgress.class, ServerBossEventUpdateProgress::toBytes, ServerBossEventUpdateProgress::new, ServerBossEventUpdateProgress::handle);
        INSTANCE.registerMessage(nextID(), CastWandActionPacket.class, CastWandActionPacket::toBytes, CastWandActionPacket::new, CastWandActionPacket::handle);
        INSTANCE.registerMessage(nextID(), SetREDrainTypePacket.class, SetREDrainTypePacket::toBytes, SetREDrainTypePacket::new, SetREDrainTypePacket::handle);
        INSTANCE.registerMessage(nextID(), SunShardPuzzleOpenScreen.class, SunShardPuzzleOpenScreen::toBytes, SunShardPuzzleOpenScreen::new, SunShardPuzzleOpenScreen::handle);
        INSTANCE.registerMessage(nextID(), SunShardPuzzlePutTilePacket.class, SunShardPuzzlePutTilePacket::toBytes, SunShardPuzzlePutTilePacket::new, SunShardPuzzlePutTilePacket::handle);
        INSTANCE.registerMessage(nextID(), SunShardPuzzleTakeTilePacket.class, SunShardPuzzleTakeTilePacket::toBytes, SunShardPuzzleTakeTilePacket::new, SunShardPuzzleTakeTilePacket::handle);
        INSTANCE.registerMessage(nextID(), BlockPlacePacket.class, BlockPlacePacket::toBytes, BlockPlacePacket::new, BlockPlacePacket::handle);
        INSTANCE.registerMessage(nextID(), BlockBreakPacket.class, BlockBreakPacket::toBytes, BlockBreakPacket::new, BlockBreakPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateItemTagInItemEntityPacket.class, UpdateItemTagInItemEntityPacket::toBytes, UpdateItemTagInItemEntityPacket::new, UpdateItemTagInItemEntityPacket::handle);
        INSTANCE.registerMessage(nextID(), WandStructureActionPacket.class, WandStructureActionPacket::toBytes, WandStructureActionPacket::new, WandStructureActionPacket::handle);
        INSTANCE.registerMessage(nextID(), SendConfigsToClientPacket.class, SendConfigsToClientPacket::toBytes, SendConfigsToClientPacket::new, SendConfigsToClientPacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateRunicEnergyInContainerPacket.class, UpdateRunicEnergyInContainerPacket::toBytes, UpdateRunicEnergyInContainerPacket::new, UpdateRunicEnergyInContainerPacket::handle);
        INSTANCE.registerMessage(nextID(), FlashPacket.class, FlashPacket::toBytes, FlashPacket::new, FlashPacket::handle);
        INSTANCE.registerMessage(nextID(), CameraShakePacket.class, CameraShakePacket::toBytes, CameraShakePacket::new, CameraShakePacket::handle);
        INSTANCE.registerMessage(nextID(), UpdateChunkPacket.class, UpdateChunkPacket::toBytes, UpdateChunkPacket::new, UpdateChunkPacket::handle);
        INSTANCE.registerMessage(nextID(), LaunchNuclearMissilePacket.class, LaunchNuclearMissilePacket::toBytes, LaunchNuclearMissilePacket::new, LaunchNuclearMissilePacket::handle);


    }
//            INSTANCE.registerMessage(nextID(), .class, ::toBytes, ::new, ::handle);
    //RepeaterParentUpdateOnClient
}
