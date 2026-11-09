package asof.colorful_ore.registries;

import asof.colorful_ore.ColorfulOres;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static asof.colorful_ore.misc_providers.ColorfulBlockTintProvider.tintableItems;

public class RegisterToolItems {
    public static final DeferredRegister.Items TOOL_ITEMS = DeferredRegister.createItems(ColorfulOres.MODID);

    // We place copper somewhere between stone and iron.
    public static final Tier COLORFUL_TIER = new SimpleTier(
            // The tag that determines what blocks this tool cannot break. See below for more information.
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            // Determines the durability of the tier.
            // Stone is 131, iron is 250.
            150,
            // Determines the mining speed of the tier. Unused by swords.
            // Stone uses 4, iron uses 6.
            6.5f,
            // Determines the attack damage bonus. Different tools use this differently. For example, swords do (getAttackDamageBonus() + 4) damage.
            // Stone uses 1, iron uses 2, corresponding to 5 and 6 attack damage for swords, respectively; our sword does 5.5 damage now.
            1.5f,
            // Determines the enchantability of the tier. This represents how good the enchantments on this tool will be.
            // Gold uses 22, we put copper slightly below that.
            10,
            // Determines the repair ingredient of the tier. Use a supplier for lazy initializing.
            () -> Ingredient.of(Tags.Items.INGOTS)
    );

    //ITEMS is a DeferredRegister<Item>
    public static final Supplier<SwordItem> COPPER_SWORD = TOOL_ITEMS.register("colorful_sword", ()->new SwordItem(
            COLORFUL_TIER,
            new Item.Properties().attributes(SwordItem.createAttributes(COLORFUL_TIER,3.0f,-2.4f)))
    );
    public static final Supplier<AxeItem> COLORFUL_AXE = TOOL_ITEMS.register("colorful_axe", ()->new AxeItem(
            COLORFUL_TIER,
            new Item.Properties().attributes(AxeItem.createAttributes(COLORFUL_TIER,5.5f,-1.6f)))
    );
    public static final Supplier<PickaxeItem> COLORFUL_PICKAXE = TOOL_ITEMS.register("colorful_pickaxe", ()->new PickaxeItem(
            COLORFUL_TIER,
            new Item.Properties().attributes(PickaxeItem.createAttributes(COLORFUL_TIER,1.0f,-2.8f)))
    );
    public static final Supplier<ShovelItem> COLORFUL_SHOVEL = TOOL_ITEMS.register("colorful_shovel", ()->new ShovelItem(
            COLORFUL_TIER,
            new Item.Properties().attributes(ShovelItem.createAttributes(COLORFUL_TIER,1.5f,-3f)))
    );
    public static final Supplier<HoeItem> COLORFUL_HOE = TOOL_ITEMS.register("colorful_hoe", ()->new HoeItem(
            COLORFUL_TIER,
            new Item.Properties().attributes(HoeItem.createAttributes(COLORFUL_TIER,3.5f,-2.8f)))
    );

    static {
        tintableItems.addAll(TOOL_ITEMS.getEntries());
    }

    public static void register(IEventBus eventBus){
        TOOL_ITEMS.register(eventBus);
    }
}
