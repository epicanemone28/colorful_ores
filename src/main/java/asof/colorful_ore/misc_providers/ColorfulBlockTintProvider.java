package asof.colorful_ore.misc_providers;

import asof.colorful_ore.blocks.ColorfulBlockEntity;
import asof.colorful_ore.blocks.ColorfulBlockHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.awt.*;
import java.util.ArrayList;

@EventBusSubscriber
public class ColorfulBlockTintProvider {
    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if(tintIndex > 0) return Color.WHITE.getRGB();

            if(level == null) return Color.RED.getRGB();
            if(pos == null) return Color.RED.getRGB();
            BlockEntity be = level.getBlockEntity(pos);
            if(be == null) return Color.RED.getRGB();
            return ((ColorfulBlockEntity) be).getTint();
        }, ColorfulBlockHelper.allColorfulBlocks.toArray(Block[]::new));
    }

    static public final ArrayList<DeferredHolder<? extends ItemLike,? extends ItemLike>> tintableItems = new ArrayList<>();
    private static final ArrayList<Item> tintedItemsCached = new ArrayList<>();

    public static boolean itemHasTint(ItemLike item) {
        if(tintedItemsCached.isEmpty()) tintedItemsCached.addAll(tintableItems.stream().map(i->i.get().asItem()).toList());
        return tintedItemsCached.contains(item.asItem());
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if(tintIndex > 0) return Color.WHITE.getRGB();

            DyedItemColor color = stack.getComponents().get(DataComponents.DYED_COLOR);
            if(color == null) return Color.RED.getRGB();
            return FastColor.ARGB32.opaque(color.rgb());
        },
        tintableItems.stream().map(DeferredHolder::get).toArray(ItemLike[]::new)
    );
    }
}