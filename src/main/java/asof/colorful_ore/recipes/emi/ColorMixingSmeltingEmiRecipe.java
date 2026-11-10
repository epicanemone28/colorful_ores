package asof.colorful_ore.recipes.emi;

import asof.colorful_ore.misc_providers.ColorfulBlockTintProvider;
import asof.colorful_ore.recipes.ColorMixingSmeltingRecipe;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ColorMixingSmeltingEmiRecipe extends EmiPatternCraftingRecipe {
    public ColorMixingSmeltingEmiRecipe(ColorMixingSmeltingRecipe recipe, ResourceLocation id) {
        super(List.of(EmiIngredient.of(recipe.getIngredients().getFirst())), EmiStack.of(recipe.result), id);
    }

    public int getRandomColor(Random random){
        return FastColor.ARGB32.color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
    }

    public EmiStack getColoredItem(EmiStack item, int rgb){
        ItemStack stack = item.getItemStack();
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(rgb,false));
        return EmiStack.of(stack);
    }

    public int[] getColors(Random r){
        int[] colors = new int[this.getInputs().size()];
        for (int i = 0; i < this.getInputs().size(); i++) {
            colors[i] = getRandomColor(r);
        }
        return colors;
    }

    public List<EmiStack> getIngredients(Random r){
        List<EmiStack> stacks = new ArrayList<>();
        for(int i = 0; i<this.getInputs().size(); i++){
            List<EmiStack> potentialStacks = this.getInputs().get(i).getEmiStacks();
            EmiStack stack = potentialStacks.get(r.nextInt(potentialStacks.size()));
            stacks.add(stack);
        }
        return stacks;
    }


    @Override
    public SlotWidget getInputWidget(int slot, int x, int y) {
        if(slot>=this.getInputs().size()) return new SlotWidget(EmiStack.EMPTY,x,y);

        List<EmiStack> potentialStacks = this.getInputs().get(slot).getEmiStacks();
        if(potentialStacks.stream().map(EmiStack::getItemStack).map(ItemStack::getItem).noneMatch(ColorfulBlockTintProvider::itemHasTint)){
            return new SlotWidget(this.getInputs().get(slot),x,y);
        }
        return new GeneratedSlotWidget(r -> {
            int[] colors = getColors(r);
            EmiStack stack = getIngredients(r).get(slot);
            if (ColorfulBlockTintProvider.itemHasTint(stack.getItemStack().getItem())) {
                stack = getColoredItem(stack, colors[slot]);
            }
            return stack;
        },unique,x,y);
    }

    @Override
    public SlotWidget getOutputWidget(int x, int y) {
        return new GeneratedSlotWidget(r -> {
            EmiStack result = this.getOutputs().getFirst();
            int[] colors = getColors(r);
            EmiStack stack = this.getIngredients(r).getFirst();
            if(ColorfulBlockTintProvider.itemHasTint(stack.getItemStack().getItem())){
                result = getColoredItem(result,colors[0]);
            }
            return result;
        },unique,x,y);
    }




}
