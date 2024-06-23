package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.QuadShape;
import com.calibermc.caliberlib.block.shapes.voxels.VoxelShapeHelper;
import com.calibermc.caliberlib.util.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.core.Direction.*;

public class EighthLayerBlock extends Block implements SimpleWaterloggedBlock {

    public static final IntegerProperty LAYERS = ModBlockStateProperties.FIVE_LAYERS;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<QuadShape> TYPE = ModBlockStateProperties.QUAD_SHAPE;
    public final int layerCount = 5;

    public EighthLayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, QuadShape.RIGHT)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LAYERS, FACING, TYPE, WATERLOGGED);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        QuadShape cornerSlabShape = pState.getValue(TYPE);
        Direction direction = pState.getValue(FACING);
        switch (cornerSlabShape) {
            case TOP_LEFT -> {
                return switch (direction) {
                    case EAST -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_EAST_RIGHT_NORTH[pState.getValue(LAYERS)];
                    case SOUTH -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_SOUTH_RIGHT_EAST[pState.getValue(LAYERS)];
                    case WEST -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_WEST_RIGHT_SOUTH[pState.getValue(LAYERS)];
                    default -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_NORTH_RIGHT_WEST[pState.getValue(LAYERS)];
                };
            }
            case TOP_RIGHT -> {
                return switch (direction) {
                    case EAST -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_SOUTH_RIGHT_EAST[pState.getValue(LAYERS)];
                    case SOUTH -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_WEST_RIGHT_SOUTH[pState.getValue(LAYERS)];
                    case WEST -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_NORTH_RIGHT_WEST[pState.getValue(LAYERS)];
                    default -> VoxelShapeHelper.EightLayerBlockShapes.TOP_LEFT_EAST_RIGHT_NORTH[pState.getValue(LAYERS)];
                };
            }
            case LEFT -> {
                return switch (direction) {
                    case EAST -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_EAST_RIGHT_NORTH[pState.getValue(LAYERS)];
                    case SOUTH -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_SOUTH_RIGHT_EAST[pState.getValue(LAYERS)];
                    case WEST -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_WEST_RIGHT_SOUTH[pState.getValue(LAYERS)];
                    default -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_NORTH_RIGHT_WEST[pState.getValue(LAYERS)];
                };
            }
            default -> {
                return switch (direction) {
                    case EAST -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_SOUTH_RIGHT_EAST[pState.getValue(LAYERS)];
                    case SOUTH -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_WEST_RIGHT_SOUTH[pState.getValue(LAYERS)];
                    case WEST -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_NORTH_RIGHT_WEST[pState.getValue(LAYERS)];
                    default -> VoxelShapeHelper.EightLayerBlockShapes.LEFT_EAST_RIGHT_NORTH[pState.getValue(LAYERS)];
                };
            }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = pContext.getLevel().getBlockState(blockpos);
        Direction direction = pContext.getHorizontalDirection();
        Direction clickedFace = pContext.getClickedFace();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        double hitY = pContext.getClickLocation().y - (double) blockpos.getY();
        double hitX = pContext.getClickLocation().x - (double) blockpos.getX();
        double hitZ = pContext.getClickLocation().z - (double) blockpos.getZ();

        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            int newCount = Math.min(layerCount, i + 1);

            return blockstate.setValue(LAYERS, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < layerCount) && fluidstate.is(FluidTags.WATER)));
        } else {
            BlockState blockstate1 = this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection())
                    .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));

            if ((direction == NORTH && hitX > 0.5 || direction == EAST && hitZ > 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.RIGHT).setValue(LAYERS, 1);
            } else if ((direction == NORTH && hitX > 0.5 || direction == EAST && hitZ > 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_RIGHT).setValue(LAYERS, 1);
            } else if ((direction == NORTH && hitX < 0.5 || direction == EAST && hitZ < 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.LEFT).setValue(LAYERS, 1);
            } else if ((direction == NORTH && hitX < 0.5 || direction == EAST && hitZ < 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_LEFT).setValue(LAYERS, 1);

            } else if ((direction == SOUTH && hitX < 0.5 || direction == WEST && hitZ < 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.RIGHT).setValue(LAYERS, 1);
            } else if ((direction == SOUTH && hitX < 0.5 || direction == WEST && hitZ < 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_RIGHT).setValue(LAYERS, 1);
            } else if ((direction == SOUTH && hitX > 0.5 || direction == WEST && hitZ > 0.5) && hitY < 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.LEFT).setValue(LAYERS, 1);
            } else if ((direction == SOUTH && hitX > 0.5 || direction == WEST && hitZ > 0.5) && hitY > 0.5) {
                return blockstate1.setValue(TYPE, QuadShape.TOP_LEFT).setValue(LAYERS, 1);

            } else {
                return blockstate1.setValue(TYPE, QuadShape.TOP_RIGHT);
            }
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext pContext) {
        int currentLayers = state.getValue(LAYERS);
        if (pContext.getItemInHand().getItem() == this.asItem()) {
            return currentLayers < layerCount;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        return state.getValue(LAYERS) < layerCount && SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluid);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.getValue(LAYERS) < layerCount && SimpleWaterloggedBlock.super.canPlaceLiquid(world, pos, state, fluid);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND, AIR -> false;
            case WATER -> pLevel.getFluidState(pPos).is(FluidTags.WATER);
        };
    }
}