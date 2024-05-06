package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.shapes.TopBottomShape;
import com.calibermc.caliberlib.block.shapes.misc.BeamConnection;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.stream.Stream;

import static com.calibermc.caliberlib.block.properties.ModBlockStateProperties.isAir;
import static com.calibermc.caliberlib.block.properties.ModBlockStateProperties.isSide;

public class DiagonalBeamBlock extends Block implements SimpleWaterloggedBlock {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaliberLib.class);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<BeamConnection> CONNECT = ModBlockStateProperties.BEAM_CONNECTION;
    public static final IntegerProperty BEAM = ModBlockStateProperties.DIAGONAL_BEAM_SHAPE;;
    public final int beamShape = 2;

    public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
            Stream.of(
                    Block.box(5.10938, 2, 11.25, 10.92188, 4, 14.75),
                    Block.box(5.10938, 4, 9.25, 10.92188, 6, 12.75),
                    Block.box(5.10938, 6, 7.25, 10.92188, 8, 10.75),
                    Block.box(5.10938, 8, 5.25, 10.92188, 10, 8.75),
                    Block.box(5.10938, 10, 3.25, 10.92188, 12, 6.75),
                    Block.box(5.10938, 12, 1.25, 10.92188, 14, 4.75)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(5.10938, 2, 11.25, 10.92188, 4, 14.75),
                    Block.box(5.10938, 4, 9.25, 10.92188, 6, 12.75),
                    Block.box(5.07812, 2, 1.25, 10.89062, 4, 4.75),
                    Block.box(5.07812, 4, 3.25, 10.89062, 6, 6.75),
                    Block.box(5.10938, 6, 5.25, 10.92188, 8, 10.75),
                    Block.box(5.10938, 8, 7.25, 10.92188, 10, 8.75)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};

    public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
            Stream.of(
                    Block.box(1.25, 2, 5.10938, 4.75, 4, 10.92188),
                    Block.box(3.25, 4, 5.10938, 6.75, 6, 10.92188),
                    Block.box(5.25, 6, 5.10938, 8.75, 8, 10.92188),
                    Block.box(7.25, 8, 5.10938, 10.75, 10, 10.92188),
                    Block.box(9.25, 10, 5.10938, 12.75, 12, 10.92188),
                    Block.box(11.25, 12, 5.10938, 14.75, 14, 10.92188)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(1.25, 2, 5.10938, 4.75, 4, 10.92188),
                    Block.box(3.25, 4, 5.10938, 6.75, 6, 10.92188),
                    Block.box(11.25, 2, 5.07812, 14.75, 4, 10.89062),
                    Block.box(9.25, 4, 5.07812, 12.75, 6, 10.89062),
                    Block.box(5.25, 6, 5.10938, 10.75, 8, 10.92188),
                    Block.box(7.25, 8, 5.10938, 8.75, 10, 10.92188)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};

    public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
            Stream.of(
                    Block.box(5.07812, 2, 1.25, 10.89062, 4, 4.75),
                    Block.box(5.07812, 4, 3.25, 10.89062, 6, 6.75),
                    Block.box(5.07812, 6, 5.25, 10.89062, 8, 8.75),
                    Block.box(5.07812, 8, 7.25, 10.89062, 10, 10.75),
                    Block.box(5.07812, 10, 9.25, 10.89062, 12, 12.75),
                    Block.box(5.07812, 12, 11.25, 10.89062, 14, 14.75)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(5.07812, 2, 1.25, 10.89062, 4, 4.75),
                    Block.box(5.07812, 4, 3.25, 10.89062, 6, 6.75),
                    Block.box(5.10938, 2, 11.25, 10.92188, 4, 14.75),
                    Block.box(5.10938, 4, 9.25, 10.92188, 6, 12.75),
                    Block.box(5.07812, 6, 5.25, 10.89062, 8, 10.75),
                    Block.box(5.07812, 8, 7.25, 10.89062, 10, 8.75)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};

    public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
            Stream.of(
                    Block.box(11.25, 2, 5.07812, 14.75, 4, 10.89062),
                    Block.box(9.25, 4, 5.07812, 12.75, 6, 10.89062),
                    Block.box(7.25, 6, 5.07812, 10.75, 8, 10.89062),
                    Block.box(5.25, 8, 5.07812, 8.75, 10, 10.89062),
                    Block.box(3.25, 10, 5.07812, 6.75, 12, 10.89062),
                    Block.box(1.25, 12, 5.07812, 4.75, 14, 10.89062)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(11.25, 2, 5.07812, 14.75, 4, 10.89062),
                    Block.box(9.25, 4, 5.07812, 12.75, 6, 10.89062),
                    Block.box(1.25, 2, 5.10938, 4.75, 4, 10.92188),
                    Block.box(3.25, 4, 5.10938, 6.75, 6, 10.92188),
                    Block.box(5.25, 6, 5.07812, 10.75, 8, 10.89062),
                    Block.box(7.25, 8, 5.07812, 8.75, 10, 10.89062)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};

    public DiagonalBeamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BEAM, 1)
                .setValue(CONNECT, BeamConnection.NONE)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.FALSE));

    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BEAM, CONNECT, WATERLOGGED);
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
        BlockPos currentPos = blockPlaceContext.getClickedPos();
        BlockState blockstate = blockPlaceContext.getLevel().getBlockState(currentPos);
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(currentPos);
        LevelAccessor levelAccessor = blockPlaceContext.getLevel();
        Direction facing = blockPlaceContext.getHorizontalDirection().getOpposite();
        Direction pOppositeFacing = blockPlaceContext.getHorizontalDirection();

        if (blockstate.is(this)) {
            int newCount = blockstate.getValue(BEAM) + 1;
            if (newCount > beamShape) {
                newCount = 1;
            }
            blockPlaceContext.getItemInHand().grow(1);
            return blockstate.setValue(BEAM, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < beamShape) && fluidstate.is(FluidTags.WATER)));
        } else {
            blockstate = this.defaultBlockState().setValue(BEAM, 1).setValue(FACING, facing).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        }

        return getConnectionState(blockstate, currentPos, levelAccessor, facing, pOppositeFacing);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext blockPlaceContext) {
        if (blockPlaceContext.getItemInHand().getItem() == this.asItem()) {
            Direction clickedFace = blockPlaceContext.getClickedFace();
            return isSide(clickedFace) && blockPlaceContext.replacingClickedOnBlock();
        }
        return false;
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    public BlockState updateShape(BlockState blockState, Direction facing, BlockState facingState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos facingPos) {

        Direction placedBlockFacing = blockState.getValue(FACING);
        Direction placedBlockOppositeFacing = blockState.getValue(FACING).getOpposite();

        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return getConnectionState(blockState, currentPos, levelAccessor, placedBlockFacing, placedBlockOppositeFacing);
    }

    @NotNull
    public BlockState getConnectionState(BlockState blockstate, BlockPos currentPos, LevelAccessor levelAccessor, Direction placedBlockFacing, Direction placedBlockOppositeFacing) {// BlockState ublockState, BlockState downState, BlockState forwardState, BlockState backwardState, BlockState forwardUblockState, BlockState backwardUblockState, Direction placedBlockFacing, Direction placedBlockOppositeFacing

        BlockPos backwardPos = currentPos.relative(placedBlockFacing);
        BlockPos forwardPos = currentPos.relative(placedBlockFacing.getOpposite());
        BlockPos backwardUblockPos = currentPos.relative(placedBlockFacing).above();
        BlockPos forwardUblockPos = currentPos.relative(placedBlockFacing.getOpposite()).above();
        BlockPos backwardDownPos = currentPos.relative(placedBlockFacing).below();
        BlockPos forwardDownPos = currentPos.relative(placedBlockFacing.getOpposite()).below();
        BlockPos ublockPos = currentPos.above();
        BlockPos downPos = currentPos.below();

        BlockState backwardState = levelAccessor.getBlockState(backwardPos);
        BlockState forwardState = levelAccessor.getBlockState(forwardPos);
        BlockState backwardUblockState = levelAccessor.getBlockState(backwardUblockPos);
        BlockState forwardUblockState = levelAccessor.getBlockState(forwardUblockPos);
        BlockState backwardDownState = levelAccessor.getBlockState(backwardDownPos);
        BlockState forwardDownState = levelAccessor.getBlockState(forwardDownPos);
        BlockState ublockState = levelAccessor.getBlockState(ublockPos);
        BlockState downState = levelAccessor.getBlockState(downPos);

        TopBottomShape backwardHorizontal = backwardState.getBlock() instanceof HorizontalBeamBlock ? backwardState.getValue(HorizontalBeamBlock.TYPE) : null;
        TopBottomShape forwardHorizontal = forwardState.getBlock() instanceof HorizontalBeamBlock ? forwardState.getValue(HorizontalBeamBlock.TYPE) : null;

        Direction upBlockVertical = ublockState.getBlock() instanceof VerticalBeamBlock ? ublockState.getValue(FACING) : null;
        Direction downBlockVertical = downState.getBlock() instanceof VerticalBeamBlock ? downState.getValue(FACING) : null;
        Direction backwardDownVertical = backwardDownState.getBlock() instanceof VerticalBeamBlock ? backwardDownState.getValue(FACING) : null;
        Direction backwardBlockDiagonal = backwardState.getBlock() instanceof DiagonalBeamBlock && (backwardState.getValue(BEAM) == 1) ? backwardState.getValue(FACING) : null;
        Direction forwardBlockDiagonal = forwardState.getBlock() instanceof DiagonalBeamBlock && (forwardState.getValue(BEAM) == 1) ? forwardState.getValue(FACING) : null;
        Direction backwardBlockDiagonal2 = backwardState.getBlock() instanceof DiagonalBeamBlock && (backwardState.getValue(BEAM) == 2) ? backwardState.getValue(FACING) : null;
        Direction forwardBlockDiagonal2 = forwardState.getBlock() instanceof DiagonalBeamBlock && (forwardState.getValue(BEAM) == 2) ? forwardState.getValue(FACING) : null;
        Direction backwardUpDiagonal = backwardUblockState.getBlock() instanceof DiagonalBeamBlock ? backwardUblockState.getValue(FACING) : null;

        BeamConnection connect = BeamConnection.NONE;
        if (forwardHorizontal == TopBottomShape.BOTTOM && downBlockVertical == placedBlockFacing
                && backwardHorizontal == TopBottomShape.TOP && upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.AH_AV_CH_CV;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM && downBlockVertical == placedBlockFacing
                && backwardHorizontal == TopBottomShape.TOP) {
            connect = BeamConnection.AH_AV_CH;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM && downBlockVertical == placedBlockFacing
                && upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.AH_AV_CV;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM && backwardHorizontal == TopBottomShape.TOP
                && upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.AH_CH_CV;

        } else if (downBlockVertical == placedBlockFacing && backwardHorizontal == TopBottomShape.TOP
                && upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.AV_CH_CV;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM && downBlockVertical == placedBlockFacing
                && backwardBlockDiagonal == placedBlockOppositeFacing && (backwardBlockDiagonal == Direction.NORTH || backwardBlockDiagonal == Direction.EAST)) {
            connect = BeamConnection.AH_AV_N;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM && backwardBlockDiagonal == placedBlockOppositeFacing
                && (backwardBlockDiagonal == Direction.NORTH || backwardBlockDiagonal == Direction.EAST)) {
            connect = BeamConnection.AH_N;

        } else if (downBlockVertical == placedBlockFacing && backwardBlockDiagonal == placedBlockOppositeFacing
                && (backwardBlockDiagonal == Direction.NORTH || backwardBlockDiagonal == Direction.EAST)) {
            connect = BeamConnection.AV_N;

            //------------------- | BEAM 2 SPECIFIC CONDITIONS |------------------------------------------------------------

        } else if (forwardHorizontal == TopBottomShape.BOTTOM && downBlockVertical == placedBlockFacing
                && (backwardBlockDiagonal2 == Direction.NORTH || backwardBlockDiagonal2 == Direction.EAST)) {
            connect = BeamConnection.AH_AV_N;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM
                && (backwardBlockDiagonal2 == Direction.NORTH || backwardBlockDiagonal2 == Direction.EAST)) {
            connect = BeamConnection.AH_N;

        } else if (!(backwardDownVertical == placedBlockFacing) && (downBlockVertical == placedBlockFacing
                && (backwardBlockDiagonal2 == Direction.NORTH || backwardBlockDiagonal2 == Direction.EAST))) {
            connect = BeamConnection.AV_N;

        } else if (backwardHorizontal == TopBottomShape.BOTTOM && downBlockVertical == placedBlockOppositeFacing
                && (forwardBlockDiagonal2 == Direction.NORTH || forwardBlockDiagonal2 == Direction.EAST)) {
            connect = BeamConnection.DH_DV_M;

        } else if (backwardHorizontal == TopBottomShape.BOTTOM
                && (forwardBlockDiagonal2 == Direction.NORTH || forwardBlockDiagonal2 == Direction.EAST)) {
            connect = BeamConnection.DH_M;

        } else if (downBlockVertical == placedBlockOppositeFacing && forwardBlockDiagonal2 == placedBlockOppositeFacing
                 && !((forwardState.getValue(CONNECT) == BeamConnection.DV_M) && (forwardBlockDiagonal2 == Direction.NORTH || forwardBlockDiagonal2 == Direction.EAST))) {
            connect = BeamConnection.DV_M;

            //--------------------------------------------------------------------------------------------------------------

        } else if (forwardHorizontal == TopBottomShape.BOTTOM
                && downBlockVertical == placedBlockFacing) {
            connect = BeamConnection.AH_AV;

        } else if (downBlockVertical == placedBlockFacing
                && backwardHorizontal == TopBottomShape.TOP) {
            connect = BeamConnection.AV_CH;

        } else if (downBlockVertical == placedBlockFacing
                && upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.AV_CV;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM
                && upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.AH_CV;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM
                && backwardHorizontal == TopBottomShape.TOP) {
            connect = BeamConnection.AH_CH;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM
                && backwardHorizontal == TopBottomShape.BOTTOM) {
            connect = BeamConnection.AH_DH;

        } else if (downBlockVertical == placedBlockFacing
                && backwardHorizontal == TopBottomShape.BOTTOM) {
            connect = BeamConnection.AV_DH;

        } else if (backwardHorizontal == TopBottomShape.TOP
                && upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.CH_CV;

        } else if (forwardHorizontal == TopBottomShape.BOTTOM) {
            connect = BeamConnection.AH;

        } else if (backwardHorizontal == TopBottomShape.TOP) {
            connect = BeamConnection.CH;

        } else if (upBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.CV;

        } else if (downBlockVertical == placedBlockFacing) {
            connect = BeamConnection.AV;

        } else if (downBlockVertical == placedBlockOppositeFacing) {
            connect = BeamConnection.DV;

        } else if (backwardHorizontal == TopBottomShape.BOTTOM) {
            connect = BeamConnection.DH;

            //------------------- | BEAM 2 SPECIFIC CONDITIONS |------------------------------------------------------------

        } else if (backwardBlockDiagonal2 ==  Direction.NORTH || backwardBlockDiagonal2 == Direction.EAST) {
            connect = BeamConnection.N;

        } else if (forwardBlockDiagonal2 == placedBlockOppositeFacing && !(forwardDownState.getBlock() instanceof VerticalBeamBlock) && (forwardBlockDiagonal2 == Direction.SOUTH || forwardBlockDiagonal2 == Direction.WEST)) {
            connect = BeamConnection.M;

            //--------------------------------------------------------------------------------------------------------------

        } else if (forwardBlockDiagonal == placedBlockOppositeFacing && !(forwardDownState.getBlock() instanceof VerticalBeamBlock) && (forwardBlockDiagonal == Direction.SOUTH || forwardBlockDiagonal == Direction.WEST)) {
            connect = BeamConnection.M;


        } else if ((backwardBlockDiagonal == Direction.NORTH || backwardBlockDiagonal == Direction.EAST)
                && ((backwardUpDiagonal == placedBlockOppositeFacing) || (isAir(backwardUblockState) && isAir(forwardUblockState)))) { //forwardUpDiagonal == placedBlockFacing ||
            connect = BeamConnection.N;


        } else {
            connect = BeamConnection.NONE;
        }

        return blockstate.setValue(CONNECT, connect);
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