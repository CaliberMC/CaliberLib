package com.calibermc.caliberlib.block.management;

import com.calibermc.caliberlib.block.custom.*;
import com.calibermc.caliberlib.data.datagen.ModBlockStateProvider;
import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.ModelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

import static com.calibermc.caliberlib.data.ModBlockFamily.Variant;

public class ModBlockHelper {

    public static final List<Variant> VARIANTS = Lists.newArrayList(Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR, Variant.QUARTER,
            Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> STONE_VARIANTS = Lists.newArrayList(Variant.ARCH, Variant.ARCH_HALF, Variant.ARCH_LARGE,
            Variant.ARCH_LARGE_HALF, Variant.ARROWSLIT, Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR, Variant.QUARTER,
            Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> STONE_VARIANTS_WITHOUT_STAIRS = Lists.newArrayList(Variant.ARCH, Variant.ARCH_HALF, Variant.ARCH_LARGE,
            Variant.ARCH_LARGE_HALF, Variant.ARROWSLIT, Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR, Variant.QUARTER,
            Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> STONE_VARIANTS_WITHOUT_STAIRS_WALL = Lists.newArrayList(Variant.ARCH, Variant.ARCH_HALF, Variant.ARCH_LARGE,
            Variant.ARCH_LARGE_HALF, Variant.ARROWSLIT, Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER, Variant.CORNER_SLAB,
            Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR, Variant.QUARTER, Variant.QUARTER_VERTICAL,
            Variant.SLAB, Variant.SLAB_VERTICAL, Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> PLANK_VARIANTS = Lists.newArrayList(Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.FENCE, Variant.FENCE_GATE, Variant.PILLAR,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> PLANK_VARIANTS_ETC = Lists.newArrayList(Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.FENCE, Variant.FENCE_GATE, Variant.PILLAR,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF,
            Variant.CEILING_HANGING_SIGN, Variant.SIGN, Variant.DOOR, Variant.TALL_DOOR, Variant.BUTTON, Variant.TRAPDOOR);

    public static final List<Variant> BOARD_VARIANTS = Lists.newArrayList(Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR, Variant.PRESSURE_PLATE,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> PLANK_VARIANTS_WITHOUT_FENCES_STAIRS = Lists.newArrayList(Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR, Variant.PRESSURE_PLATE,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> STRIPPED_WOOD_VARIANTS = Lists.newArrayList(Variant.BALUSTRADE, Variant.BEAM_HORIZONTAL,
            Variant.BEAM_LINTEL, Variant.BEAM_POSTS, Variant.BEAM_VERTICAL, Variant.CAPITAL, Variant.CORNER, Variant.CORNER_SLAB,
            Variant.CORNER_SLAB_VERTICAL, Variant.DOOR_FRAME, Variant.DOOR_FRAME_LINTEL, Variant.EIGHTH, Variant.PILLAR,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL,
            Variant.WINDOW, Variant.WINDOW_HALF);

    public static final List<Variant> TERRACOTTA_VARIANTS = Lists.newArrayList(Variant.ARCH, Variant.ARCH_HALF,
            Variant.ARCH_LARGE, Variant.ARCH_LARGE_HALF, Variant.ARROWSLIT, Variant.BALUSTRADE, Variant.CAPITAL,
            Variant.CORNER, Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.ROOF_PEAK, Variant.ROOF_22, Variant.ROOF_45,
            Variant.ROOF_67, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW,
            Variant.WINDOW_HALF);

    public static final List<Variant> THATCH_VARIANTS = Lists.newArrayList(Variant.CAPITAL,
            Variant.CORNER, Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.ROOF_PEAK, Variant.ROOF_22, Variant.ROOF_45,
            Variant.ROOF_67, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW,
            Variant.WINDOW_HALF);

    public static final List<Variant> WOOL_VARIANTS = Lists.newArrayList(Variant.CAPITAL,
            Variant.CORNER, Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR,
            Variant.QUARTER, Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW,
            Variant.WINDOW_HALF);

    public static final List<Variant> TUDOR_VARIANTS = Lists.newArrayList(Variant.CORNER, Variant.QUARTER,
            Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL);

    public static final List<Variant> ROOF_VARIANTS = Lists.newArrayList(Variant.ROOF_PEAK, Variant.ROOF_22, Variant.ROOF_45, Variant.ROOF_67);

//    public static final List<Variant> FURNITURE_VARIANTS =

//    public static final List<Variant> LIGHTING_VARIANTS =

//    public static final List<Variant> GLASS_VARIANTS =

//    public static final List<Variant> OLD_TUDOR_VARIANTS = Lists.newArrayList(CROSS, DOWN, UP, LEFT, RIGHT, HORIZONTAL_1, HORIZONTAL_2, VERTICAL_1, VERTICAL_2);

    // REMOVE ONCE ALL BLOCKS ARE ADJUSTED
//    public static final List<Variant> VARIANTS = Lists.newArrayList(Variant.ARCH, Variant.ARCH_HALF, Variant.ARCH_LARGE,
//            Variant.ARCH_LARGE_HALF, Variant.ARROWSLIT, Variant.BALUSTRADE, Variant.CAPITAL, Variant.CORNER,
//            Variant.CORNER_SLAB, Variant.CORNER_SLAB_VERTICAL, Variant.EIGHTH, Variant.PILLAR, Variant.QUARTER,
//            Variant.QUARTER_VERTICAL, Variant.SLAB, Variant.SLAB_VERTICAL, Variant.STAIRS, Variant.WALL, Variant.WINDOW, Variant.WINDOW_HALF);

//    public static final List<Variant> WOOD_VARIANTS = Lists.newArrayList(Variant.BEAM_HORIZONTAL, Variant.BEAM_LINTEL,
//            Variant.BEAM_POSTS, Variant.BEAM_VERTICAL, Variant.DOOR_FRAME, Variant.DOOR_FRAME_LINTEL);
//
//    public static final List<Variant> VANILLA_WOOD_VARIANTS = Lists.newArrayList(Variant.FENCE, Variant.FENCE_GATE, Variant.PRESSURE_PLATE);

//    public static final List<Variant> STONE_VARIANTS = Lists.newArrayList(Variant.ARCH, Variant.ARCH_HALF, Variant.ARCH_LARGE,
//            Variant.ARCH_LARGE_HALF, Variant.ARROWSLIT);


    public static <T> List<T> modifyList(List<T> list, Consumer<List<T>> consumer) {
        List<T> newList = new ArrayList<>(list);
        consumer.accept(newList);
        return newList;
    }


    // Asset & Data Generation presets
    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ARCH = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<ArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> provider.archBlock(block, side, bottom, top), provider::archBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ARCH_HALF = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<HalfArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.archHalfBlock(block, side, bottom, top), provider::archHalfBlock);
    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ARCH_LARGE = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<LargeArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.archLargeBlock(block, side, bottom, top), provider::archLargeBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ARCH_LARGE_HALF = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<LargeHalfArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.archLargeHalfBlock(block, side, bottom, top), provider::archLargeHalfBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ARROWSLIT = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<ArrowSlitBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.arrowslitBlock(block, side, bottom, top), provider::arrowslitBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> BALUSTRADE = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<BalustradeBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.balustradeBlock(block, side, bottom, top), provider::balustradeBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> BEAM_LINTEL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<BeamLintelBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.beamLintelBlock(block, side, bottom, top), provider::beamLintelBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> BEAM_HORIZONTAL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<HorizontalBeamBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.beamHorizontalBlock(block, tex), provider::beamHorizontalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> BEAM_POSTS = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<BeamPostsBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.beamPostsBlock(block, tex), provider::beamPostsBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> BEAM_VERTICAL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<VerticalBeamBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.beamVerticalBlock(block, tex), provider::beamVerticalBlock);

//    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> BUTTON = (textureFrom) -> (b, provider) ->
//            provider.buttonBlock((StoneButtonBlock) b.get(), provider.blockTexture(BlockManager.getMainBy(b, textureFrom)));

//    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> WOOD_BUTTON = (textureFrom) -> (b, provider) ->
//            provider.buttonBlock((WoodButtonBlock) b.get(), provider.blockTexture(BlockManager.getMainBy(b, textureFrom)));

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> BUTTON = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<ButtonBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> {
                provider.buttonBlock(block, top);
                provider.models().buttonInventory(provider.name(block) + "_inventory", top);
            }, (block, texture) -> {
                provider.buttonBlock(block, texture);
                provider.models().buttonInventory(provider.name(block) + "_inventory", texture);
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> CAPITAL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<CapitalBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.capitalBlock(block, side, bottom, top), provider::capitalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> CORNER = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<CornerLayerBlock>fixBlockTex(textureFrom, b, provider, provider::cornerLayerBlock, provider::cornerLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> CORNER_SLAB = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<CornerSlabBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.cornerSlabBlock(block, side, bottom, top), provider::cornerSlabBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> CORNER_SLAB_VERTICAL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<VerticalCornerSlabBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.cornerSlabVerticalBlock(block, side, bottom, top), provider::cornerSlabVerticalBlock);

//    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> DOOR = (textureFrom) -> (b, provider) ->
//            provider.doorBlock((DoorBlock) b.get(), provider.blockTexture(BlockManager.getMainBy(b, textureFrom + "_bottom")), provider.blockTexture(BlockManager.getMainBy(b, textureFrom)));

//    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> DOOR = (textureFrom) -> (b, provider) ->
//            ModBlockHelper.<DoorBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
//                    provider.doorBlock(block, tex + "_door", bottom, top), provider::doorBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> DOOR = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<DoorBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.doorBlock(block, bottom, top), (block, texture) -> {
                ResourceLocation loc = provider.blockTexture(block);
                provider.doorBlock(block, loc.withPath(loc.getPath() + "_bottom"), loc.withPath(loc.getPath() + "_top"));
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> TALL_DOOR = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<TallDoorBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.tallDoorBlock(block, bottom, side, top), (block, texture) -> {
                ResourceLocation loc = provider.blockTexture(block);
                provider.tallDoorBlock(block, loc.withPath(loc.getPath() + "_bottom"), loc.withPath(loc.getPath() + "_middle"), loc.withPath(loc.getPath() + "_top"));
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> DOOR_FRAME = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<DoorFrameBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.doorFrameBlock(block, side, bottom, top), (block, texture) -> {
                ResourceLocation loc = provider.blockTexture(block);
                provider.doorFrameBlock(block, loc.withPath(loc.getPath() + "_side"), loc.withPath(loc.getPath() + "_bottom"), loc.withPath(loc.getPath() + "_top"));
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> DOOR_FRAME_LINTEL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<DoorFrameLintelBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.doorFrameLintelBlock(block, side, bottom, top), (block, texture) -> {
                ResourceLocation loc = provider.blockTexture(block);
                provider.doorFrameLintelBlock(block, loc.withPath(loc.getPath() + "_side"), loc.withPath(loc.getPath() + "_bottom"), loc.withPath(loc.getPath() + "_top"));
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> EIGHTH = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<EighthBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.eighthBlock(block, side, bottom, top), provider::eighthBlock);

//    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> FENCE = (textureFrom) -> (b, provider) ->
//            provider.fenceBlock((FenceBlock) b.get(), provider.blockTexture(BlockManager.getMainBy(b, textureFrom)));

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> FENCE = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<FenceBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> {
                provider.fenceBlock(block, top);
                provider.models().fenceInventory(provider.name(block) + "_inventory", top);
            }, (block, texture) -> {
                provider.fenceBlock(block, texture);
                provider.models().fenceInventory(provider.name(block) + "_inventory", texture);
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> FENCE_GATE = (textureFrom) -> (b, provider) ->
            provider.fenceGateBlock((FenceGateBlock) b.get(), provider.blockTexture(BlockManager.getMainBy(b, textureFrom)));

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> PILLAR = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<PillarLayerBlock>fixBlockTex(textureFrom, b, provider, provider::pillarLayerBlock, provider::pillarLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> PRESSURE_PLATE = (textureFrom) -> (b, provider) ->
            provider.pressurePlateBlock((PressurePlateBlock) b.get(), provider.blockTexture(BlockManager.getMainBy(b, textureFrom)));

    public static final BiFunction<Supplier<Block>, Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> SIGN = (sign, wall) -> (b, provider) -> {
        try {
            provider.signBlock((StandingSignBlock) sign.get(), (WallSignBlock) wall.get(), provider.blockTexture(BlockManager.getMainBy(b, sign)));
        } catch (Exception ignored) {
        }
    };

    public static final BiFunction<Supplier<Block>, Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> HANGING_SIGN = (sign, wall) -> (b, provider) ->
            provider.hangingSignBlock(sign.get(), wall.get(), provider.blockTexture(BlockManager.getMainBy(b, sign)));

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> TRAP_DOOR = (textureFrom) -> (b, provider) ->
            provider.trapdoorBlock((TrapDoorBlock) b.get(), provider.blockTexture(BlockManager.getMainBy(b, textureFrom)), true);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> QUARTER = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<QuarterLayerBlock>fixBlockTex(textureFrom, b, provider, provider::quarterLayerBlock, provider::quarterLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> VERTICAL_QUARTER = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<VerticalQuarterLayerBlock>fixBlockTex(textureFrom, b, provider, provider::quarterLayerVerticalBlock, provider::quarterLayerVerticalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ROOF_22 = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<Roof22Block>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.roof22Block(block, tex), provider::roof22Block);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ROOF_45 = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<Roof45Block>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.roof45Block(block, tex), provider::roof45Block);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ROOF_67 = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<Roof67Block>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.roof67Block(block, tex), provider::roof67Block);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> ROOF_PEAK = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<RoofPeakBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.roofPeakBlock(block, tex), provider::roofPeakBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> SLAB = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<SlabLayerBlock>fixBlockTex(textureFrom, b, provider, provider::slabLayerBlock, provider::slabLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> SLAB_VERTICAL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<VerticalSlabLayerBlock>fixBlockTex(textureFrom, b, provider, provider::slabVerticalLayerBlock, provider::slabVerticalLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> STAIRS = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<StairBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.stairsBlock(block, side, bottom, top), provider::stairsBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> WALL = (textureFrom) -> (b, provider) ->
            ModBlockHelper.<WallBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> {
                provider.wallBlock(block, top);
                provider.models().wallInventory(provider.name(block) + "_inventory", top);
            }, (block, texture) -> {
                provider.wallBlock(block, texture);
                provider.models().wallInventory(provider.name(block) + "_inventory", texture);
            });

    public static final BiFunction<String, Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> WINDOW = (s, textureFrom) -> (b, provider) ->
            ModBlockHelper.<WindowBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.windowBlock(block, side, bottom, top), provider::windowBlock);

    public static final BiFunction<String, Supplier<Block>, BiConsumer<Supplier<Block>, ModBlockStateProvider>> WINDOW_HALF = (s, textureFrom) -> (b, provider) ->
            ModBlockHelper.<HalfWindowBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> provider.windowHalfBlock(block, side, bottom, top), provider::windowHalfBlock);


    public static <T extends ModelBuilder<T>> T textures(T builder, ResourceLocation location) {
        ResourceLocation sideLoc = location;
        ResourceLocation bottomLoc = location;
        ResourceLocation topLoc = location;
        if (location.getPath().equals("block/basalt") || location.getPath().equals("block/polished_basalt")) {
            sideLoc = new ResourceLocation(location.getNamespace(), location.getPath() + "_side");
            bottomLoc = topLoc = new ResourceLocation(location.getNamespace(), location.getPath() + "_top");
        }

        return builder.texture("side", sideLoc)
                .texture("bottom", bottomLoc)
                .texture("top", topLoc);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Block> void fixBlockTex(Supplier<Block> textureFrom, Supplier<Block> b, ModBlockStateProvider provider, BlockGenLayerWithSides<T> genWithSides, BlockGenWithBaseBlock<T> gen) {
        ResourceLocation tex = provider.blockTexture(BlockManager.getMainBy(b, textureFrom));
        ResourceLocation top = tex;
        ResourceLocation side = tex;
        ResourceLocation bottom = tex;
        String originalPath = tex.getPath();
        String modifiedPath = tex.getPath();
        boolean isMinecraftNamespace = tex.getNamespace().equals("minecraft");

        if (tex.getPath().contains("_wood") && !tex.getPath().contains("stained") || tex.getPath().contains("_hyphae")) {
            String replacement = tex.getPath().contains("_wood") ? "_wood" : "_hyphae";
            String newTexture = tex.getPath().contains("_wood") ? "_log" : "_stem";
            tex = new ResourceLocation(tex.getNamespace(), tex.getPath().replace(replacement, newTexture));
            genWithSides.data((T) b.get(), tex, tex, tex, tex);
        } else if (tex.getPath().contains("bamboo_block") && !tex.getPath().contains("stained")) {
            tex = new ResourceLocation(tex.getNamespace(), "block/stripped_bamboo_block");
            genWithSides.data((T) b.get(), tex, tex, tex, tex);
        } else if (tex.getPath().contains("tudor")) {
            String woodType = extractWoodType(tex);
            String prefix = tex.getPath().contains("stained") ? "stained_" : "";
            top = bottom = new ResourceLocation(tex.getNamespace(), "block/" + prefix + woodType + "_boards");
            genWithSides.data((T) b.get(), side, bottom, top, tex);
        } else if (tex.getPath().equals("block/basalt") || tex.getPath().equals("block/polished_basalt") || (tex.getPath().contains("quartz") && !tex.getPath().contains("brick"))) {
            side = new ResourceLocation(tex.getNamespace(), modifiedPath + "_side");
            top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
            if (tex.getPath().equals("block/smooth_quartz")) {
                side = top = bottom = new ResourceLocation(tex.getNamespace(), "block/quartz_block_bottom");
            }
            genWithSides.data((T) b.get(), side, bottom, top, tex);
        } else if (tex.getPath().contains("sandstone")) {
            if (tex.getPath().contains("cut")) {
                modifiedPath = modifiedPath.replace("cut_", "");
                side = tex;
                top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
            } else if (tex.getPath().contains("chiseled")) {
                modifiedPath = modifiedPath.replace("chiseled_", "");
                side = tex;
                top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
            } else if (tex.getPath().contains("smooth")) {
                modifiedPath = modifiedPath.replace("smooth_", "");
                side = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
                top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
            } else {
                side = tex;
                top = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
                bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_bottom");
            }

            if (isMinecraftNamespace) {
                side = tex.getPath().contains("smooth") ? top : new ResourceLocation("minecraft", originalPath);
                top = new ResourceLocation("minecraft", modifiedPath + "_top");
                bottom = !tex.getPath().contains("smooth") && !tex.getPath().contains("cut") && !tex.getPath().contains("chiseled") ?
                        new ResourceLocation("minecraft", modifiedPath + "_bottom") : top;
            }

            genWithSides.data((T) b.get(), side, bottom, top, tex);
        } else {
            gen.data((T) b.get(), tex);
        }
    }

    private static String extractWoodType(ResourceLocation resourceLocation) {
        if (resourceLocation.getPath().contains("acacia")) {
            return "acacia";
        } else if (resourceLocation.getPath().contains("bamboo")) {
            return "bamboo";
        } else if (resourceLocation.getPath().contains("birch")) {
            return "birch";
        } else if (resourceLocation.getPath().contains("cherry")) {
            return "cherry";
        } else if (resourceLocation.getPath().contains("dark_oak")) {
            return "dark_oak";
        } else if (resourceLocation.getPath().contains("jungle")) {
            return "jungle";
        } else if (resourceLocation.getPath().contains("mangrove")) {
            return "mangrove";
        } else if (resourceLocation.getPath().contains("oak") && !resourceLocation.getPath().contains("dark")) {
            return "oak";
        } else if (resourceLocation.getPath().contains("spruce")) {
            return "spruce";
        } else {
            return null;
        }
    }


    @FunctionalInterface
    interface BlockGenLayerWithSides<T extends Block> {
        void data(T block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, ResourceLocation baseBlock);
    }

    @FunctionalInterface
    interface BlockGenWithBaseBlock<T extends Block> {
        void data(T block, ResourceLocation baseBlock);
    }
}