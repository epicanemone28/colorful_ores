package asof.colorful_ore.misc_providers;

import asof.colorful_ore.ColorfulOres;
import asof.colorful_ore.blocks.ColorfulBlockEntity;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// Code adapted from vanilla's EnchantRandomlyFunction class.
// LootItemConditionalFunction is an abstract class, not an interface, so we cannot use a record here.
public class ColorfulBlockLootFunction extends LootItemConditionalFunction {
    // Our codec.

    public static final MapCodec<ColorfulBlockLootFunction> CODEC =
            // #commonFields adds the conditions field.
            RecordCodecBuilder.mapCodec(inst -> commonFields(inst).apply(inst, ColorfulBlockLootFunction::new));

    public ColorfulBlockLootFunction(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    public @NotNull LootItemFunctionType<? extends LootItemConditionalFunction> getType() {
        return ColorfulOres.COPY_TINT_TO_ITEM.get();
    }

    @Override
    public @NotNull ItemStack run(ItemStack stack, LootContext context) {
        BlockEntity be = context.getParam(LootContextParams.BLOCK_ENTITY);
        int rgb = ((ColorfulBlockEntity) be).rgb;
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(rgb,false));
        return stack;
    }
}