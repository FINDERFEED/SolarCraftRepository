package com.finderfeed.solarforge;


import com.finderfeed.solarforge.SolarAbilities.MeteoriteProjectile;
import com.finderfeed.solarforge.SolarAbilities.SolarStrikeEntity;
import com.finderfeed.solarforge.SolarAbilities.SolarStunEffect;
import com.finderfeed.solarforge.capability_mana.AttachManaCapabilityEvent;
import com.finderfeed.solarforge.capability_mana.CapabilitySolarMana;
import com.finderfeed.solarforge.entities.VillagerSolarMaster;
import com.finderfeed.solarforge.events.PlayerTickEvent;
import com.finderfeed.solarforge.infusing_table_things.*;
import com.finderfeed.solarforge.infusing_table_things.infusing_pool.InfusingPool;
import com.finderfeed.solarforge.misc_things.ParticlesList;
import com.finderfeed.solarforge.on_screen_rendering.TestRenderEvent;
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
import com.finderfeed.solarforge.registries.projectiles.Projectiles;
import com.finderfeed.solarforge.registries.sounds.Sounds;
import com.finderfeed.solarforge.registries.tile_entities.TileEntitiesRegistry;
import com.finderfeed.solarforge.solar_forge_block.SolarForgeBlock;
import com.finderfeed.solarforge.solar_forge_block.SolarForgeBlockEntity;


import com.finderfeed.solarforge.solar_forge_block.SolarForgeBlockItem;
import com.finderfeed.solarforge.solar_forge_screen.SolarForgeContainer;
import com.finderfeed.solarforge.solar_forge_screen.SolarForgeScreen;


import com.finderfeed.solarforge.structures.SolarForgeStructureFeatures;
import com.finderfeed.solarforge.structures.SolarForgeStructures;
import com.finderfeed.solarforge.world_generation.BiomesRegister;
import com.finderfeed.solarforge.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarforge.world_generation.features.foliage_placers.FoliagePlacerRegistry;
import com.finderfeed.solarforge.world_generation.features.trunk_placers.TrunkPlacersRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;


import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("solarforge")
public class SolarForge
{
    public  static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS,"solarforge");
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,"solarforge");
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,"solarforge");
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS,"solarforge");
    public static final  DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,"solarforge");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,"solarforge");
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES,"solarforge");

    public static final ItemGroup SOLAR_GROUP = new SolarGroup("solar_forge_group");
    public static final ItemGroup SOLAR_GROUP_BLOCKS = new SolarGroupBlocks("solar_forge_group_blocks");
    public static final ItemGroup SOLAR_GROUP_TOOLS = new SolarGroupTools("solar_forge_group_tools");
    public static final ItemGroup SOLAR_GROUP_FRAGMENTS = new SolarGroupFragments("solar_forge_group_fragments");

    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_SOUND = SOUND_EVENTS.register("solar_ray_sound",()-> new SoundEvent(new ResourceLocation("solarforge","solar_strike_explosion_sound")));
    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_BUILD_SOUND = SOUND_EVENTS.register("solar_ray_buildup_sound",()-> new SoundEvent(new ResourceLocation("solarforge","solar_strike_buildup")));

    public static final RegistryObject<EntityType<SolarStrikeEntity>> SOLAR_STRIKE_ENTITY_REG = ENTITY_TYPE_REGISTER.register("solar_strike_entity",
            ()->EntityType.Builder.of(SolarStrikeEntity::new, EntityClassification.CREATURE).sized(0.5F,0.5F).build("solarforge:solar_strike_entity"));

    public static final RegistryObject<Effect> SOLAR_STUN = EFFECTS.register("solar_stun",()-> new SolarStunEffect(EffectType.HARMFUL,0xd1b515));
    public  static  final  RegistryObject<SolarForgeBlock> SOLAR_FORGE = BLOCKS.register("solar_forge",()-> new SolarForgeBlock(AbstractBlock.Properties.of(Material.STONE)
            .sound(SoundType.METAL)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(1)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .strength(3,3)));

    public  static  final  RegistryObject<Block> SOLAR_ORE = BLOCKS.register("solar_ores",()-> new OreBlock(AbstractBlock.Properties.of(Material.STONE)
            .sound(SoundType.ANCIENT_DEBRIS)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(2)
            .requiresCorrectToolForDrops()
            .strength(3,3)));

    public  static  final  RegistryObject<InfusingTableBlock> SOLAR_INFUSER = BLOCKS.register("solar_infuser",()-> new InfusingTableBlock(AbstractBlock.Properties.of(Material.STONE)
            .sound(SoundType.METAL)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(1)
            .requiresCorrectToolForDrops()
            .noOcclusion()
            .dynamicShape()
            .strength(3,3)));

    public static final RegistryObject<TileEntityType<InfusingTableTileEntity>> INFUSING_STAND_BLOCKENTITY = TILE_ENTITY_TYPE.register("infusing_stand_blockentity",()->
            TileEntityType.Builder.of(InfusingTableTileEntity::new,SOLAR_INFUSER.get().getBlock()).build(null));

    public static final RegistryObject<EntityType<MeteoriteProjectile>> METEORITE = ENTITY_TYPE_REGISTER.register("solar_forge_meteorite_projectile",()->EntityType.Builder.<MeteoriteProjectile>of(MeteoriteProjectile::new,EntityClassification.MISC).sized(5,5).build("solar_forge_meteorite_projectile"));
    public static  final RegistryObject<Item> TEST_ITEM = ITEMS.register("solar_shard",()-> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP)));
    public static  final RegistryObject<Item> SOLAR_FORGE_ITEM = ITEMS.register("solar_forge",()-> new SolarForgeBlockItem(SOLAR_FORGE.get().getBlock(),new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP_BLOCKS).stacksTo(1)));
    public static  final RegistryObject<Item> SOLAR_ORE_ITEM = ITEMS.register("solar_ores",()-> new BlockItem(SOLAR_ORE.get().getBlock(),new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP_BLOCKS)));
    public static  final RegistryObject<Item> INFUSING_STAND_ITEM = ITEMS.register("solar_infuser",()-> new InfusingTableBlockItem(SOLAR_INFUSER.get().getBlock(),new Item.Properties().rarity(Rarity.EPIC).tab(SOLAR_GROUP_BLOCKS).stacksTo(1)));
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();
    public static final RegistryObject<TileEntityType<SolarForgeBlockEntity>> SOLAR_FORGE_BLOCKENTITY = TILE_ENTITY_TYPE.register("solar_forge_blockentity",()->
            TileEntityType.Builder.of(SolarForgeBlockEntity::new,SOLAR_FORGE.get().getBlock()).build(null));
    public static final RegistryObject<ContainerType<SolarForgeContainer>> SOLAR_FORGE_CONTAINER = CONTAINER_TYPE.register("solarforge_container",()-> IForgeContainerType.create(SolarForgeContainer::new));
    public static final RegistryObject<ContainerType<InfusingTableContainer>> INFUSING_TABLE_CONTAINER = CONTAINER_TYPE.register("infusing_stand_container",()-> IForgeContainerType.create(InfusingTableContainer::new));

    public static final IRecipeType<InfusingRecipe> INFUSING_RECIPE_TYPE = new InfusingRecipeType();
    public static final IRecipeType<SolarSmeltingRecipe> SOLAR_SMELTING = new SolarSmeltingRecipeType();
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
        Projectiles.ENTITY_TYPE_REGISTER.register(bus);
        EffectsRegister.EFFECTS.register(bus);
        Sounds.SOUND_EVENTS.register(bus);
        FeaturesSolarforge.FEATURES.register(bus);
        Containers.CONTAINER_TYPE.register(bus);
        FoliagePlacerRegistry.DEFERRED_REGISTER.register(bus);

//        FeaturesRegistry.FEATURE_DEFERRED_REGISTER.register(bus);
        BiomesRegister.BIOMES.register(bus);
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(TrunkPlacersRegistry::registerTrunkPlacerTypes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ConfiguredFeatures::registerConfiguredFeatures);




        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Feature.class,EventPriority.HIGHEST,FeaturesRegistry::registerFeatures);
//        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Biome.class,FeaturesRegistry::registerBiomes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(FeaturesRegistry::registerConfiguredFeatures);


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

        // some preinit code
        CapabilitySolarMana.register();
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

        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
        MinecraftForge.EVENT_BUS.register(new TestRenderEvent());

        ScreenManager.register(SOLAR_FORGE_CONTAINER.get(), SolarForgeScreen::new);
        ScreenManager.register(INFUSING_TABLE_CONTAINER.get(), InfusingTableScreen::new);
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
        event.put(Projectiles.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMaster.createAttributes().build());
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here

        }

        @SubscribeEvent
        public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt) {



        }
        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {

            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(INFUSING_RECIPE_TYPE.toString()), INFUSING_RECIPE_TYPE);
            Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(SOLAR_SMELTING.toString()), SOLAR_SMELTING);
            event.getRegistry().register(InfusingRecipe.serializer);
            event.getRegistry().register(SolarSmeltingRecipe.serializer);
        }
    }
}
