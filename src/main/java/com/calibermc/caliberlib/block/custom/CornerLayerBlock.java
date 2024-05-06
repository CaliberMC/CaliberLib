package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.LeftRightShape;
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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


import static com.calibermc.caliberlib.block.properties.ModBlockStateProperties.isSide;
import static net.minecraft.core.Direction.*;

public class CornerLayerBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<LeftRightShape> TYPE = ModBlockStateProperties.LEFT_RIGHT_SHAPE;
    public static final IntegerProperty LAYERS = ModBlockStateProperties.FIVE_LAYERS;
    public final int layerCount = 5;

    public static final VoxelShape[] LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(14, 0, 0, 16, 16, 16), Block.box(0, 0, 14, 14, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(12, 0, 0, 16, 16, 16), Block.box(0, 0, 12, 12, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 8, 8, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(4, 0, 0, 16, 16, 16), Block.box(0, 0, 4, 4, 16, 16), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 0, 14, 16, 16, 16), Block.box(0, 0, 0, 2, 16, 14), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 12, 16, 16, 16), Block.box(0, 0, 0, 4, 16, 12), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(0, 0, 0, 8, 16, 8), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 4, 16, 16, 16), Block.box(0, 0, 0, 12, 16, 4), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 0, 0, 2, 16, 16), Block.box(2, 0, 0, 16, 16, 2), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 4, 16, 16), Block.box(4, 0, 0, 16, 16, 4), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 0, 16, 16, 8), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 12, 16, 16), Block.box(12, 0, 0, 16, 16, 12), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 0, 0, 16, 16, 2), Block.box(14, 0, 2, 16, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 16, 16, 4), Block.box(12, 0, 4, 16, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(8, 0, 8, 16, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 16, 16, 12), Block.box(4, 0, 12, 16, 16, 16), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};

    public CornerLayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, 3)
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, LeftRightShape.RIGHT)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return blockState.getValue(LAYERS) < 5;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        LeftRightShape cornerShape = blockState.getValue(TYPE);
        Direction direction = blockState.getValue(FACING);

        switch (cornerShape) {
            case LEFT -> {
                return switch (direction) {
                    case EAST -> LEFT_EAST_RIGHT_NORTH[blockState.getValue(LAYERS)];
                    case SOUTH -> LEFT_SOUTH_RIGHT_EAST[blockState.getValue(LAYERS)];
                    case WEST -> LEFT_WEST_RIGHT_SOUTH[blockState.getValue(LAYERS)];
                    default -> LEFT_NORTH_RIGHT_WEST[blockState.getValue(LAYERS)];
                };
            }
            case RIGHT -> {
                return switch (direction) {
                    case EAST -> LEFT_SOUTH_RIGHT_EAST[blockState.getValue(LAYERS)];
                    case SOUTH -> LEFT_WEST_RIGHT_SOUTH[blockState.getValue(LAYERS)];
                    case WEST -> LEFT_NORTH_RIGHT_WEST[blockState.getValue(LAYERS)];
                    default -> LEFT_EAST_RIGHT_NORTH[blockState.getValue(LAYERS)];
                };
            }
        }
        return null;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(blockpos);
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        Direction clickedFace = blockPlaceContext.getClickedFace();
        double hitX = blockPlaceContext.getClickLocation().x - (double) blockpos.getX();
        double hitZ = blockPlaceContext.getClickLocation().z - (double) blockpos.getZ();
        if (blockstate.is(this) && clickedFace != Direction.UP && clickedFace != Direction.DOWN) {
            int i = blockstate.getValue(LAYERS);
            int newCount = Math.min(layerCount, i + 1);

            return blockstate.setValue(LAYERS, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < layerCount) && fluidstate.is(FluidTags.WATER)));
        } else {
            BlockState blockstate1 = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite())
                    .setValue(TYPE, LeftRightShape.RIGHT).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

            if (direction == NORTH && hitX < 0.5 || direction == EAST && hitZ < 0.5) {
                return blockstate1.setValue(TYPE, LeftRightShape.RIGHT).setValue(LAYERS, 1);
            } else if (direction == NORTH && hitX > 0.5 || direction == EAST && hitZ > 0.5) {
                return blockstate1.setValue(TYPE, LeftRightShape.LEFT).setValue(LAYERS, 1);
            } else if (direction == SOUTH && hitX > 0.5 || direction == WEST && hitZ > 0.5) {
                return blockstate1.setValue(TYPE, LeftRightShape.RIGHT).setValue(LAYERS, 1);
            } else if (direction == SOUTH && hitX < 0.5 || direction == WEST && hitZ < 0.5) {
                return blockstate1.setValue(TYPE, LeftRightShape.LEFT).setValue(LAYERS, 1);
            } else {
                return blockstate1.setValue(TYPE, LeftRightShape.RIGHT);
            }
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext blockPlaceContext) {
        int currentLayers = state.getValue(LAYERS);
        if (blockPlaceContext.getItemInHand().getItem() == this.asItem()) {
            Direction clickedFace = blockPlaceContext.getClickedFace();
            // Allow replacement if it's a side click and not at max layers
            return isSide(clickedFace) && currentLayers < layerCount;
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
            case LAND -> false;
            case WATER -> blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}