package com.finderfeed.solarcraft.content.items;

import com.finderfeed.solarcraft.content.blocks.blockentities.RuneEnergyPylonTile;
import com.finderfeed.solarcraft.content.items.isters.EnergyPylonISTER;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.misc_things.RunicEnergy;
import com.finderfeed.solarcraft.registries.items.SCItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;


import java.util.List;
import java.util.Locale;

public class RuneEnergyPylonBlockItem extends BlockItem {
    public RuneEnergyPylonBlockItem(Block block, Properties props) {
        super(block, props);
    }


    @Override
    protected boolean placeBlock(BlockPlaceContext ctx, BlockState state) {
        boolean result = super.placeBlock(ctx,state);
        Level level = ctx.getLevel();
        if (!level.isClientSide && result && level.getBlockEntity(ctx.getClickedPos()) instanceof RuneEnergyPylonTile tile){
            RunicEnergy.Type type = getType(ctx.getItemInHand());
            tile.setType(type);
            Helpers.updateTile(tile);
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> cmps, TooltipFlag flag) {
        super.appendHoverText(stack, level, cmps, flag);
        RunicEnergy.Type type = getType(stack);
        cmps.add(Component.translatable("solarcraft.item.rune_energy_pylon").withStyle(ChatFormatting.GOLD)
                .append(Component.literal(": " + type.id.toUpperCase(Locale.ROOT))));
        cmps.add(Component.translatable("solarcraft.item.rune_energy_pylon2").withStyle(ChatFormatting.RED));
    }

    public static ItemStack createWithType(RunicEnergy.Type type){
        ItemStack itemStack = new ItemStack(SCItems.RUNE_ENERGY_PYLON.get());
        itemStack.getOrCreateTag().putString("re_type",type.id);
        return itemStack;
    }

    private RunicEnergy.Type getType(ItemStack stack){
        RunicEnergy.Type type;
        return (type = RunicEnergy.Type.byId(stack.getOrCreateTag().getString("re_type"))) != null ? type : RunicEnergy.Type.ARDO;
    }

    @Override
    public Component getName(ItemStack p_41458_) {
        return Component.translatable("item.solarcraft.rune_energy_pylon");
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

}
