package com.calibermc.caliberlib.block.management;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.custom.*;
import com.calibermc.caliberlib.data.ModBlockFamily;
import com.calibermc.caliberlib.util.AdditionalDataGen;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;

/**
 * Created to reduce the code in ModBlocks, for optimization and easier register of complex blocks
 */
public class BlockManager extends BlockType {

    public static final List<Supplier<Block>> ALL_BLOCKS = new ArrayList<>();
    public static final Map<String, List<BlockManager>> BLOCK_MANAGERS = new HashMap<>();
    public static final Map<BlockAdditional, Consumer<AdditionalDataGen>> DATA_GEN_MAP = new HashMap<>();

    private final BlockSetType blockSetType;
    private final List<BlockAdditional> directions;
    private BlockAdditional baseBlock;
    private ImmutableMap<BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> blocks;

    BlockManager(ResourceLocation id, List<BlockAdditional> directions, BlockSetType blockSetType) {
        super(id);
        this.blockSetType = blockSetType;
        this.directions = directions;

        ImmutableMap.Builder<BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> builder = ImmutableMap.builder();
        for (BlockAdditional e : this.directions) {
            String modifiedName = formatName(this.id.getPath());

            if (e.variant != ModBlockFamily.Variant.BASE) {
                String name1 = "%s_%s".formatted(modifiedName, e.variant());
                ResourceLocation location = new ResourceLocation(this.id.getNamespace(), name1);
                builder.put(e, Pair.of(location, e.blockSupplier));
            } else {
                if (!e.skipRegister) {
                    RegHelper.registerBlock(this.id, e.blockSupplier);
                    builder.put(e, Pair.of(this.id, e.blockSupplier));
                } else {
                    builder.put(e, Pair.of(new ResourceLocation(this.id.getPath()), e.blockSupplier));
                }
                this.baseBlock = e;
            }
        }

        this.blocks = builder.build();
        for (Map.Entry<BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : this.blocks.entrySet()) {
            e.getKey().blockConsumer.accept(this, e.getValue());
        }
        ALL_BLOCKS.addAll(this.blocks.values().stream().map(Pair::getSecond).toList());



        if (!BLOCK_MANAGERS.containsKey(id.getNamespace())) {
            BLOCK_MANAGERS.put(id.getNamespace(), Lists.newArrayList(this));
        } else {
            List<BlockManager> managers = BLOCK_MANAGERS.get(id.getNamespace());
            managers.add(this);
            BLOCK_MANAGERS.put(id.getNamespace(), managers);
        }
    }

    public void register(Registrator<Block> blockRegistrator) {
        for (Map.Entry<BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : this.blocks.entrySet()) {
            if (e.getKey().variant != ModBlockFamily.Variant.BASE) {
                blockRegistrator.register(e.getValue().getFirst(), e.getValue().getSecond().get());
            }
        }
    }

    public static @NotNull String formatName(String name) {
        String modifiedName;
        if (name.contains("bricks")) {
            modifiedName = name.replace("_bricks", "_brick");
        } else if (name.contains("tiles")) {
            modifiedName = name.replace("_tiles", "_tile");
        } else if (name.contains("planks")) {
            modifiedName = name.replace("_planks", "");
        } else if (name.contains("wood")) {
            modifiedName = name.replace("_wood", "");
        } else if (name.contains("boards")) {
            modifiedName = name.replace("boards", "board");
        } else if (name.contains("bamboo_block")) {
            modifiedName = name.replace("_block", "");
        } else if (name.contains("shingles")) {
            modifiedName = name.replace("_shingles", "_shingle");
        } else {
            modifiedName = name;
        }
        return modifiedName;
    }

    public static Block getMainBy(Supplier<Block> block, Supplier<Block> textureFrom) {
        for (BlockManager blockManager : BLOCK_MANAGERS.get(BuiltInRegistries.BLOCK.getKey(block.get()).getNamespace())) {
            for (Pair<ResourceLocation, Supplier<Block>> e : blockManager.getBlocks().values()) {
                if (e.getSecond() == block && blockManager.getByVariant(ModBlockFamily.Variant.BASE) != null) {
                    return blockManager.mainChild();
                }
            }
        }
        return textureFrom.get();
    }

    private static final Supplier<Block> AIR = () -> Blocks.AIR;

    public static Builder register(String name, String modid, BlockBehaviour.Properties properties, Collection<ModBlockFamily.Variant> variants) {
        return BlockManager.register(name, modid, properties, AIR, variants);
    }

    public static Builder registerOneBlock(String name, String modid, Supplier<Block> block, boolean skipRegister) {
        return new Builder(name, modid).addVariant(ModBlockFamily.Variant.BASE, block, (b) -> b.skipRegister = skipRegister);
    }

    public static Builder register(String name, String modid, BlockBehaviour.Properties properties, Supplier<Block> blockSupplier, Collection<ModBlockFamily.Variant> variants) {
        return register(name, (b) -> BlockSetType.values().forEach(type -> {
            if (name.contains(type.name())) {
                b.type(type);
            }
        }), modid, properties, blockSupplier, variants);
    }

    public static Builder register(String name, Consumer<Builder> consumer, String modid, BlockBehaviour.Properties properties, Supplier<Block> blockSupplier, Collection<ModBlockFamily.Variant> variants) {
        return BlockManager.register(name, consumer, modid, () -> properties, blockSupplier, variants);
    }

//    public Block get(ModBlockFamily.Variant direction) {
//        BlockAdditional a = this.getByVariant(direction);
//        if (a != null && a.skipRegister) {
//            return a.blockSupplier.get();
//        }
//        return this.blocks.get(this.getByVariant(direction)).getSecond().get();
//    }

    public static Builder register(String name, Consumer<Builder> consumer, String modid, Supplier<BlockBehaviour.Properties> properties, Supplier<Block> blockSupplier, Collection<ModBlockFamily.Variant> variants) {
        Builder builder = new Builder(name, modid);
        consumer.accept(builder);
//        ModBlockSetType.values().forEach(mb -> {
//            if (name.contains(mb.name())) {
//                builder.type(mb);
//            }
//        });

        if (blockSupplier.equals(AIR) || variants.contains(ModBlockFamily.Variant.BASE)) {
            Supplier<Block> baseBlock = () -> new Block(properties.get());
            builder.addVariant(ModBlockFamily.Variant.BASE, baseBlock);
            BlockManager.addDefaultVariants(builder, properties, baseBlock, variants);
        } else {
            builder.addVariant(ModBlockFamily.Variant.BASE, blockSupplier, BlockAdditional::skipRegister);
            BlockManager.addDefaultVariants(builder, properties, blockSupplier, variants);
        }
        return builder;
    }

    public static void addDefaultVariants(Builder builder, BlockBehaviour.Properties properties, Supplier<Block> blockSupplier, Collection<ModBlockFamily.Variant> variants) {
        BlockManager.addDefaultVariants(builder, () -> properties, blockSupplier, variants);
    }

    public static void addDefaultVariants(Builder builder, Supplier<BlockBehaviour.Properties> properties, Supplier<Block> blockSupplier, Collection<ModBlockFamily.Variant> variants) {
        String modId = builder.modid;
        WoodType woodType = WoodType.values().filter(p ->
                p.name().equals(builder.blockSetType.name())).findFirst().orElse(WoodType.OAK);
        Supplier<BlockState> baseBlockState = () -> BlockManager.BLOCK_MANAGERS.get(modId).stream().filter(blockManager ->
                blockManager.getName().equals(builder.name)).findFirst().orElseThrow().mainChild().defaultBlockState();
        for (ModBlockFamily.Variant variant : variants) {
            if (variant != ModBlockFamily.Variant.BASE && variant.caliberIsLoaded()) {
                switch (variant) {
                    case ARCH ->
                            builder.addVariant(variant, () -> new ArchBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ARCH_HALF ->
                            builder.addVariant(variant, () -> new HalfArchBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ARCH_LARGE ->
                            builder.addVariant(variant, () -> new LargeArchBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ARCH_LARGE_HALF ->
                            builder.addVariant(variant, () -> new LargeHalfArchBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ARROWSLIT ->
                            builder.addVariant(variant, () -> new ArrowSlitBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case BALUSTRADE ->
                            builder.addVariant(variant, () -> new BalustradeBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case BUTTON ->
                            builder.addVariant(variant, () -> new ButtonBlock(properties.get(), builder.blockSetType, 20, false), (b) -> b.textureFrom(blockSupplier));
                    case BEAM_DIAGONAL ->
                            builder.addVariant(variant, () -> new DiagonalBeamBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case BEAM_HORIZONTAL ->
                            builder.addVariant(variant, () -> new HorizontalBeamBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case BEAM_LINTEL ->
                            builder.addVariant(variant, () -> new BeamLintelBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case BEAM_POSTS ->
                            builder.addVariant(variant, () -> new BeamPostsBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case BEAM_VERTICAL ->
                            builder.addVariant(variant, () -> new VerticalBeamBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case CAPITAL ->
                            builder.addVariant(variant, () -> new CapitalBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case CORNER ->
                            builder.addVariant(variant, () -> new CornerLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case CORNER_SLAB ->
                            builder.addVariant(variant, () -> new CornerSlabLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case CORNER_SLAB_VERTICAL ->
                            builder.addVariant(variant, () -> new VerticalCornerSlabLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case DOOR ->
                            builder.addVariant(variant, () -> new DoorBlock(properties.get(), builder.blockSetType), (b) -> b.lootGen(m -> new ResourceLocation(CaliberLib.MOD_ID, "template/loot_table_door.json")).textureFrom(blockSupplier));
                    case DOOR_FRAME ->
                            builder.addVariant(variant, () -> new DoorFrameBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case DOOR_FRAME_LINTEL ->
                            builder.addVariant(variant, () -> new DoorFrameLintelBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case EIGHTH ->
                            builder.addVariant(variant, () -> new EighthLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case FENCE ->
                            builder.addVariant(variant, () -> new FenceBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case FENCE_GATE ->
                            builder.addVariant(variant, () -> new FenceGateBlock(properties.get(), woodType), (b) -> b.textureFrom(blockSupplier));
                    case LAYER ->
                            builder.addVariant(variant, () -> new SlabLayerBlock(properties.get(), 1), (b) -> b.textureFrom(blockSupplier));
                    case LAYER_VERTICAL ->
                            builder.addVariant(variant, () -> new VerticalSlabLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case PILLAR ->
                            builder.addVariant(variant, () -> new PillarLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case PRESSURE_PLATE ->
                            builder.addVariant(variant, () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, properties.get(), builder.blockSetType), (b) -> b.textureFrom(blockSupplier));
                    case QUARTER ->
                            builder.addVariant(variant, () -> new QuarterLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case QUARTER_VERTICAL ->
                            builder.addVariant(variant, () -> new VerticalQuarterLayerBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ROOF_22 ->
                            builder.addVariant(variant, () -> new Roof22Block(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ROOF_45 ->
                            builder.addVariant(variant, () -> new Roof45Block(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ROOF_67 ->
                            builder.addVariant(variant, () -> new Roof67Block(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case ROOF_PEAK ->
                            builder.addVariant(variant, () -> new RoofPeakBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case SIGN ->
                            builder.addVariant(variant, () -> new StandingSignBlock(properties.get(), woodType), (a) -> a.textureFrom(blockSupplier).item((m, b) -> new SignItem(new Item.Properties().stacksTo(16), b, m.get(ModBlockFamily.Variant.WALL_SIGN))));
                    case HANGING_SIGN ->
                            builder.addVariant(variant, () -> new CeilingHangingSignBlock(properties.get(), woodType), (a) -> a.textureFrom(blockSupplier).item((m, b) -> new HangingSignItem(b, m.get(ModBlockFamily.Variant.WALL_HANGING_SIGN), new Item.Properties().stacksTo(16))));
                    case SLAB ->
                            builder.addVariant(variant, () -> new SlabBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case SLAB_VERTICAL ->
                            builder.addVariant(variant, () -> new VerticalSlabBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case TALL_DOOR ->
                            builder.addVariant(variant, () -> new TallDoorBlock(properties.get(), builder.blockSetType), (b) -> b.lootGen(m -> new ResourceLocation(CaliberLib.MOD_ID, "template/loot_table_tall_door.json")).textureFrom(blockSupplier));
                    case STAIRS ->
                            builder.addVariant(variant, () -> new StairBlock(baseBlockState.get(), properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case TRAPDOOR ->
                            builder.addVariant(variant, () -> new TrapDoorBlock(properties.get(), builder.blockSetType), (b) -> b.textureFrom(blockSupplier));
                    case WALL ->
                            builder.addVariant(variant, () -> new WallBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case WALL_SIGN -> builder.addVariant(variant, () -> new WallSignBlock(properties.get(), woodType), BlockAdditional::noItem);
                    case WALL_HANGING_SIGN ->
                            builder.addVariant(variant, () -> new WallHangingSignBlock(properties.get(), woodType), BlockAdditional::noItem);
                    case WINDOW ->
                            builder.addVariant(variant, () -> new WindowBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                    case WINDOW_HALF ->
                            builder.addVariant(variant, () -> new HalfWindowBlock(properties.get()), (b) -> b.textureFrom(blockSupplier));
                }
            }
        }
    }

    public String getName() {
        return this.id.getPath();
    }

    // example of registering with own type of block
    /*public static Builder registerCedarOrSmth(String name, String modid, BlockBehaviour.Properties properties, Supplier<Block> blockSupplier, Collection<ModBlockFamily.Variant> variants) {
        return register(name, (b) -> b.type(ModBlockSetType.CEDAR), register, properties, blockSupplier, variants);
    }*/

    public BlockSetType blockType() {
        return blockSetType;
    }

    public Block get(ModBlockFamily.Variant direction) {
        BlockAdditional a = this.getByVariant(direction);
        assert a != null;
        if (a.skipRegister) {
            return a.blockSupplier.get();
        }
        return BuiltInRegistries.BLOCK.get(this.blocks.get(this.getByVariant(direction)).getFirst());
    }

    public BlockAdditional getByVariant(ModBlockFamily.Variant variant) {
        if (variant != ModBlockFamily.Variant.BASE) {
            for (BlockAdditional blockAdditional : this.blocks.keySet()) {
                if (blockAdditional.variant == variant) {
                    return blockAdditional;
                }
            }
        }
        return this.baseBlock;
    }

    public HashMap<BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> getBlocks() {
        HashMap<BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> map = new HashMap<>();
        this.blocks.forEach((ba, pair) -> {
            if (!ba.skipRegister)
                map.put(ba, Pair.of(pair.getFirst(), () -> BuiltInRegistries.BLOCK.get(pair.getFirst())));
        });
        return map;
    }

    @Override
    public String getTranslationKey() {
        return "blockmanager." + this.getNamespace() + "." + this.getTypeName();
    }

    @Override
    protected void initializeChildrenBlocks() {
        this.addChild("base", this.mainChild());
    }

    @Override
    protected void initializeChildrenItems() {

    }

    @Override
    public Block mainChild() {
        return this.get(ModBlockFamily.Variant.BASE);
    }

    public static class Builder {

        public final String name;
        public final String modid;
        public final List<BlockAdditional> blocks = new ArrayList<>();
        public BiFunction<String, Supplier<Block>, Block> registerBlockFunc;
        public BlockSetType blockSetType = BlockSetType.STONE;

        /*public Builder(String name, Registrator<Block> registry) {
            this(name, registry.modid());
            this.registerBlockFunc = registry::register;
        }*/

        public Builder(String name, String modid) {
            this.name = name;
            this.modid = modid;
            //this.registerBlockFunc = (s, b) -> CaliberLib.BLOCKS.put(new ResourceLocation(s), b.get());
        }

        public Builder type(BlockSetType type) {
            this.blockSetType = type;
            return this;
        }

        public Builder registerBlockFunc(BiFunction<String, Supplier<Block>, Block> registerBlockFunc) {
            this.registerBlockFunc = registerBlockFunc;
            return this;
        }

        public Builder addVariant(ModBlockFamily.Variant variant, Supplier<Block> blockSupplier) {
            this.addVariant(variant, blockSupplier, (b) -> {
            });
            return this;
        }

        public Builder addVariant(ModBlockFamily.Variant variant, Supplier<Block> block, Consumer<BlockAdditional> consumer) {
            this.addVariant(variant, block, null, consumer);
            return this;
        }

        public Builder addVariant(ModBlockFamily.Variant variant, Supplier<Block> block, BiFunction<String, Supplier<Block>, Block> registerBlockFunc, Consumer<BlockAdditional> consumer) {
            BlockAdditional additional = new BlockAdditional(variant, block, registerBlockFunc);
            consumer.accept(additional);
            this.blocks.add(additional);
            return this;
        }

        public BlockManager build() {
            for (BlockAdditional block : this.blocks) {
                if (block.registerBlockFunc == null) {
                    block.registerBlockFunc = this.registerBlockFunc;
                }
            }

            return new BlockManager(new ResourceLocation(this.modid, this.name), this.blocks, this.blockSetType);
        }
    }

    public static final class BlockAdditional {
        public final ModBlockFamily.Variant variant;
        public final Supplier<Block> blockSupplier;
        public BiFunction<BlockManager, Block, Item> itemSupplier = (manager, block) -> new BlockItem(block, new Item.Properties());
        public boolean skipRegister; // used to register in manager, but don't register it in minecraft (basically when block already registered in mc)

        public BiConsumer<BlockManager, Pair<ResourceLocation, Supplier<Block>>> blockConsumer = (manager, b) -> {
        };
        public BiFunction<String, Supplier<Block>, Block> registerBlockFunc;
        public Function<BlockManager, ResourceLocation> lootGen = (b) -> new ResourceLocation(CaliberLib.MOD_ID, "template/loot_table.json");
        public Supplier<Block> textureFrom;
        public BiConsumer<BlockManager, ResourceManager> serverDynamicResources = (b, m) -> {};

        public BlockAdditional(ModBlockFamily.Variant variant, Supplier<Block> blockSupplier, BiFunction<String, Supplier<Block>, Block> registerBlockFunc) {
            this.variant = variant;
            this.blockSupplier = blockSupplier;
            this.registerBlockFunc = registerBlockFunc;
        }

        public String variant() {
            return this.variant.getName().toLowerCase();
        }

        public BlockAdditional afterRegister(BiConsumer<BlockManager, Pair<ResourceLocation, Supplier<Block>>> blockConsumer) {
            this.blockConsumer = blockConsumer;
            return this;
        }

        public BlockAdditional serverDynamicResources(BiConsumer<BlockManager, ResourceManager> serverDynamicResources) {
            this.serverDynamicResources = serverDynamicResources;
            return this;
        }

        public BlockAdditional lootGen(Function<BlockManager, ResourceLocation> lootGen) {
            this.lootGen = lootGen;
            return this;
        }

        public BlockAdditional item(BiFunction<BlockManager, Block, Item> itemFunc) {
            this.itemSupplier = itemFunc;
            return this;
        }

        public BlockAdditional noItem() {
            this.itemSupplier = (m, b) -> null;
            return this;
        }

        public BlockAdditional textureFrom(Supplier<Block> blockSupplier) {
            this.textureFrom = blockSupplier;
            return this;
        }

        public BlockAdditional skipRegister() {
            this.skipRegister = true;
            return this;
        }

    }
}
