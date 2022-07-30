package com.finderfeed.solarforge.magic.blocks;

import com.finderfeed.solarforge.Helpers;
import com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.CrystalEnergyVinesTile;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.packet_handler.packets.crystal_energy_vines_puzzle.OpenPuzzleScreenPacket;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
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
import net.minecraftforge.network.NetworkDirection;
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
                SolarForgePacketHandler.INSTANCE.sendTo(new OpenPuzzleScreenPacket(tile),s.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
            }else{
                player.sendMessage(new TranslatableComponent("solarcraft.failed_to_generate_puzzle"),player.getUUID());
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
