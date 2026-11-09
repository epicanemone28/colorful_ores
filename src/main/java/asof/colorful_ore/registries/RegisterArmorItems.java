package asof.colorful_ore.registries;

// ARMOR_MATERIALS is a DeferredRegister<ArmorMaterial>

import asof.colorful_ore.ColorfulOres;// We place copper somewhere between chainmail and iron.
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

import static asof.colorful_ore.misc_providers.ColorfulBlockTintProvider.tintableItems;


public class RegisterArmorItems {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL,ColorfulOres.MODID);

    public static final Holder<ArmorMaterial> COLORFUL_ARMOR_MATERIAL =
        ARMOR_MATERIALS.register("colorful", () -> new ArmorMaterial(
            // Determines the defense value of this armor material, depending on what armor piece it is.
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.BODY, 4);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_GENERIC,
            // Determines the repair item for this armor.
            () -> Ingredient.of(Tags.Items.INGOTS),
            // Determines the texture locations of the armor to apply when rendering
            // This can also be specified by overriding 'IItemExtension#getArmorTexture' on your item if the armor texture needs to be more dynamic
            List.of(
                // Creates a new armor texture that will be located at:
                // - 'assets/mod_id/textures/models/armor/copper_layer_1.png' for the outer texture
                // - 'assets/mod_id/textures/models/armor/copper_layer_2.png' for the inner texture (only legs)
                // 'true' means that the armor material is dyeable; however, the item must also be added to the 'minecraft:dyeable' tag
                new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ColorfulOres.MODID, "colorful"), "", true )
            ),
            0, // toughness
            0 // knockback resist
        ));


    public static final DeferredRegister.Items ARMOR_ITEMS = DeferredRegister.createItems(ColorfulOres.MODID);

    //ITEMS is a DeferredRegister<Item>
    public static final Supplier<ArmorItem> COLORFUL_HELMET = ARMOR_ITEMS.register("colorful_helmet", () -> new ArmorItem(
            COLORFUL_ARMOR_MATERIAL,
            ArmorItem.Type.HELMET,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
    ));
    public static final Supplier<ArmorItem> COLORFUL_CHESTPLATE = ARMOR_ITEMS.register("colorful_chestplate", () -> new ArmorItem(
            COLORFUL_ARMOR_MATERIAL,
            ArmorItem.Type.CHESTPLATE,
            new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
    ));
    public static final Supplier<ArmorItem> COLORFUL_LEGGINGS = ARMOR_ITEMS.register("colorful_leggings", () -> new ArmorItem(
            COLORFUL_ARMOR_MATERIAL,
            ArmorItem.Type.LEGGINGS,
            new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
    ));
    public static final Supplier<ArmorItem> COLORFUL_BOOTS = ARMOR_ITEMS.register("colorful_boots", () -> new ArmorItem(
            COLORFUL_ARMOR_MATERIAL,
            ArmorItem.Type.BOOTS,
            new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
    ));

    static {
        tintableItems.addAll(ARMOR_ITEMS.getEntries());
    }

    public static void register(IEventBus eventBus){
        ARMOR_MATERIALS.register(eventBus);
        ARMOR_ITEMS.register(eventBus);
    }
}
