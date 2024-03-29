package com.calibermc.caliberlib.block.shapes;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum SingleDoubleShape implements StringRepresentable {
    SINGLE("single"),
    DOUBLE("double");


    private final String name;

    SingleDoubleShape(String pName) {
        this.name = pName;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}

