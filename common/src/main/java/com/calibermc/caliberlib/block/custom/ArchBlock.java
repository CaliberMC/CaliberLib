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

public class ArchBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<IntersectionShape> TYPE = ModBlockStateProperties.INTERSECTION_SHAPE;

    public ArchBlock(Properties properties) {
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
        return VoxelShapeHelper.ArchBlockShapes.ARCH_SHAPE;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        BlockState blockstate = this.defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        return blockstate.setValue(TYPE, getArchShape(blockstate, pContext.getLevel(), blockpos));
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
            pState = pState.setValue(TYPE, getArchShape(pState, pLevel, pCurrentPos));

            // Handle END FACING direction
            if (pState.getValue(TYPE) == IntersectionShape.END) {
                if (!isArch(back) && isArch(front)) {
                    pState = pState.setValue(FACING, pFacing.getOpposite());
                } else if (!isArch(front) && isArch(back)) {
                    pState = pState.setValue(FACING, pFacing);
                } else if (!isArch(left) && isArch(right)) {
                    pState = pState.setValue(FACING, pFacing.getCounterClockWise());
                } else if (!isArch(right) && isArch(left)) {
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

    private IntersectionShape getArchShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        Direction facing = pState.getValue(FACING);
        BlockState front = pLevel.getBlockState(pPos.relative(facing));
        BlockState back = pLevel.getBlockState(pPos.relative(facing.getOpposite()));
        BlockState left = pLevel.getBlockState(pPos.relative(facing.getCounterClockWise()));
        BlockState right = pLevel.getBlockState(pPos.relative(facing.getClockWise()));

        int connections = (isArch(front) ? 1 : 0) + (isArch(back) ? 1 : 0) + (isArch(left) ? 1 : 0) + (isArch(right) ? 1 : 0);

        switch (connections) {
            case 0:
                return IntersectionShape.SINGLE;
            case 1:
                return IntersectionShape.END;
            case 2:
                if (isArch(front) && isArch(back)) {
                    return IntersectionShape.CONNECTED;
                }
                if (isArch(left) && isArch(right)) {
                    return IntersectionShape.CONNECTED;
                }
                if (isArch(front) && isArch(left)) {
                    return IntersectionShape.OUTER_LEFT;
                }
                if (isArch(front) && isArch(right)) {
                    return IntersectionShape.OUTER_RIGHT;
                }
                if (isArch(back) && isArch(left)) {
                    return IntersectionShape.INNER_LEFT;
                }
                if (isArch(back) && isArch(right)) {
                    return IntersectionShape.INNER_RIGHT;
                }
                break;
            case 3:
                if (!isArch(front)) {
                    return IntersectionShape.T;
                } else if (!isArch(back)) {
                    return IntersectionShape.T_OPPOSITE;
                } else if (!isArch(left)) {
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

    public static boolean isArch(BlockState pState) {
        return pState.getBlock() instanceof ArchBlock;
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