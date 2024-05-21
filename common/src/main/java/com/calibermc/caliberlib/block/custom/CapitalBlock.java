package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.IntersectionShape;
import com.calibermc.caliberlib.block.shapes.TopBottomShape;
import com.calibermc.caliberlib.util.ModBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Stream;

import static net.minecraft.core.Direction.*;

public class CapitalBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<IntersectionShape> CONNECT = ModBlockStateProperties.INTERSECTION_SHAPE;
    public static final EnumProperty<TopBottomShape> TYPE = ModBlockStateProperties.TOP_BOTTOM_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static class ShapeHelper {

        private static VoxelShape combineShapes(BooleanOp op, VoxelShape... shapes) {
            return Stream.of(shapes).reduce((v1, v2) -> Shapes.join(v1, v2, op)).get();
        }

        private static final VoxelShape BASE_SHAPE_TOP = Block.box(0, 8, 0, 16, 16, 16);
        private static final VoxelShape CENTER_SHAPE_TOP = Block.box(4, 0, 4, 12, 8, 12);
        private static final VoxelShape X_SHAPE_TOP = Block.box(4, 0, 0, 12, 8, 16);
        private static final VoxelShape Y_SHAPE_TOP = Block.box(0, 0, 4, 16, 8, 12);

        private static final VoxelShape BASE_SHAPE_BOTTOM = Block.box(0, 0, 0, 16, 8, 16);
        private static final VoxelShape CENTER_SHAPE_BOTTOM = Block.box(4, 8, 4, 12, 16, 12);
        private static final VoxelShape X_SHAPE_BOTTOM = Block.box(4, 8, 0, 12, 16, 16);
        private static final VoxelShape Y_SHAPE_BOTTOM = Block.box(0, 8, 4, 16, 16, 12);

        public static final Map<Direction, VoxelShape> TOP_CONNECTED_SHAPE = createConnectedShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_CONNECTED_SHAPE = createConnectedShapes(false);
        public static final Map<Direction, VoxelShape> TOP_END_SHAPE = createEndShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_END_SHAPE = createEndShapes(false);
        public static final Map<Direction, VoxelShape> TOP_CORNER_SHAPE = createCornerShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_CORNER_SHAPE = createCornerShapes(false);
        public static final Map<Direction, VoxelShape> TOP_T_SHAPE = createTShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_T_SHAPE = createTShapes(false);
        public static final VoxelShape TOP_SINGLE_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, CENTER_SHAPE_TOP);
        public static final VoxelShape BOTTOM_SINGLE_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, CENTER_SHAPE_BOTTOM);
        public static final VoxelShape TOP_CROSS_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, X_SHAPE_TOP, Block.box(12, 0, 4, 16, 8, 12), Block.box(0, 0, 4, 4, 8, 12));
        public static final VoxelShape BOTTOM_CROSS_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, X_SHAPE_BOTTOM, Block.box(12, 8, 4, 16, 16, 12), Block.box(0, 8, 4, 4, 16, 12));

        public static final Map<Direction, VoxelShape> createConnectedShapes(boolean top) {
            if (top) {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, X_SHAPE_TOP),
                        SOUTH, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, X_SHAPE_TOP),
                        EAST, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Y_SHAPE_TOP),
                        WEST, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Y_SHAPE_TOP)
                ));
            } else {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, X_SHAPE_BOTTOM),
                        SOUTH, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, X_SHAPE_BOTTOM),
                        EAST, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Y_SHAPE_BOTTOM),
                        WEST, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Y_SHAPE_BOTTOM)
                ));
            }
        }

        public static final Map<Direction, VoxelShape> createEndShapes(boolean top) {
            if (top) {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(4, 0, 4, 12, 8, 16)),
                        SOUTH, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(4, 0, 0, 12, 8, 12)),
                        EAST, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(0, 0, 4, 12, 8, 12)),
                        WEST, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(4, 0, 4, 16, 8, 12))
                ));
            } else {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(4, 8, 4, 12, 16, 16)),
                        SOUTH, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(4, 8, 0, 12, 16, 12)),
                        EAST, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(0, 8, 4, 12, 16, 12)),
                        WEST, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(4, 8, 4, 16, 16, 12))
                ));
            }
        }

        public static final Map<Direction, VoxelShape> createCornerShapes(boolean top) {
            if (top) {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(4, 0, 4, 12, 8, 16), Block.box(0, 0, 4, 4, 8, 12)),
                        SOUTH, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(4, 0, 0, 12, 8, 12), Block.box(12, 0, 4, 16, 8, 12)),
                        EAST, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(0, 0, 4, 12, 8, 12), Block.box(4, 0, 0, 12, 8, 4)),
                        WEST, combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, Block.box(4, 0, 4, 16, 8, 12), Block.box(4, 0, 12, 12, 8, 16))
                ));
            } else {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(4, 8, 4, 12, 16, 16), Block.box(0, 8, 4, 4, 16, 12)),
                        SOUTH, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(4, 8, 0, 12, 16, 12), Block.box(12, 8, 4, 16, 16, 12)),
                        EAST, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(0, 8, 4, 12, 16, 12), Block.box(4, 8, 0, 12, 16, 4)),
                        WEST, combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, Block.box(4, 8, 4, 16, 16, 12), Block.box(4, 8, 12, 12, 16, 16))
                ));
            }
        }

        public static final Map<Direction, VoxelShape> createTShapes(boolean top) {
            if (top) {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, Block.box(4, 0, 12, 12, 8, 16), BASE_SHAPE_TOP, Block.box(0, 0, 4, 16, 8, 12)),
                        SOUTH, combineShapes(BooleanOp.OR, Block.box(4, 0, 0, 12, 8, 4), BASE_SHAPE_TOP, Block.box(0, 0, 4, 16, 8, 12)),
                        EAST, combineShapes(BooleanOp.OR, Block.box(0, 0, 4, 4, 8, 12), BASE_SHAPE_TOP, Block.box(4, 0, 0, 12, 8, 16)),
                        WEST, combineShapes(BooleanOp.OR, Block.box(12, 0, 4, 16, 8, 12), BASE_SHAPE_TOP, Block.box(4, 0, 0, 12, 8, 16))
                ));
            } else {
                return Maps.newEnumMap(ImmutableMap.of(
                        NORTH, combineShapes(BooleanOp.OR, Block.box(4, 8, 12, 12, 16, 16), BASE_SHAPE_BOTTOM, Block.box(0, 8, 4, 16, 16, 12)),
                        SOUTH, combineShapes(BooleanOp.OR, Block.box(4, 8, 0, 12, 16, 4), BASE_SHAPE_BOTTOM, Block.box(0, 8, 4, 16, 16, 12)),
                        EAST, combineShapes(BooleanOp.OR, Block.box(0, 8, 4, 4, 16, 12), BASE_SHAPE_BOTTOM, Block.box(4, 8, 0, 12, 16, 16)),
                        WEST, combineShapes(BooleanOp.OR, Block.box(12, 8, 4, 16, 16, 12), BASE_SHAPE_BOTTOM, Block.box(4, 8, 0, 12, 16, 16))
                ));
            }
        }
    }

    public CapitalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, NORTH)
                .setValue(CONNECT, IntersectionShape.SINGLE)
                .setValue(TYPE, TopBottomShape.BOTTOM)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, CONNECT, TYPE, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        TopBottomShape type = pState.getValue(TYPE);
        IntersectionShape connect = pState.getValue(CONNECT);
        Direction facing = pState.getValue(FACING);

        if (type == TopBottomShape.TOP) {
            switch (connect) {
                case CONNECTED -> {
                    return ShapeHelper.TOP_CONNECTED_SHAPE.get(facing);
                }
                case END -> {
                    return ShapeHelper.TOP_END_SHAPE.get(facing);
                }
                case INNER_LEFT -> {
                    return ShapeHelper.TOP_CORNER_SHAPE.get(facing);
                }
                case INNER_RIGHT -> {
                    return ShapeHelper.TOP_CORNER_SHAPE.get(facing.getCounterClockWise());
                }
                case OUTER_LEFT -> {
                    return ShapeHelper.TOP_CORNER_SHAPE.get(facing.getClockWise());
                }
                case OUTER_RIGHT -> {
                    return ShapeHelper.TOP_CORNER_SHAPE.get(facing.getOpposite());
                }
                case CROSS -> {
                    return ShapeHelper.TOP_CROSS_SHAPE;
                }
                case T -> {
                    return ShapeHelper.TOP_T_SHAPE.get(facing);
                }
                case T_LEFT -> {
                    return ShapeHelper.TOP_T_SHAPE.get(facing.getClockWise());
                }
                case T_RIGHT -> {
                    return ShapeHelper.TOP_T_SHAPE.get(facing.getCounterClockWise());
                }
                case T_OPPOSITE -> {
                    return ShapeHelper.TOP_T_SHAPE.get(facing.getOpposite());
                }
                default -> {
                    return ShapeHelper.TOP_SINGLE_SHAPE;
                }
            }
        } else {
            switch (connect) {
                case CONNECTED -> {
                    return ShapeHelper.BOTTOM_CONNECTED_SHAPE.get(facing);
                }
                case END -> {
                    return ShapeHelper.BOTTOM_END_SHAPE.get(facing);
                }
                case INNER_LEFT -> {
                    return ShapeHelper.BOTTOM_CORNER_SHAPE.get(facing);
                }
                case INNER_RIGHT -> {
                    return ShapeHelper.BOTTOM_CORNER_SHAPE.get(facing.getCounterClockWise());
                }
                case OUTER_LEFT -> {
                    return ShapeHelper.BOTTOM_CORNER_SHAPE.get(facing.getClockWise());
                }
                case OUTER_RIGHT -> {
                    return ShapeHelper.BOTTOM_CORNER_SHAPE.get(facing.getOpposite());
                }
                case CROSS -> {
                    return ShapeHelper.BOTTOM_CROSS_SHAPE;
                }
                case T -> {
                    return ShapeHelper.BOTTOM_T_SHAPE.get(facing);
                }
                case T_LEFT -> {
                    return ShapeHelper.BOTTOM_T_SHAPE.get(facing.getClockWise());
                }
                case T_RIGHT -> {
                    return ShapeHelper.BOTTOM_T_SHAPE.get(facing.getCounterClockWise());
                }
                case T_OPPOSITE -> {
                    return ShapeHelper.BOTTOM_T_SHAPE.get(facing.getOpposite());
                }
                default -> {
                    return ShapeHelper.BOTTOM_SINGLE_SHAPE;
                }
            }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Direction clickedFace = pContext.getClickedFace();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        BlockState blockstate = this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER)
                .setValue(TYPE, clickedFace == Direction.DOWN || clickedFace != Direction.UP && pContext.getClickLocation().y - (double)blockpos.getY() > 0.5 ? TopBottomShape.TOP : TopBottomShape.BOTTOM);

        return blockstate.setValue(CONNECT, getCapitalShape(blockstate, pContext.getLevel(), blockpos));
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {

        // Update the shape first
        if (pFacing.getAxis().isHorizontal()) {
            BlockState front = pLevel.getBlockState(pCurrentPos.relative(pFacing));
            BlockState back = pLevel.getBlockState(pCurrentPos.relative(pFacing.getOpposite()));
            BlockState left = pLevel.getBlockState(pCurrentPos.relative(pFacing.getCounterClockWise()));
            BlockState right = pLevel.getBlockState(pCurrentPos.relative(pFacing.getClockWise()));

            // Get the capital shape
            pState = pState.setValue(CONNECT, getCapitalShape(pState, pLevel, pCurrentPos));

            // Handle END FACING direction
            if (pState.getValue(CONNECT) == IntersectionShape.END) {
                if (!isCapital(back) && isCapital(front)) {
                    pState = pState.setValue(FACING, pFacing.getOpposite());
                } else if (!isCapital(front) && isCapital(back)) {
                    pState = pState.setValue(FACING, pFacing);
                } else if (!isCapital(left) && isCapital(right)) {
                    pState = pState.setValue(FACING, pFacing.getCounterClockWise());
                } else if (!isCapital(right) && isCapital(left)) {
                    pState = pState.setValue(FACING, pFacing.getClockWise());
                }
            }
        }

        // Handling water logging
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return pState;
    }

    private IntersectionShape getCapitalShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        Direction facing = pState.getValue(FACING);
        BlockState front = pLevel.getBlockState(pPos.relative(facing));
        BlockState back = pLevel.getBlockState(pPos.relative(facing.getOpposite()));
        BlockState left = pLevel.getBlockState(pPos.relative(facing.getCounterClockWise()));
        BlockState right = pLevel.getBlockState(pPos.relative(facing.getClockWise()));

        int connections = (isCapital(front) ? 1 : 0) + (isCapital(back) ? 1 : 0) + (isCapital(left) ? 1 : 0) + (isCapital(right) ? 1 : 0);

        switch (connections) {
            case 0:
                return IntersectionShape.SINGLE;
            case 1:
                return IntersectionShape.END;
            case 2:
                if (isCapital(front) && isCapital(back)) {
                    return IntersectionShape.CONNECTED;
                }
                if (isCapital(left) && isCapital(right)) {
                    return IntersectionShape.CONNECTED;
                }
                if (isCapital(front) && isCapital(left)) {
                    return IntersectionShape.OUTER_LEFT;
                }
                if (isCapital(front) && isCapital(right)) {
                    return IntersectionShape.OUTER_RIGHT;
                }
                if (isCapital(back) && isCapital(left)) {
                    return IntersectionShape.INNER_LEFT;
                }
                if (isCapital(back) && isCapital(right)) {
                    return IntersectionShape.INNER_RIGHT;
                }
                break;
            case 3:
                if (!isCapital(front)) {
                    return IntersectionShape.T;
                } else if (!isCapital(back)) {
                    return IntersectionShape.T_OPPOSITE;
                } else if (!isCapital(left)) {
                    return IntersectionShape.T_RIGHT;
                } else {
                    return IntersectionShape.T_LEFT;
                }
            case 4:
                return IntersectionShape.CROSS;
            default:
                return IntersectionShape.END;
        }
        return IntersectionShape.END;
    }


    public static boolean isCapital(BlockState pState) {
        return pState.getBlock() instanceof CapitalBlock;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return SimpleWaterloggedBlock.super.placeLiquid(pLevel, pPos, pState, pFluidState);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(pLevel, pPos, pState, pFluid);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND -> true;
            case WATER -> pLevel.getFluidState(pPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}

