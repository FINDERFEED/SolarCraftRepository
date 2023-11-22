package com.finderfeed.solarcraft.packet_handler;

import com.finderfeed.solarcraft.client.particles.server_data.shapes.ParticleSpawnShape;
import com.finderfeed.solarcraft.config.JsonConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.runic_energy.AbstractRunicEnergyContainer;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client.SunShardPuzzleScreen;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexicon;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.items.solar_lexicon.screen.SolarLexiconScreen;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragmentHelper;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.RETypeSelectionScreen;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.StructureSelectionScreen;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.AnimatedObject;
import com.finderfeed.solarcraft.local_library.bedrock_loader.animations.manager.AnimationTicker;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.ActiveBossBar;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.overlays.SolarcraftOverlays;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class ClientPacketHandles {


    public static void handleSpawnShapeParticlesPacket(ParticleSpawnShape spawnShape, ParticleOptions options,double x,double y,double z,double xd,double yd,double zd){
        spawnShape.placeParticles(ClientHelpers.getLevel(),options, x,y,z,xd,yd,zd);
    }

    public static void handleUpdateChunkPacket(int x, int z, ClientboundLevelChunkPacketData data){
        Minecraft.getInstance().level.getChunkSource().replaceWithPacketData(
                x,z,data.getReadBuffer(),data.getHeightmaps(),data.getBlockEntitiesTagsConsumer(x,z)
        );


    }
    public static void handleFlashPacket(int inTime,int stayTime,int outTime){
        ClientHelpers.flash(inTime,stayTime,outTime);
    }
    public static void handleCameraShakePacket(int inTime,int stayTime,int outTime,float spread){
        ClientHelpers.shake(inTime,stayTime,outTime,spread);
    }

    public static void handleDimBreakPacket(){
        ClientHelpers.playsoundInEars(SolarcraftSounds.DIMENSION_BREAK.get(),1f,0.5f);
        ClientHelpers.flash(100,40,40);
        ClientHelpers.shake(50,50,20,0.5f);
    }

    public static void handleServerBossInitPacket(UUID uuid, Component name, String rendererId, boolean remove, int entityId){
        if (!remove) {
            SolarcraftOverlays.BossBars.addBossBar(new ActiveBossBar(uuid, name, 1f, rendererId,entityId));
        }else{
            SolarcraftOverlays.BossBars.removeBossBar(uuid);
        }
    }

    public static void handleServerBossEventUpdateProgressPacket(UUID id,float progress){
        ActiveBossBar bar = SolarcraftOverlays.BossBars.getBossBar(id);
        if (bar != null){
            bar.setProgress(progress);
        }
    }

    public static void handleClientREDrainWandAction(){
        Minecraft.getInstance().setScreen(new RETypeSelectionScreen());
    }

    public static void handleSunShardOpenScreenPacket(Puzzle puzzle, BlockPos tilepos){
        Minecraft.getInstance().setScreen(new SunShardPuzzleScreen(puzzle,tilepos));
    }

    public static void handleUpdateItemEntityPacket(int entityId, CompoundTag tag){
        Level world = ClientHelpers.getLevel();
        if (world.getEntity(entityId) instanceof ItemEntity item){
            item.getItem().setTag(tag);
        }
    }

    public static void handleStructureWandAction(BlockPos clickedPos, MultiblockStructure structure){
        StructureSelectionScreen.processStructure(clickedPos,structure,3);
    }

    public static void handleWandStructureActionPacket(BlockPos clickedPos, List<String> structIds, CompoundTag fragments){
        boolean openScreen = false;
        for (String fragId : fragments.getAllKeys()){
            AncientFragment fragment = AncientFragment.getFragmentByID(fragId);
            if (!structIds.contains(fragment.getStructure().getId())){
                continue;
            }
            if (fragments.getBoolean(fragId)){
                AncientFragmentHelper.givePlayerFragment(fragment,ClientHelpers.getClientPlayer());
                openScreen = true;
            }else{
                AncientFragmentHelper.revokePlayerFragment(fragment,ClientHelpers.getClientPlayer());
            }
        }
        if (openScreen) {
            List<MultiblockStructure> structures = structIds.stream().map(Multiblocks.STRUCTURES::get).toList();
            if (structures.size() == 1){
                handleStructureWandAction(clickedPos,structures.get(0));
            }else {
                Minecraft.getInstance().setScreen(new StructureSelectionScreen(clickedPos, structures));
            }
        }else{
            ClientHelpers.getClientPlayer().sendSystemMessage(Component.literal("You don't have structure fragment(s) for this structure!"));
        }
    }

    public static void handleClientConfigsPacket(List<String> strs){
        for (int i = 0; i < strs.size();i += 2){
            String id = strs.get(i);
            String json = strs.get(i + 1);
            JsonObject object = JsonParser.parseString(json).getAsJsonObject();
            JsonConfig config;
            if ((config = ConfigRegistry.EARLY_LOAD_CONFIGS.get(id)) != null){
                config.deserialize(object);
            }else if ((config = ConfigRegistry.POST_LOAD_CONFIGS.get(id)) != null){
                config.deserialize(object);
            }
        }
    }

    public static void updateContainerRunicEnergy(BlockPos pos,CompoundTag tag){
        Level world = ClientHelpers.getLevel();
        if (world.getBlockEntity(pos) instanceof AbstractRunicEnergyContainer container){
            container.loadRunicEnergy(tag);
        }
    }

    public static void handleProgressionUpdate(CompoundTag progressionData){
        Player player = Minecraft.getInstance().player;
        for (String key : progressionData.getAllKeys()){
            Progression progression = Progression.getProgressionByName(key);
            Helpers.setProgressionCompletionStatus(progression,player,progressionData.getBoolean(key));
        }
    }

    public static void handleSetEntityAnimationPacket(int entityId, String tickerName, AnimationTicker ticker){
        ClientLevel level = ClientHelpers.getLevel();
        if (level.getEntity(entityId) instanceof AnimatedObject animatable){
            animatable.getAnimationManager().setAnimation(tickerName,ticker);
        }
    }

    public static void handleRemoveEntityAnimationPacket(int entityId, String tickerName){
        ClientLevel level = ClientHelpers.getLevel();
        if (level.getEntity(entityId) instanceof AnimatedObject animatable){
            animatable.getAnimationManager().stopAnimation(tickerName);
        }
    }

    public static void openLexiconScreen(){
        Minecraft mc = Minecraft.getInstance();
        ItemStack stack = mc.player.getMainHandItem();
        if (stack.getItem() instanceof SolarLexicon){
            SolarLexicon lexicon = (SolarLexicon) stack.getItem();
            if (lexicon.currentSavedScreen == null){
                mc.setScreen(new SolarLexiconScreen());
            }else{
                mc.setScreen(lexicon.currentSavedScreen);
                lexicon.currentSavedScreen = null;
            }

        }

    }

}
