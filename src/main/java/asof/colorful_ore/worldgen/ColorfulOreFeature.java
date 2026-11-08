package asof.colorful_ore.worldgen;

import asof.colorful_ore.blocks.ColorfulBlockEntity;
import com.mojang.serialization.Codec;

import java.util.BitSet;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;

// applies a random color to a blob of ore blocks in the area.
public class ColorfulOreFeature extends Feature<OreConfiguration> {
    public ColorfulOreFeature(final Codec<OreConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(final FeaturePlaceContext<OreConfiguration> context) {
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        WorldGenLevel level = context.level();
        OreConfiguration config = context.config();
        float dir = random.nextFloat() * (float) Math.PI;
        float spreadXY = config.size / 8.0F;
        int maxRadius = Mth.ceil((config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double x0 = origin.getX() + Math.sin(dir) * spreadXY;
        double x1 = origin.getX() - Math.sin(dir) * spreadXY;
        double z0 = origin.getZ() + Math.cos(dir) * spreadXY;
        double z1 = origin.getZ() - Math.cos(dir) * spreadXY;

        double y0 = origin.getY() + random.nextInt(3) - 2;
        double y1 = origin.getY() + random.nextInt(3) - 2;
        int xStart = origin.getX() - Mth.ceil(spreadXY) - maxRadius;
        int yStart = origin.getY() - 2 - maxRadius;
        int zStart = origin.getZ() - Mth.ceil(spreadXY) - maxRadius;
        int sizeXZ = 2 * (Mth.ceil(spreadXY) + maxRadius);
        int sizeY = 2 * (2 + maxRadius);

        int color = FastColor.ARGB32.color(random.nextInt(256),random.nextInt(256),random.nextInt(256));

        for (int xprobe = xStart; xprobe <= xStart + sizeXZ; xprobe++) {
            for (int zprobe = zStart; zprobe <= zStart + sizeXZ; zprobe++) {
                if (yStart <= level.getHeight(Types.OCEAN_FLOOR_WG, xprobe, zprobe)) {
                    return this.doPlace(level, random, config, x0, x1, z0, z1, y0, y1, xStart, yStart, zStart, sizeXZ, sizeY, color);
                }
            }
        }

        return false;
    }

    protected boolean doPlace(
            final WorldGenLevel level,
            final RandomSource random,
            final OreConfiguration config,
            final double x0,
            final double x1,
            final double z0,
            final double z1,
            final double y0,
            final double y1,
            final int xStart,
            final int yStart,
            final int zStart,
            final int sizeXZ,
            final int sizeY,
            final int color
    ) {
        int placed = 0;
        BitSet tested = new BitSet(sizeXZ * sizeY * sizeXZ);
        MutableBlockPos orePos = new MutableBlockPos();
        int size = config.size;
        double[] data = new double[size * 4];

        for (int i = 0; i < size; i++) {
            float step = (float)i / size;
            double xx = Mth.lerp(step, x0, x1);
            double yy = Mth.lerp(step, y0, y1);
            double zz = Mth.lerp(step, z0, z1);
            double ss = random.nextDouble() * size / 16.0;
            double r = ((Mth.sin((float) Math.PI * step) + 1.0F) * ss + 1.0) / 2.0;
            data[i * 4    ] = xx;
            data[i * 4 + 1] = yy;
            data[i * 4 + 2] = zz;
            data[i * 4 + 3] = r;
        }

        for (int i1 = 0; i1 < size - 1; i1++) {
            if (!(data[i1 * 4 + 3] <= 0.0)) {
                for (int i2 = i1 + 1; i2 < size; i2++) {
                    if (!(data[i2 * 4 + 3] <= 0.0)) {
                        double dx = data[i1 * 4    ] - data[i2 * 4    ];
                        double dy = data[i1 * 4 + 1] - data[i2 * 4 + 1];
                        double dz = data[i1 * 4 + 2] - data[i2 * 4 + 2];
                        double dr = data[i1 * 4 + 3] - data[i2 * 4 + 3];
                        if (dr * dr > dx * dx + dy * dy + dz * dz) {
                            if (dr > 0.0) {
                                data[i2 * 4 + 3] = -1.0;
                            } else {
                                data[i1 * 4 + 3] = -1.0;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            double r = data[i * 4 + 3];
            if (!(r < 0.0)) {
                double xx = data[i * 4    ];
                double yy = data[i * 4 + 1];
                double zz = data[i * 4 + 2];
                int xMin = Math.max(Mth.floor(xx - r), xStart);
                int yMin = Math.max(Mth.floor(yy - r), yStart);
                int zMin = Math.max(Mth.floor(zz - r), zStart);
                int xMax = Math.max(Mth.floor(xx + r), xMin);
                int yMax = Math.max(Mth.floor(yy + r), yMin);
                int zMax = Math.max(Mth.floor(zz + r), zMin);
                for (int x = xMin; x <= xMax; x++) {
                    double xd = (x + 0.5 - xx) / r;
                    if (xd * xd < 1.0) {
                        for (int y = yMin; y <= yMax; y++) {
                            double yd = (y + 0.5 - yy) / r;
                            if (xd * xd + yd * yd < 1.0) {
                                for (int z = zMin; z <= zMax; z++) {
                                    double zd = (z + 0.5 - zz) / r;
                                    if (xd * xd + yd * yd + zd * zd < 1.0 && !level.isOutsideBuildHeight(y)) {
                                        int bitSetIndex = x - xStart + (y - yStart) * sizeXZ + (z - zStart) * sizeXZ * sizeY;
                                        if (!tested.get(bitSetIndex)) {
                                            tested.set(bitSetIndex);
                                            orePos.set(x, y, z);
                                            if (level.ensureCanWrite(orePos)) {
                                                BlockState blockState = level.getBlockState(orePos);

                                                for (TargetBlockState targetState : config.targetStates) {
                                                    if (canPlaceOre(blockState, level::getBlockState, random, config, targetState, orePos)) {
                                                        level.setBlock(orePos, targetState.state, Block.UPDATE_ALL);

                                                        BlockEntity be = level.getBlockEntity(orePos);
                                                        if (!(be instanceof ColorfulBlockEntity)) continue;
                                                        ((ColorfulBlockEntity) be).setTint(color);
                                                        level.getLevel().sendBlockUpdated(orePos, targetState.state, targetState.state, Block.UPDATE_ALL);
                                                        placed++;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return placed > 0;
    }

    public static boolean canPlaceOre(
            final BlockState orePosState,
            final Function<BlockPos, BlockState> blockGetter,
            final RandomSource random,
            final OreConfiguration config,
            final TargetBlockState targetState,
            final MutableBlockPos orePos
    ) {
        if (!targetState.target.test(orePosState, random)) {
            return false;
        } else {
            return shouldSkipAirCheck(random, config.discardChanceOnAirExposure) || !isAdjacentToAir(blockGetter, orePos);
        }
    }

    protected static boolean shouldSkipAirCheck(final RandomSource random, final float discardChanceOnAirExposure) {
        if (discardChanceOnAirExposure <= 0.0F) {
            return true;
        } else {
            return !(discardChanceOnAirExposure >= 1.0F) && random.nextFloat() >= discardChanceOnAirExposure;
        }
    }
}
