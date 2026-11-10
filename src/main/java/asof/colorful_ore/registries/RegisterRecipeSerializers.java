package asof.colorful_ore.registries;

import asof.colorful_ore.ColorfulOres;
import asof.colorful_ore.recipes.ColorMixingShapedRecipe;
import asof.colorful_ore.recipes.ColorMixingSmeltingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ColorfulOres.MODID);

    public static final Supplier<RecipeSerializer<?>> COLOR_MIXING_SHAPED =
            RECIPE_SERIALIZERS.register("color_mixing_shaped", ColorMixingShapedRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<?>> COLOR_MIXING_SMELTING =
            RECIPE_SERIALIZERS.register("color_mixing_smelting", ()->new SimpleCookingSerializer<>(ColorMixingSmeltingRecipe::new,200));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
