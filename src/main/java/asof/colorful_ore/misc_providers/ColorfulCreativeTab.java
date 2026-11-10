package asof.colorful_ore.misc_providers;

import asof.colorful_ore.ColorfulOres;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

import static asof.colorful_ore.registries.RegisterArmorItems.ARMOR_ITEMS;
import static asof.colorful_ore.registries.RegisterBlocks.*;
import static asof.colorful_ore.registries.RegisterItems.*;
import static asof.colorful_ore.registries.RegisterToolItems.TOOL_ITEMS;

public class ColorfulCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ColorfulOres.MODID);

    static Color[] colors = new Color[]{
            Color.DARK_GRAY,
            Color.RED,
            new Color(40, 122, 29),
            Color.GREEN,
            Color.BLUE,
            Color.CYAN,
            Color.MAGENTA,
            Color.YELLOW,
            Color.WHITE
    };

    public static void addInAllColors(CreativeModeTab.Output output, ItemLike item) {
        for(Color color : colors){
            ItemStack coloredItem = new ItemStack(item.asItem());
            coloredItem.set(DataComponents.DYED_COLOR, new DyedItemColor(color.getRGB(),false));
            output.accept(coloredItem);
        }
    }

    public static void addInGradient(CreativeModeTab.Output output, ItemLike[] items, Color fromColor, Color toColor) {
        int from = fromColor.getRGB();
        int to = toColor.getRGB();

        for(int i = 0; i<items.length; i++){
            ItemStack coloredItem = new ItemStack(items[i].asItem());
            coloredItem.set(DataComponents.DYED_COLOR, new DyedItemColor(FastColor.ARGB32.lerp(((float) i)/(items.length-1),from,to),true));
            output.accept(coloredItem);
        }
    }

    public static void getDisplayItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output){
        addInAllColors(output, RAW_COLORFUL_ORE.get());
        addInAllColors(output, COLORFUL_INGOT.get());
        addInAllColors(output, COLORFUL_PLATE_BLOCKITEM.get());
        addInAllColors(output, COLORFUL_STEP_BLOCKITEM.get());
        addInAllColors(output, COLORFUL_STURDY_PLATE_BLOCKITEM.get());
        addInAllColors(output, COLORFUL_METAL_BLOCK_BLOCKITEM.get());
        addInAllColors(output, COLORFUL_ORE_BLOCKITEM.get());
        addInAllColors(output, DEEPSLATE_COLORFUL_ORE_BLOCKITEM.get());
        output.accept(MIMICLAY_BALL.get());
        output.accept(MIMICLAY_BLOCKITEM.get());
        Color niceGreen = new Color(29, 67, 19);
        Color niceLime = new Color(113, 220, 17);
        addInGradient(output,TOOL_ITEMS.getEntries().stream().map(DeferredHolder::get).toArray(Item[]::new),niceGreen,niceLime);
        Color nicePurple = new Color(57, 19, 67);
        Color niceMagenta = new Color(255, 0, 164);
        addInGradient(output,ARMOR_ITEMS.getEntries().stream().map(DeferredHolder::get).toArray(Item[]::new),nicePurple,niceMagenta);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    static final DeferredHolder<CreativeModeTab, CreativeModeTab> COLORFUL_TAB = CREATIVE_MODE_TABS.register("colorful_ore",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.colorful_ore"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> COLORFUL_INGOT.get().getDefaultInstance())
                    .displayItems(ColorfulCreativeTab::getDisplayItems)
                    .build()
    );

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
