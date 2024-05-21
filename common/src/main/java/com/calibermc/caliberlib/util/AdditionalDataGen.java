package com.calibermc.caliberlib.util;

import com.calibermc.caliberlib.block.management.BlockManager;

public class AdditionalDataGen {
    protected final BlockManager.BlockAdditional additional;

    public AdditionalDataGen(BlockManager.BlockAdditional additional) {
        this.additional = additional;
    }

    public BlockManager.BlockAdditional back() {
        return this.additional;
    }
}
