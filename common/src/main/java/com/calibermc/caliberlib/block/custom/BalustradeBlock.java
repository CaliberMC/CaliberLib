package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.IntersectionShape;
import com.calibermc.caliberlib.block.shapes.voxels.VoxelShapeHelper;
import com.calibermc.caliberlib.util.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
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

import org.jetbrains.annotations.Nullable;

import static net.minecraft.core.Direction.*;

public class BalustradeBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<IntersectionShape> TYPE = ModBlockStateProperties.INTERSECTION_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BalustradeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, NORTH)
                .setValue(TYPE, IntersectionShape.SINGLE)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        IntersectionShape type = pState.getValue(TYPE);
        Direction facing = pState.getValue(FACING);
        switch (type) {
            case CONNECTED -> {
                return VoxelShapeHelper.BalustradeBlockShapes.CONNECTED_SHAPE.get(facing);
            }
            case END -> {
                return VoxelShapeHelper.BalustradeBlockShapes.END_SHAPE.get(facing);
            }
            case INNER_LEFT -> {
                return VoxelShapeHelper.BalustradeBlockShapes.CORNER_SHAPE.get(facing);
            }
            case INNER_RIGHT -> {
                return VoxelShapeHelper.BalustradeBlockShapes.CORNER_SHAPE.get(facing.getCounterClockWise());
            }
            case OUTER_LEFT -> {
                return VoxelShapeHelper.BalustradeBlockShapes.CORNER_SHAPE.get(facing.getClockWise());
            }
            case OUTER_RIGHT -> {
                return VoxelShapeHelper.BalustradeBlockShapes.CORNER_SHAPE.get(facing.getOpposite());
            }
            case CROSS -> {
                return VoxelShapeHelper.BalustradeBlockShapes.CROSS_SHAPE;
            }
            case T -> {
                return VoxelShapeHelper.BalustradeBlockShapes.T_SHAPE.get(facing);
            }
            case T_LEFT -> {
                return VoxelShapeHelper.BalustradeBlockShapes.T_SHAPE.get(facing.getClockWise());
            }
            case T_RIGHT -> {
                return VoxelShapeHelper.BalustradeBlockShapes.T_SHAPE.get(facing.getCounterClockWise());
            }
            case T_OPPOSITE -> {
                return VoxelShapeHelper.BalustradeBlockShapes.T_SHAPE.get(facing.getOpposite());
            }
            default -> {
                return VoxelShapeHelper.BalustradeBlockShapes.SINGLE_SHAPE;
            }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        BlockState blockstate = this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        return blockstate.setValue(TYPE, getBalustradeShape(blockstate, pContext.getLevel(), blockpos));
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
            pState = pState.setValue(TYPE, getBalustradeShape(pState, pLevel, pCurrentPos));

            // Handle END FACING direction
            if (pState.getValue(TYPE) == IntersectionShape.END) {
                if (!isBalustrade(back) && isBalustrade(front)) {
                    pState = pState.setValue(FACING, pFacing.getOpposite());
                } else if (!isBalustrade(front) && isBalustrade(back)) {
                    pState = pState.setValue(FACING, pFacing);
                } else if (!isBalustrade(left) && isBalustrade(right)) {
                    pState = pState.setValue(FACING, pFacing.getCounterClockWise());
                } else if (!isBalustrade(right) && isBalustrade(left)) {
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

    private IntersectionShape getBalustradeShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        Direction facing = pState.getValue(FACING);
        BlockState front = pLevel.getBlockState(pPos.relative(facing));
        BlockState back = pLevel.getBlockState(pPos.relative(facing.getOpposite()));
        BlockState left = pLevel.getBlockState(pPos.relative(facing.getCounterClockWise()));
        BlockState right = pLevel.getBlockState(pPos.relative(facing.getClockWise()));

        int connections = (isBalustrade(front) ? 1 : 0) + (isBalustrade(back) ? 1 : 0) + (isBalustrade(left) ? 1 : 0) + (isBalustrade(right) ? 1 : 0);

        switch (connections) {
            case 0:
                return IntersectionShape.SINGLE;
            case 1:
                return IntersectionShape.END;
            case 2:
                if (isBalustrade(front) && isBalustrade(back)) {
                    return IntersectionShape.CONNECTED;
                }
                if (isBalustrade(left) && isBalustrade(right)) {
                    return IntersectionShape.CONNECTED;
                }
                if (isBalustrade(front) && isBalustrade(left)) {
                    return IntersectionShape.OUTER_LEFT;
                }
                if (isBalustrade(front) && isBalustrade(right)) {
                    return IntersectionShape.OUTER_RIGHT;
                }
                if (isBalustrade(back) && isBalustrade(left)) {
                    return IntersectionShape.INNER_LEFT;
                }
                if (isBalustrade(back) && isBalustrade(right)) {
                    return IntersectionShape.INNER_RIGHT;
                }
                break;
            case 3:
                if (!isBalustrade(front)) {
                    return IntersectionShape.T;
                } else if (!isBalustrade(back)) {
                    return IntersectionShape.T_OPPOSITE;
                } else if (!isBalustrade(left)) {
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


    public static boolean isBalustrade(BlockState pState) {
        return pState.getBlock() instanceof BalustradeBlock;
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

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND, AIR -> false;
            case WATER -> pLevel.getFluidState(pPos).is(FluidTags.WATER);
        };
    }
}
