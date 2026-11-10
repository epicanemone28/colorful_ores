package asof.colorful_ore.recipes.emi;

import asof.colorful_ore.recipes.ColorMixingShapedRecipe;
import asof.colorful_ore.recipes.ColorMixingSmeltingRecipe;
import asof.colorful_ore.registries.RegisterRecipeTypes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@EmiEntrypoint
public final class ColorfulOresEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        List<RecipeHolder<ColorMixingShapedRecipe>> colorMixingShapedRecipes = registry.getRecipeManager().getAllRecipesFor(RegisterRecipeTypes.COLOR_MIXING_SHAPED.get());
        for(RecipeHolder<ColorMixingShapedRecipe> recipe : colorMixingShapedRecipes){
            registry.addRecipe(new ColorMixingShapedEmiRecipe(recipe.value(),recipe.id()));
        }
        List<RecipeHolder<ColorMixingSmeltingRecipe>> colorMixingSmeltingRecipes = registry.getRecipeManager().getAllRecipesFor(RegisterRecipeTypes.COLOR_MIXING_SMELTING.get());
        for(RecipeHolder<ColorMixingSmeltingRecipe> recipe : colorMixingSmeltingRecipes){
            registry.addRecipe(new ColorMixingSmeltingEmiRecipe(recipe.value(),recipe.id()));
        }
    }

    @Override
    public void initialize(EmiInitRegistry registry) {
        EmiPlugin.super.initialize(registry);
    }
}
