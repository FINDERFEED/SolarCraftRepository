package com.finderfeed.solarforge;

import com.finderfeed.solarforge.SolarAbilities.MeteoriteProjectileRenderer;
import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntity;
import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntityRender;
import com.finderfeed.solarforge.entities.renderers.VillagerSolarMasterRenderer;
import com.finderfeed.solarforge.infusing_table_things.InfusingTableRenderer;
import com.finderfeed.solarforge.infusing_table_things.infusing_pool.InfusingPoolRenderer;
import com.finderfeed.solarforge.infusing_table_things.infusing_pool.InfusingPoolTileEntity;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.SolarEnergyRepeaterBlockEntity;
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
import com.finderfeed.solarforge.solar_forge_block.SolarForgeBlockEntityRenderer;
import com.finderfeed.solarforge.solar_forge_screen.SolarForgeScreen;
import com.finderfeed.solarforge.solar_lexicon.SolarLexiconContScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class SolarForgeClientRegistry {
    public static final KeyBinding FIRST_ABILITY_KEY = new KeyBinding("key.fire_ability_one", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_H,"key.solarforge.category");
    public static final KeyBinding SECOND_ABILITY_KEY = new KeyBinding("key.fire_ability_two", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_J,"key.solarforge.category");
    public static final KeyBinding THIRD_ABILITY_KEY = new KeyBinding("key.fire_ability_three", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_S,"key.solarforge.category");
    public static final KeyBinding FORTH_ABILITY_KEY = new KeyBinding("key.fire_ability_four", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyBinding ADMIN_ABILITY_KEY = new KeyBinding("key.admin_button_solarforge", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyBinding OPEN_GUI_ABILITY_KEY = new KeyBinding("key.gui_button_solarforge", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
  //  public static final KeyBinding TOGGLE_MANA_HUD = new KeyBinding("key.mana_hud.solarforge", KeyConflictContext.UNIVERSAL, InputMappings.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    @SubscribeEvent
    public static void registerClientStuff(final FMLClientSetupEvent event){

        RenderTypeLookup.setRenderLayer(SolarForge.SOLAR_INFUSER.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(BlocksRegistry.SOLAR_POOL.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(BlocksRegistry.SOLAR_LENS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(BlocksRegistry.SOLAR_FLOWER.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlocksRegistry.ASH_LEAVES.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(BlocksRegistry.DEAD_SPROUT.get(), RenderType.cutout());

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

        ClientRegistry.bindTileEntityRenderer(SolarForge.SOLAR_FORGE_BLOCKENTITY.get(), SolarForgeBlockEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get(), InfusingPoolRenderer::new);
        ClientRegistry.bindTileEntityRenderer(SolarForge.INFUSING_STAND_BLOCKENTITY.get(), InfusingTableRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.SOLAR_REPEATER.get(), SolarRepeaterRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.ENERGY_GENERATOR_TILE.get(), EnergyGeneratorTileRender::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.SOLAR_CORE_TILE.get(), SolarCoreRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.AURA_HEALER_TILE.get(), AuraHealerRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesRegistry.RAY_TRAP_TILE_ENTITY.get(), RayTrapTileEntityRenderer::new);

        ScreenManager.register(Containers.SOLAR_FURNACE_CONTAINER.get(), SolarFurnaceScreen::new);
        ScreenManager.register(Containers.SOLAR_LEXICON_CONTAINER.get(), SolarLexiconContScreen::new);
        event.enqueueWork(()->{
            ItemModelsProperties.register(ItemsRegister.ULDORADIUM_ORE.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living)->{

                PlayerEntity playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerUnlocked(BlocksRegistry.ULDORADIUM_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
        });
    }
}
