package de.mittelalter.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ArrowSlitBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<ArrowSlitBlock> CODEC = simpleCodec(ArrowSlitBlock::new);
    private static final VoxelShape NORTH_SOUTH_SHAPE = shape(ArrowSlitGeometry.NORTH_SOUTH_FRAME);
    private static final VoxelShape EAST_WEST_SHAPE = shape(ArrowSlitGeometry.EAST_WEST_FRAME);

    public ArrowSlitBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    private static VoxelShape shape(Iterable<ArrowSlitGeometry.Box> boxes) {
        VoxelShape result = Shapes.empty();
        for (ArrowSlitGeometry.Box box : boxes) {
            result = Shapes.or(result, Block.box(
                    box.minX(), box.minY(), box.minZ(), box.maxX(), box.maxY(), box.maxZ()));
        }
        return result;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING).getAxis() == Direction.Axis.Z ? NORTH_SOUTH_SHAPE : EAST_WEST_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
