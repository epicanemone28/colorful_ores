package asof.colorful_ore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;

public class ColorfulBlockHelper {
    public static HashSet<Block> allColorfulBlocks = new HashSet<>();

    public static @NotNull ItemStack cloneStack(ItemStack item, LevelReader level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if(be == null) return item;
        int color = ((ColorfulBlockEntity) be).getTint();
        item.set(DataComponents.DYED_COLOR,new DyedItemColor(color,false));
        return item;
    }

    public static void setupBlockEntity(ItemStack stack, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if(!(be instanceof ColorfulBlockEntity)) return;

        DyedItemColor color = stack.getComponents().get(DataComponents.DYED_COLOR);
        ((ColorfulBlockEntity) be).setTint(color != null ? color.rgb() : Color.RED.getRGB());
    }
}
