package com.calibermc.caliberlib.block.custom;


import com.calibermc.caliberlib.block.shapes.TopBottomShape;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class QuarterLayerBlock extends Block implements SimpleWaterloggedBlock {
    
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<TopBottomShape> HALF = ModBlockStateProperties.TOP_BOTTOM_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LAYERS = ModBlockStateProperties.FIVE_LAYERS;
    public final int layerCount = 5;
    
    public QuarterLayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LAYERS, 3)
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, TopBottomShape.BOTTOM)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LAYERS, FACING, HALF, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        TopBottomShape quarterLayerShape = pState.getValue(HALF);
        Direction direction = pState.getValue(FACING);

        switch (quarterLayerShape) {
            case TOP -> {
                return switch (direction) {
                    case EAST -> VoxelShapeHelper.QuarterLayerBlockShapes.EAST_TOP[pState.getValue(LAYERS)];
                    case SOUTH -> VoxelShapeHelper.QuarterLayerBlockShapes.SOUTH_TOP[pState.getValue(LAYERS)];
                    case WEST -> VoxelShapeHelper.QuarterLayerBlockShapes.WEST_TOP[pState.getValue(LAYERS)];
                    default -> VoxelShapeHelper.QuarterLayerBlockShapes.NORTH_TOP[pState.getValue(LAYERS)];
                };
            }
            case BOTTOM -> {
                return switch (direction) {
                    case EAST -> VoxelShapeHelper.QuarterLayerBlockShapes.EAST_BOTTOM[pState.getValue(LAYERS)];
                    case SOUTH -> VoxelShapeHelper.QuarterLayerBlockShapes.SOUTH_BOTTOM[pState.getValue(LAYERS)];
                    case WEST -> VoxelShapeHelper.QuarterLayerBlockShapes.WEST_BOTTOM[pState.getValue(LAYERS)];
                    default -> VoxelShapeHelper.QuarterLayerBlockShapes.NORTH_BOTTOM[pState.getValue(LAYERS)];
                };
            }
        }
        return null;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        BlockState blockstate = pContext.getLevel().getBlockState(blockpos);
        Direction clickedFace = pContext.getClickedFace();
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            int newCount = Math.min(layerCount, i + 1);
            return blockstate.setValue(LAYERS, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < layerCount) && fluidstate.is(FluidTags.WATER)));
        } else {
            BlockState blockstate1 = this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection())
                    .setValue(LAYERS, 1).setValue(HALF, TopBottomShape.BOTTOM).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            return clickedFace != Direction.DOWN && (clickedFace == Direction.UP || !(pContext.getClickLocation().y - (double) blockpos.getY() > 0.5D)) ?
                    blockstate1 : blockstate1.setValue(FACING, pContext.getHorizontalDirection()).setValue(HALF, TopBottomShape.TOP);
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
            case LAND -> pState.getValue(LAYERS) < 4;
            case WATER -> pLevel.getFluidState(pPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}