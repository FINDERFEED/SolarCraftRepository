package com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal;

import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.SolarCraft;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class ClearingRitualCrystalItem extends BlockItem {
    public ClearingRitualCrystalItem(Block block, Properties props) {
        super(block, props);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> components, TooltipFlag flag) {
        CompoundTag tileTag = stack.getTagElement(SolarCraft.MOD_ID + "_tile_saved_data");
        if (tileTag != null){
            CompoundTag tag = tileTag.getCompound("data");
            if (tag.contains("retype")){
                components.add(Component.literal(tag.getString("retype").toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.GOLD));
            }
        }
        super.appendHoverText(stack, world, components, flag);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(RenderProperties.INSTANCE);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext ctx, BlockState state) {
        return super.canPlace(ctx, state) && ctx.getLevel().getBlockState(ctx.getClickedPos().above()).isAir();
    }

    @Override
    public InteractionResult place(BlockPlaceContext ctx) {
        BlockPlaceContext context = new BlockPlaceContext(ctx.getLevel(),ctx.getPlayer(),ctx.getHand(),ctx.getItemInHand(),
                new BlockHitResult(Helpers.getBlockCenter(ctx.getClickedPos().above()),Direction.UP,ctx.getClickedPos().above(),false));
        return super.place(context);
    }
}
class RenderProperties implements IClientItemExtensions {

    public static final RenderProperties INSTANCE = new RenderProperties();


    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new ClearingRitualCrystalISTER(Minecraft.getInstance().getBlockEntityRenderDispatcher(),Minecraft.getInstance().getEntityModels());
    }
}
