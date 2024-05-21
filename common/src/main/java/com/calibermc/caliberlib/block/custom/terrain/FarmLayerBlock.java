package com.calibermc.caliberlib.block.custom.terrain;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.Supplier;

public class FarmLayerBlock extends Block {

    public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    public static final int MAX_MOISTURE = 7;
    public static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.1D, 0.0D, 16.0D, 16.0D, 16.0D)};
    public final int layerCount = 8;
    private final Supplier<BlockState> cannotSurvive;

    public FarmLayerBlock(Properties properties, Supplier<BlockState> cannotSurvive) {
        super(properties);
        this.cannotSurvive = cannotSurvive;
        this.registerDefaultState(this.stateDefinition.any() // ? this.defaultBlockState()
                .setValue(LAYERS, 1)
                .setValue(MOISTURE, Integer.valueOf(0)));
    }

    private static boolean isUnderCrops(BlockGetter pLevel, BlockPos pPos) {
        BlockState plant = pLevel.getBlockState(pPos.above());
        BlockState state = pLevel.getBlockState(pPos);
        return canSustainPlant(state, pLevel, pPos, Direction.UP, plant);
    }

    private static boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, BlockState plant) {
        if (plant.getBlock() == Blocks.CACTUS) {
            return state.is(Blocks.CACTUS) || state.is(BlockTags.SAND);
        } else if (plant.getBlock() == Blocks.SUGAR_CANE && plant.getBlock() == Blocks.SUGAR_CANE) {
            return true;
        } else if (plant.getBlock() instanceof BushBlock && ((BushBlock)plant.getBlock()).mayPlaceOn(state, world, pos)) {
            return true;
        } else if (plant.getBlock() == Blocks.DEAD_BUSH) {
            return state.is(BlockTags.SAND) || state.getBlock() == Blocks.TERRACOTTA;
        } else if (plant.getBlock() == Blocks.NETHER_WART) {
            return state.getBlock() == Blocks.SOUL_SAND;
        } else if (plant.getBlock() instanceof CropBlock || plant.getBlock() == Blocks.PITCHER_CROP) {
            return state.is(Blocks.FARMLAND);
        } else if (plant.getBlock() == Blocks.RED_MUSHROOM || plant.getBlock() == Blocks.BROWN_MUSHROOM) {
            return state.isFaceSturdy(world, pos, Direction.UP);
        } else if (plant.getBlock() instanceof SaplingBlock || plant.getBlock() instanceof FlowerBlock || plant.getBlock() == Blocks.TALL_GRASS) {
            return state.is(BlockTags.DIRT) || state.getBlock() == Blocks.FARMLAND;
        } else if (plant.getBlock() == Blocks.LILY_PAD) {
            return (state.is(Blocks.WATER) || state.getBlock() instanceof IceBlock) && world.getFluidState(pos.relative(facing)).isEmpty();
        } else if (plant.getBlock() != Blocks.DEAD_BUSH) {
            return false;
        } else {
            boolean isBeach = state.is(BlockTags.DIRT) || state.is(BlockTags.SAND);
            boolean hasWater = false;

            for (Direction face : Direction.Plane.HORIZONTAL) {
                BlockState adjacentBlockState = world.getBlockState(pos.relative(face));
                FluidState adjacentFluidState = world.getFluidState(pos.relative(face));
                hasWater = adjacentBlockState.is(Blocks.FROSTED_ICE) || adjacentFluidState.is(FluidTags.WATER);
                if (hasWater) {
                    break;
                }
            }

            return isBeach && hasWater;
        }
    }


    private static boolean isNearWater(LevelReader pLevel, BlockPos pPos) {
        for (BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
            if (pLevel.getFluidState(blockpos).is(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LAYERS, MOISTURE);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS) - 1];
    }

    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_LAYER[pState.getValue(LAYERS)];
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos.above());
        return !blockstate.isSolid() || blockstate.getBlock() instanceof FenceGateBlock || blockstate.getBlock() instanceof MovingPistonBlock;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = pContext.getLevel().getBlockState(blockpos);
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            int newCount = Math.min(layerCount, i + 1);
            return blockstate.setValue(LAYERS, newCount);
        } else {
            return !this.defaultBlockState().canSurvive(pContext.getLevel(), pContext.getClickedPos()) ? this.cannotSurvive.get().setValue(LAYERS, layerCount) : super.getStateForPlacement(pContext);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext itemContext) {
        int i = state.getValue(LAYERS);
        if (itemContext.getItemInHand().getItem() == this.asItem() && i < layerCount) {
            if (itemContext.replacingClickedOnBlock()) {
                return itemContext.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRand) {
        if (!pState.canSurvive(pLevel, pPos)) {
            turnToDirt(pState, pLevel, pPos);
        }

    }

    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int i = pState.getValue(MOISTURE);
        if (!isNearWater(pLevel, pPos) && !pLevel.isRainingAt(pPos.above())) {
            if (i > 0) {
                pLevel.setBlock(pPos, pState.setValue(MOISTURE, Integer.valueOf(i - 1)), 2);
            } else if (!isUnderCrops(pLevel, pPos)) {
                turnToDirt(pState, pLevel, pPos);
            }
        } else if (i < 7) {
            pLevel.setBlock(pPos, pState.setValue(MOISTURE, Integer.valueOf(7)), 2);
        }

    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide && level.random.nextFloat() < fallDistance - 0.5F && entity instanceof LivingEntity && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F) {
            turnToDirt(state, level, pos);
        }

        super.fallOn(level, state, pos, entity, fallDistance);
    }

    public void turnToDirt(BlockState pState, Level pLevel, BlockPos pPos) {
        pLevel.setBlockAndUpdate(pPos, pushEntitiesUp(pState, Blocks.DIRT.defaultBlockState().setValue(LAYERS, layerCount), pLevel, pPos));
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND -> pState.getValue(LAYERS) < 5;
            case WATER -> pLevel.getFluidState(pPos).is(FluidTags.WATER);
            case AIR -> false;
        };
    }
}