package asof.colorful_ore.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.HashSet;

public class ColorfulBlock extends Block implements EntityBlock {
    public static HashSet<ColorfulBlock> allColorfulBlocks = new HashSet<>();

    public ColorfulBlock(Properties properties) {
        super(properties);
        allColorfulBlocks.add(this);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        BlockEntity be = level.getBlockEntity(pos);
        if(!(be instanceof ColorfulBlockEntity)) return;

        DyedItemColor color = stack.getComponents().get(DataComponents.DYED_COLOR);
        ((ColorfulBlockEntity) be).setTint(color != null ? color.rgb() : Color.RED.getRGB());
    }

    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        return new ColorfulBlockEntity(pos,state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        ItemStack item = super.getCloneItemStack(state,target,level,pos,player);
        BlockEntity be = level.getBlockEntity(pos);
        if(be == null) return item;
        int color = ((ColorfulBlockEntity) be).getTint();
        item.set(DataComponents.DYED_COLOR,new DyedItemColor(color,true));
        return item;
    }
}
