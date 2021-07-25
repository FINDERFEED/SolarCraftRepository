package com.finderfeed.solarforge;

import com.finderfeed.solarforge.SolarAbilities.MeteoriteProjectileRenderer;
import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntityRender;
import com.finderfeed.solarforge.entities.renderers.VillagerSolarMasterRenderer;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.InfusingTableRenderer;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPoolRenderer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens.RunicTableContainerScreen;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens.SolarFurnaceScreen;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers.AbstractTurretProjectileRenderer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers.MortarProjectileRenderer;
import com.finderfeed.solarforge.magic_items.blocks.render.*;
import com.finderfeed.solarforge.magic_items.items.projectiles.renderers.BlockBoomerangProjectileRenderer;
import com.finderfeed.solarforge.magic_items.items.projectiles.renderers.UltraCrossbowProjectileRenderer;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscProjectileRenderer;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntityRenderer;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexiconContScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import org.lwjgl.glfw.GLFW;

import KeyMapping;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class SolarForgeClientRegistry {
    public static final KeyMapping FIRST_ABILITY_KEY = new KeyMapping("key.fire_ability_one", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_H,"key.solarforge.category");
    public static final KeyMapping SECOND_ABILITY_KEY = new KeyMapping("key.fire_ability_two", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_J,"key.solarforge.category");
    public static final KeyMapping THIRD_ABILITY_KEY = new KeyMapping("key.fire_ability_three", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_S,"key.solarforge.category");
    public static final KeyMapping FORTH_ABILITY_KEY = new KeyMapping("key.fire_ability_four", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyMapping ADMIN_ABILITY_KEY = new KeyMapping("key.admin_button_solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyMapping OPEN_GUI_ABILITY_KEY = new KeyMapping("key.gui_button_solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
  //  public static final KeyBinding TOGGLE_MANA_HUD = new KeyBinding("key.mana_hud.solarforge", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    @SubscribeEvent
    public static void registerClientStuff(final FMLClientSetupEvent event){

        ItemBlockRenderTypes.setRenderLayer(SolarForge.SOLAR_INFUSER.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.SOLAR_POOL.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.SOLAR_LENS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.SOLAR_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.ASH_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.RUNIC_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.DEAD_SPROUT.get(), RenderType.cutout());

        ClientRegistry.registerKeyBinding(FIRST_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(SECOND_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(THIRD_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(FORTH_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(OPEN_GUI_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(ADMIN_ABILITY_KEY);
        //ClientRegistry.registerKeyBinding(TOGGLE_MANA_HUD);
        RenderingRegistry.registerEntityRenderingHandler(SolarForge.METEORITE.get(), MeteoriteProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SolarForge.SOLAR_STRIKE_ENTITY_REG.get(), SolarStrikeEntityRender::new);
        RenderingRegistry.registerEntityRenderingHandler(Projectiles.SOLAR_DISC.get(), SolarDiscProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Projectiles.BLOCK_BOOMERANG.get(), BlockBoomerangProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Projectiles.ULTRA_CROSSBOW_SHOT.get(), UltraCrossbowProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Projectiles.MORTAR_PROJECTILE.get(), MortarProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Projectiles.TURRET_PROJECTILE.get(), AbstractTurretProjectileRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(Projectiles.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMasterRenderer::new);


        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.RUNE_ENERGY_PYLON.get(), RuneEnergyPylonRenderer::new);
        ClientRegistry.bindTileEntityRenderer(SolarForge.SOLAR_FORGE_BLOCKENTITY.get(), SolarForgeBlockEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get(), InfusingPoolRenderer::new);
        ClientRegistry.bindTileEntityRenderer(SolarForge.INFUSING_STAND_BLOCKENTITY.get(), InfusingTableRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.SOLAR_REPEATER.get(), SolarRepeaterRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.ENERGY_GENERATOR_TILE.get(), EnergyGeneratorTileRender::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.SOLAR_CORE_TILE.get(), SolarCoreRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.AURA_HEALER_TILE.get(), AuraHealerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.RAY_TRAP_TILE_ENTITY.get(), RayTrapTileEntityRenderer::new);



        MenuScreens.register(Containers.SOLAR_FURNACE_CONTAINER.get(), SolarFurnaceScreen::new);
        MenuScreens.register(Containers.RUNIC_TABLE_CONTAINER.get(), RunicTableContainerScreen::new);
        MenuScreens.register(Containers.SOLAR_LEXICON_CONTAINER.get(), SolarLexiconContScreen::new);
        event.enqueueWork(()->{
            ItemProperties.register(ItemsRegister.ULDORADIUM_ORE.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerUnlocked(BlocksRegistry.ULDORADIUM_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(ItemsRegister.SOLAR_STONE.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerUnlocked(BlocksRegistry.SOLAR_STONE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SolarForge.SOLAR_ORE_ITEM.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {

                    return Helpers.hasPlayerUnlocked(SolarForge.SOLAR_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
        });
    }
}
