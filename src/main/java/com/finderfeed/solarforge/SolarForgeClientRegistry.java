package com.finderfeed.solarforge;

import com.finderfeed.solarforge.abilities.meteorite.MeteoriteProjectileRenderer;
import com.finderfeed.solarforge.abilities.solar_strike.SolarStrikeRenderer;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarforge.entities.renderers.*;
import com.finderfeed.solarforge.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalRenderer;
import com.finderfeed.solarforge.magic.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp.CorruptionWispRenderer;
import com.finderfeed.solarforge.magic.blocks.blockentities.containers.screens.*;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.renderers.ShadowBoltRenderer;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.InfuserRenderer;
import com.finderfeed.solarforge.magic.blocks.infusing_table_things.infusing_pool.InfusingStandRenderer;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.renderers.AbstractTurretProjectileRenderer;
import com.finderfeed.solarforge.magic.blocks.blockentities.projectiles.renderers.MortarProjectileRenderer;
import com.finderfeed.solarforge.magic.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarforge.magic.blocks.render.*;
import com.finderfeed.solarforge.magic.items.ProgressionBlockItem;
import com.finderfeed.solarforge.magic.projectiles.renderers.*;
import com.finderfeed.solarforge.magic.items.solar_disc_gun.SolarDiscProjectileRenderer;
import com.finderfeed.solarforge.magic.runic_network.repeater.RepeaterRenderer;
import com.finderfeed.solarforge.registries.ScreenSuppliers;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.registries.entities.EntityTypes;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic.blocks.solar_forge_block.SolarForgeBlockEntityRenderer;
import com.finderfeed.solarforge.magic.items.solar_lexicon.SolarLexiconContScreen;
import com.finderfeed.solarforge.client.rendering.RadiantTextureAtlasSpriteLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


import org.lwjgl.glfw.GLFW;



@Mod.EventBusSubscriber(modid = "solarforge",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class SolarForgeClientRegistry {
    public static final RadiantTextureAtlasSpriteLoader RADIANT_TEXTURE_ATLAS_SPRITE_LOADER = new RadiantTextureAtlasSpriteLoader();
    public static final KeyMapping FIRST_ABILITY_KEY = new KeyMapping("key.fire_ability_one", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_H,"key.solarforge.category");
    public static final KeyMapping SECOND_ABILITY_KEY = new KeyMapping("key.fire_ability_two", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_J,"key.solarforge.category");
    public static final KeyMapping THIRD_ABILITY_KEY = new KeyMapping("key.fire_ability_three", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_S,"key.solarforge.category");
    public static final KeyMapping FORTH_ABILITY_KEY = new KeyMapping("key.fire_ability_four", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyMapping OPEN_GUI_ABILITY_KEY = new KeyMapping("key.gui_button_solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyMapping GUI_ABILITY_BUY_SCREEN = new KeyMapping("key.ability_buy_screen.solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerTest(TextureStitchEvent.Pre event){
        MinecraftForgeClient.registerTextureAtlasSpriteLoader(RadiantTextureAtlasSpriteLoader.REGISTRY_ID,RADIANT_TEXTURE_ATLAS_SPRITE_LOADER);
    }


    @SubscribeEvent
    public static void registerClientStuff(final FMLClientSetupEvent event){

        ScreenParticlesRenderHandler.registerRenderType(SolarCraftRenderTypes.ParticleRenderTypes.RUNE_TILE_PARTICLE);
        ScreenParticlesRenderHandler.registerRenderType(SolarCraftRenderTypes.ParticleRenderTypes.SOLAR_STRIKE_PARTICLE_SCREEN);

        ScreenSuppliers.SCREEN_REGISTRY.registerAll();

        ItemBlockRenderTypes.setRenderLayer(SolarForge.SOLAR_INFUSER.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.INFUSING_POOL.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.SOLAR_LENS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.BONEMEALER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.SOLAR_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.ASH_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.RUNIC_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.DEAD_SPROUT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.RADIANT_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.RADIANT_GRASS_NOT_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.RADIANT_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.RADIANT_BERRY_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.RADIANT_CRYSTAL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.CRYSTAL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.EXPLOSION_BLOCKER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.CRYSTAL_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.VOID_LILY.get(), RenderType.cutout());

        ClientRegistry.registerKeyBinding(FIRST_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(SECOND_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(THIRD_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(FORTH_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(OPEN_GUI_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(GUI_ABILITY_BUY_SCREEN);





        //blockentityrenderers.register
        BlockEntityRenderers.register(TileEntitiesRegistry.REPEATER.get(), RepeaterRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.RUNE_ENERGY_PYLON.get(), RuneEnergyPylonRenderer::new);
        BlockEntityRenderers.register(SolarForge.SOLAR_FORGE_BLOCKENTITY.get(), SolarForgeBlockEntityRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.INFUSING_POOL_BLOCKENTITY.get(), InfusingStandRenderer::new);
        BlockEntityRenderers.register(SolarForge.INFUSING_STAND_BLOCKENTITY.get(), InfuserRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.SOLAR_REPEATER.get(), SolarRepeaterRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.ENERGY_GENERATOR_TILE.get(), EnergyGeneratorTileRender::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.SOLAR_CORE_TILE.get(), SolarCoreRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.AURA_HEALER_TILE.get(), AuraHealerRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.RAY_TRAP_TILE_ENTITY.get(), RayTrapTileEntityRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.PORTAL_CREATOR.get(), PortalCreatorRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.WORMHOLE.get(), WormholeRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.BONEMEALER.get(), BonemealerRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.INFUSING_CRAFTING_TABLE.get(), InfusingTableTileRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.EXPLOSTION_BLOCKER.get(), ExplosionBlockerRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.ENCHANTER.get(), EnchanterRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.RUNIC_ENERGY_CHARGER.get(), RunicEnergyChargerRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.ULDERA_PYLON.get(), UlderaPylonRenderer::new);
        BlockEntityRenderers.register(TileEntitiesRegistry.CLEARING_RITUAL_CRYSTAL.get(), ClearingRitualCrystalRenderer::new);

        MenuScreens.register(Containers.SOLAR_FURNACE_CONTAINER.get(), SolarFurnaceScreen::new);
        MenuScreens.register(Containers.RUNIC_TABLE_CONTAINER.get(), RunicTableContainerScreen::new);
        MenuScreens.register(Containers.SOLAR_LEXICON_CONTAINER.get(), SolarLexiconContScreen::new);
        MenuScreens.register(Containers.MODULE_APPLIER_CONTAINER.get(), ModuleApplierScreen::new);
        MenuScreens.register(Containers.INFUSING_TABLE_TILE.get(), InfusingTableScreen::new);
        MenuScreens.register(Containers.ENCHANTER.get(), EnchanterContainerScreen::new);
        MenuScreens.register(Containers.RUNIC_ENERGY_CHARGER.get(), RunicEnergyChargerScreen::new);

        event.enqueueWork(()->{

            ItemProperties.register(ItemsRegister.ULDORADIUM_ORE.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living,a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerUnlocked(BlocksRegistry.ULDORADIUM_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(ItemsRegister.SOLAR_STONE.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living,a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerUnlocked(BlocksRegistry.SOLAR_STONE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(ItemsRegister.ENDER_CRACKS.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living,a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerUnlocked(BlocksRegistry.ENDER_CRACKS.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SolarForge.SOLAR_ORE_ITEM.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living,a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {

                    return Helpers.hasPlayerUnlocked(SolarForge.SOLAR_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });

            ItemProperties.register(ItemsRegister.SOLAR_GOD_BOW.get(),new ResourceLocation("solarforge","pulling"),(stack,world,living,a)->{

                if (living != null){
                    return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
                }else{
                    return 0.0f;
                }
            });
        });
        registerDefaultUnknownBlockItemPredicate(ItemsRegister.LENSING_CRYSTAL_ORE.get());

    }

    private static void registerDefaultUnknownBlockItemPredicate(ProgressionBlockItem item){
        ItemProperties.register(item,new ResourceLocation("solarforge","unlocked"),(stack,world,living,a)->{

            Player playerEntity = Minecraft.getInstance().player;
            if (playerEntity != null) {

                return Helpers.hasPlayerUnlocked( ((ProgressionBlock)item.getBlock()).getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
            }else{
                return 0;
            }
        });

    }

    @SubscribeEvent
    public static void registerEntityRendering(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(SolarForge.METEORITE.get(), MeteoriteProjectileRenderer::new);
        event.registerEntityRenderer(SolarForge.SOLAR_STRIKE_ENTITY_REG.get(), SolarStrikeRenderer::new);
        event.registerEntityRenderer(EntityTypes.SOLAR_DISC.get(), SolarDiscProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypes.BLOCK_BOOMERANG.get(), BlockBoomerangProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypes.ULTRA_CROSSBOW_SHOT.get(), UltraCrossbowProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypes.MORTAR_PROJECTILE.get(), MortarProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypes.TURRET_PROJECTILE.get(), AbstractTurretProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypes.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMasterRenderer::new);
        event.registerEntityRenderer(EntityTypes.FALLING_BLOCK.get(), MyFallingBlockEntityRenderer::new);
        event.registerEntityRenderer(EntityTypes.CRYSTAL_BOSS.get(), CrystalBossRenderer::new);
        event.registerEntityRenderer(EntityTypes.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), ShieldingCrystalRenderer::new);
        event.registerEntityRenderer(EntityTypes.CRYSTAL_BOSS_ATTACK_HOLDING_MISSILE.get(), HoldingMissileRenderer::new);
        event.registerEntityRenderer(EntityTypes.CRYSTAL_BOSS_MINE.get(), MineEntityRenderer::new);
        event.registerEntityRenderer(EntityTypes.FALLING_MAGIC_MISSILE.get(), FallingMagicMissileRenderer::new);
        event.registerEntityRenderer(EntityTypes.RANDOM_BAD_EFFECT_PROJECTILE.get(), RandomBadEffectProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypes.RIP_RAY_GENERATOR.get(), RipRayGeneratorRender::new);
        event.registerEntityRenderer(EntityTypes.LEGENDARY_ITEM.get(), LegendaryItemRenderer::new);
        event.registerEntityRenderer(EntityTypes.BALL_LIGHTNING.get(), BallLightningRenderer::new);
        event.registerEntityRenderer(EntityTypes.RUNIC_ELEMENTAL_BOSS.get(), RunicElementalRenderer::new);
        event.registerEntityRenderer(EntityTypes.SUNSTRIKE.get(), SunstrikeRenderer::new);
        event.registerEntityRenderer(EntityTypes.EARTHQUAKE.get(), EarthquakeRenderer::new);
        event.registerEntityRenderer(EntityTypes.SOLAR_FIREBALL.get(), SolarFireballRenderer::new);
        event.registerEntityRenderer(EntityTypes.REFRACTION_CRYSTAL.get(), RefractionCrystalRenderer::new);
        event.registerEntityRenderer(EntityTypes.EXPLOSIVE_CRYSTAL.get(), ExplosiveCrystalRenderer::new);
        event.registerEntityRenderer(EntityTypes.RUNIC_WARRIOR.get(), RunicWarriorRenderer::new);
        event.registerEntityRenderer(EntityTypes.RUNIC_WARRIOR_ROCKET.get(), RunicWarriorSummoningRocketRenderer::new);
        event.registerEntityRenderer(EntityTypes.SHADOW_BOLT.get(), ShadowBoltRenderer::new);
        event.registerEntityRenderer(EntityTypes.SHADOW_ZOMBIE.get(), ShadowZombieRenderer::new);
        event.registerEntityRenderer(EntityTypes.CORRUPTION_WISP.get(), CorruptionWispRenderer::new);
    }

}
