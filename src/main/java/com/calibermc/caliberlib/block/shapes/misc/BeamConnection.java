package com.calibermc.caliberlib.block.shapes.misc;

import com.calibermc.caliberlib.block.custom.DiagonalBeamBlock;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum BeamConnection implements StringRepresentable {
    /**
     * H is equivalent to Horizontal and V is equivalent to Vertical.  Coordinates a, b, c and d are equivalent to the following:
     * A = forward and bottom
     * B = forward and top
     * C = backward and top
     * D = backward and bottom
     */
    NONE("none"),

    // 1 way connections
    AH("ah"),
    CH("ch"),
    DH("dh"),
    AV("av"),
    CV("cv"),
    DV("dv"),

    // 2 way connections
    AH_CH("ah_ch"),
    AH_AV("ah_av"),
    AH_DH("ah_dh"),
    AV_CV("av_cv"),
    AH_CV("ah_cv"),
    AH_DV("ah_dv"),
    AV_CH("av_ch"),
    AV_DH("av_dh"),
    CH_CV("ch_cv"),

    // 4 way connections
    AH_AV_DH_DV("ah_av_dh_dv");



    private final String name;

    BeamConnection(String pName) {
        this.name = pName;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}
