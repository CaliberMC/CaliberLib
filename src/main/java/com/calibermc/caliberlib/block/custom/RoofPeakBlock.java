package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.RoofPeakShape;
import com.calibermc.caliberlib.block.shapes.RoofShape;
import com.calibermc.caliberlib.block.properties.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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


public class RoofPeakBlock extends Block implements SimpleWaterloggedBlock {

    //    public static final EnumProperty<RoofPitch> PITCH = ModBlockStateProperties.ROOF_PITCH;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<RoofPeakShape> TYPE = ModBlockStateProperties.ROOF_PEAK_SHAPE;

    protected static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 8, 16);
    protected static final VoxelShape TOP_SHAPE = Block.box(0, .01, 0, 16, 16, 16);

    public RoofPeakBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
//                .setValue(PITCH, RoofPitch.PITCH_45)
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, RoofPeakShape.STRAIGHT)
                .setValue(HALF, Half.BOTTOM)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, HALF, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext pContext) {
        Half Half = blockState.getValue(HALF);
        switch (Half) {
            case TOP -> {
                return TOP_SHAPE;
            }
            default -> {
                return BOTTOM_SHAPE;
            }
        }
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        BlockState blockstate = level.getBlockState(blockpos);

        if (blockstate.is(this)) {
            return blockstate.setValue(HALF, Half.TOP).setValue(WATERLOGGED, Boolean.FALSE);
        } else {
            FluidState fluidstate = level.getFluidState(blockpos);
            Direction facing = blockPlaceContext.getHorizontalDirection().getOpposite();
            Half half = (blockPlaceContext.getClickedFace() != Direction.DOWN && (blockPlaceContext.getClickedFace() == Direction.UP ||
                    !(blockPlaceContext.getClickLocation().y - (double) blockpos.getY() > 0.5D))) ? Half.BOTTOM : Half.TOP;

            BlockState blockstate1 = this.defaultBlockState()
                    .setValue(FACING, facing)
                    .setValue(HALF, half)
                    .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

            // Determine RoofShape based on surrounding blocks
            RoofPeakShape roofShape = getRoofShape(blockstate1, level, blockpos);
            blockstate1 = blockstate1.setValue(TYPE, roofShape);

            return blockstate1;
        }
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext pUseContext) {
        ItemStack itemstack = pUseContext.getItemInHand();
        Half Half = blockState.getValue(HALF);
        if (Half != net.minecraft.world.level.block.state.properties.Half.TOP && itemstack.is(this.asItem())) {
            if (pUseContext.replacingClickedOnBlock()) {
                boolean flag = pUseContext.getClickLocation().y - (double) pUseContext.getClickedPos().getY() > 0.5D;
                Direction direction = pUseContext.getClickedFace();
                if (Half == net.minecraft.world.level.block.state.properties.Half.BOTTOM) {
                    return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    public BlockState updateShape(BlockState blockState, Direction pFacing, BlockState pFacingState, LevelAccessor levelAccessor, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        if (pFacing.getAxis().isHorizontal()) {
            // Set the TYPE value based on the BlockShape
            blockState = blockState.setValue(TYPE, getRoofShape(blockState, levelAccessor, pCurrentPos));

            // Set the PITCH value based on the BlockShape
//            blockState = blockState.setValue(PITCH, getRoofPitch(blockState, levelAccessor, pCurrentPos));

        } else {
            blockState = super.updateShape(blockState, pFacing, pFacingState, levelAccessor, pCurrentPos, pFacingPos);
        }

        return blockState;
    }


    private static RoofPeakShape getRoofShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        Direction facing = blockState.getValue(FACING);
        Direction opposite = facing.getOpposite();
        boolean front = isRoof(blockGetter.getBlockState(blockPos.relative(facing)));
        boolean back = isRoof(blockGetter.getBlockState(blockPos.relative(opposite)));
        boolean left = isRoof(blockGetter.getBlockState(blockPos.relative(facing.getCounterClockWise())));
        boolean right = isRoof(blockGetter.getBlockState(blockPos.relative(facing.getClockWise())));

        boolean downFrontLeft = isHip(blockGetter.getBlockState(blockPos.below().relative(facing).relative(facing.getCounterClockWise())));
        boolean downFrontRight = isHip(blockGetter.getBlockState(blockPos.below().relative(facing).relative(facing.getClockWise())));
        boolean downBackLeft = isHip(blockGetter.getBlockState(blockPos.below().relative(opposite).relative(opposite.getCounterClockWise())));
        boolean downBackRight = isHip(blockGetter.getBlockState(blockPos.below().relative(opposite).relative(opposite.getClockWise())));

        // Check for peak cap
        if (!front && !back && !left && !right) {
            return RoofPeakShape.CAP;
        }
        // Check for corner (left)
        if ((left && front && !right && !back) || (right && back && !left && !front)) {
            return RoofPeakShape.CORNER_LEFT;
        }
        // Check for corner (right)
        if ((right && front && !left && !back) || (left && back && !right && !front)) {
            return RoofPeakShape.CORNER_RIGHT;
        }
        // Check for 3-way intersection (forward)
        if (back && left && right && !front) {
            return RoofPeakShape.FACING_T;
        }
        // Check for 3-way intersection (backward)
        if (front && left && right && !back) {
            return RoofPeakShape.OPPOSITE_T;
        }
        // Check for 3-way intersection (left)
        if (left && front && back && !right) {
            return RoofPeakShape.LEFT_T;
        }
        // Check for 3-way intersection (right)
        if (right && front && back && !left) {
            return RoofPeakShape.RIGHT_T;
        }
        // Check for 4-way intersection
        if (right && left && front && back) {
            return RoofPeakShape.CROSS;
        }
        // Check for roof end hip
        if ((downFrontRight || downFrontLeft || downBackRight || downBackLeft)) {
            if (front && !back && !left && !right) {
                return RoofPeakShape.END_FACING;
            }
            if (!front && back && !left && !right) {
                return RoofPeakShape.END_OPPOSITE;
            }
        }
        return RoofPeakShape.STRAIGHT;
    }

    // TODO: Implement getRoofPitch

//    private static RoofPitch getRoofPitch(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
//        Direction facing = blockState.getValue(FACING);
//        Direction opposite = facing.getOpposite();
//        blockPos = blockPos.below();
//        BlockState frontBlock = levelAccessor.getBlockState(blockPos.relative(facing));
//
//        if(isRoof(frontBlock)) {
//            return frontBlock.getValue(PITCH);
//        }
//
//        BlockState backBlock = levelAccessor.getBlockState(blockPos.relative(opposite));
//        if(isRoof(backBlock)) {
//            return backBlock.getValue(PITCH);
//        }
//
//        BlockState leftBlock = levelAccessor.getBlockState(blockPos.relative(facing.getCounterClockWise()));
//        if(isRoof(leftBlock)) {
//            return leftBlock.getValue(PITCH);
//        }
//
//        BlockState rightBlock = levelAccessor.getBlockState(blockPos.relative(facing.getClockWise()));
//        if(isRoof(rightBlock)) {
//            return rightBlock.getValue(PITCH);
//        }
//
//        return RoofPitch.PITCH_45;
//
//    }

    public static boolean isRoof(BlockState blockState) {
        return blockState.getBlock() instanceof RoofPeakBlock;
    }

    public static boolean isHip(BlockState blockState) {
        return blockState.getBlock() instanceof Roof45Block && (blockState.getValue(Roof45Block.TYPE) == RoofShape.OUTER_LEFT || blockState.getValue(Roof45Block.TYPE) == RoofShape.OUTER_RIGHT);
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