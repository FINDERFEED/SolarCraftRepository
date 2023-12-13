package com.finderfeed.solarcraft.content.blocks;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.packet_handler.packets.crystal_energy_vines_puzzle.OpenPuzzleScreenPacket;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.NetworkDirection;
import org.jetbrains.annotations.Nullable;

public class CrystalEnergyVinesBlock extends Block implements EntityBlock {

    public CrystalEnergyVinesBlock(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult res) {
        if (player instanceof ServerPlayer s && level.getBlockEntity(pos) instanceof CrystalEnergyVinesTile tile) {
            if (tile.generatePattern()){
                Helpers.updateTile(tile);
                SCPacketHandler.INSTANCE.sendTo(new OpenPuzzleScreenPacket(tile),s.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }else{
                player.sendSystemMessage(Component.translatable("solarcraft.failed_to_generate_puzzle"));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return SolarcraftTileEntityTypes.CRYSTAL_ENERGY_VINES.get().create(pos,state);
    }
}
