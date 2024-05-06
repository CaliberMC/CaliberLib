package com.calibermc.caliberlib.block.custom;

import com.calibermc.caliberlib.block.shapes.ArchShape;
import com.calibermc.caliberlib.block.shapes.trim.ArchTrim;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class ArchBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<ArchShape> TYPE = ModBlockStateProperties.ARCH_SHAPE;
    public static final EnumProperty<ArchTrim> TRIM = ModBlockStateProperties.ARCH_TRIM;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final VoxelShape SHAPE = Block.box(0, 8, 0, 16, 16, 16);

    public ArchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TRIM, ArchTrim.BOTH)
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE, ArchShape.STRAIGHT)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState blockState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TRIM, FACING, TYPE, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        FluidState fluidstate = blockPlaceContext.getLevel().getFluidState(blockpos);
        BlockState blockstate = this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection()) //.getOpposite()
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        return blockstate.setValue(TYPE, getArchShape(blockstate, blockPlaceContext.getLevel(), blockpos)).setValue(TRIM, getTrim(blockstate, blockPlaceContext.getLevel(), blockpos));
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @Override
    public BlockState updateShape(BlockState blockState, Direction facing, BlockState facingState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos facingPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        if (facing.getAxis().isHorizontal()) {
            // Set the TYPE value based on the ArchShape
            blockState = blockState.setValue(TYPE, getArchShape(blockState, levelAccessor, currentPos));

            // Set the TRIM value
            blockState = blockState.setValue(TRIM, getTrim(blockState, levelAccessor, currentPos));
        } else {
            blockState = super.updateShape(blockState, facing, facingState, levelAccessor, currentPos, facingPos);
        }

        return blockState;
    }

    private static ArchShape getArchShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        Direction facing = blockState.getValue(FACING);
        Direction opposite = facing.getOpposite();
        boolean front = isArch(blockGetter.getBlockState(blockPos.relative(facing)));
        boolean back = isArch(blockGetter.getBlockState(blockPos.relative(opposite)));
        boolean left = isArch(blockGetter.getBlockState(blockPos.relative(facing.getCounterClockWise())));
        boolean right = isArch(blockGetter.getBlockState(blockPos.relative(facing.getClockWise())));

        // Check for corner (left)
        if ((left && front && !right && !back) || (right && back && !left && !front)) {
            return ArchShape.CORNER_LEFT;
        }
        // Check for corner (right)
        if ((right && front && !left && !back) || (left && back && !right && !front)) {
            return ArchShape.CORNER_RIGHT;
        }
        // Check for 3-way intersection (forward)
        if (back && left && right && !front) {
            return ArchShape.T;
        }
        // Check for 3-way intersection (left)
        if (left && front && back && !right) {
            return ArchShape.LEFT_T;
        }
        // Check for 3-way intersection (right)
        if (right && front && back && !left) {
            return ArchShape.RIGHT_T;
        }
        // Check for 4-way intersection
        if (right && left && front && back) {
            return ArchShape.CROSS;
        }
        return ArchShape.STRAIGHT;
    }

    private ArchTrim getTrim(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        Direction facing = blockState.getValue(FACING);
        Direction opposite = facing.getOpposite();
        boolean airInFront = ModBlockStateProperties.isAir(blockGetter.getBlockState(blockPos.relative(facing)));
        boolean airInBack = ModBlockStateProperties.isAir(blockGetter.getBlockState(blockPos.relative(opposite)));

        // Check for blocks on both sides (front and back)
        if (!airInFront && !airInBack) {
            return ArchTrim.NONE;
        }
        // Check for no blocks on either side (front and back)
        if (airInFront && airInBack) {
            return ArchTrim.BOTH;
        }
        // Check for block only in back
        if (!airInFront && airInBack) {
            return ArchTrim.FRONT;
        }

        return ArchTrim.BOTH;
    }


    public static boolean isArch(BlockState blockState) {
        return blockState.getBlock() instanceof ArchBlock;
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