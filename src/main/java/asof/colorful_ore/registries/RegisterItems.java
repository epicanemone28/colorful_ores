package asof.colorful_ore.registries;

import asof.colorful_ore.ColorfulOres;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;
import java.util.ArrayList;

public class RegisterItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ColorfulOres.MODID);

    static ArrayList<Tool.Rule> createMiningRule(TagKey<Block> key){
        ArrayList<Tool.Rule> list = new ArrayList<>();
        list.add(Tool.Rule.minesAndDrops(key,6.5f));
        return list;
    }

    public static final DeferredItem<Item> RAW_COLORFUL_ORE = ITEMS.registerSimpleItem("raw_colorful_ore", new Item.Properties()
            .component(DataComponents.DYED_COLOR, new DyedItemColor(Color.GRAY.getRGB(),false)
            ));
    public static final DeferredItem<Item> COLORFUL_INGOT = ITEMS.registerSimpleItem("colorful_ingot", new Item.Properties()
            .component(DataComponents.DYED_COLOR, new DyedItemColor(Color.GRAY.getRGB(),false)
            ));
    public static final DeferredItem<Item> MIMICLAY_BALL = ITEMS.registerSimpleItem("mimiclay_ball", new Item.Properties());



    public static final DeferredItem<Item> COLORFUL_PICKAXE = ITEMS.registerSimpleItem("colorful_pickaxe", new Item.Properties()
        .component(DataComponents.DYED_COLOR, new DyedItemColor(Color.GRAY.getRGB(),true))
        .component(DataComponents.TOOL,new Tool(createMiningRule(BlockTags.MINEABLE_WITH_PICKAXE),1.0f,1))
    );
    public static final DeferredItem<Item> COLORFUL_AXE = ITEMS.registerSimpleItem("colorful_axe", new Item.Properties()
        .component(DataComponents.DYED_COLOR, new DyedItemColor(Color.GRAY.getRGB(),true))
        .component(DataComponents.TOOL,new Tool(createMiningRule(BlockTags.MINEABLE_WITH_AXE),1.0f,1))
    );

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
