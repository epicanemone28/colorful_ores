package asof.colorful_ore.recipes.emi;

import asof.colorful_ore.recipes.ColorMixingSmeltingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.render.EmiTooltipComponents;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class ColorMixingSmeltingEmiRecipe implements EmiRecipe {
    protected final int unique = 4;

    EmiIngredient input;
    EmiStack output;
    ResourceLocation id;
    int duration;
    float experience;
    boolean infiniBurn = false;

    public ColorMixingSmeltingEmiRecipe(ColorMixingSmeltingRecipe recipe, ResourceLocation id){
        input = EmiIngredient.of(recipe.getIngredients().getFirst());
        output = EmiStack.of(recipe.result);
        this.id = id;
        this.duration = recipe.getCookingTime();
        this.experience = recipe.getExperience();
    }

    public int getRandomColor(Random random){
        return FastColor.ARGB32.color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }

    public EmiStack getColoredItem(EmiStack item, int rgb){
        ItemStack stack = item.getItemStack();
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(rgb,false));
        return EmiStack.of(stack);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return VanillaEmiRecipeCategories.SMELTING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 82;
    }

    @Override
    public int getDisplayHeight() {
        return 38;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {


        widgets.addFillingArrow(24, 5, 50 * this.duration).tooltip((mx, my) ->
                List.of(EmiTooltipComponents.of(Component.translatable("emi.cooking.time", duration / 20f)))
        );
        if (infiniBurn) {
            widgets.addTexture(EmiTexture.FULL_FLAME, 1, 24);
        } else {
            widgets.addTexture(EmiTexture.EMPTY_FLAME, 1, 24);
            widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1, 24, 4000, false, true, true);
        }
        widgets.addText(Component.translatable("emi.cooking.experience", experience), 26, 28, -1, true);
        widgets.addGeneratedSlot(r->{
            List<EmiStack> potentialStacks = input.getEmiStacks();
            return getColoredItem(potentialStacks.get(r.nextInt(potentialStacks.size())),getRandomColor(r));
        }, unique, 0,4);
        widgets.addGeneratedSlot(r->{
            r.nextInt();
            return getColoredItem(output,getRandomColor(r));
        }, unique, 56,0).large(true).recipeContext(this);

    }
}
