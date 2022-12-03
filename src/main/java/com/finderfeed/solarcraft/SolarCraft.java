package com.finderfeed.solarcraft;


import com.finderfeed.solarcraft.content.abilities.meteorite.MeteoriteProjectile;
import com.finderfeed.solarcraft.content.abilities.solar_strike.SolarStrikeEntity;
import com.finderfeed.solarcraft.content.abilities.SolarStunEffect;
import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.config.SolarcraftClientConfig;
import com.finderfeed.solarcraft.config.SolarcraftConfig;
import com.finderfeed.solarcraft.content.entities.*;
import com.finderfeed.solarcraft.content.entities.not_alive.*;

import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureTypes;
import com.finderfeed.solarcraft.events.PlayerTickEvent;
import com.finderfeed.solarcraft.events.RenderEventsHandler;
import com.finderfeed.solarcraft.content.blocks.SolarOreBlock;
import com.finderfeed.solarcraft.content.blocks.blockentities.clearing_ritual.clearing_ritual_crystal.corruption_wisp.CorruptionWisp;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.*;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStand;
import com.finderfeed.solarcraft.content.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarcraft.content.items.ProgressionBlockItem;
import com.finderfeed.solarcraft.content.items.solar_lexicon.unlockables.AncientFragment;
import com.finderfeed.solarcraft.client.particles.SolarcraftParticleTypes;
import com.finderfeed.solarcraft.registries.SolarcraftGamerules;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.data_serializers.FDEntityDataSerializers;
import com.finderfeed.solarcraft.registries.entities.SolarcraftEntityTypes;

import com.finderfeed.solarcraft.packet_handler.SolarCraftPacketHandler;
import com.finderfeed.solarcraft.registries.blocks.SolarcraftBlocks;
import com.finderfeed.solarcraft.registries.containers.SolarcraftContainers;
import com.finderfeed.solarcraft.registries.effects.SolarcraftEffects;
import com.finderfeed.solarcraft.registries.loot_modifiers.SolarcraftLootModifiers;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import com.finderfeed.solarcraft.registries.worldgen.configured.LazyConfiguredFeatures;
import com.finderfeed.solarcraft.registries.items.SolarcraftItems;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlock;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockEntity;


import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockItem;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeContainer;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeScreen;


import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureHolders;
import com.finderfeed.solarcraft.content.world_generation.BiomesRegister;
import com.finderfeed.solarcraft.content.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarcraft.content.world_generation.features.foliage_placers.FoliagePlacerRegistry;
import com.finderfeed.solarcraft.content.world_generation.features.trunk_placers.TrunkPlacersRegistry;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.common.TierSortingRegistry;

import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.List;

@Mod(SolarCraft.MOD_ID)
public class SolarCraft
{

    public static final int ENCHANTER_CONFIG_VERSION = 1;

    public static final String MOD_ID = "solarcraft";

    public  static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,"solarcraft");
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,"solarcraft");
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,"solarcraft");
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.MENU_TYPES,"solarcraft");
    public static final  DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,"solarcraft");
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"solarcraft");
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,"solarcraft");

    public static final CreativeModeTab SOLAR_GROUP = new SolarGroup("solar_forge_group");
    public static final CreativeModeTab SOLAR_GROUP_BLOCKS = new SolarGroupBlocks("solar_forge_group_blocks");
    public static final CreativeModeTab SOLAR_GROUP_TOOLS = new SolarGroupTools("solar_forge_group_tools");
    public static final CreativeModeTab SOLAR_GROUP_MATERIALS = new SolarGroupThemed("solar_group_materials", SolarcraftItems.ILLIDIUM_INGOT);
    public static final CreativeModeTab SOLAR_GROUP_WEAPONS = new SolarGroupThemed("solar_group_weapons", SolarcraftItems.ILLIDIUM_SWORD);
    public static final CreativeModeTab SOLAR_GROUP_FRAGMENTS = new SolarGroupFragments("solar_forge_group_fragments");


    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_SOUND = SOUND_EVENTS.register("solar_ray_sound",()-> new SoundEvent(new ResourceLocation("solarcraft","solar_strike_explosion_sound")));
    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_BUILD_SOUND = SOUND_EVENTS.register("solar_ray_buildup_sound",()-> new SoundEvent(new ResourceLocation("solarcraft","solar_strike_buildup")));

    public static final RegistryObject<EntityType<SolarStrikeEntity>> SOLAR_STRIKE_ENTITY_REG = ENTITY_TYPE_REGISTER.register("solar_strike_entity",
            ()->EntityType.Builder.of(SolarStrikeEntity::new, MobCategory.CREATURE).sized(0.5F,0.5F).build("solarcraft:solar_strike_entity"));

    public static final RegistryObject<MobEffect> SOLAR_STUN = EFFECTS.register("solar_stun",()-> new SolarStunEffect(MobEffectCategory.HARMFUL,0xd1b515));
    public  static  final  RegistryObject<SolarForgeBlock> SOLAR_FORGE = BLOCKS.register("solar_forge",()-> new SolarForgeBlock(BlockBehaviour.Properties.of(Material.STONE)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .strength(3,3)));

    public  static  final  RegistryObject<SolarOreBlock> SOLAR_ORE = BLOCKS.register("solar_ores",()-> new SolarOreBlock(BlockBehaviour.Properties.of(Material.STONE)
            .sound(SoundType.ANCIENT_DEBRIS)
            .requiresCorrectToolForDrops()
            .strength(3,3)));

    public  static  final  RegistryObject<InfuserBlock> SOLAR_INFUSER = BLOCKS.register("solar_infuser",
            ()-> new InfuserBlock(BlockBehaviour.Properties.of(Material.STONE)
            .sound(SoundType.METAL)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .dynamicShape()
            .strength(3,3)));

    public static final RegistryObject<BlockEntityType<InfuserTileEntity>> INFUSING_STAND_BLOCKENTITY = TILE_ENTITY_TYPE.register("infusing_stand_blockentity",()->
            BlockEntityType.Builder.of(InfuserTileEntity::new,SOLAR_INFUSER.get()).build(null));

    public static final RegistryObject<EntityType<MeteoriteProjectile>> METEORITE = ENTITY_TYPE_REGISTER.register("solar_forge_meteorite_projectile",()->EntityType.Builder.<MeteoriteProjectile>of(MeteoriteProjectile::new,MobCategory.MISC).sized(5,5).build("solar_forge_meteorite_projectile"));
//    public static  final RegistryObject<Item> SOLAR_SHARD = ITEMS.register("solar_shard",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP)));
//    public static  final RegistryObject<Item> SOLAR_FORGE_ITEM = ITEMS.register("solar_forge",()-> new SolarForgeBlockItem(SOLAR_FORGE.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP_BLOCKS).stacksTo(1)));
//    public static  final RegistryObject<Item> SOLAR_ORE_ITEM = ITEMS.register("solar_ores",()-> new ProgressionBlockItem(SOLAR_ORE.get(),new Item.Properties().tab(SOLAR_GROUP_BLOCKS)));
//    public static  final RegistryObject<Item> INFUSER_ITEM = ITEMS.register("solar_infuser",()-> new InfuserBlockItem(SOLAR_INFUSER.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP_BLOCKS).stacksTo(1),()-> AncientFragment.SOLAR_INFUSER));
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger("SOLARCRAFT");
    public static final RegistryObject<BlockEntityType<SolarForgeBlockEntity>> SOLAR_FORGE_BLOCKENTITY = TILE_ENTITY_TYPE.register("solar_forge_blockentity",()->
            BlockEntityType.Builder.of(SolarForgeBlockEntity::new,SOLAR_FORGE.get()).build(null));
    public static final RegistryObject<MenuType<SolarForgeContainer>> SOLAR_FORGE_CONTAINER = CONTAINER_TYPE.register("solarcraft_container",()-> IForgeMenuType.create(SolarForgeContainer::new));
    public static final RegistryObject<MenuType<InfuserContainer>> INFUSING_TABLE_CONTAINER = CONTAINER_TYPE.register("infusing_stand_container",()-> IForgeMenuType.create(InfuserContainer::new));

//    public static final RecipeType<InfusingCraftingRecipe> INFUSING_CRAFTING_RECIPE_TYPE = new InfusingCraftingRecipeType();
//    public static final RecipeType<InfusingRecipe> INFUSING_RECIPE_TYPE = new InfusingRecipeType();
//    public static final RecipeType<SolarSmeltingRecipe> SOLAR_SMELTING = new SolarSmeltingRecipeType();
    public SolarCraft() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SolarcraftParticleTypes.PARTICLES.register(bus);
        EFFECTS.register(bus);
        SOUND_EVENTS.register(bus);
        ENTITY_TYPE_REGISTER.register(bus);
//        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILE_ENTITY_TYPE.register(bus);
        CONTAINER_TYPE.register(bus);
        SolarcraftItems.ITEMS.register(bus);
        SolarcraftBlocks.BLOCKS.register(bus);
        SolarcraftTileEntityTypes.TILE_ENTITY_TYPE.register(bus);
        SolarcraftEntityTypes.ENTITY_TYPE_REGISTER.register(bus);
        SolarcraftEffects.EFFECTS.register(bus);
        SolarcraftSounds.SOUND_EVENTS.register(bus);
        SolarcraftContainers.CONTAINER_TYPE.register(bus);
        FoliagePlacerRegistry.DEFERRED_REGISTER.register(bus);
        AttributesRegistry.DEF_REG.register(bus);
        FDEntityDataSerializers.DEF_REG.register(bus);
        BiomesRegister.BIOMES.register(bus);
        FeaturesRegistry.FEATURES.register(bus);
        SolarcraftRecipeTypes.RECIPE_SERIALIZERS.register(bus);
        SolarcraftRecipeTypes.RECIPE_TYPES.register(bus);
        SolarcraftLootModifiers.MODIFIERS.register(bus);
        SolarcraftGamerules.init();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(TrunkPlacersRegistry::registerTrunkPlacerTypes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(LazyConfiguredFeatures::registerConfiguredFeatures);




//        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class,EventPriority.HIGHEST,FeaturesRegistry::registerFeatures);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(FeaturesRegistry::addCarvableBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(FeaturesRegistry::registerConfiguredFeatures);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SolarcraftConfig.SPEC,"solarcraft-config.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SolarcraftClientConfig.SPEC,"solarcraft-client-config.toml");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        SolarCraftPacketHandler.registerMessages();
//        SolarForgeStructures.STRUCTURES.register(bus);

    }



    private void setup(final FMLCommonSetupEvent event)
    {
        JsonFragmentsHelper.setupJSON();
        EnchanterConfigInit.setupJSON();
        Tags.init();
        AbilitiesRegistry.init();
        TierSortingRegistry.registerTier(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,new ResourceLocation("illidium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new ResourceLocation("qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new ResourceLocation("charged_qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,new ResourceLocation("solar_god"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.DIVINE_TIER,new ResourceLocation("divine"), List.of(Tiers.DIAMOND),List.of());

        MinecraftForge.EVENT_BUS.register(new PlayerTickEvent());

        MinecraftForge.EVENT_BUS.addListener(InfusingStand::placeBlockEvent);
        event.enqueueWork(()->{
//            SolarcraftStructureHolders.registerConfiguredStructures();
            SolarcraftStructureTypes.init();
            SolarcraftStructureHolders.init();
        });

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderEventsHandler());
        MenuScreens.register(SOLAR_FORGE_CONTAINER.get(), SolarForgeScreen::new);
        MenuScreens.register(INFUSING_TABLE_CONTAINER.get(), InfuserScreen::new);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {


        @SubscribeEvent
        public static void entityAttributes(EntityAttributeCreationEvent event) {
            event.put(SOLAR_STRIKE_ENTITY_REG.get(),SolarStrikeEntity.createAttributes().build());
            event.put(SolarcraftEntityTypes.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMaster.createAttributes().build());
            event.put(SolarcraftEntityTypes.CRYSTAL_BOSS.get(), CrystalBossEntity.createAttributes().build());
            event.put(SolarcraftEntityTypes.RUNIC_ELEMENTAL_BOSS.get(), RunicElementalBoss.createAttributes().build());
            event.put(SolarcraftEntityTypes.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), ShieldingCrystalCrystalBoss.createAttributes().build());
            event.put(SolarcraftEntityTypes.CRYSTAL_BOSS_MINE.get(), MineEntityCrystalBoss.createAttributes().build());
            event.put(SolarcraftEntityTypes.RIP_RAY_GENERATOR.get(), RipRayGenerator.createAttributes().build());
            event.put(SolarcraftEntityTypes.REFRACTION_CRYSTAL.get(), RefractionCrystal.createAttributes().build());
            event.put(SolarcraftEntityTypes.EXPLOSIVE_CRYSTAL.get(), ExplosiveCrystal.createAttributes().build());
            event.put(SolarcraftEntityTypes.RUNIC_WARRIOR.get(), RunicWarrior.createAttributes().build());
            event.put(SolarcraftEntityTypes.SHADOW_ZOMBIE.get(), ShadowZombie.createAttributes().build());
            event.put(SolarcraftEntityTypes.CORRUPTION_WISP.get(), CorruptionWisp.createAttributes().build());
        }

//        @SubscribeEvent
//        public static void registerRecipeSerializers(RegisterEvent event) {
//            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(INFUSING_CRAFTING_RECIPE_TYPE.toString()), INFUSING_CRAFTING_RECIPE_TYPE);
//            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(INFUSING_RECIPE_TYPE.toString()), INFUSING_RECIPE_TYPE);
//            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(SOLAR_SMELTING.toString()), SOLAR_SMELTING);
//
////            event.getForgeRegistry().register(InfusingCraftingRecipe.serializer);
////            event.getForgeRegistry().register(InfusingRecipe.serializer);
////            event.getForgeRegistry().register(SolarSmeltingRecipe.serializer);
//        }
    }
}
