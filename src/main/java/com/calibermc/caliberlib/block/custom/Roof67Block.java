package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.RoofShape;
import com.calibermc.caliberlib.block.properties.ModBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.stream.Stream;

import static com.calibermc.caliberlib.block.properties.ModBlockStateProperties.isSide;

public class Roof67Block extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<RoofShape> TYPE = ModBlockStateProperties.ROOF_SHAPE;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public Roof67Block(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, RoofShape.STRAIGHT)
                .setValue(HALF, Half.TOP)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, HALF, WATERLOGGED);
    }

    public static final Map<Direction, VoxelShape> TOP_OUTER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(8, 0.01, 8, 16, 16, 16),
            Direction.SOUTH, Block.box(0, 0.01, 0, 8, 16, 8),
            Direction.EAST, Block.box(0, 0.01, 8, 8, 16, 16),
            Direction.WEST, Block.box(8, 0.01, 0, 16, 16, 8)));

    public static final Map<Direction, VoxelShape> TOP_OUTER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(0, 0.01, 8, 8, 16, 16),
            Direction.SOUTH, Block.box(8, 0.01, 0, 16, 16, 8),
            Direction.EAST, Block.box(0, 0.01, 0, 8, 16, 8),
            Direction.WEST, Block.box(8, 0.01, 8, 16, 16, 16)));

    public static final Map<Direction, VoxelShape> TOP_INNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.EAST, Shapes.join(Block.box(0, 0.01, 8, 16, 16, 16), Block.box(0, 0.01, 0, 8, 16, 8), BooleanOp.OR),
            Direction.NORTH, Shapes.join(Block.box(8, 0.01, 0, 16, 16, 16), Block.box(0, 0.01, 8, 8, 16, 16), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(0, 0.01, 0, 16, 16, 8), Block.box(8, 0.01, 8, 16, 16, 16), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 8, 16, 16), Block.box(8, 0.01, 0, 16, 16, 8), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> TOP_INNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.WEST, Shapes.join(Block.box(8, 0.01, 0, 16, 16, 16), Block.box(0, 0.01, 8, 8, 16, 16), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 16, 8), Block.box(8, 0.01, 8, 16, 16, 16), BooleanOp.OR),
            Direction.NORTH, Shapes.join(Block.box(0, 0.01, 8, 16, 16, 16), Block.box(0, 0.01, 0, 8, 16, 8), BooleanOp.OR),
            Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 8, 16, 16), Block.box(8, 0.01, 0, 16, 16, 8), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> TOP_STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(0, 0.01, 8, 16, 16, 16),
            Direction.SOUTH, Block.box(0, 0.01, 0, 16, 16, 8),
            Direction.EAST, Block.box(0, 0.01, 0, 8, 16, 16),
            Direction.WEST, Block.box(8, 0.01, 0, 16, 16, 16)));

    public static final Map<Direction, VoxelShape> BOTTOM_STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Shapes.join(Block.box(0, 0.01, 8, 16, 16, 16), Block.box(0, 0.01, 0, 16, 8, 8), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 16, 8), Block.box(0, 0.01, 8, 16, 8, 16), BooleanOp.OR),
            Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 8, 16, 16), Block.box(8, 0.01, 0, 16, 8, 16), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(8, 0.01, 0, 16, 16, 16), Block.box(0, 0.01, 0, 8, 8, 16), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> BOTTOM_OUTER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 0, 8, 16, 8), BooleanOp.OR),
            Direction.NORTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 8, 16, 16, 16), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 0, 16, 16, 8), BooleanOp.OR),
            Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 8, 8, 16, 16), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> BOTTOM_OUTER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 0, 8, 16, 8), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 8, 16, 16, 16), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 0, 16, 16, 8), BooleanOp.OR),
            Direction.NORTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 8, 8, 16, 16), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> BOTTOM_INNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.SOUTH, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(0, 8.01, 0, 8, 16, 16),
                    Block.box(8, 8.01, 0, 16, 16, 8)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
            Direction.NORTH, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(8, 8.01, 0, 16, 16, 16),
                    Block.box(0, 8.01, 8, 8, 16, 16)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
            Direction.WEST, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(0, 8.01, 0, 16, 16, 8),
                    Block.box(8, 8.01, 8, 16, 16, 16)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
            Direction.EAST, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(0, 8.01, 8, 16, 16, 16),
                    Block.box(0, 8.01, 0, 8, 16, 8)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get()));

    public static final Map<Direction, VoxelShape> BOTTOM_INNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.EAST, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(0, 8.01, 0, 8, 16, 16),
                    Block.box(8, 8.01, 0, 16, 16, 8)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
            Direction.WEST, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(8, 8.01, 0, 16, 16, 16),
                    Block.box(0, 8.01, 8, 8, 16, 16)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
            Direction.SOUTH, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(0, 8.01, 0, 16, 16, 8),
                    Block.box(8, 8.01, 8, 16, 16, 16)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
            Direction.NORTH, Stream.of(
                    Block.box(0, 0.01, 0, 16, 8, 16),
                    Block.box(0, 8.01, 8, 16, 16, 16),
                    Block.box(0, 8.01, 0, 8, 16, 8)
            ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get()));

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        Direction direction = blockState.getValue(FACING);
        Half half = blockState.getValue(HALF);
        RoofShape type = blockState.getValue(TYPE);

        if (half == Half.TOP) {
            return switch (type) {
                case OUTER_LEFT -> TOP_OUTER_LEFT_SHAPE.get(direction);
                case OUTER_RIGHT -> TOP_OUTER_RIGHT_SHAPE.get(direction);
                case INNER_LEFT -> TOP_INNER_LEFT_SHAPE.get(direction);
                case INNER_RIGHT -> TOP_INNER_RIGHT_SHAPE.get(direction);
                case STRAIGHT -> TOP_STRAIGHT_SHAPE.get(direction);
            };
        } else {
            return switch (type) {
                case OUTER_LEFT -> BOTTOM_OUTER_LEFT_SHAPE.get(direction);
                case OUTER_RIGHT -> BOTTOM_OUTER_RIGHT_SHAPE.get(direction);
                case INNER_LEFT -> BOTTOM_INNER_LEFT_SHAPE.get(direction);
                case INNER_RIGHT -> BOTTOM_INNER_RIGHT_SHAPE.get(direction);
                case STRAIGHT -> BOTTOM_STRAIGHT_SHAPE.get(direction);
            };

        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        BlockState blockstate = level.getBlockState(blockpos);

        if (blockstate.is(this)) {
            return blockstate.setValue(HALF, Half.BOTTOM).setValue(WATERLOGGED, Boolean.FALSE);
        } else {
            FluidState fluidstate = level.getFluidState(blockpos);
            Direction facing = blockPlaceContext.getHorizontalDirection().getOpposite();
            Half half = (blockPlaceContext.getClickedFace() != Direction.UP && (blockPlaceContext.getClickedFace() == Direction.DOWN ||
                    !(blockPlaceContext.getClickLocation().y - (double) blockpos.getY() > 0.5D))) ? Half.BOTTOM : Half.TOP;

            BlockState blockstate1 = this.defaultBlockState()
                    .setValue(FACING, facing)
                    .setValue(HALF, half)
                    .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

            // Determine RoofShape based on surrounding blocks
            RoofShape roofShape = getRoofShape(blockstate1, level, blockpos);
            blockstate1 = blockstate1.setValue(TYPE, roofShape);

            return blockstate1;
        }
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        ItemStack itemstack = blockPlaceContext.getItemInHand();
        Half Half = blockState.getValue(HALF);
        Direction clickedFace = blockPlaceContext.getClickedFace();
        if (Half != net.minecraft.world.level.block.state.properties.Half.BOTTOM && itemstack.is(this.asItem())) {
            return isSide(clickedFace);
        }
        return false;
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    public BlockState updateShape(BlockState blockState, Direction pFacing, BlockState pFacingState, LevelAccessor levelAccessor, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        if (pFacing.getAxis().isHorizontal()) {
            // Set the TYPE value based on the RoofShape
            blockState = blockState.setValue(TYPE, getRoofShape(blockState, levelAccessor, pCurrentPos));

        } else {
            blockState = super.updateShape(blockState, pFacing, pFacingState, levelAccessor, pCurrentPos, pFacingPos);
        }

        return blockState;
    }

    private static RoofShape getRoofShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        Direction direction = blockState.getValue(FACING);
        BlockState blockstate = blockGetter.getBlockState(blockPos.relative(direction));
        if (isRoof(blockstate) && blockState.getValue(HALF) == blockstate.getValue(HALF)) {
            Direction direction1 = blockstate.getValue(FACING);
            if (direction1.getAxis() != blockState.getValue(FACING).getAxis() && canTakeShape(blockState, blockGetter, blockPos, direction1.getOpposite())) {
                if (direction1 == direction.getCounterClockWise()) {
                    return RoofShape.INNER_LEFT;
                }

                return RoofShape.INNER_RIGHT;
            }
        }

        BlockState blockstate1 = blockGetter.getBlockState(blockPos.relative(direction.getOpposite()));
        if (isRoof(blockstate1) && blockState.getValue(HALF) == blockstate1.getValue(HALF)) {
            Direction direction2 = blockstate1.getValue(FACING);
            if (direction2.getAxis() != blockState.getValue(FACING).getAxis() && canTakeShape(blockState, blockGetter, blockPos, direction2)) {
                if (direction2 == direction.getCounterClockWise()) {
                    return RoofShape.OUTER_LEFT;
                }

                return RoofShape.OUTER_RIGHT;
            }
        }

        return RoofShape.STRAIGHT;
    }

    public static boolean isRoof(BlockState blockState) {
        return blockState.getBlock() instanceof Roof67Block;
    }

    private static boolean canTakeShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction pFace) {
        BlockState blockstate = blockGetter.getBlockState(blockPos.relative(pFace));
        return !isRoof(blockstate) || blockstate.getValue(FACING) != blockState.getValue(FACING) || blockstate.getValue(HALF) != blockState.getValue(HALF);
    }


    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        return SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluid);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(player, world, pos, state, fluid);
    }
    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathType) {
        return switch (pathType) {
            case LAND -> false;
            case WATER -> blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}