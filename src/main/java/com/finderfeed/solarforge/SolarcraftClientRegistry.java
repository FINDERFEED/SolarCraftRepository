package com.finderfeed.solarforge;

import com.finderfeed.solarforge.content.abilities.meteorite.MeteoriteProjectileRenderer;
import com.finderfeed.solarforge.content.abilities.solar_strike.SolarStrikeRenderer;
import com.finderfeed.solarforge.client.rendering.rendertypes.SolarCraftRenderTypes;
import com.finderfeed.solarforge.content.entities.projectiles.renderers.*;
import com.finderfeed.solarforge.content.entities.renderers.*;
import com.finderfeed.solarforge.helpers.Helpers;
import com.finderfeed.solarforge.local_library.client.particles.ScreenParticlesRenderHandler;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.ClearingRitualCrystalRenderer;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp.CorruptionWispRenderer;
import com.finderfeed.solarforge.content.blocks.blockentities.clearing_ritual.clearing_ritual_main_tile.ClearingRitualTileRenderer;
import com.finderfeed.solarforge.content.blocks.blockentities.containers.screens.*;
import com.finderfeed.solarforge.content.blocks.blockentities.projectiles.renderers.ShadowBoltRenderer;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.InfuserRenderer;
import com.finderfeed.solarforge.content.blocks.infusing_table_things.infusing_pool.InfusingStandRenderer;
import com.finderfeed.solarforge.content.blocks.blockentities.projectiles.renderers.AbstractTurretProjectileRenderer;
import com.finderfeed.solarforge.content.blocks.blockentities.projectiles.renderers.MortarProjectileRenderer;
import com.finderfeed.solarforge.content.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarforge.content.blocks.render.*;
import com.finderfeed.solarforge.content.items.ProgressionBlockItem;
import com.finderfeed.solarforge.content.items.solar_disc_gun.SolarDiscProjectileRenderer;
import com.finderfeed.solarforge.content.runic_network.repeater.RepeaterRenderer;
import com.finderfeed.solarforge.registries.ScreenSuppliers;
import com.finderfeed.solarforge.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarforge.registries.containers.SolarcraftContainers;
import com.finderfeed.solarforge.registries.entities.SolarcraftEntityTypes;
import com.finderfeed.solarforge.registries.items.SolarcraftItems;
import com.finderfeed.solarforge.registries.tile_entities.SolarcraftTileEntityTypes;
import com.finderfeed.solarforge.content.blocks.solar_forge_block.SolarForgeBlockEntityRenderer;
import com.finderfeed.solarforge.content.items.solar_lexicon.SolarLexiconContScreen;
import com.finderfeed.solarforge.client.rendering.RadiantTextureAtlasSpriteLoader;
import com.finderfeed.solarforge.content.world_generation.dimension_related.radiant_land.RadiantLandDimEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.DimensionSpecialEffects;
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
public class SolarcraftClientRegistry {

    public static final DimensionSpecialEffects RADIANT_LAND  = new RadiantLandDimEffects();
    public static final RadiantTextureAtlasSpriteLoader RADIANT_TEXTURE_ATLAS_SPRITE_LOADER = new RadiantTextureAtlasSpriteLoader();
    public static final KeyMapping FIRST_ABILITY_KEY = new KeyMapping("key.fire_ability_one", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_H,"key.solarforge.category");
    public static final KeyMapping SECOND_ABILITY_KEY = new KeyMapping("key.fire_ability_two", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_J,"key.solarforge.category");
    public static final KeyMapping THIRD_ABILITY_KEY = new KeyMapping("key.fire_ability_three", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_S,"key.solarforge.category");
    public static final KeyMapping FORTH_ABILITY_KEY = new KeyMapping("key.fire_ability_four", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
//    public static final KeyMapping OPEN_GUI_ABILITY_KEY = new KeyMapping("key.gui_button_solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
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
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.INFUSING_POOL.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.SOLAR_LENS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.BONEMEALER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.SOLAR_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.ASH_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.RUNIC_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.DEAD_SPROUT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.RADIANT_LEAVES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.RADIANT_GRASS_NOT_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.RADIANT_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.RADIANT_BERRY_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.RADIANT_CRYSTAL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.CRYSTAL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.EXPLOSION_BLOCKER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.CRYSTAL_FLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.VOID_LILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(SolarcraftBlocks.RUNIC_TREE_SAPLING.get(), RenderType.cutout());

        ClientRegistry.registerKeyBinding(FIRST_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(SECOND_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(THIRD_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(FORTH_ABILITY_KEY);
//        ClientRegistry.registerKeyBinding(OPEN_GUI_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(GUI_ABILITY_BUY_SCREEN);





        //blockentityrenderers.register
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.REPEATER.get(), RepeaterRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.RUNE_ENERGY_PYLON.get(), RuneEnergyPylonRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.SAVANNA_DUNGEON_KEEPER.get(), SavannaDungeonKeeperRenderer::new);
        BlockEntityRenderers.register(SolarForge.SOLAR_FORGE_BLOCKENTITY.get(), SolarForgeBlockEntityRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.INFUSING_POOL_BLOCKENTITY.get(), InfusingStandRenderer::new);
        BlockEntityRenderers.register(SolarForge.INFUSING_STAND_BLOCKENTITY.get(), InfuserRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.SOLAR_REPEATER.get(), SolarRepeaterRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.ENERGY_GENERATOR_TILE.get(), EnergyGeneratorTileRender::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.SOLAR_CORE_TILE.get(), SolarCoreRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.AURA_HEALER_TILE.get(), AuraHealerRenderer::new);
        BlockEntityRenderers.register(SolarcraftTileEntityTypes.RAY_TRAP_TILE_ENTITY.get(), RayTrapTileEntityRenderer::new);
//        BlockEntityRenderers.register(SolarcraftTileEntityTypes.PORTAL_CREATOR.get(), PortalCreatorRenderer::new);
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


        MenuScreens.register(SolarcraftContainers.SOLAR_FURNACE_CONTAINER.get(), SolarFurnaceScreen::new);
        MenuScreens.register(SolarcraftContainers.RUNIC_TABLE_CONTAINER.get(), RunicTableContainerScreen::new);
        MenuScreens.register(SolarcraftContainers.SOLAR_LEXICON_CONTAINER.get(), SolarLexiconContScreen::new);
        MenuScreens.register(SolarcraftContainers.MODULE_APPLIER_CONTAINER.get(), ModuleApplierScreen::new);
        MenuScreens.register(SolarcraftContainers.INFUSING_TABLE_TILE.get(), InfusingTableScreen::new);
        MenuScreens.register(SolarcraftContainers.ENCHANTER.get(), EnchanterContainerScreen::new);
        MenuScreens.register(SolarcraftContainers.RUNIC_ENERGY_CHARGER.get(), RunicEnergyChargerScreen::new);

        event.enqueueWork(()->{
            DimensionSpecialEffects.EFFECTS.put(new ResourceLocation("solarforge","radiant_land"),RADIANT_LAND);
            ItemProperties.register(SolarcraftItems.ULDORADIUM_ORE.get(),new ResourceLocation("solarforge","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SolarcraftBlocks.ULDORADIUM_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SolarcraftItems.SOLAR_STONE.get(),new ResourceLocation("solarforge","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SolarcraftBlocks.SOLAR_STONE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SolarcraftItems.ENDER_CRACKS.get(),new ResourceLocation("solarforge","unlocked"),(stack, world, living, a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {
                    return Helpers.hasPlayerCompletedProgression(SolarcraftBlocks.ENDER_CRACKS.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });
            ItemProperties.register(SolarForge.SOLAR_ORE_ITEM.get(),new ResourceLocation("solarforge","unlocked"),(stack,world,living,a)->{

                Player playerEntity = Minecraft.getInstance().player;
                if (playerEntity != null) {

                    return Helpers.hasPlayerCompletedProgression(SolarForge.SOLAR_ORE.get().getRequiredProgression(), Minecraft.getInstance().player) ? 1f : 0;
                }else{
                    return 0;
                }
            });

            ItemProperties.register(SolarcraftItems.SOLAR_GOD_BOW.get(),new ResourceLocation("solarforge","pulling"),(stack, world, living, a)->{

                if (living != null){
                    return living.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - living.getUseItemRemainingTicks()) / 20.0F;
                }else{
                    return 0.0f;
                }
            });
        });
        registerDefaultUnknownBlockItemPredicate(SolarcraftItems.LENSING_CRYSTAL_ORE.get());

    }

    private static void registerDefaultUnknownBlockItemPredicate(ProgressionBlockItem item){
        ItemProperties.register(item,new ResourceLocation("solarforge","unlocked"),(stack,world,living,a)->{

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
        event.registerEntityRenderer(SolarForge.METEORITE.get(), MeteoriteProjectileRenderer::new);
        event.registerEntityRenderer(SolarForge.SOLAR_STRIKE_ENTITY_REG.get(), SolarStrikeRenderer::new);
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
