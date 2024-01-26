package com.finderfeed.solarcraft.packet_handler;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import com.finderfeed.solarcraft.packet_handler.packet_system.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handling.ConfigurationPayloadContext;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;


public class SCPacketHandler {

    //Note for myself - to create a packet
    /*
    1) Annotate the packet with @Packet, provide id and PacketType (default is PLAY)
    2) Extend FDPacket
     */
    @SubscribeEvent
    public static void registerPayload(RegisterPayloadHandlerEvent event){
        final IPayloadRegistrar registrar = event.registrar(SolarCraft.MOD_ID)
                .versioned("1")
                .optional();
        List<Class<?>> packets = Helpers.getAnnotatedClasses(Packet.class);
        for (Class<?> packet : packets){
            Packet a = packet.getAnnotation(Packet.class);
            ResourceLocation id = new ResourceLocation(SolarCraft.MOD_ID,a.value());
            if (a.type() == PacketType.PLAY){
                registerPlayPacket(registrar,id,packet);
            }else if (a.type() == PacketType.CONFIGURATION){
                registerConfigurationPacket(registrar,id,packet);
            }else{
                SolarCraft.LOGGER.error("Don't know the type of packet: " + id);
            }
        }
    }
    public static <T extends CustomPacketPayload> void registerPlayPacket(IPayloadRegistrar register, ResourceLocation id, Class<?> packetClass){
        try {
            Constructor<?> constructor = packetClass.getConstructor(FriendlyByteBuf.class);
            //Method method = packetClass.getDeclaredMethod("handle",packetClass, PlayPayloadContext.class);
            register.play(id, (buf) -> {
                T object;
                try {
                    object = (T) constructor.newInstance(buf);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return object;
            },(packetObject,context)->{
//                try {
//                    method.invoke(packetObject,context);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
                FDPacket.handlePlayPacket(packetObject,context);
            });
        }catch (Exception e){
            throw new RuntimeException("Error while loading " + id + " packet",e);
        }
    }

    public static <T extends CustomPacketPayload> void registerConfigurationPacket(IPayloadRegistrar register, ResourceLocation id, Class<?> packetClass){
        try {
            Constructor<?> constructor = packetClass.getConstructor(FriendlyByteBuf.class);
//            Method method = packetClass.getDeclaredMethod("handle",packetClass, ConfigurationPayloadContext.class);
            register.configuration(id, (buf) -> {
                T object;
                try {
                    object = (T) constructor.newInstance(buf);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return object;
            },(packetObject,context)->{
//                try {
//                    method.invoke(packetObject,context);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
                FDPacket.handleConfigurationPacket(packetObject,context);
            });
        }catch (Exception e){
            throw new RuntimeException("Error while loading " + id + " packet",e);
        }
    }

//    private static final String PROTOCOL_VERSION = "1";
//    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
//            new ResourceLocation("solarcraft", "main"),
//            () -> PROTOCOL_VERSION,
//            PROTOCOL_VERSION::equals,
//            PROTOCOL_VERSION::equals
//    );
//    private static int ID = 0;
//    public static int nextID() {
//        return ID++;
//    }
//
//    public static void registerMessages(){
//        INSTANCE.registerMessage(nextID(), CastAbilityPacket.class, CastAbilityPacket::toBytes, CastAbilityPacket::new, CastAbilityPacket::handle);
//        INSTANCE.registerMessage(nextID(), AbilityIndexSetPacket.class, AbilityIndexSetPacket::toBytes, AbilityIndexSetPacket::new, AbilityIndexSetPacket::handle);
//        INSTANCE.registerMessage(nextID(), ToggleAlchemistPacket.class, ToggleAlchemistPacket::toBytes, ToggleAlchemistPacket::new, ToggleAlchemistPacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateProgressOnClientPacket.class, UpdateProgressOnClientPacket::toBytes, UpdateProgressOnClientPacket::new, UpdateProgressOnClientPacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateStacksOnClientPacketPool.class, UpdateStacksOnClientPacketPool::toBytes, UpdateStacksOnClientPacketPool::new, UpdateStacksOnClientPacketPool::handle);
//        INSTANCE.registerMessage(nextID(), UpdateStacksOnClientTable.class, UpdateStacksOnClientTable::toBytes, UpdateStacksOnClientTable::new, UpdateStacksOnClientTable::handle);
//        INSTANCE.registerMessage(nextID(), UpdateProgressionsOnClient.class, UpdateProgressionsOnClient::toBytes, UpdateProgressionsOnClient::new, UpdateProgressionsOnClient::handle);
//        INSTANCE.registerMessage(nextID(), TriggerToastPacket.class, TriggerToastPacket::toBytes, TriggerToastPacket::new, TriggerToastPacket::handle);
//        INSTANCE.registerMessage(nextID(), OpenScreenPacket.class, OpenScreenPacket::toBytes, OpenScreenPacket::new, OpenScreenPacket::handle);
//        INSTANCE.registerMessage(nextID(), ProcImmortalityTotemAnimation.class, ProcImmortalityTotemAnimation::toBytes, ProcImmortalityTotemAnimation::new, ProcImmortalityTotemAnimation::handle);
//        INSTANCE.registerMessage(nextID(), PlaySoundPacket.class, PlaySoundPacket::toBytes, PlaySoundPacket::new, PlaySoundPacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateLaserTrapTile.class, UpdateLaserTrapTile::toBytes, UpdateLaserTrapTile::new, UpdateLaserTrapTile::handle);
//        INSTANCE.registerMessage(nextID(), ReloadChunks.class, ReloadChunks::toBytes, ReloadChunks::new, ReloadChunks::handle);
//        INSTANCE.registerMessage(nextID(), UpdateInventoryPacket.class, UpdateInventoryPacket::toBytes, UpdateInventoryPacket::new, UpdateInventoryPacket::handle);
//        INSTANCE.registerMessage(nextID(), RunicTablePacket.class, RunicTablePacket::toBytes, RunicTablePacket::new, RunicTablePacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateTypeOnClientPacket.class, UpdateTypeOnClientPacket::toBytes, UpdateTypeOnClientPacket::new, UpdateTypeOnClientPacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateEnergyOnClientPacket.class, UpdateEnergyOnClientPacket::toBytes, UpdateEnergyOnClientPacket::new, UpdateEnergyOnClientPacket::handle);
//        INSTANCE.registerMessage(nextID(), TriggerProgressionShaderPacket.class, TriggerProgressionShaderPacket::toBytes, TriggerProgressionShaderPacket::new, TriggerProgressionShaderPacket::handle);
//        INSTANCE.registerMessage(nextID(), BuyAbilityPacket.class, BuyAbilityPacket::toBytes, BuyAbilityPacket::new, BuyAbilityPacket::handle);
//        INSTANCE.registerMessage(nextID(), TakeEnergyFromForgePacket.class, TakeEnergyFromForgePacket::toBytes, TakeEnergyFromForgePacket::new, TakeEnergyFromForgePacket::handle);
//        INSTANCE.registerMessage(nextID(), SetSpeedPacket.class, SetSpeedPacket::toBytes, SetSpeedPacket::new, SetSpeedPacket::handle);
//        INSTANCE.registerMessage(nextID(), TriggerEnergyTypeToast.class, TriggerEnergyTypeToast::toBytes, TriggerEnergyTypeToast::new, TriggerEnergyTypeToast::handle);
//        INSTANCE.registerMessage(nextID(), SolarStrikeEntityDoExplosion.class, SolarStrikeEntityDoExplosion::toBytes, SolarStrikeEntityDoExplosion::new, SolarStrikeEntityDoExplosion::handle);
//        INSTANCE.registerMessage(nextID(), UpdateFragmentsOnClient.class, UpdateFragmentsOnClient::toBytes, UpdateFragmentsOnClient::new, UpdateFragmentsOnClient::handle);
//        INSTANCE.registerMessage(nextID(), UpdateRunePattern.class, UpdateRunePattern::toBytes, UpdateRunePattern::new, UpdateRunePattern::handle);
//        INSTANCE.registerMessage(nextID(), BallLightningSpawnLightningParticles.class, BallLightningSpawnLightningParticles::toBytes, BallLightningSpawnLightningParticles::new, BallLightningSpawnLightningParticles::handle);
//        INSTANCE.registerMessage(nextID(), SendFragmentsToClientPacket.class, SendFragmentsToClientPacket::toBytes, SendFragmentsToClientPacket::new, SendFragmentsToClientPacket::handle);
//        INSTANCE.registerMessage(nextID(), EnchanterPacket.class, EnchanterPacket::toBytes, EnchanterPacket::new, EnchanterPacket::handle);
//        INSTANCE.registerMessage(nextID(), ExplosionParticlesPacket.class, ExplosionParticlesPacket::toBytes, ExplosionParticlesPacket::new, ExplosionParticlesPacket::handle);
//        INSTANCE.registerMessage(nextID(), TeleportEntityPacket.class, TeleportEntityPacket::toBytes, TeleportEntityPacket::new, TeleportEntityPacket::handle);
//        INSTANCE.registerMessage(nextID(), DisablePlayerFlightPacket.class, DisablePlayerFlightPacket::toBytes, DisablePlayerFlightPacket::new, DisablePlayerFlightPacket::handle);
//        INSTANCE.registerMessage(nextID(), ShadowBoltExplosionPacket.class, ShadowBoltExplosionPacket::toBytes, ShadowBoltExplosionPacket::new, ShadowBoltExplosionPacket::handle);
//        INSTANCE.registerMessage(nextID(), ToggleableAbilityPacket.class, ToggleableAbilityPacket::toBytes, ToggleableAbilityPacket::new, ToggleableAbilityPacket::handle);
//        INSTANCE.registerMessage(nextID(), OpenPuzzleScreenPacket.class, OpenPuzzleScreenPacket::toBytes, OpenPuzzleScreenPacket::new, OpenPuzzleScreenPacket::handle);
//        INSTANCE.registerMessage(nextID(), PuzzleActionPacket.class, PuzzleActionPacket::toBytes, PuzzleActionPacket::new, PuzzleActionPacket::handle);
//        INSTANCE.registerMessage(nextID(), SetClientRadiantLandStatePacket.class, SetClientRadiantLandStatePacket::toBytes, SetClientRadiantLandStatePacket::new, SetClientRadiantLandStatePacket::handle);
//        INSTANCE.registerMessage(nextID(), RequestAbilityScreenPacket.class, RequestAbilityScreenPacket::toBytes, RequestAbilityScreenPacket::new, RequestAbilityScreenPacket::handle);
//        INSTANCE.registerMessage(nextID(), OpenAbilityScreenPacket.class, OpenAbilityScreenPacket::toBytes, OpenAbilityScreenPacket::new, OpenAbilityScreenPacket::handle);
//        INSTANCE.registerMessage(nextID(), DimensionBreakPacket.class, DimensionBreakPacket::toBytes, DimensionBreakPacket::new, DimensionBreakPacket::handle);
//        INSTANCE.registerMessage(nextID(), RetainFragmentPacket.class, RetainFragmentPacket::toBytes, RetainFragmentPacket::new, RetainFragmentPacket::handle);
//        INSTANCE.registerMessage(nextID(), CustomBossEventInitPacket.class, CustomBossEventInitPacket::toBytes, CustomBossEventInitPacket::new, CustomBossEventInitPacket::handle);
//        INSTANCE.registerMessage(nextID(), ServerBossEventUpdateProgress.class, ServerBossEventUpdateProgress::toBytes, ServerBossEventUpdateProgress::new, ServerBossEventUpdateProgress::handle);
//        INSTANCE.registerMessage(nextID(), CastWandActionPacket.class, CastWandActionPacket::toBytes, CastWandActionPacket::new, CastWandActionPacket::handle);
//        INSTANCE.registerMessage(nextID(), SetREDrainTypePacket.class, SetREDrainTypePacket::toBytes, SetREDrainTypePacket::new, SetREDrainTypePacket::handle);
//        INSTANCE.registerMessage(nextID(), SunShardPuzzleOpenScreen.class, SunShardPuzzleOpenScreen::toBytes, SunShardPuzzleOpenScreen::new, SunShardPuzzleOpenScreen::handle);
//        INSTANCE.registerMessage(nextID(), SunShardPuzzlePutTilePacket.class, SunShardPuzzlePutTilePacket::toBytes, SunShardPuzzlePutTilePacket::new, SunShardPuzzlePutTilePacket::handle);
//        INSTANCE.registerMessage(nextID(), SunShardPuzzleTakeTilePacket.class, SunShardPuzzleTakeTilePacket::toBytes, SunShardPuzzleTakeTilePacket::new, SunShardPuzzleTakeTilePacket::handle);
//        INSTANCE.registerMessage(nextID(), BlockPlacePacket.class, BlockPlacePacket::toBytes, BlockPlacePacket::new, BlockPlacePacket::handle);
//        INSTANCE.registerMessage(nextID(), BlockBreakPacket.class, BlockBreakPacket::toBytes, BlockBreakPacket::new, BlockBreakPacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateItemTagInItemEntityPacket.class, UpdateItemTagInItemEntityPacket::toBytes, UpdateItemTagInItemEntityPacket::new, UpdateItemTagInItemEntityPacket::handle);
//        INSTANCE.registerMessage(nextID(), WandStructureActionPacket.class, WandStructureActionPacket::toBytes, WandStructureActionPacket::new, WandStructureActionPacket::handle);
//        INSTANCE.registerMessage(nextID(), SendConfigsToClientPacket.class, SendConfigsToClientPacket::toBytes, SendConfigsToClientPacket::new, SendConfigsToClientPacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateRunicEnergyInContainerPacket.class, UpdateRunicEnergyInContainerPacket::toBytes, UpdateRunicEnergyInContainerPacket::new, UpdateRunicEnergyInContainerPacket::handle);
//        INSTANCE.registerMessage(nextID(), FlashPacket.class, FlashPacket::toBytes, FlashPacket::new, FlashPacket::handle);
//        INSTANCE.registerMessage(nextID(), CameraShakePacket.class, CameraShakePacket::toBytes, CameraShakePacket::new, CameraShakePacket::handle);
//        INSTANCE.registerMessage(nextID(), UpdateChunkPacket.class, UpdateChunkPacket::toBytes, UpdateChunkPacket::new, UpdateChunkPacket::handle);
//        INSTANCE.registerMessage(nextID(), LaunchOrbitalMissilePacket.class, LaunchOrbitalMissilePacket::toBytes, LaunchOrbitalMissilePacket::new, LaunchOrbitalMissilePacket::handle);
//        INSTANCE.registerMessage(nextID(), AnimationsSyncPacket.class, AnimationsSyncPacket::toBytes, AnimationsSyncPacket::new, AnimationsSyncPacket::handle);
//        INSTANCE.registerMessage(nextID(), StartEntityAnimationPacket.class, StartEntityAnimationPacket::toBytes, StartEntityAnimationPacket::new, StartEntityAnimationPacket::handle);
//        INSTANCE.registerMessage(nextID(), RemoveEntityAnimationPacket.class, RemoveEntityAnimationPacket::toBytes, RemoveEntityAnimationPacket::new, RemoveEntityAnimationPacket::handle);
//        INSTANCE.registerMessage(nextID(), SendShapeParticlesPacket.class, SendShapeParticlesPacket::toBytes, SendShapeParticlesPacket::new, SendShapeParticlesPacket::handle);
//
//
//    }
//            INSTANCE.registerMessage(nextID(), .class, ::toBytes, ::new, ::handle);
    //RepeaterParentUpdateOnClient
}
