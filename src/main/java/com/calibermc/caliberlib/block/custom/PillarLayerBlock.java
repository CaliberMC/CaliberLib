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
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static com.calibermc.caliberlib.block.properties.ModBlockStateProperties.isSide;


public class PillarLayerBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<TopBottomShape> TYPE = ModBlockStateProperties.TOP_BOTTOM_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty LAYERS = ModBlockStateProperties.FIVE_LAYERS;
    public final int layerCount = 5;

    public static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(),
            Block.box(7, 0, 7, 9, 16, 9),
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(4, 0, 4, 12, 16, 12),
            Block.box(2, 0, 2, 14, 16, 14),
            Block.box(0, 0.1, 0, 16, 16, 16)};

    public PillarLayerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any() // ? this.defaultBlockState()
                .setValue(LAYERS, 3)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return blockState.getValue(LAYERS) < 5;
//        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, WATERLOGGED);
    }

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[blockState.getValue(LAYERS) - 1];
    }

    public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter pReader, BlockPos blockPos) {
        return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
    }

    public VoxelShape getVisualShape(BlockState blockState, BlockGetter pReader, BlockPos blockPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[blockState.getValue(LAYERS)];
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(blockpos);
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        Direction clickedFace = blockPlaceContext.getClickedFace();
        if (blockstate.is(this) && clickedFace != Direction.UP && clickedFace != Direction.DOWN) {
            int i = blockstate.getValue(LAYERS);
            int newCount = Math.min(layerCount, i + 1);

            return blockstate.setValue(LAYERS, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < layerCount) && fluidstate.is(FluidTags.WATER)));
        } else {
            return this.defaultBlockState().setValue(LAYERS, 1).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
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
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
            case LAND -> blockState.getValue(LAYERS) < 5;
            case WATER -> blockGetter.getFluidState(blockPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}