package asof.colorful_ore;

import asof.colorful_ore.blocks.ColorfulBlock;
import asof.colorful_ore.blocks.ColorfulBlockEntity;
import asof.colorful_ore.misc_providers.ColorfulBlockLootFunction;
import asof.colorful_ore.misc_providers.ColorfulCreativeTab;
import asof.colorful_ore.recipes.RecipeSerializers;
import asof.colorful_ore.recipes.RecipeTypes;
import asof.colorful_ore.registries.RegisterBlocks;
import asof.colorful_ore.registries.RegisterItems;
import asof.colorful_ore.worldgen.ColorfulOreFeature;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static asof.colorful_ore.registries.RegisterBlocks.COLORFUL_ORE_BLOCKITEM;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ColorfulOres.MODID)
public class ColorfulOres {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "colorful_ore";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,MODID);
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE,MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE,MODID);

    @SuppressWarnings({"UnusedDeclaration"})
    public static final DeferredHolder<Feature<?>, ?> COLORFUL_ORE_FEATURE = FEATURES.register("colorful_ore_feature", ()->new ColorfulOreFeature(OreConfiguration.CODEC));

    @SuppressWarnings("DataFlowIssue")
    public static final Supplier<BlockEntityType<ColorfulBlockEntity>> COLORFUL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "colorful_block_entity",
            // The block entity type.
            () -> BlockEntityType.Builder.of(
                    ColorfulBlockEntity::new,
                    ColorfulBlock.allColorfulBlocks.toArray(Block[]::new)
            ).build(null)
    );

    @SuppressWarnings({"UnusedDeclaration"})
    public static final Supplier<LootItemFunctionType<ColorfulBlockLootFunction>> COPY_TINT_TO_ITEM =
            LOOT_FUNCTION_TYPES.register("copy_tint_to_item", () -> new LootItemFunctionType<>(ColorfulBlockLootFunction.CODEC));




    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ColorfulOres(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        BLOCK_ENTITY_TYPES.register(modEventBus);
        LOOT_FUNCTION_TYPES.register(modEventBus);
        FEATURES.register(modEventBus);

        RegisterBlocks.register(modEventBus);
        RecipeTypes.register(modEventBus);
        RecipeSerializers.register(modEventBus);
        ColorfulCreativeTab.register(modEventBus);
        RegisterItems.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ColorfulOres) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(COLORFUL_ORE_BLOCKITEM.get());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
