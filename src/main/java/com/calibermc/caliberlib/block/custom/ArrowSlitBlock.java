package com.calibermc.caliberlib.block.custom;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
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

public class ArrowSlitBlock extends Block implements SimpleWaterloggedBlock {
    
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    
    public static final Map<Direction, VoxelShape> SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(
                    Block.box(9, 0, 14.5, 16, 16, 16),
                    Block.box(0, 0, 14.5, 7, 16, 16),
                    Block.box(0, 0, 8, 2.5, 16, 14.5),
                    Block.box(13.5, 0, 8, 16, 16, 14.5),
                    Block.box(2.5, 0, 12.5, 4.5, 16, 14.5),
                    Block.box(11.5, 0, 12.5, 13.5, 16, 14.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(
                    Block.box(0, 0, 0, 7, 16, 1.5),
                    Block.box(9, 0, 0, 16, 16, 1.5),
                    Block.box(13.5, 0, 1.5, 16, 16, 8),
                    Block.box(0, 0, 1.5, 2.5, 16, 8),
                    Block.box(11.5, 0, 1.5, 13.5, 16, 3.5),
                    Block.box(2.5, 0, 1.5, 4.5, 16, 3.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(
                    Block.box(0, 0, 9, 1.5, 16, 16),
                    Block.box(0, 0, 0, 1.5, 16, 7),
                    Block.box(1.5, 0, 0, 8, 16, 2.5),
                    Block.box(1.5, 0, 13.5, 8, 16, 16),
                    Block.box(1.5, 0, 2.5, 3.5, 16, 4.5),
                    Block.box(1.5, 0, 11.5, 3.5, 16, 13.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(
                    Block.box(14.5, 0, 0, 16, 16, 7),
                    Block.box(14.5, 0, 9, 16, 16, 16),
                    Block.box(8, 0, 13.5, 14.5, 16, 16),
                    Block.box(8, 0, 0, 14.5, 16, 2.5),
                    Block.box(12.5, 0, 11.5, 14.5, 16, 13.5),
                    Block.box(12.5, 0, 2.5, 14.5, 16, 4.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    public ArrowSlitBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, NORTH)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        return SHAPE.get(blockState.getValue(FACING));
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection()
                .getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));

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