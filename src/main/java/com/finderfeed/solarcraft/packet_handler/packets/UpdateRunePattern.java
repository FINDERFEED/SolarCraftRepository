package com.finderfeed.solarcraft.packet_handler.packets;

import com.finderfeed.solarcraft.helpers.ClientHelpers;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.RunePattern;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import java.util.function.Supplier;

public class UpdateRunePattern {


    private final CompoundTag pattern;
    private final boolean hideButtons;

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

    public void handle(PlayPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ClientHelpers.updatePlayerPattern(pattern, hideButtons);
        });
        
    }


}
