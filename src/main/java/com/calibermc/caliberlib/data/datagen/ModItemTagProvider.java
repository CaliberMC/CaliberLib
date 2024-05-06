package com.calibermc.caliberlib.data.datagen;

import com.calibermc.caliberlib.block.management.BlockManager;
import com.calibermc.caliberlib.block.properties.properties.RecipeWoodTypes;
import com.calibermc.caliberlib.data.ModBlockFamily;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModItemTagProvider extends ItemTagsProvider {

    protected String modid;

    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pItemTags, String modid, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pItemTags, modid, existingFileHelper);
        this.modid = modid;
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        // Sort BlockManagers by name
        List<BlockManager> sortedBlockManagers = BlockManager.BLOCK_MANAGERS.get(this.modid).stream()
                .sorted(Comparator.comparing(BlockManager::getName))
                .toList();

        for (BlockManager blockManager : sortedBlockManagers) {
            // Sort blocks by name
            List<Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>>> sortedBlocks = blockManager.getBlocks().entrySet().stream()
                    .sorted(Comparator.comparing(e -> e.getValue().getFirst().getPath()))
                    .toList();

            for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : sortedBlocks) {
//        for (BlockManager blockManager : BlockManager.BLOCK_MANAGERS.get(this.modid)) {
//            for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : blockManager.getBlocks().entrySet()) {
                ModBlockFamily.Variant variant = e.getKey().variant;
                String blockName = e.getValue().getFirst().getPath();
                BlockSetType blockSetType = blockManager.blockType();
                ResourceLocation namespace = e.getValue().getFirst();

                if (blockName.contains("crimson") || blockName.contains("warped")) {
                    if (namespace != null && namespace.getNamespace().equals("caliber")) {
                        this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(e.getValue().getSecond().get().asItem());
                    } else {
                        this.tag(ItemTags.NON_FLAMMABLE_WOOD).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                    }
                }

                if (blockName.contains("thatch")) {
                    this.tag(ItemTags.DAMPENS_VIBRATIONS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                }

                if (variant.equals(ModBlockFamily.Variant.BASE)) {
                    if (blockName.contains("cobbled")) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.STONE_CRAFTING_MATERIALS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else if (blockName.contains("limestone_bricks")) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.STONE_BRICKS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.STONE_BRICKS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else if (blockName.contains("log") || ((blockName.contains("wood") && !blockName.equals("woodcutter")))) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.LOGS_THAT_BURN).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.LOGS_THAT_BURN).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else if (blockName.contains("planks")) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.PLANKS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.PLANKS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.DOOR)) {
                    if (blockName.contains("iron")) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.DOORS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.DOORS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.WOODEN_DOORS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.WOODEN_DOORS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.FENCE)) {
                    if (Arrays.stream(RecipeWoodTypes.values()).anyMatch(p -> blockName.contains(p.getName()))) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.WOODEN_FENCES).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.WOODEN_FENCES).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.FENCES).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.FENCES).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.FENCE_GATE)) {
                    if (namespace != null && namespace.getNamespace().equals("caliber")) {
                        this.tag(ItemTags.FENCE_GATES).add(e.getValue().getSecond().get().asItem());
                    } else {
                        this.tag(ItemTags.FENCE_GATES).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.HANGING_SIGN)) {
                    if (namespace != null && namespace.getNamespace().equals("caliber")) {
                        this.tag(ItemTags.HANGING_SIGNS).add(e.getValue().getSecond().get().asItem());
                    } else {
                        this.tag(ItemTags.HANGING_SIGNS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.PRESSURE_PLATE)) {
                    if (namespace != null && namespace.getNamespace().equals("caliber")) {
                        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(e.getValue().getSecond().get().asItem());
                    } else {
                        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.SIGN)) {
                    if (namespace != null && namespace.getNamespace().equals("caliber")) {
                        this.tag(ItemTags.SIGNS).add(e.getValue().getSecond().get().asItem());
                    } else {
                        this.tag(ItemTags.SIGNS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.SLAB)) {
                    if (Arrays.stream(RecipeWoodTypes.values()).anyMatch(p -> blockName.contains(p.getName()))) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.WOODEN_SLABS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.WOODEN_SLABS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.SLABS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.SLABS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.STAIRS)) {
                    if (Arrays.stream(RecipeWoodTypes.values()).anyMatch(p -> blockName.contains(p.getName()))) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.WOODEN_STAIRS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.WOODEN_STAIRS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.STAIRS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.STAIRS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.TRAPDOOR)) {
                    if (Arrays.stream(RecipeWoodTypes.values()).anyMatch(p -> blockName.contains(p.getName()))) {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.WOODEN_TRAPDOORS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.WOODEN_TRAPDOORS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    } else {
                        if (namespace != null && namespace.getNamespace().equals("caliber")) {
                            this.tag(ItemTags.TRAPDOORS).add(e.getValue().getSecond().get().asItem());
                        } else {
                            this.tag(ItemTags.TRAPDOORS).addOptional(new ResourceLocation(modid, e.getValue().getFirst().getPath()));
                        }
                    }
                }
            }
        }

        BuiltInRegistries.BLOCK.stream()
                .filter(block -> {
                    ResourceLocation registryName = BuiltInRegistries.BLOCK.getKey(block);
                    return BlockManager.ALL_BLOCKS.stream().map(Supplier::get).noneMatch(b -> b.equals(block))
                            && registryName != null && registryName.getNamespace().equals(this.modId);
                })
                .forEach(block -> {
                    String itemName = Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath();

                    if (itemName.contains("crimson") || itemName.contains("warped")) {
                        this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(block.asItem());
                    }

                    if (itemName.contains("leaves")) {
                        this.tag(ItemTags.LEAVES).add(block.asItem());
                    }

                    if (itemName.contains("sand") && !itemName.contains("stone")) {
                        this.tag(ItemTags.SAND).add(block.asItem());
                        this.tag(ItemTags.SMELTS_TO_GLASS).add(block.asItem());
                        this.tag(Tags.Items.SAND).add(block.asItem());
                    }

                    if (itemName.contains("ingot")) {
                        this.tag(ItemTags.TRIM_MATERIALS).add(block.asItem());
                    }

                    if (itemName.contains("ore")) {
                        this.tag(Tags.Items.ORES).add(block.asItem());
                        if (itemName.contains("deepslate")) {
                            this.tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(block.asItem());
                        } else if (itemName.contains("nether")) {
                            this.tag(Tags.Items.ORES_IN_GROUND_NETHERRACK).add(block.asItem());
                        } else {
                            this.tag(Tags.Items.ORES_IN_GROUND_STONE).add(block.asItem());
                        }
                    }
                });

        // Item Tags
        for (Map.Entry<ResourceKey<Item>, Item> resourceKeyItemEntry : BuiltInRegistries.ITEM.entrySet()) {
            // Check if the item's registry name is in the MOD_ID namespace
            ResourceLocation registryName = resourceKeyItemEntry.getKey().location();
            String itemName = registryKey.toString();
            if (registryName != null && this.modId.equals(registryName.getNamespace())) {
                Item item = resourceKeyItemEntry.getValue();
                Block block = Block.byItem(item);

                if (item instanceof SwordItem) {
                    this.tag(ItemTags.SWORDS).add(item);
                }

                if (item instanceof PickaxeItem) {
                    this.tag(ItemTags.PICKAXES).add(item);
                }

                if (item instanceof ShovelItem) {
                    this.tag(ItemTags.SHOVELS).add(item);
                }

                if (item instanceof HoeItem) {
                    this.tag(ItemTags.HOES).add(item);

                }
            }
        }
    }
}

