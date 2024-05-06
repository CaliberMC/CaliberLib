package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.LeftRightShape;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


import java.util.Map;

import static com.calibermc.caliberlib.block.shapes.LeftRightShape.LEFT;
import static com.calibermc.caliberlib.block.shapes.LeftRightShape.RIGHT;
import static net.minecraft.core.Direction.*;

public class LargeHalfArchBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<LeftRightShape> TYPE = ModBlockStateProperties.LEFT_RIGHT_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final Map<Direction, VoxelShape> SHAPE = Maps.newEnumMap(ImmutableMap.of(
            NORTH, Block.box(0, 13, 8, 16, 16, 16),
            SOUTH, Block.box(0, 13, 0, 16, 16, 8),
            EAST, Block.box(0, 13, 0, 8, 16, 16),
            WEST, Block.box(8, 13, 0, 16, 16, 16)));

    public LargeHalfArchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any() // ? this.defaultBlockState()
                .setValue(FACING, NORTH)
                .setValue(TYPE, RIGHT)
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
        return SHAPE.get(blockState.getValue(FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        double hitX = blockPlaceContext.getClickLocation().x - (double) blockpos.getX();
        double hitZ = blockPlaceContext.getClickLocation().z - (double) blockpos.getZ();
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        BlockState blockstate1 = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));

        if (direction == Direction.NORTH && hitX < 0.5) {
            return blockstate1.setValue(TYPE, LeftRightShape.RIGHT);
        } else if (direction == Direction.SOUTH && hitX > 0.5) {
            return blockstate1.setValue(TYPE, LeftRightShape.RIGHT);
        } else if (direction == Direction.EAST && hitZ < 0.5) {
            return blockstate1.setValue(TYPE, LeftRightShape.RIGHT);
        } else if (direction == Direction.WEST && hitZ > 0.5) {
            return blockstate1.setValue(TYPE, LeftRightShape.RIGHT);
        } else {
            return blockstate1.setValue(TYPE, LEFT);
        }
    }

    public BlockState updateShape(BlockState blockState, Direction pFacing, BlockState pFacingState, LevelAccessor levelAccessor, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, pFacing, pFacingState, levelAccessor, pCurrentPos, pFacingPos);
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