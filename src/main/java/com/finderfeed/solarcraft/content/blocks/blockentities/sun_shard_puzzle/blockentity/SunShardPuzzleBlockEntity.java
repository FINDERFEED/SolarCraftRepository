package com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.blockentity;

import com.finderfeed.solarcraft.content.blocks.blockentities.SolarcraftBlockEntity;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.Puzzle;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.puzzle_template.PuzzleTemplateManager;
import com.finderfeed.solarcraft.packet_handler.SolarCraftPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.sun_shard_puzzle.SunShardPuzzleOpenScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkDirection;

import java.util.List;

public class SunShardPuzzleBlockEntity extends SolarcraftBlockEntity {

    private Puzzle puzzle;

    public SunShardPuzzleBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }


    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void onUse(Player player){
        if (!level.isClientSide){
            if (puzzle == null){
                List<String> templates = PuzzleTemplateManager.INSTANCE.getAllTemplates();
                String template = templates.get(player.level.random.nextInt(templates.size()));
                puzzle = new Puzzle(template,20);
            }
            SolarCraftPacketHandler.INSTANCE.sendTo(new SunShardPuzzleOpenScreen(puzzle,getBlockPos()),
                    ((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (puzzle != null){
            puzzle.serialize("puzzle",tag);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("puzzle")){
            puzzle = Puzzle.deserialize("puzzle",tag);
        }
    }
}
