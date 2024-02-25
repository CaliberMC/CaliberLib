package com.calibermc.caliberlib.block.properties;

import com.calibermc.caliberlib.CaliberLib;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodType {

    public static final WoodType CEDAR = WoodType.register(new WoodType(CaliberLib.MOD_ID + ":cedar", ModBlockSetType.CEDAR));
    public static final WoodType FIR = WoodType.register(new WoodType(CaliberLib.MOD_ID + ":fir", ModBlockSetType.FIR));
    public static final WoodType MAPLE = WoodType.register(new WoodType(CaliberLib.MOD_ID + ":maple", ModBlockSetType.MAPLE));
    public static final WoodType PINE = WoodType.register(new WoodType(CaliberLib.MOD_ID + ":pine", ModBlockSetType.PINE));

}

