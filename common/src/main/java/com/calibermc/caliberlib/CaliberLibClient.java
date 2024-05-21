package com.calibermc.caliberlib;

import com.calibermc.caliberlib.block.management.BlockManager;
import net.mehvahdjukaar.moonlight.api.misc.EventCalled;
import net.mehvahdjukaar.moonlight.api.platform.ClientHelper;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class CaliberLibClient {
    public static void init() {
        ClientHelper.addBlockColorsRegistration(CaliberLibClient::registerBlockColors);
    }

    @EventCalled
    private static void registerBlockColors(ClientHelper.BlockColorEvent event) {
        for (List<BlockManager> blockManagers : BlockManager.BLOCK_MANAGERS.values()) {
            for (BlockManager blockManager : blockManagers) {
                for (var e : blockManager.getBlocks().values()) {
                    Block childBlock = e.getSecond().get();
                    Block value = blockManager.mainChild();
                    event.register((blockState, blockAndTintGetter, blockPos, i) -> event.getColor(childBlock.defaultBlockState(), blockAndTintGetter, blockPos, i), value);
                }
            }
        }
    }
}
