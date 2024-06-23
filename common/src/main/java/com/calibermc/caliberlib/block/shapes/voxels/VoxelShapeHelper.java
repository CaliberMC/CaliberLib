package com.calibermc.caliberlib.block.shapes.voxels;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.stream.Stream;

import static net.minecraft.core.Direction.*;

public class VoxelShapeHelper {

    public static class ArchBlockShapes {
        
        public static final VoxelShape ARCH_SHAPE = Block.box(0, 10, 0, 16, 16, 16);
    }
    
    public static class ArrowslitShapes {
        
        public static final Map<Direction, VoxelShape> ARROWSLIT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Stream.of(
                        Block.box(0, 0, 0, 7, 16, 1.5),
                        Block.box(9, 0, 0, 16, 16, 1.5),
                        Block.box(13.5, 0, 1.5, 16, 16, 8),
                        Block.box(0, 0, 1.5, 2.5, 16, 8),
                        Block.box(11.5, 0, 1.5, 13.5, 16, 3.5),
                        Block.box(2.5, 0, 1.5, 4.5, 16, 3.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(14.5, 0, 0, 16, 16, 7),
                        Block.box(14.5, 0, 9, 16, 16, 16),
                        Block.box(8, 0, 13.5, 14.5, 16, 16),
                        Block.box(8, 0, 0, 14.5, 16, 2.5),
                        Block.box(12.5, 0, 11.5, 14.5, 16, 13.5),
                        Block.box(12.5, 0, 2.5, 14.5, 16, 4.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                SOUTH, Stream.of(
                        Block.box(9, 0, 14.5, 16, 16, 16),
                        Block.box(0, 0, 14.5, 7, 16, 16),
                        Block.box(0, 0, 8, 2.5, 16, 14.5),
                        Block.box(13.5, 0, 8, 16, 16, 14.5),
                        Block.box(2.5, 0, 12.5, 4.5, 16, 14.5),
                        Block.box(11.5, 0, 12.5, 13.5, 16, 14.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(0, 0, 9, 1.5, 16, 16),
                        Block.box(0, 0, 0, 1.5, 16, 7),
                        Block.box(1.5, 0, 0, 8, 16, 2.5),
                        Block.box(1.5, 0, 13.5, 8, 16, 16),
                        Block.box(1.5, 0, 2.5, 3.5, 16, 4.5),
                        Block.box(1.5, 0, 11.5, 3.5, 16, 13.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));
    }
    
    public static class BalustradeBlockShapes {
        
        public static final VoxelShape SINGLE_SHAPE = Stream.of(
                Block.box(2, 14, 2, 14, 16, 14),
                Block.box(2.5, 13, 2.5, 13.5, 14, 13.5),
                Block.box(3, 3, 3, 13, 4, 13),
                Block.box(3.5, 4, 3.5, 12.5, 5, 12.5),
                Block.box(4, 5, 4, 12, 11, 12),
                Block.box(3.5, 11, 3.5, 12.5, 12, 12.5),
                Block.box(3, 12, 3, 13, 13, 13),
                Block.box(2.5, 2, 2.5, 13.5, 3, 13.5),
                Block.box(2, 0, 2, 14, 2, 14)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        public static final VoxelShape CROSS_SHAPE = Stream.of(
                Block.box(0, 0, 3.5, 16, 2, 12.5),
                Block.box(0, 2, 4, 16, 3, 12),
                Block.box(13, 3, 5, 16, 4, 11),
                Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                Block.box(14, 5, 6, 16, 11, 10),
                Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                Block.box(13, 12, 5, 16, 13, 11),
                Block.box(5, 3, 5, 11, 4, 11),
                Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                Block.box(6, 5, 6, 10, 11, 10),
                Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                Block.box(5, 12, 5, 11, 13, 11),
                Block.box(0, 3, 5, 3, 4, 11),
                Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                Block.box(0, 5, 6, 2, 11, 10),
                Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                Block.box(0, 12, 5, 3, 13, 11),
                Block.box(0, 13, 4, 16, 14, 12),
                Block.box(0, 14, 3.5, 16, 16, 12.5),
                Block.box(3.5, 0, 12.5, 12.5, 2, 16),
                Block.box(4, 2, 12, 12, 3, 16),
                Block.box(5, 3, 13, 11, 4, 16),
                Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                Block.box(6, 5, 14, 10, 11, 16),
                Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                Block.box(5, 12, 13, 11, 13, 16),
                Block.box(4, 13, 12, 12, 14, 16),
                Block.box(3.5, 14, 12.5, 12.5, 16, 16),
                Block.box(3.5, 0, 0, 12.5, 2, 3.5),
                Block.box(4, 2, 0, 12, 3, 4),
                Block.box(5, 3, 0, 11, 4, 3),
                Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                Block.box(6, 5, 0, 10, 11, 2),
                Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                Block.box(5, 12, 0, 11, 13, 3),
                Block.box(4, 13, 0, 12, 14, 4),
                Block.box(3.5, 14, 0, 12.5, 16, 3.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

        public static final Map<Direction, VoxelShape> CONNECTED_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Stream.of(
                        Block.box(3.5, 0, 0, 12.5, 2, 16),
                        Block.box(4, 2, 0, 12, 3, 16),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(4, 13, 0, 12, 14, 16),
                        Block.box(3.5, 14, 0, 12.5, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                SOUTH, Stream.of(
                        Block.box(3.5, 0, 0, 12.5, 2, 16),
                        Block.box(4, 2, 0, 12, 3, 16),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(4, 13, 0, 12, 14, 16),
                        Block.box(3.5, 14, 0, 12.5, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(0, 0, 3.5, 16, 2, 12.5),
                        Block.box(0, 2, 4, 16, 3, 12),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(0, 13, 4, 16, 14, 12),
                        Block.box(0, 14, 3.5, 16, 16, 12.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(0, 0, 3.5, 16, 2, 12.5),
                        Block.box(0, 2, 4, 16, 3, 12),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(0, 13, 4, 16, 14, 12),
                        Block.box(0, 14, 3.5, 16, 16, 12.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

        public static final Map<Direction, VoxelShape> CORNER_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Stream.of(
                        Block.box(3.5, 0, 3.5, 12.5, 2, 16),
                        Block.box(4, 2, 4, 12, 3, 16),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(4, 13, 4, 12, 14, 16),
                        Block.box(3.5, 14, 3.5, 12.5, 16, 16),
                        Block.box(0, 0, 3.5, 3.5, 2, 12.5),
                        Block.box(0, 2, 4, 4, 3, 12),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(0, 14, 3.5, 3.5, 16, 12.5),
                        Block.box(0, 13, 4, 4, 14, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                SOUTH, Stream.of(
                        Block.box(3.5, 0, 0, 12.5, 2, 12.5),
                        Block.box(4, 2, 0, 12, 3, 12),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(4, 13, 0, 12, 14, 12),
                        Block.box(3.5, 14, 0, 12.5, 16, 12.5),
                        Block.box(12.5, 0, 3.5, 16, 2, 12.5),
                        Block.box(12, 2, 4, 16, 3, 12),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(12.5, 14, 3.5, 16, 16, 12.5),
                        Block.box(12, 13, 4, 16, 14, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(0, 0, 3.5, 12.5, 2, 12.5),
                        Block.box(0, 2, 4, 12, 3, 12),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(0, 13, 4, 12, 14, 12),
                        Block.box(0, 14, 3.5, 12.5, 16, 12.5),
                        Block.box(3.5, 0, 0, 12.5, 2, 3.5),
                        Block.box(4, 2, 0, 12, 3, 4),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(3.5, 14, 0, 12.5, 16, 3.5),
                        Block.box(4, 13, 0, 12, 14, 4)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(3.5, 0, 3.5, 16, 2, 12.5),
                        Block.box(4, 2, 4, 16, 3, 12),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(4, 13, 4, 16, 14, 12),
                        Block.box(3.5, 14, 3.5, 16, 16, 12.5),
                        Block.box(3.5, 0, 12.5, 12.5, 2, 16),
                        Block.box(4, 2, 12, 12, 3, 16),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(3.5, 14, 12.5, 12.5, 16, 16),
                        Block.box(4, 13, 12, 12, 14, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

        public static final Map<Direction, VoxelShape> END_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Stream.of(
                        Block.box(3.5, 0, 12, 12.5, 2, 16),
                        Block.box(4, 2, 11.5, 12, 3, 16),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(4, 13, 11.5, 12, 14, 16),
                        Block.box(3.5, 14, 12, 12.5, 16, 16),
                        Block.box(2, 0, 0, 14, 2, 12),
                        Block.box(2.5, 2, 0.5, 13.5, 3, 11.5),
                        Block.box(3, 3, 1, 13, 4, 11),
                        Block.box(3.5, 4, 1.5, 12.5, 5, 10.5),
                        Block.box(4, 5, 2, 12, 11, 10),
                        Block.box(3.5, 11, 1.5, 12.5, 12, 10.5),
                        Block.box(3, 12, 1, 13, 13, 11),
                        Block.box(2.5, 13, 0.5, 13.5, 14, 11.5),
                        Block.box(2, 14, 0, 14, 16, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                SOUTH, Stream.of(
                        Block.box(3.5, 0, 0, 12.5, 2, 4),
                        Block.box(4, 2, 0, 12, 3, 4.5),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(4, 13, 0, 12, 14, 4.5),
                        Block.box(3.5, 14, 0, 12.5, 16, 4),
                        Block.box(2, 0, 4, 14, 2, 16),
                        Block.box(2.5, 2, 4.5, 13.5, 3, 15.5),
                        Block.box(3, 3, 5, 13, 4, 15),
                        Block.box(3.5, 4, 5.5, 12.5, 5, 14.5),
                        Block.box(4, 5, 6, 12, 11, 14),
                        Block.box(3.5, 11, 5.5, 12.5, 12, 14.5),
                        Block.box(3, 12, 5, 13, 13, 15),
                        Block.box(2.5, 13, 4.5, 13.5, 14, 15.5),
                        Block.box(2, 14, 4, 14, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(0, 0, 3.5, 4, 2, 12.5),
                        Block.box(0, 2, 4, 4.5, 3, 12),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(0, 13, 4, 4.5, 14, 12),
                        Block.box(0, 14, 3.5, 4, 16, 12.5),
                        Block.box(4, 0, 2, 16, 2, 14),
                        Block.box(4.5, 2, 2.5, 15.5, 3, 13.5),
                        Block.box(5, 3, 3, 15, 4, 13),
                        Block.box(5.5, 4, 3.5, 14.5, 5, 12.5),
                        Block.box(6, 5, 4, 14, 11, 12),
                        Block.box(5.5, 11, 3.5, 14.5, 12, 12.5),
                        Block.box(5, 12, 3, 15, 13, 13),
                        Block.box(4.5, 13, 2.5, 15.5, 14, 13.5),
                        Block.box(4, 14, 2, 16, 16, 14)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(12, 0, 3.5, 16, 2, 12.5),
                        Block.box(11.5, 2, 4, 16, 3, 12),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(11.5, 13, 4, 16, 14, 12),
                        Block.box(12, 14, 3.5, 16, 16, 12.5),
                        Block.box(0, 0, 2, 12, 2, 14),
                        Block.box(0.5, 2, 2.5, 11.5, 3, 13.5),
                        Block.box(1, 3, 3, 11, 4, 13),
                        Block.box(1.5, 4, 3.5, 10.5, 5, 12.5),
                        Block.box(2, 5, 4, 10, 11, 12),
                        Block.box(1.5, 11, 3.5, 10.5, 12, 12.5),
                        Block.box(1, 12, 3, 11, 13, 13),
                        Block.box(0.5, 13, 2.5, 11.5, 14, 13.5),
                        Block.box(0, 14, 2, 12, 16, 14)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

        public static final Map<Direction, VoxelShape> T_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Stream.of(
                        Block.box(0, 0, 3.5, 16, 2, 12.5),
                        Block.box(0, 2, 4, 16, 3, 12),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(0, 13, 4, 16, 14, 12),
                        Block.box(0, 14, 3.5, 16, 16, 12.5),
                        Block.box(3.5, 0, 12.5, 12.5, 2, 16),
                        Block.box(4, 2, 12, 12, 3, 16),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(4, 13, 12, 12, 14, 16),
                        Block.box(3.5, 14, 12.5, 12.5, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                SOUTH, Stream.of(
                        Block.box(0, 0, 3.5, 16, 2, 12.5),
                        Block.box(0, 2, 4, 16, 3, 12),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(0, 13, 4, 16, 14, 12),
                        Block.box(0, 14, 3.5, 16, 16, 12.5),
                        Block.box(3.5, 0, 0, 12.5, 2, 3.5),
                        Block.box(4, 2, 0, 12, 3, 4),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(4, 13, 0, 12, 14, 4),
                        Block.box(3.5, 14, 0, 12.5, 16, 3.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(3.5, 0, 0, 12.5, 2, 16),
                        Block.box(4, 2, 0, 12, 3, 16),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(4, 13, 0, 12, 14, 16),
                        Block.box(3.5, 14, 0, 12.5, 16, 16),
                        Block.box(0, 0, 3.5, 3.5, 2, 12.5),
                        Block.box(0, 2, 4, 4, 3, 12),
                        Block.box(0, 3, 5, 3, 4, 11),
                        Block.box(0, 4, 5.5, 2.5, 5, 10.5),
                        Block.box(0, 5, 6, 2, 11, 10),
                        Block.box(0, 11, 5.5, 2.5, 12, 10.5),
                        Block.box(0, 12, 5, 3, 13, 11),
                        Block.box(0, 13, 4, 4, 14, 12),
                        Block.box(0, 14, 3.5, 3.5, 16, 12.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(3.5, 0, 0, 12.5, 2, 16),
                        Block.box(4, 2, 0, 12, 3, 16),
                        Block.box(5, 3, 0, 11, 4, 3),
                        Block.box(5.5, 4, 0, 10.5, 5, 2.5),
                        Block.box(6, 5, 0, 10, 11, 2),
                        Block.box(5.5, 11, 0, 10.5, 12, 2.5),
                        Block.box(5, 12, 0, 11, 13, 3),
                        Block.box(5, 3, 5, 11, 4, 11),
                        Block.box(5.5, 4, 5.5, 10.5, 5, 10.5),
                        Block.box(6, 5, 6, 10, 11, 10),
                        Block.box(5.5, 11, 5.5, 10.5, 12, 10.5),
                        Block.box(5, 12, 5, 11, 13, 11),
                        Block.box(5, 3, 13, 11, 4, 16),
                        Block.box(5.5, 4, 13.5, 10.5, 5, 16),
                        Block.box(6, 5, 14, 10, 11, 16),
                        Block.box(5.5, 11, 13.5, 10.5, 12, 16),
                        Block.box(5, 12, 13, 11, 13, 16),
                        Block.box(4, 13, 0, 12, 14, 16),
                        Block.box(3.5, 14, 0, 12.5, 16, 16),
                        Block.box(12.5, 0, 3.5, 16, 2, 12.5),
                        Block.box(12, 2, 4, 16, 3, 12),
                        Block.box(13, 3, 5, 16, 4, 11),
                        Block.box(13.5, 4, 5.5, 16, 5, 10.5),
                        Block.box(14, 5, 6, 16, 11, 10),
                        Block.box(13.5, 11, 5.5, 16, 12, 10.5),
                        Block.box(13, 12, 5, 16, 13, 11),
                        Block.box(12, 13, 4, 16, 14, 12),
                        Block.box(12.5, 14, 3.5, 16, 16, 12.5)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));
    }
    
    public static class BeamLintelBlockShapes {

        public static final VoxelShape[] BOTTOM_SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(-5, 12, 14, 21, 16, 16),
                Block.box(0, 12, 14, 21, 16, 16),
                Block.box(-5, 12, 14, 16, 16, 16),
                Block.box(0, 12, 14, 16, 16, 16)};
        public static final VoxelShape[] BOTTOM_SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 12, -5, 2, 16, 21),
                Block.box(0, 12, 0, 2, 16, 21),
                Block.box(0, 12, -5, 2, 16, 16),
                Block.box(0, 12, 0, 2, 16, 16)};
        public static final VoxelShape[] BOTTOM_SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(-5, 12, 0, 21, 16, 2),
                Block.box(-5, 12, 0, 16, 16, 2),
                Block.box(0, 12, 0, 21, 16, 2),
                Block.box(0, 12, 0, 16, 16, 2)};
        public static final VoxelShape[] BOTTOM_SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 12, -5, 16, 16, 21),
                Block.box(14, 12, -5, 16, 16, 16),
                Block.box(14, 12, 0, 16, 16, 21),
                Block.box(14, 12, 0, 16, 16, 16)};
        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(-5, 0, 14, 21, 4, 16),
                Block.box(-5, 0, 14, 16, 4, 16),
                Block.box(0, 0, 14, 21, 4, 16),
                Block.box(0, 0, 14, 16, 4, 16)};
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, -5, 2, 4, 21),
                Block.box(0, 0, -5, 2, 4, 16),
                Block.box(0, 0, 0, 2, 4, 21),
                Block.box(0, 0, 0, 2, 4, 16)};
        public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(-5, 0, 0, 21, 4, 2),
                Block.box(0, 0, 0, 21, 4, 2),
                Block.box(-5, 0, 0, 16, 4, 2),
                Block.box(0, 0, 0, 16, 4, 2)};
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 0, -5, 16, 4, 21),
                Block.box(14, 0, 0, 16, 4, 21),
                Block.box(14, 0, -5, 16, 4, 16),
                Block.box(14, 0, 0, 16, 4, 16)};
    }
    
    public static class BeamPostBlockShapes {

        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(16, 0, 14, 20, 16, 16), Block.box(-4, 0, 14, 0, 16, 16), BooleanOp.OR),
                Block.box(-4, 0, 14, 0, 16, 16), Block.box(16, 0, 14, 20, 16, 16)};
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 16, 2, 16, 20), Block.box(0, 0, -4, 2, 16, 0), BooleanOp.OR),
                Block.box(0, 0, -4, 2, 16, 0), Block.box(0, 0, 16, 2, 16, 20)};
        public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(-4, 0, 0, 0, 16, 2), Block.box(16, 0, 0, 20, 16, 2), BooleanOp.OR),
                Block.box(16, 0, 0, 20, 16, 2), Block.box(-4, 0, 0, 0, 16, 2)};
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, -4, 16, 16, 0), Block.box(14, 0, 16, 16, 16, 20), BooleanOp.OR),
                Block.box(14, 0, 16, 16, 16, 20), Block.box(14, 0, -4, 16, 16, 0)};
    }
    
    public static class CapitalBlockShapes {

        private static final VoxelShape BASE_SHAPE_TOP = Block.box(0, 8, 0, 16, 16, 16);
        private static final VoxelShape CENTER_SHAPE_TOP = Block.box(4, 0, 4, 12, 8, 12);
        private static final VoxelShape X_SHAPE_TOP = Block.box(4, 0, 0, 12, 8, 16);
        private static final VoxelShape Y_SHAPE_TOP = Block.box(0, 0, 4, 16, 8, 12);

        private static final VoxelShape BASE_SHAPE_BOTTOM = Block.box(0, 0, 0, 16, 8, 16);
        private static final VoxelShape CENTER_SHAPE_BOTTOM = Block.box(4, 8, 4, 12, 16, 12);
        private static final VoxelShape X_SHAPE_BOTTOM = Block.box(4, 8, 0, 12, 16, 16);
        private static final VoxelShape Y_SHAPE_BOTTOM = Block.box(0, 8, 4, 16, 16, 12);

        public static final Map<Direction, VoxelShape> TOP_CONNECTED_SHAPE = createConnectedShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_CONNECTED_SHAPE = createConnectedShapes(false);
        public static final Map<Direction, VoxelShape> TOP_END_SHAPE = createEndShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_END_SHAPE = createEndShapes(false);
        public static final Map<Direction, VoxelShape> TOP_CORNER_SHAPE = createCornerShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_CORNER_SHAPE = createCornerShapes(false);
        public static final Map<Direction, VoxelShape> TOP_T_SHAPE = createTShapes(true);
        public static final Map<Direction, VoxelShape> BOTTOM_T_SHAPE = createTShapes(false);
        public static final VoxelShape TOP_SINGLE_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, CENTER_SHAPE_TOP);
        public static final VoxelShape BOTTOM_SINGLE_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, CENTER_SHAPE_BOTTOM);
        public static final VoxelShape TOP_CROSS_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_TOP, X_SHAPE_TOP, Block.box(12, 0, 4, 16, 8, 12), Block.box(0, 0, 4, 4, 8, 12));
        public static final VoxelShape BOTTOM_CROSS_SHAPE = combineShapes(BooleanOp.OR, BASE_SHAPE_BOTTOM, X_SHAPE_BOTTOM, Block.box(12, 8, 4, 16, 16, 12), Block.box(0, 8, 4, 4, 16, 12));

        private static VoxelShape combineShapes(BooleanOp op, VoxelShape... shapes) {
            return Stream.of(shapes).reduce((v1, v2) -> Shapes.join(v1, v2, op)).orElse(Shapes.empty());
        }

        private static Map<Direction, VoxelShape> createConnectedShapes(boolean top) {
            VoxelShape base = top ? BASE_SHAPE_TOP : BASE_SHAPE_BOTTOM;
            VoxelShape xShape = top ? X_SHAPE_TOP : X_SHAPE_BOTTOM;
            VoxelShape yShape = top ? Y_SHAPE_TOP : Y_SHAPE_BOTTOM;

            return Maps.newEnumMap(ImmutableMap.of(
                    Direction.NORTH, combineShapes(BooleanOp.OR, base, xShape),
                    Direction.SOUTH, combineShapes(BooleanOp.OR, base, xShape),
                    Direction.EAST, combineShapes(BooleanOp.OR, base, yShape),
                    Direction.WEST, combineShapes(BooleanOp.OR, base, yShape)
            ));
        }

        private static Map<Direction, VoxelShape> createEndShapes(boolean top) {
            VoxelShape base = top ? BASE_SHAPE_TOP : BASE_SHAPE_BOTTOM;
            int offset = top ? 0 : 8;

            return Maps.newEnumMap(ImmutableMap.of(
                    Direction.NORTH, combineShapes(BooleanOp.OR, base, Block.box(4, offset, 4, 12, offset + 8, 16)),
                    Direction.SOUTH, combineShapes(BooleanOp.OR, base, Block.box(4, offset, 0, 12, offset + 8, 12)),
                    Direction.EAST, combineShapes(BooleanOp.OR, base, Block.box(0, offset, 4, 12, offset + 8, 12)),
                    Direction.WEST, combineShapes(BooleanOp.OR, base, Block.box(4, offset, 4, 16, offset + 8, 12))
            ));
        }

        private static Map<Direction, VoxelShape> createCornerShapes(boolean top) {
            VoxelShape base = top ? BASE_SHAPE_TOP : BASE_SHAPE_BOTTOM;
            int offset = top ? 0 : 8;

            return Maps.newEnumMap(ImmutableMap.of(
                    Direction.NORTH, combineShapes(BooleanOp.OR, base, Block.box(4, offset, 4, 12, offset + 8, 16), Block.box(0, offset, 4, 4, offset + 8, 12)),
                    Direction.SOUTH, combineShapes(BooleanOp.OR, base, Block.box(4, offset, 0, 12, offset + 8, 12), Block.box(12, offset, 4, 16, offset + 8, 12)),
                    Direction.EAST, combineShapes(BooleanOp.OR, base, Block.box(0, offset, 4, 12, offset + 8, 12), Block.box(4, offset, 0, 12, offset + 8, 4)),
                    Direction.WEST, combineShapes(BooleanOp.OR, base, Block.box(4, offset, 4, 16, offset + 8, 12), Block.box(4, offset, 12, 12, offset + 8, 16))
            ));
        }

        private static Map<Direction, VoxelShape> createTShapes(boolean top) {
            VoxelShape base = top ? BASE_SHAPE_TOP : BASE_SHAPE_BOTTOM;
            int offset = top ? 0 : 8;

            return Maps.newEnumMap(ImmutableMap.of(
                    Direction.NORTH, combineShapes(BooleanOp.OR, Block.box(4, offset, 12, 12, offset + 8, 16), base, Block.box(0, offset, 4, 16, offset + 8, 12)),
                    Direction.SOUTH, combineShapes(BooleanOp.OR, Block.box(4, offset, 0, 12, offset + 8, 4), base, Block.box(0, offset, 4, 16, offset + 8, 12)),
                    Direction.EAST, combineShapes(BooleanOp.OR, Block.box(0, offset, 4, 4, offset + 8, 12), base, Block.box(4, offset, 0, 12, offset + 8, 16)),
                    Direction.WEST, combineShapes(BooleanOp.OR, Block.box(12, offset, 4, 16, offset + 8, 12), base, Block.box(4, offset, 0, 12, offset + 8, 16))
            ));
        }
    }

    public static class CornerBlockShapes {

        public static final Map<Direction, VoxelShape> LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(8, 0, 0, 16, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(0, 0, 8, 8, 16, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 8, 16, 16, 16), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 8, 16, 8), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(0, 0, 0, 8, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(8, 0, 8, 16, 16, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 0, 16, 16, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 8, 8, 16, 16), BooleanOp.OR)));
    }

    public static class CornerLayerBlockShapes {

        public static final VoxelShape[] LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 0, 16, 16, 16), Block.box(0, 0, 14, 14, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 0, 16, 16, 16), Block.box(0, 0, 12, 12, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 8, 8, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 0, 16, 16, 16), Block.box(0, 0, 4, 4, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 14, 16, 16, 16), Block.box(0, 0, 0, 2, 16, 14), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 12, 16, 16, 16), Block.box(0, 0, 0, 4, 16, 12), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(0, 0, 0, 8, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 4, 16, 16, 16), Block.box(0, 0, 0, 12, 16, 4), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 2, 16, 16), Block.box(2, 0, 0, 16, 16, 2), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 4, 16, 16), Block.box(4, 0, 0, 16, 16, 4), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 0, 16, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 12, 16, 16), Block.box(12, 0, 0, 16, 16, 12), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 16, 16, 2), Block.box(14, 0, 2, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 16, 16, 4), Block.box(12, 0, 4, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(8, 0, 8, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 16, 16, 12), Block.box(4, 0, 12, 16, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class CornerSlabBlockShapes {

        public static final Map<Direction, VoxelShape> LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(8, 0, 0, 16, 8, 16), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 8, 8, 16), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 8, 16, 8, 16), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 0, 0, 16, 8, 8), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 0, 8, 8, 16), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(8, 0, 0, 16, 8, 16), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 0, 16, 8, 8), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 0, 8, 16, 8, 16), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> TOP_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 8, 8, 16, 16, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 8, 0, 8, 16, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 8, 8, 16, 16, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 8, 0, 16, 16, 8), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> TOP_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 8, 0, 8, 16, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(8, 8, 0, 16, 16, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 8, 0, 16, 16, 8), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 8, 8, 16, 16, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR)));
    }
    
    public static class CornerSlabLayerBlockShapes {

        public static final VoxelShape[] LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 14, 16, 2, 16), Block.box(14, 0, 0, 16, 2, 14), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 12, 16, 4, 16), Block.box(12, 0, 0, 16, 4, 12), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 8, 16, 8, 16), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 4, 16, 12, 16), Block.box(4, 0, 0, 16, 12, 4), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 2, 2, 16), Block.box(2, 0, 14, 16, 2, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 4, 4, 16), Block.box(4, 0, 12, 16, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 8, 8, 16), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 12, 12, 16), Block.box(12, 0, 4, 16, 12, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 16, 2, 2), Block.box(0, 0, 2, 2, 2, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 16, 4, 4), Block.box(0, 0, 4, 4, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 16, 8, 8), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 16, 12, 12), Block.box(0, 0, 12, 12, 12, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 0, 16, 2, 16), Block.box(0, 0, 0, 14, 2, 2), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 0, 16, 4, 16), Block.box(0, 0, 0, 12, 4, 4), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 0, 16, 8, 16), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 0, 16, 12, 16), Block.box(0, 0, 0, 4, 12, 12), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 14, 14, 16, 16, 16), Block.box(14, 14, 0, 16, 16, 14), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 12, 16, 16, 16), Block.box(12, 12, 0, 16, 16, 12), BooleanOp.OR),
                Shapes.join(Block.box(0, 8, 8, 16, 16, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 4, 4, 16, 16, 16), Block.box(4, 4, 0, 16, 16, 4), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 14, 0, 2, 16, 16), Block.box(2, 14, 14, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 0, 4, 16, 16), Block.box(4, 12, 12, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 8, 0, 8, 16, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 4, 0, 12, 16, 16), Block.box(12, 4, 4, 16, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 14, 0, 16, 16, 2), Block.box(0, 14, 2, 2, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 0, 16, 16, 4), Block.box(0, 12, 4, 4, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 8, 0, 16, 16, 8), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 4, 0, 16, 16, 12), Block.box(0, 4, 12, 12, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 14, 0, 16, 16, 16), Block.box(0, 14, 0, 14, 16, 2), BooleanOp.OR),
                Shapes.join(Block.box(12, 12, 0, 16, 16, 16), Block.box(0, 12, 0, 12, 16, 4), BooleanOp.OR),
                Shapes.join(Block.box(8, 8, 0, 16, 16, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(4, 4, 0, 16, 16, 16), Block.box(0, 4, 0, 4, 16, 12), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }
    
    public static class DiagonalBeamBlockShapes {

        public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
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
        
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
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
        
        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
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
        
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
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
    }

    public static class DoorFrameBlockShapes {

        public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(-3, 0, 0, 0, 16, 2), Block.box(16, 0, 0, 19, 16, 2), BooleanOp.OR),
                Block.box(16, 0, 0, 19, 16, 2),
                Block.box(-3, 0, 0, 0, 16, 2)};
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, -3, 16, 16, 0), Block.box(14, 0, 16, 16, 16, 19), BooleanOp.OR),
                Block.box(14, 0, 16, 16, 16, 19),
                Block.box(14, 0, -3, 16, 16, 0)};

        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(16, 0, 14, 19, 16, 16), Block.box(-3, 0, 14, 0, 16, 16), BooleanOp.OR),
                Block.box(-3, 0, 14, 0, 16, 16),
                Block.box(16, 0, 14, 19, 16, 16)};
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 16, 2, 16, 19), Block.box(0, 0, -3, 2, 16, 0), BooleanOp.OR),
                Block.box(0, 0, -3, 2, 16, 0),
                Block.box(0, 0, 16, 2, 16, 19)};
    }

    public static class DoorFrameLintelBlockShapes {

        public static final VoxelShape[] BOTTOM_SHAPE_NORTH = new VoxelShape[]{
                Shapes.empty(),
                Block.box(-3, 13, 0, 19, 16, 2),
                Block.box(-3, 13, 0, 16, 16, 2),
                Block.box(0, 13, 0, 19, 16, 2),
                Block.box(0, 13, 0, 16, 16, 2)
        };
        public static final VoxelShape[] BOTTOM_SHAPE_EAST = new VoxelShape[]{
                Shapes.empty(),
                Block.box(14, 13, -3, 16, 16, 19),
                Block.box(14, 13, -3, 16, 16, 16),
                Block.box(14, 13, 0, 16, 16, 19),
                Block.box(14, 13, 0, 16, 16, 16)
        };
        public static final VoxelShape[] BOTTOM_SHAPE_SOUTH = new VoxelShape[]{
                Shapes.empty(),
                Block.box(-3, 13, 14, 19, 16, 16),
                Block.box(0, 13, 14, 19, 16, 16),
                Block.box(-3, 13, 14, 16, 16, 16),
                Block.box(0, 13, 14, 16, 16, 16)
        };
        public static final VoxelShape[] BOTTOM_SHAPE_WEST = new VoxelShape[]{
                Shapes.empty(),
                Block.box(0, 13, -3, 2, 16, 19),
                Block.box(0, 13, 0, 2, 16, 19),
                Block.box(0, 13, -3, 2, 16, 16),
                Block.box(0, 13, 0, 2, 16, 16)
        };
        public static final VoxelShape[] SHAPE_NORTH= new VoxelShape[]{
                Shapes.empty(),
                Block.box(-3, 0, 0, 19, 3, 2),
                Block.box(0, 0, 0, 19, 3, 2),
                Block.box(-3, 0, 0, 16, 3, 2),
                Block.box(0, 0, 0, 16, 3, 2)
        };
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{
                Shapes.empty(),
                Block.box(14, 0, -3, 16, 3, 19),
                Block.box(14, 0, 0, 16, 3, 19),
                Block.box(14, 0, -3, 16, 3, 16),
                Block.box(14, 0, 0, 16, 3, 16)
        };
        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{
                Shapes.empty(),
                Block.box(-3, 0, 14, 19, 3, 16),
                Block.box(-3, 0, 14, 16, 3, 16),
                Block.box(0, 0, 14, 19, 3, 16),
                Block.box(0, 0, 14, 16, 3, 16)
        };
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{
                Shapes.empty(),
                Block.box(0, 0, -3, 2, 3, 19),
                Block.box(0, 0, -3, 2, 3, 16),
                Block.box(0, 0, 0, 2, 3, 19),
                Block.box(0, 0, 0, 2, 3, 16)
        };
    }

    public static class EightBlockShapes {

        public static final Map<Direction, VoxelShape> LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Block.box(0, 0, 0, 8, 8, 8),
                EAST, Block.box(8, 0, 0, 16, 8, 8),
                SOUTH, Block.box(8, 0, 8, 16, 8, 16),
                WEST, Block.box(0, 0, 8, 8, 8, 16)));
        public static final Map<Direction, VoxelShape> RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Block.box(8, 0, 0, 16, 8, 8),
                SOUTH, Block.box(0, 0, 8, 8, 8, 16),
                EAST, Block.box(8, 0, 8, 16, 8, 16),
                WEST, Block.box(0, 0, 0, 8, 8, 8)));
        public static final Map<Direction, VoxelShape> TOP_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Block.box(0, 8, 0, 8, 16, 8),
                SOUTH, Block.box(8, 8, 8, 16, 16, 16),
                EAST, Block.box(8, 8, 0, 16, 16, 8),
                WEST, Block.box(0, 8, 8, 8, 16, 16)));
        public static final Map<Direction, VoxelShape> TOP_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Block.box(8, 8, 0, 16, 16, 8),
                EAST, Block.box(8, 8, 8, 16, 16, 16),
                SOUTH, Block.box(0, 8, 8, 8, 16, 16),
                WEST, Block.box(0, 8, 0, 8, 16, 8)));
    }

    public static class EightLayerBlockShapes {

        public static final VoxelShape[] LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 0, 14, 16, 2, 16),
                Block.box(12, 0, 12, 16, 4, 16),
                Block.box(8, 0, 8, 16, 8, 16),
                Block.box(4, 0, 4, 16, 12, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 14, 2, 2, 16),
                Block.box(0, 0, 12, 4, 4, 16),
                Block.box(0, 0, 8, 8, 8, 16),
                Block.box(0, 0, 4, 12, 12, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 0, 2, 2, 2),
                Block.box(0, 0, 0, 4, 4, 4),
                Block.box(0, 0, 0, 8, 8, 8),
                Block.box(0, 0, 0, 12, 12, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 0, 0, 16, 2, 2),
                Block.box(12, 0, 0, 16, 4, 4),
                Block.box(8, 0, 0, 16, 8, 8),
                Block.box(4, 0, 0, 16, 12, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 14, 14, 16, 16, 16),
                Block.box(12, 12, 12, 16, 16, 16),
                Block.box(8, 8, 8, 16, 16, 16),
                Block.box(4, 4, 4, 16, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 14, 14, 2, 16, 16),
                Block.box(0, 12, 12, 4, 16, 16),
                Block.box(0, 8, 8, 8, 16, 16),
                Block.box(0, 4, 4, 12, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 14, 0, 2, 16, 2),
                Block.box(0, 12, 0, 4, 16, 4),
                Block.box(0, 8, 0, 8, 16, 8),
                Block.box(0, 4, 0, 12, 16, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 14, 0, 16, 16, 2),
                Block.box(12, 12, 0, 16, 16, 4),
                Block.box(8, 8, 0, 16, 16, 8),
                Block.box(4, 4, 0, 16, 16, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class HalfArchBlockShapes {

        public static final Map<Direction, VoxelShape> HALF_ARCH_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Block.box(0, 8, 0, 16, 16, 8),
                EAST, Block.box(8, 8, 0, 16, 16, 16),
                SOUTH, Block.box(0, 8, 8, 16, 16, 16),
                WEST, Block.box(0, 8, 0, 8, 16, 16)));
    }

    public static class HalfWindowBlockShapes {

        public static final Map<Direction, VoxelShape> TOP_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                SOUTH, Stream.of(
                        Block.box(12, 0, 12, 16, 16, 16),
                        Block.box(0, 0, 12, 4, 16, 16),
                        Block.box(4, 14, 12, 12, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                NORTH, Stream.of(
                        Block.box(0, 0, 0, 4, 16, 4),
                        Block.box(12, 0, 0, 16, 16, 4),
                        Block.box(4, 14, 0, 12, 16, 4)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(0, 0, 12, 4, 16, 16),
                        Block.box(0, 0, 0, 4, 16, 4),
                        Block.box(0, 14, 4, 4, 16, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(12, 0, 0, 16, 16, 4),
                        Block.box(12, 0, 12, 16, 16, 16),
                        Block.box(12, 14, 4, 16, 16, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

        public static final Map<Direction, VoxelShape> MIDDLE_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                SOUTH, Shapes.join(Block.box(12, 0, 12, 16, 16, 16),
                        Block.box(0, 0, 12, 4, 16, 16), BooleanOp.OR),
                NORTH, Shapes.join(Block.box(0, 0, 0, 4, 16, 4),
                        Block.box(12, 0, 0, 16, 16, 4), BooleanOp.OR),
                WEST, Shapes.join(Block.box(0, 0, 12, 4, 16, 16),
                        Block.box(0, 0, 0, 4, 16, 4), BooleanOp.OR),
                EAST, Shapes.join(Block.box(12, 0, 0, 16, 16, 4),
                        Block.box(12, 0, 12, 16, 16, 16), BooleanOp.OR)));

        public static final Map<Direction, VoxelShape> BOTTOM_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                SOUTH, Stream.of(
                        Block.box(12, 0, 12, 16, 16, 16),
                        Block.box(0, 0, 12, 4, 16, 16),
                        Block.box(4, 0, 12, 12, 2, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                NORTH, Stream.of(
                        Block.box(0, 0, 0, 4, 16, 4),
                        Block.box(12, 0, 0, 16, 16, 4),
                        Block.box(4, 0, 0, 12, 2, 4)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(0, 0, 12, 4, 16, 16),
                        Block.box(0, 0, 0, 4, 16, 4),
                        Block.box(0, 0, 4, 4, 2, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(12, 0, 0, 16, 16, 4),
                        Block.box(12, 0, 12, 16, 16, 16),
                        Block.box(12, 0, 4, 16, 2, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

        public static final Map<Direction, VoxelShape> FULL_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                SOUTH, Stream.of(
                        Block.box(12, 0, 12, 16, 16, 16),
                        Block.box(0, 0, 12, 4, 16, 16),
                        Block.box(4, 0, 12, 12, 2, 16),
                        Block.box(4, 14, 12, 12, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                NORTH, Stream.of(
                        Block.box(0, 0, 0, 4, 16, 4),
                        Block.box(12, 0, 0, 16, 16, 4),
                        Block.box(4, 0, 0, 12, 2, 4),
                        Block.box(4, 14, 0, 12, 16, 4)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(0, 0, 12, 4, 16, 16),
                        Block.box(0, 0, 0, 4, 16, 4),
                        Block.box(0, 0, 4, 4, 2, 12),
                        Block.box(0, 14, 4, 4, 16, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(12, 0, 0, 16, 16, 4),
                        Block.box(12, 0, 12, 16, 16, 16),
                        Block.box(12, 0, 4, 16, 2, 12),
                        Block.box(12, 14, 4, 16, 16, 12)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));
    }

    public static class HorizontalBeamBlockShapes {

        public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(5, 0, 0, 11, 4, 16),
                Shapes.join(Block.box(5, 0, 0, 11, 4, 11), Block.box(0, 0, 5, 5, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 0, 11, 4, 11), Block.box(11, 0, 5, 16, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 0, 11, 4, 16), Block.box(0, 0, 5, 5, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 0, 11, 4, 16), Block.box(11, 0, 5, 16, 4, 11), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 0, 0, 11, 4, 16),
                        Block.box(11, 0, 5, 16, 4, 11),
                        Block.box(0, 0, 5, 5, 4, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 5, 16, 4, 11),
                Shapes.join(Block.box(5, 0, 5, 16, 4, 11), Block.box(5, 0, 0, 11, 4, 5), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 5, 16, 4, 11), Block.box(5, 0, 11, 11, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 5, 16, 4, 11), Block.box(5, 0, 0, 11, 4, 5), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 5, 16, 4, 11), Block.box(5, 0, 11, 11, 4, 16), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 0, 0, 11, 4, 16),
                        Block.box(11, 0, 5, 16, 4, 11),
                        Block.box(0, 0, 5, 5, 4, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(5, 0, 0, 11, 4, 16),
                Shapes.join(Block.box(5, 0, 5, 11, 4, 16), Block.box(11, 0, 5, 16, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 5, 11, 4, 16), Block.box(0, 0, 5, 5, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 0, 11, 4, 16), Block.box(11, 0, 5, 16, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 0, 11, 4, 16), Block.box(0, 0, 5, 5, 4, 11), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 0, 0, 11, 4, 16),
                        Block.box(11, 0, 5, 16, 4, 11),
                        Block.box(0, 0, 5, 5, 4, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 5, 16, 4, 11),
                Shapes.join(Block.box(0, 0, 5, 11, 4, 11), Block.box(5, 0, 11, 11, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 5, 11, 4, 11), Block.box(5, 0, 0, 11, 4, 5), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 5, 16, 4, 11), Block.box(5, 0, 11, 11, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 5, 16, 4, 11), Block.box(5, 0, 0, 11, 4, 5), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 0, 0, 11, 4, 16),
                        Block.box(11, 0, 5, 16, 4, 11),
                        Block.box(0, 0, 5, 5, 4, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
        public static final VoxelShape[] TOP_SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(5, 12, 0, 11, 16, 16),
                Shapes.join(Block.box(5, 12, 0, 11, 16, 11), Block.box(0, 12, 5, 5, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 12, 0, 11, 16, 11), Block.box(11, 12, 5, 16, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 12, 0, 11, 16, 16), Block.box(0, 12, 5, 5, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 12, 0, 11, 16, 16), Block.box(11, 12, 5, 16, 16, 11), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 12, 0, 11, 16, 16),
                        Block.box(11, 12, 5, 16, 16, 11),
                        Block.box(0, 12, 5, 5, 16, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
        public static final VoxelShape[] TOP_SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 12, 5, 16, 16, 11),
                Shapes.join(Block.box(5, 12, 5, 16, 16, 11), Block.box(5, 12, 0, 11, 16, 5), BooleanOp.OR),
                Shapes.join(Block.box(5, 12, 5, 16, 16, 11), Block.box(5, 12, 11, 11, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 5, 16, 16, 11), Block.box(5, 12, 0, 11, 16, 5), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 5, 16, 16, 11), Block.box(5, 12, 11, 11, 16, 16), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 12, 0, 11, 16, 16),
                        Block.box(11, 12, 5, 16, 16, 11),
                        Block.box(0, 12, 5, 5, 16, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
        public static final VoxelShape[] TOP_SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(5, 12, 0, 11, 16, 16),
                Shapes.join(Block.box(5, 12, 5, 11, 16, 16), Block.box(11, 12, 5, 16, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 12, 5, 11, 16, 16), Block.box(0, 12, 5, 5, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 12, 0, 11, 16, 16), Block.box(11, 12, 5, 16, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(5, 12, 0, 11, 16, 16), Block.box(0, 12, 5, 5, 16, 11), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 12, 0, 11, 16, 16),
                        Block.box(11, 12, 5, 16, 16, 11),
                        Block.box(0, 12, 5, 5, 16, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
        public static final VoxelShape[] TOP_SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 12, 5, 16, 16, 11),
                Shapes.join(Block.box(0, 12, 5, 11, 16, 11), Block.box(5, 12, 11, 11, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 5, 11, 16, 11), Block.box(5, 12, 0, 11, 16, 5), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 5, 16, 16, 11), Block.box(5, 12, 11, 11, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 12, 5, 16, 16, 11), Block.box(5, 12, 0, 11, 16, 5), BooleanOp.OR),
                Stream.of(
                        Block.box(5, 12, 0, 11, 16, 16),
                        Block.box(11, 12, 5, 16, 16, 11),
                        Block.box(0, 12, 5, 5, 16, 11)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()};
    }

    public static class LargeArchBlockShapes {

        public static final Map<Direction, VoxelShape> CORNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(0, 4.98438, 0, 2, 13.00001, 16),
                        Block.box(0, 4.98438, 0, 16, 13.00001, 2)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                SOUTH, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(14, 4.98438, 0, 16, 13.00001, 16),
                        Block.box(0, 4.98438, 14, 16, 13.00001, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(0, 4.98438, 0, 16, 13.00001, 2),
                        Block.box(14, 4.98438, 0, 16, 13.00001, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(0, 4.98438, 14, 16, 13.00001, 16),
                        Block.box(0, 4.98438, 0, 2, 13.00001, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

        public static final Map<Direction, VoxelShape> CORNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(0, 4.98438, 0, 16, 13.00001, 2),
                        Block.box(14, 4.98438, 0, 16, 13.00001, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                SOUTH, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(0, 4.98438, 14, 16, 13.00001, 16),
                        Block.box(0, 4.98438, 0, 2, 13.00001, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                EAST, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(14, 4.98438, 0, 16, 13.00001, 16),
                        Block.box(0, 4.98438, 14, 16, 13.00001, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                WEST, Stream.of(
                        Block.box(0, 13.00001, 0, 16, 16, 16),
                        Block.box(0, 4.98438, 0, 2, 13.00001, 16),
                        Block.box(0, 4.98438, 0, 16, 13.00001, 2)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));

        public static final Map<Direction, VoxelShape> STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                NORTH, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(0, 4.98438, 0, 16, 13.00001, 2), BooleanOp.OR),
                SOUTH, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(0, 4.98438, 14, 16, 13.00001, 16), BooleanOp.OR),
                EAST, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(14, 4.98438, 0, 16, 13.00001, 16), BooleanOp.OR),
                WEST, Shapes.join(Block.box(0, 13.00001, 0, 16, 16, 16), Block.box(0, 4.98438, 0, 2, 13.00001, 16), BooleanOp.OR)));

        public static final VoxelShape SHAPE = Block.box(0, 13, 0, 16, 16, 16);
    }

    public static class LargeHalfArchBlockShapes {

        public static final Map<Direction, VoxelShape> LARGE_HALF_ARCH_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                SOUTH, Block.box(0, 13, 8, 16, 16, 16),
                NORTH, Block.box(0, 13, 0, 16, 16, 8),
                WEST, Block.box(0, 13, 0, 8, 16, 16),
                EAST, Block.box(8, 13, 0, 16, 16, 16)));
    }

    public static class PillarLayerBlockShapes {

        public static final VoxelShape[] PILLAR_SHAPE = new VoxelShape[]{Shapes.empty(),
                Block.box(7, 0, 7, 9, 16, 9),
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(4, 0, 4, 12, 16, 12),
                Block.box(2, 0, 2, 14, 16, 14),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class QuarterBlockShapes {

        public static final Map<Direction, VoxelShape> TOP_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Block.box(0, 8, 8, 16, 16, 16),
                Direction.NORTH, Block.box(0, 8, 0, 16, 16, 8),
                Direction.WEST, Block.box(0, 8, 0, 8, 16, 16),
                Direction.EAST, Block.box(8, 8, 0, 16, 16, 16)));
        public static final Map<Direction, VoxelShape> BOTTOM_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Block.box(0, 0, 8, 16, 8, 16),
                Direction.NORTH, Block.box(0, 0, 0, 16, 8, 8),
                Direction.WEST, Block.box(0, 0, 0, 8, 8, 16),
                Direction.EAST, Block.box(8, 0, 0, 16, 8, 16)));
    }

    public static class QuarterLayerBlockShapes {

        public static final VoxelShape[] SOUTH_BOTTOM = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 14, 16, 2, 16),
                Block.box(0, 0, 12, 16, 4, 16),
                Block.box(0, 0, 8, 16, 8, 16),
                Block.box(0, 0, 4, 16, 12, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] WEST_BOTTOM = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 0, 2, 2, 16),
                Block.box(0, 0, 0, 4, 4, 16),
                Block.box(0, 0, 0, 8, 8, 16),
                Block.box(0, 0, 0, 12, 12, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] NORTH_BOTTOM = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 0, 16, 2, 2),
                Block.box(0, 0, 0, 16, 4, 4),
                Block.box(0, 0, 0, 16, 8, 8),
                Block.box(0, 0, 0, 16, 12, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] EAST_BOTTOM = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 0, 0, 16, 2, 16),
                Block.box(12, 0, 0, 16, 4, 16),
                Block.box(8, 0, 0, 16, 8, 16),
                Block.box(4, 0, 0, 16, 12, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] SOUTH_TOP = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 14, 14, 16, 16, 16),
                Block.box(0, 12, 12, 16, 16, 16),
                Block.box(0, 8, 8, 16, 16, 16),
                Block.box(0, 4, 4, 16, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] WEST_TOP = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 14, 0, 2, 16, 16),
                Block.box(0, 12, 0, 4, 16, 16),
                Block.box(0, 8, 0, 8, 16, 16),
                Block.box(0, 4, 0, 12, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] NORTH_TOP = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 14, 0, 16, 16, 2),
                Block.box(0, 12, 0, 16, 16, 4),
                Block.box(0, 8, 0, 16, 16, 8),
                Block.box(0, 4, 0, 16, 16, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] EAST_TOP = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 14, 0, 16, 16, 16),
                Block.box(12, 12, 0, 16, 16, 16),
                Block.box(8, 8, 0, 16, 16, 16),
                Block.box(4, 4, 0, 16, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class Roof22BlockShapes {

        public static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0.01, 0, 16, 8, 16);
        public static final VoxelShape TOP_SHAPE = Block.box(0, 0.01, 0, 16, 16, 16);
    }

    public static class Roof45BlockShapes {

        public static final Map<Direction, VoxelShape> STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Shapes.join(Block.box(0, 0, 8, 16, 16, 16), Block.box(0, 0, 0, 16, 8, 8), BooleanOp.OR),
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 0, 16, 16, 8), Block.box(0, 0, 8, 16, 8, 16), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 0, 0, 8, 16, 16), Block.box(8, 0, 0, 16, 8, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(8, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 8, 8, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> OUTER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> OUTER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.EAST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 16, 8, 16), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> INNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(0, 8, 0, 8, 16, 16),
                        Block.box(8, 8, 0, 16, 16, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Direction.NORTH, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(8, 8, 0, 16, 16, 16),
                        Block.box(0, 8, 8, 8, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Direction.WEST, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(0, 8, 0, 16, 16, 8),
                        Block.box(8, 8, 8, 16, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Direction.EAST, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(0, 8, 8, 16, 16, 16),
                        Block.box(0, 8, 0, 8, 16, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));
        public static final Map<Direction, VoxelShape> INNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.EAST, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(0, 8, 0, 8, 16, 16),
                        Block.box(8, 8, 0, 16, 16, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Direction.WEST, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(8, 8, 0, 16, 16, 16),
                        Block.box(0, 8, 8, 8, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Direction.SOUTH, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(0, 8, 0, 16, 16, 8),
                        Block.box(8, 8, 8, 16, 16, 16)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                Direction.NORTH, Stream.of(
                        Block.box(0, 0, 0, 16, 8, 16),
                        Block.box(0, 8, 8, 16, 16, 16),
                        Block.box(0, 8, 0, 8, 16, 8)
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()));
    }

    public static class Roof67BlockShapes {

        public static final Map<Direction, VoxelShape> TOP_OUTER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Block.box(8, 0.01, 8, 16, 16, 16),
                Direction.SOUTH, Block.box(0, 0.01, 0, 8, 16, 8),
                Direction.EAST, Block.box(0, 0.01, 8, 8, 16, 16),
                Direction.WEST, Block.box(8, 0.01, 0, 16, 16, 8)));
        public static final Map<Direction, VoxelShape> TOP_OUTER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Block.box(0, 0.01, 8, 8, 16, 16),
                Direction.SOUTH, Block.box(8, 0.01, 0, 16, 16, 8),
                Direction.EAST, Block.box(0, 0.01, 0, 8, 16, 8),
                Direction.WEST, Block.box(8, 0.01, 8, 16, 16, 16)));
        public static final Map<Direction, VoxelShape> TOP_INNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.EAST, Shapes.join(Block.box(0, 0.01, 8, 16, 16, 16), Block.box(0, 0.01, 0, 8, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(8, 0.01, 0, 16, 16, 16), Block.box(0, 0.01, 8, 8, 16, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0.01, 0, 16, 16, 8), Block.box(8, 0.01, 8, 16, 16, 16), BooleanOp.OR),
                Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 8, 16, 16), Block.box(8, 0.01, 0, 16, 16, 8), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> TOP_INNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.WEST, Shapes.join(Block.box(8, 0.01, 0, 16, 16, 16), Block.box(0, 0.01, 8, 8, 16, 16), BooleanOp.OR),
                Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 16, 8), Block.box(8, 0.01, 8, 16, 16, 16), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0.01, 8, 16, 16, 16), Block.box(0, 0.01, 0, 8, 16, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 8, 16, 16), Block.box(8, 0.01, 0, 16, 16, 8), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> TOP_STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Block.box(0, 0.01, 8, 16, 16, 16),
                Direction.SOUTH, Block.box(0, 0.01, 0, 16, 16, 8),
                Direction.EAST, Block.box(0, 0.01, 0, 8, 16, 16),
                Direction.WEST, Block.box(8, 0.01, 0, 16, 16, 16)));
        public static final Map<Direction, VoxelShape> BOTTOM_STRAIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, Shapes.join(Block.box(0, 0.01, 8, 16, 16, 16), Block.box(0, 0.01, 0, 16, 8, 8), BooleanOp.OR),
                Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 16, 8), Block.box(0, 0.01, 8, 16, 8, 16), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 8, 16, 16), Block.box(8, 0.01, 0, 16, 8, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(8, 0.01, 0, 16, 16, 16), Block.box(0, 0.01, 0, 8, 8, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> BOTTOM_OUTER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 0, 8, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 8, 16, 16, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 0, 16, 16, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 8, 8, 16, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> BOTTOM_OUTER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.EAST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 0, 8, 16, 8), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 8, 16, 16, 16), BooleanOp.OR),
                Direction.SOUTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(8, 8.01, 0, 16, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0.01, 0, 16, 8, 16), Block.box(0, 8.01, 8, 8, 16, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> BOTTOM_INNER_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(0, 8.01, 0, 8, 16, 16),
                        Block.box(8, 8.01, 0, 16, 16, 8)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
                Direction.NORTH, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(8, 8.01, 0, 16, 16, 16),
                        Block.box(0, 8.01, 8, 8, 16, 16)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
                Direction.WEST, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(0, 8.01, 0, 16, 16, 8),
                        Block.box(8, 8.01, 8, 16, 16, 16)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
                Direction.EAST, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(0, 8.01, 8, 16, 16, 16),
                        Block.box(0, 8.01, 0, 8, 16, 8)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get()));
        public static final Map<Direction, VoxelShape> BOTTOM_INNER_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.EAST, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(0, 8.01, 0, 8, 16, 16),
                        Block.box(8, 8.01, 0, 16, 16, 8)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
                Direction.WEST, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(8, 8.01, 0, 16, 16, 16),
                        Block.box(0, 8.01, 8, 8, 16, 16)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
                Direction.SOUTH, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(0, 8.01, 0, 16, 16, 8),
                        Block.box(8, 8.01, 8, 16, 16, 16)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get(),
                Direction.NORTH, Stream.of(
                        Block.box(0, 0.01, 0, 16, 8, 16),
                        Block.box(0, 8.01, 8, 16, 16, 16),
                        Block.box(0, 8.01, 0, 8, 16, 8)
                ).reduce((v0, v2) -> Shapes.join(v0, v2, BooleanOp.OR)).get()));
    }

    public static class RoofPeakBlockShapes {

        public static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 8, 16);
        public static final VoxelShape TOP_SHAPE = Block.box(0, .01, 0, 16, 16, 16);
    }

    public static class SlabLayerBlockShapes {

        public static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(0, 0, 0, 16, 4, 16),
                Block.box(0, 0, 0, 16, 6, 16),
                Block.box(0, 0, 0, 16, 8, 16),
                Block.box(0, 0, 0, 16, 10, 16),
                Block.box(0, 0, 0, 16, 12, 16),
                Block.box(0, 0, 0, 16, 14, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] SHAPE_BY_LAYER_TOP = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 14, 0, 16, 16, 16),
                Block.box(0, 12, 0, 16, 16, 16),
                Block.box(0, 10, 0, 16, 16, 16),
                Block.box(0, 8, 0, 16, 16, 16),
                Block.box(0, 6, 0, 16, 16, 16),
                Block.box(0, 4, 0, 16, 16, 16),
                Block.box(0, 2, 0, 16, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class TallDoorBlockShapes {

        public static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
        public static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
        public static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        public static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    }

    public static class VerticalBeamBlockShapes {

        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(5, 0, 12, 11, 16, 16),
                Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 12, 0, 11, 16, 12), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 12, 0, 11, 16, 12), BooleanOp.OR),
                Block.box(5, 0, 12, 11, 16, 16),
                Block.box(5, 0, 12, 11, 16, 16),
                Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 0, 0, 11, 4, 12), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 12, 11, 16, 16), Block.box(5, 0, 0, 11, 4, 12), BooleanOp.OR)};
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 5, 4, 16, 11),
                Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 12, 5, 16, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 12, 5, 16, 16, 11), BooleanOp.OR),
                Block.box(0, 0, 5, 4, 16, 11),
                Block.box(0, 0, 5, 4, 16, 11),
                Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 0, 5, 16, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 5, 4, 16, 11), Block.box(4, 0, 5, 16, 4, 11), BooleanOp.OR)};
        public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(5, 0, 0, 11, 16, 4),
                Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 12, 4, 11, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 12, 4, 11, 16, 16), BooleanOp.OR),
                Block.box(5, 0, 0, 11, 16, 4),
                Block.box(5, 0, 0, 11, 16, 4),
                Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 0, 4, 11, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(5, 0, 0, 11, 16, 4), Block.box(5, 0, 4, 11, 4, 16), BooleanOp.OR)};
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(12, 0, 5, 16, 16, 11),
                Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 12, 5, 12, 16, 11), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 12, 5, 12, 16, 11), BooleanOp.OR),
                Block.box(12, 0, 5, 16, 16, 11),
                Block.box(12, 0, 5, 16, 16, 11),
                Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 0, 5, 12, 4, 11), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 5, 16, 16, 11), Block.box(0, 0, 5, 12, 4, 11), BooleanOp.OR)};
    }

    public static class VerticalCornerSlabBlockShapes {

        public static final Map<Direction, VoxelShape> LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> TOP_LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR)));
        public static final Map<Direction, VoxelShape> TOP_RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Direction.NORTH, Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Direction.WEST, Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Direction.EAST, Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR)));
    }

    public static class VerticalCornerSlabLayerBlockShapes {

        public static final VoxelShape[] RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 14, 16, 16, 16), Block.box(0, 0, 14, 14, 2, 16), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 12, 16, 16, 16), Block.box(0, 0, 12, 12, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 4, 16, 16, 16), Block.box(0, 0, 4, 4, 12, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 14, 2, 16, 16), Block.box(0, 0, 0, 2, 2, 14), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 12, 4, 16, 16), Block.box(0, 0, 0, 4, 4, 12), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 4, 12, 16, 16), Block.box(0, 0, 0, 12, 12, 4), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 2, 16, 2), Block.box(2, 0, 0, 16, 2, 2), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 4, 16, 4), Block.box(4, 0, 0, 16, 4, 4), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 12, 16, 12), Block.box(12, 0, 0, 16, 12, 12), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 0, 16, 16, 2), Block.box(14, 0, 2, 16, 2, 16), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 0, 16, 16, 4), Block.box(12, 0, 4, 16, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 0, 16, 16, 12), Block.box(4, 0, 12, 16, 12, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 14, 16, 16, 16), Block.box(0, 14, 14, 14, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 12, 16, 16, 16), Block.box(0, 12, 12, 12, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 4, 16, 16, 16), Block.box(0, 4, 4, 4, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 14, 2, 16, 16), Block.box(0, 14, 0, 2, 16, 14), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 12, 4, 16, 16), Block.box(0, 12, 0, 4, 16, 12), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 4, 12, 16, 16), Block.box(0, 4, 0, 12, 16, 4), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 2, 16, 2), Block.box(2, 14, 0, 16, 16, 2), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 4, 16, 4), Block.box(4, 12, 0, 16, 16, 4), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 12, 16, 12), Block.box(12, 4, 0, 16, 16, 12), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 0, 16, 16, 2), Block.box(14, 14, 2, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 0, 16, 16, 4), Block.box(12, 12, 4, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 0, 16, 16, 12), Block.box(4, 4, 12, 16, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 0, 16, 16, 2), Block.box(0, 0, 0, 14, 2, 2), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 0, 16, 16, 4), Block.box(0, 0, 0, 12, 4, 4), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(0, 0, 0, 8, 8, 8), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 0, 16, 16, 12), Block.box(0, 0, 0, 4, 12, 12), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 14, 16, 16, 16), Block.box(14, 0, 0, 16, 2, 14), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 12, 16, 16, 16), Block.box(12, 0, 0, 16, 4, 12), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(8, 0, 0, 16, 8, 8), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 4, 16, 16, 16), Block.box(4, 0, 0, 16, 12, 4), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 14, 2, 16, 16), Block.box(2, 0, 14, 16, 2, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 12, 4, 16, 16), Block.box(4, 0, 12, 16, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(8, 0, 8, 16, 8, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 4, 12, 16, 16), Block.box(12, 0, 4, 16, 12, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 2, 16, 2), Block.box(0, 0, 2, 2, 2, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 4, 16, 4), Block.box(0, 0, 4, 4, 4, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(0, 0, 8, 8, 8, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 12, 16, 12), Block.box(0, 0, 12, 12, 12, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_EAST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 0, 16, 16, 2), Block.box(0, 14, 0, 14, 16, 2), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 0, 16, 16, 4), Block.box(0, 12, 0, 12, 16, 4), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 0, 16, 16, 8), Block.box(0, 8, 0, 8, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 0, 16, 16, 12), Block.box(0, 4, 0, 4, 16, 12), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(14, 0, 14, 16, 16, 16), Block.box(14, 14, 0, 16, 16, 14), BooleanOp.OR),
                Shapes.join(Block.box(12, 0, 12, 16, 16, 16), Block.box(12, 12, 0, 16, 16, 12), BooleanOp.OR),
                Shapes.join(Block.box(8, 0, 8, 16, 16, 16), Block.box(8, 8, 0, 16, 16, 8), BooleanOp.OR),
                Shapes.join(Block.box(4, 0, 4, 16, 16, 16), Block.box(4, 4, 0, 16, 16, 4), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_WEST = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 14, 2, 16, 16), Block.box(2, 14, 14, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 12, 4, 16, 16), Block.box(4, 12, 12, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 8, 8, 16, 16), Block.box(8, 8, 8, 16, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 4, 12, 16, 16), Block.box(12, 4, 4, 16, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] TOP_LEFT_NORTH = new VoxelShape[]{Shapes.empty(),
                Shapes.join(Block.box(0, 0, 0, 2, 16, 2), Block.box(0, 14, 2, 2, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 4, 16, 4), Block.box(0, 12, 4, 4, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 8, 16, 8), Block.box(0, 8, 8, 8, 16, 16), BooleanOp.OR),
                Shapes.join(Block.box(0, 0, 0, 12, 16, 12), Block.box(0, 4, 12, 12, 16, 16), BooleanOp.OR),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class VerticalQuarterBlockShapes {

        public static final Map<Direction, VoxelShape> LEFT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                SOUTH, Block.box(8, 0, 8, 16, 16, 16),
                NORTH, Block.box(0, 0, 0, 8, 16, 8),
                WEST, Block.box(0, 0, 8, 8, 16, 16),
                EAST, Block.box(8, 0, 0, 16, 16, 8)));
        public static final Map<Direction, VoxelShape> RIGHT_SHAPE = Maps.newEnumMap(ImmutableMap.of(
                SOUTH, Block.box(0, 0, 8, 8, 16, 16),
                NORTH, Block.box(8, 0, 0, 16, 16, 8),
                WEST, Block.box(0, 0, 0, 8, 16, 8),
                EAST, Block.box(8, 0, 8, 16, 16, 16)));
    }

    public static class VerticalQuarterLayerBlockShapes {

        public static final VoxelShape[] LEFT_SOUTH_RIGHT_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 0, 14, 16, 16, 16),
                Block.box(12, 0, 12, 16, 16, 16),
                Block.box(8, 0, 8, 16, 16, 16),
                Block.box(4, 0, 4, 16, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_WEST_RIGHT_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 14, 2, 16, 16),
                Block.box(0, 0, 12, 4, 16, 16),
                Block.box(0, 0, 8, 8, 16, 16),
                Block.box(0, 0, 4, 12, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_NORTH_RIGHT_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 0, 2, 16, 2),
                Block.box(0, 0, 0, 4, 16, 4),
                Block.box(0, 0, 0, 8, 16, 8),
                Block.box(0, 0, 0, 12, 16, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] LEFT_EAST_RIGHT_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 0, 0, 16, 16, 2),
                Block.box(12, 0, 0, 16, 16, 4),
                Block.box(8, 0, 0, 16, 16, 8),
                Block.box(4, 0, 0, 16, 16, 12),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class VerticalSlabBlockShapes {

        public static final Map<Direction, VoxelShape> SHAPE = Maps.newEnumMap(ImmutableMap.of(
                Direction.SOUTH, Block.box(0, 0, 8, 16, 16, 16),
                Direction.NORTH, Block.box(0, 0, 0, 16, 16, 8),
                Direction.WEST, Block.box(0, 0, 0, 8, 16, 16),
                Direction.EAST, Block.box(8, 0, 0, 16, 16, 16)));
        public static final VoxelShape DOUBLE = Block.box(0, 0, 0, 16, 16, 16);
    }

    public static class VerticalSlabLayerBlockShapes {

        public static final VoxelShape[] SHAPE_SOUTH = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 14, 16, 16, 16),
                Block.box(0, 0, 12, 16, 16, 16),
                Block.box(0, 0, 10, 16, 16, 16),
                Block.box(0, 0, 8, 16, 16, 16),
                Block.box(0, 0, 6, 16, 16, 16),
                Block.box(0, 0, 4, 16, 16, 16),
                Block.box(0, 0, 2, 16, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] SHAPE_WEST = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 0, 2, 16, 16),
                Block.box(0, 0, 0, 4, 16, 16),
                Block.box(0, 0, 0, 6, 16, 16),
                Block.box(0, 0, 0, 8, 16, 16),
                Block.box(0, 0, 0, 10, 16, 16),
                Block.box(0, 0, 0, 12, 16, 16),
                Block.box(0, 0, 0, 14, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] SHAPE_NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 0, 16, 16, 2),
                Block.box(0, 0, 0, 16, 16, 4),
                Block.box(0, 0, 0, 16, 16, 6),
                Block.box(0, 0, 0, 16, 16, 8),
                Block.box(0, 0, 0, 16, 16, 10),
                Block.box(0, 0, 0, 16, 16, 12),
                Block.box(0, 0, 0, 16, 16, 14),
                Block.box(0, 0.1, 0, 16, 16, 16)};
        public static final VoxelShape[] SHAPE_EAST = new VoxelShape[]{Shapes.empty(),
                Block.box(14, 0, 0, 16, 16, 16),
                Block.box(12, 0, 0, 16, 16, 16),
                Block.box(10, 0, 0, 16, 16, 16),
                Block.box(8, 0, 0, 16, 16, 16),
                Block.box(6, 0, 0, 16, 16, 16),
                Block.box(4, 0, 0, 16, 16, 16),
                Block.box(2, 0, 0, 16, 16, 16),
                Block.box(0, 0.1, 0, 16, 16, 16)};
    }

    public static class WindowBlockShapes {

        public static final VoxelShape NORTH_SOUTH_TOP_SHAPE = Stream.of(Block.box(12, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 4, 16, 16), Block.box(4, 14, 0, 12, 16, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape EAST_WEST_TOP_SHAPE = Stream.of(Block.box(0, 0, 12, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 4), Block.box(0, 14, 4, 16, 16, 12)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape NORTH_SOUTH_MIDDLE_SHAPE = Shapes.join(Block.box(12, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 4, 16, 16), BooleanOp.OR);
        public static final VoxelShape EAST_WEST_MIDDLE_SHAPE = Shapes.join(Block.box(0, 0, 12, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 4), BooleanOp.OR);
        public static final VoxelShape NORTH_SOUTH_BOTTOM_SHAPE = Stream.of(Block.box(12, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 4, 16, 16), Block.box(4, 0, 0, 12, 2, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape EAST_WEST_BOTTOM_SHAPE = Stream.of(Block.box(0, 0, 12, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 4), Block.box(0, 0, 4, 16, 2, 12)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape NORTH_SOUTH_FULL_SHAPE = Stream.of(Block.box(12, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 4, 16, 16), Block.box(4, 14, 0, 12, 16, 16), Block.box(4, 0, 0, 12, 2, 16)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        public static final VoxelShape EAST_WEST_FULL_SHAPE = Stream.of(Block.box(0, 0, 12, 16, 16, 16), Block.box(0, 0, 0, 16, 16, 4), Block.box(0, 14, 4, 16, 16, 12), Block.box(0, 0, 4, 16, 2, 12)).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }


}

