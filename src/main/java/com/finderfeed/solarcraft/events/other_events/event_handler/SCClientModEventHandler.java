package com.finderfeed.solarcraft.events.other_events.event_handler;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.radiant_texture.RadiantTextureSpriteSource;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SCRenderTypes;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.PostChainPlusUltra;
import com.finderfeed.solarcraft.client.rendering.shaders.post_chains.UniformPlusPlus;
import com.finderfeed.solarcraft.client.tooltips.REClientTooltipComponent;
import com.finderfeed.solarcraft.client.tooltips.RETooltipComponent;
import com.finderfeed.solarcraft.content.abilities.meteorite.MeteoriteProjectileRenderer;
import com.finderfeed.solarcraft.content.abilities.solar_strike.SolarStrikeRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp.CorruptionWispRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualTileRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.containers.screens.*;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers.AbstractTurretProjectileRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers.MortarProjectileRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.projectiles.renderers.ShadowBoltRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamGeneratorRenderer;
import com.finderfeed.solarcraft.content.blocks.blockentities.sun_shard_puzzle.ray_puzzle.blockentities.BeamReflectorRenderer;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserRenderer;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.InfuserScreen;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStandRenderer;
import com.finderfeed.solarcraft.content.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarcraft.content.blocks.render.*;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockEntityRenderer;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeScreen;
import com.finderfeed.solarcraft.content.entities.CrystalBossBar;
import com.finderfeed.solarcraft.content.entities.projectiles.renderers.*;
import com.finderfeed.solarcraft.content.entities.renderers.*;
import com.finderfeed.solarcraft.content.entities.renderers.uldera_crystal.EffectCrystalRenderer;
import com.finderfeed.solarcraft.content.entities.renderers.uldera_crystal.UlderaCrystalBossRenderer;
import com.finderfeed.solarcraft.content.entities.renderers.uldera_crystal.UlderaLightningRenderer;
import com.finderfeed.solarcraft.content.entities.runic_elemental.RunicElementalBossBar;
import com.finderfeed.solarcraft.content.entities.uldera_crystal.UlderaCrystalBossBar;
import com.finderfeed.solarcraft.content.items.ProgressionBlockItem;
import com.finderfeed.solarcraft.content.items.SunShardItem;
import com.finderfeed.solarcraft.content.items.solar_disc_gun.SolarDiscProjectileRenderer;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexiconContScreen;
import com.finderfeed.solarcraft.content.runic_network.repeater.RepeaterRenderer;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.RadiantLandDimEffects;
import com.finderfeed.solarcraft.events.RenderEventsHandler;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.GlowShaderProcessor;
import com.finderfeed.solarcraft.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarcraft.local_library.helpers.RenderingTools;
import com.finderfeed.solarcraft.registries.SCBedrockModels;
import com.finderfeed.solarcraft.registries.SCRenderTargets;
import com.finderfeed.solarcraft.registries.ScreenSuppliers;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.containers.SCContainers;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.overlays.SolarcraftOverlays;
import com.finderfeed.solarcraft.registries.tile_entities.SCTileEntities;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

import java.util.Map;


@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class SCClientModEventHandler {

    public static final DimensionSpecialEffects RADIANT_LAND  = new RadiantLandDimEffects();
//    public static final RadiantTextureAtlasSpriteLoader RADIANT_TEXTURE_ATLAS_SPRITE_LOADER = new RadiantTextureAtlasSpriteLoader();
    public static final KeyMapping FIRST_ABILITY_KEY = new KeyMapping("key.fire_ability_one", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_H,"key.solarcraft.category");
    public static final KeyMapping SECOND_ABILITY_KEY = new KeyMapping("key.fire_ability_two", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_J,"key.solarcraft.category");
    public static final KeyMapping THIRD_ABILITY_KEY = new KeyMapping("key.fire_ability_three", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_S,"key.solarcraft.category");
    public static final KeyMapping FORTH_ABILITY_KEY = new KeyMapping("key.fire_ability_four", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarcraft.category");
    public static final KeyMapping GUI_ABILITY_BUY_SCREEN = new KeyMapping("key.ability_buy_screen.solarcraft", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarcraft.category");
    public static final KeyMapping GUI_WAND_MODE_SELECTION = new KeyMapping("key.wand_mode_selection_screen", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_K,"key.solarcraft.category");
    public static final KeyMapping MEMORIZE_AND_CLOSE = new KeyMapping("key.memorize_and_close", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C,"key.solarcraft.category");
    public static final KeyMapping CLOSE_ALL_PAGES = new KeyMapping("key.close_all_pages", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X,"key.solarcraft.category");

    public static SpriteSourceType RADIANT_TEXTURE_TYPE;

    @SubscribeEvent
    public static void registerTooltips(RegisterClientTooltipComponentFactoriesEvent event){
        event.register(RETooltipComponent.class, REClientTooltipComponent::new);
    }

    @SubscribeEvent
    public static void registerSpriteSourceTypes(RegisterSpriteSourceTypesEvent event){
        RADIANT_TEXTURE_TYPE = event.register(new ResourceLocation(SolarCraft.MOD_ID,"radiant_textures"), RadiantTextureSpriteSource.CODEC);
    }





    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        event.register(FIRST_ABILITY_KEY);
        event.register(SECOND_ABILITY_KEY);
        event.register(THIRD_ABILITY_KEY);
        event.register(FORTH_ABILITY_KEY);
        event.register(GUI_ABILITY_BUY_SCREEN);
        event.register(GUI_WAND_MODE_SELECTION);
        event.register(MEMORIZE_AND_CLOSE);
        event.register(CLOSE_ALL_PAGES);
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event){
        event.register(new ResourceLocation("solarcraft","radiant_land"),RADIANT_LAND);
    }

    @SubscribeEvent
    public static void registerClientStuff(final FMLClientSetupEvent event){

        NeoForge.EVENT_BUS.register(new RenderEventsHandler());
        ScreenParticlesRenderHandler.registerRenderType(SCRenderTypes.ParticleRenderTypes.RUNE_TILE_PARTICLE);
        ScreenParticlesRenderHandler.registerRenderType(SCRenderTypes.ParticleRenderTypes.SOLAR_STRIKE_PARTICLE_SCREEN);

        ScreenSuppliers.SCREEN_REGISTRY.registerAll();

        SolarcraftOverlays.BossBars.registerCustomBossBar("runic_elemental",new RunicElementalBossBar());
        SolarcraftOverlays.BossBars.registerCustomBossBar("defense_crystal",new CrystalBossBar());
        SolarcraftOverlays.BossBars.registerCustomBossBar("uldera_crystal",new UlderaCrystalBossBar());

        BlockEntityRenderers.register(SCTileEntities.RUNIC_ENERGY_REPEATER.get(), RepeaterRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.RUNE_ENERGY_PYLON.get(), RuneEnergyPylonRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.SOLAR_FORGE_BLOCKENTITY.get(), SolarForgeBlockEntityRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.INFUSING_POOL_BLOCKENTITY.get(), InfusingStandRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.INFUSING_STAND_BLOCKENTITY.get(), InfuserRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.SOLAR_REPEATER.get(), SolarRepeaterRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.ENERGY_GENERATOR_TILE.get(), EnergyGeneratorTileRender::new);
        BlockEntityRenderers.register(SCTileEntities.SOLAR_CORE_TILE.get(), SolarCoreRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.AURA_HEALER_TILE.get(), AuraHealerRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.RAY_TRAP_TILE_ENTITY.get(), RayTrapTileEntityRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.WORMHOLE.get(), WormholeRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.BONEMEALER.get(), BonemealerRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.INFUSING_CRAFTING_TABLE.get(), InfusingTableTileRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.EXPLOSTION_BLOCKER.get(), ExplosionBlockerRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.ENCHANTER.get(), EnchanterRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.RUNIC_ENERGY_CHARGER.get(), RunicEnergyChargerRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.ULDERA_PYLON.get(), UlderaPylonRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.CLEARING_RITUAL_CRYSTAL.get(), ClearingRitualCrystalRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.CLEARING_RITUAL_MAIN_BLOCK.get(), ClearingRitualTileRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.DIMENSION_CORE_TILE.get(), DimensionCoreRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.SAVANNA_DUNGEON_KEEPER.get(), SavannaDungeonKeeperRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.RUNIC_ENERGY_CORE.get(), RunicEnergyCoreRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.BEAM_GENERATOR.get(), BeamGeneratorRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.BEAM_REFLECTOR.get(), BeamReflectorRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.ELEMENT_WEAVER.get(), ElementWeaverRenderer::new);
        BlockEntityRenderers.register(SCTileEntities.ORBITAL_MISSILE_LAUNCHER.get(), OrbitalMissileLauncherTERenderer::new);


        MenuScreens.register(SCContainers.SOLAR_FURNACE_CONTAINER.get(), SolarFurnaceScreen::new);
        MenuScreens.register(SCContainers.RUNIC_TABLE_CONTAINER.get(), RunicTableContainerScreen::new);
        MenuScreens.register(SCContainers.SOLAR_LEXICON_CONTAINER.get(), SolarLexiconContScreen::new);
        MenuScreens.register(SCContainers.MODULE_APPLIER_CONTAINER.get(), ModuleApplierScreen::new);
        MenuScreens.register(SCContainers.INFUSING_TABLE_TILE.get(), InfusingTableScreen::new);
        MenuScreens.register(SCContainers.ENCHANTER.get(), EnchanterContainerScreen::new);
        MenuScreens.register(SCContainers.RUNIC_ENERGY_CHARGER.get(), RunicEnergyChargerScreen::new);
        MenuScreens.register(SCContainers.ELEMENT_WEAVER.get(), ElementWeaverContainerScreen::new);
        MenuScreens.register(SCContainers.SOLAR_FORGE_CONTAINER.get(), SolarForgeScreen::new);
        MenuScreens.register(SCContainers.INFUSING_TABLE_CONTAINER.get(), InfuserScreen::new);
        event.enqueueWork(()->{

            var window = Minecraft.getInstance().getWindow();
            int width = window.getWidth();
            int height = window.getHeight();
            try {
                GlowShaderProcessor.BLOOM_SHADER = RenderingTools.loadSingleShader("solarcraft:bloom/bloom");
            }catch (Exception e){
                throw new RuntimeException("Failed to load bloom shader.",e);
            }



            SCBedrockModels.init();

            ItemProperties.register(SCItems.SUN_SHARD.get(),new ResourceLocation("solarcraft","heated_up"),(stack, world, living, a)->{
                if (stack.getItem() instanceof SunShardItem item){
                    return item.isHeated(stack) ? 1 : 0;
                }else{
                    return 0;
                }
            });

            ItemProperties.register(SCItems.ULDORADIUM_ORE.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SCBlocks.BLUE_GEM_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });


            ItemProperties.register(SCItems.SOLAR_STONE.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SCBlocks.SOLAR_STONE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SCItems.ENDER_CRACKS.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SCBlocks.ENDER_CRACKS.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SCItems.SOLAR_ORE_ITEM.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {

                    return Helpers.hasPlayerCompletedProgression(SCBlocks.SOLAR_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });

            ItemProperties.register(SCItems.SOLAR_GOD_BOW.get(),new ResourceLocation("solarcraft","pulling"),(stack, world, living, a)->{

                if (living != null){
                    return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
                }else{
                    return 0.0f;
                }
            });
        });
        registerDefaultUnknownBlockItemPredicate(SCItems.LENSING_CRYSTAL_ORE.get());
        registerDefaultUnknownBlockItemPredicate(SCItems.MAGISTONE.get());
        registerDefaultUnknownBlockItemPredicate(SCItems.CORRUPTED_SHARD_ORE.get());
        registerDefaultUnknownBlockItemPredicate(SCItems.CORRUPTED_SHARD_ORE_DEEPSLATE.get());

    }

    private static void registerDefaultUnknownBlockItemPredicate(ProgressionBlockItem item){
        ItemProperties.register(item,new ResourceLocation(SolarCraft.MOD_ID,"unlocked"),(stack,world,living,a)->{

            Player playerEntity = Minecraft.getInstance().player;
            if (playerEntity != null) {

                return Helpers.hasPlayerCompletedProgression( ((ProgressionBlock)item.getBlock()).getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
            }else{
                return 0;
            }
        });

    }

    @SubscribeEvent
    public static void registerEntityRendering(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(SCEntityTypes.METEORITE.get(), MeteoriteProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.SOLAR_STRIKE_ENTITY_REG.get(), SolarStrikeRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.SOLAR_DISC.get(), SolarDiscProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.BLOCK_BOOMERANG.get(), BlockBoomerangProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.ULTRA_CROSSBOW_SHOT.get(), UltraCrossbowProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.MORTAR_PROJECTILE.get(), MortarProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.TURRET_PROJECTILE.get(), AbstractTurretProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMasterRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.FALLING_BLOCK.get(), MyFallingBlockEntityRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.CRYSTAL_BOSS.get(), CrystalBossRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), ShieldingCrystalRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.CRYSTAL_BOSS_ATTACK_HOLDING_MISSILE.get(), HoldingMissileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.CRYSTAL_BOSS_MINE.get(), MineEntityRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.FALLING_MAGIC_MISSILE.get(), FallingMagicMissileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.RANDOM_BAD_EFFECT_PROJECTILE.get(), RandomBadEffectProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.RIP_RAY_GENERATOR.get(), RipRayGeneratorRender::new);
        event.registerEntityRenderer(SCEntityTypes.LEGENDARY_ITEM.get(), LegendaryItemRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.BALL_LIGHTNING.get(), BallLightningRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.RUNIC_ELEMENTAL_BOSS.get(), RunicElementalRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.SUNSTRIKE.get(), SunstrikeRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.EARTHQUAKE.get(), EarthquakeRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.REFRACTION_CRYSTAL.get(), RefractionCrystalRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.EXPLOSIVE_CRYSTAL.get(), ExplosiveCrystalRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.RUNIC_WARRIOR.get(), RunicWarriorRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.RUNIC_WARRIOR_ROCKET.get(), RunicWarriorSummoningRocketRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.SHADOW_BOLT.get(), ShadowBoltRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.SHADOW_ZOMBIE.get(), ShadowZombieRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.CORRUPTION_WISP.get(), CorruptionWispRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.SUMMONING_PROJECTILE.get(), NullRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.THROWN_LIGHT.get(), ThrownLightProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.ORBITAL_EXPLOSION.get(), OrbitalExplosionEntityRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.Projectiles.ORBITAL_EXPLOSION_PROJECTILE.get(), OrbitalExplosionProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.ULDERA_CRYSTAL_BOSS.get(), UlderaCrystalBossRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.Projectiles.HOMING_STAR.get(), HomingStarProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.ULDERA_LIGHTNING.get(), UlderaLightningRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.EFFECT_CRYSTAL.get(), EffectCrystalRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.SOLAR_GOD_BOW_PROJECTILE.get(), AbstractTurretProjectileRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.ELECTRIC_RAIN.get(), NullRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.Projectiles.ELECTRIC_SHOCK_PROJECTILE.get(), NullRenderer::new);
        event.registerEntityRenderer(SCEntityTypes.DUNGEON_RAY.get(), DungeonRayRenderer::new);
    }

}
