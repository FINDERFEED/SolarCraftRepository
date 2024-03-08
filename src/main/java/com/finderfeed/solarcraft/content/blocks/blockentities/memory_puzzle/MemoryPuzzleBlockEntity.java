package com.finderfeed.solarcraft.content.blocks.blockentities.memory_puzzle;

import com.finderfeed.solarcraft.client.particles.ball_particle.BallParticleOptions;
import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.helpers.CompoundNBTHelper;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacketUtil;
import com.finderfeed.solarcraft.packet_handler.packets.CloseClientScreenPacket;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemoryPuzzleBlockEntity extends SolarcraftBlockEntity {
    private MemoryPuzzle puzzle;
    private List<UUID> used = new ArrayList<>();
    private boolean canOpenScreen = true;
    private int ticker = -1;

    public MemoryPuzzleBlockEntity(BlockPos pos, BlockState state) {
        super(SCTileEntities.MEMORY_PUZZLE.get(), pos, state);
    }


    public static void tick(MemoryPuzzleBlockEntity tile, Level level){
        if (!level.isClientSide){
            tile.processCompletedPuzzle();
        }
    }



    private void processCompletedPuzzle(){
        if (ticker > 0){
            Vec3 center = Helpers.getBlockCenter(this.getBlockPos().above());
            ((ServerLevel)level).sendParticles(
                    new BallParticleOptions(0.25f,255,255,0,20,true,false),
                    center.x,center.y,center.z,10,0.25,0.25,0.25,0
            );
            ticker--;
        }else if (ticker == 0){
            ticker--;
            Vec3 center = Helpers.getBlockCenter(this.getBlockPos().above());
            ItemEntity entity = new ItemEntity(level,center.x,center.y,center.z, SCItems.COLD_STAR_PIECE.get().getDefaultInstance());
            entity.setDeltaMovement(0,0,0);
            level.addFreshEntity(entity);
        }else{
            canOpenScreen = true;
            ticker = -1;
        }
    }

    public void pushValue(ServerPlayer player, int value){
        AtomicBoolean stageCompleted = new AtomicBoolean();
        if (!puzzle.solve(value,false,stageCompleted)){
            puzzle.initiatePuzzle(false);
            MemoryPuzzleUpdatePacket packet = new MemoryPuzzleUpdatePacket(value,puzzle.getValues(),false);
            FDPacketUtil.sendToPlayer(player,packet);
        }else{
            CustomPacketPayload payload;
            if (!stageCompleted.get()) {
                payload = new MemoryPuzzleUpdatePacket(value, null, true);
            }else{
                if (puzzle.isCompleted()){
                    Inventory inv = player.getInventory();
                    int id = inv.findSlotMatchingItem(SCItems.SOLAR_KEY.get().getDefaultInstance());
                    if (id != -1) {
                        inv.setItem(id, ItemStack.EMPTY);
                        used.add(player.getUUID());
                        ticker = 50;
                        canOpenScreen = false;
                        puzzle.initiatePuzzle(true);
                    }else{
                        player.sendSystemMessage(Component.literal("No Solar Key was found in inventory."));
                    }
                    payload = new CloseClientScreenPacket();
                }else {
                    puzzle.initiatePuzzle(false);
                    payload = new MemoryPuzzleUpdatePacket(value, puzzle.getValues(), true);
                }
            }
            FDPacketUtil.sendToPlayer(player,payload);
        }
        this.setChanged();
    }

    public void onUse(ServerPlayer player){
        if (canOpenScreen && !used.contains(player.getUUID())) {
            if (player.getMainHandItem().is(SCItems.SOLAR_KEY)) {
                if (puzzle == null) {
                    puzzle = new MemoryPuzzle(8, 5, 2);
                    puzzle.initiatePuzzle(false);
                } else {
                    puzzle.initiatePuzzle(false);
                }
                this.setChanged();
                MemoryPuzzleOpenScreenPacket packet = new MemoryPuzzleOpenScreenPacket(this.getBlockPos(), puzzle.getValues());
                FDPacketUtil.sendToPlayer(player, packet);
            }else{
                player.sendSystemMessage(Component.translatable("solarcraft.solar_key_required"));
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("ticker",ticker);
        CompoundNBTHelper.saveUUIDList(tag,used,"uuids");
        if (puzzle != null){
            puzzle.serialize(tag,"puzzle");
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("ticker")){
            ticker = tag.getInt("ticker");
        }else{
            ticker = -1;
        }
        if (tag.contains("puzzle")){
            this.puzzle = MemoryPuzzle.deserialize(tag,"puzzle");
        }
        this.used = CompoundNBTHelper.loadUUIDList(tag,"uuids");
    }
}
