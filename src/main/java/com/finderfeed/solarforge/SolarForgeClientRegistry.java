package com.finderfeed.solarforge;

import com.finderfeed.solarforge.SolarAbilities.meteorite.MeteoriteProjectileRenderer;
import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntityRender;
import com.finderfeed.solarforge.entities.renderers.*;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens.InfusingTableScreen;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens.ModuleApplierScreen;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.InfuserRenderer;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingStandRenderer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens.RunicTableContainerScreen;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.containers.screens.SolarFurnaceScreen;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers.AbstractTurretProjectileRenderer;
import com.finderfeed.solarforge.magic_items.blocks.blockentities.projectiles.renderers.MortarProjectileRenderer;
import com.finderfeed.solarforge.magic_items.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarforge.magic_items.blocks.render.*;
import com.finderfeed.solarforge.magic_items.items.ProgressionBlockItem;
import com.finderfeed.solarforge.magic_items.items.projectiles.renderers.*;
import com.finderfeed.solarforge.magic_items.items.solar_disc_gun.SolarDiscProjectileRenderer;
import com.finderfeed.solarforge.magic_items.runic_network.repeater.RepeaterRenderer;
import com.finderfeed.solarforge.registries.ScreenSuppliers;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.entities.Entities;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntityRenderer;
import com.finderfeed.solarforge.magic_items.items.solar_lexicon.SolarLexiconContScreen;
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
import net.minecraft.world.item.Item;
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
    public static final KeyMapping ADMIN_ABILITY_KEY = new KeyMapping("key.admin_button_solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyMapping OPEN_GUI_ABILITY_KEY = new KeyMapping("key.gui_button_solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");
    public static final KeyMapping GUI_ABILITY_BUY_SCREEN = new KeyMapping("key.ability_buy_screen.solarforge", KeyConflictContext.UNIVERSAL, InputConstants.Type.SCANCODE, GLFW.GLFW_KEY_D,"key.solarforge.category");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerTest(TextureStitchEvent.Pre event){
        MinecraftForgeClient.registerTextureAtlasSpriteLoader(RadiantTextureAtlasSpriteLoader.REGISTRY_ID,RADIANT_TEXTURE_ATLAS_SPRITE_LOADER);
    }


    @SubscribeEvent
    public static void registerClientStuff(final FMLClientSetupEvent event){

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
        ItemBlockRenderTypes.setRenderLayer(BlocksRegistry.CRYSTAL_FLOWER.get(), RenderType.cutout());

        ClientRegistry.registerKeyBinding(FIRST_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(SECOND_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(THIRD_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(FORTH_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(OPEN_GUI_ABILITY_KEY);
        ClientRegistry.registerKeyBinding(ADMIN_ABILITY_KEY);
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

        MenuScreens.register(Containers.SOLAR_FURNACE_CONTAINER.get(), SolarFurnaceScreen::new);
        MenuScreens.register(Containers.RUNIC_TABLE_CONTAINER.get(), RunicTableContainerScreen::new);
        MenuScreens.register(Containers.SOLAR_LEXICON_CONTAINER.get(), SolarLexiconContScreen::new);
        MenuScreens.register(Containers.MODULE_APPLIER_CONTAINER.get(), ModuleApplierScreen::new);
        MenuScreens.register(Containers.INFUSING_TABLE_TILE.get(), InfusingTableScreen::new);

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
        event.registerEntityRenderer(SolarForge.SOLAR_STRIKE_ENTITY_REG.get(), SolarStrikeEntityRender::new);
        event.registerEntityRenderer(Entities.SOLAR_DISC.get(), SolarDiscProjectileRenderer::new);
        event.registerEntityRenderer(Entities.BLOCK_BOOMERANG.get(), BlockBoomerangProjectileRenderer::new);
        event.registerEntityRenderer(Entities.ULTRA_CROSSBOW_SHOT.get(), UltraCrossbowProjectileRenderer::new);
        event.registerEntityRenderer(Entities.MORTAR_PROJECTILE.get(), MortarProjectileRenderer::new);
        event.registerEntityRenderer(Entities.TURRET_PROJECTILE.get(), AbstractTurretProjectileRenderer::new);
        event.registerEntityRenderer(Entities.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMasterRenderer::new);
        event.registerEntityRenderer(Entities.FALLING_BLOCK.get(), MyFallingBlockEntityRenderer::new);
        event.registerEntityRenderer(Entities.CRYSTAL_BOSS.get(), CrystalBossRenderer::new);
        event.registerEntityRenderer(Entities.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), ShieldingCrystalRenderer::new);
        event.registerEntityRenderer(Entities.CRYSTAL_BOSS_ATTACK_HOLDING_MISSILE.get(), HoldingMissileRenderer::new);
        event.registerEntityRenderer(Entities.CRYSTAL_BOSS_MINE.get(), MineEntityRenderer::new);
        event.registerEntityRenderer(Entities.FALLING_STAR_CRYSTAL_BOSS.get(), FallingStarRenderer::new);
        event.registerEntityRenderer(Entities.RANDOM_BAD_EFFECT_PROJECTILE.get(), RandomBadEffectProjectileRenderer::new);
        event.registerEntityRenderer(Entities.RIP_RAY_GENERATOR.get(), RipRayGeneratorRender::new);
        event.registerEntityRenderer(Entities.LEGENDARY_ITEM.get(), LegendaryItemRenderer::new);
    }

}
