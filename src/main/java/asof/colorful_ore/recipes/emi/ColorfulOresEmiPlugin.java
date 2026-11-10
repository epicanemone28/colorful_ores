package asof.colorful_ore.recipes.emi;

import asof.colorful_ore.recipes.ColorMixingShapedRecipe;
import asof.colorful_ore.recipes.ColorMixingSmeltingRecipe;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.HashSet;
import java.util.List;

@EmiEntrypoint
public final class ColorfulOresEmiPlugin implements EmiPlugin {

    public static HashSet<ResourceLocation> IDS_TO_REMOVE = new HashSet<>();

    @Override
    public void register(EmiRegistry registry) {
        IDS_TO_REMOVE.clear();
        List<RecipeHolder<CraftingRecipe>> craftingRecipes = registry.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
        for(RecipeHolder<CraftingRecipe> recipe : craftingRecipes){
            if(recipe.value() instanceof ColorMixingShapedRecipe shapedColor){
                IDS_TO_REMOVE.add(recipe.id());
                registry.addRecipe(new ColorMixingShapedEmiRecipe(shapedColor,recipe.id()));
            }
        }
        List<RecipeHolder<SmeltingRecipe>> smeltingRecipes = registry.getRecipeManager().getAllRecipesFor(RecipeType.SMELTING);
        for(RecipeHolder<SmeltingRecipe> recipe : smeltingRecipes){
            if(recipe.value() instanceof ColorMixingSmeltingRecipe shapedColor){
                IDS_TO_REMOVE.add(recipe.id());
                registry.addRecipe(new ColorMixingSmeltingEmiRecipe(shapedColor,recipe.id()));
            }
        }

        registry.removeRecipes(r->
                IDS_TO_REMOVE.contains(r.getId())
                && !(r instanceof ColorMixingSmeltingEmiRecipe)
                && !(r instanceof ColorMixingShapedEmiRecipe)
        );
    }

    @Override
    public void initialize(EmiInitRegistry registry) {
        EmiPlugin.super.initialize(registry);
    }
}
