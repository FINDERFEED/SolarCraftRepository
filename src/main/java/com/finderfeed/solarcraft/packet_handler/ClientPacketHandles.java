package com.finderfeed.solarcraft.packet_handler;

import com.finderfeed.solarcraft.config.JsonConfig;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.client.SunShardPuzzleScreen;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.ProgressionHelper;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.drain_runic_enenrgy_action.RETypeSelectionScreen;
import com.finderfeed.solarcraft.content.items.solar_wand.wand_actions.structure_check.StructureSelectionScreen;
import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.helpers.multiblock.MultiblockStructure;
import com.finderfeed.solarcraft.helpers.multiblock.Multiblocks;
import com.finderfeed.solarcraft.local_library.entities.bossbar.client.ActiveBossBar;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.overlays.SolarcraftOverlays;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.UUID;

public class ClientPacketHandles {
    public static void handleDimBreakPacket(){
        ClientHelpers.playsoundInEars(SolarcraftSounds.DIMENSION_BREAK.get(),1f,0.5f);
        ClientHelpers.flash(100,40,40);
        ClientHelpers.shake(50,50,20,7.5f);
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
                ProgressionHelper.givePlayerFragment(fragment,ClientHelpers.getClientPlayer());
                openScreen = true;
            }else{
                ProgressionHelper.revokePlayerFragment(fragment,ClientHelpers.getClientPlayer());
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
}
