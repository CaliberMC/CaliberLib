package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.WindowShape;
import com.calibermc.caliberlib.block.properties.ModBlockStateProperties;
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

public class WindowBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<WindowShape> TYPE = ModBlockStateProperties.WINDOW_SHAPE;
    
    public static final Map<Direction, VoxelShape> TOP_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(
                    Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16),
                    Block.box(4, 14, 0, 12, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(
                    Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16),
                    Block.box(4, 14, 0, 12, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(
                    Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4),
                    Block.box(0, 14, 4, 16, 16, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(
                    Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4),
                    Block.box(0, 14, 4, 16, 16, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    public static final Map<Direction, VoxelShape> MIDDLE_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Shapes.join(Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16), BooleanOp.OR),
            SOUTH, Shapes.join(Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16), BooleanOp.OR),
            EAST, Shapes.join(Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4), BooleanOp.OR),
            WEST, Shapes.join(Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> BOTTOM_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(
                    Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16),
                    Block.box(4, 0, 0, 12, 2, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(
                    Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16),
                    Block.box(4, 0, 0, 12, 2, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(
                    Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4),
                    Block.box(0, 0, 4, 16, 2, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(
                    Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4),
                    Block.box(0, 0, 4, 16, 2, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    public static final Map<Direction, VoxelShape> FULL_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(
                    Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16),
                    Block.box(4, 14, 0, 12, 16, 16),
                    Block.box(4, 0, 0, 12, 2, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(
                    Block.box(12, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 4, 16, 16),
                    Block.box(4, 14, 0, 12, 16, 16),
                    Block.box(4, 0, 0, 12, 2, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(
                    Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4),
                    Block.box(0, 14, 4, 16, 16, 12),
                    Block.box(0, 0, 4, 16, 2, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(
                    Block.box(0, 0, 12, 16, 16, 16),
                    Block.box(0, 0, 0, 16, 16, 4),
                    Block.box(0, 14, 4, 16, 16, 12),
                    Block.box(0, 0, 4, 16, 2, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    public WindowBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, NORTH)
                .setValue(TYPE, WindowShape.FULL_BLOCK)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        WindowShape windowShape = blockState.getValue(TYPE);
        switch (windowShape) {
            case TOP -> {
                return TOP_SHAPE.get(blockState.getValue(FACING));
            }
            case MIDDLE -> {
                return MIDDLE_SHAPE.get(blockState.getValue(FACING));
            }
            case BOTTOM -> {
                return BOTTOM_SHAPE.get(blockState.getValue(FACING));
            }
            default -> {
                return FULL_SHAPE.get(blockState.getValue(FACING));

            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();

        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        BlockState blockstate1 = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);


        return blockstate1.setValue(TYPE, WindowShape.FULL_BLOCK)
                .setValue(WATERLOGGED, Boolean.FALSE);
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

        if (levelAccessor.getBlockState(pCurrentPos.below()).getBlock() == this && levelAccessor.getBlockState(pCurrentPos.above()).getBlock() != this) {
            return blockState.setValue(TYPE, WindowShape.TOP);
        } else if (levelAccessor.getBlockState(pCurrentPos.below()).getBlock() == this && levelAccessor.getBlockState(pCurrentPos.above()).getBlock() == this) {
            return blockState.setValue(TYPE, WindowShape.MIDDLE);
        } else if (levelAccessor.getBlockState(pCurrentPos.below()).getBlock() != this && levelAccessor.getBlockState(pCurrentPos.above()).getBlock() == this) {
            return blockState.setValue(TYPE, WindowShape.BOTTOM);
        } else {
            return blockState.setValue(TYPE, WindowShape.FULL_BLOCK);
        }
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