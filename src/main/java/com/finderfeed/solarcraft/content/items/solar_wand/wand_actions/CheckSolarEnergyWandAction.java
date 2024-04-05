package com.finderfeed.solarcraft.content.items.solar_wand.wand_actions;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.content.blocks.solar_energy.SolarEnergyContainer;
import com.finderfeed.solarcraft.content.items.solar_wand.*;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class CheckSolarEnergyWandAction implements WandAction<EmptyWandData> {
    @Override
    public InteractionResult run(WandUseContext context, EmptyWandData data) {

        Player player = context.player();
        UseOnContext ctx = context.context();
        Level level = context.level();

        if (!level.isClientSide && level.getBlockEntity(ctx.getClickedPos()) instanceof SolarEnergyContainer container){
            player.displayClientMessage(Component.literal("" + container.getSolarEnergy()).withStyle(ChatFormatting.GOLD),true);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public WandDataSerializer<EmptyWandData> getWandDataSerializer() {
        return EmptyWandData.SERIALIZER;
    }

    @Override
    public WandActionType getActionType(Player player) {
        return WandActionType.BLOCK;
    }

    @Override
    public Component getActionDescription() {
        return Component.translatable("solarcraft.wand_action.check_solar_energy");
    }

    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(SolarCraft.MOD_ID,"check_solar_energy_level");
    }

    @Override
    public ItemStack getIcon() {
        return SCItems.ENERGY_GENERATOR_BLOCK.get().getDefaultInstance();
    }
}
