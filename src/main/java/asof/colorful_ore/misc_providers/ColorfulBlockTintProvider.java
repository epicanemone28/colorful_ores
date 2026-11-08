package asof.colorful_ore.misc_providers;

import asof.colorful_ore.blocks.ColorfulBlock;
import asof.colorful_ore.blocks.ColorfulBlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.awt.*;

import static asof.colorful_ore.ColorfulOres.*;
import static asof.colorful_ore.registries.RegisterItems.*;

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
        },ColorfulBlock.allColorfulBlocks.toArray(Block[]::new));
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if(tintIndex > 0) return Color.WHITE.getRGB();

            DyedItemColor color = stack.getComponents().get(DataComponents.DYED_COLOR);
            if(color == null) return Color.RED.getRGB();
            return color.rgb();
        },
        COLORFUL_PICKAXE.get(), COLORFUL_AXE.get(),
        COLORFUL_ORE.get(), COLORFUL_PLATE.get(), COLORFUL_METAL_BLOCK.get(),
        RAW_COLORFUL_ORE.get(), COLORFUL_INGOT.get()
    );
    }
}