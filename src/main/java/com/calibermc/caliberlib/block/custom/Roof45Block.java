package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.RoofShape;
import com.calibermc.caliberlib.block.properties.ModBlockStateProperties;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

public class Roof45Block extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<RoofShape> TYPE = ModBlockStateProperties.ROOF_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final Map<Direction, VoxelShape> STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(0, 0, 0, 16, 8, 8), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(0, 0, 8, 16, 8, 16), BooleanOp.OR),
            Direction.EAST, Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 0, 16, 8, 16), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 8, 8, 16), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> OUTER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.SOUTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
            Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
            Direction.EAST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> OUTER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.EAST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
            Direction.WEST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
            Direction.SOUTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
            Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR)));

    public static final Map<Direction, VoxelShape> INNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.SOUTH, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(0, 8, 0, 8, 16, 16),
                    Block.box(8, 8, 0, 16, 16, 8)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.NORTH, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(8, 8, 0, 16, 16, 16),
                    Block.box(0, 8, 8, 8, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.WEST, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(0, 8, 0, 16, 16, 8),
                    Block.box(8, 8, 8, 16, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.EAST, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(0, 8, 8, 16, 16, 16),
                    Block.box(0, 8, 0, 8, 16, 8)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    public static final Map<Direction, VoxelShape> INNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            Direction.EAST, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(0, 8, 0, 8, 16, 16),
                    Block.box(8, 8, 0, 16, 16, 8)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.WEST, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(8, 8, 0, 16, 16, 16),
                    Block.box(0, 8, 8, 8, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.SOUTH, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(0, 8, 0, 16, 16, 8),
                    Block.box(8, 8, 8, 16, 16, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Direction.NORTH, Stream.of(
                    Block.box(0, 0, 0, 16, 8, 16),
                    Block.box(0, 8, 8, 16, 16, 16),
                    Block.box(0, 8, 0, 8, 16, 8)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        Direction direction = blockState.getValue(FACING);
        RoofShape type = blockState.getValue(TYPE);
        return switch (type) {
            case OUTER_LEFT -> OUTER_LEFT_SHAPE.get(direction);
            case OUTER_RIGHT -> OUTER_RIGHT_SHAPE.get(direction);
            case INNER_LEFT -> INNER_LEFT_SHAPE.get(direction);
            case INNER_RIGHT -> INNER_RIGHT_SHAPE.get(direction);
            case STRAIGHT -> STRAIGHT_SHAPE.get(direction);
        };
    }

    public Roof45Block(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, RoofShape.STRAIGHT)
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
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();

        FluidState fluidstate = level.getFluidState(blockpos);
        Direction facing = blockPlaceContext.getHorizontalDirection().getOpposite();
        BlockState blockstate = this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        // Determine RoofShape based on surrounding blocks
        RoofShape roofShape = getRoofShape(blockstate, level, blockpos);
        blockstate = blockstate.setValue(TYPE, roofShape);

        return blockstate;
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
        if (isRoof(blockstate)) {
            Direction direction1 = blockstate.getValue(FACING);
            if (direction1.getAxis() != blockState.getValue(FACING).getAxis() && canTakeShape(blockState, blockGetter, blockPos, direction1.getOpposite())) {
                if (direction1 == direction.getCounterClockWise()) {
                    return RoofShape.INNER_LEFT;
                }
                return RoofShape.INNER_RIGHT;
            }
        }
        BlockState blockstate1 = blockGetter.getBlockState(blockPos.relative(direction.getOpposite()));
        if (isRoof(blockstate1)) {
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
        return blockState.getBlock() instanceof Roof45Block;
    }

    private static boolean canTakeShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction pFace) {
        BlockState blockstate = blockGetter.getBlockState(blockPos.relative(pFace));
        return !isRoof(blockstate) || blockstate.getValue(FACING) != blockState.getValue(FACING);
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