package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.IntersectionShape;
import com.calibermc.caliberlib.block.shapes.TopBottomShape;
import com.calibermc.caliberlib.block.shapes.voxels.VoxelShapeHelper;
import com.calibermc.caliberlib.util.ModBlockStateProperties;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.core.Direction.*;

public class CapitalBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<IntersectionShape> TYPE = ModBlockStateProperties.INTERSECTION_SHAPE;
    public static final EnumProperty<TopBottomShape> HALF = ModBlockStateProperties.TOP_BOTTOM_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CapitalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, NORTH)
                .setValue(TYPE, IntersectionShape.SINGLE)
                .setValue(HALF, TopBottomShape.BOTTOM)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TYPE, HALF, WATERLOGGED);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        TopBottomShape half = pState.getValue(HALF);
        IntersectionShape type = pState.getValue(TYPE);
        Direction facing = pState.getValue(FACING);

        if (half == TopBottomShape.TOP) {
            switch (type) {
                case CONNECTED -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_CONNECTED_SHAPE.get(facing);
                }
                case END -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_END_SHAPE.get(facing);
                }
                case INNER_LEFT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_CORNER_SHAPE.get(facing);
                }
                case INNER_RIGHT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_CORNER_SHAPE.get(facing.getCounterClockWise());
                }
                case OUTER_LEFT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_CORNER_SHAPE.get(facing.getClockWise());
                }
                case OUTER_RIGHT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_CORNER_SHAPE.get(facing.getOpposite());
                }
                case CROSS -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_CROSS_SHAPE;
                }
                case T -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_T_SHAPE.get(facing);
                }
                case T_LEFT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_T_SHAPE.get(facing.getClockWise());
                }
                case T_RIGHT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_T_SHAPE.get(facing.getCounterClockWise());
                }
                case T_OPPOSITE -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_T_SHAPE.get(facing.getOpposite());
                }
                default -> {
                    return VoxelShapeHelper.CapitalBlockShapes.TOP_SINGLE_SHAPE;
                }
            }
        } else {
            switch (type) {
                case CONNECTED -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_CONNECTED_SHAPE.get(facing);
                }
                case END -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_END_SHAPE.get(facing);
                }
                case INNER_LEFT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_CORNER_SHAPE.get(facing);
                }
                case INNER_RIGHT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_CORNER_SHAPE.get(facing.getCounterClockWise());
                }
                case OUTER_LEFT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_CORNER_SHAPE.get(facing.getClockWise());
                }
                case OUTER_RIGHT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_CORNER_SHAPE.get(facing.getOpposite());
                }
                case CROSS -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_CROSS_SHAPE;
                }
                case T -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_T_SHAPE.get(facing);
                }
                case T_LEFT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_T_SHAPE.get(facing.getClockWise());
                }
                case T_RIGHT -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_T_SHAPE.get(facing.getCounterClockWise());
                }
                case T_OPPOSITE -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_T_SHAPE.get(facing.getOpposite());
                }
                default -> {
                    return VoxelShapeHelper.CapitalBlockShapes.BOTTOM_SINGLE_SHAPE;
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
                .setValue(HALF, clickedFace == Direction.DOWN || clickedFace != Direction.UP && pContext.getClickLocation().y - (double)blockpos.getY() > 0.5 ? TopBottomShape.TOP : TopBottomShape.BOTTOM);

        return blockstate.setValue(TYPE, getCapitalShape(blockstate, pContext.getLevel(), blockpos));
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
            pState = pState.setValue(TYPE, getCapitalShape(pState, pLevel, pCurrentPos));

            // Handle END FACING direction
            if (pState.getValue(TYPE) == IntersectionShape.END) {
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

