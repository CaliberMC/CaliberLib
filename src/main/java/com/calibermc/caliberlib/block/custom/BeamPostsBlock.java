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


public class BeamPostsBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty BEAM = ModBlockStateProperties.FRAME_SHAPE;
    public final int beamShape = 3;

    public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(16, 0, 14, 20, 16, 16), Block.box(-4, 0, 14, 0, 16, 16), BooleanOp.OR),
            Block.box(-4, 0, 14, 0, 16, 16),
            Block.box(16, 0, 14, 20, 16, 16)};
    public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 0, 16, 2, 16, 20), Block.box(0, 0, -4, 2, 16, 0), BooleanOp.OR),
            Block.box(0, 0, -4, 2, 16, 0),
            Block.box(0, 0, 16, 2, 16, 20)};
    public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(-4, 0, 0, 0, 16, 2), Block.box(16, 0, 0, 20, 16, 2), BooleanOp.OR),
            Block.box(16, 0, 0, 20, 16, 2),
            Block.box(-4, 0, 0, 0, 16, 2)};
    public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(14, 0, -4, 16, 16, 0), Block.box(14, 0, 16, 16, 16, 20), BooleanOp.OR),
            Block.box(14, 0, 16, 16, 16, 20),
            Block.box(14, 0, -4, 16, 16, 0)};

    public BeamPostsBlock(Properties properties) {
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
        switch (direction) {
            case EAST:
                return SHAPE_EAST[blockState.getValue(BEAM)];
            case SOUTH:
                return SHAPE_SOUTH[blockState.getValue(BEAM)];
            case WEST:
                return SHAPE_WEST[blockState.getValue(BEAM)];
            default:
                return SHAPE_NORTH[blockState.getValue(BEAM)];
        }
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