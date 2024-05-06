package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.properties.ModBlockStateProperties;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


import static com.calibermc.caliberlib.block.properties.ModBlockStateProperties.isSide;


public class VerticalBeamBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty BEAM = ModBlockStateProperties.VERTICAL_BEAM_SHAPE;
    public final int beamShape = 7;

    public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
            Block.box(5, 0, 12, 11, 16, 16),
            Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 12, 0, 11, 16, 12), BooleanOp.OR),
            Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 12, 0, 11, 16, 12), BooleanOp.OR),
            Block.box(5, 0, 12, 11, 16, 16),
            Block.box(5, 0, 12, 11, 16, 16),
            Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 0, 0, 11, 4, 12), BooleanOp.OR),
            Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 0, 0, 11, 4, 12), BooleanOp.OR)};

    public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
            Block.box(0, 0, 5, 4, 16, 11),
            Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 12, 5, 16, 16, 11), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 12, 5, 16, 16, 11), BooleanOp.OR),
            Block.box(0, 0, 5, 4, 16, 11),
            Block.box(0, 0, 5, 4, 16, 11),
            Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 0, 5, 16, 4, 11), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 0, 5, 16, 4, 11), BooleanOp.OR)};

    public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
            Block.box(5, 0, 0, 11, 16, 4),
            Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 12, 4, 11, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 12, 4, 11, 16, 16), BooleanOp.OR),
            Block.box(5, 0, 0, 11, 16, 4),
            Block.box(5, 0, 0, 11, 16, 4),
            Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 0, 4, 11, 4, 16), BooleanOp.OR),
            Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 0, 4, 11, 4, 16), BooleanOp.OR)};

    public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
            Block.box(12, 0, 5, 16, 16, 11),
            Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 12, 5, 12, 16, 11), BooleanOp.OR),
            Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 12, 5, 12, 16, 11), BooleanOp.OR),
            Block.box(12, 0, 5, 16, 16, 11),
            Block.box(12, 0, 5, 16, 16, 11),
            Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 0, 5, 12, 4, 11), BooleanOp.OR),
            Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 0, 5, 12, 4, 11), BooleanOp.OR)};

    public VerticalBeamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BEAM, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.FALSE));

    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BEAM, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        Direction direction = blockState.getValue(FACING);
        return switch (direction) {
            case EAST -> SHAPE_EAST[blockState.getValue(BEAM)];
            case SOUTH -> SHAPE_SOUTH[blockState.getValue(BEAM)];
            case WEST -> SHAPE_WEST[blockState.getValue(BEAM)];
            default -> SHAPE_NORTH[blockState.getValue(BEAM)];
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(blockpos);
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        if (blockstate.is(this)) {
            int newCount = blockstate.getValue(BEAM) + 1;
            if (newCount > beamShape) {
                newCount = 1;
            }
            blockPlaceContext.getItemInHand().grow(1);
            return blockstate.setValue(BEAM, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < beamShape) && fluidstate.is(FluidTags.WATER)));
        } else {
            return this.defaultBlockState().setValue(BEAM, 1).setValue(FACING, blockPlaceContext.getHorizontalDirection()
                    .getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext blockPlaceContext) {
        if (blockPlaceContext.getItemInHand().getItem() == this.asItem()) {
            Direction clickedFace = blockPlaceContext.getClickedFace();
            return isSide(clickedFace) && blockPlaceContext.replacingClickedOnBlock();
        }
        return false;
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction pFacing, BlockState pFacingState, LevelAccessor levelAccessor, BlockPos pCurrentPos, BlockPos pFacingPos) {

        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, pFacing, pFacingState, levelAccessor, pCurrentPos, pFacingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        return state.getValue(BEAM) < beamShape && SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluid);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.getValue(BEAM) < beamShape && SimpleWaterloggedBlock.super.canPlaceLiquid(player, world, pos, state, fluid);
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