package com.calibermc.caliberlib.block.custom;


import com.calibermc.caliberlib.block.shapes.LargeArchShape;
import com.calibermc.caliberlib.block.shapes.trim.LargeArchTrim;
import com.calibermc.caliberlib.block.shapes.voxels.VoxelShapeHelper;
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
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Stream;

import static net.minecraft.core.Direction.*;

public class LargeArchBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<LargeArchShape> TYPE = ModBlockStateProperties.LARGE_ARCH_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<LargeArchTrim> TRIM = ModBlockStateProperties.LARGE_ARCH_TRIM;

    public LargeArchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState() //this.stateDefinition.any()
                .setValue(TRIM, LargeArchTrim.BOTH)
                .setValue(FACING, NORTH)
                .setValue(TYPE, LargeArchShape.STRAIGHT)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    public static boolean isArch(BlockState pState) {
        return pState.getBlock() instanceof LargeArchBlock;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TRIM, FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        LargeArchShape archShape = pState.getValue(TYPE);

        switch (archShape) {
            case CORNER_LEFT -> {
                return VoxelShapeHelper.LargeArchBlockShapes.CORNER_LEFT_SHAPE.get(pState.getValue(FACING));
            }
            case CORNER_RIGHT -> {
                return VoxelShapeHelper.LargeArchBlockShapes.CORNER_RIGHT_SHAPE.get(pState.getValue(FACING));
            }
            case CORNER_OUTER_LEFT, CORNER_OUTER_RIGHT -> {
                return VoxelShapeHelper.LargeArchBlockShapes.SHAPE;
            }
            case STRAIGHT -> {
                return VoxelShapeHelper.LargeArchBlockShapes.STRAIGHT_SHAPE.get(pState.getValue(FACING));
            }
        }
        return null;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        BlockState blockstate1 = this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        return blockstate1.setValue(TYPE, getArchShape(blockstate1, pContext.getLevel(), blockpos));
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        if (pFacing.getAxis().isHorizontal()) {
            // Set the TYPE value based on the ArchShape
            pState = pState.setValue(TYPE, getArchShape(pState, pLevel, pCurrentPos));

            // Set the TRIM value
            pState = pState.setValue(TRIM, getTrim(pState, pLevel, pCurrentPos));
        } else {
            pState = super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }

        return pState;
    }

    private LargeArchShape getArchShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        Direction facing = pState.getValue(FACING);
        Direction opposite = facing.getOpposite();
        boolean front = isArch(pLevel.getBlockState(pPos.relative(facing)));
        boolean back = isArch(pLevel.getBlockState(pPos.relative(opposite)));
        boolean left = isArch(pLevel.getBlockState(pPos.relative(facing.getCounterClockWise())));
        boolean right = isArch(pLevel.getBlockState(pPos.relative(facing.getClockWise())));
        boolean leftIsAir = pLevel.getBlockState(pPos.relative(facing.getCounterClockWise())).isAir();
        boolean rightIsAir = pLevel.getBlockState(pPos.relative(facing.getClockWise())).isAir();

        if (pState.getValue(TYPE) == LargeArchShape.CORNER_OUTER_LEFT || pState.getValue(TYPE) == LargeArchShape.CORNER_OUTER_RIGHT) {
            return pState.getValue(TYPE);
        }
        if ((rightIsAir) || (leftIsAir)) {
            return LargeArchShape.STRAIGHT;
        }
        if ((back && right && !left) || (front && left && !right)) {
            return LargeArchShape.CORNER_LEFT;
        }
        if ((back && left && !right) || (front && right && !left)) {
            return LargeArchShape.CORNER_RIGHT;
        }
        return LargeArchShape.STRAIGHT;
    }

    private LargeArchTrim getTrim(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        Direction facing = pState.getValue(FACING);
        boolean airRight = pLevel.getBlockState(pPos.relative(facing.getClockWise())).isAir();
        boolean airLeft = pLevel.getBlockState(pPos.relative(facing.getCounterClockWise())).isAir();

        if (pState.getValue(TYPE) == LargeArchShape.STRAIGHT) {
            if (airLeft && airRight) {
                return LargeArchTrim.BOTH;
            }
            if (!airLeft && airRight) {
                return LargeArchTrim.RIGHT;
            }
            if (airLeft && !airRight) {
                return LargeArchTrim.LEFT;
            }
            return LargeArchTrim.NONE;
        }
        return LargeArchTrim.NONE;
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