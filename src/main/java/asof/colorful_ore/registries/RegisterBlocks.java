package asof.colorful_ore.registries;

import asof.colorful_ore.ColorfulOres;
import asof.colorful_ore.blocks.ColorfulBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static asof.colorful_ore.misc_providers.ColorfulBlockTintProvider.tintableItems;

public class RegisterBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ColorfulOres.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(ColorfulOres.MODID);

    public static final DeferredBlock<Block> MIMICLAY = BLOCKS.registerBlock("mimiclay", Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.CLAY)
    );
    public static final DeferredBlock<Block> COLORFUL_ORE = BLOCKS.registerBlock("colorful_ore", ColorfulBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).destroyTime(1.5f).requiresCorrectToolForDrops()
    );
    public static final DeferredBlock<Block> DEEPSLATE_COLORFUL_ORE = BLOCKS.registerBlock("deepslate_colorful_ore", ColorfulBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).destroyTime(2.5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)
    );
    public static final DeferredBlock<Block> COLORFUL_PLATE = BLOCKS.registerBlock("colorful_plate", ColorfulBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).destroyTime(0.75f).sound(SoundType.COPPER).explosionResistance(8)
    );
    public static final DeferredBlock<Block> COLORFUL_STURDY_PLATE = BLOCKS.registerBlock("colorful_sturdy_plate", ColorfulBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).destroyTime(1.75f).sound(SoundType.COPPER).explosionResistance(20)
    );
    public static final DeferredBlock<Block> COLORFUL_METAL_BLOCK = BLOCKS.registerBlock("colorful_metal_block", ColorfulBlock::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).destroyTime(2.5f).sound(SoundType.COPPER)
    );

    public static final DeferredItem<BlockItem> DEEPSLATE_COLORFUL_ORE_BLOCKITEM = BLOCK_ITEMS.registerSimpleBlockItem("deepslate_colorful_ore", DEEPSLATE_COLORFUL_ORE);
    public static final DeferredItem<BlockItem> COLORFUL_ORE_BLOCKITEM = BLOCK_ITEMS.registerSimpleBlockItem("colorful_ore", COLORFUL_ORE);
    public static final DeferredItem<BlockItem> COLORFUL_PLATE_BLOCKITEM = BLOCK_ITEMS.registerSimpleBlockItem("colorful_plate", COLORFUL_PLATE);
    public static final DeferredItem<BlockItem> COLORFUL_STURDY_PLATE_BLOCKITEM = BLOCK_ITEMS.registerSimpleBlockItem("colorful_sturdy_plate", COLORFUL_STURDY_PLATE);
    public static final DeferredItem<BlockItem> COLORFUL_METAL_BLOCK_BLOCKITEM = BLOCK_ITEMS.registerSimpleBlockItem("colorful_metal_block", COLORFUL_METAL_BLOCK);
    public static final DeferredItem<BlockItem> MIMICLAY_BLOCKITEM = BLOCK_ITEMS.registerSimpleBlockItem("mimiclay", MIMICLAY);

    static {
        tintableItems.add(COLORFUL_ORE);
        tintableItems.add(DEEPSLATE_COLORFUL_ORE);
        tintableItems.add(COLORFUL_PLATE);
        tintableItems.add(COLORFUL_STURDY_PLATE);
        tintableItems.add(COLORFUL_METAL_BLOCK);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }
}
