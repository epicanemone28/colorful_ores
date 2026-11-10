package asof.colorful_ore.recipes.emi;

import asof.colorful_ore.misc_providers.ColorfulBlockTintProvider;
import asof.colorful_ore.recipes.ColorMixingShapedRecipe;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ColorMixingShapedEmiRecipe extends EmiPatternCraftingRecipe {
    public ColorMixingShapedEmiRecipe(ColorMixingShapedRecipe recipe, ResourceLocation id) {
        super(padIngredients(recipe), EmiStack.of(recipe.result), id);
    }

    // this code was largely taken from emi directly.. is that fine with the MIT license?
    public static List<EmiIngredient> padIngredients(ColorMixingShapedRecipe recipe){
        List<EmiIngredient> list = new ArrayList<>(9);
        int xOffset = 0;
        int yOffset = 0;
        if(recipe.getWidth() == 1) xOffset++;
        if(recipe.getHeight() == 1) yOffset++;
        int i = 0;

        for (int y = -yOffset; y < 3 - yOffset; y++) {
            for (int x = -xOffset; x < 3 - xOffset; x++) {
                if (x<0 || y<0 || x >= recipe.getWidth() || y >= recipe.getHeight() || i >= recipe.getIngredients().size()) {
                    list.add(EmiStack.EMPTY);
                } else {
                    list.add(EmiIngredient.of(recipe.getIngredients().get(i++)));
                }
            }
        }
        return list;
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
        int[] colors = new int[9];
        for (int i = 0; i < 9; i++) {
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
            int red = 0;
            int green = 0;
            int blue = 0;
            int items = 0;

            int[] colors = getColors(r);
            List<EmiStack> stacks = getIngredients(r);

            for(int i = 0; i<stacks.size(); i++){
                if(ColorfulBlockTintProvider.itemHasTint(stacks.get(i).getItemStack().getItem())){
                    int color = colors[i];
                    red += FastColor.ARGB32.red(color);
                    green += FastColor.ARGB32.green(color);
                    blue += FastColor.ARGB32.blue(color);
                    items++;
                }
            }
            if(items==0){
                return this.getOutputs().getFirst();
            }
            int rgb = FastColor.ARGB32.color(red/items,green/items,blue/items);
            return getColoredItem(this.getOutputs().getFirst(),rgb);
        },unique,x,y);
    }




}
