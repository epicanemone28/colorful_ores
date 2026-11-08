package asof.colorful_ore.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

public class ColorMixingShapedRecipe extends ShapedRecipe {
    public ColorMixingShapedRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack result, boolean showNotification) {
        super(group, category, pattern, result, showNotification);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ItemStack assemble(CraftingInput input, HolderLookup.Provider lookup) {
        ItemStack item = this.getResultItem(lookup).copy();
        int r = 0;
        int g = 0;
        int b = 0;
        int items = 0;

        for(int l=0;l<input.size();l++){
            ItemStack potentialColorfulIngredient = input.getItem(l);
            if(potentialColorfulIngredient.isEmpty()) continue;
            if(!potentialColorfulIngredient.has(DataComponents.DYED_COLOR)) continue;
            int color = DyedItemColor.getOrDefault(potentialColorfulIngredient, Color.RED.getRGB());
            r += FastColor.ARGB32.red(color);
            g += FastColor.ARGB32.green(color);
            b += FastColor.ARGB32.blue(color);
            items++;
        }

        if(items>0){
            int rgb = FastColor.ARGB32.color(r/items,g/items,b/items);
            item.set(DataComponents.DYED_COLOR,new DyedItemColor(rgb,true));
        }

        return item;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.COLOR_MIXING_SHAPED.get();
    }

    public static class Serializer implements RecipeSerializer<ColorMixingShapedRecipe> {
        public static final MapCodec<ColorMixingShapedRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340778_ -> p_340778_.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(ColorMixingShapedRecipe::getGroup),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(ColorMixingShapedRecipe::category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
                                Codec.BOOL.optionalFieldOf("show_notification", Boolean.TRUE).forGetter(ColorMixingShapedRecipe::showNotification)
                        )
                        .apply(p_340778_, ColorMixingShapedRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ColorMixingShapedRecipe> STREAM_CODEC = StreamCodec.of(
                ColorMixingShapedRecipe.Serializer::toNetwork, ColorMixingShapedRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<ColorMixingShapedRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ColorMixingShapedRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ColorMixingShapedRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            CraftingBookCategory craftingbookcategory = buffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            boolean flag = buffer.readBoolean();
            return new ColorMixingShapedRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, flag);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, ColorMixingShapedRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeEnum(recipe.category());
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
            buffer.writeBoolean(recipe.showNotification());
        }
    }
}
