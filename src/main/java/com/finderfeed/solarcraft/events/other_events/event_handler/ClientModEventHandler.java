package com.finderfeed.solarcraft.events.other_events.event_handler;

import com.finderfeed.solarcraft.SolarCraft;
import com.finderfeed.solarcraft.client.rendering.RadiantTextureAtlasSpriteLoader;
import com.finderfeed.solarcraft.client.rendering.rendertypes.SolarCraftRenderTypes;
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
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStandRenderer;
import com.finderfeed.solarcraft.content.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarcraft.content.blocks.render.*;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockEntityRenderer;
import com.finderfeed.solarcraft.content.entities.CrystalBossBar;
import com.finderfeed.solarcraft.content.entities.projectiles.renderers.*;
import com.finderfeed.solarcraft.content.entities.renderers.*;
import com.finderfeed.solarcraft.content.entities.runic_elemental.RunicElementalBossBar;
import com.finderfeed.solarcraft.content.items.ProgressionBlockItem;
import com.finderfeed.solarcraft.content.items.SunShardItem;
import com.finderfeed.solarcraft.content.items.solar_disc_gun.SolarDiscProjectileRenderer;
import com.finderfeed.solarcraft.content.items.solar_lexicon.SolarLexiconContScreen;
import com.finderfeed.solarcraft.content.runic_network.repeater.RepeaterRenderer;
import com.finderfeed.solarcraft.content.world_generation.dimension_related.radiant_land.RadiantLandDimEffects;
import com.finderfeed.solarcraft.helpers.Helpers;
import com.finderfeed.solarcraft.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarcraft.registries.ScreenSuppliers;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.containers.SolarcraftContainers;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.overlays.SolarcraftOverlays;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;



@Mod.EventBusSubscriber(modid = "solarcraft",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientModEventHandler {

    public static final DimensionSpecialEffects RADIANT_LAND  = new RadiantLandDimEffects();
    public static final RadiantTextureAtlasSpriteLoader RADIANT_TEXTURE_ATLAS_SPRITE_LOADER = new RadiantTextureAtlasSpriteLoader();
    public static final KeyMapping FIRST_ABILITY_KEY = new KeyMapping("key.fire_ability_one", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_H,"key.solarcraft.category");
    public static final KeyMapping SECOND_ABILITY_KEY = new KeyMapping("key.fire_ability_two", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_J,"key.solarcraft.category");
    public static final KeyMapping THIRD_ABILITY_KEY = new KeyMapping("key.fire_ability_three", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_S,"key.solarcraft.category");
    public static final KeyMapping FORTH_ABILITY_KEY = new KeyMapping("key.fire_ability_four", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarcraft.category");
    public static final KeyMapping GUI_ABILITY_BUY_SCREEN = new KeyMapping("key.ability_buy_screen.solarcraft", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarcraft.category");
    public static final KeyMapping GUI_WAND_MODE_SELECTION = new KeyMapping("key.wand_mode_selection_screen", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_K,"key.solarcraft.category");


    @SubscribeEvent
    public static void registerTooltips(RegisterClientTooltipComponentFactoriesEvent event){
        event.register(RETooltipComponent.class, REClientTooltipComponent::new);
    }


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerTest(RegisterTextureAtlasSpriteLoadersEvent event){
        event.register("radiant_loader",RADIANT_TEXTURE_ATLAS_SPRITE_LOADER);
    }


    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event){
        event.register(FIRST_ABILITY_KEY);
        event.register(SECOND_ABILITY_KEY);
        event.register(THIRD_ABILITY_KEY);
        event.register(FORTH_ABILITY_KEY);
        event.register(GUI_ABILITY_BUY_SCREEN);
        event.register(GUI_WAND_MODE_SELECTION);
    }

    @SubscribeEvent
    public static void registerDimensionEffects(RegisterDimensionSpecialEffectsEvent event){
        event.register(new ResourceLocation("solarcraft","radiant_land"),RADIANT_LAND);
    }

    @SubscribeEvent
    public static void registerClientStuff(final FMLClientSetupEvent event){

        ScreenParticlesRenderHandler.registerRenderType(SolarCraftRenderTypes.ParticleRenderTypes.RUNE_TILE_PARTICLE);
        ScreenParticlesRenderHandler.registerRenderType(SolarCraftRenderTypes.ParticleRenderTypes.SOLAR_STRIKE_PARTICLE_SCREEN);

        ScreenSuppliers.SCREEN_REGISTRY.registerAll();

        SolarcraftOverlays.BossBars.registerCustomBossBar("runic_elemental",new RunicElementalBossBar());
        SolarcraftOverlays.BossBars.registerCustomBossBar("defense_crystal",new CrystalBossBar());

        BlockEntityRenderers.register(SolarcraftTileEntityTypes.RUNIC_ENERGY_REPEATER.get(), RepeaterRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.RUNE_ENERGY_PYLON.get(), RuneEnergyPylonRenderer::new);
        BlockEntityRenderers.register(SolarCraft.SOLAR_FORGE_BLOCKENTITY.get(), SolarForgeBlockEntityRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.INFUSING_POOL_BLOCKENTITY.get(), InfusingStandRenderer::new);
        BlockEntityRenderers.register(SolarCraft.INFUSING_STAND_BLOCKENTITY.get(), InfuserRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.SOLAR_REPEATER.get(), SolarRepeaterRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.ENERGY_GENERATOR_TILE.get(), EnergyGeneratorTileRender::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.SOLAR_CORE_TILE.get(), SolarCoreRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.AURA_HEALER_TILE.get(), AuraHealerRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.RAY_TRAP_TILE_ENTITY.get(), RayTrapTileEntityRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.WORMHOLE.get(), WormholeRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.BONEMEALER.get(), BonemealerRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.INFUSING_CRAFTING_TABLE.get(), InfusingTableTileRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.EXPLOSTION_BLOCKER.get(), ExplosionBlockerRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.ENCHANTER.get(), EnchanterRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.RUNIC_ENERGY_CHARGER.get(), RunicEnergyChargerRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.ULDERA_PYLON.get(), UlderaPylonRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.CLEARING_RITUAL_CRYSTAL.get(), ClearingRitualCrystalRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.CLEARING_RITUAL_MAIN_BLOCK.get(), ClearingRitualTileRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.DIMENSION_CORE_TILE.get(), DimensionCoreRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.SAVANNA_DUNGEON_KEEPER.get(), SavannaDungeonKeeperRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.RUNIC_ENERGY_CORE.get(), RunicEnergyCoreRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.BEAM_GENERATOR.get(), BeamGeneratorRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.BEAM_REFLECTOR.get(), BeamReflectorRenderer::new);


        MenuScreens.register(SolarcraftContainers.SOLAR_FURNACE_CONTAINER.get(), SolarFurnaceScreen::new);
        MenuScreens.register(SolarcraftContainers.RUNIC_TABLE_CONTAINER.get(), RunicTableContainerScreen::new);
        MenuScreens.register(SolarcraftContainers.SOLAR_LEXICON_CONTAINER.get(), SolarLexiconContScreen::new);
        MenuScreens.register(SolarcraftContainers.MODULE_APPLIER_CONTAINER.get(), ModuleApplierScreen::new);
        MenuScreens.register(SolarcraftContainers.INFUSING_TABLE_TILE.get(), InfusingTableScreen::new);
        MenuScreens.register(SolarcraftContainers.ENCHANTER.get(), EnchanterContainerScreen::new);
        MenuScreens.register(SolarcraftContainers.RUNIC_ENERGY_CHARGER.get(), RunicEnergyChargerScreen::new);

        event.enqueueWork(()->{

            ItemProperties.register(SolarcraftItems.SUN_SHARD.get(),new ResourceLocation("solarcraft","heated_up"),(stack, world, living, a)->{
                if (stack.getItem() instanceof SunShardItem item){
                    return item.isHeated(stack) ? 1 : 0;
                }else{
                    return 0;
                }
            });

            ItemProperties.register(SolarcraftItems.ULDORADIUM_ORE.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SolarcraftBlocks.ULDORADIUM_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });


            ItemProperties.register(SolarcraftItems.SOLAR_STONE.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SolarcraftBlocks.SOLAR_STONE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SolarcraftItems.ENDER_CRACKS.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SolarcraftBlocks.ENDER_CRACKS.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SolarcraftItems.SOLAR_ORE_ITEM.get(),new ResourceLocation("solarcraft","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {

                    return Helpers.hasPlayerCompletedProgression(SolarCraft.SOLAR_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });

            ItemProperties.register(SolarcraftItems.SOLAR_GOD_BOW.get(),new ResourceLocation("solarcraft","pulling"),(stack, world, living, a)->{

                if (living != null){
                    return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
                }else{
                    return 0.0f;
                }
            });
        });
        registerDefaultUnknownBlockItemPredicate(SolarcraftItems.LENSING_CRYSTAL_ORE.get());
        registerDefaultUnknownBlockItemPredicate(SolarcraftItems.MAGISTONE.get());

    }

    private static void registerDefaultUnknownBlockItemPredicate(ProgressionBlockItem item){
        ItemProperties.register(item,new ResourceLocation("solarcraft","unlocked"),(stack,world,living,a)->{

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
        event.registerEntityRenderer(SolarCraft.METEORITE.get(), MeteoriteProjectileRenderer::new);
        event.registerEntityRenderer(SolarCraft.SOLAR_STRIKE_ENTITY_REG.get(), SolarStrikeRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.SOLAR_DISC.get(), SolarDiscProjectileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.BLOCK_BOOMERANG.get(), BlockBoomerangProjectileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.ULTRA_CROSSBOW_SHOT.get(), UltraCrossbowProjectileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.MORTAR_PROJECTILE.get(), MortarProjectileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.TURRET_PROJECTILE.get(), AbstractTurretProjectileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMasterRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.FALLING_BLOCK.get(), MyFallingBlockEntityRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.CRYSTAL_BOSS.get(), CrystalBossRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), ShieldingCrystalRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.CRYSTAL_BOSS_ATTACK_HOLDING_MISSILE.get(), HoldingMissileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.CRYSTAL_BOSS_MINE.get(), MineEntityRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.FALLING_MAGIC_MISSILE.get(), FallingMagicMissileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.RANDOM_BAD_EFFECT_PROJECTILE.get(), RandomBadEffectProjectileRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.RIP_RAY_GENERATOR.get(), RipRayGeneratorRender::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.LEGENDARY_ITEM.get(), LegendaryItemRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.BALL_LIGHTNING.get(), BallLightningRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.RUNIC_ELEMENTAL_BOSS.get(), RunicElementalRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.SUNSTRIKE.get(), SunstrikeRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.EARTHQUAKE.get(), EarthquakeRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.SOLAR_FIREBALL.get(), SolarFireballRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.REFRACTION_CRYSTAL.get(), RefractionCrystalRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.EXPLOSIVE_CRYSTAL.get(), ExplosiveCrystalRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.RUNIC_WARRIOR.get(), RunicWarriorRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.RUNIC_WARRIOR_ROCKET.get(), RunicWarriorSummoningRocketRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.SHADOW_BOLT.get(), ShadowBoltRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.SHADOW_ZOMBIE.get(), ShadowZombieRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.CORRUPTION_WISP.get(), CorruptionWispRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.SUMMONING_PROJECTILE.get(), NullRenderer::new);
        event.registerEntityRenderer(SolarcraftEntityTypes.THROWN_LIGHT.get(), ThrownLightProjectileRenderer::new);
    }

}
