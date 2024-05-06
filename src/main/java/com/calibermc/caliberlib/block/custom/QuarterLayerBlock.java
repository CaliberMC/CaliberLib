package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.TopBottomShape;
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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class QuarterLayerBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<TopBottomShape> TYPE = ModBlockStateProperties.TOP_BOTTOM_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LAYERS = ModBlockStateProperties.FIVE_LAYERS;
    public final int layerCount = 5;

    public static final VoxelShape[] NORTH_BOTTOM = new VoxelShape[]{Shapes.empty(),
            Block.box(0, 0, 14, 16, 2, 16),
            Block.box(0, 0, 12, 16, 4, 16),
            Block.box(0, 0, 8, 16, 8, 16),
            Block.box(0, 0, 4, 16, 12, 16),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] EAST_BOTTOM = new VoxelShape[]{Shapes.empty(),
            Block.box(0, 0, 0, 2, 2, 16),
            Block.box(0, 0, 0, 4, 4, 16),
            Block.box(0, 0, 0, 8, 8, 16),
            Block.box(0, 0, 0, 12, 12, 16),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] SOUTH_BOTTOM = new VoxelShape[]{Shapes.empty(),
            Block.box(0, 0, 0, 16, 2, 2),
            Block.box(0, 0, 0, 16, 4, 4),
            Block.box(0, 0, 0, 16, 8, 8),
            Block.box(0, 0, 0, 16, 12, 12),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] WEST_BOTTOM = new VoxelShape[]{Shapes.empty(),
            Block.box(14, 0, 0, 16, 2, 16),
            Block.box(12, 0, 0, 16, 4, 16),
            Block.box(8, 0, 0, 16, 8, 16),
            Block.box(4, 0, 0, 16, 12, 16),
            Block.box(0, 0.1, 0, 16, 16, 16)};


    public static final VoxelShape[] NORTH_TOP = new VoxelShape[]{Shapes.empty(),
            Block.box(0, 14, 14, 16, 16, 16),
            Block.box(0, 12, 12, 16, 16, 16),
            Block.box(0, 8, 8, 16, 16, 16),
            Block.box(0, 4, 4, 16, 16, 16),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] EAST_TOP = new VoxelShape[]{Shapes.empty(),
            Block.box(0, 14, 0, 2, 16, 16),
            Block.box(0, 12, 0, 4, 16, 16),
            Block.box(0, 8, 0, 8, 16, 16),
            Block.box(0, 4, 0, 12, 16, 16),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] SOUTH_TOP = new VoxelShape[]{Shapes.empty(),
            Block.box(0, 14, 0, 16, 16, 2),
            Block.box(0, 12, 0, 16, 16, 4),
            Block.box(0, 8, 0, 16, 16, 8),
            Block.box(0, 4, 0, 16, 16, 12),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] WEST_TOP = new VoxelShape[]{Shapes.empty(),
            Block.box(14, 14, 0, 16, 16, 16),
            Block.box(12, 12, 0, 16, 16, 16),
            Block.box(8, 8, 0, 16, 16, 16),
            Block.box(4, 4, 0, 16, 16, 16),
            Block.box(0, 0.1, 0, 16, 16, 16)};


    public QuarterLayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, 3)
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, TopBottomShape.BOTTOM)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        TopBottomShape quarterLayerShape = blockState.getValue(TYPE);
        Direction direction = blockState.getValue(FACING);

        switch (quarterLayerShape) {
            case TOP -> {
                return switch (direction) {
                    case EAST -> EAST_TOP[blockState.getValue(LAYERS)];
                    case SOUTH -> SOUTH_TOP[blockState.getValue(LAYERS)];
                    case WEST -> WEST_TOP[blockState.getValue(LAYERS)];
                    default -> NORTH_TOP[blockState.getValue(LAYERS)];
                };
            }
            case BOTTOM -> {
                return switch (direction) {
                    case EAST -> EAST_BOTTOM[blockState.getValue(LAYERS)];
                    case SOUTH -> SOUTH_BOTTOM[blockState.getValue(LAYERS)];
                    case WEST -> WEST_BOTTOM[blockState.getValue(LAYERS)];
                    default -> NORTH_BOTTOM[blockState.getValue(LAYERS)];
                };
            }
        }
        return null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(blockpos);
        Direction clickedFace = blockPlaceContext.getClickedFace();
        if (blockstate.is(this) && clickedFace != Direction.UP && clickedFace != Direction.DOWN) {
            int i = blockstate.getValue(LAYERS);
            int newCount = Math.min(layerCount, i + 1);
            return blockstate.setValue(LAYERS, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < layerCount) && fluidstate.is(FluidTags.WATER)));
        } else {
            BlockState blockstate1 = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite())
                    .setValue(LAYERS, 1).setValue(TYPE, TopBottomShape.BOTTOM).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            return clickedFace != Direction.DOWN && (clickedFace == Direction.UP || !(blockPlaceContext.getClickLocation().y - (double) blockpos.getY() > 0.5D)) ?
                    blockstate1 : blockstate1.setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(TYPE, TopBottomShape.TOP);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext blockPlaceContext) {
        int currentLayers = state.getValue(LAYERS);
        if (blockPlaceContext.getItemInHand().getItem() == this.asItem()) {
            return currentLayers < layerCount;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        return state.getValue(LAYERS) < layerCount && SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluid);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.getValue(LAYERS) < layerCount && SimpleWaterloggedBlock.super.canPlaceLiquid(player, world, pos, state, fluid);
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathType) {
        return switch (pathType) {
            case LAND -> blockState.getValue(LAYERS) < 4;
            case WATER -> blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}