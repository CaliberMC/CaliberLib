package com.calibermc.caliberlib.dynamicpack;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.management.BlockManager;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynServerResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicDataPack;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

public class ServerDynamicResourcesHandler extends DynServerResourcesGenerator {

    public static final ServerDynamicResourcesHandler INSTANCE = new ServerDynamicResourcesHandler();

    public ServerDynamicResourcesHandler() {
        super(new DynamicDataPack(new ResourceLocation(CaliberLib.MOD_ID, "generated_pack")));
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
        }
    }

}
