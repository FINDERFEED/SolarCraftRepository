package com.finderfeed.solarforge.registries.overlays;

import com.finderfeed.solarforge.SolarForge;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.InfuserBlock;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.InfuserTileEntity;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.SolarWandItem;
import com.finderfeed.solarforge.content.items.UltraCrossbowItem;
import com.finderfeed.solarforge.content.recipe_types.infusing_new.InfusingRecipe;
import com.finderfeed.solarforge.helpers.ClientHelpers;
import com.finderfeed.solarforge.misc_things.PhantomInventory;
import com.finderfeed.solarforge.registries.recipe_types.SolarcraftRecipeTypes;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = SolarForge.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class SolarcraftOverlays {


    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event){
        event.registerAboveAll("solar_wand",new SolarWand());
        event.registerAboveAll("ultra_crossbow",new UltraCrossbow());
    }

    public static class UltraCrossbow implements IGuiOverlay{
        public static ResourceLocation LOC = new ResourceLocation("solarforge","textures/misc/ultra_crossbow_load.png");
        public static ResourceLocation PRICEL = new ResourceLocation("solarforge","textures/misc/solar_crossbow_pricel.png");

        @Override
        public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
            Minecraft mc = Minecraft.getInstance();
                if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof UltraCrossbowItem) {
                    PoseStack stack = poseStack;
                    Window window = gui.getMinecraft().getWindow();
                    ClientHelpers.bindText(PRICEL);
                    RenderSystem.enableBlend();

                    RenderSystem.setShaderColor(1,1,0.3f,0.5f);
                    int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                    int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                    GuiComponent.blit(stack,width,height,0,0,41,41,41,41);
                    RenderSystem.setShaderColor(1,1,1f,1f);
                }else if( Minecraft.getInstance().player.getOffhandItem().getItem() instanceof UltraCrossbowItem){
                    PoseStack stack = poseStack;
                    Window window = gui.getMinecraft().getWindow();
                    ClientHelpers.bindText(PRICEL);
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(1,1,0.3f,0.5f);

                    int width = (int)((window.getWidth())/2/window.getGuiScale() -21);
                    int height = (int)((window.getHeight())/2/window.getGuiScale() - 20);

                    GuiComponent.blit(stack,width,height,0,0,41,41,41,41);
                    RenderSystem.setShaderColor(1,1,1f,1f);
                }


        }
    }

    public static class SolarWand implements IGuiOverlay{

        public static final ResourceLocation LOC = new ResourceLocation("solarforge", "textures/misc/wand_crafting_progress.png");

        @Override
        public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
            Minecraft event = gui.getMinecraft();
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player.getMainHandItem().getItem() instanceof SolarWandItem) {
//                ClipContext ctx = new ClipContext(player.position().add(0, 1.5, 0),
//                        player.position().add(0, 1.5, 0).add(player.getLookAngle().normalize().multiply(4.5, 4.5, 4.5)),
//                        ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player);
//                BlockHitResult result = player.level.clip(ctx);
                HitResult re = mc.hitResult;
                if (re instanceof BlockHitResult result &&
                        player.level.getBlockState(result.getBlockPos()).getBlock() instanceof InfuserBlock) {
                    BlockEntity tile = player.level.getBlockEntity(result.getBlockPos());
                    if (tile instanceof InfuserTileEntity) {
                        InfuserTileEntity tileInfusing = (InfuserTileEntity) tile;
                        ClientHelpers.bindText(LOC);
                        if (tileInfusing.RECIPE_IN_PROGRESS) {
                            double percent = (float) tileInfusing.CURRENT_PROGRESS / tileInfusing.INFUSING_TIME;
                            int height = event.getWindow().getGuiScaledHeight();
                            int width = event.getWindow().getGuiScaledWidth();

                            GuiComponent.blit(poseStack, width / 2 - 20, height / 2 + 11, 0, 9, (int) (40 * percent), 3, 40, 20);
                            GuiComponent.blit(poseStack, width / 2 - 20, height / 2 + 8, 0, 0, 40, 9, 40, 20);
                        }else{
                            Optional<InfusingRecipe> recipe = mc.level.getRecipeManager().getRecipeFor(SolarcraftRecipeTypes.INFUSING.get(),new PhantomInventory(tileInfusing.getInventory()),mc.level);
                            if (recipe.isPresent()) {
                                int height = event.getWindow().getGuiScaledHeight();
                                int width = event.getWindow().getGuiScaledWidth();
                                GuiComponent.blit(poseStack, width / 2 - 20, height / 2 + 8, 0, 0, 40, 9, 40, 20);
                                GuiComponent.blit(poseStack, width / 2 -7, height / 2 + 7, 14, 24, 14, 14, 80, 40);
                            }else{
                                int height = event.getWindow().getGuiScaledHeight();
                                int width = event.getWindow().getGuiScaledWidth();
                                GuiComponent.blit(poseStack, width / 2 - 20, height / 2 + 8, 0, 0, 40, 9, 40, 20);
                                GuiComponent.blit(poseStack, width / 2 -7, height / 2 + 7, 0, 24, 14, 14, 80, 40);

                            }
                        }

                    }
                }

            }
        }
    }
}
