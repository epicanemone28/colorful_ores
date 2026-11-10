package asof.colorful_ore.blocks;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

public class StepBlock extends StairBlock {
    public StepBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }

    public static final VoxelShape[] TOP_SHAPES = makeShapes(Shapes.empty(), OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
    public static final VoxelShape[] BOTTOM_SHAPES = makeShapes(Shapes.empty(), OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
    private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};

    private int getShapeIndex(BlockState p_56983_) {
        return p_56983_.getValue(SHAPE).ordinal() * 4 + p_56983_.getValue(FACING).get2DDataValue();
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (state.getValue(HALF) == Half.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[this.getShapeIndex(state)]];
    }

}
