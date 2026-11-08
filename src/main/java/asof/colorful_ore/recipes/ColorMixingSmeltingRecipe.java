package asof.colorful_ore.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

import static asof.colorful_ore.recipes.RecipeSerializers.COLOR_MIXING_SMELTING;

public class ColorMixingSmeltingRecipe extends SmeltingRecipe {

    public ColorMixingSmeltingRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider lookup) {
        ItemStack item = this.getResultItem(lookup).copy();
        ItemStack potentialColorfulIngredient = input.getItem(0);
        if(!potentialColorfulIngredient.isEmpty() && potentialColorfulIngredient.has(DataComponents.DYED_COLOR)){
            item.set(DataComponents.DYED_COLOR,potentialColorfulIngredient.get(DataComponents.DYED_COLOR));
        }
        return item;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return COLOR_MIXING_SMELTING.get();
    }
}
