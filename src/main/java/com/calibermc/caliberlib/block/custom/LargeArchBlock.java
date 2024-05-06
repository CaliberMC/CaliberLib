package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.LargeArchShape;
import com.calibermc.caliberlib.block.shapes.trim.LargeArchTrim;
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

import static com.calibermc.caliberlib.block.properties.ModBlockStateProperties.isAir;
import static net.minecraft.core.Direction.*;

public class LargeArchBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<LargeArchShape> TYPE = ModBlockStateProperties.LARGE_ARCH_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<LargeArchTrim> TRIM = ModBlockStateProperties.LARGE_ARCH_TRIM;

    public static final Map<Direction, VoxelShape> CORNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(0, 4.98438, 0, 2, 13.00001, 16),
                    Block.box(0, 4.98438, 0, 16, 13.00001, 2)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(14, 4.98438, 0, 16, 13.00001, 16),
                    Block.box(0, 4.98438, 14, 16, 13.00001, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(0, 4.98438, 0, 16, 13.00001, 2),
                    Block.box(14, 4.98438, 0, 16, 13.00001, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(0, 4.98438, 14, 16, 13.00001, 16),
                    Block.box(0, 4.98438, 0, 2, 13.00001, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    public static final Map<Direction, VoxelShape> CORNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(0, 4.98438, 0, 16, 13.00001, 2),
                    Block.box(14, 4.98438, 0, 16, 13.00001, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            SOUTH, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(0, 4.98438, 14, 16, 13.00001, 16),
                    Block.box(0, 4.98438, 0, 2, 13.00001, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            EAST, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(14, 4.98438, 0, 16, 13.00001, 16),
                    Block.box(0, 4.98438, 14, 16, 13.00001, 16)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            WEST, Stream.of(
                    Block.box(0, 13.00001, 0, 16, 16, 16),
                    Block.box(0, 4.98438, 0, 2, 13.00001, 16),
                    Block.box(0, 4.98438, 0, 16, 13.00001, 2)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

    public static final Map<Direction, VoxelShape> STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(0, 4.98438, 0, 16, 13.00001, 2), BooleanOp.OR),
            SOUTH, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(0, 4.98438, 14, 16, 13.00001, 16), BooleanOp.OR),
            EAST, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(14, 4.98438, 0, 16, 13.00001, 16), BooleanOp.OR),
            WEST, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(0, 4.98438, 0, 2, 13.00001, 16), BooleanOp.OR)));

    public static final VoxelShape SHAPE = Block.box(0, 13, 0, 16, 16, 16);

    public LargeArchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState() //this.stateDefinition.any()
                .setValue(TRIM, LargeArchTrim.BOTH)
                .setValue(FACING, NORTH)
                .setValue(TYPE, LargeArchShape.STRAIGHT)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TRIM, FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        LargeArchShape archShape = blockState.getValue(TYPE);

        switch (archShape) {
            case CORNER_LEFT -> {
                return CORNER_LEFT_SHAPE.get(blockState.getValue(FACING));
            }
            case CORNER_RIGHT -> {
                return CORNER_RIGHT_SHAPE.get(blockState.getValue(FACING));
            }
            case CORNER_OUTER_LEFT, CORNER_OUTER_RIGHT -> {
                return SHAPE;
            }
            case STRAIGHT -> {
                return STRAIGHT_SHAPE.get(blockState.getValue(FACING));
            }
        }
        return null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        BlockState blockstate1 = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection()) //.getOpposite()
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        return blockstate1.setValue(TYPE, getArchShape(blockstate1, blockPlaceContext.getLevel(), blockpos));
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction pFacing, BlockState pFacingState, LevelAccessor levelAccessor, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        if (pFacing.getAxis().isHorizontal()) {
            // Set the TYPE value based on the ArchShape
            blockState = blockState.setValue(TYPE, getArchShape(blockState, levelAccessor, pCurrentPos));

            // Set the TRIM value
            blockState = blockState.setValue(TRIM, getTrim(blockState, levelAccessor, pCurrentPos));
        } else {
            blockState = super.updateShape(blockState, pFacing, pFacingState, levelAccessor, pCurrentPos, pFacingPos);
        }

        return blockState;
    }

    private LargeArchShape getArchShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        Direction facing = blockState.getValue(FACING);
        Direction opposite = facing.getOpposite();
        boolean front = isArch(blockGetter.getBlockState(blockPos.relative(facing)));
        boolean back = isArch(blockGetter.getBlockState(blockPos.relative(opposite)));
        boolean left = isArch(blockGetter.getBlockState(blockPos.relative(facing.getCounterClockWise())));
        boolean right = isArch(blockGetter.getBlockState(blockPos.relative(facing.getClockWise())));
        boolean leftIsAir = isAir(blockGetter.getBlockState(blockPos.relative(facing.getCounterClockWise())));
        boolean rightIsAir = isAir(blockGetter.getBlockState(blockPos.relative(facing.getClockWise())));

        if (blockState.getValue(TYPE) == LargeArchShape.CORNER_OUTER_LEFT || blockState.getValue(TYPE) == LargeArchShape.CORNER_OUTER_RIGHT) {
            return blockState.getValue(TYPE);
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

    private LargeArchTrim getTrim(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        Direction facing = blockState.getValue(FACING);
        boolean airRight = isAir(blockGetter.getBlockState(blockPos.relative(facing.getClockWise())));
        boolean airLeft = isAir(blockGetter.getBlockState(blockPos.relative(facing.getCounterClockWise())));

        if (blockState.getValue(TYPE) == LargeArchShape.STRAIGHT) {
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


    public static boolean isArch(BlockState blockState) {
        return blockState.getBlock() instanceof LargeArchBlock;
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