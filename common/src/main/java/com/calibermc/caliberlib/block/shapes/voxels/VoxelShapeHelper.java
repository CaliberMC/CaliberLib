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
}

