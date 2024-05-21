package com.calibermc.caliberlib.block.custom;


import com.calibermc.caliberlib.block.shapes.BalustradeShape;
import com.calibermc.caliberlib.data.ModBlockFamily;
import com.calibermc.caliberlib.util.ModBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
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

public class BalustradeBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<BalustradeShape> TYPE = ModBlockStateProperties.BALUSTRADE_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Map<Direction, VoxelShape> SINGLE_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(Block.box(1.5, 10, 1.5, 14.5, 16, 14.5),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3, 3, 3, 13, 4, 13),
                    Block.box(4, 4, 4, 12, 10, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(Block.box(1.5, 10, 1.5, 14.5, 16, 14.5),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3, 3, 3, 13, 4, 13),
                    Block.box(4, 4, 4, 12, 10, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(Block.box(1.5, 10, 1.5, 14.5, 16, 14.5),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3, 3, 3, 13, 4, 13),
                    Block.box(4, 4, 4, 12, 10, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(Block.box(1.5, 10, 1.5, 14.5, 16, 14.5),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3, 3, 3, 13, 4, 13),
                    Block.box(4, 4, 4, 12, 10, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));
    public static final Map<Direction, VoxelShape> CONNECTED_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(
                    Block.box(3, 10, 0, 13, 16, 16),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3.5, 3, 3, 12.5, 4, 13),
                    Block.box(5.5, 4, 4, 10.5, 10, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(
                    Block.box(3, 10, 0, 13, 16, 16),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3.5, 3, 3, 12.5, 4, 13),
                    Block.box(5.5, 4, 4, 10.5, 10, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(
                    Block.box(0, 10, 3, 16, 16, 13),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3, 3, 3.5, 13, 4, 12.5),
                    Block.box(4, 4, 5.5, 12, 10, 10.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(
                    Block.box(0, 10, 3, 16, 16, 13),
                    Block.box(1.5, 0, 1.5, 14.5, 3, 14.5),
                    Block.box(3, 3, 3.5, 13, 4, 12.5),
                    Block.box(4, 4, 5.5, 12, 10, 10.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));
    public BalustradeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, NORTH)
                .setValue(TYPE, BalustradeShape.SINGLE)
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
        BalustradeShape balustradeShape = pState.getValue(TYPE);
        if (balustradeShape == BalustradeShape.CONNECTED) {
            return CONNECTED_SHAPE.get(pState.getValue(FACING));
        }
        return SINGLE_SHAPE.get(pState.getValue(FACING));
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Direction direction = pContext.getClickedFace();
        if (direction.getAxis().isHorizontal()) {
            FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
            BlockState blockstate = this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection())
                    .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

            return blockstate.setValue(TYPE, BalustradeShape.CONNECTED);
        } else {
            return defaultBlockState();
        }
    }

    // TODO: ADD UPDATE SHAPE METHOD FOR CONNECTED BLOCKS & CORNERS

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
