package com.finderfeed.solarforge.infusing_table_things;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.misc_things.AbstractEnergyGeneratorTileEntity;
import com.finderfeed.solarforge.misc_things.AbstractSolarNetworkRepeater;
import com.finderfeed.solarforge.misc_things.IEnergyUser;
import com.finderfeed.solarforge.misc_things.ManaConsumer;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class SolarWandItem extends Item implements ManaConsumer {


    public SolarWandItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


    public ActionResultType useOn(ItemUseContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        World world = ctx.getLevel();
        if (!world.isClientSide && world.getBlockEntity(pos) != null && world.getBlockEntity(pos) instanceof InfusingTableTileEntity) {
            InfusingTableTileEntity tile = (InfusingTableTileEntity) world.getBlockEntity(pos);
            tile.triggerCrafting(ctx.getPlayer());
            return ActionResultType.SUCCESS;
        }


        return ActionResultType.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent("solarcraft.solar_wand").withStyle(TextFormatting.GOLD));
        super.appendHoverText(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public double getManacost() {
        return 0;
    }
}

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = "solarforge",value = Dist.CLIENT)
class WandEvents{

    @SubscribeEvent
    public static void renderWandOverlays(final RenderGameOverlayEvent event){

            if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
                Minecraft mc = Minecraft.getInstance();
                PlayerEntity player = mc.player;
                if (player.getMainHandItem().getItem() instanceof SolarWandItem) {
                    RayTraceContext ctx = new RayTraceContext(player.position().add(0, 1.5, 0),
                            player.position().add(0, 1.5, 0).add(player.getLookAngle().normalize().multiply(4.5, 4.5, 4.5)),
                            RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player);
                    BlockRayTraceResult result = player.level.clip(ctx);

                    if (result.getType() == RayTraceResult.Type.BLOCK &&
                            player.level.getBlockState(result.getBlockPos()).getBlock() instanceof InfusingTableBlock) {
                        TileEntity tile = player.level.getBlockEntity(result.getBlockPos());
                        if (tile instanceof InfusingTableTileEntity) {
                            InfusingTableTileEntity tileInfusing = (InfusingTableTileEntity) tile;

                            mc.getTextureManager().bind(new ResourceLocation("solarforge", "textures/misc/wand_crafting_progress.png"));
                            if (tileInfusing.RECIPE_IN_PROGRESS) {
                                double percent = (float) tileInfusing.CURRENT_PROGRESS / tileInfusing.INFUSING_TIME;
                                int height = event.getWindow().getGuiScaledHeight();
                                int width = event.getWindow().getGuiScaledWidth();

                                AbstractGui.blit(event.getMatrixStack(), width / 2 - 20, height / 2 + 11, 0, 9, (int) (40 * percent), 3, 40, 20);
                                AbstractGui.blit(event.getMatrixStack(), width / 2 - 20, height / 2 + 8, 0, 0, 40, 9, 40, 20);
                            }else{
                                Optional<InfusingRecipe> recipe = mc.level.getRecipeManager().getRecipeFor(SolarForge.INFUSING_RECIPE_TYPE,tileInfusing,mc.level);
                                if (recipe.isPresent()) {
                                    int height = event.getWindow().getGuiScaledHeight();
                                    int width = event.getWindow().getGuiScaledWidth();
                                    AbstractGui.blit(event.getMatrixStack(), width / 2 - 20, height / 2 + 8, 0, 0, 40, 9, 40, 20);
                                    AbstractGui.blit(event.getMatrixStack(), width / 2 -7, height / 2 + 7, 14, 24, 14, 14, 80, 40);
                                }else{
                                    int height = event.getWindow().getGuiScaledHeight();
                                    int width = event.getWindow().getGuiScaledWidth();
                                    AbstractGui.blit(event.getMatrixStack(), width / 2 - 20, height / 2 + 8, 0, 0, 40, 9, 40, 20);
                                    AbstractGui.blit(event.getMatrixStack(), width / 2 -7, height / 2 + 7, 0, 24, 14, 14, 80, 40);
                                }
                            }

                        }
                    }
                }
            }

    }
}
