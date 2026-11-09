package asof.colorful_ore;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = ColorfulOres.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER.comment("A magic number").defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER.comment("What you want the introduction message to be for the magic number").define("magicNumberIntroduction", "The magic number is... ");

    private static final ModConfigSpec.BooleanValue ENABLE_COLORFUL_ORE_GENERATION = BUILDER.comment("Enable the generation of colorful ores in-world.")
            .define("worldgen.generate_colorful_ores",true);
    private static final ModConfigSpec.BooleanValue ENABLE_MIMICLAY_GENERATION = BUILDER.comment("Enable the generation of mimiclay in-world.")
            .define("worldgen.generate_mimiclay",true);

    private static final ModConfigSpec.BooleanValue ENABLE_AUTODETECTION = BUILDER.comment("Should this mod try to identify valid decorative blocks at runtime?")
            .define("replicable.autodetect_decor_blocks",false);

    // a list of strings that are treated as resource locations for items
    private static final ModConfigSpec.ConfigValue<List<? extends String>> REPLICATABLE_WHITELIST = BUILDER.comment("A list of items to add to the #colorful_ore:replicable tag, allowing them to be replicated with mimiclay.")
            .defineListAllowEmpty("replicable.whitelist", List.of("minecraft:waxed_weathered_cut_copper_stairs"), ()->"minecraft:stone", Config::validateItemName);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> REPLICATABLE_BLACKLIST = BUILDER.comment("A list of items to forcibly remove from the #colorful_ore:replicable tag, preventing them from being replicated with mimiclay.")
            .defineListAllowEmpty("replicable.blacklist", List.of("minecraft:netherite_block"), ()->"minecraft:stone", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    private static boolean validateBlockName(final Object obj) {
        return obj instanceof String blockName && BuiltInRegistries.BLOCK.containsKey(ResourceLocation.parse(blockName));
    }

    public static HashMap<String,Boolean> worldgenConfigFlags = new HashMap<>();

    public static int magicNumber;
    public static String magicNumberIntroduction;

    public boolean shouldDetectDecorativeBlocks;
    public static Set<Item> forciblyUnreplicableItems;
    public static Set<Item> forciblyReplicatableItems;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        magicNumber = MAGIC_NUMBER.get();
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

        worldgenConfigFlags.clear();
        worldgenConfigFlags.put(ResourceLocation.fromNamespaceAndPath(ColorfulOres.MODID,"colorful_ores").toString(),ENABLE_COLORFUL_ORE_GENERATION.get());
        worldgenConfigFlags.put(ResourceLocation.fromNamespaceAndPath(ColorfulOres.MODID,"mimiclay").toString(),ENABLE_MIMICLAY_GENERATION.get());

        // convert the list of strings into a set of items
        forciblyUnreplicableItems = REPLICATABLE_WHITELIST.get().stream().map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName))).collect(Collectors.toSet());
        forciblyReplicatableItems = REPLICATABLE_BLACKLIST.get().stream().map(itemName -> BuiltInRegistries.ITEM.get(ResourceLocation.parse(itemName))).collect(Collectors.toSet());
    }
}
