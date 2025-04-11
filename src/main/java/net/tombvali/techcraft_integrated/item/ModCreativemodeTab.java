package net.tombvali.techcraft_integrated.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativemodeTab {
    public static final CreativeModeTab TECHCRAFT_INTEGRATED = new CreativeModeTab("techcrafttab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.DICK_STICK.get());
        }
    };
}
