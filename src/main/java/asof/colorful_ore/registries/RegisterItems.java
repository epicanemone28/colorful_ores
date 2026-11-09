package asof.colorful_ore.registries;

import asof.colorful_ore.ColorfulOres;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

import static asof.colorful_ore.misc_providers.ColorfulBlockTintProvider.tintableItems;

public class RegisterItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ColorfulOres.MODID);

    public static final DeferredItem<Item> RAW_COLORFUL_ORE = ITEMS.registerSimpleItem("raw_colorful_ore", new Item.Properties()
            .component(DataComponents.DYED_COLOR, new DyedItemColor(Color.GRAY.getRGB(),false)
    ));
    public static final DeferredItem<Item> COLORFUL_INGOT = ITEMS.registerSimpleItem("colorful_ingot", new Item.Properties()
            .component(DataComponents.DYED_COLOR, new DyedItemColor(Color.GRAY.getRGB(),false)
            ));
    public static final DeferredItem<Item> MIMICLAY_BALL = ITEMS.registerSimpleItem("mimiclay_ball", new Item.Properties());

    static {
        tintableItems.add(RAW_COLORFUL_ORE);
        tintableItems.add(COLORFUL_INGOT);
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
        RegisterArmorItems.register(eventBus);
        RegisterToolItems.register(eventBus);
    }
}
