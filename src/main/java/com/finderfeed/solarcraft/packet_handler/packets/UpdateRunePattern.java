package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.RunePattern;
import com.finderfeed.solarcraft.packet_handler.packet_system.FDPacket;
import com.finderfeed.solarcraft.packet_handler.packet_system.Packet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

@Packet("update_rune_pattern")
public class UpdateRunePattern extends FDPacket {


    private CompoundTag pattern;
    private boolean hideButtons;

    public UpdateRunePattern(Player player,boolean shouldHideButtons) {
        this.pattern = player.getPersistentData().getCompound(RunePattern.PATTERN_SAVE_ID);
        this.hideButtons = shouldHideButtons;
    }


    public UpdateRunePattern(FriendlyByteBuf buf) {
        this.pattern = buf.readNbt();
        this.hideButtons = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(pattern);
        buf.writeBoolean(hideButtons);
    }
//
//    public void handle(PlayPayloadContext ctx) {
//            ClientHelpers.updatePlayerPattern(pattern, hideButtons);
//
//    }

    @Override
    public void clientPlayHandle(PlayPayloadContext ctx) {
        ClientHelpers.updatePlayerPattern(pattern, hideButtons);
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        this.toBytes(friendlyByteBuf);
    }
}
