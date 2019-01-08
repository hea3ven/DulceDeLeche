package com.hea3ven.tools.commonutils.util;

import net.minecraft.container.Slot;

public class SlotUtil {

    public static void onSwapCraft(Slot slot, int count) {
        ReflectionUtil.reflectMethod(Slot.class, "onSwapCraft", "func_190900_b",
                new Class<?>[] {Integer.class}, mthd -> mthd.invoke(slot, count));
    }
}
