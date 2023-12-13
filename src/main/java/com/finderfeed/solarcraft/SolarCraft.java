package com.finderfeed.solarcraft;


import com.finderfeed.solarcraft.config.JsonConfig;
import com.finderfeed.solarcraft.content.abilities.meteorite.MeteoriteProjectile;
import com.finderfeed.solarcraft.content.abilities.solar_strike.SolarStrikeEntity;
import com.finderfeed.solarcraft.content.abilities.SolarStunEffect;
import com.finderfeed.solarcraft.config.enchanter_config.EnchanterConfigInit;
import com.finderfeed.solarcraft.config.JsonFragmentsHelper;
import com.finderfeed.solarcraft.config.SolarcraftClientConfig;
import com.finderfeed.solarcraft.config.SolarcraftConfig;

import com.finderfeed.solarcraft.content.blocks.primitive.ProgressionBlock;
import com.finderfeed.solarcraft.content.items.solar_lexicon.progressions.Progression;
import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureTypes;
import com.finderfeed.solarcraft.events.RenderEventsHandler;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.*;
import com.finderfeed.solarcraft.content.blocks.infusing_table_things.infusing_pool.InfusingStand;
import com.finderfeed.solarcraft.content.items.item_tiers.SolarCraftToolTiers;
import com.finderfeed.solarcraft.client.particles.SCParticleTypes;
import com.finderfeed.solarcraft.registries.ConfigRegistry;
import com.finderfeed.solarcraft.registries.SCCreativeTabs;
import com.finderfeed.solarcraft.registries.SolarcraftGamerules;
import com.finderfeed.solarcraft.registries.Tags;
import com.finderfeed.solarcraft.registries.abilities.AbilitiesRegistry;
import com.finderfeed.solarcraft.registries.attributes.AttributesRegistry;
import com.finderfeed.solarcraft.registries.blocks.SCBlocks;
import com.finderfeed.solarcraft.registries.command_argument_types.SolarcraftCommandArgumentTypes;
import com.finderfeed.solarcraft.registries.data_serializers.FDEntityDataSerializers;
import com.finderfeed.solarcraft.registries.entities.SCEntityTypes;

import com.finderfeed.solarcraft.packet_handler.SCPacketHandler;
import com.finderfeed.solarcraft.registries.containers.SolarcraftContainers;
import com.finderfeed.solarcraft.registries.effects.SCEffects;
import com.finderfeed.solarcraft.registries.loot_modifiers.SolarcraftLootModifiers;
import com.finderfeed.solarcraft.registries.recipe_types.SolarcraftRecipeTypes;
import com.finderfeed.solarcraft.registries.tile_entities.SolarcraftTileEntityTypes;
import com.finderfeed.solarcraft.registries.wand_actions.SolarCraftWandActionRegistry;
import com.finderfeed.solarcraft.registries.worldgen.configured.LazyConfiguredFeatures;
import com.finderfeed.solarcraft.registries.items.SCItems;
import com.finderfeed.solarcraft.registries.sounds.SolarcraftSounds;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlock;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.SolarForgeBlockEntity;


import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeContainer;
import com.finderfeed.solarcraft.content.blocks.solar_forge_block.solar_forge_screen.SolarForgeScreen;


import com.finderfeed.solarcraft.content.world_generation.structures.SolarcraftStructureHolders;
import com.finderfeed.solarcraft.content.world_generation.features.FeaturesRegistry;
import com.finderfeed.solarcraft.content.world_generation.features.foliage_placers.FoliagePlacerRegistry;
import com.finderfeed.solarcraft.content.world_generation.features.trunk_placers.TrunkPlacersRegistry;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.SoundType;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

import net.neoforged.neoforge.common.TierSortingRegistry;

import net.neoforged.fml.common.Mod;


import net.neoforged.neoforge.registries.DeferredRegister;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_SOUND = SOUND_EVENTS.register("solar_ray_sound",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation("solarcraft","solar_strike_explosion_sound")));
    public static final RegistryObject<SoundEvent> SOLAR_STRIKE_BUILD_SOUND = SOUND_EVENTS.register("solar_ray_buildup_sound",()-> SoundEvent.createVariableRangeEvent(new ResourceLocation("solarcraft","solar_strike_buildup")));

    public static final RegistryObject<EntityType<SolarStrikeEntity>> SOLAR_STRIKE_ENTITY_REG = ENTITY_TYPE_REGISTER.register("solar_strike_entity",
            ()->EntityType.Builder.of(SolarStrikeEntity::new, MobCategory.CREATURE).sized(0.5F,0.5F).build("solarcraft:solar_strike_entity"));

    public static final RegistryObject<MobEffect> SOLAR_STUN = EFFECTS.register("solar_stun",()-> new SolarStunEffect(MobEffectCategory.HARMFUL,0xd1b515));
    public  static  final  RegistryObject<SolarForgeBlock> SOLAR_FORGE = BLOCKS.register("solar_forge",()->
            new SolarForgeBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(SoundType.ANCIENT_DEBRIS)));

    public  static  final  RegistryObject<ProgressionBlock> SOLAR_ORE = BLOCKS.register("solar_ore",() -> new ProgressionBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANCIENT_DEBRIS),()-> Progression.ENTER_NETHER,Blocks.STONE));

    public  static  final  RegistryObject<InfuserBlock> SOLAR_INFUSER = BLOCKS.register("solar_infuser",
            ()-> new InfuserBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()));

    public static final RegistryObject<BlockEntityType<InfuserTileEntity>> INFUSING_STAND_BLOCKENTITY = TILE_ENTITY_TYPE.register("infusing_stand_blockentity",()->
            BlockEntityType.Builder.of(InfuserTileEntity::new,SOLAR_INFUSER.get()).build(null));

    public static final RegistryObject<EntityType<MeteoriteProjectile>> METEORITE = ENTITY_TYPE_REGISTER.register("solar_forge_meteorite_projectile",()->EntityType.Builder.<MeteoriteProjectile>of(MeteoriteProjectile::new,MobCategory.MISC).sized(5,5).build("solar_forge_meteorite_projectile"));

    public static final Logger LOGGER = LogManager.getLogger("SOLARCRAFT");
    public static final RegistryObject<BlockEntityType<SolarForgeBlockEntity>> SOLAR_FORGE_BLOCKENTITY = TILE_ENTITY_TYPE.register("solar_forge_blockentity",()->
            BlockEntityType.Builder.of(SolarForgeBlockEntity::new,SOLAR_FORGE.get()).build(null));
    public static final RegistryObject<MenuType<SolarForgeContainer>> SOLAR_FORGE_CONTAINER = CONTAINER_TYPE.register("solarcraft_container",()-> IForgeMenuType.create(SolarForgeContainer::new));
    public static final RegistryObject<MenuType<InfuserContainer>> INFUSING_TABLE_CONTAINER = CONTAINER_TYPE.register("infusing_stand_container",()-> IForgeMenuType.create(InfuserContainer::new));


    public SolarCraft() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SCParticleTypes.PARTICLES.register(bus);
        EFFECTS.register(bus);
        SOUND_EVENTS.register(bus);
        ENTITY_TYPE_REGISTER.register(bus);
        BLOCKS.register(bus);
        TILE_ENTITY_TYPE.register(bus);
        CONTAINER_TYPE.register(bus);
        SCCreativeTabs.REGISTRY.register(bus);
        SCItems.ITEMS.register(bus);
        SCBlocks.BLOCKS.register(bus);
        SolarcraftTileEntityTypes.TILE_ENTITY_TYPE.register(bus);
        SCEntityTypes.ENTITY_TYPE_REGISTER.register(bus);
        SCEffects.EFFECTS.register(bus);
        SolarcraftSounds.SOUND_EVENTS.register(bus);
        SolarcraftContainers.CONTAINER_TYPE.register(bus);
        FoliagePlacerRegistry.DEFERRED_REGISTER.register(bus);
        AttributesRegistry.DEF_REG.register(bus);
        FDEntityDataSerializers.DEF_REG.register(bus);
//        BiomesRegister.BIOMES.register(bus);
        FeaturesRegistry.FEATURES.register(bus);
        SolarcraftRecipeTypes.RECIPE_SERIALIZERS.register(bus);
        SolarcraftRecipeTypes.RECIPE_TYPES.register(bus);
        SolarcraftLootModifiers.MODIFIERS.register(bus);
        SolarcraftCommandArgumentTypes.ARGUMENT_TYPES.register(bus);
        SolarcraftGamerules.init();
        ConfigRegistry.init();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(TrunkPlacersRegistry::registerTrunkPlacerTypes);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(LazyConfiguredFeatures::registerConfiguredFeatures);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SCItems::registerIntoCreativeTabs);

//        FMLJavaModLoadingContext.get().getModEventBus().addListener(FeaturesRegistry::registerConfiguredFeatures);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SolarcraftConfig.SPEC,"solarcraft-config.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SolarcraftClientConfig.SPEC,"solarcraft-client-config.toml");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        NeoForge.EVENT_BUS.register(this);
        SCPacketHandler.registerMessages();
//        SolarForgeStructures.STRUCTURES.register(bus);

        for (JsonConfig early : ConfigRegistry.EARLY_LOAD_CONFIGS.values()){
            early.init();
        }
    }



    private void setup(final FMLCommonSetupEvent event)
    {
        JsonFragmentsHelper.setupJSON();
        EnchanterConfigInit.setupJSON();
        for (JsonConfig post : ConfigRegistry.POST_LOAD_CONFIGS.values()){
            post.init();
        }
        Tags.init();
        AbilitiesRegistry.init();
        SolarCraftWandActionRegistry.init();
        TierSortingRegistry.registerTier(SolarCraftToolTiers.ILLIDIUM_TOOLS_TIER,new ResourceLocation("illidium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.QUALADIUM_TOOLS_TIER,new ResourceLocation("qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.CHARGED_QUALADIUM_TOOLS_TIER,new ResourceLocation("charged_qualadium"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.SOLAR_GOD_TOOL_TIER,new ResourceLocation("solar_god"), List.of(Tiers.DIAMOND),List.of());
        TierSortingRegistry.registerTier(SolarCraftToolTiers.DIVINE_TIER,new ResourceLocation("divine"), List.of(Tiers.DIAMOND),List.of());


        NeoForge.EVENT_BUS.addListener(InfusingStand::placeBlockEvent);
        event.enqueueWork(()->{
            SolarcraftStructureTypes.init();
            SolarcraftStructureHolders.init();
        });

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(new RenderEventsHandler());
        MenuScreens.register(SOLAR_FORGE_CONTAINER.get(), SolarForgeScreen::new);
        MenuScreens.register(INFUSING_TABLE_CONTAINER.get(), InfuserScreen::new);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {


//        @SubscribeEvent
//        public static void entityAttributes(EntityAttributeCreationEvent event) {
//            event.put(SOLAR_STRIKE_ENTITY_REG.get(),SolarStrikeEntity.createAttributes().build());
//            event.put(SolarcraftEntityTypes.VILLAGER_SOLAR_MASTER.get(), VillagerSolarMaster.createAttributes().build());
//            event.put(SolarcraftEntityTypes.CRYSTAL_BOSS.get(), CrystalBossEntity.createAttributes().build());
//            event.put(SolarcraftEntityTypes.RUNIC_ELEMENTAL_BOSS.get(), RunicElementalBoss.createAttributes().build());
//            event.put(SolarcraftEntityTypes.CRYSTAL_BOSS_SHIELDING_CRYSTAL.get(), ShieldingCrystalCrystalBoss.createAttributes().build());
//            event.put(SolarcraftEntityTypes.CRYSTAL_BOSS_MINE.get(), MineEntityCrystalBoss.createAttributes().build());
//            event.put(SolarcraftEntityTypes.RIP_RAY_GENERATOR.get(), RipRayGenerator.createAttributes().build());
//            event.put(SolarcraftEntityTypes.REFRACTION_CRYSTAL.get(), RefractionCrystal.createAttributes().build());
//            event.put(SolarcraftEntityTypes.EXPLOSIVE_CRYSTAL.get(), ExplosiveCrystal.createAttributes().build());
//            event.put(SolarcraftEntityTypes.RUNIC_WARRIOR.get(), RunicWarrior.createAttributes().build());
//            event.put(SolarcraftEntityTypes.SHADOW_ZOMBIE.get(), ShadowZombie.createAttributes().build());
//            event.put(SolarcraftEntityTypes.CORRUPTION_WISP.get(), CorruptionWisp.createAttributes().build());
//        }

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
