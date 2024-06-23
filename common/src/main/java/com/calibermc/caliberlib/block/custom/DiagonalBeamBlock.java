package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.TopBottomShape;
import com.calibermc.caliberlib.block.shapes.misc.BeamConnection;
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
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static com.calibermc.caliberlib.util.ModBlockStateProperties.isSide;

public class DiagonalBeamBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<BeamConnection> CONNECT = ModBlockStateProperties.BEAM_CONNECTION;
    public static final IntegerProperty BEAM = ModBlockStateProperties.DIAGONAL_BEAM_SHAPE;
    public final int beamShape = 2;

    public DiagonalBeamBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BEAM, 1)
                .setValue(CONNECT, BeamConnection.NONE)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, BEAM, CONNECT, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (direction) {
            case EAST -> VoxelShapeHelper.DiagonalBeamBlockShapes.SHAPE_EAST[pState.getValue(BEAM)];
            case SOUTH -> VoxelShapeHelper.DiagonalBeamBlockShapes.SHAPE_SOUTH[pState.getValue(BEAM)];
            case WEST -> VoxelShapeHelper.DiagonalBeamBlockShapes.SHAPE_WEST[pState.getValue(BEAM)];
            default -> VoxelShapeHelper.DiagonalBeamBlockShapes.SHAPE_NORTH[pState.getValue(BEAM)];
        };
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos pCurrentPos = pContext.getClickedPos();
        BlockState blockstate = pContext.getLevel().getBlockState(pCurrentPos);
        FluidState fluidstate = pContext.getLevel().getFluidState(pCurrentPos);
        LevelAccessor pLevel = pContext.getLevel();
        Direction pFacing = pContext.getHorizontalDirection();
        Direction pOppositeFacing = pContext.getHorizontalDirection();

        if (blockstate.is(this)) {
            int newCount = blockstate.getValue(BEAM) + 1;
            if (newCount > beamShape) {
                newCount = 1;
            }
            pContext.getItemInHand().grow(1);
            return blockstate.setValue(BEAM, Integer.valueOf(newCount)).
                    setValue(WATERLOGGED, Boolean.valueOf((newCount < beamShape) && fluidstate.is(FluidTags.WATER)));
        } else {
            blockstate = this.defaultBlockState().setValue(BEAM, 1).setValue(FACING, pFacing).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        }

        return getConnectionState(blockstate, pCurrentPos, pLevel, pFacing, pOppositeFacing);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext pContext) {
        if (pContext.getItemInHand().getItem() == this.asItem()) {
            Direction clickedFace = pContext.getClickedFace();
            return isSide(clickedFace) && pContext.replacingClickedOnBlock();
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
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {

        Direction placedBlockFacing = pState.getValue(FACING);
        Direction placedBlockOppositeFacing = pState.getValue(FACING).getOpposite();

        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return getConnectionState(pState, pCurrentPos, pLevel, placedBlockFacing, placedBlockOppositeFacing);
    }

    @NotNull
    public BlockState getConnectionState(BlockState blockstate, BlockPos pCurrentPos, LevelAccessor pLevel, Direction placedBlockFacing, Direction placedBlockOppositeFacing) {// BlockState upState, BlockState downState, BlockState forwardState, BlockState backwardState, BlockState forwardUpState, BlockState backwardUpState, Direction placedBlockFacing, Direction placedBlockOppositeFacing

        BlockPos forwardPos = pCurrentPos.relative(placedBlockFacing);
        BlockPos backwardPos = pCurrentPos.relative(placedBlockFacing.getOpposite());
        BlockPos forwardUpPos = pCurrentPos.relative(placedBlockFacing).above();
        BlockPos backwardUpPos = pCurrentPos.relative(placedBlockFacing.getOpposite()).above();
        BlockPos forwardDownPos = pCurrentPos.relative(placedBlockFacing).below();
        BlockPos backwardDownPos = pCurrentPos.relative(placedBlockFacing.getOpposite()).below();
        BlockPos upPos = pCurrentPos.above();
        BlockPos downPos = pCurrentPos.below();

        BlockState backwardState = pLevel.getBlockState(backwardPos);
        BlockState forwardState = pLevel.getBlockState(forwardPos);
        BlockState backwardUpState = pLevel.getBlockState(backwardUpPos);
        BlockState forwardUpState = pLevel.getBlockState(forwardUpPos);
        BlockState backwardDownState = pLevel.getBlockState(backwardDownPos);
        BlockState forwardDownState = pLevel.getBlockState(forwardDownPos);
        BlockState upState = pLevel.getBlockState(upPos);
        BlockState downState = pLevel.getBlockState(downPos);

        TopBottomShape backwardHorizontal = backwardState.getBlock() instanceof HorizontalBeamBlock ? backwardState.getValue(HorizontalBeamBlock.HALF) : null;
        TopBottomShape forwardHorizontal = forwardState.getBlock() instanceof HorizontalBeamBlock ? forwardState.getValue(HorizontalBeamBlock.HALF) : null;

        Direction upBlockVertical = upState.getBlock() instanceof VerticalBeamBlock ? upState.getValue(FACING) : null;
        Direction downBlockVertical = downState.getBlock() instanceof VerticalBeamBlock ? downState.getValue(FACING) : null;
        Direction backwardDownVertical = backwardDownState.getBlock() instanceof VerticalBeamBlock ? backwardDownState.getValue(FACING) : null;
        Direction backwardBlockDiagonal = backwardState.getBlock() instanceof DiagonalBeamBlock && (backwardState.getValue(BEAM) == 1) ? backwardState.getValue(FACING) : null;
        Direction forwardBlockDiagonal = forwardState.getBlock() instanceof DiagonalBeamBlock && (forwardState.getValue(BEAM) == 1) ? forwardState.getValue(FACING) : null;
        Direction backwardBlockDiagonal2 = backwardState.getBlock() instanceof DiagonalBeamBlock && (backwardState.getValue(BEAM) == 2) ? backwardState.getValue(FACING) : null;
        Direction forwardBlockDiagonal2 = forwardState.getBlock() instanceof DiagonalBeamBlock && (forwardState.getValue(BEAM) == 2) ? forwardState.getValue(FACING) : null;
        Direction backwardUpDiagonal = backwardUpState.getBlock() instanceof DiagonalBeamBlock ? backwardUpState.getValue(FACING) : null;

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

        } else if (backwardBlockDiagonal2 == Direction.NORTH || backwardBlockDiagonal2 == Direction.EAST) {
            connect = BeamConnection.N;

        } else if (forwardBlockDiagonal2 == placedBlockOppositeFacing && !(forwardDownState.getBlock() instanceof VerticalBeamBlock) && (forwardBlockDiagonal2 == Direction.SOUTH || forwardBlockDiagonal2 == Direction.WEST)) {
            connect = BeamConnection.M;

            //--------------------------------------------------------------------------------------------------------------

        } else if (forwardBlockDiagonal == placedBlockOppositeFacing && !(forwardDownState.getBlock() instanceof VerticalBeamBlock) && (forwardBlockDiagonal == Direction.SOUTH || forwardBlockDiagonal == Direction.WEST)) {
            connect = BeamConnection.M;


        } else if ((backwardBlockDiagonal == Direction.NORTH || backwardBlockDiagonal == Direction.EAST)
                && ((backwardUpDiagonal == placedBlockOppositeFacing) || (backwardUpState.isAir() && forwardUpState.isAir()))) { //forwardUpDiagonal == placedBlockFacing ||
            connect = BeamConnection.N;


        } else {
            connect = BeamConnection.NONE;
        }

        return blockstate.setValue(CONNECT, connect);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return SimpleWaterloggedBlock.super.placeLiquid(pLevel, pPos, pState, pFluidState);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(pLevel, pPos, pState, pFluid);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND, AIR -> false;
            case WATER -> pLevel.getFluidState(pPos).is(FluidTags.WATER);
        };
    }
}