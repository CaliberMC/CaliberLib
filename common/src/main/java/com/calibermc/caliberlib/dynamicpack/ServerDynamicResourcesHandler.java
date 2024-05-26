package com.calibermc.caliberlib.dynamicpack;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.management.BlockManager;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.SimpleTagBuilder;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ServerDynamicResourcesHandler extends DynServerResourcesGenerator {

    public static ServerDynamicResourcesHandler registerResourceHandler(String modId) {
        ServerDynamicResourcesHandler resourcesHandler = new ServerDynamicResourcesHandler(modId);
        resourcesHandler.register();
        return resourcesHandler;
    }

    public ServerDynamicResourcesHandler(String modId) {
        super(new DynamicDataPack(new ResourceLocation(modId, "generated_pack")));
        //needed for tags
        getPack().addNamespaces("minecraft");
        getPack().addNamespaces("forge");
        this.dynamicPack.setGenerateDebugResources(PlatHelper.isDev());
    }

    @Override
    public Logger getLogger() {
        return CaliberLib.LOGGER;
    }

    @Override
    public boolean dependsOnLoadedPacks() {
        return true;
    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {
        if (BlockManager.BLOCK_MANAGERS.containsKey(this.modId)) {
            for (BlockManager w : BlockManager.BLOCK_MANAGERS.get(this.modId)) {
                for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : w.getBlocks().entrySet()) {
                    e.getKey().serverDynamicResources.accept(w, manager);
                    var template = StaticResource.getOrFail(manager, ResType.GENERIC.getPath(e.getKey().lootGen.apply(w)));

                    var block = e.getValue().getSecond().get();
                    String fullText = new String(template.data, StandardCharsets.UTF_8);

                    fullText = fullText.replace("$block", Utils.getID(block).toString());
                    fullText = fullText.replace("$name", Utils.getID(block).toString().replace(":", ":blocks/"));

                    String id = template.location.toString();
                    id = id.replace("template/loot_table", "loot_tables/" + block.getLootTable().getPath());
                    this.dynamicPack.addResource(StaticResource.create(fullText.getBytes(), new ResourceLocation(id)));
                }
                addTags(manager, w);
            }
        }
    }

    private void addTags(ResourceManager manager, BlockManager w) {
        copyTags(manager, w, BlockTags.NEEDS_STONE_TOOL, Registries.BLOCK);
        copyTags(manager, w, BlockTags.NEEDS_IRON_TOOL, Registries.BLOCK);
        copyTags(manager, w, BlockTags.NEEDS_DIAMOND_TOOL, Registries.BLOCK);
        copyTags(manager, w, BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK);
        copyTags(manager, w, BlockTags.MINEABLE_WITH_HOE, Registries.BLOCK);
        copyTags(manager, w, BlockTags.MINEABLE_WITH_PICKAXE, Registries.BLOCK);
        copyTags(manager, w, BlockTags.MINEABLE_WITH_SHOVEL, Registries.BLOCK);
        copyTags(manager, w, BlockTags.DRAGON_IMMUNE, Registries.BLOCK);
        copyTags(manager, w, BlockTags.DAMPENS_VIBRATIONS, Registries.BLOCK);
        copyTags(manager, w, BlockTags.GUARDED_BY_PIGLINS, Registries.BLOCK);
        copyTags(manager, w, ItemTags.PIGLIN_LOVED, Registries.ITEM);
    }

    private <T> void copyTags(ResourceManager manager, BlockManager w, TagKey<T> tagKey, ResourceKey<Registry<T>> registry) {
        Set<String> tagValues = getTags(manager, tagKey);

        SimpleTagBuilder builer = SimpleTagBuilder.of(tagKey);
        for (var e : w.getBlocks().entrySet()) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(e.getValue().getSecond().get());
            if (tagValues.contains(id.toString())) {
                builer.addEntry(e.getValue());
            }
        }
        var b = builer.build();
        if (!b.isEmpty()) {
            dynamicPack.addTag(builer, registry);
        }
    }

    @NotNull
    private static <T> Set<String> getTags(ResourceManager manager, TagKey<T> tagKey) {
        var resources = manager.getResourceStack(ResType.TAGS.getPath(tagKey.location().withPrefix(tagKey.registry().location().getPath() + "s/")));
        Set<String> tagValues = new HashSet<>();
        Set<String> actualTags = new HashSet<>();
        for (var r : resources) {
            try (var res = r.open()) {
                RPUtils.deserializeJson(res).getAsJsonArray("values")
                        .asList().stream()
                        .filter(JsonElement::isJsonPrimitive).forEach(v -> tagValues.add(v.getAsString()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for (var s : tagValues) {
            if (s.startsWith("#")) {
                var res = new ResourceLocation(s.substring(1));
                if (res.getPath().contains("slab")) {
                    TagKey<T> newKey = TagKey.create(tagKey.registry(), res);
                    actualTags.addAll(getTags(manager, newKey));
                }
            }else actualTags.add(s);
        }
        return actualTags;
    }

}