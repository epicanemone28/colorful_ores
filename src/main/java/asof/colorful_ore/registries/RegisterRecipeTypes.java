package asof.colorful_ore.registries;

import asof.colorful_ore.ColorfulOres;
import asof.colorful_ore.recipes.ColorMixingShapedRecipe;
import asof.colorful_ore.recipes.ColorMixingSmeltingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ColorfulOres.MODID);

    @SuppressWarnings({"UnusedDeclaration"})
    public static final Supplier<RecipeType<ColorMixingShapedRecipe>> COLOR_MIXING_SHAPED =
            RECIPE_TYPES.register("color_mixing_shaped", () -> registerRecipeType("color_mixing_shaped"));
    @SuppressWarnings({"UnusedDeclaration"})
    public static final Supplier<RecipeType<ColorMixingSmeltingRecipe>> COLOR_MIXING_SMELTING =
            RECIPE_TYPES.register("color_mixing_smelting", () -> registerRecipeType("color_mixing_smelting"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return ColorfulOres.MODID + ":" + identifier;
            }
        };
    }

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}
