package com.calibermc.caliberlib.fabric;

import com.calibermc.caliberlib.CaliberLib;
import net.fabricmc.api.ModInitializer;

public class CaliberLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CaliberLib.init();
    }
}
