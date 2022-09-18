package com.finderfeed.solarforge.packet_handler.packets;

import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.content.items.solar_lexicon.unlockables.RunePattern;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateRunePattern {


    private final CompoundTag pattern;
    private final boolean hideButtons;

    public UpdateRunePattern(Player player,boolean shouldHideButtons) {
        this.pattern = player.getPersistentData().getCompound(RunePattern.PATTERN_SAVE_ID);
        this.hideButtons = shouldHideButtons;
    }

    public UpdateRunePattern(FriendlyByteBuf buf) {
        this.pattern = buf.readAnySizeNbt();
        this.hideButtons = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(pattern);
        buf.writeBoolean(hideButtons);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientHelpers.updatePlayerPattern(pattern, hideButtons);
        });
        ctx.get().setPacketHandled(true);
    }


}
