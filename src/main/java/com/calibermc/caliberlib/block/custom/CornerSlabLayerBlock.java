package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.QuadShape;
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


import static net.minecraft.core.Direction.*;

public class CornerSlabLayerBlock extends Block implements SimpleWaterloggedBlock {

    public static final IntegerProperty LAYERS = ModBlockStateProperties.FIVE_LAYERS;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<QuadShape> TYPE = ModBlockStateProperties.QUAD_SHAPE;
    public final int layerCount = 5;

    public static final VoxelShape[] LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 0, 14, 16, 2, 16), Block.box(14, 0, 0, 16, 2, 14), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 12, 16, 4, 16), Block.box(12, 0, 0, 16, 4, 12), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 8, 16, 8, 16), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 4, 16, 12, 16), Block.box(4, 0, 0, 16, 12, 4), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 0, 0, 2, 2, 16), Block.box(2, 0, 14, 16, 2, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 4, 4, 16), Block.box(4, 0, 12, 16, 4, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 8, 8, 16), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 12, 12, 16), Block.box(12, 0, 4, 16, 12, 16), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 0, 0, 16, 2, 2), Block.box(0, 0, 2, 2, 2, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 16, 4, 4), Block.box(0, 0, 4, 4, 4, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 16, 8, 8), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 0, 0, 16, 12, 12), Block.box(0, 0, 12, 12, 12, 16), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(14, 0, 0, 16, 2, 16), Block.box(0, 0, 0, 14, 2, 2), BooleanOp.OR),
            Shapes.join(Block.box(12, 0, 0, 16, 4, 16), Block.box(0, 0, 0, 12, 4, 4), BooleanOp.OR),
            Shapes.join(Block.box(8, 0, 0, 16, 8, 16), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR),
            Shapes.join(Block.box(4, 0, 0, 16, 12, 16), Block.box(0, 0, 0, 4, 12, 12), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};

    public static final VoxelShape[] TOP_LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 14, 14, 16, 16, 16), Block.box(14, 14, 0, 16, 16, 14), BooleanOp.OR),
            Shapes.join(Block.box(0, 12, 12, 16, 16, 16), Block.box(12, 12, 0, 16, 16, 12), BooleanOp.OR),
            Shapes.join(Block.box(0, 8, 8, 16, 16, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
            Shapes.join(Block.box(0, 4, 4, 16, 16, 16), Block.box(4, 4, 0, 16, 16, 4), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] TOP_LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 14, 0, 2, 16, 16), Block.box(2, 14, 14, 16, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 12, 0, 4, 16, 16), Block.box(4, 12, 12, 16, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 8, 0, 8, 16, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 4, 0, 12, 16, 16), Block.box(12, 4, 4, 16, 16, 16), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] TOP_LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(0, 14, 0, 16, 16, 2), Block.box(0, 14, 2, 2, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 12, 0, 16, 16, 4), Block.box(0, 12, 4, 4, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 8, 0, 16, 16, 8), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR),
            Shapes.join(Block.box(0, 4, 0, 16, 16, 12), Block.box(0, 4, 12, 12, 16, 16), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};
    public static final VoxelShape[] TOP_LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
            Shapes.join(Block.box(14, 14, 0, 16, 16, 16), Block.box(0, 14, 0, 14, 16, 2), BooleanOp.OR),
            Shapes.join(Block.box(12, 12, 0, 16, 16, 16), Block.box(0, 12, 0, 12, 16, 4), BooleanOp.OR),
            Shapes.join(Block.box(8, 8, 0, 16, 16, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
            Shapes.join(Block.box(4, 4, 0, 16, 16, 16), Block.box(0, 4, 0, 4, 16, 12), BooleanOp.OR),
            Block.box(0, 0.1, 0, 16, 16, 16)};

    public CornerSlabLayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, QuadShape.RIGHT)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        QuadShape cornerSlabShape = blockState.getValue(TYPE);
        Direction direction = blockState.getValue(FACING);
        switch (cornerSlabShape) {
            case TOP_LEFT -> {
                return switch (direction) {
                    case EAST -> TOP_LEFT_EAST_RIGHT_NORTH[blockState.getValue(LAYERS)];
                    case SOUTH -> TOP_LEFT_SOUTH_RIGHT_EAST[blockState.getValue(LAYERS)];
                    case WEST -> TOP_LEFT_WEST_RIGHT_SOUTH[blockState.getValue(LAYERS)];
                    default -> TOP_LEFT_NORTH_RIGHT_WEST[blockState.getValue(LAYERS)];
                };
            }
            case TOP_RIGHT -> {
                return switch (direction) {
                    case EAST -> TOP_LEFT_SOUTH_RIGHT_EAST[blockState.getValue(LAYERS)];
                    case SOUTH -> TOP_LEFT_WEST_RIGHT_SOUTH[blockState.getValue(LAYERS)];
                    case WEST -> TOP_LEFT_NORTH_RIGHT_WEST[blockState.getValue(LAYERS)];
                    default -> TOP_LEFT_EAST_RIGHT_NORTH[blockState.getValue(LAYERS)];
                };
            }
            case LEFT -> {
                return switch (direction) {
                    case EAST -> LEFT_EAST_RIGHT_NORTH[blockState.getValue(LAYERS)];
                    case SOUTH -> LEFT_SOUTH_RIGHT_EAST[blockState.getValue(LAYERS)];
                    case WEST -> LEFT_WEST_RIGHT_SOUTH[blockState.getValue(LAYERS)];
                    default -> LEFT_NORTH_RIGHT_WEST[blockState.getValue(LAYERS)];
                };
            }
            default -> {
                return switch (direction) {
                    case EAST -> LEFT_SOUTH_RIGHT_EAST[blockState.getValue(LAYERS)];
                    case SOUTH -> LEFT_WEST_RIGHT_SOUTH[blockState.getValue(LAYERS)];
                    case WEST -> LEFT_NORTH_RIGHT_WEST[blockState.getValue(LAYERS)];
                    default -> LEFT_EAST_RIGHT_NORTH[blockState.getValue(LAYERS)];
                };
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(blockpos);
        Direction direction = blockPlaceContext.getHorizontalDirection().getOpposite();
        Direction clickedFace = blockPlaceContext.getClickedFace();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        double hitY = blockPlaceContext.getClickLocation().y - (double) blockpos.getY();
        double hitX = blockPlaceContext.getClickLocation().x - (double) blockpos.getX();
        double hitZ = blockPlaceContext.getClickLocation().z - (double) blockpos.getZ();

        if (blockstate.is(this) && clickedFace != Direction.UP && clickedFace != Direction.DOWN) {
            int i = blockstate.getValue(LAYERS);
            int newCount = Math.min(layerCount, i + 1);

            return blockstate.setValue(LAYERS, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < layerCount) && fluidstate.is(FluidTags.WATER)));
        } else {
            BlockState blockstate1 = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite())
                    .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));

            if ((direction == NORTH && hitX < 0.5 || direction == EAST && hitZ < 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.RIGHT).setValue(LAYERS, 1);
            } else if ((direction == NORTH && hitX > 0.5 || direction == EAST && hitZ > 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.LEFT).setValue(LAYERS, 1);
            } else if ((direction == SOUTH && hitX > 0.5 || direction == WEST && hitZ > 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.RIGHT).setValue(LAYERS, 1);
            } else if ((direction == SOUTH && hitX < 0.5 || direction == WEST && hitZ < 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.LEFT).setValue(LAYERS, 1);
            } else if ((direction == NORTH && hitX < 0.5 || direction == EAST && hitZ < 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_RIGHT).setValue(LAYERS, 1);
            } else if ((direction == NORTH && hitX > 0.5 || direction == EAST && hitZ > 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_LEFT).setValue(LAYERS, 1);
            } else if ((direction == SOUTH && hitX > 0.5 || direction == WEST && hitZ > 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_RIGHT).setValue(LAYERS, 1);
            } else if ((direction == SOUTH && hitX < 0.5 || direction == WEST && hitZ < 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_LEFT).setValue(LAYERS, 1);
            } else {
                return blockstate1.setValue(TYPE, QuadShape.RIGHT).setValue(LAYERS, 1);
            }
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
            case LAND -> false;
            case WATER -> blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}