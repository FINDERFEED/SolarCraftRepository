package com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile;

import com.finderfeed.solarforge.events.other_events.event_handler.ClientEventsHandler;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.ClearingRitual;
import com.finderfeed.solarforge.local_library.helpers.FDMathHelper;
import com.finderfeed.solarforge.misc_things.Flash;
import com.finderfeed.solarforge.registries.sounds.SolarcraftSounds;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualTileRenderer.DIMENSION_CRACK_ANIMATION_TICKS;
import static com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualTileRenderer.RAY_ANIMATION_TICKS;

public class ClearingRitualMainTile extends BlockEntity {


    public ClearingRitual ritual = new ClearingRitual(this);


    public ClearingRitualMainTile(BlockPos pos, BlockState state) {
        super(SolarcraftTileEntityTypes.CLEARING_RITUAL_MAIN_BLOCK.get(), pos, state);
    }

    public static void tick(ClearingRitualMainTile tile, BlockPos pos, BlockState state, Level world){
        tile.ritual.tick();

    }

    public void notifyCrystalExploded(){
        this.ritual.stopRitual();
    }

    public void startRitual(Player user){
        ritual.tryStartRitual(user);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return Helpers.createTilePacket(this,this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);

        return tag;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ritual.save(tag);
    }


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ritual.load(tag);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return Helpers.createAABBWithRadius(Helpers.getBlockCenter(this.getBlockPos()),20,100);
    }
}
