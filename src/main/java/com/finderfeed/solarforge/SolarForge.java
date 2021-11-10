package com.finderfeed.solarforge;


import com.finderfeed.solarforge.SolarAbilities.meteorite.MeteoriteProjectile;
import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntity;
import com.finderfeed.solarforge.SolarAbilities.SolarStunEffect;
import com.finderfeed.solarforge.capabilities.capability_mana.AttachManaCapabilityEvent;
import com.finderfeed.solarforge.config.SolarcraftConfig;
import com.finderfeed.solarforge.entities.*;
import com.finderfeed.solarforge.events.PlayerTickEvent;
import com.finderfeed.solarforge.events.RenderEventsHandler;
import com.finderfeed.solarforge.magic_items.blocks.SolarOreBlock;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.*;
import com.finderfeed.solarforge.magic_items.blocks.infusing_table_things.infusing_pool.InfusingPool;
import com.finderfeed.solarforge.magic_items.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarforge.magic_items.items.ProgressionBlockItem;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.multiblocks.Multiblocks;
import com.finderfeed.solarforge.recipe_types.infusing_crafting.InfusingCraftingRecipe;
import com.finderfeed.solarforge.registries.Tags;
import com.finderfeed.solarforge.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarforge.registries.attributes.AttributesRegistry;
import com.finderfeed.solarforge.registries.entities.Entities;
import com.finderfeed.solarforge.client.rendering.on_screen_rendering.TestRenderEvent;
import com.finderfeed.solarforge.packet_handler.SolarForgePacketHandler;
import com.finderfeed.solarforge.recipe_types.InfusingRecipe;
import com.finderfeed.solarforge.recipe_types.InfusingRecipeType;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipe;
import com.finderfeed.solarforge.recipe_types.solar_smelting.SolarSmeltingRecipeType;
import com.finderfeed.solarforge.registries.blocks.BlocksRegistry;
import com.finderfeed.solarforge.registries.containers.Containers;
import com.finderfeed.solarforge.registries.effects.EffectsRegister;
import com.finderfeed.solarforge.registries.features.FeaturesSolarforge;
import com.finderfeed.solarforge.registries.features.configured.ConfiguredFeatures;
import com.finderfeed.solarforge.registries.items.ItemsRegister;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlock;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockEntity;


import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.SolarForgeBlockItem;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen.SolarForgeContainer;
import com.finderfeed.solarforge.magic_items.blocks.solar_forge_block.solar_forge_screen.SolarForgeScreen;


import com.finderfeed.solarforge.world_generation.structures.SolarForgeStructureFeatures;
import com.finderfeed.solarforge.world_generation.structures.SolarForgeStructures;
import com.finderfeed.solarforge.world_generation.BiomesRegister;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarforge.world_generation.features.foliage_placers.FoliagePlacerRegistry;
import com.finderfeed.solarforge.world_generation.features.trunk_placers.TrunkPlacersRegistry;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fmlserverevents.FMLServerStartingEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.List;

@Mod(SolarForge.MOD_ID)
public class SolarForge
{

    public static final String MOD_ID = "solarforge";

    public  static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,"solarforge");
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,"solarforge");
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,"solarforge");
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS,"solarforge");
    public static final  DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,"solarforge");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"solarforge");
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES,"solarforge");

    public static final CreativeModeTab SOLAR_GROUP = new SolarGroup("solar_forge_group");
    public static final CreativeModeTab SOLAR_GROUP_BLOCKS = new SolarGroupBlocks("solar_forge_group_blocks");
    public static final CreativeModeTab SOLAR_GROUP_TOOLS = new SolarGroupTools("solar_forge_group_tools");
    public static final CreativeModeTab SOLAR_GROUP_MATERIALS = new SolarGroupThemed("solar_group_materials",ItemsRegister.ILLIDIUM_INGOT);
    public static final CreativeModeTab SOLAR_GROUP_WEAPONS = new SolarGroupThemed("solar_group_weapons",ItemsRegister.ILLIDIUM_SWORD);
    public static final CreativeModeTab SOLAR_GROUP_FRAGMENTS = new SolarGroupFragments("solar_forge_group_fragments");


    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_SOUND = SOUND_EVENTS.register("solar_ray_sound",()-> new SoundEvent(new ResourceLocation("solarforge","solar_strike_explosion_sound")));
    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_BUILD_SOUND = SOUND_EVENTS.register("solar_ray_buildup_sound",()-> new SoundEvent(new ResourceLocation("solarforge","solar_strike_buildup")));

    public static final RegistryObject<EntityType<SolarStrikeEntity>> SOLAR_STRIKE_ENTITY_REG = ENTITY_TYPE_REGISTER.register("solar_strike_entity",
            ()->EntityType.Builder.of(SolarStrikeEntity::new, MobCategory.CREATURE).sized(0.5F,0.5F).build("solarforge:solar_strike_entity"));

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
    public static  final RegistryObject<Item> TEST_ITEM = ITEMS.register("solar_shard",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP)));
    public static  final RegistryObject<Item> SOLAR_FORGE_ITEM = ITEMS.register("solar_forge",()-> new SolarForgeBlockItem(SOLAR_FORGE.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP_BLOCKS).stacksTo(1)));
    public static  final RegistryObject<Item> SOLAR_ORE_ITEM = ITEMS.register("solar_ores",()-> new ProgressionBlockItem(SOLAR_ORE.get(),new Item.Properties().tab(SOLAR_GROUP_BLOCKS)));
    public static  final RegistryObject<Item> INFUSING_STAND_ITEM = ITEMS.register("solar_infuser",()-> new InfuserBlockItem(SOLAR_INFUSER.get(),new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP_BLOCKS).stacksTo(1)));
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final RegistryObject<BlockEntityType<SolarForgeBlockEntity>> SOLAR_FORGE_BLOCKENTITY = TILE_ENTITY_TYPE.register("solar_forge_blockentity",()->
            BlockEntityType.Builder.of(SolarForgeBlockEntity::new,SOLAR_FORGE.get()).build(null));
    public static final RegistryObject<MenuType<SolarForgeContainer>> SOLAR_FORGE_CONTAINER = CONTAINER_TYPE.register("solarforge_container",()-> IForgeContainerType.create(SolarForgeContainer::new));
    public static final RegistryObject<MenuType<InfuserContainer>> INFUSING_TABLE_CONTAINER = CONTAINER_TYPE.register("infusing_stand_container",()-> IForgeContainerType.create(InfuserContainer::new));

    public static final RecipeType<InfusingCraftingRecipe> INFUSING_CRAFTING_RECIPE_TYPE = new RecipeType<InfusingCraftingRecipe>() {};
    public static final RecipeType<InfusingRecipe> INFUSING_RECIPE_TYPE = new InfusingRecipeType();
    public static final RecipeType<SolarSmeltingRecipe> SOLAR_SMELTING = new SolarSmeltingRecipeType();
    public SolarForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ParticlesList.PARTICLES.register(bus);
        EFFECTS.register(bus);
        SOUND_EVENTS.register(bus);
        ENTITY_TYPE_REGISTER.register(bus);
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILE_ENTITY_TYPE.register(bus);
        CONTAINER_TYPE.register(bus);
        ItemsRegister.ITEMS.register(bus);
        BlocksRegistry.BLOCKS.register(bus);
        TileEntitiesRegistry.TILE_ENTITY_TYPE.register(bus);
        Entities.ENTITY_TYPE_REGISTER.register(bus);
        EffectsRegister.EFFECTS.register(bus);
        Sounds.SOUND_EVENTS.register(bus);
        FeaturesSolarforge.FEATURES.register(bus);
        Containers.CONTAINER_TYPE.register(bus);
        FoliagePlacerRegistry.DEFERRED_REGISTER.register(bus);
        AttributesRegistry.DEF_REG.register(bus);
//        FeaturesRegistry.FEATURE_DEFERRED_REGISTER.register(bus);
        BiomesRegister.BIOMES.register(bus);
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(TrunkPlacersRegistry::registerTrunkPlacerTypes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfiguredFeatures::registerConfiguredFeatures);




        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class,EventPriority.HIGHEST,FeaturesRegistry::registerFeatures);
//        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class,FeaturesRegistry::registerBiomes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(FeaturesRegistry::registerConfiguredFeatures);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SolarcraftConfig.SPEC,"solarcraft-config.toml");

        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
//        BiomesRegister.BIOMES.register(bus);
        // Register ourselves for server and other game events we are interested in

        MinecraftForge.EVENT_BUS.register(this);
        SolarForgePacketHandler.registerMessages();
        SolarForgeStructures.STRUCTURES.register(bus);

    }



    private void setup(final FMLCommonSetupEvent event)
    {
        Tags.init();
        AbilitiesRegistry.ABILITIES.registerAll();
        TierSortingRegistry.registerTier(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,new ResourceLocation("illidium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new ResourceLocation("qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new ResourceLocation("charged_qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,new ResourceLocation("solar_god"), List.of(Tiers.DIAMOND),List.of());
//        CapabilitySolarMana.register();
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class,AttachManaCapabilityEvent::attachCapabilities);
        MinecraftForge.EVENT_BUS.addListener(AttachManaCapabilityEvent::tickEvent);
        MinecraftForge.EVENT_BUS.register(new PlayerTickEvent());
        //MinecraftForge.EVENT_BUS.addListener(SolarWandItem::renderWandOverlays);
        MinecraftForge.EVENT_BUS.addListener(InfusingPool::placeBlockEvent);
        event.enqueueWork(()->{


            SolarForgeStructures.setupStructures();
            SolarForgeStructureFeatures.registerConfiguredStructures();
            BiomeManager.addBiome(BiomeManager.BiomeType.DESERT,new BiomeManager.BiomeEntry(FeaturesRegistry.MOLTEN_BIOME_KEY,4));

        });

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        MinecraftForge.EVENT_BUS.register(new RenderEventsHandler());

        MinecraftForge.EVENT_BUS.register(new TestRenderEvent());

        MenuScreens.register(SOLAR_FORGE_CONTAINER.get(), SolarForgeScreen::new);
        MenuScreens.register(INFUSING_TABLE_CONTAINER.get(), InfuserScreen::new);
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {


        @SubscribeEvent
        public static void entityAttributes(EntityAttributeCreationEvent event) {
            event.put(SOLAR_STRIKE_ENTITY_REG.get(),SolarStrikeEntity.createAttributes().build());
            event.put(Entities.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMaster.createAttributes().build());
            event.put(Entities.CRYSTAL_BOSS.get(), CrystalBossEntity.createAttributes().build());
            event.put(Entities.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), ShieldingCrystalCrystalBoss.createAttributes().build());
            event.put(Entities.CRYSTAL_BOSS_MINE.get(), MineEntityCrystalBoss.createAttributes().build());
            event.put(Entities.RIP_RAY_GENERATOR.get(), RipRayGenerator.createAttributes().build());
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here

        }

        @SubscribeEvent
        public static void registerTE(RegistryEvent.Register<BlockEntityType<?>> evt) {



        }
        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MOD_ID,"infusing_crafting"), INFUSING_CRAFTING_RECIPE_TYPE);
            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(INFUSING_RECIPE_TYPE.toString()), INFUSING_RECIPE_TYPE);
            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(SOLAR_SMELTING.toString()), SOLAR_SMELTING);


            event.getRegistry().register(InfusingCraftingRecipe.serializer);
            event.getRegistry().register(InfusingRecipe.serializer);
            event.getRegistry().register(SolarSmeltingRecipe.serializer);
        }
    }
}
